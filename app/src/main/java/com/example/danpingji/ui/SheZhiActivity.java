package com.example.danpingji.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danpingji.MyAppLaction;
import com.example.danpingji.R;
import com.example.danpingji.beans.BaoCunBean;
import com.example.danpingji.beans.BaoCunBeanDao;
import com.example.danpingji.beans.FanHuiBean;
import com.example.danpingji.beans.Photos;
import com.example.danpingji.dialog.TiJIaoDialog;
import com.example.danpingji.dialog.XiuGaiJiuDianDialog;
import com.example.danpingji.dialog.XiuGaiXinXiDialog;
import com.example.danpingji.utils.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class SheZhiActivity extends Activity {
    private Button ipDiZHI,gengxin,zhuji2,jiudian;
    private TextView title;
    private ImageView famhui;
    private BaoCunBeanDao baoCunBeanDao=null;
    private BaoCunBean baoCunBean=null;
    private TiJIaoDialog tiJIaoDialog=null;
    public static final int TIMEOUT = 1000 * 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_she_zhi);
      //  Log.d("SheZhiActivity", getSerialNumber());
    //    Log.d("SheZhiActivity", getIMEI(SheZhiActivity.this));
       // Log.d("SheZhiActivity", getIMSI(SheZhiActivity.this));

        baoCunBeanDao= MyAppLaction.myAppLaction.getDaoSession().getBaoCunBeanDao();
        baoCunBean=baoCunBeanDao.load(123456L);

        zhuji2= (Button) findViewById(R.id.zhuji);


        zhuji2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final XiuGaiXinXiDialog dialog=new XiuGaiXinXiDialog(SheZhiActivity.this);
                dialog.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (baoCunBean==null){
                            BaoCunBean baoCunBean2=new BaoCunBean();
                            baoCunBean2.setId(123456L);
                            baoCunBean2.setZhuji(dialog.getContents());
                            baoCunBeanDao.insert(baoCunBean2);
                            TastyToast.makeText(SheZhiActivity.this,"保存成功",TastyToast.LENGTH_LONG,TastyToast.INFO).show();
                            baoCunBean=baoCunBeanDao.load(123456L);
                            dialog.dismiss();

                        }else {
                            baoCunBean.setZhuji(dialog.getContents());
                            baoCunBeanDao.update(baoCunBean);
                            baoCunBean=baoCunBeanDao.load(123456L);
                            TastyToast.makeText(SheZhiActivity.this,"更新成功",TastyToast.LENGTH_LONG,TastyToast.INFO).show();
                            dialog.dismiss();
                        }

                    }


                });
                dialog.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                if (baoCunBean!=null && baoCunBean.getZhuji()!=null){
                    dialog.setContents("设置主机地址",baoCunBean.getZhuji()+"");
                }else {
                    dialog.setContents("设置主机地址","http://ly.huifnet.com");

                }
                dialog.show();
            }
        });

        ipDiZHI= (Button) findViewById(R.id.shezhiip);
        gengxin= (Button) findViewById(R.id.jiancha);
        title= (TextView) findViewById(R.id.title);
        title.setText("系统设置");
        famhui= (ImageView) findViewById(R.id.leftim);
        famhui.setVisibility(View.VISIBLE);
        famhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ipDiZHI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final XiuGaiXinXiDialog dialog=new XiuGaiXinXiDialog(SheZhiActivity.this);
                dialog.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (baoCunBean==null){
                            BaoCunBean baoCunBean2=new BaoCunBean();
                            baoCunBean2.setId(123456L);
                            baoCunBean2.setCameraIP(dialog.getContents());
                            baoCunBeanDao.insert(baoCunBean2);
                            TastyToast.makeText(SheZhiActivity.this,"保存成功",TastyToast.LENGTH_LONG,TastyToast.INFO).show();
                            baoCunBean=baoCunBeanDao.load(123456L);
                            dialog.dismiss();
                        }else {
                            baoCunBean.setCameraIP(dialog.getContents());
                            baoCunBeanDao.update(baoCunBean);
                            TastyToast.makeText(SheZhiActivity.this,"更新成功",TastyToast.LENGTH_LONG,TastyToast.INFO).show();
                            baoCunBean=baoCunBeanDao.load(123456L);
                            dialog.dismiss();
                        }

                    }


                });
                dialog.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                if (baoCunBean!=null && baoCunBean.getCameraIP()!=null){
                    dialog.setContents("设置IP摄像头地址",baoCunBean.getCameraIP()+"");
                }else {
                    dialog.setContents("设置IP摄像头地址","192.168.2.32");
                }
                dialog.show();
            }
        });
        gengxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TastyToast.makeText(SheZhiActivity.this,"已经是最新版本",TastyToast.LENGTH_LONG,TastyToast.INFO).show();

            }
        });


        jiudian= (Button) findViewById(R.id.jiudian);
        jiudian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final XiuGaiJiuDianDialog dianDialog=new XiuGaiJiuDianDialog(SheZhiActivity.this);
                dianDialog.setOnQueRenListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (baoCunBean==null){
                            BaoCunBean baoCunBean2=new BaoCunBean();
                            baoCunBean2.setId(123456L);
                            baoCunBean2.setZhangHuID(dianDialog.getJiuDianBean().getId());
                            baoCunBean2.setZhangHuName(dianDialog.getJiuDianBean().getName());
                            baoCunBeanDao.insert(baoCunBean2);
//                            TastyToast.makeText(SheZhiActivity.this,"保存成功",TastyToast.LENGTH_LONG,TastyToast.INFO).show();
                            baoCunBean=baoCunBeanDao.load(123456L);
                            dianDialog.dismiss();
                            if (baoCunBean.getZhuji()!=null){
                                link_save(dianDialog.getJiuDianBean().getName(),dianDialog.getJiuDianBean().getId());
                            }else {
                                TastyToast.makeText(SheZhiActivity.this,"请先设置主机地址",TastyToast.LENGTH_LONG,TastyToast.INFO).show();
                            }


                        }else {
                            baoCunBean.setZhangHuID(dianDialog.getJiuDianBean().getId());
                            baoCunBean.setZhangHuName(dianDialog.getJiuDianBean().getName());
                            baoCunBeanDao.update(baoCunBean);
                          //  TastyToast.makeText(SheZhiActivity.this,"更新成功",TastyToast.LENGTH_LONG,TastyToast.INFO).show();
                            baoCunBean=baoCunBeanDao.load(123456L);
                            dianDialog.dismiss();
                            if (baoCunBean.getZhuji()!=null){
                                link_save(dianDialog.getJiuDianBean().getName(),dianDialog.getJiuDianBean().getId());
                            }else {
                                TastyToast.makeText(SheZhiActivity.this,"请先设置主机地址",TastyToast.LENGTH_LONG,TastyToast.INFO).show();
                            }
                        }


                    }
                });
                dianDialog.setQuXiaoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dianDialog.dismiss();
                    }
                });
                if (baoCunBean!=null){
                    dianDialog.setContents(baoCunBean.getZhangHuID()+"",baoCunBean.getZhangHuName()+"");
                }
                dianDialog.show();
            }
        });

    }

    private String getSerialNumber(){
        String serial = null;
        try {
            Class<?> c =Class.forName("android.os.SystemProperties");
            Method get =c.getMethod("get", String.class);
            serial = (String)get.invoke(c, "ro.serialno");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }
    /**
     * 获取手机IMEI号
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();

        return imei;
    }

    /**
     * 获取手机IMSI号
     */
    public static String getIMSI(Context context){
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = mTelephonyMgr.getSubscriberId();

        return imsi ;
    }

    private void link_save(String name,String id) {
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
                .add("possId",getIMSI(SheZhiActivity.this)+"")
                .add("possName",name)
                .add("accountId",id)
                .build();
        // Log.d("InFoActivity3", userInfoBena.getGender());
               Request.Builder requestBuilder = new Request.Builder()
                 .header("Content-Type", "application/json")
                .post(body)
                .url(baoCunBean.getZhuji() + "/addPossEntity.do");

        if (tiJIaoDialog==null){
            tiJIaoDialog=new TiJIaoDialog(SheZhiActivity.this);
            tiJIaoDialog.show();
        }

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求识别失败"+e.getMessage());
                if (tiJIaoDialog!=null){
                    tiJIaoDialog.dismiss();
                    tiJIaoDialog=null;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TastyToast.makeText(SheZhiActivity.this,"请求失败,请检查网络",TastyToast.LENGTH_LONG,TastyToast.INFO).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (tiJIaoDialog!=null){
                    tiJIaoDialog.dismiss();
                    tiJIaoDialog=null;
                }
                Log.d("AllConnects", "请求识别成功"+call.request().toString());
                //获得返回体
                try {

                    ResponseBody body = response.body();
                    String ss=body.string().trim();
                   // Log.d("InFoActivity", "ss" + ss);
                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson=new Gson();
                    final FanHuiBean fanHuiBean=gson.fromJson(jsonObject,FanHuiBean.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast tastyToast= TastyToast.makeText(SheZhiActivity.this,fanHuiBean.getDtoDesc(),TastyToast.LENGTH_LONG,TastyToast.INFO);
                            tastyToast.setGravity(Gravity.CENTER,0,0);
                            tastyToast.show();

                        }
                    });

                }catch (Exception e){

                    if (tiJIaoDialog!=null){
                        tiJIaoDialog.dismiss();
                        tiJIaoDialog=null;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast tastyToast= TastyToast.makeText(SheZhiActivity.this,"数据异常",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                            tastyToast.setGravity(Gravity.CENTER,0,0);
                            tastyToast.show();

                        }
                    });
                    Log.d("WebsocketPushMsg", e.getMessage());
                }
            }
        });

        TextView banben= (TextView) findViewById(R.id.banben);
        banben.setText("V"+getVersionName(SheZhiActivity.this));
    }

    /**
     * 获取版本号
     * @param context 上下文
     * @return 版本号
     */
    public  String getVersionName(Context context){
        //获取包管理器
        PackageManager pm = context.getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(),0);
            return  packageInfo.versionName;
            //返回版本号
            //  return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0.0";
    }
}
