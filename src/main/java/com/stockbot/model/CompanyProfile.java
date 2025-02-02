package com.stockbot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyProfile {

    private String country;
    private String currency;
    private String exchange;
    private String ipo;
    private BigDecimal marketCapitalization;
    private String name;
    private String phone;
    private BigDecimal shareOutstanding;
    private String ticker;
    @JsonProperty("weburl")
    private String webURL;
    private String logo;
    @JsonProperty("finnhubIndustry")
    private String category;
}
