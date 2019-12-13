package com.nmadcreations.newsmarthomev2;

public class SingleDevice {

    private int mImageResource;
    private String id;
    private String name;
    private String type;

    public SingleDevice(int imageResource, String text1, String text2){
        mImageResource = imageResource;
        id = text1;
        name = text2;
    }

    //set itemclick change text
    public void changeText2(String text){
        name = text;
    }


    public SingleDevice(String id, String name, String type) {
        this.id = id;
        this.name = name;
        mImageResource=1;
        this.type = type;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
