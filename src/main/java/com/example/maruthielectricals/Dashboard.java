package com.example.maruthielectricals;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {
    @FXML
    private Label noc,nop,noi;
    @FXML
    private LineChart<Date,Float> linechart;
    @FXML
    private ListView<String> istock=new ListView<>(), ostock=new ListView<>();
    private ObservableList<String> ls1= FXCollections.observableArrayList();
    private ObservableList<String> ls2= FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DbUtils dbUtils=null;
        try {
            XYChart.Series series=new XYChart.Series();
            dbUtils=new DbUtils();
            Map<String,Float> ls=dbUtils.getLastWeek();
            for(String l:ls.keySet()){
                series.getData().add(new XYChart.Data<>(l,ls.get(l)));
            }
            linechart.getData().add(series);
            noc.setText(String.valueOf(dbUtils.nCustomers()));
            nop.setText(String.valueOf(dbUtils.nProducts()));
            noi.setText(String.valueOf(dbUtils.getInvoiceId()));
            ls1=dbUtils.getOutOfStock();
            ls2=dbUtils.getDefficientStock();
            istock.setItems(ls2);
            ostock.setItems(ls1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
