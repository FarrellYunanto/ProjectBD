package com.example.project_bd.staff;

import com.example.project_bd.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class listPesananController {
    public static int notaIdProgress;
    //Attribute
    @FXML
    Button progressButton;
    Pesanan pesanan = new Pesanan();
    //Tabel
    @FXML
    private TableView<Pesanan> TABLE_PESANAN;

    @FXML
    private TableColumn<Pesanan, Integer> COL_ID_NOTA;

    @FXML
    private TableColumn<Pesanan, String> COL_STATUS;

    @FXML
    private TableColumn<Pesanan, String> COL_JASA;
    @FXML
    private TableColumn<Pesanan, Integer> COL_JUMLAH;
    ObservableList<Pesanan> listPesanan = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        //connect antara kolom dengan class pesanan
        progressButton.setDisable(true);
        COL_ID_NOTA.setCellValueFactory(new PropertyValueFactory<Pesanan, Integer>("ID_Nota"));
        COL_STATUS.setCellValueFactory(new PropertyValueFactory<Pesanan, String>("status"));
        COL_JASA.setCellValueFactory(new PropertyValueFactory<Pesanan, String>("jasa"));
        COL_JUMLAH.setCellValueFactory(new PropertyValueFactory<Pesanan, Integer>("jumlah"));


        pesanan.viewListPesanan(listPesanan);
        TABLE_PESANAN.setItems(listPesanan);
    }

    @FXML
    public void tableSelected(){
        progressButton.setDisable(false);
        notaIdProgress = listPesanan.get(TABLE_PESANAN.getSelectionModel().selectedIndexProperty().get()).getID_Nota();
        Pesanan selectedItem = TABLE_PESANAN.getSelectionModel().getSelectedItem();
        notaIdProgress = selectedItem.getID_Nota();
        System.out.println(notaIdProgress);
    }

    @FXML
    public void openProgress(){

    }

    @FXML
    public void openKomplain(){
        HelloApplication app = HelloApplication.getApp();
        Stage mainStage = app.getMainStage();
        Scene scene = app.getKomplainScene();
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
    public void openStaffProgress (){
        HelloApplication app = HelloApplication.getApp();
        Stage mainStage = app.getMainStage();
        Scene scene = app.getProgressStaffScene();
        mainStage.setScene(scene);
        HelloApplication.getApp().getStaffProgressController().initialize();
    }
}