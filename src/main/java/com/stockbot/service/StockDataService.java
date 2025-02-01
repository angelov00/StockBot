package com.stockbot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockbot.model.CompanyProfile;
import com.stockbot.model.MarketStatusResponse;
import com.stockbot.model.StockQuoteResponse;
import com.stockbot.model.StockSymbolResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public class StockDataService {

    private final String apiKey;
    private final OkHttpClient client;
    private final ObjectMapper mapper;
    private final Logger logger;

    public StockDataService(String apiKey) {
        this.apiKey = apiKey;
        this.client = new OkHttpClient();
        this.mapper = new ObjectMapper();
        this.logger = LoggerFactory.getLogger(StockDataService.class);
    }

    /**
     * Fetches real-time stock price for a given ticker symbol.
     */
    public Optional<StockQuoteResponse> getStockByTickerSymbol(String symbol) {
        String url = String.format("https://finnhub.io/api/v1/quote?symbol=%s&token=%s", symbol, apiKey);
        System.out.println(url);
        return getDataFromApi(url, StockQuoteResponse.class);
    }

    /**
     * Returns stock symbols matching a query (e.g., company name, ISIN, CUSIP).
     */
    public Optional<StockSymbolResponse> getSymbolLookup(String query) {
        String url = String.format("https://finnhub.io/api/v1/search?q=%s&token=%s", query, apiKey);
        return getDataFromApi(url, StockSymbolResponse.class);
    }

    /**
     * Fetches the market status for a given exchange.
     */
    public Optional<MarketStatusResponse> getMarketStatus(String exchangeCode) {
        String url = String.format("https://finnhub.io/api/v1/stock/market-status?exchange=%s&token=%s", exchangeCode, apiKey);
        return getDataFromApi(url, MarketStatusResponse.class);
    }

    public Optional<CompanyProfile> getCompanyProfile(String query) {
        String url = String.format("https://finnhub.io/api/v1/stock/profile2?symbol=%s&token=%s", query, apiKey);
        return getDataFromApi(url, CompanyProfile.class);
    }

    /**
     * General method to fetch data from the API and map it to the appropriate response class.
     */
    private <T> Optional<T> getDataFromApi(String url, Class<T> responseType) {
        try {
            String responseData = makeRequest(url);
            if (responseData.isEmpty() || responseData.equals("{}")) {
                return Optional.empty();
            }
            return Optional.ofNullable(mapper.readValue(responseData, responseType));
        } catch (IOException e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Executes an HTTP GET request and returns the response body as a String.
     */
    private String makeRequest(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null ) {
                return response.body().string();
            } else {
                throw new IOException("Failed to fetch data: " + response.code() + " " + response.message());
            }
        }
    }
}
