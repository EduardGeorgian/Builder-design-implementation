package VehicleComponents;

public class Frame implements VehicleComponent {

    private final String frameType;
    private final String modelName;
    private final String frameColor;
    private final int doorNumber;

    //constructor privat pentru a preveni instantierea directa, o sa o facem doar din builder
    private Frame(String frameType, String modelName, String frameColor, int doorNumber) {
        this.frameType = frameType;
        this.modelName = modelName;
        this.frameColor = frameColor;
        this.doorNumber = doorNumber;
    }

    //metoda prin care returnam descrierea componentei
    @Override
    public String getDescription() {
        return
                " Chassis: " +
                frameType +
                " ,Inspired from: "+
                modelName+
                " ,Color: "+
                frameColor+
                " ,Door number:"+
                doorNumber;
    }

    public static class Builder {
        private String frameType;
        private String modelName;
        private String frameColor;
        private int doorNumber;

        public Builder setFrameType(String frameType) {
            this.frameType = frameType;
            return this;
        }
        public Builder setModelName(String modelName) {
            this.modelName = modelName;
            return this;
        }
        public Builder setFrameColor(String frameColor) {
            this.frameColor = frameColor;
            return this;
        }
        public Builder setDoorNumber(int doorNumber) {
            this.doorNumber = doorNumber;
            return this;
        }
        //metoda finala build() pentru a construi un obiect Frame
        public Frame build() {
            return new Frame(frameType, modelName, frameColor, doorNumber);
        }
    }
}
