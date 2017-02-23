# Article-Saver
A Java program to crawl and download web articles.

## About
### What does this do?
This program retrieves articles from a news source, parses them, and then saves them for offline reading.

### How does it work?
Article Saver uses [Jsoup](https://jsoup.org/) to parse the news source pages, [Mercury Reader's Web Parser](https://mercury.postlight.com/web-parser/) to retrieve the articles, and then Google's [gson](https://github.com/google/gson) library for parsing Mercury Reader's output. Article Saver is made with Maven.

## Setup
1. Get a [Mercury Web Parser API Key](https://mercury.postlight.com/web-parser/)
2. Create a file, `Api.java`, and save your key as a String with the `public` and `static` modifiers.

## Usage
To run the program, simply run `Main.java`. To change the query, change `Main.string`. 
