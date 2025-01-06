import com.google.gson.Gson;

public class APIResponse {
    public String generated_image;
    public String error;

    @Override
    public String toString() {
        return "Generated Image URL: " + generated_image;
    }
}
