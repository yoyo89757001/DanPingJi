package com.example.danpingji.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.danpingji.MyAppLaction;
import com.example.danpingji.R;
import com.example.danpingji.beans.BaoCunBean;
import com.example.danpingji.beans.BaoCunBeanDao;
import com.example.danpingji.beans.FanHuiBean;
import com.example.danpingji.beans.Photos;
import com.example.danpingji.beans.ShiBieBean;
import com.example.danpingji.beans.ShouFangBean;
import com.example.danpingji.beans.UserInfoBena;
import com.example.danpingji.beans.YuYueBean;
import com.example.danpingji.beans.YuYueInterface;
import com.example.danpingji.dialog.JiaZaiDialog;
import com.example.danpingji.dialog.TiJIaoDialog;
import com.example.danpingji.dialog.XuanZeDialog;
import com.example.danpingji.utils.DateUtils;
import com.example.danpingji.utils.FileUtil;
import com.example.danpingji.utils.GsonUtil;
import com.example.danpingji.utils.ImageUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sdsmdg.tastytoast.TastyToast;
import com.tzutalin.dlib.FaceDet;
import com.tzutalin.dlib.VisionDetRet;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class YuYueActivity2 extends Activity implements YuYueInterface, SurfaceHolder.Callback {
    private EditText name,shouji,biduijieguo2,xiangsidu;
    private ImageView p1,p2;
    private Button chaxun,xiayibu;
    public static final int TIMEOUT = 1000 * 60;
    private BaoCunBeanDao baoCunBeanDao=null;
    private BaoCunBean baoCunBean=null;
    private String zhuji=null;
    private TiJIaoDialog tiJIaoDialog=null;
    private List<YuYueBean.ObjectsBean> objectsBeanList=null;
    private String shengfenzhengPath=null;
 //   private final int REQUEST_TAKE_PHOTO=33;
    private JiaZaiDialog  jiaZaiDialog=null;
    private UserInfoBena userInfoBena=null;
    private XuanZeDialog xuanZeDialog=null;
    private boolean isPai=false;
    private YuYueBean.ObjectsBean objectsBean=null;
    private SensorInfoReceiver sensorInfoReceiver;
    private SurfaceView surfaceView;
    private Camera mCamera;
    private SurfaceHolder sh;
    private android.view.ViewGroup.LayoutParams lp;
    private FaceDet mFaceDet;
    private Bitmap bmp2=null;
    private static boolean isTrue3=true;
    private static boolean isTrue4=false;
    private static int count=1;
    private TextView tishi;
    private ImageView imageView;
    private LinearLayout jiemian;
    private boolean isKD=false;
    private String filePath2=null;
    private String xiangsi="";
    private String biduijieguo="";
    private boolean bidui;
    private boolean isTiJiao=false;
    private TextView tishi1,tishi2;
    private Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yu_yue);
        baoCunBeanDao= MyAppLaction.myAppLaction.getDaoSession().getBaoCunBeanDao();
        baoCunBean=baoCunBeanDao.load(123456L);

        mFaceDet=MyAppLaction.mFaceDet;

        if (baoCunBean!=null && baoCunBean.getZhuji()!=null){
            zhuji=baoCunBean.getZhuji();
        }else {
            Toast tastyToast= TastyToast.makeText(YuYueActivity2.this,"请先设置主机地址",TastyToast.LENGTH_LONG,TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER,0,0);
            tastyToast.show();
        }
        userInfoBena=new UserInfoBena();
        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction("guanbi2");
        intentFilter1.addAction("guanbi");
        sensorInfoReceiver = new SensorInfoReceiver();
        registerReceiver(sensorInfoReceiver, intentFilter1);

        initView();

        lp = surfaceView.getLayoutParams();
        sh = surfaceView.getHolder();
        sh.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        sh.addCallback(this);

        OpenCameraAndSetSurfaceviewSize(0);

    }

    private class SensorInfoReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (action.equals("guanbi")) {
                count++;

                userInfoBena.setCardPhoto(intent.getStringExtra("cardPath"));
                userInfoBena.setScanPhoto(intent.getStringExtra("saomiaoPath"));
                bidui=intent.getBooleanExtra("biduijieguo",false);

                if (bidui){
                    biduijieguo2.setText("比对通过");
                    biduijieguo="比对通过";
                    jiemian.setVisibility(View.VISIBLE);
                        link_zhiliang();
                }else {

                    if (count>3){

                        jiemian.setVisibility(View.VISIBLE);
                        mCamera.stopPreview();
                        isTrue4=false;
                    }else {

                        isTrue4=true;
                    }
                    biduijieguo2.setText("比对不通过");
                    biduijieguo="比对不通过";

                }
                xiangsi=intent.getStringExtra("xiangsidu");
                xiangsidu.setText(intent.getStringExtra("xiangsidu")+"");


            }

            if (action.equals("guanbi2")) {
                finish();

            }
        }}

    private void initView() {
        tishi1= (TextView) findViewById(R.id.tishi1);
        tishi2= (TextView) findViewById(R.id.tishi2);
        tishi= (TextView) findViewById(R.id.tishi);
        jiemian= (LinearLayout) findViewById(R.id.jiemian);
        imageView= (ImageView) findViewById(R.id.ffff);
        surfaceView= (SurfaceView) findViewById(R.id.fff);
        name= (EditText) findViewById(R.id.name);
        shouji= (EditText) findViewById(R.id.shouji);
        biduijieguo2= (EditText) findViewById(R.id.jieguo);
        xiangsidu= (EditText) findViewById(R.id.xiangsidu);
        p1= (ImageView) findViewById(R.id.zhengjian);
        p2= (ImageView) findViewById(R.id.paizhao);
//        p2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isKD){
//
//
//                }else {
//                    Toast tastyToast = TastyToast.makeText(YuYueActivity.this, "请先查询出预约访客", TastyToast.LENGTH_LONG, TastyToast.ERROR);
//                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
//                    tastyToast.show();
//                }
//
//            }
//        });
        chaxun= (Button) findViewById(R.id.wancheng);
        chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().trim().equals("")&& shouji.getText().toString().trim().equals("")){
                    Toast tastyToast = TastyToast.makeText(YuYueActivity2.this, "请填写姓名和手机号", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
                    tastyToast.show();
                }else {
                    link_chaxun();
                }

            }
        });
        xiayibu= (Button) findViewById(R.id.xiayibu);
        xiayibu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bidui){
                    if (isTiJiao){
                        link_shenghe();
                    }else {
                        Toast tastyToast = TastyToast.makeText(YuYueActivity2.this, "照片质量不符合入库标准,请重新拍照!.", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                        tastyToast.setGravity(Gravity.CENTER, 0, 0);
                        tastyToast.show();
                    }

                }else {

                    Toast tastyToast = TastyToast.makeText(YuYueActivity2.this, "比对不通过,不能进行下一步", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
                    tastyToast.show();
                }

            }
        });
        animation = AnimationUtils.loadAnimation(YuYueActivity2.this, R.anim.alpha_anim);
        animation.setRepeatCount(-1);

    }

    private Void OpenCameraAndSetSurfaceviewSize(int cameraId) {
        mCamera = Camera.open(cameraId);
        Camera.Parameters parameters = mCamera.getParameters();
        Camera.Size pre_size = parameters.getPreviewSize();
        //  Camera.Size pic_size = parameters.getPictureSize();
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);

        lp.height =pre_size.height*2;
        lp.width = pre_size.width*2;

        mCamera.setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {

                Camera.Size size = camera.getParameters().getPreviewSize();
                try{
                    YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width, size.height, null);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    image.compressToJpeg(new android.graphics.Rect(0, 0, size.width, size.height), 80, stream);

                    bmp2 = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());

                    if (isTrue4) {
                        isTrue4=false;

                        List<VisionDetRet> results = mFaceDet.detect(bmp2);

                        if (results!=null) {

                            int s = results.size();
                            VisionDetRet face;
                            if (s > 0) {

                                if (s > count - 1) {

                                    face = results.get(count - 1);

                                } else {

                                    face = results.get(0);

                                }
                                int xx = 0;
                                int yy = 0;
                                int xx2 = 0;
                                int yy2 = 0;
                                int ww = bmp2.getWidth();
                                int hh = bmp2.getHeight();
                                if (face.getRight() - 340 >= 0) {
                                    xx = face.getRight() - 340;
                                } else {
                                    xx = 0;
                                }
                                if (face.getTop() - 3400 >= 0) {
                                    yy = face.getTop() - 340;
                                } else {
                                    yy = 0;
                                }
                                if (xx + 760 <= ww) {
                                    xx2 = 760;
                                } else {
                                    xx2 = ww - xx;
                                }
                                if (yy + 660 <= hh) {
                                    yy2 = 660;
                                } else {
                                    yy2 = hh - yy;
                                }


                                final Bitmap bitmap = Bitmap.createBitmap(bmp2, xx, yy, xx2, yy2);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        imageView.setImageBitmap(bitmap);
                                    }
                                });

                                String fn = "bbbb.jpg";
                                FileUtil.isExists(FileUtil.PATH, fn);
                                saveBitmap2File2(bitmap.copy(Bitmap.Config.ARGB_8888,false), FileUtil.SDPATH + File.separator + FileUtil.PATH + File.separator + fn, 100);

                            } else {
                                isTrue4 = true;
                            }

                        }

                    }
                    stream.close();

                }catch(Exception ex){
                    Log.e("Sys","Error:"+ex.getMessage());
                }
            }
        });

        return null;
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("InFoActivity2", "暂停");
        count=1;
        isTrue4=false;
        isTrue3=false;

        if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
            jiaZaiDialog.dismiss();
            jiaZaiDialog=null;
        }

    }

    public  void saveBitmap2File2(Bitmap bm, final String path, int quality) {
        try {
            filePath2=path;
            if (null == bm) {
                Log.d("InFoActivity", "回收|空");
                return ;
            }

            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap= BitmapFactory.decodeFile(FileUtil.SDPATH+ File.separator+FileUtil.PATH+File.separator+"bbbb.jpg");
                    p2.setImageBitmap(bitmap);
                    jiemian.setVisibility(View.VISIBLE);
                    tishi.setVisibility(View.VISIBLE);
                    tishi.setText("上传图片中。。。");
                }
            });

            link_P1(shengfenzhengPath);


        } catch (Exception e) {
            e.printStackTrace();

        } finally {
			if (!bm.isRecycled()) {
				bm.recycle();
			}
            bm = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        SetAndStartPreview(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        Log.d("InFoActivity3", "surfaceView销毁");

        if (mCamera != null) {
            mCamera.setPreviewCallback(null) ;
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }

    }

    private Void SetAndStartPreview(SurfaceHolder holder) {
        try {
            if (mCamera!=null){
                mCamera.setPreviewDisplay(holder);
                mCamera.setDisplayOrientation(0);
            }


        } catch (Exception e) {
            Log.d("InFoActivity2", e.getMessage()+"相机");

        }
        return null;
    }

    private void link_chaxun() {
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
        RequestBody body=null;
        try {

            body = new FormBody.Builder()
                    .add("name",name.getText().toString().trim())
                    .add("audit","0")
                    .add("compareTimeStart", DateUtils.timeData(System.currentTimeMillis()+"")+" 00:00")
                    .add("compareTimeEnd",DateUtils.timeData(System.currentTimeMillis()+"")+" 23:59")
                    .add("pageNum","1")
                    .add("pageSize","10")
                    .add("accountId",baoCunBean.getZhangHuID())
                    .add("visitDepartment",shouji.getText().toString().trim())
                    .build();


            Request.Builder requestBuilder = new Request.Builder()
                    // .header("Content-Type", "application/json")
                    .post(body)
                    .url(zhuji + "/iqueryCompares.do");

            if (tiJIaoDialog==null){
                tiJIaoDialog=new TiJIaoDialog(YuYueActivity2.this);
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

                    Log.d("AllConnects", "请求识别成功"+call.request().toString());
                    //获得返回体
                    try {

                        ResponseBody body = response.body();
                        String ss=body.string().trim();
                        Log.d("InFoActivity", ss);
                        JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
                        Gson gson=new Gson();
                        YuYueBean yuYueBean=gson.fromJson(jsonObject,YuYueBean.class);
                        objectsBeanList=yuYueBean.getObjects();
                        int size=objectsBeanList.size();
                        if (size==1){

                            objectsBean=objectsBeanList.get(0);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Glide.with(YuYueActivity2.this).load(zhuji+"/upload/compare/"+ objectsBeanList.get(0).getScanPhoto()).asBitmap().into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                            if (tiJIaoDialog!=null){
                                                tiJIaoDialog.dismiss();
                                                tiJIaoDialog=null;
                                            }
                                            String fn="yuyuezhao.jpg";
                                            FileUtil.isExists(FileUtil.PATH,fn);
                                            saveBitmap2File(resource, FileUtil.SDPATH+ File.separator+FileUtil.PATH+File.separator+fn,100);


                                            if (mCamera!=null){
                                                mCamera.startPreview();
                                                jiemian.setVisibility(View.GONE);
                                                count=1;

                                                tishi1.setVisibility(View.VISIBLE);
                                                tishi2.setVisibility(View.VISIBLE);

                                                CountDownTimer timer = new CountDownTimer(4000, 1000) {

                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        tishi2.setText((millisUntilFinished / 1000)+"");
                                                        if ((millisUntilFinished / 1000)==1){
                                                            tishi1.setText("开始自动抓拍!");
                                                            tishi2.setAnimation(null);
                                                        }
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        isTrue4=true;
                                                        tishi2.setVisibility(View.GONE);
                                                        tishi1.setVisibility(View.GONE);
                                                        tishi2.setAnimation(null);

                                                    }
                                                };
                                                timer.start();
                                                tishi2.setAnimation(animation);
                                                jiemian.setVisibility(View.GONE);

                                            }

                                        }
                                    });
                                }
                            });

                        }else if (size>1){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                     xuanZeDialog=new XuanZeDialog(YuYueActivity2.this,objectsBeanList,zhuji,YuYueActivity2.this);
                                    xuanZeDialog.setCanceledOnTouchOutside(false);
                                     xuanZeDialog.show();
                                }
                            });


                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (tiJIaoDialog!=null){
                                        tiJIaoDialog.dismiss();
                                        tiJIaoDialog=null;
                                    }
                                    Toast tastyToast = TastyToast.makeText(YuYueActivity2.this, "没有查询到数据", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast tastyToast = TastyToast.makeText(YuYueActivity2.this, "查询失败...", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                tastyToast.show();

                            }
                        });
                        Log.d("WebsocketPushMsg", e.getMessage());
                    }
                }
            });

        }catch (NullPointerException e){
            Log.d("InFoActivity2", e.getMessage()+"");
        }

    }

    private void kill_camera() {
      //  Log.d("InFoActivity3", "销毁");
        try {
            isTrue4=false;
            isTrue3=false;
            surfaceView.setVisibility(View.GONE);
            if (mCamera!=null){
                mCamera.stopPreview();
                mCamera.release();
            }

        }catch (Exception e){
            Log.d("InFoActivity2", e.getMessage()+"销毁");
        }


    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            switch (requestCode) {
//                case REQUEST_TAKE_PHOTO:  //拍照
//                    //注意，如果拍照的时候设置了MediaStore.EXTRA_OUTPUT，data.getData=null
//                   // p2.setImageURI(Uri.fromFile(mSavePhotoFile));
//                    isPai=true;
//                    Glide.with(YuYueActivity.this)
//                            .load(mSavePhotoFile)
//                            .skipMemoryCache(true)
//                            .diskCacheStrategy(DiskCacheStrategy.NONE)
//                           // .transform(new GlideCircleTransform(RenGongFuWuActivity.this,1, Color.parseColor("#ffffffff")))
//                            .into(p2);
//                    break;
//
//            }
//        }
//    }

//    /**
//     * 启动拍照
//     */
//    private void startCamera() {
//
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            // Continue only if the File was successfully created
//            if (mSavePhotoFile != null) {
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                        Uri.fromFile(mSavePhotoFile));//设置文件保存的URI
//                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//            }
//        }
//    }


    /***
     *保存bitmap对象到文件中
     */
    public  void saveBitmap2File(Bitmap bm, final String path, int quality) {
        if (null == bm || bm.isRecycled()) {
            Log.d("InFoActivity", "回收|空");
            return ;
        }
        try {

            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();
            shengfenzhengPath=path;

        } catch (Exception e) {
            e.printStackTrace();

        } finally {

            if (!bm.isRecycled()) {
                bm.recycle();
            }
            bm = null;
            if (shengfenzhengPath!=null)
            p1.setImageBitmap(ImageUtil.decodeSampledBitmapFromFilePath(shengfenzhengPath,280,280));
        }
    }

    private void link_P1(String filename1) {
        jiaZaiDialog=new JiaZaiDialog(YuYueActivity2.this);
        jiaZaiDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        jiaZaiDialog.setText("上传图片中...");
        jiaZaiDialog.show();

        //final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        //http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
        OkHttpClient okHttpClient= new OkHttpClient.Builder()
                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();

         /* 第一个要上传的file */
        File file1 = new File(filename1);
        RequestBody fileBody1 = RequestBody.create(MediaType.parse("application/octet-stream") , file1);
        final String file1Name = System.currentTimeMillis()+"testFile1.jpg";

//    /* 第二个要上传的文件,*/
//        File file2 = new File(fileName2);
//        RequestBody fileBody2 = RequestBody.create(MediaType.parse("application/octet-stream") , file2);
//        String file2Name =System.currentTimeMillis()+"testFile2.jpg";


//    /* form的分割线,自己定义 */
//        String boundary = "xx--------------------------------------------------------------xx";

        MultipartBody mBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
            /* 底下是上传了两个文件 */
                .addFormDataPart("voiceFile" , file1Name , fileBody1)
                  /* 上传一个普通的String参数 */
                //  .addFormDataPart("subject_id" , subject_id+"")
                //  .addFormDataPart("image_2" , file2Name , fileBody2)
                .build();
        Request.Builder requestBuilder = new Request.Builder()
                // .header("Content-Type", "application/json")
                .post(mBody)
                .url(zhuji + "/AppFileUploadServlet?FilePathPath=cardFilePath&AllowFileType=.jpg,.gif,.jpeg,.bmp,.png&MaxFileSize=10");

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
                            jiaZaiDialog.dismiss();
                            jiaZaiDialog=null;
                        }
                        Toast tastyToast= TastyToast.makeText(YuYueActivity2.this,"上传图片出错，请返回后重试！",TastyToast.LENGTH_LONG,TastyToast.ERROR);
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
                    String ss=body.string();

                    Log.d("AllConnects", "aa   "+ss);

                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson=new Gson();
                    Photos zhaoPianBean=gson.fromJson(jsonObject,Photos.class);
                    userInfoBena.setCardPhoto(zhaoPianBean.getExDesc());
                    link_P2();


                }catch (Exception e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
                                jiaZaiDialog.dismiss();
                                jiaZaiDialog=null;
                            }
                            Toast tastyToast= TastyToast.makeText(YuYueActivity2.this,"上传图片出错，请返回后重试！",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                            tastyToast.setGravity(Gravity.CENTER,0,0);
                            tastyToast.show();
                        }
                    });
                    Log.d("WebsocketPushMsg", e.getMessage());
                }
            }
        });

    }

    private void link_P2() {
        //final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        //http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
        OkHttpClient okHttpClient= new OkHttpClient.Builder()
                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();

//         /* 第一个要上传的file */
//        File file1 = new File(filename1);
//        RequestBody fileBody1 = RequestBody.create(MediaType.parse("application/octet-stream") , file1);
//        final String file1Name = System.currentTimeMillis()+"testFile1.jpg";

    /* 第二个要上传的文件,*/
        File file2 = new File(filePath2);

        RequestBody fileBody2 = RequestBody.create(MediaType.parse("application/octet-stream") , file2);
        String file2Name =System.currentTimeMillis()+"testFile2.jpg";


//    /* form的分割线,自己定义 */
//        String boundary = "xx--------------------------------------------------------------xx";

        MultipartBody mBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
            /* 底下是上传了两个文件 */
                //  .addFormDataPart("image_1" , file1Name , fileBody1)
                  /* 上传一个普通的String参数 */
                //  .addFormDataPart("subject_id" , subject_id+"")
                .addFormDataPart("voiceFile" , file2Name , fileBody2)
                .build();
        Request.Builder requestBuilder = new Request.Builder()
                // .header("Content-Type", "application/json")
                .post(mBody)
                .url(zhuji + "/AppFileUploadServlet?FilePathPath=compareFilePath&AllowFileType=.jpg,.gif,.jpeg,.bmp,.png&MaxFileSize=10");


        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求识别失败"+e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
                            jiaZaiDialog.dismiss();
                            jiaZaiDialog=null;
                        }
                        Toast tastyToast= TastyToast.makeText(YuYueActivity2.this,"上传图片出错，请返回后重试！",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                        tastyToast.setGravity(Gravity.CENTER,0,0);
                        tastyToast.show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求识别成功"+call.request().toString());
                //删掉文件

                //获得返回体
                try {
                    ResponseBody body = response.body();
                    String ss=body.string();
                     Log.d("AllConnects", "aa   "+ss);
                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson=new Gson();
                    Photos zhaoPianBean=gson.fromJson(jsonObject,Photos.class);
                    userInfoBena.setScanPhoto(zhaoPianBean.getExDesc());

                    link_tianqi3();


                }catch (Exception e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
                                jiaZaiDialog.dismiss();
                                jiaZaiDialog=null;
                            }
                            Toast tastyToast= TastyToast.makeText(YuYueActivity2.this,"上传图片出错，请返回后重试！",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                            tastyToast.setGravity(Gravity.CENTER,0,0);
                            tastyToast.show();
                        }
                    });
                    Log.d("WebsocketPushMsg", e.getMessage());
                }
            }
        });

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
                .add("cardPhoto",userInfoBena.getCardPhoto())
                .add("scanPhoto",userInfoBena.getScanPhoto())
                .build();

        Request.Builder requestBuilder = new Request.Builder()
                // .header("Content-Type", "application/json")
                .post(body)
                .url(zhuji + "/compare.do");


        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
                            jiaZaiDialog.dismiss();
                            jiaZaiDialog=null;
                        }
                        Toast tastyToast= TastyToast.makeText(YuYueActivity2.this,"上传图片出错，请返回后重试！",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                        tastyToast.setGravity(Gravity.CENTER,0,0);
                        tastyToast.show();
                    }
                });
                Log.d("AllConnects", "请求识别失败"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
                            jiaZaiDialog.dismiss();
                            jiaZaiDialog=null;
                        }
                    }
                });
                Log.d("AllConnects", "请求识别成功"+call.request().toString());
                //获得返回体
                try {

                    ResponseBody body = response.body();
                    // Log.d("AllConnects", "识别结果返回"+response.body().string());
                    String ss=body.string();
                    Log.d("InFoActivity", ss);
                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson=new Gson();
                    final ShiBieBean zhaoPianBean=gson.fromJson(jsonObject,ShiBieBean.class);

                    if (zhaoPianBean.getScore()>=65.0) {

                        //比对成功
                        sendBroadcast(new Intent("guanbi").putExtra("biduijieguo",true)
                                .putExtra("xiangsidu",(zhaoPianBean.getScore()+"").substring(0,5))
                                .putExtra("cardPath",userInfoBena.getCardPhoto()).putExtra("saomiaoPath",userInfoBena.getScanPhoto()));


                    }else {

                        sendBroadcast(new Intent("guanbi").putExtra("biduijieguo",false)
                                .putExtra("xiangsidu",(zhaoPianBean.getScore()+"").substring(0,5))
                                .putExtra("cardPath",userInfoBena.getCardPhoto()).putExtra("saomiaoPath",userInfoBena.getScanPhoto()));

                    }


                }catch (Exception e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
                                jiaZaiDialog.dismiss();
                                jiaZaiDialog=null;
                            }
                        }
                    });

                    sendBroadcast(new Intent("guanbi").putExtra("biduijieguo",false).putExtra("xiangsidu","43.21")
                            .putExtra("cardPath",userInfoBena.getCardPhoto()).putExtra("saomiaoPath",userInfoBena.getScanPhoto()));

                    Log.d("WebsocketPushMsg", e.getMessage());
                }
            }
        });

    }




    @Override
    public void setP( int position) {

        objectsBean=objectsBeanList.get(position);

        Glide.with(YuYueActivity2.this).load(zhuji+"/upload/compare/"+ objectsBeanList.get(position).getScanPhoto()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (tiJIaoDialog!=null){
                    tiJIaoDialog.dismiss();
                    tiJIaoDialog=null;
                }

                xuanZeDialog.dismiss();
                xuanZeDialog=null;
                String fn="yuyuezhao.jpg";
                FileUtil.isExists(FileUtil.PATH,fn);
                saveBitmap2File(resource, FileUtil.SDPATH+ File.separator+FileUtil.PATH+File.separator+fn,100);

             //  isKD=true;
                if (mCamera!=null){
                    mCamera.startPreview();
                    jiemian.setVisibility(View.GONE);
                    count=1;
                    tishi1.setVisibility(View.VISIBLE);
                    tishi2.setVisibility(View.VISIBLE);

                    CountDownTimer timer = new CountDownTimer(4000, 1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            tishi2.setText((millisUntilFinished / 1000)+"");
                            if ((millisUntilFinished / 1000)==1){
                                tishi1.setText("开始自动抓拍!");
                                tishi2.setText("0");
                            }
                        }

                        @Override
                        public void onFinish() {
                            isTrue4=true;
                            tishi2.setVisibility(View.GONE);
                            tishi1.setVisibility(View.GONE);
                            tishi2.setAnimation(null);

                        }
                    };
                    timer.start();
                    tishi2.setAnimation(animation);
                    jiemian.setVisibility(View.GONE);
                }

            }
        });
    }


    private void link_shenghe() {
        tiJIaoDialog=new TiJIaoDialog(YuYueActivity2.this);
        tiJIaoDialog.show();

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
                .add("audit","1")
                .add("accountId",baoCunBean.getZhangHuID())
                .add("reason","默认POS机审核")
                .add("id",objectsBean.getId()+"")
                .build();


        Request.Builder requestBuilder = new Request.Builder()
                // .header("Content-Type", "application/json")
                .post(body)
                .url(zhuji + "/iauditVisitor.do");

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("AllConnects", "请求识别失败"+e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        xiayibu.setEnabled(true);
                        if (tiJIaoDialog!=null){
                            tiJIaoDialog.dismiss();
                            tiJIaoDialog=null;
                        }
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        xiayibu.setEnabled(true);
                        if (tiJIaoDialog!=null){
                            tiJIaoDialog.dismiss();
                            tiJIaoDialog=null;
                        }
                    }
                });
                Log.d("AllConnects", "请求识别成功"+call.request().toString());
                //获得返回体
                try {

                    ResponseBody body = response.body();
                    String ss=body.string().trim();
                  //  Log.d("DengJiActivity", ss);

                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson=new Gson();
                    FanHuiBean fanHuiBean=gson.fromJson(jsonObject,FanHuiBean.class);

                    if (fanHuiBean.getDtoResult()==0){
//                        ChuanSongBean bean=new ChuanSongBean(name.getText().toString(),2,objectsBean.getId(),objectsBean.getPhone()
//                                ,objectsBean.getVisitPerson(),DateUtils.timet2(objectsBean.getVisitDate()+""),"");
//                        Bundle bundle = new Bundle();
//                        bundle.putParcelable("chuansong", Parcels.wrap(bean));
//                        startActivity(new Intent(YuYueActivity.this,ShiYouActivity.class).putExtras(bundle));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast tastyToast= TastyToast.makeText(YuYueActivity2.this,"提交成功",TastyToast.LENGTH_LONG,TastyToast.INFO);
                                tastyToast.setGravity(Gravity.CENTER,0,0);
                                tastyToast.show();
                                finish();
                            }
                        });

                    }else {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast tastyToast= TastyToast.makeText(YuYueActivity2.this,"审核失败",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                                tastyToast.setGravity(Gravity.CENTER,0,0);
                                tastyToast.show();

                            }
                        });

                    }

                }catch (Exception e){

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            xiayibu.setEnabled(true);
                            if (tiJIaoDialog!=null){
                                tiJIaoDialog.dismiss();
                                tiJIaoDialog=null;
                            }
                            Toast tastyToast= TastyToast.makeText(YuYueActivity2.this,"提交失败,请检查网络",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                            tastyToast.setGravity(Gravity.CENTER,0,0);
                            tastyToast.show();
                        }
                    });
                    Log.d("WebsocketPushMsg", e.getMessage());
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        kill_camera();
        unregisterReceiver(sensorInfoReceiver);
        super.onDestroy();

    }

    private void link_zhiliang() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (tiJIaoDialog==null && !YuYueActivity2.this.isFinishing()){
                    tiJIaoDialog=new TiJIaoDialog(YuYueActivity2.this);
                    tiJIaoDialog.show();
                }
            }
        });


        //final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        //http://192.168.2.4:8080/sign?cmd=getUnSignList&subjectId=jfgsdf
        OkHttpClient okHttpClient= new OkHttpClient.Builder()
                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();

        if (null!=baoCunBean.getZhangHuID()) {


            //    /* form的分割线,自己定义 */
            //        String boundary = "xx--------------------------------------------------------------xx";
            RequestBody body = new FormBody.Builder()
                    .add("scanPhoto", userInfoBena.getScanPhoto())
                    .add("accountId", baoCunBean.getZhangHuID() + "")
                    .build();


            Request.Builder requestBuilder = new Request.Builder()
                    // .header("Content-Type", "application/json")
                    .post(body)
                    .url(zhuji + "/faceQuality.do");

            // step 3：创建 Call 对象
            Call call = okHttpClient.newCall(requestBuilder.build());

            //step 4: 开始异步请求
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("AllConnects", "请求识别失败" + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (tiJIaoDialog != null) {
                                tiJIaoDialog.dismiss();
                                tiJIaoDialog = null;
                            }
                        }
                    });

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (tiJIaoDialog != null) {
                                tiJIaoDialog.dismiss();
                                tiJIaoDialog = null;
                            }
                        }
                    });
                    Log.d("AllConnects", "请求识别成功" + call.request().toString());
                    //获得返回体
                    try {

                        ResponseBody body = response.body();
                        String ss = body.string().trim();
                        Log.d("DengJiActivity", ss);

                        JsonObject jsonObject = GsonUtil.parse(ss).getAsJsonObject();
                        Gson gson = new Gson();
                        ShouFangBean zhaoPianBean = gson.fromJson(jsonObject, ShouFangBean.class);

                        if (zhaoPianBean.getDtoResult() != 0) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (!YuYueActivity2.this.isFinishing()) {
                                        Toast tastyToast = TastyToast.makeText(YuYueActivity2.this, "照片质量不符合入库要求,请拍正面照!", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                        tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                        tastyToast.show();
                                    }


                                }
                            });

                        } else {

                            isTiJiao = true;
                        }

                    } catch (Exception e) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (tiJIaoDialog != null) {
                                    tiJIaoDialog.dismiss();
                                    tiJIaoDialog = null;
                                }
                            }
                        });
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast tastyToast = TastyToast.makeText(YuYueActivity2.this, "提交失败,请检查网络", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                tastyToast.show();

                            }
                        });
                        Log.d("WebsocketPushMsg", e.getMessage());
                    }
                }
            });
        }else {
            Toast tastyToast = TastyToast.makeText(YuYueActivity2.this, "账户ID为空!,请设置帐户ID", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER, 0, 0);
            tastyToast.show();
        }


    }
}
