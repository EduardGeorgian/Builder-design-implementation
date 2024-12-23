package ProdiaAI;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.UUID;

public class ProdiaClient {

    private static final String API_URL = "https://api.prodia.com/v1/sd/generate";
    private static final String API_KEY = "39dc32a2-6991-41b4-95d2-a95563f8ef38";
   static Gson gson = new Gson();

    public static BufferedImage generateVehicleImage(String vehicleDescription, String outputPath, String vehicleID) {
        try {
            GenerateCallRequest callRequest=new GenerateCallRequest(vehicleDescription);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.prodia.com/v1/sd/generate"))
                    .header("accept", "application/json")
                    .header("content-type", "application/json")
                    .header("X-Prodia-Key", "39dc32a2-6991-41b4-95d2-a95563f8ef38")
                    .method("POST", HttpRequest.BodyPublishers.ofString(gson.toJson(callRequest)))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());



            GenerateCallResponse callResponse = gson.fromJson(response.body(), GenerateCallResponse.class);


            if (response.statusCode() != 200) {
                throw new RuntimeException("Failed to generate image: " + response.body());
            }

            HttpResponse<String> getImageResponse;
            while (true) {
                HttpRequest getImageRequest = HttpRequest.newBuilder()
                        .uri(URI.create("https://api.prodia.com/v1/job/" + callResponse.job))
                        .header("accept", "application/json")
                        .header("X-Prodia-Key", "39dc32a2-6991-41b4-95d2-a95563f8ef38")
                        .method("GET", HttpRequest.BodyPublishers.noBody())
                        .build();
                getImageResponse = HttpClient.newHttpClient().send(getImageRequest, HttpResponse.BodyHandlers.ofString());
                System.out.println(getImageResponse.body());

                GenerateImageResponse imageResponse = gson.fromJson(getImageResponse.body(), GenerateImageResponse.class);
                System.out.println(imageResponse.toString());

                if (imageResponse.status.equals("succeeded"))
                    break;

                Thread.sleep(1000);
            }
            // Extrage URL-ul imaginii din raspuns
            GenerateImageResponse imageResponse = gson.fromJson(getImageResponse.body(), GenerateImageResponse.class);
            String imageUrl = imageResponse.imageUrl;
            if (imageUrl == null) {
                throw new RuntimeException("Image URL not found in response.");
            }

            // Descarcare imagine din URL
            return downloadImage(imageResponse.imageUrl, "D:\\PIP-2024-2025\\Builder-design-implementation\\Resources\\generated-images", vehicleID);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private static BufferedImage downloadImage(String imageUrl, String outputPath,String imageId) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URI(imageUrl).toURL().openConnection();
        connection.setRequestMethod("GET");

        try (InputStream inputStream = connection.getInputStream()) {
            // Salveaza imaginea în fisier
            UUID randomId = UUID.randomUUID();

            File outputFile = new File(outputPath + "\\" + imageId + ".jpg");
            try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
            }

            // incarca imaginea in memorie
            return ImageIO.read(outputFile);
        }
    }
}
