package com.example.project_bd;

import javafx.fxml.FXML;

import java.sql.*;

public class jdbcDao {
    String dbname = "project_bd";
    String dbUrl = "jdbc:mysql://localhost/" + dbname;
    String dbUser = "root";
    String dbpass = "";

    public void insertData(String _fullname, String _email,String _password) throws SQLException {
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
    public void viewTotalPenjualan() throws SQLException{
        String Query = "SELECT SUM(total_tagihan) FROM nota";
        String Query2 = "SELECT total_tagihan FROM nota";
        System.out.println("a");
        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbpass);
//            PreparedStatement preparedStatement = connection.prepareStatement(Query);

            Statement statement = connection.createStatement();
            ResultSet rs;
            ResultSet resultSet;
            System.out.println(statement);

            resultSet = statement.executeQuery(Query2);

            while (resultSet.next()){
                System.out.println(resultSet.getDouble("total_tagihan"));
            }


            rs = statement.executeQuery(Query);
            while (rs.next()) {
                Double totalPenghasilan = rs.getDouble("SUM(total_tagihan)");
                System.out.println("Total Penghasilan: Rp. " + totalPenghasilan);
            }

            connection.close();
        } catch (SQLException e){
            System.out.println("Exception: ");
            System.out.println(e.getMessage());
        }
    }
}
