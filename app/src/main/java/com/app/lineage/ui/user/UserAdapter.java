package com.app.lineage.ui.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;



import com.app.lineage.db.OnClickItemInterface;
import com.app.lineage.db.Person;
import com.app.lineage.db.Relation;
import com.app.lineage6.R;
import com.app.lineage6.databinding.UserItemLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>  implements Filterable{

    List<Person> personList;
    private List<Person> filteredPersonList;
    private List<Person> personNames;
    Person person;
    public List<Person> getPersonListFilter=new ArrayList<>();

    private OnClickItemInterface onClickItemInterface;
    UserItemLayoutBinding binding;

    public UserAdapter(OnClickItemInterface onClickItemInterface) {
        this.onClickItemInterface = onClickItemInterface;
        this.personList = new ArrayList<>();
        this.getPersonListFilter = new ArrayList<>();
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
            holder.binding.setListener(onClickItemInterface);}



    }


    @Override
    public int getItemCount() {
        if (personList != null)
            return personList.size();
        else return 0;
    }

    public void setUsers(List<Person> personList) {
        this.personList.clear();
        this.personList.addAll(personList);
        this.getPersonListFilter.clear();
        this.getPersonListFilter.addAll(personList);
        notifyDataSetChanged();
    }

    public void setFilteredList(List<Person> filteredList){
        this.personList = filteredList;
        notifyDataSetChanged();
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<Person> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(getPersonListFilter);
                } else {
                    String searchStr = constraint.toString().toLowerCase();
                    for (Person person : getPersonListFilter) {
                        if (person.name.toLowerCase().contains(searchStr)) {
                            filteredList.add(person);
                        }
                    }
                }

                filterResults.values = filteredList;
                filterResults.count = filteredList.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                personList.clear();
                personList.addAll((List<Person>) results.values);
                notifyDataSetChanged();
            }
        };

        return filter;
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        UserItemLayoutBinding binding;


        public ViewHolder(@NonNull UserItemLayoutBinding binding) {
            super(binding.getRoot());

//            if (person != null && person.imageUrl==null){
//                if (person.gender == "Male") {
//                    binding.userImage.setImageResource(R.drawable.male_profile);
//                } else if (person.gender == "Female") {
//                    binding.userImage.setImageResource(R.drawable.male_profile);
//
//                } else {
//                    binding.userImage.setImageResource(R.drawable.ic_person_24);
//
//
//                }
//            }

            this.binding = binding;
            binding.view.setVisibility(View.VISIBLE);


        }


    }
    public  void deleteUser(int position){
        this.personList.remove(position);
        notifyItemRemoved(position);
    }
    public Person getUserAt(int position){
        return personList.get(position);
    }
    public void setPersonList(List<Person> persons) {
        personList = persons;
        getPersonListFilter = persons;
        notifyDataSetChanged();
    }



}
