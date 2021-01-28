package com.brewed.core.predict;

import com.brewed.core.rest.predict.PredictResponse;
import com.brewed.core.utils.UrlBuilder;
import com.google.gson.Gson;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

public class PredictLauncher {

    private final MultipartFile file;
    private final String serverAddress;
    private final String predictPort;

    private final String predictRequest = "api/predict";

    public PredictLauncher(MultipartFile multipartFile, String serverAddress,
                           String predictPort) {
        this.file = multipartFile;
        this.serverAddress = serverAddress;
        this.predictPort = predictPort;
    }

    public Optional<PredictResponse> predict() throws IOException {
        String predictResponse = producePredictResponse();
        Gson gson = new Gson();
        PredictResponse parsedPredictResponse = gson.fromJson(predictResponse, PredictResponse.class);
        if (!new File(Objects.requireNonNull(file.getOriginalFilename())).delete()) {
            throw new IllegalArgumentException("File not deleted");
        }
        return Optional.of(parsedPredictResponse);
    }

    private String producePredictResponse() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        byte[] fileContent = file.getBytes();
        MultiValueMap<String, Object> requestMap = generateMapFromMultipartFileContent(fileContent);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(requestMap, headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(
                UrlBuilder.buildUrl(serverAddress, predictPort, predictRequest), httpEntity, String.class);
    }

    private MultiValueMap<String, Object> generateMapFromMultipartFileContent(byte[] fileContent) throws IOException {
        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
        String fileKey = "image";
        requestMap.add(fileKey, createTempFileResource(fileContent, file.getOriginalFilename()));
        return requestMap;
    }

    private Object createTempFileResource(byte[] fileContent, String fileName) throws IOException {
        Path tempFile = Path.of(fileName);
        Files.write(tempFile, fileContent);
        return new FileSystemResource(tempFile.toFile());
    }
}
