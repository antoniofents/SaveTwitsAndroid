package com.example.afentanes.twitsaver;

import android.os.Parcel;
import android.os.Parcelable;

import com.twitter.sdk.android.core.models.Tweet;

/**
 * Created by afentanes on 10/26/17.
 */

public class Twit implements Parcelable , Comparable {


    public  Twit(){

    }
    long id;
    public String author;
    public String text;


    public  Twit(Tweet tweet){
        this.id=tweet.id;
        this.author=tweet.user.name;
        this.text=tweet.text;
    }

    protected Twit(Parcel in) {
        id = in.readLong();
        author = in.readString();
        text = in.readString();
    }

    public static final Creator<Twit> CREATOR = new Creator<Twit>() {
        @Override
        public Twit createFromParcel(Parcel in) {
            return new Twit(in);
        }

        @Override
        public Twit[] newArray(int size) {
            return new Twit[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(author);
        parcel.writeString(text);
    }

    @Override
    public int compareTo(Object o) {
       if(o!=null &&  o instanceof  Twit){
           Twit twit = (Twit) o;
           return twit.id>this.id?1:-1;
       }
       return -1;
    }
}
