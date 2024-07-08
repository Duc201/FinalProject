package com.example.urban_area_manager.core.recycleview;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

public abstract class BaseViewHolder1 <VB extends ViewBinding> extends RecyclerView.ViewHolder {

    private final VB viewBinding;

    public BaseViewHolder1(VB viewBinding) {
        super(viewBinding.getRoot());
        this.viewBinding = viewBinding;
    }

    public VB getViewBinding() {
        return viewBinding;
    }

    public abstract void bindData(Object item);
}
