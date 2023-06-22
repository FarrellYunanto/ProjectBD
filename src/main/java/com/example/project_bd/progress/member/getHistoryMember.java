package com.example.project_bd.progress.member;

import java.sql.Timestamp;

public class getHistoryMember {

    private Timestamp waktu;
    private String deskripsi;

    public getHistoryMember(Timestamp waktu, String deskripsi) {
        this.waktu = waktu;
        this.deskripsi = deskripsi;
    }

    public Timestamp getWaktu() {
        return waktu;
    }

    public String getDeskripsi() {
        return deskripsi;
    }
}
