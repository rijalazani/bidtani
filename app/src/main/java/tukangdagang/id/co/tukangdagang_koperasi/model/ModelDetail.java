package tukangdagang.id.co.tukangdagang_koperasi.model;

import android.graphics.drawable.Drawable;

public class ModelDetail {

    private String id;
    private String id_transaksi;
    private String agen;
    private String harga_penawaran;

    public ModelDetail(String id, String agen, String harga_penawaran, String id_transaksi) {
        this.id = id;
        this.agen = agen;
        this.harga_penawaran = harga_penawaran;
        this.id_transaksi = id_transaksi;
    }


    public String getId() {
        return id;
    }
    public String getId_transaksi() {
        return id_transaksi;
    }
    public String getAgen() {
        return agen;
    }
    public String getHarga_penawaran() {
        return harga_penawaran;
    }

}
