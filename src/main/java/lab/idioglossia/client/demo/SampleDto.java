package lab.idioglossia.client.demo;


public class SampleDto {
    private String field;

    public SampleDto() {
    }

    public SampleDto(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "SampleDto{" +
                "field='" + field + '\'' +
                '}';
    }
}
