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

    private static final String API_BASE_URL = "https://finnhub.io/api/v1";
    private static final String QUOTE_ENDPOINT = "/quote";
    private static final String SEARCH_ENDPOINT = "/search";
    private static final String MARKET_STATUS_ENDPOINT = "/stock/market-status";
    private static final String COMPANY_PROFILE_ENDPOINT = "/stock/profile2";

    private final String apiKey;
    private final OkHttpClient client;
    private final ObjectMapper mapper;
    private final Logger logger;
    private final CacheService cacheService;

    public StockDataService(String apiKey, CacheService cacheService) {
        this.apiKey = apiKey;
        this.cacheService = cacheService;
        this.client = new OkHttpClient();
        this.mapper = new ObjectMapper();
        this.logger = LoggerFactory.getLogger(StockDataService.class);
    }

    /**
     * Fetches real-time stock price for a given ticker symbol.
     *
     * @param symbol the ticker symbol
     * @return an Optional containing the StockQuoteResponse if available, otherwise empty
     */
    public Optional<StockQuoteResponse> getStockByTickerSymbol(String symbol) {
        StockQuoteResponse cachedResponse = cacheService.getCachedStock(symbol);
        if (cachedResponse != null) {
            logger.info("Cached response!");
            return Optional.of(cachedResponse);
        }

        String url = buildUrl(QUOTE_ENDPOINT, "symbol", symbol);
        Optional<StockQuoteResponse> result = getDataFromApi(url, StockQuoteResponse.class);
        result.ifPresent(response -> {
            response.setTickerSymbol(symbol);
            this.cacheService.cacheStock(symbol, response);
        });
        return result;
    }

    /**
     * Returns stock symbols matching a query (e.g., company name, ISIN, CUSIP).
     *
     * @param query the search query
     * @return an Optional containing the StockSymbolResponse if available, otherwise empty
     */
    public Optional<StockSymbolResponse> getSymbolLookup(String query) {
        String url = buildUrl(SEARCH_ENDPOINT, "q", query);
        return getDataFromApi(url, StockSymbolResponse.class);
    }

    /**
     * Fetches the market status for a given exchange.
     *
     * @param exchangeCode the exchange code
     * @return an Optional containing the MarketStatusResponse if available, otherwise empty
     */
    public Optional<MarketStatusResponse> getMarketStatus(String exchangeCode) {
        String url = buildUrl(MARKET_STATUS_ENDPOINT, "exchange", exchangeCode);
        return getDataFromApi(url, MarketStatusResponse.class);
    }

    /**
     * Fetches the company profile for a given stock symbol.
     *
     * @param symbol the ticker symbol
     * @return an Optional containing the CompanyProfile if available, otherwise empty
     */
    public Optional<CompanyProfile> getCompanyProfile(String symbol) {
        String url = buildUrl(COMPANY_PROFILE_ENDPOINT, "symbol", symbol);
        return getDataFromApi(url, CompanyProfile.class);
    }

    /**
     * General method to fetch data from the API and map it to the appropriate response class.
     *
     * @param url          the API URL
     * @param responseType the class to map the response to
     * @param <T>          the type of the response
     * @return an Optional containing the mapped response if successful, otherwise empty
     */
    private <T> Optional<T> getDataFromApi(String url, Class<T> responseType) {
        try {
            String responseData = makeRequest(url);
            if (responseData.isEmpty() || responseData.equals("{}")) {
                return Optional.empty();
            }
            T result = mapper.readValue(responseData, responseType);
            return Optional.ofNullable(result);
        } catch (IOException e) {
            logger.error("IOException while calling API at {}: {}", url, e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error processing API response from {}: {}", url, e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Executes an HTTP GET request and returns the response body as a String.
     *
     * @param url the URL to request
     * @return the response body as a String
     * @throws IOException if an I/O error occurs during the request
     */
    private String makeRequest(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                throw new IOException("Failed to fetch data: " + response.code() + " " + response.message());
            }
        }
    }

    /**
     * Constructs the full API URL with the given endpoint, query parameter name, and value.
     *
     * @param endpoint the API endpoint
     * @param paramKey the query parameter key
     * @param paramVal the query parameter value
     * @return the full URL string
     */
    private String buildUrl(String endpoint, String paramKey, String paramVal) {
        return String.format("%s%s?%s=%s&token=%s", API_BASE_URL, endpoint, paramKey, paramVal, apiKey);
    }
}
