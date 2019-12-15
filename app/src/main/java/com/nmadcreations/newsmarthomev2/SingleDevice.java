package com.nmadcreations.newsmarthomev2;

public class SingleDevice {

    private int mImageResource;
    private String name;
    private String type;
    private String mDeviceId;

    public SingleDevice(String did ,int imageResource, String type, String name){
        mDeviceId = did;
        mImageResource = imageResource;
        this.name = name;
        this.type = type;
    }

    //set itemclick change text
    public void changeText2(String text){
        type = text;
    }


    public String getmDeviceId() {
        return mDeviceId;
    }

    public void setmDeviceId(String mDeviceId) {
        this.mDeviceId = mDeviceId;
    }

    public int getmImageResource(){
        return mImageResource;
    }

    public String getname() {
        return name;
    }

    public String gettype() {
        return type;
    }

}
