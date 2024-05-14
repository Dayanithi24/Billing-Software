package com.example.maruthielectricals;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.GrooveBorder;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.TextAlignment;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.awt.Desktop;


public class ManageCust implements Initializable {
    @FXML
    private TableView<Customer> custTable;
    @FXML
    private TableColumn<Customer,Integer> cid;
    @FXML
    private TableColumn<Customer,String> cname;
    @FXML
    private TableColumn<Customer,String> cmail;
    @FXML
    private TableColumn<Customer,String> cphone;
    @FXML
    private TableColumn<Customer,String> caddress;
    @FXML
    private Button add;
    @FXML
    private Button update;
    @FXML
    private Button delete;

    @FXML
    private TextField tindex,tname,tnum,tmail,filter;
    @FXML
    private TextArea taddress;
    @FXML
    private CheckBox up;
    ObservableList<Customer> list;

    int ind=-1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cid.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("id"));
        cname.setCellValueFactory(new PropertyValueFactory<Customer,String>("name"));
        cmail.setCellValueFactory(new PropertyValueFactory<Customer,String>("email"));
        cphone.setCellValueFactory(new PropertyValueFactory<Customer,String>("phone"));
        caddress.setCellValueFactory(new PropertyValueFactory<Customer,String>("address"));
        DbUtils dbUtils= null;
        try {
            dbUtils = new DbUtils();
            list=dbUtils.getAllCustomers();
            custTable.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void getSelected(MouseEvent mouseEvent){
        ind=custTable.getSelectionModel().getSelectedIndex();
        if(ind<=-1) return;
        tindex.setText(cid.getCellData(ind).toString());
        tname.setText(cname.getCellData(ind));
        tmail.setText(cmail.getCellData(ind));
        tnum.setText(cphone.getCellData(ind));
        taddress.setText(caddress.getCellData(ind));
    }

    public void addCustomer(ActionEvent actionEvent) throws IOException {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure?");
        alert.setTitle("Add Customer");
        ButtonType yes=new ButtonType("Add");
        ButtonType no=new ButtonType("Cancel");
        alert.getButtonTypes().setAll(yes,no);
        Optional<ButtonType> clcked=alert.showAndWait();
        if(clcked.isPresent()&&clcked.get()==yes) {
            try {
                DbUtils dbUtils = new DbUtils();
                dbUtils.addCustomer(tname.getText(),tmail.getText(),tnum.getText(),taddress.getText());
                list.clear();
                list=dbUtils.getAllCustomers();
                custTable.setItems(list);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void visible(ActionEvent actionEvent){
        if(up.isSelected()){
            tname.setDisable(false);
            tnum.setDisable(false);
            tmail.setDisable(false);
            taddress.setDisable(false);
        }
        else{
            tname.setDisable(true);
            tnum.setDisable(true);
            tmail.setDisable(true);
            taddress.setDisable(true);
        }
    }
    public void updateCustomer(ActionEvent actionEvent){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure want to update");
        alert.setTitle("Update Customer");
        ButtonType yes=new ButtonType("Update");
        ButtonType no=new ButtonType("Cancel");
        alert.getButtonTypes().setAll(yes,no);
        Optional<ButtonType> clcked=alert.showAndWait();
        if(clcked.isPresent()&&clcked.get()==yes) {
            try {
                DbUtils dbUtils = new DbUtils();
                dbUtils.updateCustomer(Integer.valueOf(tindex.getText()),tname.getText(),tmail.getText(),tnum.getText(),taddress.getText());
                list.clear();
                list=dbUtils.getAllCustomers();
                custTable.setItems(list);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void deleteCustomer(ActionEvent actionEvent){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure want to delete");
        alert.setTitle("Delete Customer");
        ButtonType yes=new ButtonType("Delete");
        ButtonType no=new ButtonType("Cancel");
        alert.getButtonTypes().setAll(yes,no);
        Optional<ButtonType> clcked=alert.showAndWait();
        if(clcked.isPresent()&&clcked.get()==yes) {
            try {
                DbUtils dbUtils = new DbUtils();
                dbUtils.deleteCustomer(Integer.valueOf(tindex.getText()));
                list.clear();
                list=dbUtils.getAllCustomers();
                custTable.setItems(list);
                clearData(actionEvent);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void clearData(ActionEvent actionEvent){
        tindex.setText(null);
        tname.setText(null);
        tmail.setText(null);
        tnum.setText(null);
        taddress.setText(null);
    }
    public void searchCustomer(ActionEvent actionEvent){
        FilteredList<Customer> filteredData=new FilteredList<>(list,b->true);
        filter.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredData.setPredicate(p->{
                if(newValue==null||newValue.isEmpty()) return true;
                String lowerCaseFilter=newValue.toLowerCase();
                if(p.getName().toLowerCase().indexOf(lowerCaseFilter)!=-1) return true;
                else if(p.getEmail().toLowerCase().indexOf(lowerCaseFilter)!=-1) return true;
                else if(p.getPhone().toLowerCase().indexOf(lowerCaseFilter)!=-1) return true;
                else if(p.getAddress().toLowerCase().indexOf(lowerCaseFilter)!=-1) return true;
                else return false;
            });
        }));
        SortedList<Customer> sortedData=new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(custTable.comparatorProperty());
        custTable.setItems(sortedData);
    }

    public void generateCustomers(ActionEvent actionEvent) throws FileNotFoundException, MalformedURLException {
        ImageData imageData = ImageDataFactory.create("C://Users//m//IdeaProjects//Maruthi-Electricals//src//main//resources//com//example//maruthielectricals//ME_logo2.png");
        String path="Customers.pdf";
        PdfWriter pdfWriter=new PdfWriter(path);
        PdfDocument pdfDocument=new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        Document document=new Document(pdfDocument);
        document.add(new Paragraph("MARUTHI ELECTRICALS").setBold().setMarginTop(10).setFontSize(24).setBorderBottom(new SolidBorder(com.itextpdf.kernel.color.Color.GRAY,3)).setBorder(Border.NO_BORDER));
        document.add(new Paragraph("14, N.M.S. Street, (Opp. Old Bus Stand) Kamaraj Road, TIRUPUR - 641 604").setItalic().setFontSize(12).setBorder(Border.NO_BORDER));
        document.add(new Paragraph("Ph : 0421-2241465").setBorder(Border.NO_BORDER));
        document.add(new Paragraph("Cell : 94898 04577").setBorder(Border.NO_BORDER).setMarginBottom(40));
        document.add(new Paragraph("Customers").setMarginBottom(30).setBold().setFontSize(14));
        Table product=new Table(new float[]{50f,150f,200f,150f,250f});
        product.addCell(new com.itextpdf.layout.element.Cell().add("Customer_ID").setBackgroundColor(Color.BLACK,0.7f).setFontColor(Color.WHITE).setBold().setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        product.addCell(new com.itextpdf.layout.element.Cell().add("Name").setBackgroundColor(Color.BLACK,0.7f).setFontColor(Color.WHITE).setBold().setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        product.addCell(new com.itextpdf.layout.element.Cell().add("Email").setBackgroundColor(Color.BLACK,0.7f).setFontColor(Color.WHITE).setBold().setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        product.addCell(new com.itextpdf.layout.element.Cell().add("Phone").setBackgroundColor(Color.BLACK,0.7f).setFontColor(Color.WHITE).setBold().setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        product.addCell(new com.itextpdf.layout.element.Cell().add("Address").setBackgroundColor(Color.BLACK,0.7f).setFontColor(Color.WHITE).setMarginRight(15f).setBold().setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        for(Customer c:list){
            product.addCell(new com.itextpdf.layout.element.Cell().add(String.valueOf(c.getId())).setBorder(Border.NO_BORDER));
            product.addCell(new com.itextpdf.layout.element.Cell().add(c.getName()).setBorder(Border.NO_BORDER));
            product.addCell(new com.itextpdf.layout.element.Cell().add(c.getEmail()).setBorder(Border.NO_BORDER));
            product.addCell(new com.itextpdf.layout.element.Cell().add(c.getPhone()).setBorder(Border.NO_BORDER));
            product.addCell(new Cell().add(c.getAddress()).setTextAlignment(TextAlignment.RIGHT).setMarginRight(15f).setBorder(Border.NO_BORDER));
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
            File pdfFile = new File("C://Users//m//IdeaProjects//Maruthi-Electricals//Customers.pdf");
            try {
                Desktop.getDesktop().open(pdfFile);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle error opening file
            }

        }
    }
}
