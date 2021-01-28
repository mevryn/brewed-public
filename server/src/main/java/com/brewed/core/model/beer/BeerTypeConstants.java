package com.brewed.core.model.beer;

import java.util.UUID;

public final class BeerTypeConstants {
    public static final BeerType DEFAULT_BEER_TYPE = new BeerType(
            UUID.fromString("9a268949-3077-4599-a81a-ea2767df17a4"), "Brak");

    private BeerTypeConstants() {
    }
}
