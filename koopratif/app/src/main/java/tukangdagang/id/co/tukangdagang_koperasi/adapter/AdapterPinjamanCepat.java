package tukangdagang.id.co.tukangdagang_koperasi.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tukangdagang.id.co.tukangdagang_koperasi.DetailKoprasi;
import tukangdagang.id.co.tukangdagang_koperasi.FormPinjam;
import tukangdagang.id.co.tukangdagang_koperasi.Login;
import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.WebSyarat;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelAlasanPinjam;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelCariKoperasi;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelPinjamanCepatKoperasiku;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.formatRupiah;


public class AdapterPinjamanCepat extends RecyclerView.Adapter<AdapterPinjamanCepat.MyViewHolder> {

    private Context mContext ;
    private List<ModelPinjamanCepatKoperasiku> mData ;
    private List<ModelAlasanPinjam> mAlasan ;
    ArrayList<ModelAlasanPinjam> arrayAlasan;
    ArrayList<ModelPinjamanCepatKoperasiku> arrayList;
    private boolean loggedIn = false;
//    private String path_gambar = Config.path+Config.logokoperasi;

    public AdapterPinjamanCepat(Context mContext, List<ModelPinjamanCepatKoperasiku> mData, List<ModelAlasanPinjam> mAlasan) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelPinjamanCepatKoperasiku>();
        this.arrayList.addAll(mData);

        this.mAlasan = mAlasan;
        this.arrayAlasan = new ArrayList<ModelAlasanPinjam>();
        this.arrayAlasan.addAll(mAlasan);
    }

    @Override
    public AdapterPinjamanCepat.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_pinjaman_cepat,parent,false);
        return new AdapterPinjamanCepat.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterPinjamanCepat.MyViewHolder holder, final int position) {

        holder.namakoperasi.setText(mData.get(position).getNama_koperasi());
        holder.sukubunga.setText(mData.get(position).getBunga()+" %");
        holder.waktuproses.setText(mData.get(position).getWaktu_proses());
        holder.tenor.setText(mData.get(position).getTenor());
        holder.pinjaman.setText( formatRupiah.format((double) Double.valueOf(String.valueOf(mData.get(position).getMinimal_pinjaman())))+" - "+formatRupiah.format((double) Double.valueOf(String.valueOf(mData.get(position).getMaksimal_pinjaman()))));
        //set the result in imageview
//        holder.mIconIv.setImageResource(modellist.get(postition).getIcon());

        Glide.with(mContext)
                .load(mData.get(position).getUrl_image())

                .into(holder.mIconIv);

        holder.btnSyarat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

                //Fetching the boolean value form sharedpreferences
                loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);
                if(!loggedIn) {
                    //We will start the SimpananF Activity
                    Intent intent = new Intent(mContext, Login.class);
                    mContext.startActivity(intent);
                }else {
                    Intent i = new Intent(mContext, WebSyarat.class);
                    i.putExtra("urlsyarat", mData.get(position).getUrl_syarat_pinjam());
                    mContext.startActivity(i);
                }
            }
        });

        holder.btnPinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

                //Fetching the boolean value form sharedpreferences
                loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);
                if(!loggedIn) {
                    //We will start the SimpananF Activity
                    Intent intent = new Intent(mContext, Login.class);
                    mContext.startActivity(intent);
                }else {
                    Intent i = new Intent(mContext, FormPinjam.class);
                    i.putExtra("modelpinjamancepat", new Gson().toJson(mData.get(position))); // converting model json into string type and sending it via intent
                    i.putExtra("modelAlasan", new Gson().toJson(mAlasan)); // converting model json into string type and sending it via intent
                    mContext.startActivity(i);
//                    Toast.makeText(mContext,mData.get(position).toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.LDetailKop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailKoprasi.class);
                intent.putExtra("idkoperasi", mData.get(position).getId_koperasi());
                intent.putExtra("gambarkoperasi", mData.get(position).getUrl_image());
                intent.putExtra("namakoperasi", mData.get(position).getNama_koperasi());
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView namakoperasi, sukubunga,waktuproses,tenor,pinjaman;
        ImageView mIconIv;
        Button btnSyarat,btnPinjam;
        LinearLayout LDetailKop;

        public MyViewHolder(View itemView) {
            super(itemView);

            btnPinjam = (Button) itemView.findViewById(R.id.btn_pinjam) ;
            btnSyarat = (Button) itemView.findViewById(R.id.btn_syarat) ;
            namakoperasi = (TextView) itemView.findViewById(R.id.nama_koperasi) ;
            sukubunga = (TextView) itemView.findViewById(R.id.sukubunga) ;
            mIconIv = (ImageView) itemView.findViewById(R.id.logo) ;
            waktuproses = (TextView) itemView.findViewById(R.id.waktuproses) ;
            tenor = (TextView) itemView.findViewById(R.id.tenor) ;
            pinjaman = (TextView) itemView.findViewById(R.id.pinjaman) ;
            LDetailKop = (LinearLayout) itemView.findViewById(R.id.LdetailKop) ;



        }
    }

    //filter
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        mData.clear();
        if (charText.length()==0){
            mData.clear();
            mData.addAll(arrayList);
        }
        else {
            for (ModelPinjamanCepatKoperasiku modelPinjamanCepatKoperasiku : arrayList){
                if (modelPinjamanCepatKoperasiku.getNama_koperasi().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    mData.clear();
                    mData.add(modelPinjamanCepatKoperasiku);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void filterList(ArrayList<ModelPinjamanCepatKoperasiku> filteredList) {
        mData = filteredList;
        notifyDataSetChanged();
    }


}
