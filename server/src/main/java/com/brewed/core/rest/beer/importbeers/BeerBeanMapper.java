package com.brewed.core.rest.beer.importbeers;

import com.brewed.core.model.beer.Beer;
import com.brewed.core.model.beer.BeerNote;
import com.brewed.core.model.beer.BeerType;
import com.brewed.core.model.brewery.Brewery;

public final class BeerBeanMapper {
    private BeerBeanMapper() {

    }

    public static Beer toModel(ImportBearBean bean) {
        Beer beer = new Beer();
        beer.setName(bean.getName());
        beer.setBrewery(new Brewery(bean.getBreweryName()));
        beer.setBarCode(bean.getBarCode());
        beer.setPrice(bean.getPrice());
        beer.setBeerNote(new BeerNote(bean.getNote()));
        beer.setBeerType(new BeerType(bean.getType()));
        beer.setAlcoholPercentage(bean.getAlcoholPercentage());
        beer.setServingTemperature(bean.getServingTemperature());
        beer.setIbu(bean.getIbu());
        return beer;
    }

}
