package ru.startandroid.importantdates.framework;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ImageConverter {
    public static Bitmap getBitmap(byte[] data) {
        try {
            ByteArrayInputStream imageStream = new ByteArrayInputStream(data);
            return BitmapFactory.decodeStream(imageStream);
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] getByteArray(Bitmap bitmap) {
        return getByteArray(bitmap, 0);
    }

    public static byte[] getByteArray(Bitmap bitmap, int quality) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, stream);
            byte[] byteArray = stream.toByteArray();
            bitmap.recycle();
            return byteArray;
        } catch (Exception e) {
            return null;
        }
    }
}
