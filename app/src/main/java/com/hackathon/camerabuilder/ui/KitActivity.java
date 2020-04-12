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
import com.hackathon.camerabuilder.api.model.Kit;
import com.hackathon.camerabuilder.api.model.Lens;
import com.hackathon.camerabuilder.api.model.NetworkCallBack;
import com.hackathon.camerabuilder.api.model.Product;
import com.hackathon.camerabuilder.databinding.ActivityKitBinding;
import java.util.ArrayList;
import java.util.Iterator;

public class KitActivity extends BaseActivity<ActivityKitBinding> implements DialogProductSelect.DialogActionListener{

    private ArrayList<Product> products;
    private boolean isCamSelected = false;

    public static void launch(Activity activity) {
        activity.startActivity(new Intent(activity, KitActivity.class));
    }
    @Override
    public int getLayoutResource() {
        return R.layout.activity_kit;
    }

    private KitProductAdapter kitProductAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewDataBinding.toolbar.setNavigationOnClickListener(view -> finish());
        mViewDataBinding.rvKit.setLayoutManager(new LinearLayoutManager(this));
        products = new ArrayList<>();
        kitProductAdapter = new KitProductAdapter(products);

        mViewDataBinding.btnRefresh.setOnClickListener(view -> {
            isCamSelected = false;
            mViewDataBinding.btnChoose.setText("Please choose a camera");
            products.clear();
            kitProductAdapter.notifyDataSetChanged();
        });

        mViewDataBinding.btnChooseFlash.setOnClickListener(view ->{
            mViewDataBinding.progressCircular.setVisibility(View.VISIBLE);
            repository.getAllFlashes(new NetworkCallBack<ArrayList<Flash>>() {
            @Override
            public void onError(String message) {
                runOnUiThread(() -> {
                    mViewDataBinding.progressCircular.setVisibility(View.GONE);
                    Toast.makeText(KitActivity.this, message, Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onSuccess(ArrayList<Flash> data, String message) {
                runOnUiThread(() -> mViewDataBinding.progressCircular.setVisibility(View.GONE));
                ArrayList<Product> flashes = new ArrayList<>(data);
                DialogProductSelect dialogProductSelect = new DialogProductSelect(KitActivity.this, flashes);
                dialogProductSelect.show(getSupportFragmentManager(), "Flashes");
            }
        });
        });

        mViewDataBinding.btnSave.setOnClickListener(view -> {
            if (products == null || products.size() == 0) {
                Toast.makeText(this, "Please add item", Toast.LENGTH_LONG).show();
                return;
            }
            Kit kit = new Kit();
            kit.setUserId(repository.getUserInfo().getId());
            String items = "";
            Iterator<Product> iterator = products.iterator();
            while (iterator.hasNext()){
                Product product = iterator.next();
                if (product instanceof Camera) {
                    items += "c"+ product.getId();
                }

                if (product instanceof Adapter) {
                    items += "a"+ product.getId();
                }

                if (product instanceof Flash) {
                    items += "f"+ product.getId();
                }

                if (product instanceof Lens) {
                    items += "l"+ product.getId();
                }
                if (iterator.hasNext()){
                    items += ",";
                }
            }
            kit.setItems(items);
            repository.createKit(kit, new NetworkCallBack() {

                @Override
                public void onError(String message) {
                    runOnUiThread(() -> {
                        mViewDataBinding.progressCircular.setVisibility(View.GONE);
                        Toast.makeText(KitActivity.this, message, Toast.LENGTH_SHORT).show();
                    });
                }

                @Override
                public void onSuccess(Object data, String message) {
                    runOnUiThread(() -> {
                        mViewDataBinding.progressCircular.setVisibility(View.GONE);
                        Toast.makeText(KitActivity.this, message, Toast.LENGTH_SHORT).show();
                    });
                    KitActivity.this.finish();
                }
            });
        });

        mViewDataBinding.btnChoose.setOnClickListener(view -> {
            mViewDataBinding.progressCircular.setVisibility(View.VISIBLE);
            if (!isCamSelected){
                repository.getAllCams(new NetworkCallBack<ArrayList<Camera>>() {
                    @Override
                    public void onError(String message) {
                        runOnUiThread(() -> {
                            mViewDataBinding.progressCircular.setVisibility(View.GONE);
                            Toast.makeText(KitActivity.this, message, Toast.LENGTH_SHORT).show();
                        });
                    }

                    @Override
                    public void onSuccess(ArrayList<Camera> data, String message) {
                        runOnUiThread(() -> mViewDataBinding.progressCircular.setVisibility(View.GONE));
                           ArrayList<Product> cameras = new ArrayList<>(data);
                           DialogProductSelect dialogProductSelect = new DialogProductSelect(KitActivity.this, cameras);
                           dialogProductSelect.show(getSupportFragmentManager(), "Cameras");
                    }
                });
                return;
            }

            repository.getAllLenses(new NetworkCallBack<ArrayList<Lens>>() {
                @Override
                public void onError(String message) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(KitActivity.this, message, Toast.LENGTH_SHORT).show();
                            mViewDataBinding.progressCircular.setVisibility(View.GONE);
                        }
                    });
                }
                @Override
                public void onSuccess(ArrayList<Lens> data, String message) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mViewDataBinding.progressCircular.setVisibility(View.GONE);
                        }
                    });
                    ArrayList<Product> lenses = new ArrayList<>(data);
                    DialogProductSelect dialogProductSelect = new DialogProductSelect(KitActivity.this, lenses);
                    dialogProductSelect.show(getSupportFragmentManager(), "lenses");
                }
            });
        });
        mViewDataBinding.rvKit.setAdapter(kitProductAdapter);
    }


    private Camera getCam() {
        for (Product product:products) {
            if (product instanceof Camera){
                return (Camera) product;
            }
        }
        return null;
    }

    @Override
    public void onChooseProduct(Product product) {

        if (product instanceof  Camera) {
            mViewDataBinding.btnChoose.setText(R.string.scx);
            isCamSelected = true;
            products.add(product);
            kitProductAdapter.notifyItemRangeChanged(0, products.size());
            return;
        }

        if (product instanceof  Lens) {
            Lens lens = (Lens) product;
            if (getCam() != null) {
                if (lens.getCompatibleMount().equalsIgnoreCase(getCam().getMountType())) {
                    products.add(product);
                    kitProductAdapter.notifyItemRangeChanged(0, products.size());
                    Toast.makeText(KitActivity.this, "This Lens is Compatible", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(KitActivity.this, "This Lens and cam  are not Compatible, please choose an adapter", Toast.LENGTH_SHORT).show();
                repository.findCompatibleAdapters(getCam().getMountType(), lens.getCompatibleMount(), new NetworkCallBack<ArrayList<Adapter>>() {
                    @Override
                    public void onError(String message) {
                        runOnUiThread(() -> {
                            Toast.makeText(KitActivity.this, message, Toast.LENGTH_SHORT).show();
                            mViewDataBinding.progressCircular.setVisibility(View.GONE);
                        });
                    }

                    @Override
                    public void onSuccess(ArrayList<Adapter> data, String message) {
                        if (data == null || data.size() ==0){
                            runOnUiThread(() -> {mViewDataBinding.progressCircular.setVisibility(View.GONE);
                                Toast.makeText(KitActivity.this, "Sorry at this moment can't match that lens with the cam", Toast.LENGTH_LONG).show();
                            });
                            return;
                        }
                        runOnUiThread(() -> mViewDataBinding.progressCircular.setVisibility(View.GONE));
                        ArrayList<Product> adapters = new ArrayList<>(data);
                        DialogProductSelect dialogProductSelect = new DialogProductSelect(KitActivity.this, adapters, lens);
                        dialogProductSelect.show(getSupportFragmentManager(), "adapters");
                    }
                });
            }
            return;
        }
        products.add(product);
        kitProductAdapter.notifyItemRangeChanged(0, products.size());
    }

    @Override
    public void onChooseAdapter(Adapter adapter, Lens lens) {
        products.add(adapter);
        products.add(lens);
        kitProductAdapter.notifyItemRangeChanged(0, products.size());
    }
}
