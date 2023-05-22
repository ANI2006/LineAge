package com.example.lineage6.db;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class ProjectModel implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int uId;

    @ColumnInfo(name="u_first_name")
    public String firstName;

    public String lastName;

    public String date;
    public String gender;
    public String description;
    public String relation;






    //public Bitmap  image;
   // public String imagePath;

//    @TypeConverter
//    public static byte[] bitmapToByteArray(Bitmap bitmap) {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
//        return outputStream.toByteArray();
//    }
//
//    @TypeConverter
//    public static Bitmap byteArrayToBitmap(byte[] byteArray) {
//        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//    }


    public ProjectModel() {

    }

    protected ProjectModel(Parcel in) {
        uId = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        gender = in.readString();
        description = in.readString();
        relation = in.readString();
        date = in.readString();

       // imagePath = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uId);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(gender);
        dest.writeString(description);
        dest.writeString(relation);
        dest.writeString(date);



        // dest.writeString(imagePath);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public static final Creator<ProjectModel> CREATOR = new Creator<ProjectModel>() {
        @Override
        public ProjectModel createFromParcel(Parcel in) {
            return new ProjectModel(in);
        }

        @Override
        public ProjectModel[] newArray(int size) {
            return new ProjectModel[size];
        }
    };
}