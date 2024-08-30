package mera.proj.tiff.model;

public class Product {
    private String name;
    private String measure;
    private int quantity;
    private String type;

    public Product(){
        name = "";
        measure = "";
        quantity = 0;
    }

    public Product(String name, String measure, int quantity, String type){
        this.name = name;
        this.measure = measure;
        this.quantity = quantity;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
