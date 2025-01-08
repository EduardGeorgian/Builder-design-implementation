import VehicleComponents.*;
import java.util.Scanner;

// Clasa VehicleDirector este responsabila pentru construirea unui vehicul
// in mod interactiv, cerand utilizatorului informatii despre vehicul si
// componentele sale.
public class VehicleDirector {

    // Metoda care construieste un vehicul pe baza informatiilor introduse de utilizator
    public Vehicle buildVehicle() {
        Scanner scanner = new Scanner(System.in);  // Crearea unui obiect scanner pentru a citi input-ul de la utilizator
        Vehicle.Builder vehicleBuilder = new Vehicle.Builder();  // Initializam builder-ul pentru a construi vehiculul

        // Colectam informatii despre vehicul
        System.out.println("Enter vehicle name: ");
        String vehicleName = scanner.nextLine();
        vehicleBuilder.setName(vehicleName);  // Setam numele vehiculului

        System.out.println("Enter vehicle type:(e.g., Car, Bike, Motorcycle): ");
        String vehicleType = scanner.nextLine();
        vehicleBuilder.setVehicleType(vehicleType);  // Setam tipul vehiculului

        // Construim cadrul vehiculului (Frame)
        System.out.println("------Building Frame------\n");
        System.out.println("Frame type:");
        String frameType = scanner.nextLine();
        System.out.println("Frame name:");
        String modelName = scanner.nextLine();
        System.out.println("Frame color:");
        String frameColor = scanner.nextLine();
        System.out.println("Door number:");
        String doorNumber = scanner.nextLine();
        int doorNum = Integer.parseInt(doorNumber);  // Convertim numarul de usi intr-un numar intreg

        // Cream obiectul Frame si il adaugam la vehicul
        Frame frame = new Frame.Builder()
                .setFrameType(frameType)
                .setModelName(modelName)
                .setFrameColor(frameColor)
                .setDoorNumber(doorNum)
                .build();
        vehicleBuilder.addComponent(frame);  // Adaugam componenta Frame la vehicul

        // Construim motorul vehiculului (Engine)
        System.out.println("------Building Engine------\n");
        System.out.println("Engine type: ");
        String engineType = scanner.nextLine();
        System.out.println("Fuel type:");
        String fuelType = scanner.nextLine();
        System.out.println("Horse Power:");
        String horsePower = scanner.nextLine();
        int horsePowerInt = Integer.parseInt(horsePower);  // Convertim puterea motorului intr-un numar intreg
        System.out.println("Torque:");
        String torque = scanner.nextLine();
        int torqueInt = Integer.parseInt(torque);  // Convertim cuplul motorului intr-un numar intreg

        // Cream obiectul Engine si il adaugam la vehicul
        Engine engine = new Engine.Builder()
                .setEngineType(engineType)
                .setFuelType(fuelType)
                .setHorsePower(horsePowerInt)
                .setTorque(torqueInt)
                .build();
        vehicleBuilder.addComponent(engine);  // Adaugam componenta Engine la vehicul

        // Colectam informatii despre roti (Wheel)
        int wheelNumber;
        System.out.println("Wheel number:");
        wheelNumber = scanner.nextInt();  // Citim numarul de roti
        for (int i = 0; i < wheelNumber; i++) {
            System.out.println("Building wheel " + (i + 1) + ":");

            System.out.println("Wheel size:");
            int wheelSize = scanner.nextInt();  // Citim dimensiunea rotii
            scanner.nextLine();  // Consuma newline-ul ramas in buffer

            System.out.println("Wheel type (e.g., Color, Architecture, Capacity, Tires):");
            String wheelType = scanner.nextLine();  // Citim tipul rotii

            // Cream obiectul Wheel si il adaugam la vehicul
            Wheel wheel = new Wheel.Builder()
                    .setSize(wheelSize)
                    .setType(wheelType)
                    .build();
            vehicleBuilder.addComponent(wheel);  // Adaugam componenta Wheel la vehicul
        }

        // Adaugam componente personalizate
        System.out.println("------Custom Components-----");
        while(true) {
            System.out.println("Any custom components or features? (yes/no):");
            String answer = scanner.nextLine();
            if(answer.equals("yes")) {
                break;  // Iesim din bucla daca utilizatorul nu mai doreste sa adauge componente personalizate
            }
            System.out.println("Custom component name:");
            String componentName = scanner.nextLine();
            System.out.println("Custom component description:");
            String componentDescription = scanner.nextLine();

            // Cream si adaugam componenta personalizata
            CustomComponent customComponent = new CustomComponent(componentName, componentDescription);
            vehicleBuilder.addComponent(customComponent);  // Adaugam componenta personalizata la vehicul
        }

        // Returnam vehiculul construit
        return vehicleBuilder.build();
    }
}
