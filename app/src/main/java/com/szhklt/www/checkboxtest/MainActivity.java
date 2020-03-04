package com.szhklt.www.checkboxtest;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;


public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBox = (CheckBox) findViewById(R.id.checkbox);

    }

    public void startSettings(View view){
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        ComponentName cn = new ComponentName("com.dfzt.com.monsettings", "com.dfzt.com.monsettings.MainActivity");
//        intent.setComponent(cn);
//        startActivity(intent);

        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo("com.dfzt.com.monsettings",0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return;
        }

        Intent locatIntent = getPackageManager().getLaunchIntentForPackage("com.dfzt.com.monsettings");
        startActivity(locatIntent);

    }


}
