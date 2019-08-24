package tukangdagang.id.co.tukangdagang_koperasi.model;
public class ModelNotifikasi2 {

    String pemberitahuan_id;
    String url_rincian_pemberitahuan;
    String waktu;
    String pengirim;
    String user_id;
    String judul;
    String konten;
    String ikon;
    String dilihat;
    String url;
    String pendaftaran_id;
    String pinjaman_id;
    String keterangan;


    //constructor
    public ModelNotifikasi2(String pemberitahuan_id,String url_rincian_pemberitahuan,String waktu, String pengirim, String user_id, String judul, String konten, String ikon, String dilihat, String url, String pendaftaran_id,String pinjaman_id,String keterangan) {
        this.pemberitahuan_id = pemberitahuan_id;
        this.url_rincian_pemberitahuan = url_rincian_pemberitahuan;
        this.waktu = waktu;
        this.pengirim = pengirim;
        this.user_id = user_id;
        this.judul = judul;
        this.konten = konten;
        this.ikon = ikon;
        this.dilihat = dilihat;
        this.url = url;
        this.pendaftaran_id = pendaftaran_id;
        this.pinjaman_id = pinjaman_id;
        this.keterangan = keterangan;

    }

    //getters


    public String getPemberitahuan_id() {
        return this.pemberitahuan_id;
    }
    public String getUrl_rincian_pemberitahuan() {
        return this.url_rincian_pemberitahuan;
    }
    public String getWaktu() {
        return this.waktu;
    }
    public String getPengirim() {
        return this.pengirim;
    }

    public String getUser_id() {
        return this.user_id;
    }
    public String getJudul() {
        return this.judul;
    }
    public String getKonten() {
        return this.konten;
    }
    public String getIkon() {
        return this.ikon;
    }
    public String getDilihat() {
        return this.dilihat;
    }
    public String getUrl() {
        return this.url;
    }
    public String getPendaftaran_id() {
        return this.pendaftaran_id;
    }
    public String getPinjaman_id() {
        return this.pinjaman_id;
    }
    public String getKeterangan() {
        return this.keterangan;
    }
}
