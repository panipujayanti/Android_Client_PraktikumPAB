package com.praktikumpab.android_client;

public class User {
    private int id;
    private String NIM;
    private String name;
    private String progstud;
    private String email;
    private String alamat;
    private String tanggal_lahir;
    private String jenis_kelamin;

    public User(String NIM, String name, String progstud, String email, String alamat, String tanggal_lahir, String jenis_kelamin) {
        this.NIM = NIM;
        this.name = name;
        this.progstud = progstud;
        this.email = email;
        this.alamat = alamat;
        this.tanggal_lahir = tanggal_lahir;
        this.jenis_kelamin = jenis_kelamin;
    }

    public int getId() { return id; }
    public String getNIM() { return  NIM; }

    public String getName() { return name; }
    public String getProgstud() { return progstud; }
    public String getEmail() { return email; }
    public String getAlamat() { return alamat; }
    public String getTanggalLahir() { return tanggal_lahir; }
    public String getJenisKelamin() { return jenis_kelamin; }
}
