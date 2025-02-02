package com.stockbot.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.stockbot.model.StockQuoteResponse;

import java.util.concurrent.TimeUnit;

public class CacheService {

    private final Cache<String, StockQuoteResponse> stockQuoteCache;

    public CacheService() {
        this.stockQuoteCache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(128)
                .build();
    }

    public StockQuoteResponse getCachedStock(String ticker) {
        return stockQuoteCache.getIfPresent(ticker);
    }

    public void cacheStock(String ticker, StockQuoteResponse stockQuote) {
        stockQuoteCache.put(ticker, stockQuote);
    }
}
