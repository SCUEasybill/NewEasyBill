package com.waq8.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.waq8.neweasybill.R;
import com.waq8.util.MyConnector;

/**
 * Created by guyu on 2016/2/27.
 */
public class Login extends AppCompatActivity {
    MyConnector mc;
    Button btn_login;
    EditText edt_account,edt_pwd;
    TextView tv_register,tv_soundHelp;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }
}
