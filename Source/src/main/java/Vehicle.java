import VehicleComponents.VehicleComponent;
import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private final String name;
    private final String vehicleType;
    private final List<VehicleComponent> components;

    private Vehicle(String name,String vehicleType, List<VehicleComponent> components) {
        this.name = name;
        this.components = components;
        this.vehicleType = vehicleType;
    }

    public String getDescription() {
        StringBuilder description = new StringBuilder("Create a full-view image of a "+ vehicleType+ " that has these features: \n");
        for (VehicleComponent component : components) {
            description.append(" - ").append(component.getDescription()).append("\n");
        }
        return description.toString();
    }

    String getVehicleID(){
        return this.name;
    }

    public static class Builder {
        private String name;
        private String vehicleType;
        private List<VehicleComponent> components = new ArrayList<>();

        public Builder setName(String name) {
            this.name = name;
            return this;
        }
        public Builder setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType;
            return this;
        }
        public Builder addComponent(VehicleComponent component) {
            components.add(component);
            return this;
        }
        public Vehicle build() {
            return new Vehicle(name, vehicleType, components);
        }
    }
}
