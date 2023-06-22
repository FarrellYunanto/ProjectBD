package com.example.project_bd.staff;

import com.example.project_bd.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;

public class AdminController {

    //Attribute
    //Fields
    @FXML
    TextField emailField;
    @FXML
    TextField namaDepanField;
    @FXML
    TextField namaBelakangField;
    @FXML
    TextField noHpField;
    @FXML
    TextField alamatField;
    @FXML
    TextField passwordField;

    //Buttons
    @FXML
    Button deleteButton;
    @FXML
    Button updateButton;
    @FXML
    Button insertButton;
    @FXML
    Button clearButton;
    Admin admin = new Admin();
    //FXML
    //Tabel
    @FXML
    private TableView<Admin> TABLE_STAFF;

    @FXML
    private TableColumn<Admin, Integer> COL_ID_STAFF;

    @FXML
    private TableColumn<Admin, String> COL_EMAIL;

    @FXML
    private TableColumn<Admin, String> COL_NAMA;
    @FXML
    private TableColumn<Admin, String> COL_HP;
    @FXML
    private TableColumn<Admin, String> COL_ALAMAT;
    @FXML
    private TableColumn<Admin, String> COL_PASSWORD;

    ObservableList<Admin> listStaff = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        //connect antara kolom dengan class pesanan
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
        clearButton.setDisable(true);
        insertButton.setDisable(false);

        COL_ID_STAFF.setCellValueFactory(new PropertyValueFactory<Admin, Integer>("id"));
        COL_EMAIL.setCellValueFactory(new PropertyValueFactory<Admin, String>("email"));
        COL_NAMA.setCellValueFactory(new PropertyValueFactory<Admin, String>("namaLengkap"));
        COL_HP.setCellValueFactory(new PropertyValueFactory<Admin,String>("noHp"));
        COL_ALAMAT.setCellValueFactory(new PropertyValueFactory<Admin, String>("alamat"));
        COL_PASSWORD.setCellValueFactory(new PropertyValueFactory<Admin, String>("password"));

        admin.viewListStaff(listStaff);
        TABLE_STAFF.setItems(listStaff);

    }

    @FXML
    public void tableSelected(){
        clearButton.setDisable(false);
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
        insertButton.setDisable(true);

        String namaLengkap[] = listStaff.get(TABLE_STAFF.getSelectionModel().selectedIndexProperty().get()).getNamaLengkap().split( " ");
        String namaDepan = namaLengkap[0];
        String namaBelakang = "";
        if (namaLengkap.length > 1){
            namaBelakang = namaLengkap[1];
        }
        emailField.setText(listStaff.get(TABLE_STAFF.getSelectionModel().selectedIndexProperty().get()).getEmail());
        namaDepanField.setText(namaDepan);
        namaBelakangField.setText(namaBelakang);
        noHpField.setText(listStaff.get(TABLE_STAFF.getSelectionModel().selectedIndexProperty().get()).getNoHp());
        alamatField.setText(listStaff.get(TABLE_STAFF.getSelectionModel().selectedIndexProperty().get()).getAlamat());
        passwordField.setText(listStaff.get(TABLE_STAFF.getSelectionModel().selectedIndexProperty().get()).getPassword());
    }

    @FXML
    public void openKomplain(){
        HelloApplication app = HelloApplication.getApp();
        Stage mainStage = app.getMainStage();
        Scene scene = app.getKomplainScene();
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
    public void openPesanan(){
        HelloApplication app = HelloApplication.getApp();
        Stage mainStage = app.getMainStage();
        Scene scene = app.getMainStaffScene();
        mainStage.setScene(scene);
    }
    @FXML
    public void deleteClicked(){
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
        insertButton.setDisable(false);

        admin.deleteData(listStaff.get(TABLE_STAFF.getSelectionModel().selectedIndexProperty().get()).getId());
        listStaff = FXCollections.observableArrayList();
        admin.viewListStaff(listStaff);
        TABLE_STAFF.setItems(listStaff);
        TABLE_STAFF.refresh();

        emailField.clear();
        namaDepanField.clear();
        namaBelakangField.clear();
        noHpField.clear();
        alamatField.clear();
        passwordField.clear();
    }

    @FXML
    public void updateClicked(){
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
        insertButton.setDisable(false);

        Window owner = updateButton.getScene().getWindow();
        if (emailField.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, owner, "FORM ERROR!!",
                    "EMAIL BELUM TERISI");
            return;
        }

        if (namaDepanField.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, owner, "FORM ERROR!!",
                    "NAMA DEPAN BELUM TERISI");
            return;
        }

        if (passwordField.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, owner, "FORM ERROR!!",
                    "PASSWORD BELUM TERISI");
            return;
        }

        admin.updateData(emailField.getText(),
                namaDepanField.getText(),
                namaBelakangField.getText(),
                noHpField.getText(),
                alamatField.getText(),
                listStaff.get(TABLE_STAFF.getSelectionModel().selectedIndexProperty().get()).getId(),
                passwordField.getText());

        listStaff = FXCollections.observableArrayList();
        admin.viewListStaff(listStaff);
        TABLE_STAFF.setItems(listStaff);
        TABLE_STAFF.refresh();

        emailField.clear();
        namaDepanField.clear();
        namaBelakangField.clear();
        noHpField.clear();
        alamatField.clear();
        passwordField.clear();
    }

    @FXML
    public void insertClicked(){
        Window owner = updateButton.getScene().getWindow();
        if (emailField.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, owner, "FORM ERROR!!",
                    "EMAIL BELUM TERISI");
            return;
        }

        if (namaDepanField.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, owner, "FORM ERROR!!",
                    "NAMA DEPAN BELUM TERISI");
            return;
        }

        if (noHpField.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, owner, "FORM ERROR!!",
                    "NO HP BELUM TERISI");
            return;
        }

        if (alamatField.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, owner, "FORM ERROR!!",
                    "ALAMAT BELUM TERISI");
            return;
        }

        if (passwordField.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, owner, "FORM ERROR!!",
                    "PASSWORD BELUM TERISI");
            return;
        }
        admin.insertStaff(emailField.getText(),
                namaDepanField.getText(),
                namaBelakangField.getText(),
                noHpField.getText(),
                alamatField.getText(),
                passwordField.getText());

        emailField.clear();
        namaDepanField.clear();
        namaBelakangField.clear();
        noHpField.clear();
        alamatField.clear();
        passwordField.clear();

        listStaff = FXCollections.observableArrayList();
        admin.viewListStaff(listStaff);
        TABLE_STAFF.setItems(listStaff);
        TABLE_STAFF.refresh();
    }

    public void clearClicked(){
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
        clearButton.setDisable(true);
        insertButton.setDisable(false);

        emailField.clear();
        namaDepanField.clear();
        namaBelakangField.clear();
        noHpField.clear();
        alamatField.clear();
        passwordField.clear();
    }

    public static void showAlert(Alert.AlertType _alertType, Window _owner,
                                 String _title, String _message){
        Alert alert = new Alert(_alertType);
        alert.setTitle(_title);
        alert.setHeaderText(null);
        alert.setContentText(_message);
        alert.initOwner(_owner);
        alert.show();
    }

}
