package fr.nkri.shell.cli;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * HTTP requests with token and header support
 */
public class APICLI {

    private static final HttpClient client = HttpClient.newHttpClient();

    /**
     * Asynchronous GET with token and additional headers
     *
     * @param url Target URL
     * @param token Token for Authorization (can be null)
     * @param headers Other custom headers
     * @return CompleteableFuture of the body

     */
    public static CompletableFuture<String> getAsync(final String url, final String token, final Map<String, String> headers) {
        final HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET();

        /*
        Add token if not null
         */
        if(token != null && !token.isEmpty()){
            builder.header("Authorization", "Bearer " + token);
        }

        /*
        Add more header
         */
        if(headers != null){
            headers.forEach(builder::header);
        }

        return client.sendAsync(builder.build(), HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .exceptionally(e -> {
                    MiniCLI.error("GET request error: " + e.getMessage());
                    return null;
                });
    }

    /**
     * Asynchronous POST with token, headers, and JSON body
     *
     * @param url URL
     * @param jsonBody JSON content
     * @param token token for Authorization (can be null)
     * @param headers other custom headers
     * @return CompleteableFuture of the body
     */
    public static CompletableFuture<String> postAsync(final String url, final String jsonBody, final String token, final Map<String, String> headers) {
        final HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url)).POST(HttpRequest.BodyPublishers.ofString(jsonBody)).header("Content-Type", "application/json");

        /*
        Add token if not null
         */
        if(token != null && !token.isEmpty()){
            builder.header("Authorization", "Bearer " + token);
        }

        /*
        Add more header
         */
        if(headers != null){
            headers.forEach(builder::header);
        }

        return client.sendAsync(builder.build(), HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .exceptionally(e -> {
                    MiniCLI.error("POST request error: " + e.getMessage());
                    return null;
                });
    }

    /*
    Synchronous versions for GET and POST
     */
    public static String getSync(final String url, final String token, final Map<String, String> headers) {
        try {
            return getAsync(url, token, headers).get();
        }
        catch (final InterruptedException | ExecutionException e) {
            MiniCLI.error("GET request error: " + e.getMessage());
            return null;
        }
    }

    public static String postSync(final String url, final String jsonBody, final String token, final Map<String, String> headers) {
        try {
            return postAsync(url, jsonBody, token, headers).get();
        }
        catch (final InterruptedException | ExecutionException e) {
            MiniCLI.error("POST request error: " + e.getMessage());
            return null;
        }
    }
}