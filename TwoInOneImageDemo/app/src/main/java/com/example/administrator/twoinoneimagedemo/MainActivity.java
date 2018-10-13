package com.example.administrator.twoinoneimagedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView) findViewById(R.id.img);
        Bitmap bit1 = BitmapFactory.decodeResource(getResources(),R.mipmap.bj);
        Bitmap bit2 = BitmapFactory.decodeResource(getResources(),R.mipmap.yes);
        Bitmap bit = toInOne(bit1,bit2);//bit1要比bit2大，不然会对bit2进行缩放
        img.setImageBitmap(bit);
    }

    private Bitmap toInOne(Bitmap bit1, Bitmap bit2) {
        if(bit2.getWidth()>bit1.getWidth()||bit2.getHeight()>bit1.getHeight()){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            float sacle = calculateInSampleSize(bit2,bit1);
            Matrix matrix = new Matrix();
            matrix.postScale(sacle,sacle);
            bit2 = Bitmap.createBitmap(bit2,0,0,bit2.getWidth(),bit2.getHeight(),matrix,true);
        }
        Bitmap retBmp = Bitmap.createBitmap(bit1.getWidth(), bit1.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(retBmp);
        canvas.drawBitmap(bit1, new Matrix(),null);
        canvas.translate((bit1.getWidth() - bit2.getWidth()) / 2, (bit1.getHeight() - bit2.getHeight()) / 2);
        canvas.drawBitmap(bit2, new Matrix(),null);
        return  retBmp;
    }

    private float calculateInSampleSize(Bitmap b1, Bitmap b2)
    {
        // reqWidth、reqHeight是想要显示图片的大小
        float inSampleSize = 0f;

        float w1 = b1.getWidth();
        float w2 = b2.getWidth();
        float h1 = b1.getHeight();
        float h2 = b2.getHeight();

        float f1 = w2/w1;
        float f2 = h2/h1;
        if(f1>f2){
            inSampleSize=f2;
        }else{
            inSampleSize=f1;
        }

        return inSampleSize;
    }
}
