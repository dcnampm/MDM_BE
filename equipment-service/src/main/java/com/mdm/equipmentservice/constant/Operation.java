package com.mdm.equipmentservice.constant;

public enum Operation {
    CREATE("create"), UPDATE("update");

    private final String text;

    Operation(String text) {
        this.text = text.toLowerCase();
    }

    @Override
    public String toString() {
        return text.toLowerCase();
    }
}
