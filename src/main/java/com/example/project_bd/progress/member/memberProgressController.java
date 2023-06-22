package com.example.project_bd.progress.member;

import com.example.project_bd.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.*;



public class memberProgressController {
    String dbname = "bd_project";
    String dbUrl = "jdbc:mysql://localhost/" + dbname;
    String dbUser = "root";
    String dbpass = "";

    @FXML
    private TableColumn<getPesanan, String> colNama;
    @FXML
    private TableColumn <getPesanan, String>colStatus;
    @FXML
    private TableView<getPesanan> pesananTableView;

    @FXML
    private TableColumn <getHistoryMember, String>colDeskripsi;

    @FXML
    private TableColumn <getHistoryMember, String>colWaktu;

    @FXML
    private TableView<getHistoryMember> historyTableView;

    ObservableList<getHistoryMember> dataHistory = FXCollections.observableArrayList();


    int nt = 1;
    Alert alert;


    public memberProgressController() {
    }


    public ObservableList<getPesanan> dataPesan (){
        String SQL = "SELECT  j.nama_jasa, CASE WHEN d.status = 1 THEN 'SELESAI' WHEN d.status = 0 THEN" +
                " 'BELUM SELESAI' END AS status_pengerjaan, \n" +
                "d.nota_Id, d.jasa_id FROM detail_pesanan d  INNER JOIN jasa j ON d.jasa_id = j.id where d.nota_id = '" + nt + "'";

        ObservableList<getPesanan> dataPesanan = FXCollections.observableArrayList();
        try {

            Connection connect = DriverManager.getConnection(dbUrl, dbUser, dbpass);
            Statement statement = connect.createStatement();
            ResultSet result = statement.executeQuery(SQL);
            getPesanan getP;
            int column_count = result.getMetaData().getColumnCount();
            if(column_count > 0) // ada data
            {
                while (result.next()){
                    getP = new getPesanan(result.getString("nama_jasa"),
                            result.getString("status_pengerjaan"),
                            result.getInt("nota_id"),
                            result.getInt("jasa_id"));

                    dataPesanan.add(getP); }
                connect.close();
            }

        }catch (Exception e){e.printStackTrace();}
        return dataPesanan;
    }

    private ObservableList<getPesanan> pesananListData;
    //UNTUK MENUNJUKKAN DATA DI TABLEVIEW
    public void PesananShowData(){
        pesananListData =  dataPesan();

        colNama.setCellValueFactory(new PropertyValueFactory<>("nama_jasa"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));


        pesananTableView.setItems(pesananListData);

    }

    public void fillDetailTable( int js)
    {
        try {
            Connection con = DriverManager.getConnection(dbUrl, dbUser, dbpass);
            String query ="SELECT waktu, deskripsi FROM  history_detail_pesanan WHERE nota_id  = '" + nt
                    + "' and jasa_id ='"+js+"'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);


            int column_count = rs.getMetaData().getColumnCount();
            dataHistory.clear();
            if(column_count > 0) // ada data
            {
                while (rs.next())
                {
                    getHistoryMember getH = new getHistoryMember(rs.getTimestamp("waktu"),
                            rs.getString("deskripsi"));

                    dataHistory.add(getH);
                }
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        historyTableView.setItems(dataHistory);
    }






    public void Initialize(){
        PesananShowData();


        pesananTableView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<getPesanan>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends getPesanan> change) {
                getPesanan selected = pesananTableView.getSelectionModel().getSelectedItem();
              fillDetailTable(selected.getJasa_id());

            }
        });
        historyTableView.setItems(dataHistory);
        colWaktu.setCellValueFactory(new PropertyValueFactory<>("waktu"));
        colDeskripsi.setCellValueFactory(new PropertyValueFactory<>("deskripsi"));



    }

}