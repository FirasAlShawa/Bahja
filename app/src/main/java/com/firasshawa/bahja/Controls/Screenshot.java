package com.firasshawa.bahja.Controls;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;

public class Screenshot {

    private Activity activity;

    public Screenshot(Activity activity) {
        this.activity = activity;
    }

    public void TakeScreenShot(){
        View screenView = getView();
        Bitmap bitmap = getScreenShot(screenView);
        store(bitmap,new TimeOptions(activity).getDate().toString());
    }

    public View getView(){
        View view = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        return  view;
    }

    public Bitmap getScreenShot(View view){
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public void store(Bitmap bitmap , String filename){
        final String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/GraduationScreenshots";
        File dir = new File(dirPath);
        if(!dir.exists())
            dir.mkdir();

        File file = new File(dirPath,"screenshot"+new TimeOptions(activity).calendar.getTimeInMillis()+".png");
        try {
            System.out.println(file.getAbsolutePath());
            FileOutputStream fout = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,85,fout);
            fout.flush();
            fout.close();
            shareIt(file);
            Toast.makeText(activity,"screenshow done!",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void shareIt(File file){
        Uri uri = FileProvider.getUriForFile(activity,this.activity.getPackageName()+".provider",file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        intent.putExtra(Intent.EXTRA_SUBJECT,"");
        intent.putExtra(Intent.EXTRA_TEXT,"");
        intent.putExtra(Intent.EXTRA_STREAM,uri);

        try {
            activity.startActivity(Intent.createChooser(intent, "Share Screenshot"));
        }catch (ActivityNotFoundException e) {
            Toast.makeText(activity, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }

}
