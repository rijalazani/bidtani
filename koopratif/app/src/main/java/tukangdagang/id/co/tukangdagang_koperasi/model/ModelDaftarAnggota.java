package tukangdagang.id.co.tukangdagang_koperasi.model;

public class ModelDaftarAnggota {
    String title,desc;
    int icon;


    //constructor
    public ModelDaftarAnggota(String title, String desc, int icon) {
        this.title = title;
        this.desc = desc;
        this.icon = icon;
    }

    //getters


    public String getTitle() {
        return this.title;
    }
    public String getDesc() {
        return this.desc;
    }
    public int getIcon() {
        return this.icon;
    }

}
