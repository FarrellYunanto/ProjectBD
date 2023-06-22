module com.example.projectbd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.project_bd to javafx.fxml;
    exports com.example.project_bd;
    exports com.example.project_bd.staff;
    opens com.example.project_bd.staff to javafx.fxml;
    exports com.example.project_bd.progress.staff;
    opens com.example.project_bd.progress.staff to javafx.fxml;
}