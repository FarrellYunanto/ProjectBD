package com.example.project_bd.staff;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.*;

public class Pesanan {
    String dbname = "bd_project";
    String dbUrl = "jdbc:mysql://localhost/" + dbname;
    String dbUser = "root";
    String dbpass = "";

    private int ID_Nota;
    private String status;
    private String jasa;
    private int jumlah;

    //Constructor
    public Pesanan() {
    }

    public Pesanan(int _ID_Nota, String _status, String _jasa, int _jumlah) {
        ID_Nota = _ID_Nota;
        status = _status;
        jasa = _jasa;
        jumlah = _jumlah;
    }

    //getter setter
    public int getID_Nota() {
        return ID_Nota;
    }

    public String getStatus() {
        return status;
    }

    public String getJasa() {
        return jasa;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void insertData(String _fullname, String _email, String _password) throws SQLException {
        String Query = "INSERT INTO registrasi (full_name, email_id, password) VALUES (?, ?, ?)";

        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbpass);
            PreparedStatement preparedStatement = connection.prepareStatement(Query);
            preparedStatement.setString(1, _fullname);
            preparedStatement.setString(2, _email);
            preparedStatement.setString(3, _password);

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void viewListPesanan(ObservableList<Pesanan> _pesanan){
        String jenisStatus;
        String Query = "SELECT nota_id, jasa_id, status, jumlah, (SELECT nama_jasa FROM jasa WHERE id = p.jasa_id) AS " + "nama_jasa" + " FROM detail_pesanan p";
        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbpass);

            Statement statement = connection.createStatement();
            ResultSet rs;
            rs = statement.executeQuery(Query);

            while (rs.next()) {
                if (rs.getInt("status") == 0){
                    jenisStatus = "Unfinished";
                } else {
                    jenisStatus = "Finished";
                }
                _pesanan.add(new Pesanan(rs.getInt("nota_id"), jenisStatus, rs.getString("nama_jasa")
                , rs.getInt("jumlah")));
            }

            connection.close();
        } catch (SQLException e){
            System.out.println("Exception: ");
            System.out.println(e.getMessage());
        }
    }
}
