package com.learzhu.learzhuandroidmaterialdesign.views.foreground;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.learzhu.learzhuandroidmaterialdesign.MainActivity;
import com.learzhu.learzhuandroidmaterialdesign.R;

public class ForegroundActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreground);
        findViewById(R.id.framelayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Toast", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*设置前景色的缺点*/
//    case R.styleable.View_foreground:
//            if (targetSdkVersion >= Build.VERSION_CODES.M || this instanceof FrameLayout) {
//        setForeground(a.getDrawable(attr));
//    }
//    break;

    /*优化 通过自定义View */

}
