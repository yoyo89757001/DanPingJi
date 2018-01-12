package com.example.danpingji.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.danpingji.MyAppLaction;
import com.example.danpingji.R;
import com.example.danpingji.beans.BaoCunBean;
import com.example.danpingji.beans.BaoCunBeanDao;
import com.example.danpingji.beans.LogoBean;
import com.example.danpingji.utils.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ShouYeActivity extends Activity implements View.OnClickListener {

    public static final int TIMEOUT = 1000 * 60;
    private String zhuji=null;
    private BaoCunBeanDao baoCunBeanDao=null;
    private BaoCunBean baoCunBean=null;
    private ImageView logo;
    private String pathyy="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shou_ye);
        baoCunBeanDao= MyAppLaction.myAppLaction.getDaoSession().getBaoCunBeanDao();
        baoCunBean=baoCunBeanDao.load(123456L);
        if (baoCunBean!=null && baoCunBean.getZhuji()!=null){
            zhuji=baoCunBean.getZhuji();
        }
        logo= (ImageView) findViewById(R.id.d1);
        TextView imageView= (TextView) findViewById(R.id.dengji2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ShouYeActivity.this,InFoActivity2.class).putExtra("pathyy",pathyy));
            }
        });
        ImageView imageView2= (ImageView) findViewById(R.id.shezhi);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ShouYeActivity.this, SheZhiActivity.class));

            }
        });
        if (baoCunBean!=null && baoCunBean.getZhangHuID()!=null && !baoCunBean.getZhangHuID().equals("")){
            try {
                link_tianqi3();
            }catch (Exception e){
                Log.d("ShouYeActivity", e.getMessage());
            }

        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            System.out.println("按下了back键   onKeyDown()");
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }


    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.dengji24:
                    startActivity(new Intent(ShouYeActivity.this, RenGongActivity.class));
                    break;
                case R.id.dengji3:
                    startActivity(new Intent(ShouYeActivity.this, YuYueActivity.class));
                    break;
                case R.id.dengji245:
                    startActivity(new Intent(ShouYeActivity.this, RenGongActivity3.class));
                    break;
            }
    }


    private void link_tianqi3() {
        //final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        //http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
        OkHttpClient okHttpClient= new OkHttpClient.Builder()
                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();


//    /* form的分割线,自己定义 */
//        String boundary = "xx--------------------------------------------------------------xx";
        RequestBody body = new FormBody.Builder()
                .add("accountId",baoCunBean.getZhangHuID())
                .build();

        Request.Builder requestBuilder = new Request.Builder()
                // .header("Content-Type", "application/json")
                .post(body)
                .url(zhuji + "/getTemplate.do");


        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast tastyToast= TastyToast.makeText(ShouYeActivity.this,"访问出错！",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                        tastyToast.setGravity(Gravity.CENTER,0,0);
                        tastyToast.show();
                    }
                });
                Log.d("AllConnects", "请求识别失败"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.d("AllConnects", "请求识别成功"+call.request().toString());
                //获得返回体
                try {

                    ResponseBody body = response.body();
                    // Log.d("AllConnects", "识别结果返回"+response.body().string());
                    String ss=body.string().trim();
                    Log.d("InFoActivity", ss);
                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson=new Gson();
                    final LogoBean logoBean=gson.fromJson(jsonObject,LogoBean.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pathyy=logoBean.getLogo();
                            Glide.with(ShouYeActivity.this)
                                    .load(zhuji+"/upload/logo/"+logoBean.getLogo())
                                    //  .transform(new GlideCircleTransform(ShouYeActivity.this,1, Color.parseColor("#ffffffff")))
                                    .thumbnail(0.1f)
                                     //  .transform(new GlideRoundTransform(MyApplication.getAppContext(), 6))
                                    .into(logo);
//                            Glide.with(ShouYeActivity.this).load(zhuji+"/upload/logo/"+logoBean.getLogo()).asBitmap().into(new SimpleTarget<Bitmap>() {
//                                @Override
//                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                                    logo.setImageBitmap(resource);
//                                }
//                            });
                        }
                    });


                }catch (Exception e){

                    Log.d("WebsocketPushMsg", e.getMessage());
                }
            }
        });

    }
}
