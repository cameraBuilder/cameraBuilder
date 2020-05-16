package com.hackathon.camerabuilder.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hackathon.camerabuilder.BaseActivity;
import com.hackathon.camerabuilder.R;
import com.hackathon.camerabuilder.api.model.NetworkCallBack;
import com.hackathon.camerabuilder.api.model.UserInfo;
import com.hackathon.camerabuilder.databinding.ActivityRegisterBinding;

import java.util.regex.Pattern;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {

    private boolean isEmail = false;
    private boolean isPassword = false;
    private boolean isUserName = false;


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

        mViewDataBinding.btnRegister.setOnClickListener(view -> {
            mViewDataBinding.progressCircular.setVisibility(View.VISIBLE);
            repository.login( mViewDataBinding.etEmail.getText().toString(),
                    mViewDataBinding.etPassword.getText().toString(), new NetworkCallBack<UserInfo>() {

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
                                repository.setUserInfo(new Gson().toJson(data));
                                MainActivity.launch(RegisterActivity.this);
                                mViewDataBinding.progressCircular.setVisibility(View.GONE);
                                finish();
                            });
                        }});
        });



        mViewDataBinding.etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mViewDataBinding.tvUsername.setVisibility(TextUtils.isEmpty(charSequence.toString()) ? View.VISIBLE:View.GONE);
                isUserName = TextUtils.isEmpty(charSequence.toString());
                mViewDataBinding.btnRegister.setEnabled(isPassword && isUserName && isEmail);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
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
                    mViewDataBinding.btnRegister.setEnabled(false);
                    return;
                }
                isPassword = true;
                mViewDataBinding.btnRegister.setEnabled(isUserName && isEmail);
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
                    mViewDataBinding.btnRegister.setEnabled(false);
                    return;
                }
                isEmail = true;
                mViewDataBinding.btnRegister.setEnabled(isPassword && isUserName);
                mViewDataBinding.tvEmail.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
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
