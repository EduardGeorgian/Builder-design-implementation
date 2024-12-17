package VehicleComponents;

public class Frame implements VehicleComponent {

    private final String frameType;
    private final String modelName;
    private final String frameColor;
    private final String doorNumber;


    Frame(String frameType, String modelName, String frameColor, String doorNumber) {
        this.frameType = frameType;
        this.modelName = modelName;
        this.frameColor = frameColor;
        this.doorNumber = doorNumber;
        System.out.println("Frame created");
    }

    @Override
    public String getDescription() {
        return
                "Frame type:" +
                frameType +
                " Model name:"+
                modelName+
                " Frame color:"+
                frameColor+
                " Door number:"+
                doorNumber;
    }
}
