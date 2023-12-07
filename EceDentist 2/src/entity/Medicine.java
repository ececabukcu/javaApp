package entity;

public class Medicine {
    private String medicine;
    private Integer price;

    public Medicine(String medicine, Integer price) {
        this.medicine = medicine;
        this.price = price;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
