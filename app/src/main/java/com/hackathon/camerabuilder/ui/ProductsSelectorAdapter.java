package com.hackathon.camerabuilder.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.hackathon.camerabuilder.R;
import com.hackathon.camerabuilder.api.model.Camera;
import com.hackathon.camerabuilder.api.model.Lens;
import com.hackathon.camerabuilder.api.model.Product;

import java.util.ArrayList;

import static com.hackathon.camerabuilder.api.ApiHelper.host;

public class ProductsSelectorAdapter extends RecyclerView.Adapter<ProductsSelectorAdapter.ProductRadioViewHolder> {

    private ArrayList<Product> products;
    private Product selectedProduct = null;
    private int selectedOption = -1;
    ProductsSelectorAdapter(ArrayList<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductRadioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductRadioViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_radio_product, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRadioViewHolder holder, int position) {

        Context context = holder.itemView.getContext();
        Product product = products.get(position);

        SpannableString spannableNameContent=new SpannableString(String.format("%s %s", context.getResources().getString(R.string.name_bold), product.getName()));
        spannableNameContent.setSpan(new StyleSpan(Typeface.BOLD),
                0, context.getResources().getString(R.string.name_bold).length(), 0);
        holder.tvName.setText(spannableNameContent);
        SpannableString spannableBrandContent=new SpannableString(String.format("%s %s", context.getResources().getString(R.string.brand), product.getBrand()));
        spannableBrandContent.setSpan(new StyleSpan(Typeface.BOLD),
                0, context.getResources().getString(R.string.brand).length(), 0);
        holder.tvBrand.setText(spannableBrandContent);
        if (product instanceof Camera) {
            Camera camera = (Camera) product;
            SpannableString spannableTypeContent=new SpannableString(String.format("%s %s", context.getResources().getString(R.string.type), camera.getType()));
            spannableTypeContent.setSpan(new StyleSpan(Typeface.BOLD),
                    0, context.getResources().getString(R.string.type).length(), 0);
            holder.tvType.setText(spannableTypeContent);
            SpannableString spannableMountContent=new SpannableString(String.format("%s %s", context.getResources().getString(R.string.mount), camera.getMountType()));
            spannableMountContent.setSpan(new StyleSpan(Typeface.BOLD),
                     0, context.getResources().getString(R.string.mount).length(), 0);
            holder.tvMount.setText(spannableMountContent);
        }

        if (product instanceof Lens) {
            Lens lens = (Lens) product;
            holder.tvType.setVisibility(View.GONE);
            SpannableString spannableMountContent=new SpannableString(String.format("%s %s", context.getString(R.string.compatible_mount), lens.getCompatibleMount()));
            spannableMountContent.setSpan(new StyleSpan(Typeface.BOLD),
                    0, context.getString(R.string.compatible_mount).length(), 0);
            holder.tvMount.setText(spannableMountContent);
        }

        if (product instanceof Adapter) {
            Adapter adapter = (Adapter) product;
            SpannableString spannableTypeContent=new SpannableString(String.format("%s %s", context.getString(R.string.compatibe_cam_mount), adapter.getCompatibleCameraMount()));
            spannableTypeContent.setSpan(new StyleSpan(Typeface.BOLD),
                    0, context.getString(R.string.compatibe_cam_mount).length(), 0);
            holder.tvType.setText(spannableTypeContent);
            SpannableString spannableMountContent=new SpannableString(String.format("%s %s", context.getString(R.string.copatibe_lens_mount), adapter.getCompatibleLenseMount()));
            spannableMountContent.setSpan(new StyleSpan(Typeface.BOLD),
                    0,context.getString(R.string.copatibe_lens_mount).length(), 0);
            holder.tvMount.setText(spannableMountContent);
        }


        if (!TextUtils.isEmpty(product.getImages())) {
            if (!product.getImages().contains(",")) {
                holder.sdProduct.setImageURI(host + product.getImages());
                return;
            }
            holder.sdProduct.setImageURI(host + product.getImages().split(",")[0]);
        }
        if (position == selectedOption) {
            System.out.println("eq");
        }
        holder.materialRadioButton.setChecked(position == selectedOption);

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public Product getSelectedProduct(){
        return selectedProduct;
    }


    public interface OnAddToBagListener{
        void onAddToBagAdd(Product product);
    }

    public  class  ProductRadioViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvBrand;
        TextView tvType;
        TextView tvMount;
        SimpleDraweeView sdProduct;
        MaterialRadioButton materialRadioButton;

        ProductRadioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvBrand = itemView.findViewById(R.id.tv_brand);
            tvMount = itemView.findViewById(R.id.tv_mount);
            tvType = itemView.findViewById(R.id.tv_type);
            sdProduct = itemView.findViewById(R.id.sd_product);
            materialRadioButton = itemView.findViewById(R.id.radio);
            materialRadioButton.setOnClickListener(view ->{
                selectedOption = getAdapterPosition();
                selectedProduct = products.get(getAdapterPosition());
                notifyDataSetChanged();
            });

        }
    }
}
