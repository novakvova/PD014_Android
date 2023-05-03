package com.example.sim.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sim.R;
import com.example.sim.application.HomeApplication;
import com.example.sim.constants.Urls;
import com.example.sim.dto.category.CategoryItemDTO;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoryCardViewHolder> {
    private List<CategoryItemDTO> categories;
    private final OnItemClickListener editListener;

    public CategoriesAdapter(List<CategoryItemDTO> categories, OnItemClickListener editListener) {
        this.categories = categories;
        this.editListener = editListener;
    }

    @NonNull
    @Override
    public CategoryCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.category_view, parent, false);
        return new CategoryCardViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryCardViewHolder holder, int position) {
        if(categories!=null && position<categories.size()) {
            CategoryItemDTO cat = categories.get(position);
            holder.categoryName.setText(cat.getName());
            String url = Urls.BASE+cat.getImage();
            Glide.with(HomeApplication.getAppContext())
                    .load(url)
                    .apply(new RequestOptions().override(600))
                    .into(holder.categoryImage);
            holder.btnCategoryEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editListener.OnItemClick(cat);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
