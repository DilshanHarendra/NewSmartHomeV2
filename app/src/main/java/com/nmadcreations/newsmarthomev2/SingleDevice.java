package com.nmadcreations.newsmarthomev2;

public class SingleDevice {

    private int mImageResource;
    private String mText1;
    private String mtext2;
    private String mDeviceId;

    public SingleDevice(String did ,int imageResource, String text1, String text2){
        mDeviceId = did;
        mImageResource = imageResource;
        mText1 = text1;
        mtext2 = text2;
    }

    //set itemclick change text
    public void changeText2(String text){
        mtext2 = text;
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

    public String getmText1() {
        return mText1;
    }

    public String getMtext2() {
        return mtext2;
    }

}
