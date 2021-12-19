package com.example.calculate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import java.math.BigDecimal;


public class MainActivity extends AppCompatActivity {

    private TextView calculator_monitor;//编辑框
    private Button b_0,b_1,b_2,b_3,b_4,b_5,b_6,b_7,b_8,b_9,b_clear,b_divide,
            b_multiply,b_add,b_minus,b_fac,b_dot,b_equal,b_percent,b_negative;//按键
    double num1=0,num2=0;//操作数
    double result=0;//结果
    int operation=0;//操作
    int mark1=0;//判断正负数,0表示正数，1表示负数
    int mark2=0;
    int factorial=0;//阶乘数
    int negativeclicktimes=0;//正负数按键的点击次数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//加载布局

//获得文本框和各个按键的ID
        calculator_monitor=(TextView)findViewById(R.id.calculator_screen);
        b_0=(Button)findViewById(R.id.button_0);
        b_1=(Button)findViewById(R.id.button_1);
        b_2=(Button)findViewById(R.id.button_2);
        b_3=(Button)findViewById(R.id.button_3);
        b_4=(Button)findViewById(R.id.button_4);
        b_5=(Button)findViewById(R.id.button_5);
        b_6=(Button)findViewById(R.id.button_6);
        b_7=(Button)findViewById(R.id.button_7);
        b_8=(Button)findViewById(R.id.button_8);
        b_9=(Button)findViewById(R.id.button_9);
        b_add=(Button)findViewById(R.id.button_add);
        b_clear=(Button)findViewById(R.id.button_clear);
        b_divide=(Button)findViewById(R.id.button_divide);
        b_dot=(Button)findViewById(R.id.button_dot);
        b_equal=(Button)findViewById(R.id.button_equal);
        b_minus=(Button)findViewById(R.id.button_minus);
        b_multiply=(Button)findViewById(R.id.button_multiply);
        b_fac=(Button)findViewById(R.id.button_fac);
        b_percent=(Button)findViewById(R.id.button_percent);
        b_negative=(Button)findViewById(R.id.button_negative);

//加监视器,监测事件动作
        b_0.setOnClickListener(new MyOnClickListener());
        b_1.setOnClickListener(new MyOnClickListener());
        b_2.setOnClickListener(new MyOnClickListener());
        b_3.setOnClickListener(new MyOnClickListener());
        b_4.setOnClickListener(new MyOnClickListener());
        b_5.setOnClickListener(new MyOnClickListener());
        b_6.setOnClickListener(new MyOnClickListener());
        b_7.setOnClickListener(new MyOnClickListener());
        b_8.setOnClickListener(new MyOnClickListener());
        b_9.setOnClickListener(new MyOnClickListener());
        b_clear.setOnClickListener(new MyOnClickListener());
        b_divide.setOnClickListener(new MyOnClickListener());
        b_minus.setOnClickListener(new MyOnClickListener());
        b_add.setOnClickListener(new MyOnClickListener());
        b_equal.setOnClickListener(new MyOnClickListener());
        b_multiply.setOnClickListener(new MyOnClickListener());
        b_dot.setOnClickListener(new MyOnClickListener());
        b_fac.setOnClickListener(new MyOnClickListener());
        b_percent.setOnClickListener(new MyOnClickListener());
        b_negative.setOnClickListener(new MyOnClickListener());

    }

 //实现事件监听器
    class MyOnClickListener implements  View.OnClickListener{

 //点击事件的处理方法
        public void onClick(View v){
//判断传参
            switch (v.getId()) {
//清除
                case R.id.button_clear:
                    calculator_monitor.setText(null);
                    mark1 = 0;
                    mark2 = 0;
                    operation = 0;
                    factorial = 0;
                    negativeclicktimes = 0;
                    break;
//数字按键
                case R.id.button_0:
                    String str0 = calculator_monitor.getText().toString();//获取输入的内容并转换为string型
                    str0 += "0";
                    calculator_monitor.setText(str0);
                    break;
                case R.id.button_1:
                    String str1 = calculator_monitor.getText().toString();
                    str1 += "1";
                    calculator_monitor.setText(str1);
                    break;
                case R.id.button_2:
                    String str2 = calculator_monitor.getText().toString();
                    str2 += "2";
                    calculator_monitor.setText(str2);
                    break;
                case R.id.button_3:
                    String str3 = calculator_monitor.getText().toString();
                    str3 += "3";
                    calculator_monitor.setText(str3);
                    break;
                case R.id.button_4:
                    String str4 = calculator_monitor.getText().toString();
                    str4 += "4";
                    calculator_monitor.setText(str4);
                    break;
                case R.id.button_5:
                    String str5 = calculator_monitor.getText().toString();
                    str5 += "5";
                    calculator_monitor.setText(str5);
                    break;
                case R.id.button_6:
                    String str6 = calculator_monitor.getText().toString();
                    str6 += "6";
                    calculator_monitor.setText(str6);
                    break;
                case R.id.button_7:
                    String str7 = calculator_monitor.getText().toString();
                    str7 += "7";
                    calculator_monitor.setText(str7);
                    break;
                case R.id.button_8:
                    String str8 = calculator_monitor.getText().toString();
                    str8 += "8";
                    calculator_monitor.setText(str8);
                    break;
                case R.id.button_9:
                    String str9 = calculator_monitor.getText().toString();
                    str9 += "9";
                    calculator_monitor.setText(str9);
                    break;
                case R.id.button_dot:
                    String strdot = calculator_monitor.getText().toString();
                    strdot += ".";
                    calculator_monitor.setText(strdot);
                    break;

//正负数转换
                case R.id.button_negative:
                    negativeclicktimes++;     //每次点击，点击次数增加
                    if (operation == 0){           //判断第一个操作数的正负
                        if(negativeclicktimes % 2 == 0)
                        {
                            mark1 = 0;
                            Toast.makeText(MainActivity.this,"正数模式",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            mark1 = 1;
                            Toast.makeText(MainActivity.this,"负数模式",Toast.LENGTH_SHORT).show();
                        }

                    }

                    else{       //判断第二个操作数的正负
                        if(negativeclicktimes % 2 == 0)
                        {
                            mark2 = 0;
                            Toast.makeText(MainActivity.this,"正数模式",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            mark2 = 1;
                            Toast.makeText(MainActivity.this,"负数模式",Toast.LENGTH_SHORT).show();
                        }
                    }

                    break;

//运算符
 //加法
                case R.id.button_add:
                    try {
                        if(operation == 0) {      //判断输入一个操作数后,操作符是否改变
                            String stradd = calculator_monitor.getText().toString();
                            num1 = Double.valueOf(stradd);
                        }

                        if(mark1 == 1)      //判断正负
                        {
                            num1 = -num1;
                        }

                        calculator_monitor.setText(null);
                        operation = 1;
                        negativeclicktimes = 0;
                        break;
                    }catch (Exception e){
                        e.printStackTrace();
                        break;
                    }
//减法
                case R.id.button_minus:
                    try {
                        if(operation == 0) {
                            String strminus = calculator_monitor.getText().toString();
                            num1 = Double.valueOf(strminus);
                        }

                        if(mark1 == 1)
                        {
                            num1 = -num1;
                        }
                        calculator_monitor.setText(null);
                        operation = 2;
                        negativeclicktimes = 0;
                        break;
                    }catch (Exception e){
                        e.printStackTrace();
                        break;
                    }
//乘法
                case R.id.button_multiply:
                    try {
                        if(operation == 0) {
                            String strmultiply = calculator_monitor.getText().toString();
                            num1 = Double.valueOf(strmultiply);
                        }
                        if(mark1 == 1) {
                            num1 = -num1;
                        }
                        calculator_monitor.setText(null);
                        operation = 3;
                        negativeclicktimes = 0;
                        break;
                    }catch (Exception e){
                        e.printStackTrace();
                        break;
                    }
//除法
                case R.id.button_divide:
                    try {
                        if(operation == 0) {
                            String strdivide = calculator_monitor.getText().toString();
                            num1 = Double.valueOf(strdivide);
                        }
                        if(mark1 == 1)
                        {
                            num1 = -num1;
                        }
                        calculator_monitor.setText(null);
                        operation = 4;
                        negativeclicktimes = 0;
                        break;
                    }catch (Exception e){
                        e.printStackTrace();
                        break;
                    }
//百分比
                case R.id.button_percent:
                    try {
                        String strpercent = calculator_monitor.getText().toString();
                        num1 = Double.valueOf(strpercent);
                        if(mark1 == 1)
                        {
                            num1 = -num1;
                        }
                         strpercent += "%";
                         calculator_monitor.setText(strpercent);
                         operation = 5;
                         break;
                    }catch (Exception e){
                        e.printStackTrace();
                         break;
                    }

//阶乘
                case R.id.button_fac:
                    try {
                        String strfac = calculator_monitor.getText().toString();
                        num1=Double.valueOf(strfac);
                        if(mark1 == 1)
                        {
                            num1 = -num1;
                        }
                        factorial = Integer.valueOf(strfac);
                        strfac += "!";
                        calculator_monitor.setText(strfac);
                        operation = 6;
                        break;
                    }catch (Exception e){
                        e.printStackTrace();
                        break;
                    }
//等号，进行判断运算类型
                case R.id.button_equal:
                    try {
//获取第二个数
                        String strequ = calculator_monitor.getText().toString();
                        if (operation == 6) {
                            strequ = strequ.split("!")[0];
                        }
                        if (operation == 5) {
                            strequ = strequ.split("%")[0];
                        }
                        num2 = Double.valueOf(strequ);
//判断第二个数正负
                        if (mark2 == 1) {
                            num2 = -num2;
                        }
//进行运算操作
                        switch (operation){
//加法运算
                            case 1:
                                result = num1 + num2;
                                result = (double)Math.round(result *100)/100;
                                calculator_monitor.setText(String.valueOf(result));
                                operation = 0;
                                break;
//减法运算
                            case 2:
                                result = num1 - num2;
                                result = (double)Math.round(result *100)/100;
                                calculator_monitor.setText(String.valueOf(result));
                                operation = 0;
                                break;
//乘法运算
                            case 3:
                                result = num1 * num2;
                                result = (double)Math.round(result *100)/100;
                                calculator_monitor.setText(String.valueOf(result));
                                operation = 0;
                                break;
//除法运算
                            case 4:
                                if(num2 == 0)
                                {
                                    calculator_monitor.setText("除数不能为0！");
                                    operation = 0;
                                    break;
                                }
                                else {
                                    result = num1 / num2;
                                    result = (double) Math.round(result * 100) / 100;
                                    calculator_monitor.setText(String.valueOf(result));
                                    operation = 0;
                                    break;
                                }
//百分数
                            case 5:
                                result = num1 / 100;
                                calculator_monitor.setText(String.valueOf(result));
                                operation = 0;
                                break;
//阶乘运算
                            case 6:
                                if(num1 < 0)
                                {
                                    Toast.makeText(MainActivity.this, "负数没有阶乘！请重新输入", Toast.LENGTH_SHORT).show();
                                    operation = 0;
                                    break;
                                }
                                else {
                                    result = 1;
                                    for (int i = factorial; i >= 1; i--) {
                                        result = result * i;
                                    }
                                    calculator_monitor.setText(String.valueOf(result));
                                    operation = 0;
                                    break;
                                }
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                                break;
                    }

            }
        }
    }


}