package com.smartjinyu.beatbox;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
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
    private static final int MAX_SOUNDS = 5;

    private AssetManager mAssetManager;
    private SoundPool mSoundPool;
    private List<Sound> mSounds = new ArrayList<>();

    public BeatBox(Context context){
        mAssetManager = context.getAssets();
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setMaxStreams(MAX_SOUNDS);
        builder.setAudioAttributes(new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build());
        mSoundPool = builder.build();
        //avoid using the deprecated constructor of SoundPool
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
            try {
                String assetPath = SOUNDS_FOLDER + "/" + filename;
                Sound sound = new Sound(assetPath);
                load(sound);
                mSounds.add(sound);
            }catch (IOException ioe){
                Log.e(TAG,"Could not load sound "+ filename,ioe);
            }
        }
    }

    private void load(Sound sound) throws IOException{
        AssetFileDescriptor afd = mAssetManager.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(afd,1);
        sound.setSoundId(soundId);
    }
    public void play(Sound sound){
        Integer soundId = sound.getSoundId();
        if(soundId == null){//if the sound failed to load, its id will be null
            return;
        }
        mSoundPool.play(soundId,1.0f,1.0f,1,0,1.0f);
    }

    public void release(){
        mSoundPool.release();
    }

    public List<Sound> getSounds() {
        return mSounds;
    }

}
