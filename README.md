# StockBot
A Discord bot for stock market tracking and trading insights.

## Commands

### `!lookup`
🔍 **Search for stock symbols**  
Find the best-matching stock symbols based on your query. You can input anything from a stock symbol, security name, ISIN, or CUSIP.  

**Example usage:**  
```plaintext
!lookup aapl
```
![Lookup command](images/lookup.png)

---

### `!market-status`
📊 **Check global market status**  
Get the current market status for global exchanges, indicating whether they are open or closed.  

**Example usage:**  
```plaintext
!market-status US
```
![Market status command](images/market-status.png)

---

### `!quote`
💰 **Get current stock price**  
Displays the latest stock price for a given symbol. Use `!quote <stock symbol>`.  

**Example usage:**  
```plaintext
!quote NVDA
```
![Quote command](images/quote.png)

---

### `!ping`
📡 **Test bot connection**  
A simple command to check if the bot is online. It should respond with "Pong!".  

**Example usage:**  
```plaintext
!ping
```
![Ping command](images/ping.png)

---

### `!company`
🏢 **Get company information**  
Fetch general information about a company using a ticker symbol, ISIN, or CUSIP.  

**Example usage:**  
```plaintext
!company IBM
```
![Company command](images/company.png)

---

### `!watchlist-add`
📌 **Add stock to watchlist**  
Adds a stock to the server's watchlist. The bot will notify you about its price every hour.  

**Example usage:**  
```plaintext
!watchlist-add AAPL
```
![Watchlist add command](images/watchlist-add.png)

![Watchlist notification](images/watchlist.png)

---

### `!watchlist-remove`
❌ **Remove stock from watchlist**  
Removes a stock from the server's watchlist.  

**Example usage:**  
```plaintext
!watchlist-remove AAPL
```
![Watchlist remove command](images/watchlist-remove.png)
