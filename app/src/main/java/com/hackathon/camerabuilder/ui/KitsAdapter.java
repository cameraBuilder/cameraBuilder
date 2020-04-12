package com.hackathon.camerabuilder.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hackathon.camerabuilder.R;
import com.hackathon.camerabuilder.api.ApiHelper;
import com.hackathon.camerabuilder.api.model.Product;

import java.util.ArrayList;

import static java.lang.String.format;

public class KitsAdapter extends RecyclerView.Adapter<KitsAdapter.KitsViewHolder>{

    private ArrayList<KitWithProducts> kitWithProducts;

    public KitsAdapter(ArrayList<KitWithProducts> kitWithProducts) {
        this.kitWithProducts = kitWithProducts;
    }

    @NonNull
    @Override
    public KitsAdapter.KitsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new KitsViewHolder(View.inflate(parent.getContext(), R.layout.row_kit, null));
    }

    @Override
    public void onBindViewHolder(@NonNull KitsAdapter.KitsViewHolder holder, int position) {
        KitWithProducts kitWithProduct = kitWithProducts.get(position);
        holder.tvId.setText(format("# %d", kitWithProduct.getId()));
        int counter = 0;
        for (Product product:kitWithProduct.getProducts()) {
            if (counter < 8) {
                String imageUrl = "";
                if (product.getImages().contains(",")){
                    imageUrl = product.getImages().split(",")[0];
                }
                else {
                    imageUrl = product.getImages();
                }
                imageUrl = ApiHelper.host + imageUrl;
                switch (counter){
                    case 0:
                        holder.sd1.setImageURI(imageUrl);
                        break;
                    case 1:
                        holder.sd2.setImageURI(imageUrl);
                        break;
                    case 2:
                        holder.sd3.setImageURI(imageUrl);
                        break;
                    case 3:
                        holder.sd4.setImageURI(imageUrl);
                        break;
                    case 4:
                        holder.sd5.setImageURI(imageUrl);
                        break;
                    case 5:
                        holder.sd6.setImageURI(imageUrl);
                        break;
                    case 6:
                        holder.sd7.setImageURI(imageUrl);
                        break;
                    case 7:
                        holder.sd8.setImageURI(imageUrl);
                        break;
                }
            }
            counter ++;
        }
    }

    @Override
    public int getItemCount() {
        return kitWithProducts.size();
    }

    class KitsViewHolder extends RecyclerView.ViewHolder{

        TextView tvId;
        SimpleDraweeView sd1;
        SimpleDraweeView sd2;
        SimpleDraweeView sd3;
        SimpleDraweeView sd4;
        SimpleDraweeView sd5;
        SimpleDraweeView sd6;
        SimpleDraweeView sd7;
        SimpleDraweeView sd8;


        KitsViewHolder(@NonNull View itemView) {
            super(itemView);
            sd1 = itemView.findViewById(R.id.sd_1);
            sd2 = itemView.findViewById(R.id.sd_2);
            sd3 = itemView.findViewById(R.id.sd_3);
            sd4 = itemView.findViewById(R.id.sd_4);
            sd5 = itemView.findViewById(R.id.sd_5);
            sd6 = itemView.findViewById(R.id.sd_6);
            sd7 = itemView.findViewById(R.id.sd_7);
            sd8 = itemView.findViewById(R.id.sd_8);
            tvId = itemView.findViewById(R.id.tv_id);
        }

    }
}
