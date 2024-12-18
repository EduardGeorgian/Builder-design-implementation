import VehicleComponents.CustomComponent;
import VehicleComponents.Frame;
import VehicleComponents.Wheel;
import VehicleComponents.Engine;

public class Main {
    public static void main(String[] args) {
        Wheel frontWheel=new Wheel.Builder()
                .setSize(17)
                .setType("Sport")
                .build();

        Wheel wheelBack = new Wheel.Builder()
                .setSize(17)
                .setType("Sport")
                .build();

        Engine engine = new Engine.Builder()
                .setEngineType("Combustion engine")
                .setFuelType("Gasoline")
                .setHorsePower(150)
                .setTorque(400)
                .build();


        Frame frame = new Frame.Builder()
                .setFrameColor("Red")
                .setFrameType("Sedan")
                .setModelName("BMW")
                .setDoorNumber(4)
                .build();

        CustomComponent customComponent1=new CustomComponent("custom1","airplane wings and vampire fangs");

        Vehicle vehicle=new Vehicle.Builder()
                .setName("First Car")
                .setVehicleType("Car")
                .addComponent(frontWheel)
                .addComponent(wheelBack)
                .addComponent(engine)
                .addComponent(frame)
                .addComponent(customComponent1)
                .build();

        System.out.println(vehicle.getDescription());
    }
}
