package com.hackathon.camerabuilder.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.hackathon.camerabuilder.BaseActivity;
import com.hackathon.camerabuilder.R;
import com.hackathon.camerabuilder.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }

    public static void launch(Activity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewDataBinding.tvUsername.setText(repository.getUserInfo().getUsername());
        mViewDataBinding.btnLogout.setOnClickListener(view -> {
            repository.logout();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finishAffinity();
        });
    }

    public void openProduct(View view) {
        ProductsActivity.launch(this,(String) view.getTag());
    }

    public void openKits(View view) {
        KitsActivity.launch(this);
    }
}
