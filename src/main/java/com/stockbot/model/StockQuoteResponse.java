package com.stockbot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockQuoteResponse {

    @JsonProperty("c")
    private Double currentPrice;

    @JsonProperty("d")
    private Double change;

    @JsonProperty("dp")
    private Double percentChange;

    @JsonProperty("h")
    private Double dayHigh;

    @JsonProperty("l")
    private Double dayLow;

    @JsonProperty("o")
    private Double openPrice;

    @JsonProperty("pc")
    private Double previousClosePrice;

    private LocalDateTime time;

    public StockQuoteResponse() {
        this.time = LocalDateTime.now();
    }

}
