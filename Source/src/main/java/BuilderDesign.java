import ProdiaAI.ProdiaClient;
import VehicleComponents.CustomComponent;
import VehicleComponents.Engine;
import VehicleComponents.Frame;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class BuilderDesign extends JFrame {

    private JPanel MainPanel;
    private JPanel ConfigPanel;
    private JButton createVehicleButton;
    private JLabel titleLabel;
    private JButton backToMainPanelButton;
    private JPanel vehicleTypesPanels;
    Vehicle.Builder builder=new Vehicle.Builder();


    // Variabile pentru componentele din ConfigPanel
    private JTextField vehicleNameField;
    private JComboBox<String> vehicleTypeMenu;
    private JComboBox<String> carFrameTypeMenu;
    private JTextField carFrameNameField;
    private JButton carFrameColorButton;
    private JTextField carDoorNumberField;
    private JTextField carEngineTypeField;
    private JTextField carFuelTypeField;
    private JTextField carPowerField;
    private JTextField carTorqueField;
    private JTextField customUpgradeField;

    public BuilderDesign() {
        //Panel uri separate pentru main si pentru configurare
        ConfigPanel = new JPanel();
        ConfigPanel.setLayout(null);
        ConfigPanel.setBounds(0,0,1080,850);
        ConfigPanel.setVisible(false);

        //panel pentru a schimba afisarea in functie de ce vehicul construim
        vehicleTypesPanels = new JPanel(new CardLayout());

        //buton de back to main
        backToMainPanelButton = new JButton("Back");
        backToMainPanelButton.setBounds(950,50,100,30);
        backToMainPanelButton.setFont(new Font("Arial",Font.BOLD,10));
        backToMainPanelButton.setFocusPainted(false);
        backToMainPanelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConfigPanel.setVisible(false);
                MainPanel.setVisible(true);
                createVehicleButton.setVisible(true);
                createVehicleButton.setEnabled(true);
            }
        });


        setContentPane(MainPanel);
        setTitle("Builder Design Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080,860);
        setLocationRelativeTo(null);
        setVisible(true);
        createVehicleButton.setFocusPainted(false);



        JLabel vehicleNameLabel=new JLabel("Vehicle Name");
        vehicleNameLabel.setBounds(50,50,150,30);
        vehicleNameField=new JTextField();
        vehicleNameField.setBounds(200,50,200,30);

        JLabel vehicleTypeLabel = new JLabel("Vehicle Type");
        vehicleTypeLabel.setBounds(50,100,150,30);

        String[] vehicleTypes={"Car","Motorcycle","Bicycle"};
       vehicleTypeMenu=new JComboBox<>(vehicleTypes);
        vehicleTypeMenu.setFocusable(Boolean.FALSE);
        vehicleTypeMenu.setBounds(200,100,200,30);



        ConfigPanel.add(backToMainPanelButton);
        ConfigPanel.add(vehicleNameLabel);
        ConfigPanel.add(vehicleNameField);
        ConfigPanel.add(vehicleTypeLabel);
        ConfigPanel.add(vehicleTypeMenu);

        MainPanel.setLayout(null);
        ConfigPanel.setVisible(false);
        MainPanel.add(ConfigPanel);




        JPanel carPanel=createCarPanel();
        JPanel motorcyclePanel=createMotorcyclePanel();
//        JPanel bicyclePanel=createBicyclePanel();


        vehicleTypesPanels.add(carPanel,"Car");
        vehicleTypesPanels.add(motorcyclePanel,"Motorcycle");
//        vehicleTypesPanels.add(bicyclePanel,"Bicycle");

        vehicleTypesPanels.setBounds(50,100,600,800);
        ConfigPanel.add(vehicleTypesPanels);



        createVehicleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createVehicleButton.setEnabled(false);
                createVehicleButton.setVisible(false);
                ConfigPanel.setVisible(true);
            }
        });

        vehicleTypeMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String vehicleType=vehicleTypeMenu.getSelectedItem().toString();
                CardLayout c1=(CardLayout)(vehicleTypesPanels.getLayout());
                c1.show(vehicleTypesPanels,vehicleType);
                titleLabel.setText(vehicleType+" Configuration ");
            }
        });

//        vehicleNameField.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                updateVariable();
//            }
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                updateVariable();
//            }
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                updateVariable();
//            }
//            private void updateVariable() {
//                vehicleName[0]=vehicleNameField.getText();
//            }
//        });





        setVisible(true);

    }


    private JPanel createCarPanel(){
        JPanel carPanel=new JPanel();
        carPanel.setLayout(null);
        carPanel.setPreferredSize(new Dimension(1080,860));


        JLabel frameType=new JLabel("Frame type");
        frameType.setBounds(10,100,200,30);
        String[] carFrameTypes = {"Sedan","Hatchback","Break","SUV","Crossover","Coupe","Cabrio"};
        carFrameTypeMenu=new JComboBox<>(carFrameTypes);
        carFrameTypeMenu.setFocusable(Boolean.FALSE);
        carFrameTypeMenu.setBounds(250,100,200,30);
        carPanel.add(frameType);
        carPanel.add(carFrameTypeMenu);


        JLabel frameName=new JLabel("Frame name or inspiration");
        frameName.setBounds(10,150,200,30);
        carFrameNameField=new JTextField();
        carFrameNameField.setBounds(250,150,200,30);
        carPanel.add(frameName);
        carPanel.add(carFrameNameField);

        JLabel frameColor=new JLabel("Frame color");
        frameColor.setBounds(10,200,200,30);
        final Color[][] color = {{(Color.white)}};
        JButton frameColorButton=new JButton("Pick color");
        frameColorButton.setBounds(250,200,200,30);
        frameColorButton.setFocusPainted(false);
        carFrameColorButton=frameColorButton;
        frameColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    color[0][0] = JColorChooser.showDialog(null, "Pick the color", color[0][0]);
                    if (color[0][0] == null) {
                        color[0][0] = Color.WHITE;
                    }
                    frameColorButton.setBackground(color[0][0]);
                    frameColorButton.setForeground(color[0][0]);
                    frameColorButton.repaint();
                    carFrameColorButton =frameColorButton;
                });
            }
        });
        carPanel.add(frameColor);
        carPanel.add(frameColorButton);

        String frameColorFinal=color[0][0].toString();


        JLabel doorNumberLabel=new JLabel("Door number");
        doorNumberLabel.setBounds(10,250,200,30);
        carDoorNumberField=new JTextField();
        carDoorNumberField.setBounds(250,250,200,30);
        carDoorNumberField.setText("0");
        carPanel.add(doorNumberLabel);
        carPanel.add(carDoorNumberField);



        JLabel engine=new JLabel("Engine type");
        engine.setBounds(10,300,200,30);
        carEngineTypeField=new JTextField();
        carEngineTypeField.setBounds(250,300,200,30);
        JLabel fuelType=new JLabel("Fuel type");
        fuelType.setBounds(10,350,200,30);
        carFuelTypeField=new JTextField();
        carFuelTypeField.setBounds(250,350,200,30);
        JLabel power=new JLabel("Power (HP)");
        power.setBounds(10,400,200,30);
        carPowerField=new JTextField();
        carPowerField.setBounds(250,400,200,30);
        carPowerField.setText("0");
        JLabel torque=new JLabel("Torque(NM)");
        torque.setBounds(10,450,200,30);
        carTorqueField=new JTextField();
        carTorqueField.setBounds(250,450,200,30);
        carTorqueField.setText("0");

        carPanel.add(engine);
        carPanel.add(carEngineTypeField);
        carPanel.add(fuelType);
        carPanel.add(carFuelTypeField);
        carPanel.add(power);
        carPanel.add(carPowerField);
        carPanel.add(torque);
        carPanel.add(carTorqueField);


        JLabel customUpgrades=new JLabel("Custom upgrades");
        customUpgrades.setBounds(10,500,200,30);
        customUpgradeField=new JTextField();
        customUpgradeField.setBounds(250,500,200,50);

        carPanel.add(customUpgrades);
        carPanel.add(customUpgradeField);

        JButton buildButton = new JButton("Build");
        buildButton.setBounds(10,600,200,30);
        buildButton.setFocusPainted(false);
        carPanel.add(buildButton);
        buildButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(buildVehicle().getDescription());

//                System.out.println("Generating image via Prodia API...");
//                String outputPath = "generated_vehicle_image.png";
//                ProdiaClient ProdiaIntegration = new ProdiaClient();
//                BufferedImage vehicleImage = ProdiaClient.generateVehicleImage(buildVehicle().getDescription(),outputPath,buildVehicle().getVehicleID());
//                if (vehicleImage != null) {
//                    System.out.println("Image generated and saved to: " + outputPath);
//
//                    // Afisare imagine
//                    ImageDisplayPanel.displayImageInWindow(vehicleImage, "Generated Vehicle");
//                } else {
//                    System.out.println("Failed to generate vehicle image.");
//                }
            }
        });


        return carPanel;

    }


    private JPanel createMotorcyclePanel(){
        JPanel motoPanel=new JPanel();
        motoPanel.setLayout(null);
        motoPanel.setPreferredSize(new Dimension(1080,860));


        JLabel frameType=new JLabel("Frame type");
        frameType.setBounds(10,100,200,30);
        String[] carFrameTypes = {"Sport","Mountain Bike","Street"};
        carFrameTypeMenu=new JComboBox<>(carFrameTypes);
        carFrameTypeMenu.setFocusable(Boolean.FALSE);
        carFrameTypeMenu.setBounds(250,100,200,30);
        motoPanel.add(frameType);
        motoPanel.add(carFrameTypeMenu);


        JLabel frameName=new JLabel("Frame name or inspiration");
        frameName.setBounds(10,150,200,30);
        carFrameNameField=new JTextField();
        carFrameNameField.setBounds(250,150,200,30);
        motoPanel.add(frameName);
        motoPanel.add(carFrameNameField);

        JLabel frameColor=new JLabel("Frame color");
        frameColor.setBounds(10,200,200,30);
        final Color[][] color = {{(Color.white)}};
        JButton frameColorButton=new JButton("Pick color");
        frameColorButton.setBounds(250,200,200,30);
        frameColorButton.setFocusPainted(false);
        carFrameColorButton=frameColorButton;
        frameColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    color[0][0] = JColorChooser.showDialog(null, "Pick the color", color[0][0]);
                    if (color[0][0] == null) {
                        color[0][0] = Color.WHITE;
                    }
                    frameColorButton.setBackground(color[0][0]);
                    frameColorButton.setForeground(color[0][0]);
                    frameColorButton.repaint();
                    carFrameColorButton =frameColorButton;
                });
            }
        });
        motoPanel.add(frameColor);
        motoPanel.add(frameColorButton);

        String frameColorFinal=color[0][0].toString();


//        JLabel doorNumberLabel=new JLabel("Door number");
//        doorNumberLabel.setBounds(10,250,200,30);
//        carDoorNumberField=new JTextField();
//        carDoorNumberField.setBounds(250,250,200,30);
//        carDoorNumberField.setText("0");
//        motoPanel.add(doorNumberLabel);
//        motoPanel.add(carDoorNumberField);



        JLabel engine=new JLabel("Engine type");
        engine.setBounds(10,300,200,30);
        carEngineTypeField=new JTextField();
        carEngineTypeField.setBounds(250,300,200,30);
        JLabel fuelType=new JLabel("Fuel type");
        fuelType.setBounds(10,350,200,30);
        carFuelTypeField=new JTextField();
        carFuelTypeField.setBounds(250,350,200,30);
        JLabel power=new JLabel("Power (HP)");
        power.setBounds(10,400,200,30);
        carPowerField=new JTextField();
        carPowerField.setBounds(250,400,200,30);
        carPowerField.setText("0");
        JLabel torque=new JLabel("Torque(NM)");
        torque.setBounds(10,450,200,30);
        carTorqueField=new JTextField();
        carTorqueField.setBounds(250,450,200,30);
        carTorqueField.setText("0");

        motoPanel.add(engine);
        motoPanel.add(carEngineTypeField);
        motoPanel.add(fuelType);
        motoPanel.add(carFuelTypeField);
        motoPanel.add(power);
        motoPanel.add(carPowerField);
        motoPanel.add(torque);
        motoPanel.add(carTorqueField);


        JLabel customUpgrades=new JLabel("Custom upgrades");
        customUpgrades.setBounds(10,500,200,30);
        customUpgradeField=new JTextField();
        customUpgradeField.setBounds(250,500,200,50);

        motoPanel.add(customUpgrades);
        motoPanel.add(customUpgradeField);

        JButton buildButton = new JButton("Build");
        buildButton.setBounds(10,600,200,30);
        buildButton.setFocusPainted(false);
        motoPanel.add(buildButton);
        buildButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(buildVehicle().getDescription());

//                System.out.println("Generating image via Prodia API...");
//                String outputPath = "generated_vehicle_image.png";
//                ProdiaClient ProdiaIntegration = new ProdiaClient();
//                BufferedImage vehicleImage = ProdiaClient.generateVehicleImage(buildVehicle().getDescription(),outputPath,buildVehicle().getVehicleID());
//                if (vehicleImage != null) {
//                    System.out.println("Image generated and saved to: " + outputPath);
//
//                    // Afisare imagine
//                    ImageDisplayPanel.displayImageInWindow(vehicleImage, "Generated Vehicle");
//                } else {
//                    System.out.println("Failed to generate vehicle image.");
//                }
            }
        });


        return motoPanel;

    }

    Vehicle buildVehicle(){
        String vehicleName=vehicleNameField.getText();
        String vehicleType=Objects.requireNonNull(vehicleTypeMenu.getSelectedItem().toString());
        String frameType = Objects.requireNonNull(carFrameTypeMenu.getSelectedItem()).toString();
        String frameName = carFrameNameField.getText();
        int red=carFrameColorButton.getBackground().getRed();
        int green=carFrameColorButton.getBackground().getGreen();
        int blue=carFrameColorButton.getBackground().getBlue();
        String frameColor="r="+red+",g="+green+",b="+blue;

        int doorNumber = Integer.parseInt(carDoorNumberField.getText());
        String engineType = carEngineTypeField.getText();
        String fuelType = carFuelTypeField.getText();
        int power = Integer.parseInt(carPowerField.getText());
        int torque = Integer.parseInt(carTorqueField.getText());
        String customComponentDescription=customUpgradeField.getText();

        builder.setName(vehicleName)
                .setVehicleType(vehicleType);

        Frame frame = new Frame.Builder()
                .setFrameType(frameType)
                .setModelName(frameName)
                .setFrameColor(frameColor)
                .setDoorNumber(doorNumber)
                .build();
        builder.addComponent(frame);

        Engine engine = new Engine.Builder()
                .setEngineType(engineType)
                .setFuelType(fuelType)
                .setHorsePower(power)
                .setTorque(torque).build();

        builder.addComponent(engine);

        CustomComponent customComponent = new CustomComponent("custom",customComponentDescription);
        builder.addComponent(customComponent);

        return builder.build();
    }






    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");  // Windows Look and feel
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        new BuilderDesign();


    }
}
