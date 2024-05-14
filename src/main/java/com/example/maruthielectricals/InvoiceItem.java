package com.example.maruthielectricals;

public class InvoiceItem {
    private int invoiceId,quantity,id,pid;
    private String name;
    private float uPrice,tPrice;

    public InvoiceItem(int invoiceId, int quantity,int pid, String name, float uPrice, float tPrice) {
        this.invoiceId = invoiceId;
        this.name = name;
        this.pid=pid;
        this.quantity = quantity;
        this.uPrice = uPrice;
        this.tPrice = tPrice;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "invoiceId=" + invoiceId +
                ", quantity=" + quantity +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", uPrice=" + uPrice +
                ", tPrice=" + tPrice +
                '}';
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getUPrice() {
        return uPrice;
    }

    public void setUPrice(float uPrice) {
        this.uPrice = uPrice;
    }

    public float getTPrice() {
        return tPrice;
    }

    public void setTPrice(float tPrice) {
        this.tPrice = tPrice;
    }
}
