package tukangdagang.id.co.tukangdagang_koperasi.model;
public class ModelProsesAnggota {

    String id;
    String id_koperasi;
    String nama_koperasi;
    String url_image;
    String url_alasan;
    String url_pembayaran;
    String url_rincian_pengajuan;
    String tanggal_diajukan;
    String tanggal_ditolak;
    String tanggal_diterima;
    String alasan_ditolak;
    String flag_nama;
    String user_id;
    String flag_bayar_nama;
    String lat_koperasi;
    String lng_koperasi;
    String no_telepon;
    String total_bayar;
    String order_id;

    //constructor
    public ModelProsesAnggota(String id,String id_koperasi, String nama_koperasi,
                              String url_image, String url_alasan,
                              String url_pembayaran,String url_rincian_pengajuan, String tanggal_diajukan,
                              String tanggal_ditolak,String tanggal_diterima,
                              String alasan_ditolak,String flag_nama,String user_id,String flag_bayar_nama,String lat_koperasi,String lng_koperasi,String no_telepon,String total_bayar,String order_id ) {
        this.id = id;
        this.id_koperasi = id_koperasi;
        this.nama_koperasi = nama_koperasi;
        this.url_image = url_image;
        this.url_alasan = url_alasan;
        this.url_pembayaran = url_pembayaran;
        this.url_rincian_pengajuan = url_rincian_pengajuan;
        this.tanggal_diajukan = tanggal_diajukan;
        this.tanggal_ditolak = tanggal_ditolak;
        this.tanggal_diterima = tanggal_diterima;
        this.alasan_ditolak = alasan_ditolak;
        this.flag_nama = flag_nama;
        this.user_id = user_id;
        this.flag_bayar_nama = flag_bayar_nama;
        this.lat_koperasi = lat_koperasi;
        this.lng_koperasi = lng_koperasi;
        this.no_telepon = no_telepon;
        this.total_bayar = total_bayar;
        this.order_id = order_id;
    }

    //getters


    public String getId_anggota() {
        return this.id;
    }
    public String getId_koperasi() {
        return this.id_koperasi;
    }

    public String getNama_koperasi() {
        return this.nama_koperasi;
    }
    public String getUrl_image() {
        return this.url_image;
    }
    public String getUrl_alasan() {
        return this.url_alasan;
    }
    public String getUrl_pembayaran() {
        return this.url_pembayaran;
    }
    public String getUrl_rincian_pengajuan() {
        return this.url_rincian_pengajuan;
    }
    public String getTanggal_diajukan() {
        return this.tanggal_diajukan;
    }
    public String getTanggal_ditolak() {
        return this.tanggal_ditolak;
    }
    public String getTanggal_diterima() {
        return this.tanggal_diterima;
    }
    public String getAlasan_ditolak() {
        return this.alasan_ditolak;
    }
    public String getStatus() {
        return this.flag_nama;
    }
    public String getUser_id() {
        return this.user_id;
    }
    public String getFlag_bayar_nama() {
        return this.flag_bayar_nama;
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
    public String getTotal_bayar() {
        return this.total_bayar;
    }
    public String getOrder_id() {
        return this.order_id;
    }
}
