package com.hackathon.camerabuilder.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hackathon.camerabuilder.R;
import com.hackathon.camerabuilder.api.model.Lens;
import com.hackathon.camerabuilder.api.model.Product;
import java.util.ArrayList;
import java.util.Objects;


public class DialogProductSelect extends DialogFragment {

    private DialogActionListener onChoose;
    private ProductsSelectorAdapter productsSelectorAdapter;

    public DialogProductSelect(DialogActionListener onChoose, ArrayList<Product> products) {
        this.onChoose = onChoose;
        productsSelectorAdapter = new ProductsSelectorAdapter(products);
    }

    private Lens lens;
    public DialogProductSelect(DialogActionListener onChoose, ArrayList<Product> products, Lens lens) {
        this.onChoose = onChoose;
        productsSelectorAdapter = new ProductsSelectorAdapter(products);
        this.lens = lens;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_product_select, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_select).setOnClickListener(view12 -> {
            if (productsSelectorAdapter.getSelectedProduct() instanceof Adapter) {
                onChoose.onChooseAdapter((Adapter)productsSelectorAdapter.getSelectedProduct(), lens);
                dismiss();
                return;
            }
            onChoose.onChooseProduct(productsSelectorAdapter.getSelectedProduct());
            dismiss();
        });
        view.findViewById(R.id.btn_close).setOnClickListener(view1 -> dismiss());
        RecyclerView recyclerView = view.findViewById(R.id.rv_products_selector);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(productsSelectorAdapter);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {

    }


    public interface DialogActionListener{
        void onChooseProduct(Product product);
        void onChooseAdapter(Adapter adapter, Lens lens);
    }

}
