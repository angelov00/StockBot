# StockBot
A Discord bot for stock market tracking and trading insights.

## Commands
- `!lookup` - Search for best-matching symbols based on your query. You can input anything from symbol, security's name to ISIN and Cusip. 
  *Example usage:* `!lookup aapl`
  ![lookup](images/lookup.png)
- `!market-status` - Get current market status for global exchanges (whether exchanges are open or close). 
  *Example usage*: `!market-status US`
  ![market-status](images/market-status.png)
- `!quote` - Displays the current stock price for a given symbol. Use !quote \<stock symbol>, for example: !price AAPL. 
  *Example usage:* `!quote NVDA`
  ![quote](images/quote.png)
- `!ping` - Connection testing command. The bot should answer with "Pong!" 
  *Example usage:* `!ping`
  ![ping](images/ping.png)
- `!company` - Get general information of a company. You can query by ticker symbol, ISIN or CUSIP. 
  *Example usage:* `!company IBM`
  ![company](images/company.png)
- `!watchlist-add` - Adds stock to the server's watchlist. The bot notifies about the price every hour. 
  *Example usage:* `watchlist-add AAPL`
  ![watchlist-add](images/watchlist-add.png)
  ![watchlist](images/watchlist.png)
- `!watchlist-remove` - Removes a stock from the server's watchlist.
  *Example usage:* `watchlist-remove AAPL`
  ![watchlist](images/watchlist-remove.png)

