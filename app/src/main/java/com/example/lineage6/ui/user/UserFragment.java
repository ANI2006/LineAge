package com.example.lineage6.ui.user;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lineage6.databinding.FragmentUserBinding;
import com.example.lineage6.db.OnClickItemInterface;
import com.example.lineage6.db.Person;
import com.example.lineage6.mvvm.UserViewModel;
import com.example.lineage6.ui.relations.RelationsFragment;

import java.util.List;

    public class UserFragment extends Fragment implements OnClickItemInterface {

        private FragmentUserBinding binding;
        private SharedPreferences sharedPreferences;
        private SearchView searchView;

        private UserAdapter adapter;
        private List<Person> favoriteList;

        private UserViewModel userViewModel;
        private ToggleButton toggleButton;
        private RelationsFragment favoritesFragment;


        public UserFragment() {
            // Required empty public constructor
        }



        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            binding = FragmentUserBinding.inflate(inflater, container, false);
            View root = binding.getRoot();

            super.onCreate(savedInstanceState);





            binding.projectRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new UserAdapter(this);
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
                    }
                }
            });

            searchView =binding.searchView;
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    adapter.getFilter().filter(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    return false;
                }
            });



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
            }
        }



        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }


    }
