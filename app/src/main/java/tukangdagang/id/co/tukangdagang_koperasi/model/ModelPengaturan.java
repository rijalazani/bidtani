package tukangdagang.id.co.tukangdagang_koperasi.model;

import android.graphics.drawable.Drawable;

public class ModelPengaturan {

    String title;
    String desc;
    int gambar;

    //constructor
    public ModelPengaturan(String title, String desc, int gambar) {
        this.title = title;
        this.desc = desc;
        this.gambar = gambar;
    }

    //getters


    public String getTitle() {
        return this.title;
    }
    public String getDesc() {
        return this.desc;
    }
    public int getGambar() {
        return this.gambar;
    }

}
