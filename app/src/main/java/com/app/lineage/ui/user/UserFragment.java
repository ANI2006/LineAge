package com.app.lineage.ui.user;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.app.lineage.MainActivity;
import com.app.lineage.db.Relation;
import com.app.lineage.db.UserDao;
import com.app.lineage.mvvm.UserViewModel;
import com.app.lineage.db.OnClickItemInterface;
import com.app.lineage.db.Person;
import com.app.lineage.ui.relations.AddRelationDialog;
import com.app.lineage6.R;
import com.app.lineage6.databinding.FragmentUserBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment implements OnClickItemInterface {

    private FragmentUserBinding binding;
    private SearchView searchView;
    private UserDao userDao;
    private UserAdapter adapter;
    private List<Person> personList = new ArrayList<>();
    public static List<String> personNames =new ArrayList<>();

    private UserViewModel userViewModel;

    public UserFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // Set up the searchView
        searchView = root.findViewById(R.id.search_view);



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

        binding.projectRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UserAdapter(this);
        adapter.setPersonList(personList); // Add this line to set the initial list

        binding.projectRecyclerView.setAdapter(adapter);

        binding.addUser.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), AddUserActivity.class));
        });

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        userViewModel.getAllUserLive().observe(getViewLifecycleOwner(), new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> personList) {
                if (personList != null) {
                    adapter.setUsers(personList);
                   personNames = adapter.getPersonNames();
                    System.out.println(personNames);
                }
            }
        });
        Intent intent = new Intent(getActivity(), UserFragment.class);
        intent.putExtra("value", (Serializable) personNames);
        //startActivity(intent);




        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                showDeleteConfirmationDialog(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(binding.projectRecyclerView);

        return root;
    }


    @Override
    public void onClickItem(Person person, boolean isEdit) {
        if (isEdit) {
            Intent intent = new Intent(getContext(), AddUserActivity.class);
            intent.putExtra("model", person);
            startActivity(intent);
        } else {
            userViewModel.deleteUser(person);
            getActivity().finish();

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void filterList(String text) {
        List<Person> filteredList = new ArrayList<>();
        List<Person> relationList = userViewModel.getPersons().getValue();
        //if(goalList!=null) {
        for (Person person : personList) {
            if (person.name.toLowerCase().startsWith(text.toLowerCase())) {
                filteredList.add(person);
            }
        }
        // }
        if(filteredList.isEmpty()) {
            Toast.makeText(getContext(), "No result found", Toast.LENGTH_SHORT).show();
        }else{
            adapter.setFilteredList(filteredList);
        }
    }
    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to delete this user?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteUser(position);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.notifyItemChanged(position);
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void deleteUser(int position) {
        Person deletedPerson = adapter.getUserAt(position);
        userViewModel.deleteUser(deletedPerson);
        adapter.deleteUser(position);
    }
}

