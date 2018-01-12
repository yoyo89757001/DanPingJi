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
import com.example.danpingji.MainActivity;
import com.example.danpingji.MyAppLaction;
import com.example.danpingji.R;
import com.example.danpingji.beans.BaoCunBean;
import com.example.danpingji.beans.BaoCunBeanDao;
import com.example.danpingji.beans.ChuanSongBean;
import com.example.danpingji.beans.Photos;
import com.example.danpingji.beans.ShiBieBean;
import com.example.danpingji.beans.ShouFangBean;
import com.example.danpingji.beans.UserInfoBena;
import com.example.danpingji.dialog.JiaZaiDialog;
import com.example.danpingji.dialog.QueRenDialog;
import com.example.danpingji.dialog.TiJIaoDialog;
import com.example.danpingji.grg.idcard.IDCardMsg;
import com.example.danpingji.grg.idcard.IDCardRecognition;
import com.example.danpingji.utils.FileUtil;
import com.example.danpingji.utils.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sdsmdg.tastytoast.TastyToast;
import com.tzutalin.dlib.FaceDet;
import com.tzutalin.dlib.VisionDetRet;
import org.parceler.Parcels;
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


public class InFoActivity2 extends Activity implements SurfaceHolder.Callback {
    private EditText name,shenfengzheng,xingbie,mingzu,chusheng,dianhua,fazhengjiguan,
            youxiaoqixian,zhuzhi,fanghao,chepaihao,shibiejieguo,xiangsifdu;
    private ImageView zhengjianzhao,xianchengzhao;
    private Button button;
    private File mSavePhotoFile;
    private JiaZaiDialog jiaZaiDialog=null;
    private String xiangsi="";
    private String biduijieguo="";
    private TiJIaoDialog tiJIaoDialog=null;
   public static final int TIMEOUT = 1000 * 60;
    private static boolean isTrue=true;
    private static boolean isTrue2=true;
    private boolean bidui=false;
    private UserInfoBena userInfoBena=null;
    private SensorInfoReceiver sensorInfoReceiver;
   // private String filePath=null;
    private String filePath2=null;
    private File file1=null;
 //   private File file2=null;
    private  String ip=null;
    long c=0;
    private ImageView imageView;
    private LinearLayout jiemian;
    private Thread thread;
    private String shengfenzhengPath=null;
    private  String zhuji=null;
    private BaoCunBeanDao baoCunBeanDao=null;
    private BaoCunBean baoCunBean=null;
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
    private TextView tishi1,tishi2;
    private Animation animation;
    private boolean isTiJiao=false;
    private String pathyy="";
    private IDCardRecognition mIDCardRecognition;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.zhujiemian2);
        pathyy=getIntent().getStringExtra("pathyy");
        surfaceView= (SurfaceView) findViewById(R.id.fff);
        baoCunBeanDao= MyAppLaction.myAppLaction.getDaoSession().getBaoCunBeanDao();
        baoCunBean=baoCunBeanDao.load(123456L);
        if (baoCunBean!=null && baoCunBean.getZhuji()!=null){
            zhuji=baoCunBean.getZhuji();
        }else {
            Toast tastyToast= TastyToast.makeText(InFoActivity2.this,"请先设置主机地址",TastyToast.LENGTH_LONG,TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER,0,0);
            tastyToast.show();
        }

        String fn = "bbbb.jpg";
        FileUtil.isExists(FileUtil.PATH, fn);
        mSavePhotoFile=new File( FileUtil.SDPATH + File.separator + FileUtil.PATH + File.separator + fn);


        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction("guanbi");
        intentFilter1.addAction("guanbi2");
        sensorInfoReceiver = new SensorInfoReceiver();
        registerReceiver(sensorInfoReceiver, intentFilter1);
        ImageView dd= (ImageView) findViewById(R.id.dd22);
        if (!pathyy.equals("")){
            Glide.with(InFoActivity2.this)
                    .load(zhuji+"/upload/logo/"+pathyy)
                    //  .transform(new GlideCircleTransform(ShouYeActivity.this,1, Color.parseColor("#ffffffff")))
                    .thumbnail(0.1f)
                    //  .transform(new GlideRoundTransform(MyApplication.getAppContext(), 6))
                    .into(dd);
        }

        userInfoBena=new UserInfoBena();

        ImageView imageView= (ImageView) findViewById(R.id.dd);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        initView();
        openDevice();



        count=1;
        isTrue3=true;

        jiaZaiDialog=new JiaZaiDialog(InFoActivity2.this);
       // jiaZaiDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        jiaZaiDialog.setProgressBar();
        jiaZaiDialog.show();


        mFaceDet=MyAppLaction.mFaceDet;

        lp = surfaceView.getLayoutParams();
        sh = surfaceView.getHolder();
        sh.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        sh.addCallback(this);

        try {
            OpenCameraAndSetSurfaceviewSize(0);
        }catch (Exception e){
            Toast tastyToast= TastyToast.makeText(InFoActivity2.this,"开启摄像头失败,请重启机器",TastyToast.LENGTH_LONG,TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER,0,0);
            tastyToast.show();
        }


    }

    private void openDevice() {
        mIDCardRecognition = new IDCardRecognition(InFoActivity2.this, mIDCardRecListener);
        mIDCardRecognition.start();

    }

    private IDCardRecognition.IDCardRecListener mIDCardRecListener = new IDCardRecognition.IDCardRecListener() {
        @Override
        public void onResp(IDCardMsg card) {
            if(card == null) {
                return;
            }
            mIDCardRecognition.close();
            Bitmap bitmap = BitmapFactory.decodeByteArray(card.getPortrait(), 0, card.getPortrait().length);
           // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                name.setText(card.getName());
                xingbie.setText(card.getSexStr());
                mingzu.setText(card.getNationStr());
                chusheng.setText(card.getBirthDate().toString());
                zhuzhi.setText(card.getAddress());
                shenfengzheng.setText(card.getIdCardNum());
                fazhengjiguan.setText(card.getSignOffice());
                youxiaoqixian.setText(card.getUsefulEndDate().toString() + " 到 " +card.getUsefulStartDate());
                //  mFinger.setText(card.isSupportFingerprint() ? R.string.exist : R.string.not_exist);//指纹
                if (bitmap != null) {
                    zhengjianzhao.setImageBitmap(bitmap);

                    String fn="aaaa.jpg";
                    FileUtil.isExists(FileUtil.PATH,fn);
                    userInfoBena = new UserInfoBena(card.getName(), card.getSexStr().equals("男") ? 1 + "" : 2 + "", card.getNationStr(),
                            card.getBirthDate().toString(), card.getAddress(), card.getIdCardNum(), card.getSignOffice(), card.getUsefulEndDate().toString(), card.getUsefulStartDate().toString(), null, null, null);

                    saveBitmap2File(bitmap.copy(Bitmap.Config.ARGB_8888,false), FileUtil.SDPATH+ File.separator+FileUtil.PATH+File.separator+fn,100);

                }
                if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
                    jiaZaiDialog.dismiss();
                    jiaZaiDialog=null;
                }
                jiemian.setVisibility(View.GONE);

        }
    };

    private Void OpenCameraAndSetSurfaceviewSize(int cameraId) {
        mCamera = Camera.open(cameraId);
        Camera.Parameters parameters = mCamera.getParameters();
        Camera.Size pre_size = parameters.getPreviewSize();
      //  Camera.Size pic_size = parameters.getPictureSize();
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);

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
                    tishi.setVisibility(View.VISIBLE);
                    tishi.setText("上传图片中。。。");
                }
            });

            link_P1(shengfenzhengPath,filePath2);


        } catch (Exception e) {
            e.printStackTrace();

        } finally {

			if (!bm.isRecycled()) {
				bm.recycle();
			}
            bm = null;
        }
    }

    private void startReadCard() {

        isTrue=true;
        isTrue2=true;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (isTrue) {
                    if (isTrue2){
                        isTrue2=false;
                        try {
//                            Result res = mReader.read();
//                            if (res.error == IDCardReader.RESULT_OK) {
//                                try {
//                                    showPeopleInfo((IDCard) res.data);
//                                }catch (Exception e){
//                                    e.printStackTrace();
//                                }
//
//                            } else if (res.error == IDCardReader.NO_CARD) {
//
//                                isTrue2=true;
//                            } else {
//                                Toast tastyToast= TastyToast.makeText(InFoActivity2.this,"读卡出现错误",TastyToast.LENGTH_LONG,TastyToast.ERROR);
//                                tastyToast.setGravity(Gravity.CENTER,0,0);
//                                tastyToast.show();
//                            }
//
//                            Thread.sleep(800);

                       } catch (Exception e) {
                            isTrue=false;
                            Log.d("SerialReadActivity", e.getMessage());

                        }

                    }


                }
            }

        });
        thread.start();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

//    private void showPeopleInfo(final IDCard card) {
//        isTrue=false;
//        if (!InFoActivity2.this.isFinishing())
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
//                name.setText(card.getName());
//                xingbie.setText(card.getSex().toString());
//                mingzu.setText(card.getNationality().toString());
//                chusheng.setText(df.format(card.getBirthday()));
//                zhuzhi.setText(card.getAddress());
//                shenfengzheng.setText(card.getNumber());
//                fazhengjiguan.setText(card.getAuthority());
//                youxiaoqixian.setText(df.format(card.getValidFrom()) + " 到 " + (card.getValidTo() == null ? getString(R.string.long_term) : df.format(card.getValidTo())));
//                //  mFinger.setText(card.isSupportFingerprint() ? R.string.exist : R.string.not_exist);//指纹
//                if (card.getPhoto() != null) {
//                    zhengjianzhao.setImageBitmap(card.getPhoto());
//
//                    String fn="aaaa.jpg";
//                    FileUtil.isExists(FileUtil.PATH,fn);
//                    userInfoBena = new UserInfoBena(card.getName(), card.getSex().toString().equals("男") ? 1 + "" : 2 + "", card.getNationality().toString(),
//                            df.format(card.getBirthday()), card.getAddress(), card.getNumber(), card.getAuthority(), df.format(card.getValidFrom()), df.format(card.getValidTo()), null, null, null);
//
//                    saveBitmap2File(card.getPhoto().copy(Bitmap.Config.ARGB_8888,false), FileUtil.SDPATH+ File.separator+FileUtil.PATH+File.separator+fn,100);
//
//                }
//                if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
//                    jiaZaiDialog.dismiss();
//                    jiaZaiDialog=null;
//                }
//                jiemian.setVisibility(View.GONE);
//
//            }
//        });
//
//
//    }


    private void initView() {
        tishi1= (TextView) findViewById(R.id.tishi1);
        tishi2= (TextView) findViewById(R.id.tishi2);
        animation = AnimationUtils.loadAnimation(InFoActivity2.this, R.anim.alpha_anim);
        animation.setRepeatCount(-1);
        tishi= (TextView) findViewById(R.id.tishi);
        jiemian= (LinearLayout) findViewById(R.id.jiemian);
        imageView= (ImageView) findViewById(R.id.ffff);
        name= (EditText) findViewById(R.id.name);
        shenfengzheng= (EditText) findViewById(R.id.shenfenzheng);
        xingbie= (EditText) findViewById(R.id.xingbie);
        mingzu= (EditText) findViewById(R.id.mingzu);
        chusheng= (EditText) findViewById(R.id.chusheng);
        dianhua= (EditText) findViewById(R.id.dianhua);
        fazhengjiguan= (EditText) findViewById(R.id.jiguan);
        youxiaoqixian= (EditText) findViewById(R.id.qixian);
        zhuzhi= (EditText) findViewById(R.id.dizhi);
        fanghao= (EditText) findViewById(R.id.fanghao);
        chepaihao= (EditText) findViewById(R.id.chepai);
        xiangsifdu= (EditText) findViewById(R.id.xiangsidu);
        shibiejieguo= (EditText) findViewById(R.id.jieguo);
        zhengjianzhao= (ImageView) findViewById(R.id.zhengjian);
        xianchengzhao= (ImageView) findViewById(R.id.paizhao);
        button= (Button) findViewById(R.id.wancheng);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!userInfoBena.getCertNumber().equals("")){
                    try {
                        if (isTiJiao){
                            if (bidui){
                                link_save();
                            }else {
                                final QueRenDialog dialog=new QueRenDialog(InFoActivity2.this,"比对未通过，你确定要进行下一步?");
                                dialog.setOnPositiveListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        link_save();
                                        dialog.dismiss();

                                    }
                                });
                                dialog.setOnQuXiaoListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                            }

                        }else {
                            Toast tastyToast= TastyToast.makeText(InFoActivity2.this,"照片质量不符合入库标准,请重试！",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                            tastyToast.setGravity(Gravity.CENTER,0,0);
                            tastyToast.show();

                        }


                    }catch (Exception e){
                        Toast tastyToast= TastyToast.makeText(InFoActivity2.this,"数据异常,请返回后重试",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                        tastyToast.setGravity(Gravity.CENTER,0,0);
                        tastyToast.show();
                        Log.d("InFoActivity", e.getMessage());
                    }

                }else {
                    Toast tastyToast= TastyToast.makeText(InFoActivity2.this,"请先读取身份证信息",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                    tastyToast.setGravity(Gravity.CENTER,0,0);
                    tastyToast.show();
                }

            }
        });



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
                   shibiejieguo.setText("比对通过");
                   biduijieguo="比对通过";
                   jiemian.setVisibility(View.VISIBLE);
                    link_zhiliang();
               }else {
                   if (count>3){
                       jiemian.setVisibility(View.VISIBLE);
                       isTrue4=false;
                       kill_camera();
                   }else {
                       isTrue4=true;
                   }
                   shibiejieguo.setText("比对不通过");
                   biduijieguo="比对不通过";

               }
               xiangsi=intent.getStringExtra("xiangsidu");
                xiangsifdu.setText(intent.getStringExtra("xiangsidu")+"");

                Bitmap bitmap= BitmapFactory.decodeFile(FileUtil.SDPATH+ File.separator+FileUtil.PATH+File.separator+"bbbb.jpg");
                xianchengzhao.setImageBitmap(bitmap);

            }
            if (action.equals("guanbi2")){
                finish();
            }
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
//
//    private class GetIDInfoTask extends
//            AsyncTask<Void, Integer, TelpoException> {
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            //开始
//            info = null;
//            zhengjianBitmap = null;
//
//        }
//
//        @Override
//        protected TelpoException doInBackground(Void... arg0) {
//            TelpoException result = null;
//            try {
//                publishProgress(1);
////				info = IdCard.checkIdCard(4000);
//                info = IdCard.checkIdCard(1600);//luyq modify
//                if (info != null) {
//                    images = IdCard.getIdCardImage();
//                    zhengjianBitmap = IdCard.decodeIdCardImage(images);
//                    // luyq add 增加指纹信息
//                    fringerprint = IdCard.getFringerPrint();
//                    fringerprintData = Utils.getFingerInfo(fringerprint, InFoActivity2.this);
//                }
//            } catch (TelpoException e) {
//                Log.d("GetIDInfoTask", "异常" + e.getMessage());
//                result = e;
//            }
//            return result;
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//
//        }
//
//        @Override
//        protected void onPostExecute(TelpoException result) {
//            super.onPostExecute(result);
//
//            if (result == null && !info.getName().equals("timeout")) {
//                isTrue2 = false;
//                isTrue = false;
//
//                if (async != null) {
//                    async.cancel(true);
//                    async = null;
//                }
//                if (jiaZaiDialog != null) {
//                    jiaZaiDialog.dismiss();
//                    jiaZaiDialog = null;
//                }
//
//                //设置信息
//                beepManager.playBeepSoundAndVibrate();
//                name.setText(info.getName());
//                xingbie.setText(info.getSex());
//              //  Log.d("GetIDInfoTask", info.getSex());
//                shenfengzheng.setText(info.getNo());
//                mingzu.setText(info.getNation());
//                String time = info.getBorn().substring(0, 4) + "-" + info.getBorn().substring(4, 6) + "-" + info.getBorn().substring(6, 8);
//                chusheng.setText(time);
//                fazhengjiguan.setText(info.getApartment());
//
//                String time2 = info.getPeriod().substring(0, 4) + "-" + info.getPeriod().substring(4, 6) + "-" + info.getPeriod().substring(6, 8);
//                String time3 = info.getPeriod().substring(9, 13) + "-" + info.getPeriod().substring(13, 15) + "-" + info.getPeriod().substring(15, 17);
//                youxiaoqixian.setText(time2 + " " + time3);
//                zhuzhi.setText(info.getAddress());
//
//                zhengjianzhao.setImageBitmap(zhengjianBitmap);
//                String fn="aaaa.jpg";
//                FileUtil.isExists(FileUtil.PATH,fn);
//
//                saveBitmap2File(zhengjianBitmap.copy(Bitmap.Config.ARGB_8888,false), FileUtil.SDPATH+ File.separator+FileUtil.PATH+File.separator+fn,100);
//
//                userInfoBena = new UserInfoBena(info.getName(), info.getSex().equals("男") ? 1 + "" : 2 + "", info.getNation(), time, info.getAddress(), info.getNo(), info.getApartment(), time2, time3, null, null, null);
//
//
//            } else {
//                isTrue2 = true;
////                Toast tastyToast = TastyToast.makeText(InFoActivity2.this, "读取身份证信息失败", TastyToast.LENGTH_LONG, TastyToast.ERROR);
////                tastyToast.setGravity(Gravity.CENTER, 0, 0);
////                tastyToast.show();
//
//            }
//        }
//
//    }


    /***
     *保存bitmap对象到文件中
     * @param bm
     * @param path
     * @param quality
     * @return
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

            //开始截图
            mCamera.startPreview();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            CountDownTimer timer = new CountDownTimer(4000, 1000) {

                                @Override
                                public void onTick(long millisUntilFinished) {
                                    if (!InFoActivity2.this.isFinishing()){
                                        tishi1.setVisibility(View.VISIBLE);
                                        tishi2.setVisibility(View.VISIBLE);
                                        tishi2.setText((millisUntilFinished / 1000)+"");
                                        if ((millisUntilFinished / 1000)==1){
                                            tishi1.setText("开始自动抓拍!");
                                            tishi2.setAnimation(null);
                                        }
                                    }

                                }

                                @Override
                                public void onFinish() {
                                    isTrue4=true;
                                    if (!InFoActivity2.this.isFinishing()){
                                        tishi2.setVisibility(View.GONE);
                                        tishi1.setVisibility(View.GONE);
                                        tishi2.setAnimation(null);
                                    }


                                }
                            };
                            timer.start();
                            if (!InFoActivity2.this.isFinishing()){
                                tishi2.setAnimation(animation);
                                jiemian.setVisibility(View.GONE);
                            }

                        }
                    });
                }
            }).start();


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


        } catch (IOException e) {
            Log.d("InFoActivity2", e.getMessage()+"相机");

        }
        return null;
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("InFoActivity2", "暂停");

        count=1;

        isTrue4=false;
        isTrue3=false;
        isTrue2=false;
        isTrue=false;

        if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
            jiaZaiDialog.dismiss();
            jiaZaiDialog=null;
        }

    }

    @Override
    protected void onDestroy() {
        mIDCardRecognition.close();
        unregisterReceiver(sensorInfoReceiver);
        super.onDestroy();

    }



//    /**
//     * 启动拍照
//     * @param
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

    private void link_save() {
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
                    .add("cardNumber",userInfoBena.getCertNumber())
                    .add("name",userInfoBena.getPartyName())
                    .add("gender",userInfoBena.getGender())
                    .add("birthday",userInfoBena.getBornDay())
                    .add("address",userInfoBena.getCertAddress())
                    .add("cardPhoto",userInfoBena.getCardPhoto())
                    .add("scanPhoto",userInfoBena.getScanPhoto())
                    .add("organ",userInfoBena.getCertOrg())
                    .add("termStart",userInfoBena.getEffDate())
                    .add("termEnd",userInfoBena.getExpDate())
                    .add("accountId",baoCunBean.getZhangHuID()+"")
                    .add("result",biduijieguo)
                    .add("homeNumber",fanghao.getText().toString().trim())
                    .add("phone",dianhua.getText().toString().trim())
                    .add("carNumber",chepaihao.getText().toString().trim())
                    .add("score",xiangsi)
                     .add("source","1")
                    .build();


        Request.Builder requestBuilder = new Request.Builder()
                // .header("Content-Type", "application/json")
                .post(body)
                .url(zhuji + "/saveCompareResult.do");

        if (tiJIaoDialog==null){
            tiJIaoDialog=new TiJIaoDialog(InFoActivity2.this);
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
                if (tiJIaoDialog!=null){
                    tiJIaoDialog.dismiss();
                    tiJIaoDialog=null;
                }
                Log.d("AllConnects", "请求识别成功"+call.request().toString());
                //获得返回体
                try {

                    ResponseBody body = response.body();
                    String ss=body.string().trim();
                    Log.d("InFoActivity", ss);

                    if (Long.parseLong(ss)>0){

                        ChuanSongBean bean=new ChuanSongBean(name.getText().toString(),1,Long.parseLong(ss),"","","","");
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("chuansong", Parcels.wrap(bean));
                        startActivity(new Intent(InFoActivity2.this,ShiYouActivity.class).putExtras(bundle));


                    }else {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast tastyToast = TastyToast.makeText(InFoActivity2.this, "保存失败,请检查网络", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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

                            Toast tastyToast = TastyToast.makeText(InFoActivity2.this, "保存失败,请检查网络", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            tastyToast.setGravity(Gravity.CENTER, 0, 0);
                            tastyToast.show();

                        }
                    });
                    Log.d("WebsocketPushMsg", e.getMessage());
                }
            }
        });

        }catch (NullPointerException e){
            Log.d("InFoActivity2", e.getMessage());
        }

    }


//
//    private void startThread() {
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (jiaZaiDialog != null && jiaZaiDialog.isShowing()) {
//                    jiaZaiDialog.dismiss();
//                }
//
//                ObjectAnimator animator2 = ObjectAnimator.ofFloat(jiemian, "scaleY", 1f, 0f);
//                animator2.setDuration(600);//时间1s
//                animator2.start();
//                //起始为1，结束时为0
//                ObjectAnimator animator = ObjectAnimator.ofFloat(jiemian, "scaleX", 1f, 0f);
//                animator.setDuration(600);//时间1s
//                animator.addListener(new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        jiemian.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animation) {
//
//                    }
//                });
//                animator.start();
//            }
//        });
//    }



//        Thread thread=  new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                while (isTrue3){
//
//                    if (isTrue4){
//                        isTrue4=false;
//                        try {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    bitmapBig=videoView.getBitmap();
//
//                                    if (bitmapBig!=null){
//
//                                       new Thread(new Runnable() {
//                                           @Override
//                                           public void run() {
//
//                                               Bitmap bmpf = bitmapBig.copy(Bitmap.Config.RGB_565, true);
//
//                                               //返回识别的人脸数
//                                               //	int faceCount = new FaceDetector(bmpf.getWidth(), bmpf.getHeight(), 1).findFaces(bmpf, facess);
//                                               //	FaceDetector faceCount2 = new FaceDetector(bmpf.getWidth(), bmpf.getHeight(), 2);
//
//                                               myFace = new FaceDetector.Face[numberOfFace];       //分配人脸数组空间
//                                               myFaceDetect = new FaceDetector(bmpf.getWidth(), bmpf.getHeight(), numberOfFace);
//                                               numberOfFaceDetected = myFaceDetect.findFaces(bmpf, myFace);    //FaceDetector 构造实例并解析人脸
//
//                                               if (numberOfFaceDetected > 0) {
//
//                                                   FaceDetector.Face face;
//                                                   if (numberOfFaceDetected>count-1){
//                                                       face = myFace[count-1];
//
//                                                   }else {
//                                                       face = myFace[0];
//
//                                                   }
//
//                                                   PointF pointF = new PointF();
//                                                   face.getMidPoint(pointF);
//
//
//                                                 //  myEyesDistance = (int)face.eyesDistance();
//
//                                                   int xx=0;
//                                                   int yy=0;
//                                                   int xx2=0;
//                                                   int yy2=0;
//
//                                                   if ((int)pointF.x-200>=0){
//                                                       xx=(int)pointF.x-200;
//                                                   }else {
//                                                       xx=0;
//                                                   }
//                                                   if ((int)pointF.y-320>=0){
//                                                       yy=(int)pointF.y-320;
//                                                   }else {
//                                                       yy=0;
//                                                   }
//                                                   if (xx+350 >=bitmapBig.getWidth()){
//                                                       xx2=bitmapBig.getWidth()-xx;
//
//                                                   }else {
//                                                       xx2=350;
//                                                   }
//                                                   if (yy+500>=bitmapBig.getHeight()){
//                                                       yy2=bitmapBig.getHeight()-yy;
//
//                                                   }else {
//                                                       yy2=500;
//                                                   }
//
//
//                                                   Bitmap bitmap = Bitmap.createBitmap(bitmapBig,xx,yy,xx2,yy2);
//
//                                                 //  Bitmap bitmap = Bitmap.createBitmap(bitmapBig,0,0,bitmapBig.getWidth(),bitmapBig.getHeight());
//
//                                                   Message message=Message.obtain();
//                                                   message.what=MESSAGE_QR_SUCCESS;
//                                                   message.obj=bitmap;
//                                                   mHandler2.sendMessage(message);
//
//
//                                                   String fn="bbbb.jpg";
//                                                   FileUtil.isExists(FileUtil.PATH,fn);
//                                                   saveBitmap2File2(bitmap, FileUtil.SDPATH+ File.separator+FileUtil.PATH+File.separator+fn,100);
//
//
//                                               }else {
//                                                   isTrue4=true;
//                                               }
//
//                                               bmpf.recycle();
//                                               bmpf = null;
//                                           }
//                                       }).start();
//
//
//                                    }
//                                }
//                            });
//
//                        }catch (IllegalStateException e){
//                            Log.d("InFoActivity2", e.getMessage()+"");
//                        }
//
//
//
//                    }
//
//                }
//
//
//            }
//        });
//
//        thread.start();
//
//    }

//    public  void saveBitmap2File2(Bitmap bm, final String path, int quality) {
//        try {
//            filePath2=path;
//            if (null == bm) {
//                Log.d("InFoActivity", "回收|空");
//                return ;
//            }
//
//            File file = new File(path);
//            if (file.exists()) {
//                file.delete();
//            }
//            BufferedOutputStream bos = new BufferedOutputStream(
//                    new FileOutputStream(file));
//            bm.compress(Bitmap.CompressFormat.JPEG, quality, bos);
//            bos.flush();
//            bos.close();
//
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            });
//
//            link_P1(shengfenzhengPath,filePath2);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        } finally {
//
////			if (!bm.isRecycled()) {
////				bm.recycle();
////			}
//            bm = null;
//        }
//    }


    private void link_P1(String filename1, final String fileName2) {
        jiaZaiDialog=new JiaZaiDialog(InFoActivity2.this);
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
        file1 = new File(filename1);
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
                        Toast tastyToast= TastyToast.makeText(InFoActivity2.this,"上传图片出错，请返回后重试！",TastyToast.LENGTH_LONG,TastyToast.ERROR);
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
                    link_P2(fileName2);


                }catch (Exception e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
                                jiaZaiDialog.dismiss();
                                jiaZaiDialog=null;
                            }
                            Toast tastyToast= TastyToast.makeText(InFoActivity2.this,"上传图片出错，请返回后重试！",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                            tastyToast.setGravity(Gravity.CENTER,0,0);
                            tastyToast.show();
                        }
                    });
                    Log.d("WebsocketPushMsg", e.getMessage());
                }
            }
        });

    }

    private void link_P2( String fileName2) {
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
        //file2 = new File(fileName2);

        RequestBody fileBody2 = RequestBody.create(MediaType.parse("application/octet-stream") , mSavePhotoFile);
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
                        Toast tastyToast= TastyToast.makeText(InFoActivity2.this,"上传图片出错，请返回后重试！",TastyToast.LENGTH_LONG,TastyToast.ERROR);
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
                    // Log.d("AllConnects", "aa   "+response.body().string());

                    JsonObject jsonObject= GsonUtil.parse(body.string()).getAsJsonObject();
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
                            Toast tastyToast= TastyToast.makeText(InFoActivity2.this,"上传图片出错，请返回后重试！",TastyToast.LENGTH_LONG,TastyToast.ERROR);
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
                        Toast tastyToast= TastyToast.makeText(InFoActivity2.this,"网络出错!",TastyToast.LENGTH_LONG,TastyToast.ERROR);
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

    private void link_zhiliang() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (tiJIaoDialog==null && !InFoActivity2.this.isFinishing()){
                    tiJIaoDialog=new TiJIaoDialog(InFoActivity2.this);
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

                                    if (!InFoActivity2.this.isFinishing()) {
                                        Toast tastyToast = TastyToast.makeText(InFoActivity2.this, "照片质量不符合入库要求,请拍正面照!", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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

                                Toast tastyToast = TastyToast.makeText(InFoActivity2.this, "提交失败,请检查网络", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                tastyToast.show();

                            }
                        });
                        Log.d("WebsocketPushMsg", e.getMessage());
                    }
                }
            });
        }else {
            Toast tastyToast = TastyToast.makeText(InFoActivity2.this, "账户ID为空!,请设置帐户ID", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER, 0, 0);
            tastyToast.show();
        }

    }



}
