package entity;

public class Treatment {

    private String treatmentName;
    private Integer treatmentPrice;

    public Treatment(String treatmentName, Integer treatmentPrice) {
        this.treatmentName = treatmentName;
        this.treatmentPrice = treatmentPrice;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public Integer getTreatmentPrice() {
        return treatmentPrice;
    }

    public void setTreatmentPrice(Integer treatmentPrice) {
        this.treatmentPrice = treatmentPrice;
    }
}
