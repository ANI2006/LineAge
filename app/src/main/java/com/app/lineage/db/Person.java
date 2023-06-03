package com.app.lineage.db;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class Person implements Parcelable {
    @PrimaryKey(autoGenerate = true)

    public int uId;

    @ColumnInfo(name="name")
    public String name;


    public String date;
    public String gender;
    public String description;
    public String relation;
    public String imageUrl;






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


    public Person() {

    }

    protected Person(Parcel in) {
        uId = in.readInt();
       name = in.readString();
        gender = in.readString();
        description = in.readString();
        relation = in.readString();
        date = in.readString();
        imageUrl = in.readString();


        // imagePath = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uId);
        dest.writeString(name);

        dest.writeString(gender);
        dest.writeString(description);
        dest.writeString(relation);
        dest.writeString(date);
        dest.writeString(imageUrl);


    }

    @Override
    public int describeContents() {
        return 0;
    }


    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}