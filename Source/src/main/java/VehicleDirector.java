import VehicleComponents.*;
import java.util.Scanner;


public class VehicleDirector {

    public Vehicle buildVehicle(){
        Scanner scanner = new Scanner(System.in);
        Vehicle.Builder vehicleBuilder = new Vehicle.Builder();

        System.out.println("Enter vehicle name: ");
        String vehicleName = scanner.nextLine();
        vehicleBuilder.setName(vehicleName);

        System.out.println("Enter vehicle type:(e.g., Car, Bike, Motorcycle): ");
        String vehicleType = scanner.nextLine();
        vehicleBuilder.setVehicleType(vehicleType);

        System.out.println("------Building Frame------\n");
        System.out.println("Frame type:");
        String frameType = scanner.nextLine();
        System.out.println("Frame name:");
        String  modelName = scanner.nextLine();
        System.out.println("Frame color:");
        String frameColor = scanner.nextLine();
        System.out.println("Door number:");
        String doorNumber = scanner.nextLine();
        int doorNum = Integer.parseInt(doorNumber);

        Frame frame=new Frame.Builder()
                .setFrameType(frameType)
                .setModelName(modelName)
                .setFrameColor(frameColor)
                .setDoorNumber(doorNum)
                .build();

        vehicleBuilder.addComponent(frame);

        System.out.println("------Building Engine------\n");
        System.out.println("Engine type: ");
        String engineType = scanner.nextLine();
        System.out.println("Fuel type:");
        String fuelType = scanner.nextLine();
        System.out.println("Horse Power:");
        String horsePower = scanner.nextLine();
        int horsePowerInt = Integer.parseInt(horsePower);
        System.out.println("Torque:");
        String torque = scanner.nextLine();
        int torqueInt = Integer.parseInt(torque);
        Engine engine=new Engine.Builder()
                .setEngineType(engineType)
                .setFuelType(fuelType)
                .setHorsePower(horsePowerInt)
                .setTorque(torqueInt)
                .build();
        vehicleBuilder.addComponent(engine);


        int wheelNumber;
        System.out.println("Wheel number:");
        wheelNumber = scanner.nextInt();
        for (int i = 0; i < wheelNumber; i++) {
            System.out.println("Building wheel " + (i + 1) + ":");

            System.out.println("Wheel size:");
            int wheelSize = scanner.nextInt();
            scanner.nextLine(); // Consuma newline-ul ramas în buffer

            System.out.println("Wheel type (e.g., Color, Architecture, Capacity, Tires):");
            String wheelType = scanner.nextLine();

            Wheel wheel = new Wheel.Builder()
                    .setSize(wheelSize)
                    .setType(wheelType)
                    .build();
            vehicleBuilder.addComponent(wheel);
        }


        System.out.println("------Custom Components-----");
        while(true){
            System.out.println("Any custom components or features? (yes/no):");
            String answer = scanner.nextLine();
            if(answer.equals("yes")){
                break;
            }
            System.out.println("Custom component name:");
            String componentName = scanner.nextLine();
            System.out.println("Custom component description:");
            String componentDescription = scanner.nextLine();

            CustomComponent customComponent=new CustomComponent(componentName,componentDescription);
            vehicleBuilder.addComponent(customComponent);

        }
        return vehicleBuilder.build();
    }
}
