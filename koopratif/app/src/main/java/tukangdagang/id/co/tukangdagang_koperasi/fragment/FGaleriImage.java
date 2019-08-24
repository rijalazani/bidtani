package tukangdagang.id.co.tukangdagang_koperasi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import tukangdagang.id.co.tukangdagang_koperasi.R;

public class FGaleriImage extends Fragment {

    private String imageURL;

    public FGaleriImage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_galeri_image, container, false);
        PhotoView imageView = view.findViewById(R.id.imageView);
        imageURL = getArguments().getString("imageURL");
        Picasso.with(getActivity())
                .load(imageURL)
                .into(imageView);
        return view;
    }
}