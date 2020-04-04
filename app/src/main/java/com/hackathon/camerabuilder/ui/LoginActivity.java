package com.hackathon.camerabuilder.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hackathon.camerabuilder.BaseActivity;
import com.hackathon.camerabuilder.R;
import com.hackathon.camerabuilder.api.model.NetworkCallBack;
import com.hackathon.camerabuilder.api.model.UserInfo;
import com.hackathon.camerabuilder.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    @Override
    public int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewDataBinding.btnLogin.setOnClickListener(view -> {
            mViewDataBinding.progressCircular.setVisibility(View.VISIBLE);
            repository.login( mViewDataBinding.etEmail.getText().toString(),
                    mViewDataBinding.etPassword.getText().toString(), new NetworkCallBack<UserInfo>() {
                        @Override
                        public void onError(String message) {
                            runOnUiThread(() -> {
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                mViewDataBinding.progressCircular.setVisibility(View.GONE);
                            });
                        }
                        @Override
                        public void onSuccess(UserInfo data, String message) {
                            runOnUiThread(() -> {
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                                repository.setUserInfo(new Gson().toJson(data));
                                MainActivity.launch(LoginActivity.this);
                                mViewDataBinding.progressCircular.setVisibility(View.GONE);
                                finish();
                            });
                        }
                    });
        });

        mViewDataBinding.tvRegister.setOnClickListener(view -> {
            RegisterActivity.launch(this);
        });
    }
}
