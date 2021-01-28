package com.brewed.core.rest.predict;

import com.brewed.core.model.beer.Beer;
import com.brewed.core.predict.PredictLauncher;
import com.brewed.core.rest.beer.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PredictService {
    private final BeerService service;

    @Autowired
    public PredictService(BeerService service) {
        this.service = service;
    }


    public List<Beer> predictBeer(MultipartFile file, String serverAddress,
                                  String predictPort) throws IOException {
        PredictLauncher predictLauncher = new PredictLauncher(file, serverAddress, predictPort);
        Optional<PredictResponse> predict = predictLauncher.predict();
        if (predict.isEmpty()) {
            return null;
        }
        return service.getBeersByBarCode(predict.orElseThrow(IllegalArgumentException::new)
                                                .getLabel());
    }

}
