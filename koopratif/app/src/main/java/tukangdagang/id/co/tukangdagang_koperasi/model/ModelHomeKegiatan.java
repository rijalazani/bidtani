package tukangdagang.id.co.tukangdagang_koperasi.model;
public class ModelHomeKegiatan {

    String acara_id;
    String nama_acara;
    String tanggal;
    String bulan_tahun;
    String tempat_acara;
    String tanggal_acara;
    String acara_mulai;
    String acara_selesai;
    String biaya;
    String pembicara;
    String latitude;
    String longitude;
    String nama_koperasi;
    String kota_koperasi;
    String logo_koperasi;
    String poster_acara;


    //constructor
    public ModelHomeKegiatan(String acara_id, String nama_acara, String tanggal, String bulan_tahun, String tempat_acara, String tanggal_acara, String acara_mulai, String acara_selesai, String biaya, String pembicara, String latitude, String longitude, String nama_koperasi, String kota_koperasi, String logo_koperasi, String poster_acara) {
        this.acara_id = acara_id;
        this.nama_acara = nama_acara;
        this.tanggal = tanggal;
        this.bulan_tahun = bulan_tahun;
        this.tempat_acara = tempat_acara;
        this.tanggal_acara = tanggal_acara;
        this.acara_mulai = acara_mulai;
        this.acara_selesai = acara_selesai;
        this.biaya = biaya;
        this.pembicara = pembicara;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nama_koperasi = nama_koperasi;
        this.kota_koperasi = kota_koperasi;
        this.logo_koperasi = logo_koperasi;
        this.poster_acara = poster_acara;

    }

    //getters

    public String getAcara_id() {
        return this.acara_id;
    }

    public String getNama_acara() {
        return this.nama_acara;
    }
    public String getTanggal() {
        return this.tanggal;
    }
    public String getBulan_tahun() {
        return this.bulan_tahun;
    }
    public String getTempat_acara() {
        return this.tempat_acara;
    }
    public String getTanggal_acara() {
        return this.tanggal_acara;
    }
    public String getAcara_mulai() {
        return this.acara_mulai;
    }
    public String getAcara_selesai() {
        return this.acara_selesai;
    }
    public String getBiaya() {
        return this.biaya;
    }
    public String getPembicara() {
        return this.pembicara;
    }
    public String getLatitude() {
        return this.latitude;
    }
    public String getLongitude() {
        return this.longitude;
    }
    public String getNama_koperasi() {
        return this.nama_koperasi;
    }
    public String getKota_koperasi() {
        return this.kota_koperasi;
    }
    public String getLogo_koperasi() {
        return this.logo_koperasi;
    }
    public String getPoster_acara() {
        return this.poster_acara;
    }
}
