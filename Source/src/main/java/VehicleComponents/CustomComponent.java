package VehicleComponents;

import java.awt.*;
//un custom component pentru a oferi utilizatorului optiuni mai variate
//api ul nu prea stie sa faca diferente, e cam slabut, insa cu un alt api
//mai bun majoritatea cerintelor care nu sunt standard ar trebui sa fie
//luate in considerare

//Clasa CustomComponent care implementeaza interfata vehicleComponent
public class CustomComponent implements VehicleComponent {
    private final String componentName;//un nume componentei, desi nu l am folosit prea mult
    private final String componentDescription;//si descrierea

    public CustomComponent(String componentName, String componentDescription) {//constructor
        this.componentName = componentName;
        this.componentDescription = componentDescription;
    }
    @Override
    public String getDescription() {
        return " and add:"+componentDescription;//si implementarea metodei getDescription, am adaugat "and add:" pentru
        //promptul api ului
    }
}
