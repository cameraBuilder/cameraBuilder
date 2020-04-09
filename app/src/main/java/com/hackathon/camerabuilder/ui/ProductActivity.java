package com.hackathon.camerabuilder.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.google.gson.Gson;
import com.hackathon.camerabuilder.BaseActivity;
import com.hackathon.camerabuilder.R;
import com.hackathon.camerabuilder.api.model.Camera;
import com.hackathon.camerabuilder.api.model.Lens;
import com.hackathon.camerabuilder.api.model.Product;
import com.hackathon.camerabuilder.databinding.ActivityProductBinding;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ProductActivity extends BaseActivity<ActivityProductBinding> {

    public static <T extends Product> void launch(Context context, T product, String type) {
        Gson gson = new Gson();
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra("productString", gson.toJson(product));
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_product;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Gson gson = new Gson();
        if (getIntent().getExtras() != null) {
            String productString = getIntent().getExtras().getString("productString");
            String type = getIntent().getExtras().getString("type");

            Product product = null;
            mViewDataBinding.toolbar.setTitle(type);
            switch (Objects.requireNonNull(type)) {
                case "Camera":
                    Camera camera = gson.fromJson(productString, Camera.class);
                    mViewDataBinding.tvType.setText(camera.getType());
                    mViewDataBinding.tvMount.setText(camera.getMountType());
                    product = camera;
                    break;
                case "Lens":
                    Lens lens = gson.fromJson(productString, Lens.class);
                    mViewDataBinding.rlType.setVisibility(View.GONE);
                    mViewDataBinding.tvMountLabel.setText(R.string.copat);
                    mViewDataBinding.tvMount.setText(lens.getCompatibleMount());
                    product = lens;
                    break;
                case "Adapter":
                    Adapter adapter = gson.fromJson(productString, Adapter.class);
                    mViewDataBinding.tvTypeLabel.setText(R.string.com_cam);
                    mViewDataBinding.tvType.setText(adapter.getCompatibleCameraMount());
                    mViewDataBinding.tvMountLabel.setText(R.string.cop_lens);
                    mViewDataBinding.tvMount.setText(adapter.getCompatibleLenseMount());
                    product = adapter;
                    break;
                default:
                    product = gson.fromJson(productString, Product.class);
                    mViewDataBinding.rlType.setVisibility(View.GONE);
                    mViewDataBinding.rlMount.setVisibility(View.GONE);
                    break;
            }

            mViewDataBinding.tvName.setText(product.getName());
            mViewDataBinding.tvBrand.setText(product.getBrand());
            mViewDataBinding.tvSpecs.setText(product.getDescription());
            mViewDataBinding.tvSpecs.setTag(product.getDescription());
            mViewDataBinding.toolbar.setNavigationOnClickListener(view -> finish());

            if (!TextUtils.isEmpty(product.getImages())) {
                if (!product.getImages().contains(",")) {
                    ProductImagePagerAdapter productImagePagerAdapter = new ProductImagePagerAdapter(getSupportFragmentManager(), new String[]{product.getImages()});
                    mViewDataBinding.vpCameras.setAdapter(productImagePagerAdapter);
                    return;
                }
                ProductImagePagerAdapter productImagePagerAdapter = new ProductImagePagerAdapter(getSupportFragmentManager(), product.getImages().split(","));
                mViewDataBinding.vpCameras.setAdapter(productImagePagerAdapter);
            }
            mViewDataBinding.dotsIndicator.setViewPager(mViewDataBinding.vpCameras);
        }
    }

    public void openSite(View view) {
        String url = (String) view.getTag();
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    public static class ProductImagePagerAdapter extends FragmentPagerAdapter {
        private String[] imageUrls;

        ProductImagePagerAdapter(FragmentManager fragmentManager, String[] imageUrls) {
            super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.imageUrls = imageUrls;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return imageUrls.length;
        }

        // Returns the fragment to display for that page
        @NotNull
        @Override
        public Fragment getItem(int position) {
            return ProductPictureFragment.newInstance(imageUrls[position]);
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }
}
