package com.smartjinyu.criminalintent;

import java.util.UUID;

/**
 * Created by smartjinyu on 2016/10/30.
 */

public class Crime {
    private UUID mId;
    private String mTitle;

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
    }
}
