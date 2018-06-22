package com.simcoder.novalauncherclone;

import android.graphics.drawable.Drawable;

public class AppObject {
    private String  name,
                    packageName;
    private Drawable image;

    public AppObject(String packageName, String name, Drawable image){
        this.name = name;
        this.packageName = packageName;
        this.image = image;
    }

    public String getPackageName(){return packageName;}
    public String getName(){return name;}
    public Drawable getImage(){return image;}

}
