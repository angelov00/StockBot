package com.stockbot.formatting;

import com.stockbot.model.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MessageFormatter {

    public static MessageEmbed createStockQuoteEmbed(StockQuoteResponse response) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Stock Quote for AAPL");
        embed.setColor(Color.GREEN);

        embed.addField("Current Price", "$" + response.getCurrentPrice(), true);
        embed.addField("Change", String.format("%.2f", response.getChange()), true);
        embed.addField("Percentage Change", String.format("%.2f%%", response.getPercentChange()), true);
        embed.addField("Day High", "$" + response.getDayHigh(), true);
        embed.addField("Day Low", "$" + response.getDayLow(), true);
        embed.addField("Open Price", "$" + response.getOpenPrice(), true);
        embed.addField("Previous Close", "$" + response.getPreviousClosePrice(), true);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = response.getTime().format(formatter);
        embed.addField("Time", formattedTime, false);

        return embed.build();
    }

    public static MessageEmbed createMarketStatusEmbed(MarketStatusResponse response) {

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Market Status: ");
        embed.setColor(Color.GREEN);

        embed.addField("Exchange", response.getExchange(), true);
        embed.addField("Holiday", response.getHoliday() != null ? response.getHoliday() : "N/A", true);
        embed.addField("Market Open", response.getIsOpen() != null && response.getIsOpen() ? "Yes" : "No", true);
        embed.addField("Session", response.getSession() != null ? response.getSession() : "N/A", true);
        embed.addField("Timezone", response.getTimezone(), true);

        return embed.build();

    }

    public static MessageEmbed createStockSymbolResponseEmbed(StockSymbolResponse response) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Stock Symbol Search Results");
        embed.setColor(Color.GREEN);

        embed.addField("Total Results", String.valueOf(response.getCount()), false);

        List<StockSymbol> symbols = response.getResult();

        if (symbols != null && !symbols.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            int limit = Math.min(symbols.size(), 5);
            for (int i = 0; i < limit; i++) {
                StockSymbol symbol = symbols.get(i);
                sb.append("**Symbol**: ").append(symbol.getSymbol()).append("\n")
                        .append("**Description**: ").append(symbol.getDescription()).append("\n")
                        .append("**Display Symbol**: ").append(symbol.getDisplaySymbol()).append("\n")
                        .append("**Type**: ").append(symbol.getType()).append("\n\n");
            }
            embed.addField("Top Symbols", sb.toString(), false);
        } else {
            embed.addField("Symbols", "No symbols found matching the query.", false);
        }

        return embed.build();
    }

    public static MessageEmbed createStockSymbolEmbed(StockSymbol symbol) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Stock Symbol Info");
        embed.setColor(0x00FF00); // You can change this color

        embed.addField("Symbol", symbol.getSymbol(), true);
        embed.addField("Description", symbol.getDescription(), true);
        embed.addField("Display Symbol", symbol.getDisplaySymbol(), true);
        embed.addField("Type", symbol.getType(), true);

        return embed.build();
    }

    public static MessageEmbed createCompanyProfileEmbed(CompanyProfile profile) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(profile.getName() + " (" + profile.getTicker() + ")");
        if (profile.getLogo() != null && !profile.getLogo().isEmpty()) {
            embed.setThumbnail(profile.getLogo());
        }
        embed.setColor(Color.GREEN);
        embed.addField("Country", profile.getCountry() != null ? profile.getCountry() : "N/A", true);
        embed.addField("Currency", profile.getCurrency() != null ? profile.getCurrency() : "N/A", true);
        embed.addField("Exchange", profile.getExchange() != null ? profile.getExchange() : "N/A", true);
        embed.addField("IPO", profile.getIpo() != null ? profile.getIpo() : "N/A", true);
        embed.addField("Market Cap", profile.getMarketCapitalization() != null ? profile.getMarketCapitalization().toString() : "N/A", true);
        embed.addField("Outstanding Shares", profile.getShareOutstanding() != null ? profile.getShareOutstanding().toString() : "N/A", true);
        embed.addField("Phone", profile.getPhone() != null ? profile.getPhone() : "N/A", false);
        embed.addField("Website", profile.getWebURL() != null ? profile.getWebURL() : "N/A", false);
        embed.addField("Industry", profile.getCategory() != null ? profile.getCategory() : "N/A", false);
        return embed.build();
    }
}
