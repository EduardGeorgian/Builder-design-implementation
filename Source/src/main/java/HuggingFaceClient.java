import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class HuggingFaceClient {

    private static final String API_URL = "https://api-inference.huggingface.co/models/stabilityai/stable-diffusion-2";
    private static final String API_KEY = "Bearer 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";  // Folosește cheia ta de API

    public static void generateImage(String description, String outputPath) throws Exception {
        String prompt = "{\"inputs\": \"" + description + "\"}";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(prompt))
                .build();

        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        // Verificăm dacă răspunsul conține un flux de date binare (imaginea)
        if (response.statusCode() == 200) {
            // Descărcăm și salvăm imaginea folosind datele binare
            downloadBinaryData(response.body(), outputPath);
        } else {
            System.out.println("Error from HuggingFace API. Status code: " + response.statusCode()+response.headers().toString());
        }
    }

    public static void downloadBinaryData(InputStream inputStream, String outputPath) throws IOException {
        // Descarcă și salvează imaginea binară în fișierul specificat
        try (InputStream in = inputStream;
             FileOutputStream out = new FileOutputStream(outputPath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            System.out.println("Image saved successfully to " + outputPath);
        }
    }
}
