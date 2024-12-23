package VehicleComponents;

public class Wheel implements VehicleComponent {
    private final int size;
    private final String type;

    //constructor privat pentru a evita instantierea directa
    private Wheel(int size, String type) {
        this.size = size;
        this.type = type;
    }
    @Override
    public String getDescription() {
        return "Wheels: ( size: " + size + " model: "+type+")";
    }
    public static class Builder {
        private int size;
        private String type;

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }
        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Wheel build() {
            return new Wheel(size, type);
        }
    }

}
