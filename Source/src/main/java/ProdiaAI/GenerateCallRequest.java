package ProdiaAI;

public class GenerateCallRequest {
    public String model = "v1-5-pruned-emaonly.safetensors [d7049739]";
    public String prompt;
    public String style_preset="digital-art";
    public int height=1024;
    public int width=768;

    GenerateCallRequest(String prompt) {
        this.prompt = prompt;
    }
}
