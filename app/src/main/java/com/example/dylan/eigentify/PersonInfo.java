package com.example.dylan.eigentify;

public class PersonInfo {
    private int _id;
    private String _first;
    private String _last;
    private String _major;
    private String _misc;
    private byte[] _img;
    //________________________
    public PersonInfo()
    {
    }
    public PersonInfo(int id, String first, String last, String major, String misc, byte[] img)
    {
        this._id=id;
        this._first=first;
        this._last=last;
        this._major=major;
        this._misc=misc;
        this._img = img;;
        //________________________
    }
    public void setId(int id) {this._id = id; }
    public void setFirst(String first) {
        this._first = first;
    }
    public void setLast(String last) {this._last = last; }
    public void setMajor(String major) {
        this._major = major;
    }
    public void setMisc(String misc) {
        this._misc = misc;
    }
    public void setImage(byte[] img) {
        this._img = img;
    }
    //________________________

    public int getId() {
        return _id;
    }
    public String getFirst() {return _first; }
    public String getLast() {return _last; }
    public String getMajor() {return _major; }
    public String getMisc() {return _misc; }
    public byte[] getImage() {return _img; }
    //________________________
}
