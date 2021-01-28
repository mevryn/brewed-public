package com.brewed.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.Range;

import java.util.List;

@Data
@NoArgsConstructor
public class FilterDescription {

    private Range<Double> alcoholPercentageRange;
    private Range<Integer> ibu;
    private Range<Double> sweetness;
    private Range<Double> bitterness;
    private Range<Double> sourness;
    private List<String> beerTypes;

}
