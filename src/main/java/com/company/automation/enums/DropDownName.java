package com.company.automation.enums;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public enum DropDownName {
    COMPANY("Company"),
    CHARGE_TYPE_ID("Charge Type Id"),
    CHARGE_GROUP("Charge Group"),
    USER("User");


    private final String dropdown;

    DropDownName(String dropdown) {
        this.dropdown = dropdown;
    }

    public String getDropdown() {
        return this.dropdown;
    }

}
