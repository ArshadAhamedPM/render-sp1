package com.quran.api.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TopicScraper {

    // A class to hold topic information
    static class Topic {
        private String title;
        private String url;

        public Topic(String title, String url) {
            this.title = title;
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        @Override
        public String toString() {
            return "Topic{" +
                    "title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        String baseUrl = "https://www.alim.org";
        String landingPageUrl = "https://www.alim.org/duas/masnoon-duas/landing/";

        List<Topic> topicsList = new ArrayList<>();

        try {
            // Fetch the landing page
            Document doc = Jsoup.connect(landingPageUrl).get();

            // Select the list of topics based on CSS classes or tags
            Elements topicElements = doc.select(".container max-w-screen-xl mx-auto min-h-screen pb-14"); // Adjust the selector as per the page structure

            // Iterate through each topic element
            for (Element element : topicElements) {
                String topicTitle = element.text(); // Get the topic title
                String topicUrl = baseUrl + element.attr("href"); // Get the relative URL and append the base URL

                topicsList.add(new Topic(topicTitle, topicUrl));
            }

            // Print the list of topics
            for (Topic topic : topicsList) {
                System.out.println(topic);
            }

        } catch (IOException e) {
            System.err.println("Error fetching the topics from the landing page.");
            e.printStackTrace();
        }
    }
}

