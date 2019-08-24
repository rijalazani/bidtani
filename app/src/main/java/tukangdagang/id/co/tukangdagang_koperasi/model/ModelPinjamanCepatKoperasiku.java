package tukangdagang.id.co.tukangdagang_koperasi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelPinjamanCepatKoperasiku {

    String id_koperasi;
    String nama_koperasi;
    String tanggal_diterima;
    String status_anggota_id;
    String status_anggota;
    String minimal_pinjaman;
    String maksimal_pinjaman;
    String alamat_koperasi;

    String tenor;
    String bunga;
    String waktu_proses;
    String url_image;
    String url_syarat_pinjaman;
    @SerializedName("daftar_pinjaman")
    private List<Daftar_pinjaman> daftar_pinjamanList;
    @SerializedName("daftar_waktu_cicilan")
    private List<Daftar_waktucicilan> daftar_waktucicilanList;
    @SerializedName("daftar_durasi_cicilan")
    private List<Daftar_durasicicilan> daftar_durasicicilanList;
    @SerializedName("daftar_agunan")
    private List<Daftar_agunan> daftar_agunanList;


    //constructor
    public ModelPinjamanCepatKoperasiku(String id_koperasi,String tanggal_diterima,String status_anggota_id,String status_anggota, String nama_koperasi,String alamat_koperasi, String minimal_pinjaman, String maksimal_pinjaman, String tenor, String bunga, String waktu_proses, String url_image, String url_syarat_pinjaman, List<Daftar_pinjaman> daftar_pinjamanList, List<Daftar_waktucicilan> daftar_waktucicilanList, List<Daftar_durasicicilan> daftar_durasicicilanList, List<Daftar_agunan> daftar_agunanList) {
        this.id_koperasi = id_koperasi;
        this.nama_koperasi = nama_koperasi;
        this.alamat_koperasi = alamat_koperasi;
        this.tanggal_diterima = tanggal_diterima;
        this.status_anggota_id = status_anggota_id;
        this.status_anggota = status_anggota;
        this.minimal_pinjaman = minimal_pinjaman;
        this.maksimal_pinjaman = maksimal_pinjaman;
        this.tenor = tenor;
        this.bunga = bunga;
        this.waktu_proses = waktu_proses;
        this.url_image = url_image;
        this.url_syarat_pinjaman = url_syarat_pinjaman;
        this.daftar_pinjamanList = daftar_pinjamanList;
        this.daftar_waktucicilanList = daftar_waktucicilanList;
        this.daftar_durasicicilanList = daftar_durasicicilanList;
        this.daftar_agunanList = daftar_agunanList;
    }

    //getters

    public String getId_koperasi() {
        return this.id_koperasi;
    }

    public String getNama_koperasi() {
        return this.nama_koperasi;
    }
    public String getAlamat_koperasi() {
        return this.alamat_koperasi;
    }
    public String getTanggal_diterima() {
        return this.tanggal_diterima;
    }
    public String getStatus_anggota_id() {
        return this.status_anggota_id;
    }
    public String getStatus_anggota() {
        return this.status_anggota;
    }


    public String getMinimal_pinjaman() {
        return this.minimal_pinjaman;
    }
    public String getMaksimal_pinjaman() {
        return this.maksimal_pinjaman;
    }

    public String getTenor() {
        return this.tenor;
    }
    public String getBunga() {
        return this.bunga;
    }
    public String getWaktu_proses() {
        return this.waktu_proses;
    }
    public String getUrl_image() {
        return this.url_image;
    }
    public String getUrl_syarat_pinjam() {
        return this.url_syarat_pinjaman;
    }
    public List<Daftar_pinjaman> getDaftar_pinjamanList() {
        return this.daftar_pinjamanList;
    }
    public List<Daftar_waktucicilan> getDaftar_waktucicilanList() {
        return this.daftar_waktucicilanList;
    }
    public List<Daftar_durasicicilan> getDaftar_durasicicilanList() {
        return this.daftar_durasicicilanList;
    }
    public List<Daftar_agunan> getDaftar_agunanList() {
        return this.daftar_agunanList;
    }

    public static class Daftar_pinjaman {
        private String pinjaman;

        public String getPinjaman() {
            return pinjaman;
        }

        public void setPinjaman(String pinjaman) {
            this.pinjaman = pinjaman;
        }
    }

    public static class Daftar_waktucicilan {
        private String waktu_cicilan;

        public String getWaktu_cicilan() {
            return waktu_cicilan;
        }

        public void setWaktu_cicilan(String waktu_cicilan) {
            this.waktu_cicilan = waktu_cicilan;
        }
    }

    public static class Daftar_durasicicilan {
        private String durasi_cicilan;

        public String getDurasi_cicilan() {
            return durasi_cicilan;
        }

        public void setDurasi_cicilan(String durasi_cicilan) {
            this.durasi_cicilan = durasi_cicilan;
        }
    }

    public static class Daftar_agunan {
        private String agunan;

        public String getAgunan() {
            return agunan;
        }

        public void setAgunan(String agunan) {
            this.agunan = agunan;
        }
    }
}
