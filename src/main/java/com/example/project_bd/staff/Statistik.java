package com.example.project_bd.staff;

import javafx.fxml.FXML;

import java.sql.*;

public class Statistik {
    String dbname = "bd_project";
    String dbUrl = "jdbc:mysql://localhost/" + dbname;
    String dbUser = "root";
    String dbpass = "";

    private int idStaff;
    private String email;
    private String namaLengkap;
    private String noHp;
    private String alamat;

    //Constructor
    public Statistik() {
    }

    public Statistik(int _iD_Staff, String _email, String _namaLengkap, String _noHp, String _alamat) {
        this.idStaff = _iD_Staff;
        this.email = _email;
        this.namaLengkap = _namaLengkap;
        this.noHp = _noHp;
        this.alamat = _alamat;
    }

    //getter setter

    public int getIdStaff() {
        return idStaff;
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


    @FXML
    public String cariBestSeller(){
        String namaJasa = "";
        String Query = """
                SELECT (SELECT nama_jasa FROM jasa WHERE id = jasa_id) AS "namaJasa"\s
                FROM detail_pesanan
                GROUP BY jasa_id
                ORDER BY COUNT(jasa_id) DESC
                LIMIT 1;""";
        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbpass);

            Statement statement = connection.createStatement();
            ResultSet rs;
            rs = statement.executeQuery(Query);

            while (rs.next()) {
                namaJasa = rs.getString("namaJasa");
            }

            connection.close();
        } catch (SQLException e){
            System.out.println("Exception: ");
            System.out.println(e.getMessage());
        }
        return namaJasa;
    }

    @FXML
    public String cariBestCustomer(){
        String namaMember = "";
        String Query = """
                SELECT (SELECT CONCAT(nama_depan, ' ', nama_belakang) FROM member WHERE id = member_id) AS "namaMember"\s
                FROM nota
                GROUP BY member_id
                ORDER BY COUNT(member_id) DESC
                LIMIT 1;""";
        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbpass);

            Statement statement = connection.createStatement();
            ResultSet rs;
            rs = statement.executeQuery(Query);

            while (rs.next()) {
                namaMember = rs.getString("namaMember");
            }

            connection.close();
        } catch (SQLException e){
            System.out.println("Exception: ");
            System.out.println(e.getMessage());
        }
        return namaMember;
    }

    @FXML
    public String cariTotalPenghasilan(){
        String totalPenghasilan = "";
        String Query = "SELECT SUM(jumlah * (SELECT harga FROM jasa WHERE id = dp.jasa_id)) AS \"sum\"\n" +
                "FROM detail_pesanan dp";
        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbpass);

            Statement statement = connection.createStatement();
            ResultSet rs;
            rs = statement.executeQuery(Query);

            while (rs.next()) {
                totalPenghasilan = rs.getString("sum");
            }

            connection.close();
        } catch (SQLException e){
            System.out.println("Exception: ");
            System.out.println(e.getMessage());
        }
        return totalPenghasilan;
    }
}
