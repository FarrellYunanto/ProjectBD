package com.example.project_bd.staff;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.*;

public class Admin {
    String dbname = "bd_project";
    String dbUrl = "jdbc:mysql://localhost/" + dbname;
    String dbUser = "root";
    String dbpass = "";

    private int id;
    private String email;
    private String namaLengkap;
    private String noHp;
    private String alamat;
    private String password;

    //Constructor
    public Admin() {
    }

    public Admin(int _iD_Staff, String _email, String _namaLengkap, String _noHp, String _alamat, String _password) {
        this.id = _iD_Staff;
        this.email = _email;
        this.namaLengkap = _namaLengkap;
        this.noHp = _noHp;
        this.alamat = _alamat;
        this.password = _password;
    }

    //getter setter

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public String getNoHp() {
        return noHp;
    }

    public String getAlamat() {
        return alamat;
    }
    public String getPassword(){return password;}

    public void deleteData(int _id){
        String Query = "DELETE FROM staff WHERE id = " + _id;
        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbpass);
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void updateData(String _email, String _namaDepan,
                           String _namaBelakang, String _noHp, String _alamat, int _id, String _password){
        String Query = "UPDATE staff set email = '" +_email +
                "', nama_depan = '" + _namaDepan +
                "', nama_belakang = '" + _namaBelakang +
                "', no_hp = '" +_noHp +
                "', alamat = '" + _alamat +
                "', password = '" + _password +
                "'WHERE id = " +_id;
        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbpass);

            Statement statement = connection.createStatement();
            statement.executeUpdate(Query);

            connection.close();
        } catch (SQLException e){
            System.out.println("Exception: ");
            System.out.println(e.getMessage());
        }
    }

    @FXML
    protected void insertStaff(String _email, String _namaDepan, String _namaBelakang,
    String _noHp, String _alamat, String _password){
        String Query = "INSERT INTO staff (email, nama_depan, nama_belakang, no_hp, alamat, password) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbpass);
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setString(1, _email);
            preparedStatement.setString(2, _namaDepan);
            preparedStatement.setString(3, _namaBelakang);
            preparedStatement.setString(4, _noHp);
            preparedStatement.setString(5, _alamat);
            preparedStatement.setString(6, _password);

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void viewListStaff (ObservableList<Admin> _admin){
        String Query = "SELECT id, email, CONCAT(nama_depan, ' ', nama_belakang) AS \"namaFull\"" +
                ", no_hp, alamat, password FROM staff";
        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbpass);

            Statement statement = connection.createStatement();
            ResultSet rs;
            rs = statement.executeQuery(Query);

            while (rs.next()) {
                _admin.add(new Admin(rs.getInt("id"), rs.getString("email"),
                        rs.getString("namaFull"),
                        rs.getString("no_hp"),
                        rs.getString("alamat"),
                        rs.getString("password")));
            }

            connection.close();
        } catch (SQLException e){
            System.out.println("Exception: ");
            System.out.println(e.getMessage());
        }
    }
}
