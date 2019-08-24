package tukangdagang.id.co.tukangdagang_koperasi.model;

public class ModelAlasanPinjam {

    String id;
    String alasan;

    //constructor
    public ModelAlasanPinjam(String id, String alasan) {
        this.id = id;
        this.alasan = alasan;
    }

    //getters


    public String getId_alasan() {
        return this.id;
    }
    public String getAlasan() {
        return this.alasan;
    }

    public void setEditTextValue(String alasan) {
        this.alasan = alasan;
    }
}
