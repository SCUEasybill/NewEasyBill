package com.waq8.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.waq8.util.ConstantsUtil.SERVER_ADDRESS;
import static com.waq8.util.ConstantsUtil.SERVER_PORT;

import com.waq8.neweasybill.R;
import com.waq8.util.MyConnector;

import java.io.IOException;

/**
 * Created by guyu on 2016/2/28.
 */
public class Register extends Activity {

    MyConnector mc = null;
    Button btn_register;
    EditText edt_account, edt_pwd, edt_pwd2, edt_email, edt_age;
    String account, pwd, pwd2, email, age;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

 edt_account = (EditText) findViewById(R.id.edtAccount);
                edt_pwd = (EditText) findViewById(R.id.edtPwd);
                edt_pwd2 = (EditText) findViewById(R.id.edtPwd2);
                edt_email = (EditText)findViewById(R.id.edtEmail);
                edt_age = (EditText) findViewById(R.id.edtAge);
//        account = edt_account.getText().toString().trim();
                account = edt_account.getEditableText().toString().trim();
                pwd = edt_pwd.getText().toString().trim();
                pwd2 = edt_pwd2.getText().toString().trim();
                email = edt_email.getText().toString().trim();
                age = edt_age.getText().toString().trim();
                //注册
                if(account.equals("") || pwd.equals("") || pwd2.equals("") || email.equals("") || age.equals("")){
                    Toast.makeText(Register.this, "请将注册信息填写完整", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!pwd.equals(pwd2)){
                    Toast.makeText(Register.this,"输入密码不一致",Toast.LENGTH_LONG).show();
                    return;
                }
                registe();
            }
        });
    }

    /**
     * 连接服务器，进行注册
     */
    private void registe() {
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                try {
                    mc = new MyConnector(SERVER_ADDRESS,SERVER_PORT);
                    String regInfo = "<#REGISTER#>"+account+"|"+pwd+"|"+email+"|"+age;
                    pd = ProgressDialog.show(Register.this,"请稍后","正在注册...",true,true);
                    mc.dout.writeUTF(regInfo);
                    String result = mc.din.readUTF();
                    pd.dismiss();
                    if(result.startsWith("<#REG_SUCCESS#>")){
                        result = result.substring(15);
                        Toast.makeText(Register.this, "注册成功！", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Register.this,Login.class);
                        intent.putExtra("account",result);
                        startActivity(intent);
                        //跳转到登陆界面，登陆账号（我们分配，还是注册时的用户名登陆）
                        finish();
                    }
                    else{		//注册失败
                        Toast.makeText(Register.this, "注册失败！请重试！", Toast.LENGTH_LONG).show();
                        Looper.loop();
                        Looper.myLooper().quit();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        btn_register = (Button) findViewById(R.id.btnRegister);

        edt_account = (EditText) findViewById(R.id.edtAccount);
        edt_pwd = (EditText) findViewById(R.id.edtPwd);
        edt_pwd2 = (EditText) findViewById(R.id.edtPwd2);
        edt_email = (EditText)findViewById(R.id.edtEmail);
        edt_age = (EditText) findViewById(R.id.edtAge);
    }
    /**
     *方法：断开连接
     */
    protected void onDestroy() {
        if(mc != null){
            mc.sayBye();
        }
        super.onDestroy();
    }
}
