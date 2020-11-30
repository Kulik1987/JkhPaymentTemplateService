package ru.kulikovskiy.jkh.jkhpaymenttemplate.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DaDataResponse {
    @JsonProperty("suggestions")
    private List<DaData> daData;
}
