package ru.startandroid.importantdates.presentation.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.exifinterface.media.ExifInterface;

import java.io.IOException;

public class ImageHelper {
    public static int IMAGE_RESOLUTION = 150;

    public static Bitmap getRotatedBitmap(String absoluteImPath) {
        Bitmap rotatedBitmap = null;
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(absoluteImPath);
            ExifInterface ei = new ExifInterface(absoluteImPath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            int angle;
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    angle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    angle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    angle = 270;
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    angle = 0;
                    break;
            }
            rotatedBitmap = rotateImage(bitmap, angle);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotatedBitmap;
    }

    private static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public static Bitmap getScaledBitmap(Bitmap bitmap) {
        Bitmap output = null;
        if (bitmap != null) {
            bitmap = Bitmap.createScaledBitmap(bitmap, IMAGE_RESOLUTION,
                    IMAGE_RESOLUTION, false);
            output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.RGB_565);

            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            canvas.drawBitmap(bitmap, rect, rect, paint);
        }
        return output;
    }
}
