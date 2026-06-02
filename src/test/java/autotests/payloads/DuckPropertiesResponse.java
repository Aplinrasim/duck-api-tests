package autotests.payloads;

public class DuckPropertiesResponse {
    private String color;
    private double height;
    private String material;
    private String sound;
    private String wingsState;

    public DuckPropertiesResponse() {
    }

    public DuckPropertiesResponse(String color, double height, String material, String sound, String wingsState) {
        this.color = color;
        this.height = height;
        this.material = material;
        this.sound = sound;
        this.wingsState = wingsState;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getWingsState() {
        return wingsState;
    }

    public void setWingsState(String wingsState) {
        this.wingsState = wingsState;
    }
}