package com.app.lineage6.ui.user;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.app.lineage6.R;
import com.app.lineage6.databinding.UserItemLayoutBinding;
import com.app.lineage6.db.OnClickItemInterface;
import com.app.lineage6.db.Person;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> implements Filterable {

    List<Person> personList;
    private List<Person> filteredPersonList;

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
        if (personList != null) {
            Person person = personList.get(position);

            holder.binding.setUserModel(person);
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
        if (personList != null)
            return personList.size();
        else return 0;
    }

    public void setUsers(List<Person> projects) {
        personList = projects;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString();
                if (query.isEmpty()) {
                    filteredPersonList = personList;
                } else {
                    List<Person> filteredList = new ArrayList<>();
                    for (Person projectModel : personList) {
                        if (projectModel.name.toLowerCase().contains(query.toLowerCase()) ) {
                            filteredList.add(projectModel);
                        }
                    }
                    filteredPersonList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredPersonList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredPersonList = (List<Person>) results.values;
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
