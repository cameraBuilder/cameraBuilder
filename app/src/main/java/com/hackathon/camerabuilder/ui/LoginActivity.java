package com.hackathon.camerabuilder.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;
import com.google.gson.Gson;
import com.hackathon.camerabuilder.BaseActivity;
import com.hackathon.camerabuilder.R;
import com.hackathon.camerabuilder.api.model.NetworkCallBack;
import com.hackathon.camerabuilder.api.model.UserInfo;
import com.hackathon.camerabuilder.databinding.ActivityLoginBinding;
import java.util.regex.Pattern;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    @Override
    public int getLayoutResource() {
        return R.layout.activity_login;
    }

    private boolean isEmail = false;
    private boolean isPassword = false;
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
                }});
        });

        mViewDataBinding.tvRegister.setOnClickListener(view -> {
            RegisterActivity.launch(this);
        });


        mViewDataBinding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Pattern pattern = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%?=*&]).{8,20})");
                final boolean isMatched = pattern.matcher(charSequence).find();
                if (!isMatched) {
                    isPassword = false;
                    mViewDataBinding.tvPassword.setVisibility(View.VISIBLE);
                    mViewDataBinding.tvPassword.setText(getString(R.string.password_mut_contain_at_least_8_characters_with_upper_case_and_special_characters));
                    mViewDataBinding.btnLogin.setEnabled(false);
                    return;
                }
                isPassword = true;
                mViewDataBinding.btnLogin.setEnabled(isEmail);
                mViewDataBinding.tvPassword.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mViewDataBinding.etEmail.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                final boolean isMatched = pattern.matcher(charSequence).find();
                if (!isMatched) {
                    mViewDataBinding.tvEmail.setVisibility(View.VISIBLE);
                    mViewDataBinding.tvEmail.setText(getString(R.string.invalid_email));
                    isEmail = false;
                    mViewDataBinding.btnLogin.setEnabled(false);
                    return;
                }
                isEmail = true;
                mViewDataBinding.btnLogin.setEnabled(isPassword);
                mViewDataBinding.tvEmail.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
