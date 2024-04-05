package Command;

public class Command {

    private String type;
    private String key;
    private String value;

    public Command() {

    }

    public Command(String type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return this.type;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

}
