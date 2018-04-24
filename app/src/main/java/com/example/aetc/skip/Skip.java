package com.example.aetc.skip;

/**
 * Created by douglasng on 4/17/18.
 */

public class Skip {

    private String title,desc,image;
    public Skip(){

    }

    public Skip(String title,String desc, String image){
        this.title = title;
        this.desc = desc;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getImage() {
        return image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
