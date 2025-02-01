package com.stockbot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockSymbolResponse {

    private int count;
    private List<StockSymbol> result;
}
