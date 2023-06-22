package com.example.project_bd.staff;

import com.example.project_bd.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StatistikControl {
    @FXML
    Label jasaBestSeller;
    @FXML
    Label bestCustomer;
    @FXML
    Label totalPenghasilan;
    Statistik stats = new Statistik();

    @FXML
    public void initialize(){
        jasaBestSeller.setText(stats.cariBestSeller());
        bestCustomer.setText(stats.cariBestCustomer());
        totalPenghasilan.setText("Rp. " + stats.cariTotalPenghasilan());
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
    public void openPesanan(){
        HelloApplication app = HelloApplication.getApp();
        Stage mainStage = app.getMainStage();
        Scene scene = app.getMainStaffScene();
        mainStage.setScene(scene);
    }



}
