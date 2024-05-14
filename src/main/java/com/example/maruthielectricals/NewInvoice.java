package com.example.maruthielectricals;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.border.GrooveBorder;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

public class NewInvoice implements Initializable {
    @FXML
    private TextArea caddress;
    @FXML
    private TextField cmail,cphone;
    @FXML
    private ComboBox<String> custCombo,prodCombo;
    @FXML
    private TextArea pdescription;
    @FXML
    private TextField pprice,pquantity,total;
    @FXML
    private Spinner<Integer> qty;
    @FXML
    private TableView<InvoiceItem> Invoice_Table;
    @FXML
    private  TableColumn sno;
    @FXML
    private TableColumn<InvoiceItem,String> des;
    @FXML
    private TableColumn<InvoiceItem,Integer> quan;
    @FXML
    private TableColumn<InvoiceItem,Float> pr;
    @FXML
    private TableColumn<InvoiceItem,Float> tot;
    @FXML
    private CheckBox gst;
    @FXML
    private Button clear1,remove;
    private Customer c=null;
    private Product p=null;
    private ObservableList<Customer> customer;
    private ObservableList<Product> product;
    private ObservableList<String> custname= FXCollections.observableArrayList();
    private ObservableList<String> prodname= FXCollections.observableArrayList();
    private ObservableList<InvoiceItem> inItems= FXCollections.observableArrayList();
    private int inId;
    private float totprice=0,inter=0;
    public Date generateInvoice() throws FileNotFoundException, MalformedURLException {
        String path="invoices//invoice"+String.valueOf(inId)+".pdf";
        PdfWriter pdfWriter=new PdfWriter(path);
        PdfDocument pdfDocument=new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        Document document=new Document(pdfDocument);
        float a=600f,b=300f;
        float d[]={a,b};
        Table t=new Table(d);
        ImageData imageData = ImageDataFactory.create("C://Users//m//IdeaProjects//Maruthi-Electricals//src//main//resources//com//example//maruthielectricals//ME_logo2.png");
        Table nt=new Table(new float[]{a});
        Border bo=new GrooveBorder(3);
        nt.addCell(new Cell().add("MARUTHI ELECTRICALS").setBold().setMarginTop(10).setFontSize(22).setBorderBottom(bo).setBorder(Border.NO_BORDER));
        nt.addCell(new Cell().add("14, N.M.S. Street, (Opp. Old Bus Stand) Kamaraj Road, TIRUPUR - 641 604").setItalic().setFontSize(12).setBorder(Border.NO_BORDER));
        nt.addCell(new Cell().add("Ph : 0421-2241465").setBorder(Border.NO_BORDER));
        nt.addCell(new Cell().add("Cell : 94898 04577").setBorder(Border.NO_BORDER));
        nt.addCell(new Cell().setHeight(30).setBorder(Border.NO_BORDER));
        nt.addCell(new Cell().add("BILL TO").setBold().setFontSize(14).setBorderBottom(bo).setBorder(Border.NO_BORDER));
        nt.addCell(new Cell().add(custCombo.getValue()).setBold().setFontSize(12).setItalic().setBorder(Border.NO_BORDER));
        nt.addCell(new Cell().add(caddress.getText()).setItalic().setFontSize(12).setBorder(Border.NO_BORDER));
        nt.addCell(new Cell().add("Mail : "+cmail.getText()).setBorder(Border.NO_BORDER));
        nt.addCell(new Cell().add("Cell : "+cphone.getText()).setBorder(Border.NO_BORDER));
        t.addCell(new Cell().add(nt.setMarginRight(20)).setBorder(Border.NO_BORDER));
        Table nested1=new Table(new float[]{b});
        Table nested2=new Table(new float[]{b/2,b/2});
        t.addCell(new Cell().add(nested1).setBorder(Border.NO_BORDER));
        Image img=new Image(imageData);
        Image img1=new Image(imageData);
        float x=pdfDocument.getDefaultPageSize().getHeight()/2;
        float y=pdfDocument.getDefaultPageSize().getWidth()/2;
        img1.setFixedPosition(x-300,y-20);
        img1.setOpacity(0.1f);
        document.add(img1);
        img.setWidth(120f);
        img.setHeight(120f);
        nested1.addCell(new Cell().add(img.setHorizontalAlignment(HorizontalAlignment.CENTER)).setBorder(Border.NO_BORDER));
        nested1.addCell(new Cell().setHeight(10).setBorder(Border.NO_BORDER));
        nested1.addCell(new Cell().add(nested2).setBorder(Border.NO_BORDER));
        nested2.addCell(new Cell().add("Invoice No.").setTextAlignment(TextAlignment.LEFT).setBold().setBorder(Border.NO_BORDER));
        nested2.addCell(new Cell().add(String.valueOf(inId)).setBorder(Border.NO_BORDER));
        nested2.addCell(new Cell().add("GST No.").setTextAlignment(TextAlignment.LEFT).setBold().setBorder(Border.NO_BORDER));
        nested2.addCell(new Cell().add("33AAHFM0037G1ZR").setBorder(Border.NO_BORDER));
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        LocalTime lt=LocalTime.now();
        nested2.addCell(new Cell().add("Date.").setTextAlignment(TextAlignment.LEFT).setBold().setBorder(Border.NO_BORDER));
        nested2.addCell(new Cell().add(formatter.format(date)).setBorder(Border.NO_BORDER));
        nested2.addCell(new Cell().add("Time.").setTextAlignment(TextAlignment.LEFT).setBold().setBorder(Border.NO_BORDER));
        nested2.addCell(new Cell().add(lt.toString()).setBorder(Border.NO_BORDER));

        document.add(new Cell().add(t.setBorderBottom(new DashedBorder(Color.GRAY,0.5f))).setBorder(Border.NO_BORDER));
        document.add(new Paragraph("Products").setBold().setFontSize(14));
        Table product=new Table(new float[]{50f,350f,200f,200f,200f});
        product.addCell(new Cell().add("Sno.").setBackgroundColor(Color.BLACK,0.7f).setFontColor(Color.WHITE).setBold().setBorder(Border.NO_BORDER));
        product.addCell(new Cell().add("Description").setBackgroundColor(Color.BLACK,0.7f).setFontColor(Color.WHITE).setBold().setBorder(Border.NO_BORDER));
        product.addCell(new Cell().add("Quantity").setBackgroundColor(Color.BLACK,0.7f).setFontColor(Color.WHITE).setBold().setBorder(Border.NO_BORDER));
        product.addCell(new Cell().add("Price").setBackgroundColor(Color.BLACK,0.7f).setFontColor(Color.WHITE).setBold().setBorder(Border.NO_BORDER));
        product.addCell(new Cell().add("Total").setBackgroundColor(Color.BLACK,0.7f).setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT).setMarginRight(15f).setBold().setBorder(Border.NO_BORDER));
        int k=0;
        for(InvoiceItem i:inItems){
            product.addCell(new Cell().add(String.valueOf(++k)).setBorder(Border.NO_BORDER));
            product.addCell(new Cell().add(i.getName()).setBorder(Border.NO_BORDER));
            product.addCell(new Cell().add(String.valueOf(i.getQuantity())).setBorder(Border.NO_BORDER));
            product.addCell(new Cell().add(String.valueOf(i.getUPrice())).setBorder(Border.NO_BORDER));
            product.addCell(new Cell().add(String.valueOf(i.getTPrice())).setTextAlignment(TextAlignment.RIGHT).setMarginRight(15f).setBorder(Border.NO_BORDER));
        }
        for(int i=0;i<5;i++){
            product.addCell(new Cell().setHeight(20).setBorder(Border.NO_BORDER));
        }
        product.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        product.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        product.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        product.addCell(new Cell().add("SUM").setBold().setBorderTop(new DashedBorder(0.5f)).setBorder(Border.NO_BORDER));
        product.addCell(new Cell().add(String.valueOf(totprice)).setBold().setTextAlignment(TextAlignment.RIGHT).setMarginRight(15f).setBorderTop(new DashedBorder(0.5f)).setBorder(Border.NO_BORDER));

        product.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        product.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        product.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        product.addCell(new Cell().add("GST").setBold().setBorder(Border.NO_BORDER));
        product.addCell(new Cell().add(String.valueOf(inter)).setBold().setTextAlignment(TextAlignment.RIGHT).setMarginRight(15f).setBorder(Border.NO_BORDER));

        product.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        product.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        product.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        product.addCell(new Cell().add("TOTAL").setBold().setBorderBottom(new DashedBorder(0.5f)).setBorder(Border.NO_BORDER));
        product.addCell(new Cell().add(total.getText()).setBold().setTextAlignment(TextAlignment.RIGHT).setMarginRight(15f).setBorderBottom(new DashedBorder(0.5f)).setBorder(Border.NO_BORDER));
        for(int i=0;i<5;i++){
            product.addCell(new Cell().setHeight(20).setBorder(Border.NO_BORDER));
        }
        document.add(new Cell().add(product.setBorderBottom(new SolidBorder(Color.GRAY,3))).setBorder(Border.NO_BORDER));
        document.add(new Paragraph("TERMS AND CONDITIONS").setFontSize(14).setBold());
        document.add(new Paragraph("1. Refunds will be issued in the original form of payment upon receipt of returned goods, minus any applicable restocking fees."));
        document.add(new Paragraph("2. The company is not liable for any damages or losses resulting from the use of our products or services."));
        document.add(new Paragraph("3. Payment of this invoice constitutes acceptance of these terms and conditions."));
        document.add(new Paragraph("Seal & Signature").setTextAlignment(TextAlignment.RIGHT).setMarginTop(60f).setMarginRight(20f));
        document.close();
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Generated Successfully");
        alert.setTitle("Invoice");
        ButtonType yes=new ButtonType("View");
        ButtonType no=new ButtonType("Cancel");
        alert.getButtonTypes().setAll(yes,no);
        Optional<ButtonType> clcked=alert.showAndWait();
        if(clcked.isPresent()&&clcked.get()==yes) {
            File pdfFile = new File("C://Users//m//IdeaProjects//Maruthi-Electricals//"+path);
            try {
                Desktop.getDesktop().open(pdfFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return date;
    }
    public void generate1(ActionEvent actionEvent) throws MalformedURLException, FileNotFoundException, SQLException {
        Date d= generateInvoice();
        DbUtils dbUtils=new DbUtils();
        dbUtils.saveInvoice(c.getId(),d,totprice+inter);
        dbUtils.insertInvoiceItem(inItems);
        inItems.clear();
        Invoice_Table.setItems(inItems);
        product=dbUtils.getAllProducts();
        total.clear();
    }
    public void addToTable(ActionEvent actionEvent){
        int a= qty.getValue();
        float b=Float.valueOf(pprice.getText());
        String c=prodCombo.getValue();
        System.out.println(a+" "+b+" "+a*b);
        for(InvoiceItem i:inItems){
            if(i.getName().equals(c)){
                inItems.remove(i);
            }
        }
        inItems.add(new InvoiceItem(inId,a,p.getPid(),c,b,a*b));
        totprice+=a*b;
        setPrice(actionEvent);
        setsno();
        Invoice_Table.setItems(inItems);
        clearProduct(actionEvent);
    }
    public void removeFromTable(ActionEvent actionEvent){
        InvoiceItem i=Invoice_Table.getSelectionModel().getSelectedItem();
        if (i != null) {
            totprice-=i.getTPrice();
            Invoice_Table.getItems().remove(i);
        }
        setPrice(actionEvent);
    }
    public void getSelected(MouseEvent event){
        InvoiceItem i=Invoice_Table.getSelectionModel().getSelectedItem();
        prodCombo.setValue(i.getName());
        qty.getValueFactory().setValue(i.getQuantity());
    }
    public void setsno(){
        int k=0;
        for(InvoiceItem i:inItems){
            i.setId(k+1);
            k++;
        }
    }
    public void setPrice(ActionEvent actionEvent){
        if(gst.isSelected()){
            inter=(float) (totprice*0.18);
        }
        else inter=0;
        total.setText(String.valueOf(totprice+inter));
    }
    public void clearCustomer(ActionEvent actionEvent){
        custCombo.setValue(null);
        custCombo.setPromptText("Select Customer");
        cmail.setText(null);
        cphone.setText(null);
        caddress.setText(null);
    }
    public void clearProduct(ActionEvent actionEvent){
        prodCombo.setValue(null);
        prodCombo.setPromptText("Select Product");
        pprice.setText(null);
        pquantity.setText(null);
        pdescription.setText(null);
        qty.getValueFactory().setValue(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sno.setCellValueFactory(new PropertyValueFactory<>("id"));
        des.setCellValueFactory(new PropertyValueFactory<InvoiceItem,String>("name"));
        quan.setCellValueFactory(new PropertyValueFactory<InvoiceItem,Integer>("quantity"));
        pr.setCellValueFactory(new PropertyValueFactory<InvoiceItem,Float>("uPrice"));
        tot.setCellValueFactory(new PropertyValueFactory<InvoiceItem,Float>("tPrice"));
        DbUtils dbUtils= null;
        try {
            dbUtils = new DbUtils();
            inId=dbUtils.getInvoiceId()+1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        customer=dbUtils.getAllCustomers();
        for(Customer c:customer) custname.add(c.getName());
        custCombo.setItems(custname);
        custCombo.valueProperty().addListener((obs, oldValue, newValue) -> {
            // newValue is the selected name
            int selectedIndex = custCombo.getSelectionModel().getSelectedIndex();
            // Ensure the index is valid
            if (selectedIndex >= 0 && selectedIndex < customer.size()) {
                c = customer.get(selectedIndex);
                cmail.setText(c.getEmail());
                cphone.setText(c.getPhone());
                caddress.setText(c.getAddress());
            }
        });
        qty.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));

        product=dbUtils.getAllProducts();
        for(Product p:product) prodname.add(p.getPname());
        prodCombo.setItems(prodname);
        prodCombo.valueProperty().addListener((obs, oldValue, newValue) -> {
            // newValue is the selected name
            int selectedIndex = prodCombo.getSelectionModel().getSelectedIndex();
            // Ensure the index is valid
            if (selectedIndex >= 0 && selectedIndex < product.size()) {
                p = product.get(selectedIndex);
                pquantity.setText(String.valueOf(p.getQuantity()));
                pprice.setText(String.valueOf(p.getPrice()));
                pdescription.setText(p.getDescription());
                qty.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, p.getQuantity()));
            }
        });
    }


}
