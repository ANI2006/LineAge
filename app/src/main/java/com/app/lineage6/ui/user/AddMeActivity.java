package com.app.lineage6.ui.user;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.app.lineage6.R;
import com.app.lineage6.databinding.ActivityAddMeBinding;
import com.app.lineage6.db.Person;
import com.app.lineage6.mvvm.UserViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class AddMeActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    EditText etDate;
    DatePickerDialog.OnDateSetListener setListener;

    ImageView imageView;
    FloatingActionButton button;
    private ActivityAddMeBinding binding;
    private String name,description,date;
    // private int age;

    private UserViewModel userViewModel;
    private Person person;
    private boolean isEdit=false;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding= ActivityAddMeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        etDate=findViewById(R.id.et_date_m);
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        initDropDown();
        userViewModel= ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(UserViewModel.class);

        if (getIntent().hasExtra("model")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                person=getIntent().getParcelableExtra("model",Person.class);
            }
            binding.edtNameM.setText(person.name);

            if (person.gender.equals("Male")) {
                binding.radioButtonMale.setChecked(true);
            } else if (person.gender.equals("Female")) {
                binding.radioButtonFemale.setChecked(true);
            }
            binding.edtDescription.setText(person.description);
            binding.etDateM.setText(person.date);

            isEdit=true;

        }


        binding.btnAddUserM.setOnClickListener(view -> {
            if (isEdit) {
                name = binding.edtNameM.getText().toString().trim();
                String selectedGender = "";
                int selectedGenderId = binding.radioGroupGenderM.getCheckedRadioButtonId();
                if (selectedGenderId == R.id.radioButtonMale) {
                    selectedGender = "Male";
                } else if (selectedGenderId == R.id.radioButtonFemale) {
                    selectedGender = "Female";
                }
                description = binding.edtDescription.getText().toString().trim();
                date = binding.etDateM.getText().toString().trim();

                person.name = name;
                person.gender = selectedGender;
                person.description = description;
                person.date = date;

                userViewModel.updateUser(person);
                Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();

                finish();
            } else {
                name = binding.edtNameM.getText().toString().trim();
                String selectedGender = "";
                int selectedGenderId = binding.radioGroupGenderM.getCheckedRadioButtonId();
                if (selectedGenderId == R.id.radioButtonMale) {
                    selectedGender = "Male";
                } else if (selectedGenderId == R.id.radioButtonFemale) {
                    selectedGender = "Female";
                }
                description = binding.edtDescription.getText().toString().trim();
                date = binding.etDateM.getText().toString().trim();
                person = new Person();
                person.name = name;
                person.gender = selectedGender;
                person.description = description;
                person.date = date;

                userViewModel.insertUser(person);

                Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(AddMeActivity.this,new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month=month+1;
                        String date=dayOfMonth+"/"+month+"/"+year;
                        etDate.setText(date);

                    }
                },year,month,day);
                datePickerDialog.show();
            }

        });

        imageView=findViewById(R.id.imageView);
        button= findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagePicker.with(AddMeActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri=data.getData();
        imageView.setImageURI(uri);

    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void initDropDown() {
        imageView = findViewById(R.id.imageView);
        binding.radioGroupGenderM.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                String selectedGender = radioButton.getText().toString().trim();

                // Update the imageView based on the selected gender
                if (selectedGender.equalsIgnoreCase("Male")) {
                    imageView.setImageResource(R.drawable.male_profile);
                } else if (selectedGender.equalsIgnoreCase("Female")) {
                    imageView.setImageResource(R.drawable.female_profile);
                } else {
                    imageView.setImageResource(R.drawable.ic_person_24); // Default image
                }
            }
        });

    }

}

