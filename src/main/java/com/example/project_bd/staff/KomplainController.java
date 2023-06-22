package com.example.project_bd.staff;

import com.example.project_bd.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class KomplainController {
    //Attribute

    Komplain komplain = new Komplain();
    //FXML
    @FXML
    Label kompensasiLabel;
    @FXML
    Button terimaKomplain;
    @FXML
    Button tolakKomplain;
    @FXML
    TextField kompensasi_field;
    //Tabel
    @FXML
    private TableView<Komplain> TABLE_KOMPLAIN;

    @FXML
    private TableColumn<Komplain, Integer> COL_ID_NOTA;

    @FXML
    private TableColumn<Komplain, String> COL_DESKRIPSI;

    @FXML
    private TableColumn<Komplain, String> COL_KOMPENSASI;
    @FXML
    private TableColumn<Komplain, String> COL_VALIDASI;
//    @FXML
//    private TableColumn<Komplain, String> COL_STAFF_PENGURUS;
    ObservableList<Komplain> listKomplain = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        //connect antara kolom dengan class pesanan
        terimaKomplain.setDisable(true);
        tolakKomplain.setDisable(true);
        kompensasi_field.setDisable(true);
        kompensasiLabel.setDisable(true);


        COL_ID_NOTA.setCellValueFactory(new PropertyValueFactory<Komplain, Integer>("ID_Nota"));
        COL_DESKRIPSI.setCellValueFactory(new PropertyValueFactory<Komplain, String>("deskripsi"));
        COL_KOMPENSASI.setCellValueFactory(new PropertyValueFactory<Komplain, String>("kompensasi"));
        COL_VALIDASI.setCellValueFactory(new PropertyValueFactory<Komplain,String>("validasi"));
//        COL_STAFF_PENGURUS.setCellValueFactory(new PropertyValueFactory<Komplain, String>("staffPengurus"));

        komplain.viewListKomplain(listKomplain);
        TABLE_KOMPLAIN.setItems(listKomplain);

    }

    @FXML
    public void tableSelected(){
        tolakKomplain.setDisable(false);
        kompensasi_field.setDisable(false);
        kompensasiLabel.setDisable(false);
    }

    @FXML
    public void openPesanan(){
        HelloApplication app = HelloApplication.getApp();
        Stage mainStage = app.getMainStage();
        Scene scene = app.getMainStaffScene();
        mainStage.setScene(scene);
    }

    @FXML
    public void openStaffList(){
        HelloApplication app = HelloApplication.getApp();
        Stage mainStage = app.getMainStage();
        Scene scene = app.getAdminScene();
        mainStage.setScene(scene);
    }

    @FXML
    public void openStats (){
        HelloApplication app = HelloApplication.getApp();
        Stage mainStage = app.getMainStage();
        Scene scene = app.getStatsScene();
        mainStage.setScene(scene);
    }

    @FXML
    public void terimaAble(){
        terimaKomplain.setDisable(false);
        if (kompensasi_field.getText().equalsIgnoreCase("")){
            terimaKomplain.setDisable(true);
        }
    }

    @FXML
    public void terimaKomplain(){
        int notaId;
        notaId = listKomplain.get(TABLE_KOMPLAIN.getSelectionModel().selectedIndexProperty().get()).getID_Nota();
        komplain.komplainDiterima(kompensasi_field.getText(), notaId);
        listKomplain = FXCollections.observableArrayList();
        komplain.viewListKomplain(listKomplain);
        TABLE_KOMPLAIN.setItems(listKomplain);
        TABLE_KOMPLAIN.refresh();

        terimaKomplain.setDisable(true);
        tolakKomplain.setDisable(true);
        kompensasi_field.setDisable(true);
        kompensasiLabel.setDisable(true);
    }
    @FXML
    public void tolakKomplain(){
        int notaId;
        notaId = listKomplain.get(TABLE_KOMPLAIN.getSelectionModel().selectedIndexProperty().get()).getID_Nota();
        komplain.komplainDitolak(notaId);
        listKomplain = FXCollections.observableArrayList();
        komplain.viewListKomplain(listKomplain);
        TABLE_KOMPLAIN.setItems(listKomplain);
        TABLE_KOMPLAIN.refresh();

        terimaKomplain.setDisable(true);
        tolakKomplain.setDisable(true);
        kompensasi_field.setDisable(true);
        kompensasiLabel.setDisable(true);
    }
}
