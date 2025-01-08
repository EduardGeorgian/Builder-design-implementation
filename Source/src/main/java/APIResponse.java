import com.google.gson.Gson;

//clasa pentru a avea raspunsul api ului, initial prodia ai returna un url al imaginii
//insa hugging face ai returneaza direct imaginea in biti, ceea ce face un pic inutila clasa
public class APIResponse {
    public String generated_image;
    public String error;

    @Override
    public String toString() {
        return "Generated Image URL: " + generated_image;
    }
}
