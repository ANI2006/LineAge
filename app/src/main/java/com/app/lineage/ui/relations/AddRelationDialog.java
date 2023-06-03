package com.app.lineage.ui.relations;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;


import com.app.lineage.mvvm.RelationViewModel;
import com.app.lineage.ui.user.UserFragment;
import com.app.lineage6.R;
import com.app.lineage6.databinding.DialogAddRelationBinding;


public class AddRelationDialog extends DialogFragment {
    private DialogAddRelationBinding binding;
    private RelationViewModel relationViewModel;
    private String[] relationList;
    private UserFragment userFragment;
    private String[] personNames;

    private String relation;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_relation, container, false);
        return binding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        getDialog().getWindow().setLayout((95 * width)/100, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        relationList = getResources().getStringArray(R.array.relation_list);
//        List<String> personNames = userFragment.getPersonNames();


        initDropDown();


        relationViewModel = new ViewModelProvider(requireActivity()).get(RelationViewModel.class);

        binding.relationAddButton.setOnClickListener(v -> {
                    relationViewModel.addRelation(binding.person1.getText().toString(),
                            binding.relationBetween.getText().toString(),
                            binding.person2.getText().toString() );
                    dismiss();
                }
        );
    }

    private void initDropDown() {
        // Create an ArrayAdapter for relations
        ArrayAdapter<String> relationAdapter = new ArrayAdapter<>(getContext(), android.R.layout.select_dialog_item, relationList);
        binding.relationBetween.setAdapter(relationAdapter);
        binding.relationBetween.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                relation = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });
//        ArrayAdapter<String> nameAdapter = new ArrayAdapter<>(getContext(), android.R.layout.select_dialog_item, personNames);
//        binding.person1.setAdapter(nameAdapter);
//        binding.person2.setAdapter(nameAdapter);
//
//        binding.person1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                relation = (String) adapterView.getItemAtPosition(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                // Do nothing
//            }
//        });
//        binding.person2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                relation = (String) adapterView.getItemAtPosition(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                // Do nothing
//            }
//        });

    }

}
