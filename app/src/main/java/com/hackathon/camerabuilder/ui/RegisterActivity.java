package com.hackathon.camerabuilder.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.hackathon.camerabuilder.BaseActivity;
import com.hackathon.camerabuilder.R;
import com.hackathon.camerabuilder.api.model.NetworkCallBack;
import com.hackathon.camerabuilder.api.model.UserInfo;
import com.hackathon.camerabuilder.databinding.ActivityRegisterBinding;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {

    @Override
    public int getLayoutResource() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewDataBinding.toolbar.setNavigationOnClickListener(view -> {
           onBackPressed();
        });
    }

    public static void launch(Activity activity) {
        activity.startActivity(new Intent(activity, RegisterActivity.class));
    }

    public void register(View view) {
        mViewDataBinding.progressCircular.setVisibility(View.VISIBLE);
        repository.register( mViewDataBinding.etEmail.getText().toString(),
                mViewDataBinding.etPassword.getText().toString(), mViewDataBinding.etUsername.getText().toString(),new NetworkCallBack<UserInfo>() {
                    @Override
                    public void onError(String message) {
                        runOnUiThread(() -> {
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                            mViewDataBinding.progressCircular.setVisibility(View.GONE);
                        });
                    }
                    @Override
                    public void onSuccess(UserInfo data, String message) {
                        runOnUiThread(() -> {
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                            mViewDataBinding.etPassword.setText("");
                            mViewDataBinding.etUsername.setText("");
                            mViewDataBinding.etEmail.setText("");
                            mViewDataBinding.progressCircular.setVisibility(View.GONE);
                            onBackPressed();
                        });
                    }
                });
    }
}
