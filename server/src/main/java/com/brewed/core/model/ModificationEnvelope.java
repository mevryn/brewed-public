package com.brewed.core.model;

import lombok.Data;

@Data
public class ModificationEnvelope {
    public final String current;
    public final String modified;

    public ModificationEnvelope(String current, String modified) {
        this.current = current;
        this.modified = modified;
    }
}
