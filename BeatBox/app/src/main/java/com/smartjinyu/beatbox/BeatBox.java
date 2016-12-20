package com.smartjinyu.beatbox;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartjinyu on 2016/12/20.
 * This class is used for asset management: finding assets, keeping track of them and finally playing them as sounds.
 */

public class BeatBox {
    private static final String TAG = "BeatBox";
    private static final String SOUNDS_FOLDER = "sample_sounds";

    private AssetManager mAssetManager;

    private List<Sound> mSounds = new ArrayList<>();

    public BeatBox(Context context){
        mAssetManager = context.getAssets();
        loadSounds();
    }

    private void loadSounds(){
        String[] SoundNames;
        try{
            SoundNames = mAssetManager.list(SOUNDS_FOLDER);
            Log.i(TAG,"Found " + SoundNames.length + " sounds");
        }catch (IOException ioe){
            Log.e(TAG,"Could not list assets",ioe);
            return;
        }
        for(String filename: SoundNames){
            String assetPath = SOUNDS_FOLDER + "/" + filename;
            Sound sound = new Sound(assetPath);
            mSounds.add(sound);
        }
    }

    public List<Sound> getSounds() {
        return mSounds;
    }

}
