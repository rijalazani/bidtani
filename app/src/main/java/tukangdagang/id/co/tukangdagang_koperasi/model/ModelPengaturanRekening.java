package tukangdagang.id.co.tukangdagang_koperasi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelPengaturanRekening {
    String id;
    String bank_id;
    String nama_pemilik;
    String nama_bank;
    String no_rekening;
    String is_utama;
    String update_at;



    public String getId() {
        return this.id;
    }
    public String getBank_id() {
        return this.bank_id;
    }
    public String getNama_pemilik() {
        return this.nama_pemilik;
    }
    public String getNama_bank() {
        return this.nama_bank;
    }
    public String getNo_rekening() {
        return this.no_rekening;
    }
    public String getIs_utama() {
        return this.is_utama;
    }
    public String getUpdate_at() {
        return this.update_at;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }
    public void setNama_pemilik(String nama_pemilik) {
        this.nama_pemilik = nama_pemilik;
    }
    public void setNama_bank(String nama_bank) {
        this.nama_bank = nama_bank;
    }
    public void setNo_rekening(String no_rekening) {
        this.no_rekening = no_rekening;
    }
    public void setIs_utama(String is_utama) {
        this.is_utama = is_utama;
    }
    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

}
