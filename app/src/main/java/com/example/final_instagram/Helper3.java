package com.example.final_instagram;

public class Helper3 {
    String photourl,caption;
    Helper3(){}

    public Helper3(String photourl, String caption) {
        this.photourl = photourl;
        this.caption = caption;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
