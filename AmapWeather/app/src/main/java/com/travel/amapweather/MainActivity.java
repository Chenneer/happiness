package com.travel.amapweather;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText mEt;
    private Button mQuery;
    private TextView mWeatherInfo;
    private Button mList;
    private Button mCare;
    WeatherInfo.LivesBean curLive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        mQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeatherInfo.setText("");
                curLive=null;
                getWeather(mEt.getText().toString());
            }
        });
        mCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curLive==null){
                    Toast.makeText(getApplication(),"请先查找到对应的天气信息",Toast.LENGTH_SHORT).show();
                     return;
                }
                String care = CacheUtil.get(getApplication()).getString("care", "");
                List<CiteyData> citeyDataList =JSON.parseArray(care,CiteyData.class);
                if(citeyDataList==null){
                    citeyDataList=new ArrayList<>();
                }
                int pos=-1;
                if(citeyDataList!=null) {
                    for (int i = 0; i < citeyDataList.size(); i++) {
                        if (citeyDataList.get(i).getCode().equals(curLive.getAdcode())) {
                            pos = i;
                            break;
                        }
                    }
                }

                if(pos!=-1){
                    citeyDataList.remove(pos);
                    Log.i(TAG, "onClick: ---");
                }else {
                    CiteyData citeyData=new CiteyData();
                    citeyData.setName(curLive.getProvince()+" "+curLive.getCity());
                    citeyData.setCode(curLive.getAdcode());
                    citeyDataList.add(citeyData);
                    Log.i(TAG, "onClick: 1---");
                }
                CacheUtil.get(getApplication()).put("care",JSON.toJSONString(citeyDataList));
                checkCare();

            }
        });

        mList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String care = CacheUtil.get(getApplication()).getString("care", "");
                List<CiteyData> citeyDataList =JSON.parseArray(care,CiteyData.class);

                if(citeyDataList==null||citeyDataList.isEmpty()){
                    Toast.makeText(getApplication(),"请先添加关注",Toast.LENGTH_SHORT).show();
                return;
                }
                String[] d=new String[citeyDataList.size()];
                int i=0;
                for(CiteyData citeyData:citeyDataList){
                    d[i]=citeyData.getName();
                    i++;
                }
                new AlertDialog.Builder(MainActivity.this).setTitle("选择城市").setItems(d, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       mEt.setText(citeyDataList.get(which).getCode());
                       getWeather(mEt.getText().toString());
                    }
                }).create().show();


            }
        });



    }

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void checkCare(){
        String care = CacheUtil.get(getApplication()).getString("care", "");
        List<CiteyData> citeyDataList =JSON.parseArray(care,CiteyData.class);
        if(citeyDataList!=null) {
            if (!citeyDataList.isEmpty()) {
                int pos = -1;
                for (int i = 0; i < citeyDataList.size(); i++) {
                    if (citeyDataList.get(i).getCode().equals(curLive.getAdcode())) {
                        pos = i;
                        break;
                    }
                }
                if (pos != -1) {
                    mCare.setText("取消关注 ");
                } else {
                    mCare.setText("关注");
                }
            }else {
                mCare.setText("关注");
            }
        }else {
            mCare.setText("关注");
        }

    }

    public void getWeather(String code) {



        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String string = CacheUtil.get(getApplicationContext()).getString(code, "");
                    if (!TextUtils.isEmpty(string)) {

                        WeatherInfo weatherInfo = JSON.parseObject(string, WeatherInfo.class);
                        WeatherInfo.LivesBean livesBean = weatherInfo.getLives().get(0);
                        curLive=livesBean;
                        if (System.currentTimeMillis() - simpleDateFormat.parse(livesBean.getReporttime()).getTime() < 60 * 60 * 1000l) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    checkCare();
                                    mWeatherInfo.setText("天气：" + livesBean.getWeather() + "\n地区：" +
                                            livesBean.getProvince() + " " + livesBean.getCity() + "\n"
                                            + "温度：" + livesBean.getTemperature() + "\n湿度:" + livesBean.getHumidity() + "\n" + "风向：" + livesBean.getWinddirection() + " " + livesBean.getWindpower() + " \n" +
                                            "更新时间：" + livesBean.getReporttime()
                                    );
                                }
                            });
                            return;
                        }


                    }

                    String post = RequestUtil.requestHttps("https://restapi.amap.com/v3/weather/weatherInfo", "POST", "key=cf231137851193ee3299986d888bfd9e&city=" + code);
                    WeatherInfo weatherInfo;
                        try {


                       weatherInfo = JSON.parseObject(post, WeatherInfo.class);

                        }catch (Exception e){
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "没有查找到城市编码", Toast.LENGTH_SHORT).show();
                                }
                            });
                            return;
                        }
                    if (weatherInfo.getStatus().equals("1")) {
                        if( weatherInfo.getLives()==null||weatherInfo.getLives().size()==0){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "没有查找到城市编码", Toast.LENGTH_SHORT).show();
                                }
                            });
                            return;

                        }

                        WeatherInfo.LivesBean livesBean = weatherInfo.getLives().get(0);
                        CacheUtil.get(getApplicationContext()).put(code, post);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                curLive=livesBean;
                                checkCare();
                                mWeatherInfo.setText("天气：" + livesBean.getWeather() + "\n地区：" +
                                        livesBean.getProvince() + " " + livesBean.getCity() + "\n"
                                        + "温度：" + livesBean.getTemperature() + "\n湿度:" + livesBean.getHumidity() + "\n" + "风向：" + livesBean.getWinddirection() + " " + livesBean.getWindpower() + " \n" +
                                        "更新时间：" + livesBean.getReporttime()
                                );
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "没有查找到城市编码", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }

    private void initViews() {
        mEt = findViewById(R.id.et);
        mQuery = findViewById(R.id.query);
        mWeatherInfo = findViewById(R.id.weather_info);
        mList = findViewById(R.id.list);
        mCare = findViewById(R.id.care);
    }
}