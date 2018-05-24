package com2027.group9_cw.sk00806.fitme_group9;

import android.os.Parcel;
import android.os.Parcelable;

public class ProgressPicture implements Parcelable{
    String url;
    String imageTitle;

    public ProgressPicture(String url, String imageTitle){
        this.url = url;
        this.imageTitle = imageTitle;
    }

    public ProgressPicture(Parcel in){
        this.url = in.readString();
        this.imageTitle = in.readString();

    }

    public static final Creator<ProgressPicture> CREATOR = new Creator<ProgressPicture>() {
        @Override
        public ProgressPicture createFromParcel(Parcel in) {
            return new ProgressPicture(in);
        }

        @Override
        public ProgressPicture[] newArray(int size) {
            return new ProgressPicture[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(imageTitle);
    }
}
