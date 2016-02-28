package com.waq8.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.waq8.util.ConstantsUtil.SERVER_ADDRESS;
import static com.waq8.util.ConstantsUtil.SERVER_PORT;


import com.waq8.neweasybill.MainActivity;
import com.waq8.neweasybill.R;
import com.waq8.util.MyConnector;

/**
 * Created by guyu on 2016/2/27.
 */
public class Login extends AppCompatActivity {
    MyConnector mc;
    Button btn_login;
    EditText edt_account,edt_pwd,etUid,etPwd;
    TextView tv_register,tv_soundHelp;
    ProgressDialog pd;
    String uid, pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initView();
        checkIfRemember();
        //登陆
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(edt_account.getText().equals("guyu")&&edt_pwd.getText().equals("123")){
//                    Intent intent = new Intent(Login.this, MainActivity.class);
//                    startActivity(intent);
//                }
                Log.d("login","login-1");
                 etUid = (EditText)findViewById(R.id.edtAccount);	//获得帐号EditText
                 etPwd = (EditText)findViewById(R.id.edtPwd);	//获得密码EditText
                 uid = etUid.getEditableText().toString().trim();	//获得输入的帐号
                 pwd = etPwd.getEditableText().toString().trim();	//获得输入的密码
                if(uid.equals("") || pwd.equals("")){		//判断输入是否为空
                    Toast.makeText(Login.this, "请输入帐号或密码!", Toast.LENGTH_SHORT).show();//输出提示消息
                    return;
                }
                login();
            }
        });

        //注册
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(Login.this, Register.class);
                startActivity(intent);
                finish();
            }
        });

        //忘记密码
        tv_soundHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * 初始化界面
     */
    private void initView() {
        btn_login = (Button) findViewById(R.id.btnLogin);

        tv_register = (TextView) findViewById(R.id.tvRegister);
        tv_soundHelp = (TextView) findViewById(R.id.tvSound_help);

//        CheckBox cb = (CheckBox) findViewById(R.id.cbRemember);
    }
    /**
     * 登陆
     */
    public void login(){
        new Thread(){
            public void run(){
                Looper.prepare();
                try{
                    if(mc == null){
                        mc = new MyConnector(SERVER_ADDRESS, SERVER_PORT);
                        Log.d("login", "lianjie-server");
                    }
                    pd = ProgressDialog.show(Login.this, "请稍候", "正在连接服务器...", true, true);
                    String msg = "<#LOGIN#>"+uid+"|"+pwd;					//组织要返回的字符串
                    mc.dout.writeUTF(msg);										//发出消息
                    String receivedMsg = mc.din.readUTF();		//读取服务器发来的消息
                    pd.dismiss();
                    if(receivedMsg.startsWith("<#LOGIN_SUCCESS#>")){	//收到的消息为登录成功消息
                        receivedMsg = receivedMsg.substring(17);
                        String [] sa = receivedMsg.split("\\|");
                        CheckBox cb = (CheckBox)findViewById(R.id.cbRemember);		//获得CheckBox对象
                        if(cb.isChecked()){
                            rememberMe(uid,pwd);
                        }
                        //转到功能面板
                        Intent intent = new Intent(Login.this,MainActivity.class);
                        intent.putExtra("uno", sa[0]);
                        startActivity(intent);						//启动功能Activity
                        finish();
                    }
                    else if(msg.startsWith("<#LOGIN_FAIL#>")){					//收到的消息为登录失败
                        Toast.makeText(Login.this, msg.substring(14), Toast.LENGTH_LONG).show();
                        Looper.loop();
                        Looper.myLooper().quit();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
    //方法：将用户的id和密码存入Preferences
    public void rememberMe(String uid,String pwd){
        SharedPreferences sp = getPreferences(MODE_PRIVATE);	//获得Preferences
        SharedPreferences.Editor editor = sp.edit();			//获得Editor
        editor.putString("uid", uid);							//将用户名存入Preferences
        editor.putString("pwd", pwd);							//将密码存入Preferences
        editor.commit();
    }
    //方法：从Preferences中读取用户名和密码
    public void checkIfRemember(){
        SharedPreferences sp = getPreferences(MODE_PRIVATE);	//获得Preferences
        String uid = sp.getString("uid", null);
        String pwd = sp.getString("pwd", null);
        if(uid != null && pwd!= null){
            EditText etUid = (EditText)findViewById(R.id.edtAccount);
            EditText etPwd = (EditText)findViewById(R.id.edtPwd);
            CheckBox cbRemember = (CheckBox)findViewById(R.id.cbRemember);
            etUid.setText(uid);
            etPwd.setText(pwd);
            cbRemember.setChecked(true);
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onDestroy() {
        if(mc != null){
            mc.sayBye();
        }
        super.onDestroy();
    }
}
