package com.szhklt.www.inputmethodtest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private EditText editText;

    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //拿到输入法的全部条目
        List<InputMethodInfo> list = imm.getInputMethodList();
        Log.e("rzrz","list.size():"+list.size());
        Log.e("rzrz",list.get(0).toString());
        Log.e("rzrz",list.get(1).toString());
        Log.e("rzrz",list.get(0).getPackageName());
        //textView中显示输入法的全部条目
//        textView.setText(list.toString());
        //输入法选择框
        imm.showInputMethodPicker();


    }

    private void init() {
        textView = (TextView)findViewById(R.id.textView);
        editText = (EditText)findViewById(R.id.editText);
        button = (Button)findViewById(R.id.button);
    }

}
