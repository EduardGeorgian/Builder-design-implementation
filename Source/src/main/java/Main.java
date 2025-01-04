import ProdiaAI.ProdiaClient;

import java.awt.image.BufferedImage;

public class Main {
    public static void main(String[] args) {
//        VehicleDirector director = new VehicleDirector();
//        Vehicle vehicle = director.buildVehicle();
//
//        System.out.println("Vehicle Built:");
//        System.out.println(vehicle.getDescription());

        System.out.println("Generating image via Prodia API...");
        String outputPath = "generated_vehicle_image.png";
        ProdiaClient ProdiaIntegration = new ProdiaClient();
        //BufferedImage vehicleImage = ProdiaClient.generateVehicleImage(vehicle.getDescription(), outputPath,vehicle.getVehicleID());
        BufferedImage vehicleImage = ProdiaIntegration.generateVehicleImage("Create a single image  of a vehicle, no " +
                        "variations, no collage, full-view that respects these " +
                "exact requirements: Type: Car, Cabrio, inspired from Mercedes, Chassis color black, with 4 doors",outputPath,
                "test-vehicle");


        if (vehicleImage != null) {
            System.out.println("Image generated and saved to: " + outputPath);

            // Afișare imagine
            ImageDisplayPanel.displayImageInWindow(vehicleImage, "Generated Vehicle");
        } else {
            System.out.println("Failed to generate vehicle image.");
        }
    }
}
