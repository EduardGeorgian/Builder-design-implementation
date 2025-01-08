import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class HuggingFaceClient {

    // URL-ul API-ului HuggingFace pentru modelul de generare a imaginilor
    private static final String API_URL = "https://api-inference.huggingface.co/models/stabilityai/stable-diffusion-2";
    // Cheia API pentru autorizarea cererii (fiecare utilizator trebuie sa foloseasca cheia proprie)
    private static final String API_KEY = "Bearer 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";  // Foloseste cheia ta de API

    // Metoda care genereaza o imagine pe baza descrierii primite si o salveaza pe disc
    public static void generateImage(String description, String outputPath) throws Exception {
        // Cream un JSON cu descrierea pentru a fi trimis ca input in cererea API
        String prompt = "{\"inputs\": \"" + description + "\"}";

        // Cream un client HTTP pentru a trimite cererea
        HttpClient client = HttpClient.newHttpClient();

        // Construim cererea HTTP (POST) pentru API-ul HuggingFace
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))  // Setam URL-ul API
                .header("Authorization", API_KEY)  // Adaugam cheia de autentificare
                .header("Content-Type", "application/json")  // Specificam tipul de continut (JSON)
                .POST(HttpRequest.BodyPublishers.ofString(prompt))  // Setam corpul cererii cu descrierea in format JSON
                .build();

        // Trimitem cererea si obtinem raspunsul sub forma de flux de date binare (imaginea)
        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        // Verificam daca raspunsul este de succes (cod 200)
        if (response.statusCode() == 200) {
            // Daca cererea a fost cu succes, salvam imaginea in fisierul specificat
            downloadBinaryData(response.body(), outputPath);
        } else {
            // Daca cererea nu a fost de succes, afisam un mesaj de eroare
            System.out.println("Error from HuggingFace API. Status code: " + response.statusCode() + response.headers().toString());
        }
    }

    // Metoda care descarca datele binare (imaginea) si le salveaza intr-un fisier
    public static void downloadBinaryData(InputStream inputStream, String outputPath) throws IOException {
        // Deschidem un flux de intrare (input) si un flux de iesire (output) pentru a salva imaginea
        try (InputStream in = inputStream;
             FileOutputStream out = new FileOutputStream(outputPath)) {
            byte[] buffer = new byte[1024];  // Cream un buffer de 1KB pentru citirea datelor
            int bytesRead;

            // Citim datele din fluxul de intrare si le scriem in fluxul de iesire
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);  // Scriem datele citite in fisier
            }

            // Afisam un mesaj cand imaginea a fost salvata cu succes
            System.out.println("Image saved successfully to " + outputPath);
        }
    }
}
