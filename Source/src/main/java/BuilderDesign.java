import VehicleComponents.CustomComponent;
import VehicleComponents.Engine;
import VehicleComponents.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Objects;

public class BuilderDesign extends JFrame {

    private JPanel MainPanel;
    private JPanel ConfigPanel;
    private JButton createVehicleButton;
    private JLabel titleLabel;
    private JLabel myVehiclesLabel;
    private JScrollPane scrollPane;
    private JButton openImageButton;
    private JButton backToMainPanelButton;
    private JPanel vehicleTypesPanels;
    Vehicle.Builder builder=new Vehicle.Builder();
    HuggingFaceClient client=new HuggingFaceClient();


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
    private JTextField carCustomUpgradeField;
    private JTextField motorcycleCustomUpgradeField;
    private JTextArea vehicleDescriptionArea;
    private JLabel statusLabel;
    private JButton viewCarImageButton;
    private JButton viewMotorcycleImageButton;

    public BuilderDesign() {
        String imageDirectoryPath="D:\\PIP-2024-2025\\Builder-design-implementation\\Resources\\generated-images";

        DefaultListModel<String> imageListModel = new DefaultListModel<>();
        // inncarca numele fisierelor din director
        File imageDirectory = new File(imageDirectoryPath);
        if (imageDirectory.exists() && imageDirectory.isDirectory()) {
            for (File file : imageDirectory.listFiles()) {
                if (file.isFile() && file.getName().endsWith(".png")) {
                    imageListModel.addElement(file.getName());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Image directory not found: " + imageDirectoryPath,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        JList<String> imageList = new JList<>(imageListModel);
        imageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        imageList.setFont(new Font("Arial Black", Font.PLAIN, 12 ));
        imageList.setVisibleRowCount(10);
        imageList.setBounds(0, 0, 500, 400);
        imageList.setBackground(new Color(92,146,139));
        scrollPane.add(imageList);
        imageList.revalidate();
        imageList.repaint();
        scrollPane.setViewportView(imageList);

        imageList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String selectedFile = imageList.getSelectedValue();
                    if (selectedFile != null) {
                        JFrame frame = new JFrame("Image Viewer");
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.setSize(1080, 860);

                        //incarcam imaginea
                        ImageIcon imageIcon = new ImageIcon(imageDirectoryPath+"\\" + selectedFile);
                        JLabel label = new JLabel(imageIcon);
                        frame.add(new JScrollPane(label)); // scroll daca imaginea este prea mare
                        frame.setVisible(true);
                    }
                }
            }
        });










        //Panel uri separate pentru main si pentru configurare
        ConfigPanel = new JPanel();
        ConfigPanel.setLayout(null);
        ConfigPanel.setBounds(0,0,1080,850);
        ConfigPanel.setVisible(false);
        ConfigPanel.setBackground(new Color(53,146,142));

         statusLabel= new JLabel("");
            statusLabel.setBounds(700,400,400,200);
            statusLabel.setFont(new Font("Arial Black", Font.PLAIN, 20));
//        vehicleDescriptionArea=new JTextArea();
//        vehicleDescriptionArea.setEditable(false);
//        vehicleDescriptionArea.setBackground(Color.WHITE);
//        vehicleDescriptionArea.setLineWrap(true);
//        vehicleDescriptionArea.setWrapStyleWord(true);
//        vehicleDescriptionArea.setBounds(800,400,200,200);
//        vehicleDescriptionArea.setFont(new Font("Arial",Font.PLAIN,10));

        ConfigPanel.add(statusLabel);
//        ConfigPanel.add(vehicleDescriptionArea);

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
                myVehiclesLabel.setVisible(true);
                imageList.revalidate();
                imageList.repaint();
                scrollPane.repaint();
                scrollPane.revalidate();
                scrollPane.setVisible(true);
                titleLabel.setText("Vehicle Builder");
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
                myVehiclesLabel.setVisible(false);
                scrollPane.setVisible(false);
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
        carPanel.setBackground(new Color(92,146,139));




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
        carCustomUpgradeField=new JTextField();
        carCustomUpgradeField.setBounds(250,500,200,50);

        carPanel.add(customUpgrades);
        carPanel.add(carCustomUpgradeField);


        viewCarImageButton = new JButton("View generated image");
        viewCarImageButton.setBounds(250, 600, 200, 30);
        viewCarImageButton.setFocusPainted(false);
        viewCarImageButton.setEnabled(false); // initial butonul este dezactivat.
        carPanel.add(viewCarImageButton);

        JButton buildButton = new JButton("Build");
        buildButton.setBounds(10,600,200,30);
        buildButton.setFocusPainted(false);
        carPanel.add(buildButton);
        buildButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Generating your vehicle...");
                String prompt = buildVehicle().getDescription();
                System.out.println("Vehicle Description: " + prompt);



                System.out.println("Generating image via HuggingFaceClient...");
                try {
                    // Specificăm calea de salvare pentru imagine
                    String outputPath = "D:\\PIP-2024-2025\\Builder-design-implementation\\Resources\\generated-images\\"+buildVehicle().getVehicleID()+".png";

                    // Apelăm metoda pentru a genera și salva imaginea
                    HuggingFaceClient.generateImage(prompt, outputPath);
                    System.out.println("Image generated and saved successfully at " + outputPath);
                    statusLabel.setText("Vehicle generated.");
                    viewCarImageButton.setEnabled(true);//activam butonul cand se genereaza imaginea

                    viewCarImageButton.addActionListener(new ActionListener() {
                       @Override
                       public void actionPerformed(ActionEvent e) {
                           JFrame imageFrame=new JFrame("Generated image");
                           imageFrame.setSize(1080,860);
                           imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                           ImageIcon imageIcon=new ImageIcon(outputPath);
                           JLabel imageLabel=new JLabel(imageIcon);
                           imageFrame.add(imageLabel);

                           imageFrame.setVisible(true);
                       }
                    });

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An error occurred while generating the image. Please try again.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });




        return carPanel;

    }


    private JPanel createMotorcyclePanel(){
        JPanel motoPanel=new JPanel();
        motoPanel.setLayout(null);
        motoPanel.setPreferredSize(new Dimension(1080,860));
        motoPanel.setBackground(new Color(92,146,139));


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
        motorcycleCustomUpgradeField=new JTextField();
        motorcycleCustomUpgradeField.setBounds(250,500,200,50);

        motoPanel.add(customUpgrades);
        motoPanel.add(motorcycleCustomUpgradeField);

        viewMotorcycleImageButton = new JButton("View generated image");
        viewMotorcycleImageButton.setBounds(250, 600, 200, 30);
        viewMotorcycleImageButton.setFocusPainted(false);
        viewMotorcycleImageButton.setEnabled(false); // initial butonul este dezactivat.
        motoPanel.add(viewMotorcycleImageButton);


        JButton buildButton = new JButton("Build");
        buildButton.setBounds(10,600,200,30);
        buildButton.setFocusPainted(false);
        motoPanel.add(buildButton);
        buildButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Generating your vehicle...");
                String prompt = buildVehicle().getDescription();
                System.out.println("Vehicle Description: " + prompt);


                System.out.println("Generating image via HuggingFaceClient...");
                try {
                    // Specificăm calea de salvare pentru imagine
                    String outputPath = "D:\\PIP-2024-2025\\Builder-design-implementation\\Resources\\generated-images\\"+buildVehicle().getVehicleID()+".png";

                    // Apelăm metoda pentru a genera și salva imaginea
                    HuggingFaceClient.generateImage(prompt, outputPath);
                    System.out.println("Image generated and saved successfully at " + outputPath);
                    statusLabel.setText("Vehicle Generated");
                    viewMotorcycleImageButton.setEnabled(true);//activam butonul cand se genereaza imaginea

                    viewMotorcycleImageButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JFrame imageFrame=new JFrame("Generated image");
                            imageFrame.setSize(1080,860);
                            imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                            ImageIcon imageIcon=new ImageIcon(outputPath);
                            JLabel imageLabel=new JLabel(imageIcon);
                            imageFrame.add(imageLabel);

                            imageFrame.setVisible(true);
                        }
                    });


                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An error occurred while generating the image. Please try again.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
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
        String customComponentDescription;
        if(vehicleType.equals("Car")){
            customComponentDescription=carCustomUpgradeField.getText();
        }else if(vehicleType.equals("Motorcycle")){
            customComponentDescription=motorcycleCustomUpgradeField.getText();
        }else{
            customComponentDescription="";
        }

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
        System.out.println(customComponentDescription);

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
