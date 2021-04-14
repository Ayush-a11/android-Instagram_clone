package com.example.final_instagram;

public class GridviewImageAdapter {
    String Imageurl,caption;
    GridviewImageAdapter(){

    }

    public GridviewImageAdapter(String imageurl, String caption) {
        Imageurl = imageurl;
        this.caption = caption;
    }

    public String getImageurl() {
        return Imageurl;
    }

    public void setImageurl(String imageurl) {
        Imageurl = imageurl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
