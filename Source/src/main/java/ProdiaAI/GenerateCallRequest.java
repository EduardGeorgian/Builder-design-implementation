package ProdiaAI;

public class GenerateCallRequest {
    public String model = "v1-5-pruned-emaonly.safetensors [d7049739]";
    public String prompt;
    public String style_preset="3d-model";
    public int height=512;
    public int width=824;

    GenerateCallRequest(String prompt) {
        this.prompt = prompt;
    }
}
