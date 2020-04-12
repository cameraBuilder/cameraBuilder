package com.hackathon.camerabuilder.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.gson.Gson;
import com.hackathon.camerabuilder.BaseActivity;
import com.hackathon.camerabuilder.R;
import com.hackathon.camerabuilder.api.model.ActualKit;
import com.hackathon.camerabuilder.api.model.Camera;
import com.hackathon.camerabuilder.api.model.Flash;
import com.hackathon.camerabuilder.api.model.KitItem;
import com.hackathon.camerabuilder.api.model.Lens;
import com.hackathon.camerabuilder.api.model.NetworkCallBack;
import com.hackathon.camerabuilder.api.model.Product;
import com.hackathon.camerabuilder.databinding.ActivityKitsBinding;
import java.util.ArrayList;


public class KitsActivity extends BaseActivity<ActivityKitsBinding> {

    private ArrayList<ActualKit> actualKits;
    public static void launch(Activity activity) {
        activity.startActivity(new Intent(activity, KitsActivity.class));
    }
    @Override
    public int getLayoutResource() {
        return R.layout.activity_kits;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewDataBinding.rvKits.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBinding.toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getKits();
    }

    public void openKit(View view) {
        KitActivity.launch(this);
    }

    private void getKits(){
        Gson gson = new Gson();
        mViewDataBinding.progressCircular.setVisibility(View.VISIBLE);
        repository.getAllKits(new NetworkCallBack<ArrayList<ActualKit>>() {
            @Override
            public void onError(String message) {
                runOnUiThread(() -> {
                    Toast.makeText(KitsActivity.this, message, Toast.LENGTH_SHORT).show();
                    mViewDataBinding.progressCircular.setVisibility(View.GONE);
                });
            }
            @Override
            public void onSuccess(ArrayList<ActualKit> data, String message) {
                ArrayList<KitWithProducts> kitsWithProducts = new ArrayList<>();
                actualKits = data;
                for (ActualKit actualKit:data) {
                    ArrayList<Product> products = new ArrayList<>();
                    KitWithProducts kitWithProducts = new KitWithProducts();
                    kitWithProducts.setId(actualKit.getId());
                    for (KitItem kitItem: actualKit.getKitItems()){
                        switch (kitItem.getTag()) {
                            case "camera":
                                Camera camera = gson.fromJson(kitItem.getProduct(), Camera.class);
                                products.add(camera);
                                break;

                            case "lens":
                                Lens lens = gson.fromJson(kitItem.getProduct(), Lens.class);
                                products.add(lens);
                                break;

                            case "adapter":
                                Adapter adapter = gson.fromJson(kitItem.getProduct(), Adapter.class);
                                products.add(adapter);
                                break;

                            case "flash":
                                Flash flash = gson.fromJson(kitItem.getProduct(), Flash.class);
                                products.add(flash);
                                break;
                        }
                    }
                    kitWithProducts.setProducts(products);
                    kitsWithProducts.add(kitWithProducts);
                }
                runOnUiThread(() ->{
                    mViewDataBinding.rvKits.setAdapter(new KitsAdapter(kitsWithProducts));
                    mViewDataBinding.progressCircular.setVisibility(View.GONE);
                });
            }
        });
    }
}
