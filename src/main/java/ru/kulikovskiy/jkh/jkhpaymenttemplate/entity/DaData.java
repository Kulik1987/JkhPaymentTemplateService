package ru.kulikovskiy.jkh.jkhpaymenttemplate.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author kulikovskiypn <kulikovskiypn@pochtabank.ru>
 */

public class DaData {
    @JsonProperty("value")
    private String value;
    @JsonProperty("unrestricted_value")
    private String unrestrictedValue;
    @JsonProperty("data")
    private AddressData data;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnrestrictedValue() {
        return unrestrictedValue;
    }

    public void setUnrestrictedValue(String unrestrictedValue) {
        this.unrestrictedValue = unrestrictedValue;
    }

    public AddressData getData() {
        return data;
    }

    public void setData(AddressData data) {
        this.data = data;
    }
}
