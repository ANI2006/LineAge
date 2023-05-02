package com.example.lineage6.ui.user;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lineage6.R;
import com.example.lineage6.databinding.FragmentUserBinding;
import com.example.lineage6.databinding.UserItemLayoutBinding;
import com.example.lineage6.ui.OnClickItemInterface;

import java.util.List;

public class UserFragment extends Fragment implements OnClickItemInterface {

    private FragmentUserBinding binding;

    private UserAdapter adapter;

    private UserViewModel userViewModel;

    public UserFragment() {
        // Required empty public constructor
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        binding = FragmentUserBinding.inflate(inflater, container, false);
//        return binding.getRoot();
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.projectRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UserAdapter(this);
        binding.projectRecyclerView.setAdapter(adapter);

        binding.addUser.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), AddUserActivity.class));
        });

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        userViewModel.getAllUserLive().observe(getViewLifecycleOwner(), new Observer<List<ProjectModel>>() {
            @Override
            public void onChanged(List<ProjectModel> projectModelList) {
                if (projectModelList != null) {
                    adapter.setUsers(projectModelList);
                }
            }
        });

        return root;
    }

    @Override
    public void onClickItem(ProjectModel projectModel, boolean isEdit) {
        if (isEdit) {
            Intent intent = new Intent(getContext(), AddUserActivity.class);
            intent.putExtra("model", projectModel);
            startActivity(intent);
        } else {
            userViewModel.deleteUser(projectModel);
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
