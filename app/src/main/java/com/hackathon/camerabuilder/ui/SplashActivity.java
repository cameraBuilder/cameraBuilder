package com.hackathon.camerabuilder.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.hackathon.camerabuilder.BaseActivity;
import com.hackathon.camerabuilder.R;
import com.hackathon.camerabuilder.databinding.ActivitySplashBinding;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    @Override
    public int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(() -> {
            if (repository == null || repository.getUserInfo() == null) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
            else {
                MainActivity.launch(SplashActivity.this);
            }
            finish();
        }, 2000);
    }
}
