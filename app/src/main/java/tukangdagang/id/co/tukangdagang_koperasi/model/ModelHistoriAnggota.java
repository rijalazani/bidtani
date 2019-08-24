package tukangdagang.id.co.tukangdagang_koperasi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelHistoriAnggota {
    private String id;
    private String waktu;
    private String pendaftaran_deskripsi;
    private String aktif;

    //constructor
    public ModelHistoriAnggota(String id,String waktu,String pendaftaran_deskripsi,String aktif) {
        this.id = id;
        this.waktu = waktu;
        this.pendaftaran_deskripsi = pendaftaran_deskripsi;
        this.aktif = aktif;
    }

    //getters

    public String getId() {
        return this.id;
    }
    public String getWaktu() {
        return this.waktu;
    }
    public String getPendaftaran_deskripsi() {
        return this.pendaftaran_deskripsi;
    }
    public String getAktif() {
        return this.aktif;
    }


}
