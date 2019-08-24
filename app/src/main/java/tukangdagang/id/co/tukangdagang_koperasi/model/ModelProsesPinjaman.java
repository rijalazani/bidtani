package tukangdagang.id.co.tukangdagang_koperasi.model;
public class ModelProsesPinjaman {

    String url_alasan;
    String id_koperasi;
    String id_pengajuan;
    String nama_koperasi;
    String url_image;
    String url_rincian_pinjaman;
    String total_pinjaman;
    String flag_nama;
    String user_id;
    String tanggal_diajukan;
    String tanggal_diterima;
    String tanggal_ditolak;
    String diterima_oleh;
    String ditolak_oleh;
    String lat_koperasi;
    String lng_koperasi;
    String no_telepon;


    //constructor
    public ModelProsesPinjaman(String url_alasan,String id_koperasi,String id_pengajuan, String nama_koperasi,
                              String url_image,String url_rincian_pinjaman, String total_pinjaman,
                              String flag_nama, String user_id,String tanggal_diajukan,
                               String tanggal_diterima, String tanggal_ditolak,
                               String diterima_oleh, String ditolak_oleh,String lat_koperasi,String lng_koperasi,String no_telepon) {

        this.url_alasan = url_alasan;
        this.id_koperasi = id_koperasi;
        this.id_pengajuan = id_pengajuan;
        this.nama_koperasi = nama_koperasi;
        this.url_image = url_image;
        this.url_rincian_pinjaman = url_rincian_pinjaman;
        this.total_pinjaman = total_pinjaman;
        this.flag_nama = flag_nama;
        this.user_id = user_id;
        this.tanggal_diajukan = tanggal_diajukan;
        this.tanggal_diterima = tanggal_diterima;
        this.tanggal_ditolak = tanggal_ditolak;
        this.diterima_oleh = diterima_oleh;
        this.ditolak_oleh = ditolak_oleh;
        this.lat_koperasi = lat_koperasi;
        this.lng_koperasi = lng_koperasi;
        this.no_telepon = no_telepon;
    }


//getters

    public String getUrl_alasan() {
        return this.url_alasan;
    }
    public String getId_koperasi() {
        return this.id_koperasi;
    }
    public String getId_pengajuan() {
        return this.id_pengajuan;
    }
    public String getNama_koperasi() {
        return this.nama_koperasi;
    }
    public String getUrl_image() {
        return this.url_image;
    }
    public String getUrl_rincian_pinjaman() {
        return this.url_rincian_pinjaman;
    }
    public String getTotal_pinjaman() {
        return this.total_pinjaman;
    }
    public String getFlag_nama() {
        return this.flag_nama;
    }
    public String getUser_id() {
        return this.user_id;
    }
    public String getTanggal_diajukan() {
        return this.tanggal_diajukan;
    }
    public String getTanggal_diterima() {
        return this.tanggal_diterima;
    }
    public String getTanggal_ditolak() {
        return this.tanggal_ditolak;
    }
    public String getDiterima_oleh() {
        return this.diterima_oleh;
    }
    public String getDitolak_oleh() {
        return this.ditolak_oleh;
    }
    public String getLat_koperasi() {
        return this.lat_koperasi;
    }
    public String getLng_koperasi() {
        return this.lng_koperasi;
    }
    public String getNo_telepon() {
        return this.no_telepon;
    }
}
