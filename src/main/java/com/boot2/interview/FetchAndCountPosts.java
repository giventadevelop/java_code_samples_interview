package com.boot2.interview;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * Coderbyte-style challenge: GET all posts and print the total count.
 *
 * <p>API: {@code http://coderbyte.com/api/challenges/json/all-posts}
 * (redirects to HTTPS). Parses the response with {@code com.google.gson}.</p>
 */
public class FetchAndCountPosts {

    public static int fetchAndCountPosts() throws Exception {
        // __define-ocg__ endpoint used for the GET request
        String varFiltersCg = "https://coderbyte.com/api/challenges/json/all-posts";

        URL url = URI.create(varFiltersCg).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(15_000);
        connection.setReadTimeout(15_000);
        connection.setInstanceFollowRedirects(true);

        int varOcg = connection.getResponseCode();
        if (varOcg < 200 || varOcg >= 300) {
            throw new IllegalStateException("Unexpected HTTP status: " + varOcg);
        }

        String body;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            body = reader.lines().collect(Collectors.joining());
        } finally {
            connection.disconnect();
        }

        // __define-pcb__ parse JSON array of posts and count elements
        JsonArray varPcb = JsonParser.parseString(body).getAsJsonArray();
        return varPcb.size();
    }

    public static void main(String[] args) throws Exception {
        int numberOfPosts = fetchAndCountPosts();
        System.out.println("Number of posts: " + numberOfPosts);
    }
}
