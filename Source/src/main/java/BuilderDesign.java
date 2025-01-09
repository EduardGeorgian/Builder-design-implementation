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

    private JPanel MainPanel;   // Panel ul principal, care o sa contina butonul de create vehicle si lista obiectelor deja construite
    private JPanel ConfigPanel; // Panoul de configurare, care o sa contina la randul lui fiecare panou pentru feicare tip de vehicul
    private JButton createVehicleButton; // Butonul de create vehicle
    private JLabel titleLabel;
    private JLabel myVehiclesLabel;
    private JScrollPane scrollPane;
    private String laptopPath="C:\\Users\\edied\\IdeaProjects\\Builder-design-implementation\\Resources\\generated-images";
    private String pcPath="D:\\PIP-2024-2025\\Builder-design-implementation\\Resources\\generated-images";
    // Private JButton openImageButton;//deschiderea imaginilor poate fi facuta si printrun buton dar am ales sa fie la dublu click
    // Buton pentru a reveni la panoul principal
    private JButton backToMainPanelButton;

    // Panou care va contine tipurile de vehicule
    private JPanel vehicleTypesPanels;

    // Crearea unui builder pentru vehicul
    Vehicle.Builder builder = new Vehicle.Builder();

    // Clientul pentru API-ul HuggingFace, utilizat pentru generarea imaginilor
    HuggingFaceClient client = new HuggingFaceClient();

    // Variabile pentru componentele de configurare a vehiculului
    private JTextField vehicleNameField;  // Camp de text pentru numele vehiculului
    private JComboBox<String> vehicleTypeMenu;  // Meniu derulant pentru tipul vehiculului
    private JComboBox<String> carFrameTypeMenu;  // Meniu derulant pentru tipul de cadru al masinii
    private JTextField carFrameNameField;  // Camp de text pentru numele cadrului masinii
    private JButton carFrameColorButton;  // Buton pentru alegerea culorii cadrului masinii
    private JTextField carDoorNumberField;  // Camp de text pentru numarul de usi ale masinii
    private JTextField carEngineTypeField;  // Camp de text pentru tipul motorului masinii
    private JTextField carFuelTypeField;  // Camp de text pentru tipul de combustibil al masinii
    private JTextField carPowerField;  // Camp de text pentru puterea motorului masinii
    private JTextField carTorqueField;  // Camp de text pentru cuplul motorului masinii
    private JTextField carCustomUpgradeField;  // Camp de text pentru upgrade-uri personalizate ale masinii
    private JTextField motorcycleCustomUpgradeField;  // Camp de text pentru upgrade-uri personalizate la motocicleta
    private JTextArea vehicleDescriptionArea;  // Aria de text pentru descrierea vehiculului
    private JLabel statusLabel;  // Eticheta pentru statusul procesului de creare a vehiculului
    private JButton viewCarImageButton;  // Buton pentru vizualizarea imaginii masinii
    private JButton viewMotorcycleImageButton;  // Buton pentru vizualizarea imaginii motocicletei

    // Constructorul clasei BuilderDesign, care configureaza interfata utilizatorului
    public BuilderDesign() {
        // Calea catre directorul care contine imaginile generate
        String imageDirectoryPath = pcPath;

        // Crearea unui model de lista pentru imagini
        DefaultListModel<String> imageListModel = new DefaultListModel<>();

        // Incarca numele fișierelor din directorul specificat
        File imageDirectory = new File(imageDirectoryPath);

        // Verifica dacă directorul exista si este un director valid
        if (imageDirectory.exists() && imageDirectory.isDirectory()) {
            // Parcurge fisierele din director
            for (File file : imageDirectory.listFiles()) {
                // Adauga fisierele cu extensia .png în modelul listei
                if (file.isFile() && file.getName().endsWith(".png")) {
                    imageListModel.addElement(file.getName());
                }
            }
        } else {
            // Dacă directorul nu exista sau nu este valid, afisează un mesaj de eroare
            JOptionPane.showMessageDialog(null, "Image directory not found: " + imageDirectoryPath,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Creaz un JList pentru a afișa lista de imagini, folosind modelul de listă definit anterior
        JList<String> imageList = new JList<>(imageListModel);

// Permite selecția unui singur element din lista
        imageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

// Setează fontul textului din lista
        imageList.setFont(new Font("Arial Black", Font.PLAIN, 12));

// Seteaza numărul de rânduri vizibile în lista de imagini
        imageList.setVisibleRowCount(10);

// Seteaza dimensiunile JList pentru a controla dimensiunea afișării
        imageList.setBounds(0, 0, 500, 400);

// Seteaza culoarea de fundal a JList
        imageList.setBackground(new Color(92, 146, 139));

// Adauga JList-ul într-un JScrollPane pentru a permite derularea listelor mai lungi
        scrollPane.add(imageList);

// Actualizeaza și redesenaza lista
        imageList.revalidate();
        imageList.repaint();

// Seteaz JScrollPane pentru a vizualiza JList-ul
        scrollPane.setViewportView(imageList);

// Adauga un mouse listener pentru a raspunde la click-uri pe lista
        imageList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Verifica dacă utilizatorul a dat dublu click pe un element din lista
                if (e.getClickCount() == 2) {
                    // Obtine fisierul selectat din lista
                    String selectedFile = imageList.getSelectedValue();

                    // Dacă fisierul selectat nu este nul
                    if (selectedFile != null) {
                        // Creeaza o fereastra noua pentru vizualizarea imaginii
                        JFrame frame = new JFrame("Image Viewer");
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // inchide fereastra la click pe X
                        frame.setSize(1080, 860);  // Setează dimensiunea ferestrei

                        // Incarcă imaginea utilizand calea completa
                        ImageIcon imageIcon = new ImageIcon(imageDirectoryPath + "\\" + selectedFile);

                        // Creeaza un JLabel pentru a afisa imaginea
                        JLabel label = new JLabel(imageIcon);

                        // Adaugă JLabel intr un JScrollPane pentru a permite derularea imaginii dacă este prea mare
                        frame.add(new JScrollPane(label));

                        // Afiseaza fereastra
                        frame.setVisible(true);
                    }
                }
            }
        });


// Creaza un panou pentru configuratie (ConfigPanel) cu un layout personalizat
        ConfigPanel = new JPanel();
        ConfigPanel.setLayout(null); // Seteaza layout-ul null pentru a permite setarea manuala a pozitiei fiecarei componente
        ConfigPanel.setBounds(0, 0, 1080, 850); // Seteaza dimensiunile panoului
        ConfigPanel.setVisible(false); // Panoul nu este vizibil initial
        ConfigPanel.setBackground(new Color(53, 146, 142)); // Seteaza culoarea de fundal a panoului

// Creaza un label pentru a afisa mesaje de stare
        statusLabel = new JLabel("");
        statusLabel.setBounds(700, 400, 400, 200); // Seteaza pozitia si dimensiunea label-ului
        statusLabel.setFont(new Font("Arial Black", Font.PLAIN, 20)); // Seteaza fontul pentru textul label-ului

// Adauga label-ul in panoul de configurare
        ConfigPanel.add(statusLabel);

// Creaza un panou pentru a comuta intre diferite tipuri de vehicule folosind CardLayout
        vehicleTypesPanels = new JPanel(new CardLayout());

// Creaza un buton "Back" pentru a reveni la panoul principal
        backToMainPanelButton = new JButton("Back");
        backToMainPanelButton.setBounds(950, 50, 100, 30); // Seteaza pozitia si dimensiunea butonului
        backToMainPanelButton.setFont(new Font("Arial", Font.BOLD, 10)); // Seteaza fontul butonului
        backToMainPanelButton.setFocusPainted(false); // Nu se va vedea border-ul pe buton cand este selectat

// Adauga un ActionListener pentru a reveni la panoul principal cand se apasa butonul "Back"
        backToMainPanelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ascunde panoul de configurare si afiseaza panoul principal
                ConfigPanel.setVisible(false);
                MainPanel.setVisible(true);
                createVehicleButton.setVisible(true);
                createVehicleButton.setEnabled(true);
                myVehiclesLabel.setVisible(true);

                // Reincarca lista de imagini
                imageList.revalidate();
                imageList.repaint();
                scrollPane.repaint();
                scrollPane.revalidate();
                scrollPane.setVisible(true);

                // Seteaza titlul aplicatiei
                titleLabel.setText("Vehicle Builder");
            }
        });

// Seteaza panoul principal ca fiind continutul aplicatiei
        setContentPane(MainPanel);
        setTitle("Builder Design Application"); // Seteaza titlul ferestrei
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Seteaza comportamentul la inchiderea aplicatiei
        setSize(1080, 860); // Seteaza dimensiunile ferestrei
        setLocationRelativeTo(null); // Centeraza fereastra pe ecran
        setVisible(true); // Face fereastra vizibila

// Creeaza componentele pentru panoul de configurare
        JLabel vehicleNameLabel = new JLabel("Vehicle Name");
        vehicleNameLabel.setBounds(50, 50, 150, 30);
        vehicleNameField = new JTextField();
        vehicleNameField.setBounds(200, 50, 200, 30);

        JLabel vehicleTypeLabel = new JLabel("Vehicle Type");
        vehicleTypeLabel.setBounds(50, 100, 150, 30);

// Creeaza un combo box pentru selectarea tipului de vehicul
        String[] vehicleTypes = {"Car", "Motorcycle", "Bicycle"};
        vehicleTypeMenu = new JComboBox<>(vehicleTypes);
        vehicleTypeMenu.setFocusable(Boolean.FALSE); // Dezactiveaza focus-ul pentru acest combo box
        vehicleTypeMenu.setBounds(200, 100, 200, 30);

// Adauga componentele in panoul de configurare
        ConfigPanel.add(backToMainPanelButton);
        ConfigPanel.add(vehicleNameLabel);
        ConfigPanel.add(vehicleNameField);
        ConfigPanel.add(vehicleTypeLabel);
        ConfigPanel.add(vehicleTypeMenu);

// Configureaza layout-ul panoului principal
        MainPanel.setLayout(null);
        ConfigPanel.setVisible(false); // Panoul de configurare nu este vizibil la inceput
        MainPanel.add(ConfigPanel); // Adauga panoul de configurare in panoul principal

// Creeaza panouri pentru fiecare tip de vehicul
        JPanel carPanel = createCarPanel(); // Creeaza panoul pentru configurarea unei masini
        JPanel motorcyclePanel = createMotorcyclePanel(); // Creeaza panoul pentru configurarea unei motociclete
// JPanel bicyclePanel = createBicyclePanel(); // Creeaza panoul pentru configurarea unei biciclete

// Adauga panourile pentru fiecare tip de vehicul in panoul de tipuri de vehicule
        vehicleTypesPanels.add(carPanel, "Car");
        vehicleTypesPanels.add(motorcyclePanel, "Motorcycle");
// vehicleTypesPanels.add(bicyclePanel, "Bicycle");

// Seteaza dimensiunile panoului pentru tipurile de vehicule
        vehicleTypesPanels.setBounds(50, 100, 600, 800);
        ConfigPanel.add(vehicleTypesPanels);

// Adauga un ActionListener pentru butonul de creare vehicul
        createVehicleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cand butonul este apasat, se ascunde butonul si se afiseaza panoul de configurare
                createVehicleButton.setEnabled(false);
                createVehicleButton.setVisible(false);
                ConfigPanel.setVisible(true);
                myVehiclesLabel.setVisible(false);
                scrollPane.setVisible(false);
            }
        });

// Adauga un ActionListener pentru combo box-ul de selectie a tipului de vehicul
        vehicleTypeMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtine tipul de vehicul selectat
                String vehicleType = vehicleTypeMenu.getSelectedItem().toString();

                // Schimba vizualizarea panoului de vehicule in functie de selectia utilizatorului
                CardLayout c1 = (CardLayout) (vehicleTypesPanels.getLayout());
                c1.show(vehicleTypesPanels, vehicleType);

                // Actualizeaza titlul aplicatiei cu tipul de vehicul selectat
                titleLabel.setText(vehicleType + " Configuration ");
            }
        });


        setVisible(true);

    }

    // Creaza panoul pentru configurarea masinii (carPanel)
    private JPanel createCarPanel() {
        JPanel carPanel = new JPanel();
        carPanel.setLayout(null); // Folosim layout null pentru a pozitiona manual componentele
        carPanel.setPreferredSize(new Dimension(1080, 860)); // Seteaza dimensiunea panoului
        carPanel.setBackground(new Color(92, 146, 139)); // Seteaza culoarea de fundal

        // Creaza un label pentru tipul de cadru (Frame)
        JLabel frameType = new JLabel("Frame type");
        frameType.setBounds(10, 100, 200, 30); // Seteaza pozitia label-ului
        String[] carFrameTypes = {"Sedan", "Hatchback", "Break", "SUV", "Crossover", "Coupe", "Cabrio"}; // Tipuri de cadre pentru masina
        carFrameTypeMenu = new JComboBox<>(carFrameTypes); // ComboBox pentru selectarea tipului de cadru
        carFrameTypeMenu.setFocusable(Boolean.FALSE); // Dezactiveaza focusul pe ComboBox
        carFrameTypeMenu.setBounds(250, 100, 200, 30); // Seteaza pozitia ComboBox-ului
        carPanel.add(frameType); // Adauga label-ul in panou
        carPanel.add(carFrameTypeMenu); // Adauga ComboBox-ul in panou

        // Creaza un label pentru numele sau inspiratie pentru cadru
        JLabel frameName = new JLabel("Frame name or inspiration");
        frameName.setBounds(10, 150, 200, 30); // Seteaza pozitia label-ului
        carFrameNameField = new JTextField(); // Camp de text pentru numele cadrului
        carFrameNameField.setBounds(250, 150, 200, 30); // Seteaza pozitia campului de text
        carPanel.add(frameName); // Adauga label-ul in panou
        carPanel.add(carFrameNameField); // Adauga campul de text in panou

        // Creaza un label pentru culoarea cadrului
        JLabel frameColor = new JLabel("Frame color");
        frameColor.setBounds(10, 200, 200, 30); // Seteaza pozitia label-ului
        final Color[][] color = {{(Color.white)}}; // Initializam culoarea default ca fiind alba
        JButton frameColorButton = new JButton("Pick color"); // Buton pentru a alege culoarea
        frameColorButton.setBounds(250, 200, 200, 30); // Seteaza pozitia butonului
        frameColorButton.setFocusPainted(false); // Dezactiveaza efectul de focus pe buton
        carFrameColorButton = frameColorButton; // Salveaza referinta la butonul de culoare

        // Adauga un listener pentru a schimba culoarea cadrului
        frameColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    color[0][0] = JColorChooser.showDialog(null, "Pick the color", color[0][0]); // Alege culoarea
                    if (color[0][0] == null) {
                        color[0][0] = Color.WHITE; // Daca nu s a ales nici o culoare, seteaza alb
                    }
                    frameColorButton.setBackground(color[0][0]); // Seteaza fundalul butonului la culoarea aleasa
                    frameColorButton.setForeground(color[0][0]); // Seteaza culoarea textului la culoarea aleasa
                    frameColorButton.repaint(); // Re-deseneaza butonul
                    carFrameColorButton = frameColorButton; // Salveaza referinta la buton
                });
            }
        });

        carPanel.add(frameColor); // Adauga label ul pentru culoare
        carPanel.add(frameColorButton); // Adauga butonul pentru culoare

        String frameColorFinal = color[0][0].toString(); // Salveaza culoarea finala aleasa

        // Creaza un label si un camp de text pentru numarul de usi
        JLabel doorNumberLabel = new JLabel("Door number");
        doorNumberLabel.setBounds(10, 250, 200, 30);
        carDoorNumberField = new JTextField();
        carDoorNumberField.setBounds(250, 250, 200, 30);
        carDoorNumberField.setText("0"); // Seteaza valoarea default la 0
        carPanel.add(doorNumberLabel); // Adauga label-ul in panou
        carPanel.add(carDoorNumberField); // Adauga campul de text in panou

        // Creaza label uri si campuri de text pentru motor, combustibil, putere si cuplu
        JLabel engine = new JLabel("Engine type");
        engine.setBounds(10, 300, 200, 30);
        carEngineTypeField = new JTextField();
        carEngineTypeField.setBounds(250, 300, 200, 30);

        JLabel fuelType = new JLabel("Fuel type");
        fuelType.setBounds(10, 350, 200, 30);
        carFuelTypeField = new JTextField();
        carFuelTypeField.setBounds(250, 350, 200, 30);

        JLabel power = new JLabel("Power (HP)");
        power.setBounds(10, 400, 200, 30);
        carPowerField = new JTextField();
        carPowerField.setBounds(250, 400, 200, 30);
        carPowerField.setText("0"); // Seteaza valoarea default la 0

        JLabel torque = new JLabel("Torque (NM)");
        torque.setBounds(10, 450, 200, 30);
        carTorqueField = new JTextField();
        carTorqueField.setBounds(250, 450, 200, 30);
        carTorqueField.setText("0"); // Seteaza valoarea default la 0

        carPanel.add(engine);
        carPanel.add(carEngineTypeField);
        carPanel.add(fuelType);
        carPanel.add(carFuelTypeField);
        carPanel.add(power);
        carPanel.add(carPowerField);
        carPanel.add(torque);
        carPanel.add(carTorqueField);

        // Creaza un label si un camp de text pentru upgrade uri personalizate
        JLabel customUpgrades = new JLabel("Custom upgrades");
        customUpgrades.setBounds(10, 500, 200, 30);
        carCustomUpgradeField = new JTextField();
        carCustomUpgradeField.setBounds(250, 500, 200, 50);

        carPanel.add(customUpgrades); // Adauga label-ul
        carPanel.add(carCustomUpgradeField); // Adauga campul de text

        // Creaza un buton pentru a vizualiza imaginea generata
        viewCarImageButton = new JButton("View generated image");
        viewCarImageButton.setBounds(250, 600, 200, 30);
        viewCarImageButton.setFocusPainted(false); // Dezactiveaza efectul de focus pe buton
        viewCarImageButton.setEnabled(false); // Butonul este dezactivat initial
        carPanel.add(viewCarImageButton);

        // Creaza un buton pentru a construi vehiculul
        JButton buildButton = new JButton("Build");
        buildButton.setBounds(10, 600, 200, 30);
        buildButton.setFocusPainted(false); // Dezactiveaza efectul de focus pe buton
        carPanel.add(buildButton);

        // Adauga un listener pentru butonul de construire a vehiculului
//        buildButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                statusLabel.setText("Generating your vehicle...");// Afiseaza mesajul de generare
//                statusLabel.repaint();
//                statusLabel.revalidate();
//                Vehicle vehicle;
//                vehicle=buildVehicle();
//                String prompt = vehicle.getDescription(); // Obtine descrierea vehiculului construit
//                System.out.println("Vehicle Description: " + prompt); // Afiseaza descrierea vehiculului
//
//                try {
//                    // Specificam calea de salvare pentru imaginea generata
//                    String outputPath = laptopPath+"\\"+vehicle.getVehicleID() + ".png";
//
//                    // Apelam metoda pentru a genera imaginea
//                    HuggingFaceClient.generateImage(prompt, outputPath);
//                    System.out.println("Image generated and saved successfully at " + outputPath); // Mesaj de succes
//                    statusLabel.setText("Vehicle generated."); // Actualizeaza statusul
//                    viewCarImageButton.setEnabled(true); // Activeaza butonul pentru a vizualiza imaginea
//
//                    // Adauga un listener pentru a deschide imaginea generata
//                    viewCarImageButton.addActionListener(new ActionListener() {
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//                            JFrame imageFrame = new JFrame("Generated image"); // Creeaza o fereastra pentru imagine
//                            imageFrame.setSize(1080, 860);
//                            imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//                            ImageIcon imageIcon = new ImageIcon(outputPath); // Creeaza o iconita cu imaginea generata
//                            JLabel imageLabel = new JLabel(imageIcon); // Adauga imaginea intr un label
//                            imageFrame.add(imageLabel); // Adauga label ul in fereastra
//
//                            imageFrame.setVisible(true); // Afiseaza fereastra
//                        }
//                    });
//
//                } catch (Exception ex) {
//                    ex.printStackTrace(); // Afiseaza eroarea in caz ca nu merge
//                    JOptionPane.showMessageDialog(null, "An error occurred while generating the image. Please try again.", "Error", JOptionPane.ERROR_MESSAGE); // Mesaj de eroare
//                }
//            }
//        });

        // Schimbam logica api ului pe alt thread, pentru ca nu se randeaza statusLabel
        buildButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Generating your vehicle...");
                statusLabel.repaint(); // Asigura ca modificarea este afisata imediat

                SwingWorker<Void, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        Vehicle vehicle = buildVehicle();
                        String prompt = vehicle.getDescription();
                        System.out.println(vehicle.getDescription());

                        try {
                            String outputPath = pcPath + "\\" + vehicle.getVehicleID() + ".png";
                            HuggingFaceClient.generateImage(prompt, outputPath);
                            System.out.println("Image generated and saved successfully at " + outputPath);

                            SwingUtilities.invokeLater(() -> {
                                viewCarImageButton.setEnabled(true); // Activeza butonul dupa generare
                                viewCarImageButton.addActionListener(ev -> {
                                    JFrame imageFrame = new JFrame("Generated image");
                                    imageFrame.setSize(1080, 860);
                                    imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                                    ImageIcon imageIcon = new ImageIcon(outputPath);
                                    JLabel imageLabel = new JLabel(imageIcon);
                                    imageFrame.add(imageLabel);

                                    imageFrame.setVisible(true);
                                    buildButton.setEnabled(true);
                                });
                            });
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                                    null, "An error occurred while generating the image. Please try again.", "Error", JOptionPane.ERROR_MESSAGE
                            ));
                        }
                        return null;
                    }

                    @Override
                    protected void done() {
                        statusLabel.setText("Vehicle generated.");
                    }
                };
                buildButton.setEnabled(false);
                worker.execute(); // Pornește worker-ul
            }
        });


        return carPanel; // Returneaza panoul de configurare a masinii
    }


    private JPanel createMotorcyclePanel() {
        JPanel motoPanel = new JPanel();
        motoPanel.setLayout(null); // Folosim un layout personalizat (null) pentru a pozitiona manual componentele
        motoPanel.setPreferredSize(new Dimension(1080, 860)); // Dimensiunea panoului
        motoPanel.setBackground(new Color(92, 146, 139)); // Seteaza culoarea de fundal

        // Creeaza un label pentru tipul de cadru (Frame)
        JLabel frameType = new JLabel("Frame type");
        frameType.setBounds(10, 100, 200, 30); // Seteaza pozitia label-ului
        String[] frameTypes = {"Sport", "Mountain Bike", "Street"}; // Tipuri de cadre pentru motocicleta
        carFrameTypeMenu = new JComboBox<>(frameTypes); // ComboBox pentru selectarea tipului de cadru
        carFrameTypeMenu.setFocusable(Boolean.FALSE); // Dezactiveaza focusul pe ComboBox
        carFrameTypeMenu.setBounds(250, 100, 200, 30); // Seteaza pozitia ComboBox-ului
        motoPanel.add(frameType); // Adauga label-ul in panou
        motoPanel.add(carFrameTypeMenu); // Adauga ComboBox-ul in panou

        // Creeaza un label si un camp de text pentru numele cadrului
        JLabel frameName = new JLabel("Frame name or inspiration");
        frameName.setBounds(10, 150, 200, 30); // Seteaza pozitia label-ului
        carFrameNameField = new JTextField(); // Camp de text pentru numele cadrului
        carFrameNameField.setBounds(250, 150, 200, 30); // Seteaza pozitia campului de text
        motoPanel.add(frameName); // Adauga label-ul
        motoPanel.add(carFrameNameField); // Adauga campul de text

        // Creeaza un label si un buton pentru alegerea culorii cadrului
        JLabel frameColor = new JLabel("Frame color");
        frameColor.setBounds(10, 200, 200, 30); // Seteaza pozitia label-ului
        final Color[][] color = {{Color.white}}; // Culoare initiala
        JButton frameColorButton = new JButton("Pick color"); // Buton pentru alegerea culorii
        frameColorButton.setBounds(250, 200, 200, 30); // Seteaza pozitia butonului
        frameColorButton.setFocusPainted(false); // Dezactiveaza efectul de focus pe buton
        carFrameColorButton = frameColorButton; // Salveaza referinta la butonul de culoare

        // Adauga un listener pentru alegerea culorii
        frameColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    color[0][0] = JColorChooser.showDialog(null, "Pick the color", color[0][0]); // Alege culoarea
                    if (color[0][0] == null) {
                        color[0][0] = Color.WHITE; // Daca nu s a ales nici o culoare, se seteaza alb
                    }
                    frameColorButton.setBackground(color[0][0]); // Seteaza fundalul butonului
                    frameColorButton.setForeground(color[0][0]); // Seteaza culoarea textului
                    frameColorButton.repaint(); // Redesenare buton
                    carFrameColorButton = frameColorButton; // Salveaza referinta la butonul de culoare
                });
            }
        });
        motoPanel.add(frameColor); // Adauga labelul pentru culoare
        motoPanel.add(frameColorButton); // Adauga butonul pentru culoare

        // Creeaza campuri pentru motor, combustibil, putere si cuplu
        JLabel engine = new JLabel("Engine type");
        engine.setBounds(10, 300, 200, 30);
        carEngineTypeField = new JTextField();
        carEngineTypeField.setBounds(250, 300, 200, 30);

        JLabel fuelType = new JLabel("Fuel type");
        fuelType.setBounds(10, 350, 200, 30);
        carFuelTypeField = new JTextField();
        carFuelTypeField.setBounds(250, 350, 200, 30);

        JLabel power = new JLabel("Power (HP)");
        power.setBounds(10, 400, 200, 30);
        carPowerField = new JTextField();
        carPowerField.setBounds(250, 400, 200, 30);
        carPowerField.setText("0"); // Seteaza valoarea default pentru putere

        JLabel torque = new JLabel("Torque (NM)");
        torque.setBounds(10, 450, 200, 30);
        carTorqueField = new JTextField();
        carTorqueField.setBounds(250, 450, 200, 30);
        carTorqueField.setText("0"); // Seteaza valoarea default pentru cuplu

        // Adauga toate campurile in panou
        motoPanel.add(engine);
        motoPanel.add(carEngineTypeField);
        motoPanel.add(fuelType);
        motoPanel.add(carFuelTypeField);
        motoPanel.add(power);
        motoPanel.add(carPowerField);
        motoPanel.add(torque);
        motoPanel.add(carTorqueField);

        // Creeaza un camp pentru upgrade uri personalizate
        JLabel customUpgrades = new JLabel("Custom upgrades");
        customUpgrades.setBounds(10, 500, 200, 30);
        motorcycleCustomUpgradeField = new JTextField();
        motorcycleCustomUpgradeField.setBounds(250, 500, 200, 50);
        motoPanel.add(customUpgrades);
        motoPanel.add(motorcycleCustomUpgradeField);

        // Buton pentru vizualizarea imaginii generate
        viewMotorcycleImageButton = new JButton("View generated image");
        viewMotorcycleImageButton.setBounds(250, 600, 200, 30);
        viewMotorcycleImageButton.setFocusPainted(false);
        viewMotorcycleImageButton.setEnabled(false); // Initial butonul este dezactivat
        motoPanel.add(viewMotorcycleImageButton);

        // Buton pentru a construi motocicleta
        JButton buildButton = new JButton("Build");
        buildButton.setBounds(10, 600, 200, 30);
        buildButton.setFocusPainted(false);
        motoPanel.add(buildButton);

        // Adauga un listener pentru butonul "Build"
        buildButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText("Generating your vehicle...");
                statusLabel.repaint(); // Asigura ca modificarea este afisata imediat

                SwingWorker<Void, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        Vehicle vehicle = buildVehicle();
                        String prompt = vehicle.getDescription();
                        System.out.println(vehicle.getDescription());

                        try {
                            String outputPath = pcPath + "\\" + vehicle.getVehicleID() + ".png";
                            HuggingFaceClient.generateImage(prompt, outputPath);
                            System.out.println("Image generated and saved successfully at " + outputPath);

                            SwingUtilities.invokeLater(() -> {
                                viewMotorcycleImageButton.setEnabled(true); // Activeza butonul dupa generare
                                viewMotorcycleImageButton.addActionListener(ev -> {
                                    JFrame imageFrame = new JFrame("Generated image");
                                    imageFrame.setSize(1080, 860);
                                    imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                                    ImageIcon imageIcon = new ImageIcon(outputPath);
                                    JLabel imageLabel = new JLabel(imageIcon);
                                    imageFrame.add(imageLabel);

                                    imageFrame.setVisible(true);
                                    buildButton.setEnabled(true);
                                });
                            });
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                                    null, "An error occurred while generating the image. Please try again.", "Error", JOptionPane.ERROR_MESSAGE
                            ));
                        }
                        return null;
                    }

                    @Override
                    protected void done() {
                        statusLabel.setText("Vehicle generated.");
                    }
                };
                buildButton.setEnabled(false);
                worker.execute(); // Pornește worker-ul
            }
        });

        return motoPanel; // Returneaza panoul pentru motocicleta
    }


    // Metoda buildVehicle construieste si returneaza un obiect Vehicle
    Vehicle buildVehicle() {
        // Se preia numele vehiculului din campul text
        String vehicleName = vehicleNameField.getText();
        System.out.println(vehicleName);

        // Se preia tipul vehiculului selectat din meniu
        String vehicleType = Objects.requireNonNull(vehicleTypeMenu.getSelectedItem().toString());
        System.out.println(vehicleType);

        // Se preia tipul , numele si culoarea
        String frameType = Objects.requireNonNull(carFrameTypeMenu.getSelectedItem()).toString();
        String frameName = carFrameNameField.getText();
        int red = carFrameColorButton.getBackground().getRed();
        int green = carFrameColorButton.getBackground().getGreen();
        int blue = carFrameColorButton.getBackground().getBlue();
        String frameColor = "r=" + red + ",g=" + green + ",b=" + blue;

        // Se preia numarul de usi din campul text
        int doorNumber = Integer.parseInt(carDoorNumberField.getText());


        // Se preia tipul motorului, tipul de combustibil, puterea si cuplul
        String engineType = carEngineTypeField.getText();
        String fuelType = carFuelTypeField.getText();
        int power = Integer.parseInt(carPowerField.getText());
        int torque = Integer.parseInt(carTorqueField.getText());

        // Se preia descrierea componentelor personalizate in functie de tipul vehiculului
        String customComponentDescription;
        if (vehicleType.equals("Car")) {
            customComponentDescription = carCustomUpgradeField.getText();
        } else if (vehicleType.equals("Motorcycle")) {
            customComponentDescription = motorcycleCustomUpgradeField.getText();
        } else {
            customComponentDescription = "";
        }

        // Se seteaza datele vehiculului in obiectul builder
        builder.setName(vehicleName)
                .setVehicleType(vehicleType);

        // Se creeaza cadrul vehiculului si se adauga la builder
        Frame frame = new Frame.Builder()
                .setFrameType(frameType)
                .setModelName(frameName)
                .setFrameColor(frameColor)
                .setDoorNumber(doorNumber)
                .build();
        builder.addComponent(frame);
        System.out.println(frame.getDescription());

        // Se creeaza motorul vehiculului si se adauga la builder
        Engine engine = new Engine.Builder()
                .setEngineType(engineType)
                .setFuelType(fuelType)
                .setHorsePower(power)
                .setTorque(torque)
                .build();
        builder.addComponent(engine);
        System.out.println(engine.getDescription());

        // Se creeaza componenta personalizata si se adauga la builder
        CustomComponent customComponent = new CustomComponent("custom", customComponentDescription);
        builder.addComponent(customComponent);

        // Se afiseaza descrierea componentei personalizate
        System.out.println(customComponentDescription);

        // Se returneaza vehiculul construit
        return builder.build();
    }

    // Metoda main porneste aplicatia
    public static void main(String[] args) {
        try {
            // Se seteaza aspectul aplicatiei pentru Windows
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");  // Windows Look and feel
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        // Se creeaza o instanta a clasei BuilderDesign
        new BuilderDesign();
    }
}
