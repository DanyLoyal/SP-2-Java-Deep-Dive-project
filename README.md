# SP-2-Java-Deep-Dive-project
Dette er Gruppe 5 sp2 project 3sem

Software:
- IntelliJ v.2023.2
- Maven project
- Java jdk corretto - 19
- PlantUML v8059
- JUNIT v5.9.1
- Jsoup v1.16.1
- GSON v2.10.1
- okhttp v4.9.1
- postgres v42.6.0
- lombok v1.18.28
- hibernate v6.2.4


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

Description:

Stockmarket.

Create a DB, with the biggest changes on the Stockexchange, including the market value at the given date. Then collect an analysis rapport from morning-star.
The collective data is for potential investments, to give the buyer/seller the best opportunities.

Scrape Nordnet, for the 4 shares with the biggest change (2 positive, and 2 negatives).
Use Morning-star as a RestAPI to collect a stock analysis about the selected companies.
Store data (name, analysis, price, price change, stock type)  in a database, for later use.

Diagrams:

Domain model: 

![image](https://github.com/DanyLoyal/SP-2-Java-Deep-Dive-project/assets/113057317/093b15d2-3a7a-4b0e-8af1-e144d3bb455f)


EER diagram:

![EERSP2.png](Documentation%2FEERSP2.png)




Links:
Nordnet - https://www.nordnet.dk/dk

Morning-star - https://rapidapi.com/apidojo/api/morning-star
