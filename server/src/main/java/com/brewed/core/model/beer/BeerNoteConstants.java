package com.brewed.core.model.beer;

import java.util.UUID;

public final class BeerNoteConstants {
    public static final BeerNote DEFAULT_BEER_NOTE = new BeerNote(
            UUID.fromString("04965ea3-96c1-43a7-b3fe-27bb0f59765f"), "Brak");

    private BeerNoteConstants() {
    }
}
