package com.example.mynote;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;



public class RegisterActivity extends AppCompatActivity {

    private LinearLayout mTopLayout;
    private EditText mEtName;
    private LinearLayout mNameLayout;
    private EditText mEtPwd;
    private LinearLayout mPwdLayout;
    private CheckBox mCb;
    private Button mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        setTitle("注册");
    }

    private void initViews() {
        mTopLayout = (LinearLayout) findViewById(R.id.top_layout);
        mEtName = (EditText) findViewById(R.id.et_name);
        mNameLayout = (LinearLayout) findViewById(R.id.name_layout);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mPwdLayout = (LinearLayout) findViewById(R.id.pwd_layout);
        mCb = (CheckBox) findViewById(R.id.cb);
        mLogin = (Button) findViewById(R.id.login);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=mEtName.getText().toString();
                String pwd=mEtPwd.getText().toString();


                if(TextUtils.isEmpty(name)|| TextUtils.isEmpty(pwd)){
                    Toast.makeText(getApplicationContext(),"请输入用户名和密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!TextUtils.isEmpty(CacheUtil.get(getApplicationContext()).getString(name,""))){
                    Toast.makeText(getApplicationContext(),"该用户已经注册", Toast.LENGTH_SHORT).show();
                     return;
                }


                Intent intent=new Intent();
                intent.putExtra("name",name);
                intent.putExtra("pwd",pwd);
                setResult(RESULT_OK,intent);
                CacheUtil.get(getApplicationContext()).put(name,pwd);
                Toast.makeText(getApplicationContext(),"注册成功，请用用户名和密码登录", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}