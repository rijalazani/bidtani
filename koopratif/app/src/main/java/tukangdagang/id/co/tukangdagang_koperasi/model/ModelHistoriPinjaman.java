package tukangdagang.id.co.tukangdagang_koperasi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelHistoriPinjaman {
    private String id;
    private String waktu;
    private String pinjaman_deskripsi;
    private String aktif;

    //constructor
    public ModelHistoriPinjaman(String id,String waktu,String pinjaman_deskripsi,String aktif) {
        this.id = id;
        this.waktu = waktu;
        this.pinjaman_deskripsi = pinjaman_deskripsi;
        this.aktif = aktif;
    }

    //getters

    public String getId() {
        return this.id;
    }
    public String getWaktu() {
        return this.waktu;
    }
    public String getPinjaman_deskripsi() {
        return this.pinjaman_deskripsi;
    }
    public String getAktif() {
        return this.aktif;
    }


}
