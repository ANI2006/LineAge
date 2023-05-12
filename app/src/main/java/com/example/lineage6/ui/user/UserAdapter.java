package com.example.lineage6.ui.user;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lineage6.R;
import com.example.lineage6.databinding.UserItemLayoutBinding;
import com.example.lineage6.ui.AppDatabase;
import com.example.lineage6.ui.OnClickItemInterface;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> implements Filterable {

    List<ProjectModel> projectModelList;
    private List<ProjectModel> filteredProjectModelList;

    private OnClickItemInterface onClickItemInterface;
    UserItemLayoutBinding binding;

    public UserAdapter(OnClickItemInterface onClickItemInterface) {
        this.onClickItemInterface = onClickItemInterface;
    }

    public UserAdapter() {
        super() ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        UserItemLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.user_item_layout, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (projectModelList != null) {
            ProjectModel projectModel = projectModelList.get(position);

            holder.binding.setUserModel(projectModel);
            holder.binding.setListener(onClickItemInterface);

//            if (projectModel.imagePath != null) {
//                Glide.with(holder.itemView.getContext())
//                        .load(projectModel.imagePath)
//                        .into(holder.binding.userImage);
//            } else {
//                holder.binding.userImage.setImageResource(R.drawable.);
//            }
        }



    }

    @Override
    public int getItemCount() {
        if (projectModelList != null)
            return projectModelList.size();
        else return 0;
    }

    public void setUsers(List<ProjectModel> projects) {
        projectModelList = projects;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString();
                if (query.isEmpty()) {
                    filteredProjectModelList = projectModelList;
                } else {
                    List<ProjectModel> filteredList = new ArrayList<>();
                    for (ProjectModel projectModel : projectModelList) {
                        if (projectModel.firstName.toLowerCase().contains(query.toLowerCase()) ||
                                projectModel.lastName.toLowerCase().contains(query.toLowerCase())) {
                            filteredList.add(projectModel);
                        }
                    }
                    filteredProjectModelList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredProjectModelList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredProjectModelList = (List<ProjectModel>) results.values;
                notifyDataSetChanged();
            }
    };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        UserItemLayoutBinding binding;

        public ViewHolder(@NonNull UserItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }

}
