package com.smartjinyu.beatbox;

/**
 * Created by smartjinyu on 2016/12/20.
 * This class is used to represent sounds. Every sound file have an instance of this class.
 * It keeps track of the filename, the name the user should see,
 * and any other information related to the sound.
 */

public class Sound {
    private String mAssetPath;
    private String mName;
    private Integer mSoundId;

    public Sound(String assetPath){
        mAssetPath = assetPath;
        String[] components = assetPath.split("/");
        String filename = components[components.length-1];
        mName = filename.replace(".wav","");//get the presentable name
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public String getName() {
        return mName;
    }

    public Integer getSoundId() {
        return mSoundId;
    }

    public void setSoundId(Integer soundId) {
        mSoundId = soundId;
    }


}
