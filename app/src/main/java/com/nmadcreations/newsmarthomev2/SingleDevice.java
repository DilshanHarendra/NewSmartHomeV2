package com.nmadcreations.newsmarthomev2;

public class SingleDevice {

    private int mImageResource;
    private String mText1;
    private String mtext2;
    private String mdeviceId;

    public SingleDevice(String did, int imageResource, String text1, String text2){
        mdeviceId = did;
        mImageResource = imageResource;
        mText1 = text1;
        mtext2 = text2;
    }

    public String getMdeviceId() {
        return mdeviceId;
    }

    public void setMdeviceId(String mdeviceId) {
        this.mdeviceId = mdeviceId;
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
