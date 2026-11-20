import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class TriviaCheckIn {
    public static void main(String[] args) {
        String categoryId = "21"; // Sports
        try {
            requestTrivia(categoryId);
        } catch (IOException e) {
            System.err.println("Could not reach Open Trivia Database: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("The trivia request was interrupted.");
        }
    }

    private static void requestTrivia(String categoryId)
            throws IOException, InterruptedException {
        String url = "https://opentdb.com/api.php?amount=5&category="
                + categoryId + "&type=multiple";
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(15))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            System.out.println(response.body());
        } else {
            System.err.println("Open Trivia Database returned HTTP " + response.statusCode());
        }
    }
}
