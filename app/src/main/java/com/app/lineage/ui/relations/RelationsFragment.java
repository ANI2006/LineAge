package com.app.lineage.ui.relations;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.app.lineage.mvvm.RelationViewModel;
import com.app.lineage.db.Relation;
import com.app.lineage.ui.user.UserAdapter;
import com.app.lineage6.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class RelationsFragment extends Fragment {
    private RelationsAdapter relationsAdapter;
    private RelationViewModel relationViewModel;

    private SearchView searchView;
    ActivityResultLauncher<Intent> infoLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                //goalsViewModel.refresh();
            });

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_relations, container, false);
        searchView = root.findViewById(R.id.search_view);
        searchView.clearFocus();




        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
        RecyclerView recyclerView = root.findViewById(R.id.relation_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        relationsAdapter = new RelationsAdapter();
        recyclerView.setAdapter(relationsAdapter);

        relationViewModel = new ViewModelProvider(requireActivity()).get(RelationViewModel.class);
        relationViewModel.getRelations().observe(getViewLifecycleOwner(), relations -> {
            relationsAdapter.setRelationList(relations);
        });

        FloatingActionButton fab = root.findViewById(R.id.add_relation);
        fab.setOnClickListener(view -> {
            AddRelationDialog addRelationDialog = new AddRelationDialog();
            addRelationDialog.show(getChildFragmentManager(), "add_relation");
        });

        return root;
    }

    private void filterList(String text) {
        List<Relation> filteredList = new ArrayList<>();
        List<Relation> relationList = relationViewModel.getRelations().getValue();

        for (Relation relation : relationList) {
            if (relation.person1.toLowerCase().startsWith(text.toLowerCase()) || relation.person2.toLowerCase().startsWith(text.toLowerCase()) ||relation.relationBetween.toLowerCase().startsWith(text.toLowerCase())) {
                filteredList.add(relation);
            }
        }
        // }
        if(filteredList.isEmpty()) {
            Toast.makeText(getContext(), "No result found", Toast.LENGTH_SHORT).show();
        }else{
            relationsAdapter.setFilteredList(filteredList);
        }
    }

    private class RelationsAdapter extends RecyclerView.Adapter<RelationsAdapter.RelationCardHolder> {
        List<Relation> relationList;

        public void setRelationList(List<Relation> relationList) {
            // this.goalList = goalList;
            this.relationList = new ArrayList<>(relationList);
            notifyDataSetChanged();
        }
        public void setFilteredList(List<Relation> filteredList){
            this.relationList = filteredList;
            notifyDataSetChanged();
        }
        public RelationsAdapter.RelationCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            return new RelationsAdapter.RelationCardHolder(inflater.inflate(R.layout.view_relation_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder (@NonNull RelationsAdapter.RelationCardHolder holder,int position) {
            holder.person1.setText(relationList.get(position).person1);
            holder.person2.setText(relationList.get(position).person2);
            holder.relationBetween.setText(relationList.get(position).relationBetween);


        }


        @Override
        public int getItemCount() {
            return relationList != null ? relationList.size() : 0;
        }

        public class RelationCardHolder extends RecyclerView.ViewHolder {
            TextView person1;
            TextView relationBetween;
            TextView person2;

            ImageView delete;


            public RelationCardHolder(View view) {
                super(view);
                person1 = view.findViewById(R.id.person1_tv);
                relationBetween = view.findViewById(R.id.relation_between_tv);
                person2 = view.findViewById(R.id.person2_tv);

                delete = view.findViewById(R.id.goal_delete);

                delete.setOnClickListener(v -> relationViewModel.deleteRelation(relationList.get(getAdapterPosition())));

//                view.setOnClickListener(v -> {
//                    Intent i = new Intent(getActivity(), GoalInfoActivity.class);
//                    i.putExtra(Constants.GOAL_ID, goalList.get(getAdapterPosition()).goalId);
//                    i.putExtra(Constants.GOAL_NAME, goalList.get(getAdapterPosition()).goalName);
//                    infoLauncher.launch(i);
//                });
            }
        }
    }
}