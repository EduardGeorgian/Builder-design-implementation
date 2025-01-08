import VehicleComponents.VehicleComponent;
import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private final String name; // Numele vehiculului
    private final String vehicleType; // Tipul vehiculului (ex. masina, camion, motocicleta)
    private final List<VehicleComponent> components; // Lista componentelor vehiculului (ex. motor, caroserie)

    // Constructor privat pentru a impune utilizarea Builder pentru crearea obiectului Vehicle
    private Vehicle(String name, String vehicleType, List<VehicleComponent> components) {
        this.name = name;
        this.components = components;
        this.vehicleType = vehicleType;
    }

    // Metoda care returneaza o descriere a vehiculului, inclusiv toate componentele sale
    public String getDescription() {
        StringBuilder description = new StringBuilder(vehicleType + " with these features: \n");
        // Parcurgem fiecare componenta a vehiculului si adaugam descrierea acesteia
        for (VehicleComponent component : components) {
            description.append(" , ").append(component.getDescription()).append("\n");
        }
        return description.toString();
    }

    // Metoda care returneaza ID-ul vehiculului (numele vehiculului)
    String getVehicleID() {
        return this.name;
    }

    // Builder pentru crearea unui obiect Vehicle
    public static class Builder {
        private String name;  // Numele vehiculului
        private String vehicleType;  // Tipul vehiculului
        private List<VehicleComponent> components = new ArrayList<>();  // Lista componentelor vehiculului

        // Metoda pentru setarea numelui vehiculului
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        // Metoda pentru setarea tipului vehiculului
        public Builder setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType;
            return this;
        }

        // Metoda pentru adaugarea unei componente la lista vehiculului
        public Builder addComponent(VehicleComponent component) {
            components.add(component);
            return this;
        }

        // Metoda pentru construirea obiectului Vehicle
        public Vehicle build() {
            return new Vehicle(name, vehicleType, components);
        }
    }
}
