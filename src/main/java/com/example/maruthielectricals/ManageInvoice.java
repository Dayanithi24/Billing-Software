package com.example.maruthielectricals;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageInvoice implements Initializable {
    @FXML
    private TableColumn<Invoice, String> cname;

    @FXML
    private TableColumn<Invoice, Date> date;

    @FXML
    private Button delete;

    @FXML
    private TableColumn<Invoice, Integer> id;

    @FXML
    private TableView<Invoice> itable;

    @FXML
    private TableColumn<Invoice, Float> total;
    @FXML
    private PieChart piechart;
    private ObservableList<Invoice> list;
    private ObservableList<PieChart.Data> plist= FXCollections.observableArrayList();
    private Invoice i=null;
    private int k=-1,n=0;

    @FXML
    private Button view;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setCellValueFactory(new PropertyValueFactory<Invoice,Integer>("invoiceId"));
        cname.setCellValueFactory(new PropertyValueFactory<Invoice,String>("cname"));
        date.setCellValueFactory(new PropertyValueFactory<Invoice,Date>("date"));
        total.setCellValueFactory(new PropertyValueFactory<Invoice,Float>("total"));
        DbUtils dbUtils= null;
        try {
            dbUtils = new DbUtils();
            list=dbUtils.getAllInvoices();
            itable.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void viewInvoice(ActionEvent actionEvent) {
        String path="invoice"+String.valueOf(n)+".pdf";
        File pdfFile =null;
        try {
            pdfFile=new File("C://Users//m//IdeaProjects//Maruthi-Electricals//invoices//"+path);
            Desktop.getDesktop().open(pdfFile);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle error opening file
        }
    }

    public void deleteInvoice(ActionEvent actionEvent) {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure want to delete");
        alert.setTitle("Delete Product");
        ButtonType yes=new ButtonType("Delete");
        ButtonType no=new ButtonType("Cancel");
        alert.getButtonTypes().setAll(yes,no);
        Optional<ButtonType> clcked=alert.showAndWait();
        if(clcked.isPresent()&&clcked.get()==yes) {
            try {
                DbUtils dbUtils = new DbUtils();
                dbUtils.deleteInvoice(id.getCellData(k));
                list.clear();
                list=dbUtils.getAllInvoices();
                itable.setItems(list);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void getSelected(MouseEvent mouseEvent) throws SQLException {
        plist.clear();
        piechart.getData().clear();
        k=itable.getSelectionModel().getSelectedIndex();
        if(k<=-1) return;
        n=id.getCellData(k);
        DbUtils dbUtils=new DbUtils();
        HashMap<String,Integer> ls=dbUtils.getInvoiceItems(n);
        for(String l:ls.keySet()){
            plist.add(new PieChart.Data(l,ls.get(l)));
        }
        plist.forEach(data -> data.nameProperty().bind(Bindings.concat(data.getName()," ",data.pieValueProperty())));
        piechart.getData().addAll(plist);
    }
    public void generateInvoices(ActionEvent actionEvent) throws FileNotFoundException, MalformedURLException {
        ImageData imageData = ImageDataFactory.create("C://Users//m//IdeaProjects//Maruthi-Electricals//src//main//resources//com//example//maruthielectricals//ME_logo2.png");
        String path="Invoices.pdf";
        PdfWriter pdfWriter=new PdfWriter(path);
        PdfDocument pdfDocument=new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        Document document=new Document(pdfDocument);
        document.add(new Paragraph("MARUTHI ELECTRICALS").setBold().setMarginTop(10).setFontSize(24).setBorderBottom(new SolidBorder(com.itextpdf.kernel.color.Color.GRAY,3)).setBorder(Border.NO_BORDER));
        document.add(new Paragraph("14, N.M.S. Street, (Opp. Old Bus Stand) Kamaraj Road, TIRUPUR - 641 604").setItalic().setFontSize(12).setBorder(Border.NO_BORDER));
        document.add(new Paragraph("Ph : 0421-2241465").setBorder(Border.NO_BORDER));
        document.add(new Paragraph("Cell : 94898 04577").setBorder(Border.NO_BORDER).setMarginBottom(40));
        document.add(new Paragraph("Invoices").setMarginBottom(30).setBold().setFontSize(14));
        Table product=new Table(new float[]{100f,200f,150f,200f});
        product.addCell(new com.itextpdf.layout.element.Cell().add("Invoice_ID").setBackgroundColor(com.itextpdf.kernel.color.Color.BLACK,0.7f).setFontColor(com.itextpdf.kernel.color.Color.WHITE).setBold().setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        product.addCell(new com.itextpdf.layout.element.Cell().add("Customer_Name").setBackgroundColor(com.itextpdf.kernel.color.Color.BLACK,0.7f).setFontColor(com.itextpdf.kernel.color.Color.WHITE).setBold().setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        product.addCell(new com.itextpdf.layout.element.Cell().add("Invoice_Date").setBackgroundColor(com.itextpdf.kernel.color.Color.BLACK,0.7f).setFontColor(com.itextpdf.kernel.color.Color.WHITE).setBold().setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        product.addCell(new com.itextpdf.layout.element.Cell().add("Total_Price").setBackgroundColor(com.itextpdf.kernel.color.Color.BLACK,0.7f).setFontColor(com.itextpdf.kernel.color.Color.WHITE).setBold().setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        for(Invoice i:list){
            product.addCell(new com.itextpdf.layout.element.Cell().add(String.valueOf(i.getInvoiceId())).setBorder(Border.NO_BORDER));
            product.addCell(new com.itextpdf.layout.element.Cell().add(i.getCname()).setBorder(Border.NO_BORDER));
            product.addCell(new com.itextpdf.layout.element.Cell().add(String.valueOf(i.getDate())).setBorder(Border.NO_BORDER));
            product.addCell(new com.itextpdf.layout.element.Cell().add(String.valueOf(i.getTotal())).setBorder(Border.NO_BORDER));

        }
        document.add(new Cell().add(product.setBorder(Border.NO_BORDER)));
        com.itextpdf.layout.element.Image img=new com.itextpdf.layout.element.Image(imageData);
        com.itextpdf.layout.element.Image img1=new Image(imageData);
        float x=pdfDocument.getDefaultPageSize().getHeight()/2;
        float y=pdfDocument.getDefaultPageSize().getWidth()/2;
        img1.setFixedPosition(x-300,y-20);
        img1.setOpacity(0.1f);
        document.add(img1);
        document.close();
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Generated Successfully");
        alert.setTitle("Report");
        ButtonType yes=new ButtonType("View");
        ButtonType no=new ButtonType("Cancel");
        alert.getButtonTypes().setAll(yes,no);
        Optional<ButtonType> clcked=alert.showAndWait();
        if(clcked.isPresent()&&clcked.get()==yes) {
            File pdfFile = new File("C://Users//m//IdeaProjects//Maruthi-Electricals//Invoices.pdf");
            try {
                Desktop.getDesktop().open(pdfFile);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle error opening file
            }

        }
    }
}
