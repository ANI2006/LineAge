package com.example.lineage6.ui.user;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
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

import java.util.Calendar;
import java.util.Objects;

public class AddUserActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    ImageView imageView;
    FloatingActionButton button;
    private EditText etSelectDate;
    private ActivityAddUserBinding binding;
    private String firstName,lastName,description,gender,relation;
    private int age;
    private final String[] genders={" Male"," Female"};
    private final String[] relations={" Brother"," Mother"};
    private UserViewModel userViewModel;
    private ProjectModel projectModel;
    private boolean isEdit=false;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding= ActivityAddUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
            binding.edtAge.setText(String.valueOf(projectModel.age));
            binding.edtDescription.setText(projectModel.description);
            binding.edtRelation.setText(projectModel.relation);

            isEdit=true;


        }
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setShowHideAnimationEnabled(true);

        binding.btnAddUser.setOnClickListener(view -> {

            if(isEdit){
                firstName=binding.edtFirstName.getText().toString().trim();
                lastName=binding.edtLastName.getText().toString().trim();
                gender=binding.edtGender.getText().toString().trim();
                age=Integer.parseInt(binding.edtAge.getText().toString().trim());
                description=binding.edtDescription.getText().toString().trim();
                relation=binding.edtRelation.getText().toString().trim();



                projectModel.firstName=firstName;
                projectModel.lastName=lastName;
                projectModel.gender=gender;
                projectModel.age=age;
                projectModel.description=description;
                projectModel.relation=relation;


                userViewModel.updateUser(projectModel);
                Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();

                finish();
            }else {
                firstName=binding.edtFirstName.getText().toString().trim();
                lastName=binding.edtLastName.getText().toString().trim();
                gender=binding.edtGender.getText().toString().trim();
                age=Integer.parseInt(binding.edtAge.getText().toString().trim());
                description=binding.edtDescription.getText().toString().trim();
                relation=binding.edtRelation.getText().toString().trim();


                projectModel=new ProjectModel();
                projectModel.firstName=firstName;
                projectModel.lastName=lastName;
                projectModel.gender=gender;
                projectModel.age=age;
                projectModel.description=description;
                projectModel.relation=relation;


                userViewModel.insertUser(projectModel);

                Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();


                finish();

            }
//            dateButton=findViewById(R.id.edtAge);
//            dateButton.setText(getTodaysDay());

//            etSelectDate=findViewById(R.id.edtAge);
//            final Calendar calendar=Calendar.getInstance();
//            final int year=calendar.get(Calendar.YEAR);
//            final int month=calendar.get(Calendar.MONTH);
//            final int day=calendar.get(Calendar.DAY_OF_MONTH);
//            etSelectDate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    DatePickerDialog dialog=new DatePickerDialog(AddUserActivity.this, new DatePickerDialog.OnDateSetListener() {
//                        @Override
//                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                            month=month+1;
//                            String date=dayOfMonth+"/"+month+"/"+year;
//                            etSelectDate.setText(date);
//                        }
//                    },year,month,day);
//                    dialog.show();
//
//
//                }
//            });


        });

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getColor(R.color.black)));
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

//    private String getTodaysDay() {
//        Calendar cal=Calendar.getInstance();
//        int year=cal.get(Calendar.YEAR);
//        int month=cal.get(Calendar.MONTH);
//        month=month+1;
//        int day=cal.get(Calendar.DAY_OF_MONTH);
//        return makeDateString(day,month,year);
//    }


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

        // Create an ArrayAdapter for relations
        ArrayAdapter<String> relationAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, relations);
        binding.edtRelation.setAdapter(relationAdapter);
        binding.edtRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                relation = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });
    }


//    private void initDataPicker() {
//        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                month=month+1;
//                String date=makeDateString(dayOfMonth,month,year);
//                dateButton.setText(date);
//            }
//        };
//        Calendar cal=Calendar.getInstance();
//        int year=cal.get(Calendar.YEAR);
//        int month=cal.get(Calendar.MONTH);
//        int day=cal.get(Calendar.DAY_OF_MONTH);
//        int style= AlertDialog.BUTTON_POSITIVE;
//        datePickerDialog=new DatePickerDialog(this,style,dateSetListener,year,month,day);
//
//
//
//    }

//    private String makeDateString(int dayOfMonth, int month, int year) {
//        return getMonthFormat(month) +" "+dayOfMonth+" "+year;
//    }
//
//    private String getMonthFormat(int month) {
//        if(month==1)return "JAN";
//        if(month==2)return "FEB";
//        if(month==3)return "MARCH";
//        if(month==4)return "APRIL";
//        if(month==5)return "MAY";
//        if(month==6)return "JUN";
//        if(month==7)return "JULY";
//        if(month==8)return "AUG";
//        if(month==9)return "SEP";
//        if(month==10)return "OCT";
//        if(month==11)return "NOV";
//        if(month==12)return "DEC";
//
//
//        return "JAN";
//    }
//
//    public void openDatePicker(View view) {
//
//        datePickerDialog.show();
//    }
}
