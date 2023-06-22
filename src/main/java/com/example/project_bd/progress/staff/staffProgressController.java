package com.example.project_bd.progress.staff;

import com.example.project_bd.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.example.project_bd.staff.listPesananController;
import java.sql.*;

public class staffProgressController {
    listPesananController controller = new listPesananController();
    @FXML
    private TableColumn<getPesanan, String> colNama;
    @FXML
    private TableColumn <getPesanan, String>colStatus;
    @FXML
    private TableView<getPesanan> pesananTableView;

    @FXML
    private TableColumn<getHistory, Integer> colID;
    @FXML
    private TableColumn <getHistory, String>colDeskripsi;

    @FXML
    private TableColumn <getHistory, String>colWaktu;

    @FXML
    private TableColumn <getHistory, String>colNota;
    @FXML
    private TableColumn <getHistory, String>colJasa;

    @FXML
    private TableView<getHistory> historyTableView;

    @FXML
    private TextField jasa;

    @FXML
    private TextArea desc;



    Alert alert;

    ObservableList<getHistory> dataHistory = FXCollections.observableArrayList();




    public staffProgressController() {
    }


    public ObservableList<getPesanan> dataPesan (){
        String SQL = "SELECT  j.nama_jasa, CASE WHEN d.status = 1 THEN 'SELESAI' WHEN d.status = 0 THEN" +
                " 'BELUM SELESAI' END AS status_pengerjaan, \n" +
                "d.nota_Id, d.jasa_id FROM detail_pesanan d  INNER JOIN jasa j ON d.jasa_id = j.id where d.nota_id = '" + listPesananController.notaIdProgress  + "'";

        ObservableList<getPesanan> dataPesanan = FXCollections.observableArrayList();
        try {

            Connection connect = HelloApplication.createDatabaseConnection();
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

        }catch (Exception e){e.printStackTrace();
        }
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

    @FXML
    public void back (){
        HelloApplication app = HelloApplication.getApp();
        Stage mainStage = app.getMainStage();
        Scene scene = app.getMainStaffScene();
        mainStage.setScene(scene);
    }
    @FXML
    public void addDeleteButtonToTable() {
        TableColumn<getHistory, Void> colBtn = new TableColumn("Delete");

        Callback<TableColumn<getHistory, Void>, TableCell<getHistory, Void>> cellFactory = new Callback<TableColumn<getHistory, Void>, TableCell<getHistory, Void>>() {
            @Override
            public TableCell<getHistory, Void> call(final TableColumn<getHistory, Void> param) {
                final TableCell<getHistory, Void> cell = new TableCell<getHistory, Void>() {

                    private final Button btn = new Button("Delete");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            getHistory selected = getTableView().getItems().get(getIndex());

                            Alert alert = new Alert(Alert.AlertType.NONE, "Hapus history dengan ID_History: " + selected.getId_history() + " dan Jasa_id: " + selected.getJasa_id() + "?", ButtonType.YES, ButtonType.NO);
                            alert.setTitle("Delete Selected");
                            alert.showAndWait();
                            if(alert.getResult() == ButtonType.YES)
                                try {
                                    Connection con = HelloApplication.createDatabaseConnection();
                                    String query = "DELETE FROM `history_detail_pesanan` WHERE id_history = '" + selected.getId_history() + "'";

                                    PreparedStatement preparedStatement = con.prepareStatement(query);
                                    preparedStatement.executeUpdate();
                                    fillDetailTable( selected.getJasa_id());
                                    con.close();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                } catch (ClassNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        historyTableView.getColumns().add(colBtn);

    }

    @FXML
    public void insertBtn(){
        if(jasa.getText().isEmpty() ||
                desc.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("ISI DULU SEMUA TEXT FIELD NYA");
            alert.showAndWait();
        }else {

            try {
                Connection connect = HelloApplication.createDatabaseConnection();
                String masuk = "INSERT INTO history_detail_pesanan( `waktu`, `deskripsi`, `nota_id`, `jasa_id`) VALUES (?,?,?,?)";
                PreparedStatement prepare;
                prepare = connect.prepareStatement(masuk);
                java.util.Date date = new java.util.Date();
                long millis = date.getTime();
                Timestamp timestamp = new Timestamp(millis);
                //untuk mendapatkan tanggal dan waktu saat ini
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                prepare.setString(1, String.valueOf(timestamp));
                prepare.setString(2, desc.getText());
                prepare.setInt(3, listPesananController.notaIdProgress);
                prepare.setInt(4,Integer.parseInt(jasa.getText()));
                prepare.executeUpdate();
                fillDetailTable(Integer.parseInt(jasa.getText()));
                connect.close();


            }catch (Exception e){e.printStackTrace();}
        }
    }


    @FXML
    public void addSelesaiButtonToTable() {
        TableColumn<getPesanan, Void> colBtn = new TableColumn("Selesai");

        Callback<TableColumn<getPesanan, Void>, TableCell<getPesanan, Void>> cellFactory = new Callback<TableColumn<getPesanan, Void>, TableCell<getPesanan, Void>>() {
            @Override
            public TableCell<getPesanan, Void> call(final TableColumn<getPesanan, Void> param) {
                final TableCell<getPesanan, Void> cell = new TableCell<getPesanan, Void>() {

                    private final Button btn = new Button("Selesai");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            getPesanan selected = getTableView().getItems().get(getIndex());

                            Alert alert = new Alert(Alert.AlertType.NONE,  selected.getNama_jasa() + " sudah selesai?", ButtonType.YES, ButtonType.NO);
                            alert.setTitle("Selesai Selected");
                            alert.showAndWait();
                            if(alert.getResult() == ButtonType.YES)
                                try {
                                    Connection con = HelloApplication.createDatabaseConnection();
                                    String query = "UPDATE detail_pesanan SET status= 1  WHERE nota_id  = '" + selected.getNota_id()
                                            + "' and jasa_id ='"+selected.getJasa_id()+"'";

                                    PreparedStatement preparedStatement = con.prepareStatement(query);
                                    preparedStatement.executeUpdate();
                                    PesananShowData();
                                    con.close();
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                } catch (ClassNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        pesananTableView.getColumns().add(colBtn);

    }


   public void fillDetailTable( int js)
    {
        try {
            Connection con = HelloApplication.createDatabaseConnection();
            String query ="SELECT * FROM  history_detail_pesanan WHERE nota_id  = '" + listPesananController.notaIdProgress
                    + "' and jasa_id ='"+js+"'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);


            int column_count = rs.getMetaData().getColumnCount();
            dataHistory.clear();
            if(column_count > 0) // ada data
            {
                while (rs.next())
                {
                    getHistory getH = new getHistory(rs.getInt("id_history"),
                            rs.getTimestamp("waktu"),
                            rs.getString("deskripsi"),
                            rs.getInt("nota_id"),
                            rs.getInt("jasa_id"));

                    dataHistory.add(getH);
                }
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        historyTableView.setItems(dataHistory);
    }




    public void initialize(){
        PesananShowData();
        this.addSelesaiButtonToTable();

        pesananTableView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<getPesanan>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends getPesanan> change) {
                getPesanan selected = pesananTableView.getSelectionModel().getSelectedItem();
                fillDetailTable(selected.getJasa_id());
                System.out.println(selected.getJasa_id());
            }
        });

        this.addDeleteButtonToTable();

        historyTableView.setItems(dataHistory);
        colID.setCellValueFactory(new PropertyValueFactory<>("id_history"));
        colWaktu.setCellValueFactory(new PropertyValueFactory<>("waktu"));
        colDeskripsi.setCellValueFactory(new PropertyValueFactory<>("deskripsi"));
        colNota.setCellValueFactory(new PropertyValueFactory<>("nota_id"));
        colJasa.setCellValueFactory(new PropertyValueFactory<>("jasa_id"));

        System.out.println("dari progress = " + controller.notaIdProgress );

    }
}