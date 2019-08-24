package tukangdagang.id.co.tukangdagang_koperasi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;
import tukangdagang.id.co.tukangdagang_koperasi.InformasiUmum;
import tukangdagang.id.co.tukangdagang_koperasi.LengkapiProfilGambar;
import tukangdagang.id.co.tukangdagang_koperasi.LengkapiProfilNomor;
import tukangdagang.id.co.tukangdagang_koperasi.LengkapiProfilUmum;
import tukangdagang.id.co.tukangdagang_koperasi.LengkapiProfilWali;
import tukangdagang.id.co.tukangdagang_koperasi.PengaturanInformasiDiri;
import tukangdagang.id.co.tukangdagang_koperasi.PengaturanRekening;
import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.Uploadktp;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelPengaturan;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelPengaturan;

public class AdapterPengaturanInformasiDiri extends RecyclerView.Adapter<AdapterPengaturan.MyViewHolder> {

    private Context mContext ;
    private List<ModelPengaturan> mData ;
    ArrayList<ModelPengaturan> arrayList;
    private boolean loggedIn = false;

    public AdapterPengaturanInformasiDiri(Context mContext, List<ModelPengaturan> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelPengaturan>();
        this.arrayList.addAll(mData);
    }

    @Override
    public AdapterPengaturan.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_pengaturan,parent,false);
        return new AdapterPengaturan.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterPengaturan.MyViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mData.get(position).getTitle().equals("Informasi pribadi")){
                    Intent intent = new Intent(mContext, LengkapiProfilUmum.class);
                    intent.putExtra("pengaturan","1");
                    mContext.startActivity(intent);
                    CustomIntent.customType(mContext, "left-to-right");

                } if (mData.get(position).getTitle().equals("Informasi nomor")){
                    //start NewActivity with title for actionbar and text for textview
                    Intent intent = new Intent(mContext, LengkapiProfilNomor.class);
                    intent.putExtra("pengaturan","1");
                    mContext.startActivity(intent);
                    CustomIntent.customType(mContext, "left-to-right");
                } if (mData.get(position).getTitle().equals("Gambar")){
                    //start NewActivity with title for actionbar and text for textview
                    Intent intent = new Intent(mContext, LengkapiProfilGambar.class);
                    intent.putExtra("pengaturan","1");
                    mContext.startActivity(intent);
                    CustomIntent.customType(mContext, "left-to-right");
                }if (mData.get(position).getTitle().equals("Ahli waris")){
                    //start NewActivity with title for actionbar and text for textview
                    Intent intent = new Intent(mContext, LengkapiProfilWali.class);
                    intent.putExtra("pengaturan","1");
                    mContext.startActivity(intent);
                    CustomIntent.customType(mContext, "left-to-right");
                }

            }
        });
        holder.title.setText(mData.get(position).getTitle());
        holder.desc.setText(mData.get(position).getDesc());
        holder.gambar.setImageResource(mData.get(position).getGambar());





    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, desc;
        ImageView gambar;


        public MyViewHolder(View itemView) {
            super(itemView);


            title = (TextView) itemView.findViewById(R.id.title) ;
            desc = (TextView) itemView.findViewById(R.id.desc) ;
            gambar = (ImageView) itemView.findViewById(R.id.icon) ;


        }
    }

}
