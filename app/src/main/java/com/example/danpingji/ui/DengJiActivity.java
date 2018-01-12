package com.example.danpingji.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.danpingji.MyAppLaction;
import com.example.danpingji.R;
import com.example.danpingji.beans.BaoCunBean;
import com.example.danpingji.beans.BaoCunBeanDao;
import com.example.danpingji.beans.BuMenBeans;
import com.example.danpingji.beans.ChuanSongBean;
import com.example.danpingji.beans.PopupWindowAdapter;
import com.example.danpingji.beans.ShouFangBean;
import com.example.danpingji.dialog.ShouFangRenBean;
import com.example.danpingji.dialog.TiJIaoDialog;
import com.example.danpingji.dialog.YuYueDialog;
import com.example.danpingji.utils.DateUtils;
import com.example.danpingji.utils.FileUtil;
import com.example.danpingji.utils.GsonUtil;
import com.example.danpingji.view.GlideCircleTransform;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sdsmdg.tastytoast.TastyToast;

import org.parceler.Parcels;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DengJiActivity extends Activity implements View.OnClickListener {
    private ImageView riqi_im,touxiang;
    private TextView riqi_tv,name,bidui_tv,bumen_ET;
    private EditText shoufangren,shoufangrenshu,laifangrendianhua;
    private Button wancheng;
    private SensorInfoReceiver sensorInfoReceiver;
    public static final int TIMEOUT = 1000 * 60;
    private TiJIaoDialog tiJIaoDialog=null;
    private String zhuji=null;
    private YuYueDialog dialog=null;
    private BaoCunBeanDao baoCunBeanDao=null;
    private BaoCunBean baoCunBean=null;
    private ChuanSongBean chuanSongBean=null;
    private List<String> stringList=new ArrayList<>();
    private PopupWindow popupWindow=null;
    private PopupWindowAdapter adapterss;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deng_ji);

        baoCunBeanDao= MyAppLaction.myAppLaction.getDaoSession().getBaoCunBeanDao();
        baoCunBean=baoCunBeanDao.load(123456L);
        if (baoCunBean!=null && baoCunBean.getZhuji()!=null){
            zhuji=baoCunBean.getZhuji();
        }else {
            Toast tastyToast= TastyToast.makeText(DengJiActivity.this,"请先设置主机地址",TastyToast.LENGTH_LONG,TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER,0,0);
            tastyToast.show();
        }


        chuanSongBean= Parcels.unwrap(getIntent().getParcelableExtra("chuansong"));


        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction("guanbi2");
        sensorInfoReceiver = new SensorInfoReceiver();
        registerReceiver(sensorInfoReceiver, intentFilter1);

        laifangrendianhua= (EditText) findViewById(R.id.editText12);
        touxiang= (ImageView) findViewById(R.id.touxiang);
        bidui_tv= (TextView) findViewById(R.id.bidui_tv);
        name= (TextView) findViewById(R.id.editText);
        bumen_ET= (TextView) findViewById(R.id.bumen);
        bumen_ET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stringList.size()>0) {
                View contentView = LayoutInflater.from(DengJiActivity.this).inflate(R.layout.xiangmu_po_item, null);

                ListView listView = (ListView) contentView.findViewById(R.id.dddddd);
                adapterss = new PopupWindowAdapter(DengJiActivity.this, stringList);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        bumen_ET.setText(stringList.get(position));
                        popupWindow.dismiss();
                    }
                });
                listView.setAdapter(adapterss);
                popupWindow = new PopupWindow(contentView, 200, setListViewHeightBasedOnChildren(listView));
                popupWindow.setFocusable(true);//获取焦点
                popupWindow.setOutsideTouchable(true);//获取外部触摸事件
                popupWindow.setTouchable(true);//能够响应触摸事件
                popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//设置背景
                popupWindow.showAsDropDown(bumen_ET, bumen_ET.getLeft() - 100, 0);
            }else {
                Toast tastyToast= TastyToast.makeText(DengJiActivity.this,"没有部门数据",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                tastyToast.setGravity(Gravity.CENTER,0,0);
                tastyToast.show();
            }

            }
        });
        riqi_tv= (TextView) findViewById(R.id.riqi);
        shoufangren= (EditText) findViewById(R.id.shoufang);
        shoufangrenshu= (EditText) findViewById(R.id.renshu);
        shoufangrenshu.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (zhuji!=null)
                    link_save();
                }
                return false;
            }
        });
        riqi_im= (ImageView) findViewById(R.id.imageView);
        riqi_im.setOnClickListener(this);

        wancheng= (Button) findViewById(R.id.queren2);
        wancheng.setOnClickListener(this);

        if (chuanSongBean.getBeifangshijian().equals("")){
            riqi_tv.setText(DateUtils.timet2(System.currentTimeMillis()+""));
        }else {
            riqi_tv.setText(chuanSongBean.getBeifangshijian());
        }

        shoufangren.setText(chuanSongBean.getBeifangren());
        name.setText(chuanSongBean.getName());
        bidui_tv.setText("比对通过");


        Glide.with(DengJiActivity.this)
                .load(FileUtil.SDPATH + File.separator + FileUtil.PATH + File.separator + "bbbb.jpg")
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(new GlideCircleTransform(DengJiActivity.this,2,Color.parseColor("#ffffffff")))
                .into(touxiang);
        link_bumen();
        link_shoufangren();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView: //日期

                Intent intent = new Intent(DengJiActivity.this, DatePickActivity.class);
                startActivityForResult(intent,2);

                break;
//            case R.id.imageView2: //部门
//                View popupView =getLayoutInflater().inflate(R.layout.popupwindow, null);
//                //  2016/5/17 为了演示效果，简单的设置了一些数据，实际中大家自己设置数据即可，相信大家都会。
//                ListView lsvMore = (ListView) popupView.findViewById(R.id.lsvMore);
//                lsvMore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        xuanzhong=position;
//                        window.dismiss();
//
//                    }
//                });
//                lsvMore.setAdapter(myadapter);
//                //  2016/5/17 创建PopupWindow对象，指定宽度和高度
//                 window = new PopupWindow(popupView, 200, 340);
//                //  2016/5/17 设置动画
//               // window.setAnimationStyle(R.style.AnimBottom2);
//                //  2016/5/17 设置背景颜色
//                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#cc4285f4")));
//                //  2016/5/17 设置可以获取焦点
//                window.setFocusable(true);
//                //  2016/5/17 设置可以触摸弹出框以外的区域
//                window.setOutsideTouchable(true);
//                // 更新popupwindow的状态
//                window.update();
//                //  2016/5/17 以下拉的方式显示，并且可以设置显示的位置
//                window.showAsDropDown(bumen_im, -200,0);
//
//
//                break;
            case R.id.queren2:

                if (zhuji!=null)

                if (shoufangrenshu.getText().toString().trim().equals("") && laifangrendianhua.getText().toString().trim().equals("")){
                    link_save();
                }else {
                    if (!shoufangrenshu.getText().toString().trim().equals("") && !laifangrendianhua.getText().toString().trim().equals("")){
                        if (isMobile(shoufangrenshu.getText().toString().trim()) && isMobile(laifangrendianhua.getText().toString().trim())){
                            link_save();
                        }else {
                            Toast tastyToast= TastyToast.makeText(DengJiActivity.this,"手机号码格式不正确",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                            tastyToast.setGravity(Gravity.CENTER,0,0);
                            tastyToast.show();
                        }
                    }else if (shoufangrenshu.getText().toString().trim().equals("") && !laifangrendianhua.getText().toString().trim().equals("")){
                        if (isMobile(laifangrendianhua.getText().toString().trim())){
                            link_save();
                        }else {
                            Toast tastyToast= TastyToast.makeText(DengJiActivity.this,"手机号码格式不正确",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                            tastyToast.setGravity(Gravity.CENTER,0,0);
                            tastyToast.show();
                        }
                    }else if (!shoufangrenshu.getText().toString().trim().equals("") && laifangrendianhua.getText().toString().trim().equals("")){
                        if (isMobile(shoufangrenshu.getText().toString().trim())){
                            link_save();
                        }else {
                            Toast tastyToast= TastyToast.makeText(DengJiActivity.this,"手机号码格式不正确",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                            tastyToast.setGravity(Gravity.CENTER,0,0);
                            tastyToast.show();
                        }
                    }

                }


                break;

        }

    }
    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][35789]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

//    private class Myadapter extends BaseAdapter{
//        Context mContext;
//
//        LayoutInflater mInflater;
//
//        List<String> mDataList;
//
//        /**
//         * @param context
//         * @param data
//         */
//        public Myadapter(Context context, List<String> data) {
//            mContext = context;
//            mInflater = LayoutInflater.from(context);
//            mDataList = data;
//        }
//
//        @Override
//        public int getCount() {
//            return mDataList.size();
//        }
//
//        @Override
//        public String getItem(int position) {
//
//            return mDataList.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder viewHolder = null;
//            if (convertView == null) {
//                convertView = mInflater.inflate(R.layout.tachuangdialog, null, false);
//                viewHolder = new ViewHolder();
//                viewHolder.mTextView = (TextView) convertView.findViewById(R.id.fffff);
//                convertView.setTag(viewHolder);
//            } else {
//                viewHolder = (ViewHolder) convertView.getTag();
//            }
//
//
//            viewHolder.mTextView.setText(getItem(position));
//            return convertView;
//        }
//
//        /**
//         * ViewHolder
//         *
//         * @author mrsimple
//         */
//        private class ViewHolder {
//
//            TextView mTextView;
//        }
//
//    }

    private class SensorInfoReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (action.equals("guanbi2")) {
                finish();

            }
        }}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 2) {
            // 选择预约时间的页面被关闭
            String date = data.getStringExtra("date");
            riqi_tv.setText(date);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(sensorInfoReceiver);
        if (dialog!=null && dialog.isShowing()){
            dialog.dismiss();
        }
    }


    private void link_save() {
        //final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        //http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
        OkHttpClient okHttpClient= new OkHttpClient.Builder()
                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();
      //  Log.d("DengJiActivity", "chuanSongBean.getId():" + chuanSongBean.getId());

//    /* form的分割线,自己定义 */
//        String boundary = "xx--------------------------------------------------------------xx";
        RequestBody body = new FormBody.Builder()
                .add("id",chuanSongBean.getId()+"")
                .add("visitIncident",chuanSongBean.getShiyou())
                .add("accountId",baoCunBean.getZhangHuID())
                .add("visitDate2",riqi_tv.getText().toString().trim())
                .add("visitDepartment",bumen_ET.getText().toString().trim()+"")
                .add("visitPerson",shoufangren.getText().toString().trim()+"")
                .add("visitNum","1")
                .add("source","1")
                .add("phone",laifangrendianhua.getText().toString().trim())
                .add("homeNumber",shoufangrenshu.getText().toString().trim())
                .build();

        Request.Builder requestBuilder = new Request.Builder()
                // .header("Content-Type", "application/json")
                .post(body)
                .url(zhuji + "/saveCompareVisit.do");

        if (tiJIaoDialog==null){
            tiJIaoDialog=new TiJIaoDialog(DengJiActivity.this);
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
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (tiJIaoDialog != null) {
                    tiJIaoDialog.dismiss();
                    tiJIaoDialog = null;
                }
            try {

                //获得返回体
                ResponseBody body = response.body();
                String ss = body.string().trim();
                Log.d("DengJiActivity", "请求识别成功" + ss);

                JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                Gson gson = new Gson();
                ShouFangBean zhaoPianBean = gson.fromJson(jsonObject, ShouFangBean.class);

                if (zhaoPianBean.getDtoResult() == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog = new YuYueDialog(DengJiActivity.this, "你已成功预约,感谢你的来访!");
                            dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
                            dialog.setOnPositiveListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    Intent intent = new Intent("guanbi2");
                                    sendBroadcast(intent);
                                }
                            });
                            dialog.setOnQuXiaoListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    ChuanSongBean bean=new ChuanSongBean(name.getText().toString(),chuanSongBean.getType(),chuanSongBean.getId(),
                                            chuanSongBean.getDianhua()
                                            ,shoufangren.getText().toString().trim(),riqi_tv.getText().toString().trim(),chuanSongBean.getShiyou());
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable("chuansong", Parcels.wrap(bean));
                                   // startActivity(new Intent(DengJiActivity.this,DaYingActivity.class).putExtras(bundle));

                                }
                            });

                            dialog.show();
                        }
                    });


                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast tastyToast = TastyToast.makeText(DengJiActivity.this, "提交失败,请检查网络", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            tastyToast.setGravity(Gravity.CENTER, 0, 0);
                            tastyToast.show();

                        }
                    });

                }

            }catch (Exception e){

                    if (tiJIaoDialog!=null){
                        tiJIaoDialog.dismiss();
                        tiJIaoDialog=null;
                    }
                    Log.d("WebsocketPushMsg", e.getMessage());
                }
            }
        });
    }
    private void link_bumen() {

        //final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        //http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
        OkHttpClient okHttpClient= new OkHttpClient.Builder()
                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();

        if (null!=baoCunBean.getZhuji()) {

            //    /* form的分割线,自己定义 */
            //        String boundary = "xx--------------------------------------------------------------xx";
            RequestBody body = new FormBody.Builder()
                    .add("status","1")
                    .add("token",System.currentTimeMillis()+"")
                    .add("accountId",baoCunBean.getZhangHuID())
                    .build();

            Request.Builder requestBuilder = new Request.Builder()
                    // .header("Content-Type", "application/json")
                    .post(body)
                    .url(baoCunBean.getZhuji() + "/queryAllDept.do");

            // step 3：创建 Call 对象
            Call call = okHttpClient.newCall(requestBuilder.build());

            //step 4: 开始异步请求
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("AllConnects", "请求识别失败" + e.getMessage());


                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String ss=null;
                    Log.d("AllConnects", "请求识别成功" + call.request().toString());
                    //获得返回体
                    try {

                        ResponseBody body = response.body();
                        ss = body.string().trim();
                        Log.d("DengJiActivity", ss);

                        JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                        Gson gson = new Gson();
                        BuMenBeans zhaoPianBean = gson.fromJson(jsonObject, BuMenBeans.class);
                        int size=zhaoPianBean.getObjects().size();
                        if (stringList.size()>0){
                            stringList.clear();
                        }
                        for (int i=0;i<size;i++){
                            stringList.add(zhaoPianBean.getObjects().get(i).getDeptName());
                        }
                        Collections.reverse(stringList); // 倒序排列


                    } catch (Exception e) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (tiJIaoDialog != null && !DengJiActivity.this.isFinishing()) {
                                    tiJIaoDialog.dismiss();
                                    tiJIaoDialog = null;
                                }
                            }
                        });

                        Log.d("WebsocketPushMsg", e.getMessage());
                    }
                }
            });
        }else {
            Toast tastyToast = TastyToast.makeText(DengJiActivity.this, "账户ID为空!,请设置帐户ID", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER, 0, 0);
            tastyToast.show();
        }
    }
    private void link_shoufangren() {

        //final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        //http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
        OkHttpClient okHttpClient= new OkHttpClient.Builder()
                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();

        if (null!=baoCunBean.getZhuji()) {

            //    /* form的分割线,自己定义 */
            //        String boundary = "xx--------------------------------------------------------------xx";
            RequestBody body = new FormBody.Builder()
                    .add("status","1")
                    .add("token",System.currentTimeMillis()+"")
                    .add("accountId",baoCunBean.getZhangHuID())
                    .build();

            Request.Builder requestBuilder = new Request.Builder()
                    // .header("Content-Type", "application/json")
                    .post(body)
                    .url(baoCunBean.getZhuji() + "/searchPhone.do");

            // step 3：创建 Call 对象
            Call call = okHttpClient.newCall(requestBuilder.build());

            //step 4: 开始异步请求
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("AllConnects", "请求识别失败" + e.getMessage());


                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String ss=null;
                    Log.d("AllConnects", "请求识别成功" + call.request().toString());
                    //获得返回体
                    try {

                        ResponseBody body = response.body();
                        ss = body.string().trim();
                        Log.d("DengJiActivity", ss+"来访人");

                        JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                        Gson gson = new Gson();
                        ShouFangRenBean zhaoPianBean = gson.fromJson(jsonObject, ShouFangRenBean.class);
                        int size=zhaoPianBean.getObjects().size();

                   //     Collections.reverse(stringList); // 倒序排列


                    } catch (Exception e) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (tiJIaoDialog != null && !DengJiActivity.this.isFinishing()) {
                                    tiJIaoDialog.dismiss();
                                    tiJIaoDialog = null;
                                }
                            }
                        });

                        Log.d("WebsocketPushMsg", e.getMessage());
                    }
                }
            });
        }else {
            Toast tastyToast = TastyToast.makeText(DengJiActivity.this, "账户ID为空!,请设置帐户ID", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER, 0, 0);
            tastyToast.show();
        }
    }

    public int setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return 0;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            // listItem.measure(0, 0);
            listItem.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += listItem.getMeasuredHeight();
        }

//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight
//                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
        return totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
    }

}
