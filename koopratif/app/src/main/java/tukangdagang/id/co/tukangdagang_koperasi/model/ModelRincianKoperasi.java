package tukangdagang.id.co.tukangdagang_koperasi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelRincianKoperasi {

    @SerializedName("gambar")
    private List<ModelRincianKoperasi.Gambar> gambarList;
    @SerializedName("anggota")
    private List<ModelRincianKoperasi.Anggota> anggotaList;
    @SerializedName("rating")
    private List<ModelRincianKoperasi.Rating> ratingList;

    //constructor
    public ModelRincianKoperasi( List<Gambar> gambarList, List<Anggota> anggotaList, List<Rating> ratingList) {
        this.gambarList = gambarList;
        this.anggotaList = anggotaList;
        this.ratingList = ratingList;
    }

    public List<Gambar> getGambarList() {
        return this.gambarList;
    }
    public List<Anggota> getAnggotaList() {
        return this.anggotaList;
    }
    public List<Rating> getRatingList() {
        return this.ratingList;
    }



    public static class Gambar {
        private String url_image;

        public String getGambar_koperasi() {
            return url_image;
        }

        public void setGambar_koperasi(String gambar_koperasi) {
            this.url_image = url_image;
        }
    }

    public static class Anggota {
        private String nama;
        private String avatar;

        public String getNama() {
            return nama;
        }
        public String getAvatar() {
            return avatar;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }
        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

    public static class Rating {
        private String rating;
        private String komen;
        private String waktu;
        private String pengguna;

        public String getRating() {
            return rating;
        }
        public String getKomen() {
            return komen;
        }
        public String getWaktu() {
            return waktu;
        }
        public String getPengguna() {
            return pengguna;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }
        public void setKomen(String komen) {
            this.komen = komen;
        }
        public void setWaktu(String waktu) {
            this.waktu = waktu;
        }
        public void setPengguna(String pengguna) {
            this.pengguna = pengguna;
        }
    }
}
