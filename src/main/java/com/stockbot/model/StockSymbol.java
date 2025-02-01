package com.stockbot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockSymbol {

    private String description;
    private String displaySymbol;
    private String symbol;
    private String type;
}
