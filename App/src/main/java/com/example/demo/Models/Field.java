package com.example.demo.Models;

public class Field {
    private String label;
    private String name;
    private String value;
    private boolean readonly;
    private boolean hidden;

    public Field() {
    }

    public Field(String label, String name) {
        this.label = label;
        this.name = name;
    }

    public Field(String label, String name, String value) {
        this.label = label;
        this.name = name;
        this.value = value;
    }

    public Field(String label, String name, String value, boolean readonly) {
        this.label = label;
        this.name = name;
        this.value = value;
        this.readonly = readonly;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public boolean isHidden() {
        return hidden;
    }

    public Field setHidden(boolean hidden) {
        this.hidden = hidden;

        return this;
    }
}
