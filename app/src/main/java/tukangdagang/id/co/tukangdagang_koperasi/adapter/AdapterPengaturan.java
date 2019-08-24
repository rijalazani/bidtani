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

import tukangdagang.id.co.tukangdagang_koperasi.InformasiUmum;
import tukangdagang.id.co.tukangdagang_koperasi.PengaturanInformasiDiri;
import tukangdagang.id.co.tukangdagang_koperasi.PengaturanRekening;
import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.Uploadktp;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelPengaturan;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelPengaturan;

public class AdapterPengaturan extends RecyclerView.Adapter<AdapterPengaturan.MyViewHolder> {

    private Context mContext ;
    private List<ModelPengaturan> mData ;
    ArrayList<ModelPengaturan> arrayList;
    private boolean loggedIn = false;

    public AdapterPengaturan(Context mContext, List<ModelPengaturan> mData) {
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
                if (mData.get(position).getTitle().equals("Akun")){
                    Intent intent = new Intent(mContext, PengaturanInformasiDiri.class);
                    mContext.startActivity(intent);

                } if (mData.get(position).getTitle().equals("Rekening")){
                    //start NewActivity with title for actionbar and text for textview
                    Intent intent = new Intent(mContext, PengaturanRekening.class);
                    mContext.startActivity(intent);
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
