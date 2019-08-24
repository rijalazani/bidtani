package tukangdagang.id.co.tukangdagang_koperasi.model;

public class ModelReview {
    String rating;
    String komen;
    String waktu;
    String pengguna;

    //constructor
    public ModelReview(String rating, String komen,String waktu,String pengguna) {
        this.rating = rating;
        this.komen = komen;
        this.waktu = waktu;
        this.pengguna = pengguna;
    }

    //getters


    public String getRating() {
        return this.rating;
    }
    public String getKomen() {
        return this.komen;
    }
    public String getWaktu() {
        return this.waktu;
    }
    public String getPengguna() {
        return this.pengguna;
    }

}
