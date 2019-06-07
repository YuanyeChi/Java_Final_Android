package com.chiyuanye.demo.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileHelper {
    public String copyFile(Context context, String DesPath, String fileName, boolean deleteOrNot) {
        AssetManager assetManager = context.getAssets();
        String newFilePath= Environment.getExternalStorageDirectory() + DesPath + fileName;

        File file=new File(newFilePath);

        if(file.exists() && deleteOrNot){
            file.delete();
            Log.i("CYY","delete exist file " + newFilePath);
        }

        try {
            InputStream in = assetManager.open(fileName);
            OutputStream out = new FileOutputStream(newFilePath);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            out.flush();
            out.close();
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
        return newFilePath;
    }
}
