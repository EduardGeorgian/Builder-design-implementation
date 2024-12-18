package VehicleComponents;

public class Engine  implements VehicleComponent {
    private final String engineType;//electric, motor termic, ardere interna, externa, motoare pneumatice
    private final String fuelType;//diesel,gaz,benzina
    private final int horsePower;
    private final int torque;

    private Engine(String engineType, String fuelType, int horsePower, int torque) {
        this.engineType = engineType;
        this.fuelType = fuelType;
        this.horsePower = horsePower;
        this.torque = torque;
    }

    @Override
    public String getDescription(){
        return "Engine: "+engineType
                +" Fuel: "+fuelType
                +" Horsepower: "+horsePower
                +" Torque: "+torque;
    }

    public static class Builder {
        private String engineType;
        private String fuelType;
        private int horsePower;
        private int torque;
        public Builder setEngineType(String engineType) {
            this.engineType = engineType;
            return this;
        }
        public Builder setFuelType(String fuelType) {
            this.fuelType = fuelType;
            return this;
        }
        public Builder setHorsePower(int horsePower) {
            this.horsePower = horsePower;
            return this;
        }
        public Builder setTorque(int torque) {
            this.torque = torque;
            return this;
        }
        public Engine build() {
            return new Engine(engineType, fuelType, horsePower, torque);
        }
    }
}
