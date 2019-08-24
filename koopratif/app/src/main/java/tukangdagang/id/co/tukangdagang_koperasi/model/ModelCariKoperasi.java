package tukangdagang.id.co.tukangdagang_koperasi.model;
public class ModelCariKoperasi {

    String id;
    String nik;
    String kode_koperasi;
    String nama_koperasi;
    String nomor_badanhukum;
    String tanggal_badanhukum;
    String grade_koperasi;
    String alamat_koperasi;
    String kelurahan;
    String kecamatan;
    String kota;
    String provinsi;
    String kode_pos_id;
    String status_koperasi;
    String lat_koperasi;
    String lng_koperasi;
    String url_image;
    String url_syarat;
    String url_rincian_koperasi;
    String rating_koperasi;
    String pinjaman_min_koperasi;
    String pinjaman_max_koperasi;
    String simpanan_wajib;
    String simpanan_pokok;
    String simpanan_sukarela;
    String simpanan_khusus;
    String simpanan_total;
    String persen_admin_pinjaman;
    String biaya_admin_daftar;
    String denda_terlambat;
    String isaktif_daftar_online;
    String bagihasil_pendaftaran;
    String bagihasil_pinjaman;


    //constructor
    public ModelCariKoperasi(String id,String nik, String kode_koperasi, String nama_koperasi, String nomor_badanhukum, String tanggal_badanhukum,
                             String grade_koperasi, String alamat_koperasi, String kelurahan, String kecamatan, String kota, String provinsi,
                             String kode_pos_id, String status_koperasi, String lat_koperasi, String lng_koperasi, String url_image,
                             String url_syarat,String url_rincian_koperasi, String rating_koperasi, String pinjaman_min_koperasi, String pinjaman_max_koperasi, String simpanan_wajib,
                             String simpanan_pokok, String simpanan_sukarela, String simpanan_khusus, String simpanan_total, String persen_admin_pinjaman,
                             String biaya_admin_daftar, String denda_terlambat, String isaktif_daftar_online, String bagihasil_pendaftaran, String bagihasil_pinjaman) {
        this.id = id;
        this.nik = nik;
        this.kode_koperasi = kode_koperasi;
        this.nama_koperasi = nama_koperasi;
        this.nomor_badanhukum = nomor_badanhukum;
        this.tanggal_badanhukum = tanggal_badanhukum;
        this.grade_koperasi = grade_koperasi;
        this.alamat_koperasi = alamat_koperasi;
        this.kelurahan = kelurahan;
        this.kecamatan = kecamatan;
        this.kota = kota;
        this.provinsi = provinsi;
        this.kode_pos_id = kode_pos_id;
        this.status_koperasi = status_koperasi;
        this.lat_koperasi = lat_koperasi;
        this.lng_koperasi = lng_koperasi;
        this.url_image = url_image;
        this.url_syarat = url_syarat;
        this.url_rincian_koperasi = url_rincian_koperasi;
        this.rating_koperasi = rating_koperasi;
        this.pinjaman_min_koperasi = pinjaman_min_koperasi;
        this.pinjaman_max_koperasi = pinjaman_max_koperasi;
        this.simpanan_wajib = simpanan_wajib;
        this.simpanan_pokok = simpanan_pokok;
        this.simpanan_sukarela = simpanan_sukarela;
        this.simpanan_khusus = simpanan_khusus;
        this.simpanan_total = simpanan_total;
        this.persen_admin_pinjaman = persen_admin_pinjaman;
        this.biaya_admin_daftar = biaya_admin_daftar;
        this.denda_terlambat = denda_terlambat;
        this.isaktif_daftar_online = isaktif_daftar_online;
        this.bagihasil_pendaftaran = bagihasil_pendaftaran;
        this.bagihasil_pinjaman = bagihasil_pinjaman;
    }

    //getters


    public String getId() {
        return this.id;
    }
    public String getNik() {
        return this.nik;
    }
    public String getKode_koperasi() {
        return this.kode_koperasi;
    }
    public String getNama_koperasi() {
        return this.nama_koperasi;
    }
    public String getNomor_badanhukum() {
        return this.nomor_badanhukum;
    }
    public String getTanggal_badanhukum() {
        return this.tanggal_badanhukum;
    }
    public String getGrade_koperasi() {
        return this.grade_koperasi;
    }
    public String getAlamat_koperasi() {
        return this.alamat_koperasi;
    }
    public String getKelurahan() {
        return this.kelurahan;
    }
    public String getKecamatan() {
        return this.kecamatan;
    }
    public String getKota() {
        return this.kota;
    }
    public String getProvinsi() {
        return this.provinsi;
    }
    public String getKode_pos_id() {
        return this.kode_pos_id;
    }
    public String getStatus_koperasi() {
        return this.status_koperasi;
    }
    public String getLat_koperasi() {
        return this.lat_koperasi;
    }
    public String getLng_koperasi() {
        return this.lng_koperasi;
    }
    public String getUrl_image() {
        return this.url_image;
    }
    public String getUrl_syarat() {
        return this.url_syarat;
    }
    public String getUrl_rincian_koperasi() {
        return this.url_rincian_koperasi;
    }
    public String getRating_koperasi() {
        return this.rating_koperasi;
    }
    public String getPinjaman_min_koperasi() {
        return this.pinjaman_min_koperasi;
    }
    public String getPinjaman_max_koperasi() {
        return this.pinjaman_max_koperasi;
    }
    public String getSimpanan_wajib() {
        return this.simpanan_wajib;
    }
    public String getSimpanan_pokok() {
        return this.simpanan_pokok;
    }
    public String getSimpanan_sukarela() {
        return this.simpanan_sukarela;
    }
    public String getSimpanan_khusus() {
        return this.simpanan_khusus;
    }
    public String getSimpanan_total() {
        return this.simpanan_total;
    }
    public String getPersen_admin_pinjaman() {
        return this.persen_admin_pinjaman;
    }
    public String getBiaya_admin_daftar() {
        return this.biaya_admin_daftar;
    }
    public String getDenda_terlambat() {
        return this.denda_terlambat;
    }
    public String getIsaktif_daftar_online() {
        return this.isaktif_daftar_online;
    }
    public String getBagihasil_pendaftaran() {
        return this.bagihasil_pendaftaran;
    }
    public String getBagihasil_pinjaman() {
        return this.bagihasil_pinjaman;
    }
}
