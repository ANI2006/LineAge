package com.example.lineage6.ui.user;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lineage6.R;
import com.example.lineage6.databinding.DialogAddRelationBinding;
import com.example.lineage6.mvvm.RelationViewModel;

public class AddRelationDialog extends DialogFragment {
    private DialogAddRelationBinding binding;
    private RelationViewModel relationViewModel;

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


        relationViewModel = new ViewModelProvider(requireActivity()).get(RelationViewModel.class);

        binding.relationAddButton.setOnClickListener(v -> {
                    relationViewModel.addRelation(binding.person1.getText().toString(),binding.relationBetween.getText().toString(),binding.person2.getText().toString() );
                    dismiss();
                }
        );
    }
}
