package com.brewed.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.Range;

import java.io.IOException;

public class FilterDescriptionRangeDeserilizer extends JsonDeserializer<Range<?>> {

    @Override
    public Range<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec()
                                  .readTree(jsonParser);
        Comparable min = (Comparable) node.get("min")
                                          .numberValue();
        Comparable max = (Comparable) node.get("max")
                                          .numberValue();
        return Range.between(min, max);
    }
}
