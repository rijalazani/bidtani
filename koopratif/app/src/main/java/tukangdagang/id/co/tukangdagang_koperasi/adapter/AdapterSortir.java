package tukangdagang.id.co.tukangdagang_koperasi.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tukangdagang.id.co.tukangdagang_koperasi.R;
import tukangdagang.id.co.tukangdagang_koperasi.app.Config;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelBatal;
import tukangdagang.id.co.tukangdagang_koperasi.model.ModelSortir;

import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_alasan;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_sortir;
import static tukangdagang.id.co.tukangdagang_koperasi.app.Config.n_info_sortir_pos;

public class AdapterSortir extends RecyclerView.Adapter<AdapterSortir.MyViewHolder> {

    private static Context mContext ;
    private List<ModelSortir> mData ;
    ArrayList<ModelSortir> arrayList;
    public static ArrayList<ModelSortir> editModelArrayList;
    private boolean loggedIn = false;
    int selectedPosition=-1;

    public AdapterSortir(Context mContext, List<ModelSortir> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.arrayList = new ArrayList<ModelSortir>();
        this.arrayList.addAll(mData);

    }

    @Override
    public AdapterSortir.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_sortir,parent,false);
        return new AdapterSortir.MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final AdapterSortir.MyViewHolder holder, final int position) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String Nsortir="";
        if(selectedPosition==position) {
            holder.iconchek.setVisibility(View.VISIBLE);
            holder.itemView.setBackgroundColor(Color.parseColor("#e8e8e8"));

            Nsortir = mData.get(position).getId();
            editor.putString(n_info_sortir, Nsortir);
            editor.putString(n_info_sortir_pos, String.valueOf(position));
            editor.commit();
//            Toast.makeText(mContext,mData.get(position).getSortir(),Toast.LENGTH_SHORT).show();
        }
        else {
            holder.iconchek.setVisibility(View.GONE);
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));

        }



//        holder.title.setText(mData.get(position).getId_alasan());
        holder.sortir.setText(mData.get(position).getSortir());

        //set the result in imageview
//        holder.mIconIv.setImageResource(modellist.get(postition).getIcon());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition=position;
                notifyDataSetChanged();


            }
        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView sortir;
        ImageView iconchek;
        RecyclerView myrv;



        public MyViewHolder(View itemView) {
            super(itemView);
            sortir = (TextView) itemView.findViewById(R.id.sortir) ;
            iconchek = (ImageView) itemView.findViewById(R.id.iconchek) ;



//            //position to be clicked
//            final int pos = 0;
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    recicleview.findViewHolderForAdapterPosition(pos).itemView.performClick();
//                }
//            },1);
        }

    }


}
