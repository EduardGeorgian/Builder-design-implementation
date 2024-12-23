package VehicleComponents;

import java.awt.*;

public class CustomComponent implements VehicleComponent {
    private final String componentName;
    private final String componentDescription;

    public CustomComponent(String componentName, String componentDescription) {
        this.componentName = componentName;
        this.componentDescription = componentDescription;
    }
    @Override
    public String getDescription() {
        return componentDescription;
    }
}
