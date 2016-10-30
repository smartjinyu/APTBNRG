package com.smartjinyu.criminalintent;

import java.util.Date;
import java.util.UUID;
/**
 * Created by smartjinyu on 2016/10/30.
 */

public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public UUID getId() {
        return mId;
    }

    public Crime(){
        mId = UUID.randomUUID();
        mDate = new Date();//return current date
    }
}
