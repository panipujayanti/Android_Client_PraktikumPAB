package com.praktikumpab.android_client;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private int id;

    @SerializedName("NIM")
    private String NIM;
    @SerializedName("name")
    private String name;

    @SerializedName("progstud")
    private String progstud;

    @SerializedName("email")
    private String email;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("tanggal_lahir")
    private String tanggal_lahir;

    @SerializedName("jenis_kelamin")
    private String jenis_kelamin;

    // Konstruktor untuk membuat objek User baru
    public User(int id, String NIM, String name, String progstud, String email, String alamat, String tanggal_lahir, String jenis_kelamin) {
        this.id = id;
        this.NIM = NIM;
        this.name = name;
        this.progstud = progstud;
        this.email = email;
        this.alamat = alamat;
        this.tanggal_lahir = tanggal_lahir;
        this.jenis_kelamin = jenis_kelamin;
    }

    // Konstruktor untuk membuat objek User tanpa id (misalnya, untuk menambahkan user baru)
    public User( String NIM, String name, String progstud, String email, String alamat, String tanggal_lahir, String jenis_kelamin) {
        this.NIM = NIM;
        this.name = name;
        this.progstud = progstud;
        this.email = email;
        this.alamat = alamat;
        this.tanggal_lahir = tanggal_lahir;
        this.jenis_kelamin = jenis_kelamin;
    }

    // Getter untuk mendapatkan id user
    public int getId() {
        return id;
    }

    // Setter untuk mengatur id user
    public void setId(int id) {
        this.id = id;
    }

    // Getter untuk mendapatkan nama user
    public String getNIM() {
        return NIM;
    }

    // Setter untuk mengatur nama user
    public void setNIM(String NIM) {
        this.NIM = NIM;
    }

    // Getter untuk mendapatkan nama user
    public String getName() {
        return name;
    }

    // Setter untuk mengatur nama user
    public void setName(String name) {
        this.name = name;
    }

    // Getter untuk mendapatkan nama user
    public String getProgstud() {
        return progstud;
    }

    // Setter untuk mengatur nama user
    public void setProgstud(String progstud) {
        this.progstud = progstud;
    }

    // Getter untuk mendapatkan email user
    public String getEmail() {
        return email;
    }

    // Setter untuk mengatur email user
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter untuk mendapatkan nama user
    public String getAlamat() {
        return alamat;
    }

    // Setter untuk mengatur nama user
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    // Getter untuk mendapatkan nama user
    public String getTanggalLahir() {
        return tanggal_lahir;
    }

    // Setter untuk mengatur nama user
    public void setTanggalLahir(String tanggalLahir) {
        this.tanggal_lahir = tanggalLahir;
    }

    // Getter untuk mendapatkan nama user
    public String getJenisKelamin() {
        return jenis_kelamin;
    }

    // Setter untuk mengatur nama user
    public void setJenisKelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }
}