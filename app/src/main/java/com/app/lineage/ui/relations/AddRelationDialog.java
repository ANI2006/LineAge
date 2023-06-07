package com.app.lineage.ui.relations;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;


import com.app.lineage.mvvm.RelationViewModel;
import com.app.lineage.ui.user.UserFragment;
import com.app.lineage6.R;
import com.app.lineage6.databinding.DialogAddRelationBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AddRelationDialog extends DialogFragment {
    private DialogAddRelationBinding binding;
    private RelationViewModel relationViewModel;
    private String[] relationList;
    Dialog dialog;

    private UserFragment userFragment;
    List<String> personNames1;


    private String relation;
    private String[] personNamesArray;





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
//        Intent intent = getActivity().getIntent();
//        ArrayList<String> personNames1 = (ArrayList<String>) intent.getSerializableExtra("value");


        relationList = getResources().getStringArray(R.array.relation_list);
       personNames1=UserFragment.personNames;
//        String[] personNamesArray = personNames.toArray(new String[personNames.size()]);
//
//        System.out.println(Arrays.toString(personNamesArray)); // Print the contents of the array
//
//
        searchableSpinner();
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
        binding.relationBetween.setThreshold(1);


    }
    private void searchableSpinner(){
        TextView textView1=binding.person1;
        TextView textView2=binding.person2;
        binding.person1.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                dialog=new Dialog(getContext());

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner);

                // set custom height and width
                dialog.getWindow().setLayout(650,800);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText=dialog.findViewById(R.id.edit_text);
                ListView listView=dialog.findViewById(R.id.list_view);

                // Initialize array adapter
                ArrayAdapter<String> adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,personNames1);

                // set adapter
                listView.setAdapter(adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // when item selected from list
                        // set selected item on textView
                        textView1.setText(adapter.getItem(position));


                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });
            }
        });
        binding.person2.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                dialog=new Dialog(getContext());

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner);

                // set custom height and width
                dialog.getWindow().setLayout(650,800);

                // set transparent background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // show dialog
                dialog.show();

                // Initialize and assign variable
                EditText editText=dialog.findViewById(R.id.edit_text);
                ListView listView=dialog.findViewById(R.id.list_view);

                // Initialize array adapter
                ArrayAdapter<String> adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,personNames1);

                // set adapter
                listView.setAdapter(adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // when item selected from list
                        // set selected item on textView
                        textView2.setText(adapter.getItem(position));


                        // Dismiss dialog
                        dialog.dismiss();
                    }
                });
            }
        });
    }



}
