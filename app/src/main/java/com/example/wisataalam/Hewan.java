package com.example.wisataalam;

public class Hewan {
    private String nama;
    private String jenis;
    private String asal;
    private String deskripsi;
    private int imageResId; // ID resource gambar

    public Hewan(String nama, String jenis, String asal, String deskripsi, int imageResId) {
        this.nama = nama;
        this.jenis = jenis;
        this.asal = asal;
        this.deskripsi = deskripsi;
        this.imageResId = imageResId;
    }

    // Getter dan Setter
    public String getNama() { return nama; }
    public String getJenis() { return jenis; }
    public String getAsal() { return asal; }
    public String getDeskripsi() { return deskripsi; }
    public int getImageResId() { return imageResId; }
}
