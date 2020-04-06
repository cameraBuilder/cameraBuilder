package com.hackathon.camerabuilder;

import android.app.Activity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.hackathon.camerabuilder.api.Repository;

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    protected LoaderDialog loaderDialog;
    protected Repository repository;

    public T mViewDataBinding;

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutResource());
        mViewDataBinding.setLifecycleOwner(this);
    }

    public abstract int getLayoutResource();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        performDataBinding();
        repository = ApplicationContext.getInstance().repository;

        if (loaderDialog == null) {
            loaderDialog = new LoaderDialog(this, "Loading");
        }

    }

    @Override
    public ApplicationContext getApplicationContext() {
        return ApplicationContext.getInstance();
    }

    protected void showLoader(String text, Activity activity) {
        if (loaderDialog == null) {
            loaderDialog = new LoaderDialog(activity, "Loading");
        }
        loaderDialog.setText(text);
        if (loaderDialog.isShowing()) {
            return;
        }
        loaderDialog.show();
    }

    protected  void dismissLoader() {
        loaderDialog.dismiss();
    }
}
