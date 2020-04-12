package com.hackathon.camerabuilder.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.hackathon.camerabuilder.BaseActivity;
import com.hackathon.camerabuilder.R;
import com.hackathon.camerabuilder.api.model.Camera;
import com.hackathon.camerabuilder.api.model.Flash;
import com.hackathon.camerabuilder.api.model.Lens;
import com.hackathon.camerabuilder.api.model.NetworkCallBack;
import com.hackathon.camerabuilder.databinding.ActivityProductsBinding;
import java.util.ArrayList;
import java.util.Objects;

public class ProductsActivity extends BaseActivity<ActivityProductsBinding> {

    public static void launch(Activity activity, String type) {
        Intent intent = new Intent(activity, ProductsActivity.class);
        intent.putExtra("product", type);
        activity.startActivity(intent);
    }
    @Override
    public int getLayoutResource() {
        return R.layout.activity_products;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = Objects.requireNonNull(getIntent().getExtras()).getString("product");
        mViewDataBinding.rvProducts.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBinding.toolbar.setTitle(title);
        mViewDataBinding.toolbar.setNavigationOnClickListener(view -> {
            onBackPressed();
        });

        switch (Objects.requireNonNull(title)) {
            case "Cameras":
                repository.getAllCams(new NetworkCallBack<ArrayList<Camera>>() {
                    @Override
                    public void onError(String message) {
                        runOnUiThread(() ->{
                            Toast.makeText(ProductsActivity.this, message, Toast.LENGTH_SHORT).show();
                            mViewDataBinding.progress.setVisibility(View.GONE);
                        });
                    }
                    @Override
                    public void onSuccess(ArrayList<Camera> data, String message) {
                        runOnUiThread(() -> {
                            mViewDataBinding.rvProducts.setAdapter(new ProductsAdapter<>(data, product -> {
                                repository.addToBag(product.getId() + "", "C", new NetworkCallBack() {
                                    @Override
                                    public void onError(String message) {
                                    }

                                    @Override
                                    public void onSuccess(Object data, String message) {
                                    }
                                });
                            }));
                            mViewDataBinding.progress.setVisibility(View.GONE);
                        });
                    }
                });
                break;

            case "Lenses":
                repository.getAllLenses(new NetworkCallBack<ArrayList<Lens>>() {
                    @Override
                    public void onError(String message) {
                        runOnUiThread(() ->{
                            Toast.makeText(ProductsActivity.this, message, Toast.LENGTH_SHORT).show();
                            mViewDataBinding.progress.setVisibility(View.GONE);
                        });
                    }
                    @Override
                    public void onSuccess(ArrayList<Lens> data, String message) {
                        runOnUiThread(() -> {
                            mViewDataBinding.rvProducts.setAdapter(new ProductsAdapter<>(data, product -> {
                            }));
                            mViewDataBinding.progress.setVisibility(View.GONE);
                        });
                    }
                });
                break;

            case "Adapters":
                repository.getAllAdapters(new NetworkCallBack<ArrayList<Adapter>>() {
                    @Override
                    public void onError(String message) {
                        runOnUiThread(() ->{
                            Toast.makeText(ProductsActivity.this, message, Toast.LENGTH_SHORT).show();
                            mViewDataBinding.progress.setVisibility(View.GONE);
                        });
                    }
                    @Override
                    public void onSuccess(ArrayList<Adapter> data, String message) {
                        runOnUiThread(() -> {
                            mViewDataBinding.rvProducts.setAdapter(new ProductsAdapter<>(data, product -> {

                            }));
                            mViewDataBinding.progress.setVisibility(View.GONE);
                        });
                    }
                });
                break;

            case "Flashes":
                repository.getAllFlashes(new NetworkCallBack<ArrayList<Flash>>() {
                    @Override
                    public void onError(String message) {
                        runOnUiThread(() ->{
                            Toast.makeText(ProductsActivity.this, message, Toast.LENGTH_SHORT).show();
                            mViewDataBinding.progress.setVisibility(View.GONE);
                        });
                    }
                    @Override
                    public void onSuccess(ArrayList<Flash> data, String message) {
                        runOnUiThread(() -> {
                            mViewDataBinding.rvProducts.setAdapter(new ProductsAdapter<>(data, product -> {

                            }));
                            mViewDataBinding.progress.setVisibility(View.GONE);
                        });
                    }
                });
                break;
        }
    }
}
