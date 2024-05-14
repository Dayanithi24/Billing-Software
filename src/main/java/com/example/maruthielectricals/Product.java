package com.example.maruthielectricals;

public class Product {
    private int pid,quantity;
    private String category,pname,description;
    private float price;

    public Product(int pid, String category, String pname, int quantity, float price, String description ) {
        this.pid = pid;
        this.quantity = quantity;
        this.category = category;
        this.pname = pname;
        this.description = description;
        this.price = price;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
