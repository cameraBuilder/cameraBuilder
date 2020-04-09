package com.hackathon.camerabuilder.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hackathon.camerabuilder.R;
import com.hackathon.camerabuilder.api.ApiHelper;

public class ProductPictureFragment extends Fragment {

    public ProductPictureFragment() {
    }

    public static ProductPictureFragment newInstance(String imageUrl) {
        ProductPictureFragment fragment = new ProductPictureFragment();
        Bundle args = new Bundle();
        args.putString("url", imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    private String url;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString("url");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((SimpleDraweeView)view.findViewById(R.id.sd_picture)).setImageURI(ApiHelper.host + url);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_picture, container, false);
    }
}
