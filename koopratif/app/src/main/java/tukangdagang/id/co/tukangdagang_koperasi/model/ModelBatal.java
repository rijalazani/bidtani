package tukangdagang.id.co.tukangdagang_koperasi.model;

public class ModelBatal {

    String id_alasan;
    String alasan;

    //constructor
    public ModelBatal(String id_alasan, String alasan) {
        this.id_alasan = id_alasan;
        this.alasan = alasan;
    }

    //getters


    public String getId_alasan() {
        return this.id_alasan;
    }
    public String getAlasan() {
        return this.alasan;
    }

    public void setEditTextValue(String alasan) {
        this.alasan = alasan;
    }
}
