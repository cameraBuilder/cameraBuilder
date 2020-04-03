package com.hackathon.camerabuilder;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import androidx.annotation.NonNull;

import java.util.Objects;

public class LoaderDialog extends Dialog {


    public LoaderDialog(@NonNull Context context, String text) {
        super(context);
        setContentView(R.layout.layout_loader);
        setCancelable(false);
        Window window = getWindow();
        Objects.requireNonNull(window).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView textView = findViewById(R.id.tv_operation);
        textView.setText(text);
    }

    public void setText(String text) {
        TextView textView = findViewById(R.id.tv_operation);
        textView.setText(text);
    }

}
