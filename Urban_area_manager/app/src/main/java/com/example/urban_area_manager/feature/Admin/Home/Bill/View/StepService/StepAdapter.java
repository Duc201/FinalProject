package com.example.urban_area_manager.feature.Admin.Home.Bill.View.StepService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.example.urban_area_manager.core.recycleview.BaseRecycleAdapter;
import com.example.urban_area_manager.core.recycleview.BaseViewHolder;
import com.example.urban_area_manager.databinding.ItemStepBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.Step;

public class StepAdapter extends BaseRecycleAdapter<Step> {
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClickEdit(Step step);

        void onItemClickDelete(Step step);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public BaseViewHolder<?> createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new StepViewHoder(ItemStepBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    public class StepViewHoder extends BaseViewHolder<ItemStepBinding> {
        public StepViewHoder(@NonNull ItemStepBinding viewBinding) {
            super(viewBinding);
        }

        @Override
        public void bindData(int position) {
            Step step = mData.get(position);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bậc ").append(step.getNameStep());
            getViewBinding().tvNameStep.setText(stringBuilder);
            if (step.getEndValue() == Integer.MAX_VALUE){
                StringBuilder range = new StringBuilder();
                range.append("Khoảng: ").append(step.getStartValue()).append(" trở lên");
                getViewBinding().ranger.setText(range);
            }
            else {
                StringBuilder range = new StringBuilder();
                range.append("Khoảng: ").append(step.getStartValue()).append(" - ").append(step.getEndValue());
                getViewBinding().ranger.setText(range);
            }




            StringBuilder price = new StringBuilder();
            price.append("Giá: ").append(step.getPrice()).append(" VNĐ");
            getViewBinding().price.setText(price);
            getViewBinding().edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClickEdit(step);
                    }
                }
            });
            getViewBinding().delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClickDelete(step);
                }
            });
        }
    }


//    @Override
//    public void submitList(List<Step> data) {
//        Collections.sort(mData, new Comparator<Step>() {
//            @Override
//            public int compare(Step o1, Step o2) {
//                return Integer.compare(o2.getNameStep(), o1.getNameStep());
//            }
//        });
//        super.submitList(data);
//    }


}
