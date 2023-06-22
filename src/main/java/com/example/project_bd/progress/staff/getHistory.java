package com.example.project_bd.progress.staff;

import java.sql.Timestamp;

public class getHistory {
    private Integer id_history;
    private Timestamp waktu;
    private String deskripsi;
    private Integer nota_id;
    private Integer jasa_id;

    public getHistory(Integer id_history, Timestamp waktu, String deskripsi,
                      Integer nota_id, Integer jasa_id){
        this.id_history = id_history;
        this.waktu = waktu;
        this.deskripsi = deskripsi;
        this.nota_id = nota_id;
        this.jasa_id = jasa_id;
    }

    public Integer getId_history() {
        return id_history;
    }

    public Timestamp getWaktu() {
        return waktu;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public Integer getNota_id() {
        return nota_id;
    }

    public Integer getJasa_id() {
        return jasa_id;
    }
}
