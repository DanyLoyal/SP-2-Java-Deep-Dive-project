# SP-2-Java-Deep-Dive-project
Dette er Gruppe 5 sp2 project 3sem


Requirements:
1.	Choose a website that provides some interesting data that you can scrape daily.
2.	Scrape the data from the website and using JSoup.
3.	Enrich the data by fetching additional information from a REST API.
4.	Store the data in a database using JPA.
5.	Write unit tests for scraping, enrichment, and API reading logic using JUnit.
6.	Use DTOs to represent the data.
7.	Use as many of the concepts you have learned as possible as functional programming, streams, lambdas, generics etc.
8.	Share the source code on GitHub.
9.	Write documentation for the project and add to your repo.

Idea: 
Stockmarket.

Scrape Nasdaq, ”www.nasdaq.com/market-activity/stocks” for the 4 shares with the biggest change (2 positive, and 2 negatives).
Use a RestAPI to collect information about the selected companies.
Store relevant data In a database, for later use.

Description:

Create a DB, with the biggest changes on the Stockexchange, including the market value at the given date. Then collect an analysis rapport from morning-star “https://rapidapi.com/apidojo/api/morning-star”.
The collective data is for potential investments, to give the buyer/seller the best opportunities.

