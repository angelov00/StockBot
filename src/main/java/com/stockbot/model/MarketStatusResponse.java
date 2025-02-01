package com.stockbot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketStatusResponse {

    private String exchange;
    private String holiday;
    private Boolean isOpen;
    private String session;
    private String timezone;
}
