package VehicleComponents;

//interfata pentru componente separate ale vehiculelor, ca sa putem adauga cat mai multe componente si
//pentru ca sa fie mai usor sa dezvoltam proiectul
//avem doar o functie de getDescription pentru ca in final avem nevoie doar de un prompt cu
//descrierea vehiculului pentru a fi trimis api ului, insa daca avem nevoie de altceva, pentru
//alte feature uri putem adauga
public interface VehicleComponent  {
    String getDescription(); //pentru descriere
}





