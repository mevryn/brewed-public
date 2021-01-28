package com.brewed.core.rest.predict;

import com.brewed.core.model.beer.Beer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/predict")
public class PredictController {

    private final PredictService predictService;

    @Value("${brewed.predict.server.address}")
    private String serverAddress;
    @Value("${brewed.predict.server.port}")
    private String predictPort;


    public PredictController(PredictService predictService) {
        this.predictService = predictService;
    }

    @PostMapping
    public List<Beer> predictBeer(@RequestParam("image") MultipartFile file) throws IOException {
        return predictService.predictBeer(file, serverAddress, predictPort);
    }
}
