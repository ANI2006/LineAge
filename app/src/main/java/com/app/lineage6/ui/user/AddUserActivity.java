package com.app.lineage6.ui.user;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.app.lineage6.databinding.ActivityAddUserBinding;
import com.app.lineage6.db.Person;
import com.app.lineage6.mvvm.UserViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class AddUserActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    EditText etDate;
    DatePickerDialog.OnDateSetListener setListener;

    ImageView imageView;
    FloatingActionButton button;
    private ActivityAddUserBinding binding;
    private String name,description,gender,date,relation,profileImageUrl;
   // private int age;
    private final String[] genders={" Male"," Female"};
    private final String[] relations={" Brother"," Mother"};




    private UserViewModel userViewModel;
    private Person person;
    private boolean isEdit=false;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding= ActivityAddUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        etDate=findViewById(R.id.et_date);

        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        // initDataPicker();

        initDropDown();
        userViewModel= ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(UserViewModel.class);

        if (getIntent().hasExtra("model")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                person=getIntent().getParcelableExtra("model",Person.class);
            }
            binding.edtName.setText(person.name);

            if (person.gender.equals("Male")) {
                binding.radioButtonMale.setChecked(true);
            } else if (person.gender.equals("Female")) {
                binding.radioButtonFemale.setChecked(true);
            }
            //   binding.edtAge.setText(String.valueOf(projectModel.age));
            binding.edtDescription.setText(person.description);
            binding.etDate.setText(person.date);
            binding.edtRelation.setText(person.relation);



            isEdit=true;


        }
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setShowHideAnimationEnabled(true);

        binding.btnAddUser.setOnClickListener(view -> {
            if (isEdit) {
                name = binding.edtName.getText().toString().trim();
                String selectedGender = "";
                int selectedGenderId = binding.radioGroupGender.getCheckedRadioButtonId();
                if (selectedGenderId == R.id.radioButtonMale) {
                    selectedGender = "Male";
                } else if (selectedGenderId == R.id.radioButtonFemale) {
                    selectedGender = "Female";
                }
                description = binding.edtDescription.getText().toString().trim();
                date = binding.etDate.getText().toString().trim();
                relation = binding.edtRelation.getText().toString().trim();

                person.name = name;
                person.gender = selectedGender;
                person.description = description;
                person.date = date;
                person.relation = relation;

                userViewModel.updateUser(person);
                Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();

                finish();
            } else {
                name = binding.edtName.getText().toString().trim();
                String selectedGender = "";
                int selectedGenderId = binding.radioGroupGender.getCheckedRadioButtonId();
                if (selectedGenderId == R.id.radioButtonMale) {
                    selectedGender = "Male";
                } else if (selectedGenderId == R.id.radioButtonFemale) {
                    selectedGender = "Female";
                }
                description = binding.edtDescription.getText().toString().trim();
                date = binding.etDate.getText().toString().trim();
                relation = binding.edtRelation.getText().toString().trim();

                person = new Person();
                person.name = name;
                person.gender = selectedGender;
                person.description = description;
                person.date = date;
                person.relation = relation;

                userViewModel.insertUser(person);

                Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DatePickerDialog datePickerDialog=new DatePickerDialog(AddUserActivity.this,new DatePickerDialog.OnDateSetListener() {

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
//        binding.radioGroupGender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String selectedGender = parent.getItemAtPosition(position).toString();
//
//                // Update the imageView based on the selected gender
//                if (selectedGender.equalsIgnoreCase("Male")) {
//                    imageView.setImageResource(R.drawable.baseline_favorite_24);
//                } else if (selectedGender.equalsIgnoreCase("Female")) {
//                    imageView.setImageResource(R.drawable.ic_baseline_delete);
//                } else {
//                    imageView.setImageResource(R.drawable.ic_person_24); // Default image
//                }
//            }
//        });



        imageView=findViewById(R.id.imageView);
        button= findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagePicker.with(AddUserActivity.this)
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
        binding.radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
