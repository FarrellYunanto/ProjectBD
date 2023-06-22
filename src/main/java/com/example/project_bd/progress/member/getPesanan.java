package com.example.project_bd.progress.member;

public class getPesanan {
    private String nama_jasa;
    private Integer nota_id;
    private Integer jasa_id;
    private String status;

    public getPesanan(String nama_jasa, String status, Integer nota_id, Integer jasa_id) {
        this.nama_jasa = nama_jasa;
        this.status = status;
        this.nota_id = nota_id;
        this.jasa_id = jasa_id;
    }

    public String getNama_jasa() {
        return nama_jasa;
    }

    public Integer getNota_id() {
        return nota_id;
    }

    public Integer getJasa_id() {
        return jasa_id;
    }

    public String getStatus() {
        return status;
    }
}
