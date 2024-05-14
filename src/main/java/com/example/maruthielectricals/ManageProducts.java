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
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageProducts implements Initializable {
    @FXML
    private Button add;
    @FXML
    private Button clear;
    @FXML
    private TableView<Product> prodTable;
    @FXML
    private Button delete;
    @FXML
    private TableColumn<Product, Integer> pid;
    @FXML
    private TableColumn<Product, String> pcategory;
    @FXML
    private TableColumn<Product, String> pproname;
    @FXML
    private TableColumn<Product, Integer> pquantity;
    @FXML
    private TableColumn<Product, Float> pprice;
    @FXML
    private TableColumn<Product, String> pdescription;

    @FXML
    private TextArea pdes;

    @FXML
    private TextField pindex,pcat,filter,pname,pp,pqty;
    @FXML
    private CheckBox up;
    @FXML
    private Button update;

    ObservableList<Product> list;

    int ind=-1;

    public void addProduct(ActionEvent event) {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure?");
        alert.setTitle("Add Product");
        ButtonType yes=new ButtonType("Add");
        ButtonType no=new ButtonType("Cancel");
        alert.getButtonTypes().setAll(yes,no);
        Optional<ButtonType> clcked=alert.showAndWait();
        if(clcked.isPresent()&&clcked.get()==yes) {
            try {
                DbUtils dbUtils=new DbUtils();
                dbUtils.saveProduct(pcat.getText(),pname.getText(),Integer.valueOf(pqty.getText()),Float.valueOf(pp.getText()),pdes.getText());
                list.clear();
                list=dbUtils.getAllProducts();
                prodTable.setItems(list);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void clearData(ActionEvent event) {
        pindex.setText(null);
        pcat.setText(null);
        pname.setText(null);
        pqty.setText(null);
        pp.setText(null);
        pdes.setText(null);
    }

    public void deleteProduct(ActionEvent actionEvent) {
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
                dbUtils.deleteProduct(Integer.valueOf(pindex.getText()));
                list.clear();
                list=dbUtils.getAllProducts();
                prodTable.setItems(list);
                clearData(actionEvent);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void getSelected(MouseEvent event) {
        ind=prodTable.getSelectionModel().getSelectedIndex();
        if(ind<=-1) return;
        pindex.setText(pid.getCellData(ind).toString());
        pcat.setText(pcategory.getCellData(ind));
        pname.setText(pproname.getCellData(ind));
        pqty.setText(pquantity.getCellData(ind).toString());
        pp.setText(pprice.getCellData(ind).toString());
        pdes.setText(pdescription.getCellData(ind));
    }
    public void searchProduct(ActionEvent event) {
        FilteredList<Product> filteredData=new FilteredList<>(list, b->true);
        filter.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredData.setPredicate(p->{
                if(newValue==null||newValue.isEmpty()) return true;
                String lowerCaseFilter=newValue.toLowerCase();
                if(p.getCategory().toLowerCase().indexOf(lowerCaseFilter)!=-1) return true;
                else if(p.getPname().toLowerCase().indexOf(lowerCaseFilter)!=-1) return true;
                else if(p.getDescription().toLowerCase().indexOf(lowerCaseFilter)!=-1) return true;
                else return false;
            });
        }));
        SortedList<Product> sortedData=new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(prodTable.comparatorProperty());
        prodTable.setItems(sortedData);
    }

    public void updateProduct(ActionEvent event) {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure want to update");
        alert.setTitle("Update Product");
        ButtonType yes=new ButtonType("Update");
        ButtonType no=new ButtonType("Cancel");
        alert.getButtonTypes().setAll(yes,no);
        Optional<ButtonType> clcked=alert.showAndWait();
        if(clcked.isPresent()&&clcked.get()==yes) {
            try {
                DbUtils dbUtils = new DbUtils();
                dbUtils.updateProduct(Integer.valueOf(pindex.getText()),pcat.getText(),pname.getText(),Integer.valueOf(pqty.getText()),Float.valueOf(pp.getText()),pdes.getText());
                list.clear();
                list=dbUtils.getAllProducts();
                prodTable.setItems(list);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void visible(ActionEvent event) {
        if(up.isSelected()){
            pcat.setDisable(false);
            pname.setDisable(false);
            pqty.setDisable(false);
            pp.setDisable(false);
            pdes.setDisable(false);
        }
        else {
            pcat.setDisable(true);
            pname.setDisable(true);
            pqty.setDisable(true);
            pp.setDisable(true);
            pdes.setDisable(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pid.setCellValueFactory(new PropertyValueFactory<Product,Integer>("pid"));
        pcategory.setCellValueFactory(new PropertyValueFactory<Product,String>("category"));
        pproname.setCellValueFactory(new PropertyValueFactory<Product,String>("pname"));
        pquantity.setCellValueFactory(new PropertyValueFactory<Product,Integer>("quantity"));
        pprice.setCellValueFactory(new PropertyValueFactory<Product,Float>("price"));
        pdescription.setCellValueFactory(new PropertyValueFactory<Product,String>("description"));
        DbUtils dbUtils= null;
        try {
            dbUtils = new DbUtils();
            list=dbUtils.getAllProducts();
            prodTable.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateProducts(ActionEvent actionEvent) throws FileNotFoundException, MalformedURLException {
        ImageData imageData = ImageDataFactory.create("C://Users//m//IdeaProjects//Maruthi-Electricals//src//main//resources//com//example//maruthielectricals//ME_logo2.png");
        String path="Products.pdf";
        PdfWriter pdfWriter=new PdfWriter(path);
        PdfDocument pdfDocument=new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        Document document=new Document(pdfDocument);
        document.add(new Paragraph("MARUTHI ELECTRICALS").setBold().setMarginTop(10).setFontSize(24).setBorderBottom(new SolidBorder(com.itextpdf.kernel.color.Color.GRAY,3)).setBorder(Border.NO_BORDER));
        document.add(new Paragraph("14, N.M.S. Street, (Opp. Old Bus Stand) Kamaraj Road, TIRUPUR - 641 604").setItalic().setFontSize(12).setBorder(Border.NO_BORDER));
        document.add(new Paragraph("Ph : 0421-2241465").setBorder(Border.NO_BORDER));
        document.add(new Paragraph("Cell : 94898 04577").setBorder(Border.NO_BORDER).setMarginBottom(40));
        document.add(new Paragraph("Products").setMarginBottom(30).setBold().setFontSize(14));
        Table product=new Table(new float[]{50f,100f,150f,100f,100f,250f});
        product.addCell(new com.itextpdf.layout.element.Cell().add("Product_ID").setBackgroundColor(Color.BLACK,0.7f).setFontColor(Color.WHITE).setBold().setBorder(Border.NO_BORDER));
        product.addCell(new com.itextpdf.layout.element.Cell().add("Category").setBackgroundColor(Color.BLACK,0.7f).setFontColor(Color.WHITE).setBold().setBorder(Border.NO_BORDER));
        product.addCell(new com.itextpdf.layout.element.Cell().add("Name").setBackgroundColor(Color.BLACK,0.7f).setFontColor(Color.WHITE).setBold().setBorder(Border.NO_BORDER));
        product.addCell(new com.itextpdf.layout.element.Cell().add("Quantity").setBackgroundColor(Color.BLACK,0.7f).setFontColor(Color.WHITE).setBold().setBorder(Border.NO_BORDER));
        product.addCell(new com.itextpdf.layout.element.Cell().add("Price").setBackgroundColor(Color.BLACK,0.7f).setFontColor(Color.WHITE).setMarginRight(15f).setBold().setBorder(Border.NO_BORDER));
        product.addCell(new com.itextpdf.layout.element.Cell().add("Description").setBackgroundColor(Color.BLACK,0.7f).setFontColor(Color.WHITE).setMarginRight(15f).setBold().setBorder(Border.NO_BORDER));
        for(Product p:list){
            product.addCell(new com.itextpdf.layout.element.Cell().add(String.valueOf(p.getPid())).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
            product.addCell(new com.itextpdf.layout.element.Cell().add(p.getCategory()).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
            product.addCell(new com.itextpdf.layout.element.Cell().add(p.getPname()).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
            product.addCell(new com.itextpdf.layout.element.Cell().add(String.valueOf(p.getQuantity())).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
            product.addCell(new com.itextpdf.layout.element.Cell().add(String.valueOf(p.getPrice())).setTextAlignment(TextAlignment.CENTER).setMarginRight(15f).setBorder(Border.NO_BORDER));
            product.addCell(new com.itextpdf.layout.element.Cell().add(p.getDescription()).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
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
            File pdfFile = new File("C://Users//m//IdeaProjects//Maruthi-Electricals//Products.pdf");
            try {
                Desktop.getDesktop().open(pdfFile);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle error opening file
            }

        }
    }
}
