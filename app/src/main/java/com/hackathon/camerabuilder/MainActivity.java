package com.hackathon.camerabuilder;

import android.os.Bundle;
import android.widget.Toast;

import com.hackathon.camerabuilder.api.model.NetworkCallBack;
import com.hackathon.camerabuilder.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository.test(new NetworkCallBack<String>() {
            @Override
            public void onError(String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onSuccess(String data, String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mViewDataBinding.tvHello.setText(message);
                    }
                });
            }
        });
    }
}
