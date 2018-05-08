package com.view.sign;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.view.sign.view.SignView;

public class MainActivity extends AppCompatActivity {

    private ImageView ivSignShow;
    private SignView signView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivSignShow = (ImageView) findViewById(R.id.iv_sign_show);
        signView = (SignView) findViewById(R.id.signview);
    }

    public void clearSignature(View view){
        signView.clearSignature();
    }

    public void getSignatureBitmap(View view){
        Bitmap bitmap = signView.getSignatureBitmap();
        if(bitmap != null){
            ivSignShow.setImageBitmap(bitmap);
        }
        ivSignShow.setVisibility(View.VISIBLE);
    }

    public void browseTheComplete(View view){
        ivSignShow.setVisibility(View.GONE);
    }

}
