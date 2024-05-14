package com.example.maruthielectricals;

import java.util.Date;

public class Invoice {
    private int custId,invoiceId;
    private Date date;
    private String cname;
    private float total;

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Invoice(int custId, Date date, float total, String name) {
        this.custId = custId;
        this.cname=name;
        this.date = date;
        this.total = total;
    }
}
