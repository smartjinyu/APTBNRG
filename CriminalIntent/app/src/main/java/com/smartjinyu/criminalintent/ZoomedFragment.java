package com.smartjinyu.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.util.UUID;

/**
 * Created by smartjinyu on 2016/11/27.
 */

public class ZoomedFragment extends DialogFragment {
    private static final String ARG_ID = "id";
    private ImageView mImageView;

    public static ZoomedFragment newInstance(UUID uuid){
        Bundle args = new Bundle();
        args.putSerializable(ARG_ID,uuid);
        ZoomedFragment fragment = new ZoomedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        UUID uuid = (UUID) getArguments().getSerializable(ARG_ID);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_zoomed,null);
        mImageView = (ImageView) v.findViewById(R.id.zoomed_in_image);
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        Crime crime = crimeLab.getCrime(uuid);
        File file = crimeLab.getPhotoFile(crime);
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
        mImageView.setImageBitmap(bitmap);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(android.R.string.ok,null)
                .setTitle(R.string.crime_photo)
                .create();

    }


}
