package com.example.lineage6.ui.user;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lineage6.R;
import com.example.lineage6.databinding.UserItemLayoutBinding;
import com.example.lineage6.ui.OnClickItemInterface;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    List<ProjectModel> projectModelList;
    private OnClickItemInterface onClickItemInterface;

    public UserAdapter(OnClickItemInterface onClickItemInterface) {
        this.onClickItemInterface = onClickItemInterface;
    }

    public UserAdapter() {
        super();
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        UserItemLayoutBinding binding;

        public ViewHolder(@NonNull UserItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}