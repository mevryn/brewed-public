package com.brewed.core.rest.beer.importbeers;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportBearBean {
    @CsvBindByName(column = "NAZWA")
    private String name;
    @CsvBindByName(column = "MARKA")
    private String breweryName;
    @CsvBindByName(column = "CENA_SPRZEDAZY")
    private double price;
    @CsvBindByName(column = "EAN")
    private String barCode;
    @CsvBindByName(column = "typ")
    private String type;
    @CsvBindByName(column = "nuty")
    private String note;
    @CsvBindByName(column = "alkohol")
    private double alcoholPercentage;
    @CsvBindByName(column = "TEMPERATURA")
    private double servingTemperature;
    @CsvBindByName(column = "ibu")
    private int ibu;
}
