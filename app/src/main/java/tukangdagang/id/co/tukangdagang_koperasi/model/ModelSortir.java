package tukangdagang.id.co.tukangdagang_koperasi.model;

public class ModelSortir {
    String id;
    String sortir;

    //constructor
    public ModelSortir(String id, String sortir) {
        this.id = id;
        this.sortir = sortir;
    }

    //getters


    public String getId() {
        return this.id;
    }
    public String getSortir() {
        return this.sortir;
    }

    public void setEditTextValue(String sortir) {
        this.sortir = sortir;
    }
}
