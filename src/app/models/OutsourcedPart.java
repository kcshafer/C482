package app.models;

public class OutsourcedPart extends AbstractPart {
    private String companyName;

    public OutsourcedPart() {}

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
