package com.example.lineage6.ui.user;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.lineage6.R;
import com.example.lineage6.databinding.ActivityAddUserBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class AddUserActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    EditText etDate;
    DatePickerDialog.OnDateSetListener setListener;

    ImageView imageView;
    FloatingActionButton button;
    private ActivityAddUserBinding binding;
    private String firstName,lastName,description,gender,date;
   // private int age;
    private final String[] genders={" Male"," Female"};
    private UserViewModel userViewModel;
    private ProjectModel projectModel;
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
                projectModel=getIntent().getParcelableExtra("model",ProjectModel.class);
            }
            binding.edtFirstName.setText(projectModel.firstName);
            binding.edtLastName.setText(projectModel.lastName);
            binding.edtGender.setText(projectModel.gender);
         //   binding.edtAge.setText(String.valueOf(projectModel.age));
            binding.edtDescription.setText(projectModel.description);
            binding.etDate.setText(projectModel.date);

            isEdit=true;


        }
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setShowHideAnimationEnabled(true);

        binding.btnAddUser.setOnClickListener(view -> {

            if(isEdit){
                firstName=binding.edtFirstName.getText().toString().trim();
                lastName=binding.edtLastName.getText().toString().trim();
                gender=binding.edtGender.getText().toString().trim();
             //   age=Integer.parseInt(binding.edtAge.getText().toString().trim());
                description=binding.edtDescription.getText().toString().trim();
                date=binding.etDate.getText().toString().trim();





                projectModel.firstName=firstName;
                projectModel.lastName=lastName;
                projectModel.gender=gender;
               // projectModel.age=age;
                projectModel.description=description;
                projectModel.date=date;



                userViewModel.updateUser(projectModel);
                Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();

                finish();
            }else {
                firstName=binding.edtFirstName.getText().toString().trim();
                lastName=binding.edtLastName.getText().toString().trim();
                gender=binding.edtGender.getText().toString().trim();
              //  age=Integer.parseInt(binding.edtAge.getText().toString().trim());
                description=binding.edtDescription.getText().toString().trim();
                date=binding.etDate.getText().toString().trim();




                projectModel=new ProjectModel();
                projectModel.firstName=firstName;
                projectModel.lastName=lastName;
                projectModel.gender=gender;
               // projectModel.age=age;
                projectModel.description=description;
                projectModel.date=date;


                userViewModel.insertUser(projectModel);

                Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();


                finish();

            }

        });
//        etDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatePickerDialog datePickerDialog=new DatePickerDialog(AddUserActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
//                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                datePickerDialog.show();
//
//
//            }
//        });
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






     //   Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getColor(R.color.red)));
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

    private void initDropDown(){
        // Create an ArrayAdapter for genders
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, genders);
        binding.edtGender.setAdapter(genderAdapter);
        binding.edtGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                gender = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });


    }


}
