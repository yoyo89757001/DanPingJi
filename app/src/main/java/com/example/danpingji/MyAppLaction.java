package com.example.danpingji;

import android.app.Application;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.anupcowkur.reservoir.Reservoir;
import com.example.danpingji.beans.DaoMaster;
import com.example.danpingji.beans.DaoSession;
import com.example.danpingji.utils.LibVLCUtil;
import com.tencent.bugly.Bugly;
import com.tzutalin.dlib.Constants;
import com.tzutalin.dlib.FaceDet;
import org.videolan.libvlc.LibVLC;
import java.io.IOException;

/**
 * Created by Administrator on 2017/7/5.
 */

public class MyAppLaction extends Application{
    public static FaceDet mFaceDet;
   // public static String sip=null;
    public static LibVLC libvlc;
   // public static JiuDianBean jiuDianBean=null;
  //  public static String zhuji=null;
    public DaoMaster mDaoMaster;
    public DaoSession mDaoSession;
    public static MyAppLaction myAppLaction;



    @Override
    public void onCreate() {
        super.onCreate();

        myAppLaction=this;

        setDatabase();

        try {
            Bugly.init(getApplicationContext(), "fdb87466bd", false);

            mFaceDet = new FaceDet(Constants.getFaceShapeModelPath());

            Reservoir.init(this, 900 * 1024); //in bytes 1M

//            //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
//            QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
//
//                @Override
//                public void onViewInitFinished(boolean arg0) {
//
//                    //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//                    Log.d("app", " onViewInitFinished is " + arg0);
//                }
//
//                @Override
//                public void onCoreInitFinished() {
//                    QbSdk.reset(getApplicationContext());
//                }
//            };
//            //x5内核初始化接口
//            QbSdk.initX5Environment(getApplicationContext(), cb);
        } catch (IOException e) {
            Log.d("gggg", e.getMessage());

        }

        libvlc = LibVLCUtil.getLibVLC(getApplicationContext());

    }



    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this, "notes-posji", null);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();


    }


    public  DaoSession getDaoSession() {
        return mDaoSession;
    }




//        Log.d("MainActivity", "OpenCVLoader.initDebug():" + OpenCVLoader.initDebug());
        // Example of a call to a native method
//        try {
//            // load cascade file from application resources
//            InputStream is = getResources().openRawResource(R.raw.lbpcascade_frontalface);
//            File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
//            mCascadeFile = new File(cascadeDir, "haarcascade_frontalface_alt2.xml");
//            FileOutputStream os = new FileOutputStream(mCascadeFile);
//
//            byte[] buffer = new byte[4096];
//            int bytesRead;
//            while ((bytesRead = is.read(buffer)) != -1) {
//                os.write(buffer, 0, bytesRead);
//            }
//            is.close();
//            os.close();
//
//            mJavaDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
//            if (mJavaDetector.empty()) {
//
//                mJavaDetector = null;
//            }
//            cascadeDir.delete();
//
//
//        } catch (IOException e) {
//            Log.d("InFoActivity2", e.getMessage());
//        }




}
