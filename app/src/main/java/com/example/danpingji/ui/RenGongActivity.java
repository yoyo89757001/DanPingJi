package com.example.danpingji.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.YuvImage;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.danpingji.beans.Photos;
import com.example.danpingji.beans.PopupWindowAdapter;
import com.example.danpingji.beans.PopupWindowAdapter2;
import com.example.danpingji.beans.ShouFangBean;
import com.example.danpingji.dialog.JiaZaiDialog;
import com.example.danpingji.dialog.TiJIaoDialog;
import com.example.danpingji.utils.DateUtils;
import com.example.danpingji.utils.FileUtil;
import com.example.danpingji.utils.GsonUtil;
import com.example.danpingji.view.GlideCircleTransform;
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
import java.util.ArrayList;
import java.util.Collections;
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

public class RenGongActivity extends Activity implements SurfaceHolder.Callback {
    private final int REQUEST_TAKE_PHOTO=33;
    private ImageView paizhao;
    private EditText name,brdianhua,lfrdianhua,beifangren;
    private TextView riqi,renshu,beizhu;
    private Button baocun;
    public static final int TIMEOUT = 1000 * 60;
    private BaoCunBeanDao baoCunBeanDao=null;
    private BaoCunBean baoCunBean=null;
    private String zhuji=null;
    private JiaZaiDialog jiaZaiDialog=null;
    private Photos photos=null;
    private TiJIaoDialog tiJIaoDialog=null;
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
    private String filePath2=null;
    private LinearLayout jiemian;
    private String TAG = "paizhao";
    private TextView tishi1,tishi2;
    private Animation animation;
    private Call call=null;
    private boolean isTiJiao=false;
    private List<String> stringList=new ArrayList<>();
    private PopupWindow popupWindow=null;
    private PopupWindowAdapter adapterss;
    private PopupWindowAdapter2 adapterss2;
    private List<String> stringList2=new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_gong);


        baoCunBeanDao= MyAppLaction.myAppLaction.getDaoSession().getBaoCunBeanDao();
        baoCunBean=baoCunBeanDao.load(123456L);
        if (baoCunBean!=null && baoCunBean.getZhuji()!=null){
            zhuji=baoCunBean.getZhuji();
        }else {
            Toast tastyToast= TastyToast.makeText(RenGongActivity.this,"请先设置主机地址",TastyToast.LENGTH_LONG,TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER,0,0);
            tastyToast.show();
        }
        jiemian= (LinearLayout) findViewById(R.id.jiemian);

        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction("guanbi2");
        sensorInfoReceiver = new SensorInfoReceiver();
        registerReceiver(sensorInfoReceiver, intentFilter1);
        mFaceDet=MyAppLaction.mFaceDet;
        stringList2.add("业务");
        stringList2.add("合作");
        stringList2.add("面试");
        stringList2.add("其它");
        initView();

        count=1;
        isTrue3=true;
        isTrue4=false;
        surfaceView= (SurfaceView) findViewById(R.id.fff);

        lp = surfaceView.getLayoutParams();
        sh = surfaceView.getHolder();
        sh.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        sh.addCallback(this);

        try {
            OpenCameraAndSetSurfaceviewSize(0);
        }catch (Exception e){
            Toast tastyToast= TastyToast.makeText(RenGongActivity.this,"开启摄像头失败,请重启机器",TastyToast.LENGTH_LONG,TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER,0,0);
            tastyToast.show();
        }

        link_bumen();
        link_laifangren();
    }


    private void kill_camera() {

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



    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            Camera.Parameters parameters = mCamera.getParameters();
        parameters.setJpegQuality(100); // 设置照片质量
        if (parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);// 连续对焦模式
        }
        mCamera.cancelAutoFocus();//自动对焦。
        mCamera.setDisplayOrientation(0);// 设置PreviewDisplay的方向，效果就是将捕获的画面旋转多少度显示
        mCamera.setParameters(parameters);
        mCamera.setPreviewDisplay(sh);
           } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.startPreview();


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {


//            // 获取摄像头支持的PictureSize列表
//            List<Camera.Size> pictureSizeList = parameters.getSupportedPictureSizes();
//            for (Camera.Size size : pictureSizeList) {
//                Log.i(TAG, "pictureSizeList size.width=" + size.width + "  size.height=" + size.height);
//            }
//            /**从列表中选取合适的分辨率*/
//            Camera.Size picSize = getProperSize(pictureSizeList, ((float) height / width));
//            if (null == picSize) {
//                //  Log.i(TAG, "null == picSize");
//                picSize = parameters.getPictureSize();
//            }
//            //   Log.i(TAG, "picSize.width=" + picSize.width + "  picSize.height=" + picSize.height);
//            // 根据选出的PictureSize重新设置SurfaceView大小
//            float w = picSize.width;
//            float h = picSize.height;
//            parameters.setPictureSize(picSize.width, picSize.height);
//            surfaceView.setLayoutParams(new RelativeLayout.LayoutParams((int) (height * (h / w)), height));
//
//            // 获取摄像头支持的PreviewSize列表
//            List<Camera.Size> previewSizeList = parameters.getSupportedPreviewSizes();
//
//            for (Camera.Size size : previewSizeList) {
//                Log.i(TAG, "previewSizeList size.width=" + size.width + "  size.height=" + size.height);
//            }
//            Camera.Size preSize = getProperSize(previewSizeList, ((float) height) / width);
//            if (null != preSize) {
//                // Log.i(TAG, "preSize.width=" + preSize.width + "  preSize.height=" + preSize.height);
//                parameters.setPreviewSize(preSize.width, preSize.height);
//            }


    }

    /**
     * 从列表中选取合适的分辨率
     * 默认w:h = 4:3
     * <p>注意：这里的w对应屏幕的height
     * h对应屏幕的width<p/>
     */
    private Camera.Size getProperSize(List<Camera.Size> pictureSizeList, float screenRatio) {
        Log.i(TAG, "screenRatio=" + screenRatio);
        Camera.Size result = null;
        for (Camera.Size size : pictureSizeList) {
            float currentRatio = ((float) size.width) / size.height;
            if (currentRatio - screenRatio == 0) {
                result = size;
                break;
            }
        }

        if (null == result) {
            for (Camera.Size size : pictureSizeList) {
                float curRatio = ((float) size.width) / size.height;
                if (curRatio == 4f / 3) {// 默认w:h = 4:3
                    result = size;
                    break;
                }
            }
        }

        return result;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

     //   Log.d("InFoActivity3", "surfaceView销毁");
        sh.removeCallback(this);

        if (mCamera != null) {
            mCamera.setPreviewCallback(null) ;
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }

    }



    private class SensorInfoReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (action.equals("guanbi2")){
                finish();
            }
        }
    }


    private Void OpenCameraAndSetSurfaceviewSize(int cameraId) {
        mCamera = Camera.open(cameraId);
        Camera.Parameters parameters = mCamera.getParameters();
        Camera.Size pre_size = parameters.getPreviewSize();
      //  Camera.Size pic_size = parameters.getPictureSize();
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);


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
                                        paizhao.setImageBitmap(bitmap);
                                    }
                                });
                                String fn = "bbbb.jpg";
                                FileUtil.isExists(FileUtil.PATH, fn);
                                saveBitmap2File2(bitmap, FileUtil.SDPATH + File.separator + FileUtil.PATH + File.separator + fn, 100);

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

    private void initView() {
        tishi1= (TextView) findViewById(R.id.tishi1);
        tishi2= (TextView) findViewById(R.id.tishi2);
        paizhao= (ImageView) findViewById(R.id.paizhao);
        paizhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tishi1.setVisibility(View.VISIBLE);
                tishi2.setVisibility(View.VISIBLE);

                tishi2.setAnimation(animation);
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
                jiemian.setVisibility(View.GONE);

            }
        });
        baocun= (Button) findViewById(R.id.wancheng);
        baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存
                if (photos==null){
                    Toast tastyToast= TastyToast.makeText(RenGongActivity.this,"请点击顶部圆形图标拍照",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                    tastyToast.setGravity(Gravity.CENTER,0,0);
                    tastyToast.show();
                }else if (name.getText().toString().trim().equals("")){
                    Toast tastyToast= TastyToast.makeText(RenGongActivity.this,"请填写姓名",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                    tastyToast.setGravity(Gravity.CENTER,0,0);
                    tastyToast.show();

                }else {
                    if (isTiJiao){
                        if (lfrdianhua.getText().toString().trim().equals("") && brdianhua.getText().toString().trim().equals("")){
                            link_save();
                        }else {
                            if (!lfrdianhua.getText().toString().trim().equals("") && !brdianhua.getText().toString().trim().equals("")){
                                if (isMobile(lfrdianhua.getText().toString().trim()) && isMobile(brdianhua.getText().toString().trim())){
                                    link_save();
                                }else {
                                    Toast tastyToast= TastyToast.makeText(RenGongActivity.this,"手机号码格式不正确",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                                    tastyToast.setGravity(Gravity.CENTER,0,0);
                                    tastyToast.show();
                                }
                            }else if (lfrdianhua.getText().toString().trim().equals("") && !brdianhua.getText().toString().trim().equals("")){
                                if (isMobile(brdianhua.getText().toString().trim())){
                                    link_save();
                                }else {
                                    Toast tastyToast= TastyToast.makeText(RenGongActivity.this,"手机号码格式不正确",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                                    tastyToast.setGravity(Gravity.CENTER,0,0);
                                    tastyToast.show();
                                }
                            }else if (!lfrdianhua.getText().toString().trim().equals("") && brdianhua.getText().toString().trim().equals("")){
                                if (isMobile(lfrdianhua.getText().toString().trim())){
                                    link_save();
                                }else {
                                    Toast tastyToast= TastyToast.makeText(RenGongActivity.this,"手机号码格式不正确",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                                    tastyToast.setGravity(Gravity.CENTER,0,0);
                                    tastyToast.show();
                                }
                            }
                        }
                    }else {
                        Toast tastyToast= TastyToast.makeText(RenGongActivity.this,"照片质量不符合入库标准,请重新拍摄!",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                        tastyToast.setGravity(Gravity.CENTER,0,0);
                        tastyToast.show();
                    }


                }

            }
        });
        riqi= (TextView) findViewById(R.id.qixian);
        riqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RenGongActivity.this, DatePickActivity.class);
                startActivityForResult(intent,2);
            }
        });
        riqi.setText(DateUtils.timet2(System.currentTimeMillis()+""));
        name= (EditText) findViewById(R.id.name);
        brdianhua= (EditText) findViewById(R.id.dianhua);
        lfrdianhua= (EditText) findViewById(R.id.chepai);
        beifangren= (EditText) findViewById(R.id.dizhi);
        renshu= (TextView) findViewById(R.id.renshu);
        renshu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stringList.size()>0) {
                    View contentView = LayoutInflater.from(RenGongActivity.this).inflate(R.layout.xiangmu_po_item, null);

                    ListView listView = (ListView) contentView.findViewById(R.id.dddddd);
                    adapterss = new PopupWindowAdapter(RenGongActivity.this, stringList);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            renshu.setText(stringList.get(position));
                            popupWindow.dismiss();
                        }
                    });
                    listView.setAdapter(adapterss);
                    popupWindow = new PopupWindow(contentView, 200, setListViewHeightBasedOnChildren(listView));
                    popupWindow.setFocusable(true);//获取焦点
                    popupWindow.setOutsideTouchable(true);//获取外部触摸事件
                    popupWindow.setTouchable(true);//能够响应触摸事件
                    popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//设置背景
                    popupWindow.showAsDropDown(renshu, renshu.getLeft() - 100, 0);
                }else {
                    Toast tastyToast= TastyToast.makeText(RenGongActivity.this,"没有部门数据",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                    tastyToast.setGravity(Gravity.CENTER,0,0);
                    tastyToast.show();
                }
            }
        });
        beizhu= (TextView) findViewById(R.id.mudi);
        beizhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View contentView = LayoutInflater.from(RenGongActivity.this).inflate(R.layout.xiangmu_po_item, null);
                ListView listView= (ListView) contentView.findViewById(R.id.dddddd);
                adapterss2=new PopupWindowAdapter2(RenGongActivity.this,stringList2);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        beizhu.setText(stringList2.get(position));
                        popupWindow.dismiss();
                    }
                });
                listView.setAdapter(adapterss2);
                popupWindow=new PopupWindow(contentView,200, setListViewHeightBasedOnChildren(listView));
                popupWindow.setFocusable(true);//获取焦点
                popupWindow.setOutsideTouchable(true);//获取外部触摸事件
                popupWindow.setTouchable(true);//能够响应触摸事件
                popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//设置背景
                popupWindow.showAsDropDown(beizhu,beizhu.getLeft()-100,0);

            }
        });
        animation = AnimationUtils.loadAnimation(RenGongActivity.this, R.anim.alpha_anim);
        animation.setRepeatCount(-1);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            switch (requestCode) {
//                case REQUEST_TAKE_PHOTO:  //拍照
//                    //注意，如果拍照的时候设置了MediaStore.EXTRA_OUTPUT，data.getData=null
//                   // paizhao.setImageURI(Uri.fromFile(mSavePhotoFile));
//
//                    link_P2();
//
//                    break;
//            }
//        }

        if (resultCode == Activity.RESULT_OK && requestCode == 2) {
            // 选择预约时间的页面被关闭
            String date = data.getStringExtra("date");
            riqi.setText(date);
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
            link_P2();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {

//			if (!bm.isRecycled()) {
//				bm.recycle();
//			}
            bm = null;
        }
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

    private void link_P2() {

        if (jiaZaiDialog==null){
            jiaZaiDialog=new JiaZaiDialog(RenGongActivity.this);
            jiaZaiDialog.setText("上传图片中...");
            jiaZaiDialog.show();
        }
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
         call = okHttpClient.newCall(requestBuilder.build());

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
                        Toast tastyToast= TastyToast.makeText(RenGongActivity.this,"上传图片出错，请重试！",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                        tastyToast.setGravity(Gravity.CENTER,0,0);
                        tastyToast.show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("AllConnects", "请求识别成功"+call.request().toString());
                //删掉文件
                if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
                    jiaZaiDialog.dismiss();
                    jiaZaiDialog=null;
                }
                //获得返回体
                try {

                    ResponseBody body = response.body();
                    String ss=body.string();
                     Log.d("AllConnects", "aa  "+ss);

                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson=new Gson();
                    photos= gson.fromJson(jsonObject,Photos.class);
                    isTrue4=false;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            link_zhiliang();
                            jiemian.setVisibility(View.VISIBLE);
                            Glide.with(RenGongActivity.this)
                                    .load(filePath2)
                                    .skipMemoryCache(true)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .transform(new GlideCircleTransform(RenGongActivity.this,1, Color.parseColor("#ffffffff")))
                                    .into(paizhao);
                        }
                    });

                }catch (Exception e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (jiaZaiDialog!=null && jiaZaiDialog.isShowing()){
                                jiaZaiDialog.dismiss();
                                jiaZaiDialog=null;
                            }
                            Toast tastyToast= TastyToast.makeText(RenGongActivity.this,"上传图片出错，请重试！",TastyToast.LENGTH_LONG,TastyToast.ERROR);
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
        if (call!=null){
            call.cancel();
            call=null;
        }
        super.onDestroy();
        unregisterReceiver(sensorInfoReceiver);

    }

    private void link_save() {
        if (tiJIaoDialog==null){
            tiJIaoDialog=new TiJIaoDialog(RenGongActivity.this);
            tiJIaoDialog.show();
        }

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
                .add("name",name.getText().toString().trim()+"")
                .add("phone",brdianhua.getText().toString().trim()+"")
                .add("visitDate2",riqi.getText().toString().trim()+"")
                .add("visitPerson",beifangren.getText().toString().trim()+"")
                .add("visitDepartment",renshu.getText().toString().trim()+"")
                .add("visitNum","1")
                .add("accountId",baoCunBean.getZhangHuID())
                .add("homeNumber",lfrdianhua.getText().toString().trim())
                .add("scanPhoto",photos.getExDesc())
                .add("visitIncident",beizhu.getText().toString().trim()+"")
                .add("cardNumber","rt"+System.currentTimeMillis())
                .add("source","1")
                .build();


        Request.Builder requestBuilder = new Request.Builder()
                // .header("Content-Type", "application/json")
                .post(body)
                .url(zhuji + "/saveCompareVisit.do");

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
                    Log.d("DengJiActivity", ss);

                    JsonObject jsonObject= GsonUtil.parse(ss).getAsJsonObject();
                    Gson gson=new Gson();
                    ShouFangBean zhaoPianBean=gson.fromJson(jsonObject,ShouFangBean.class);

                    if (zhaoPianBean.getDtoResult()==0){
                        //   Log.d("DengJiActivity", "dddd");

//                        ChuanSongBean bean=new ChuanSongBean(name.getText().toString(),3,zhaoPianBean.getSid(),lfrdianhua.getText().toString().trim()
//                                ,beifangren.getText().toString().trim(),riqi.getText().toString().trim(),"");
//                        Bundle bundle = new Bundle();
//                        bundle.putParcelable("chuansong", Parcels.wrap(bean));
//                        startActivity(new Intent(RenGongActivity.this,ShiYouActivity.class).putExtras(bundle));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast tastyToast= TastyToast.makeText(RenGongActivity.this,"提交成功",TastyToast.LENGTH_LONG,TastyToast.INFO);
                                tastyToast.setGravity(Gravity.CENTER,0,0);
                                tastyToast.show();
                                finish();
                            }
                        });

//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                final YuYueDialog dialog=new YuYueDialog(RenGongActivity.this,"你已成功预约,感谢你的来访!");
//                                dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
//                                dialog.setOnPositiveListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        dialog.dismiss();
//                                        finish();
//                                    }
//                                });
//                                dialog.show();
//                            }
//                        });


                    }else {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast tastyToast= TastyToast.makeText(RenGongActivity.this,"提交失败",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                                tastyToast.setGravity(Gravity.CENTER,0,0);
                                tastyToast.show();

                            }
                        });


                    }

                }catch (Exception e){

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (tiJIaoDialog!=null){
                                tiJIaoDialog.dismiss();
                                tiJIaoDialog=null;
                            }
                        }
                    });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast tastyToast= TastyToast.makeText(RenGongActivity.this,"提交失败,请检查网络",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                            tastyToast.setGravity(Gravity.CENTER,0,0);
                            tastyToast.show();

                        }
                    });
                    Log.d("WebsocketPushMsg", e.getMessage());
                }
            }
        });
    }

    private void link_zhiliang() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (tiJIaoDialog==null && !RenGongActivity.this.isFinishing()){
                    tiJIaoDialog=new TiJIaoDialog(RenGongActivity.this);
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
                    .add("scanPhoto", photos.getExDesc())
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

                                    if (!RenGongActivity.this.isFinishing()) {
                                        Toast tastyToast = TastyToast.makeText(RenGongActivity.this, "照片质量不符合入库要求,请拍正面照!", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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

                                Toast tastyToast = TastyToast.makeText(RenGongActivity.this, "提交失败,请检查网络", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                                tastyToast.show();

                            }
                        });
                        Log.d("WebsocketPushMsg", e.getMessage());
                    }
                }
            });
        }else {
            Toast tastyToast = TastyToast.makeText(RenGongActivity.this, "账户ID为空!,请设置帐户ID", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER, 0, 0);
            tastyToast.show();
        }
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
                                if (tiJIaoDialog != null && !RenGongActivity.this.isFinishing()) {
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
            Toast tastyToast = TastyToast.makeText(RenGongActivity.this, "账户ID为空!,请设置帐户ID", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER, 0, 0);
            tastyToast.show();
        }
    }

    private void link_laifangren() {

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
                                if (tiJIaoDialog != null && !RenGongActivity.this.isFinishing()) {
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
            Toast tastyToast = TastyToast.makeText(RenGongActivity.this, "账户ID为空!,请设置帐户ID", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER, 0, 0);
            tastyToast.show();
        }
    }

}
