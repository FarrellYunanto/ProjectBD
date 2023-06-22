package com.example.project_bd;

import com.example.project_bd.staff.AdminController;
import com.example.project_bd.staff.KomplainController;
import com.example.project_bd.staff.StatistikControl;
import com.example.project_bd.progress.staff.*;
import com.example.project_bd.progress.member.*;
import com.example.project_bd.staff.listPesananController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HelloApplication extends Application {
    private Scene mainStaffScene;
    private com.example.project_bd.staff.listPesananController listPesananController;
    private Scene komplainScene;
    private KomplainController komplainController;
    private Scene adminScene;
    private AdminController adminController;
    private Scene statsScene;
    private StatistikControl statistikControl;
    private Scene progressStaffScene;
    private staffProgressController staffProgressController;
    private static HelloApplication app;
    private Stage mainStage;

    //Getter

    public static HelloApplication getApp() {
        return app;
    }

    public Scene getMainStaffScene() {
        return mainStaffScene;
    }

    public Scene getKomplainScene() {
        return komplainScene;
    }

    public Scene getAdminScene() {
        return adminScene;
    }

    public Scene getStatsScene() {
        return statsScene;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public Scene getProgressStaffScene() {
        return progressStaffScene;
    }

    public com.example.project_bd.progress.staff.staffProgressController getStaffProgressController() {
        return staffProgressController;
    }

    @Override
    public void start(Stage stage) throws IOException {
        app = this;
        mainStage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("staff/pesanan.fxml"));
        mainStaffScene = new Scene(fxmlLoader.load());
        listPesananController = fxmlLoader.getController();

        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("staff/komplain.fxml"));
        komplainScene = new Scene(fxmlLoader.load());
        komplainController = fxmlLoader.getController();

        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("staff/adminPage.fxml"));
        adminScene = new Scene(fxmlLoader.load());
        adminController = fxmlLoader.getController();

        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("staff/statistik.fxml"));
        statsScene = new Scene(fxmlLoader.load());
        statistikControl = fxmlLoader.getController();

        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("progress/staff/staffProgress.fxml"));
        progressStaffScene = new Scene(fxmlLoader.load());
        staffProgressController = fxmlLoader.getController();


        staffProgressController staff = (staffProgressController) fxmlLoader.getController();



        stage.setTitle("Hello!");
        stage.setScene(mainStaffScene);
        staff.initialize();
        stage.show();
    }
    public static Connection createDatabaseConnection() throws ClassNotFoundException, SQLException {
        String dbname = "bd_project";
        String url = "jdbc:mysql://localhost/" + dbname;
        String user = "root";
        String password = "";
        String driver = "com.mysql.cj.jdbc.Driver";

        Class.forName(driver);
        Connection con = DriverManager.getConnection(url, user, password);
        return con;
    }

    public static void main(String[] args) {
        launch();
    }
}