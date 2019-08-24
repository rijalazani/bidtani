package tukangdagang.id.co.tukangdagang_koperasi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelLelang {

    String id;
    String start_time;
    String end_time;
    String created_at;
    String updated_at;
    String petani;
    String nama_barang;
    String harga_awal;
    String qty_barang;
    String satuan_barang;
    String gambar;
    String lat;
    String lon;
    String status;

    @SerializedName("detail")
    private List<Detail> detail;



    //constructor
    public ModelLelang(String id,String start_time,String end_time,String created_at,String updated_at,String petani,String nama_barang,
                       String harga_awal, String qty_barang, String satuan_barang, String gambar, String lat, String lon, String status, List<Detail> detail) {
        this.id = id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.petani = petani;
        this.nama_barang = nama_barang;
        this.harga_awal = harga_awal;
        this.qty_barang = qty_barang;
        this.satuan_barang = satuan_barang;
        this.gambar = gambar;
        this.lat = lat;
        this.lon = lon;
        this.status = status;
        this.detail = detail;

    }

    //getters


    public String getId() {
        return this.id;
    }
    public String getStart_time() {
        return this.start_time;
    }
    public String getEnd_time() {
        return this.end_time;
    }
    public String getCreated_at() {
        return this.created_at;
    }
    public String getUpdated_at() {
        return this.updated_at;
    }
    public String getPetani() {
        return this.petani;
    }
    public String getNama_barang() {
        return this.nama_barang;
    }
    public String getHarga_awal() {
        return this.harga_awal;
    }
    public String getQty_barang() {
        return this.qty_barang;
    }
    public String getSatuan_barang() {
        return this.satuan_barang;
    }
    public String getGambar(){
        return this.gambar;
    }
    public String getLat() {
        return this.lat;
    }
    public String getLon() {
        return this.lon;
    }
    public String getStatus() {
        return this.status;
    }
    public List<Detail> getDetail() {
        return this.detail;
    }


    public static class Detail {
        private String id;
        private String id_transaksi;
        private String agen;
        private String harga_penawaran;


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

        public void setId(String id) {
            this.id = id;
        }
        public void setId_transaksi(String id_transaksi) {
            this.id_transaksi = id_transaksi;
        }
        public void setAgen(String agen) {
            this.agen = agen;
        }
        public void setHarga_penawaran(String harga_penawaran) {
            this.harga_penawaran = harga_penawaran;
        }
    }

}
