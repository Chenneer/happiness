package com.example.mynote;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;



public class LoginActivity extends AppCompatActivity {

    private LinearLayout mTopLayout;
    private EditText mEtName;
    private LinearLayout mNameLayout;
    private EditText mEtPwd;
    private LinearLayout mPwdLayout;
    private CheckBox mCb;
    private Button mLogin;
    private Button mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("登录");
        initViews();
    }

    private void initViews() {
        mTopLayout = (LinearLayout) findViewById(R.id.top_layout);
        mEtName = (EditText) findViewById(R.id.et_name);
        mNameLayout = (LinearLayout) findViewById(R.id.name_layout);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mPwdLayout = (LinearLayout) findViewById(R.id.pwd_layout);
        mCb = (CheckBox) findViewById(R.id.cb);
        mLogin = (Button) findViewById(R.id.login);
        mRegister = (Button) findViewById(R.id.register);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=mEtName.getText().toString();
                String pwd=mEtPwd.getText().toString();

                if(TextUtils.isEmpty(CacheUtil.get(getApplicationContext()).getString(name,""))){
                    Toast.makeText(getApplicationContext(),"请先注册", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(name)|| TextUtils.isEmpty(pwd)){
                    Toast.makeText(getApplicationContext(),"请输入用户名和密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(CacheUtil.get(getApplicationContext()).getString(name,"").equals(pwd)){
                    Toast.makeText(getApplicationContext(),"登录成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,NotepadActivity.class));
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"用户名或密码不正确", Toast.LENGTH_SHORT).show();
                }

            }
        });
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class),1);
            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK){
            mEtName.setText(data.getStringExtra("name"));
            mEtPwd.setText(data.getStringExtra("pwd"));
        }

    }
}