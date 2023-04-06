package fr.stvenchg.bleatz.api.set;

public class SetAddressRequest {
    private String field;
    private String value;

    public SetAddressRequest(String value) {
        this.field = "address";
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}