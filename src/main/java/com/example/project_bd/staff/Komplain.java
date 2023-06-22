package com.example.project_bd.staff;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.*;

public class Komplain {
    String dbname = "bd_project";
    String dbUrl = "jdbc:mysql://localhost/" + dbname;
    String dbUser = "root";
    String dbpass = "";

    private int ID_Nota;
    private String deskripsi;
    private String kompensasi;
    private String validasi;
    private String staffPengurus;

    //Constructor
    public Komplain() {
    }

    public Komplain(int _ID_Nota, String _deskripsi, String _kompensasi, String _validasi) {
        this.ID_Nota = _ID_Nota;
        this.deskripsi = _deskripsi;
        this.kompensasi = _kompensasi;
        this.validasi = _validasi;
//        this.staffPengurus = _staffPengurus;
    }

    //getter setter
    public int getID_Nota() {
        return ID_Nota;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getKompensasi() {
        return kompensasi;
    }

    public String getValidasi() {
        return validasi;
    }

    public String getStaffPengurus() {
        return staffPengurus;
    }

    @FXML
    public void viewListKomplain(ObservableList<Komplain> _komplain) {
        String _validasi;
        String Query = "SELECT nota_id, deskripsi, kompensasi, validasi " +
                "FROM komplain;";
        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbpass);

            Statement statement = connection.createStatement();
            ResultSet rs;
            rs = statement.executeQuery(Query);

            while (rs.next()) {

                switch (rs.getInt("validasi")){
                    case 1:
                        _validasi = "Diterima";
                        break;
                    case 2:
                        _validasi = "Ditolak";
                        break;
                    default:
                        _validasi = "Proses";
                }
                _komplain.add(new Komplain(rs.getInt("nota_id"), rs.getString("deskripsi"),
                        rs.getString("kompensasi"), _validasi));
            }

            connection.close();
        } catch (SQLException e) {
            System.out.println("Exception: ");
            System.out.println(e.getMessage());
        }
    }

    public void komplainDitolak(int _idNota) {
        String Query = "UPDATE komplain SET validasi = 2 WHERE nota_id = " + _idNota;

        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbpass);
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.executeUpdate();

            Query = "UPDATE komplain SET kompensasi = \" - \" " +
                    "WHERE nota_id = " + _idNota;
            preparedStatement = connection.prepareStatement(Query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void komplainDiterima(String _kompensasi, int _idNota) {
        String Query = "UPDATE komplain SET validasi = 1 WHERE nota_id = " + _idNota;

        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbpass);
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.executeUpdate();

            Query = "UPDATE komplain SET kompensasi = (?) " +
                    "WHERE nota_id = " + _idNota;
            preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setString(1, _kompensasi);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
