package com.example.danpingji.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.danpingji.R;
import com.example.danpingji.beans.ChuanSongBean;
import org.parceler.Parcels;

public class ShiYouActivity extends Activity implements View.OnClickListener {
    private TextView l1,l2,l3,l4;
    private SensorInfoReceiver sensorInfoReceiver;
    private ChuanSongBean chuanSongBean=null;
    private int type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shi_you);
        chuanSongBean= Parcels.unwrap(getIntent().getParcelableExtra("chuansong"));
        type=chuanSongBean.getType();
        Log.d("ShiYouActivity", chuanSongBean.toString());

        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction("guanbi2");
        sensorInfoReceiver = new SensorInfoReceiver();
        registerReceiver(sensorInfoReceiver, intentFilter1);

        l1= (TextView) findViewById(R.id.l1);
        l1.setOnClickListener(this);
        l2= (TextView) findViewById(R.id.l2);
        l2.setOnClickListener(this);
        l3= (TextView) findViewById(R.id.l3);
        l3.setOnClickListener(this);
        l4= (TextView) findViewById(R.id.l4);
        l4.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.l1:

                ChuanSongBean bean=new ChuanSongBean(chuanSongBean.getName(),chuanSongBean.getType(),chuanSongBean.getId(),
                        chuanSongBean.getDianhua(),chuanSongBean.getBeifangren(),chuanSongBean.getBeifangshijian(),"业务");
                Bundle bundle = new Bundle();
                bundle.putParcelable("chuansong", Parcels.wrap(bean));
                if (type==1){
                    Intent intent=new Intent(ShiYouActivity.this,DengJiActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                  //  Intent intent=new Intent(ShiYouActivity.this,DaYingActivity.class);
                  //  intent.putExtras(bundle);
                   // startActivity(intent);
                }


                break;
            case R.id.l2:
                ChuanSongBean bean2=new ChuanSongBean(chuanSongBean.getName(),chuanSongBean.getType(),chuanSongBean.getId(),
                        chuanSongBean.getDianhua(),chuanSongBean.getBeifangren(),chuanSongBean.getBeifangshijian(),"合作");
                Bundle bundle2 = new Bundle();
                bundle2.putParcelable("chuansong", Parcels.wrap(bean2));
                if (type==1){
                    Intent intent2=new Intent(ShiYouActivity.this,DengJiActivity.class);
                    intent2.putExtras(bundle2);
                    startActivity(intent2);
                }else{
                   // Intent intent2=new Intent(ShiYouActivity.this,DaYingActivity.class);
                   // intent2.putExtras(bundle2);
                   // startActivity(intent2);
                }
              ;

                break;
            case R.id.l3:
                ChuanSongBean bean3=new ChuanSongBean(chuanSongBean.getName(),chuanSongBean.getType(),chuanSongBean.getId(),
                        chuanSongBean.getDianhua(),chuanSongBean.getBeifangren(),chuanSongBean.getBeifangshijian(),"面试");
                Bundle bundle3 = new Bundle();
                bundle3.putParcelable("chuansong", Parcels.wrap(bean3));
                if (type==1){
                    Intent intent3=new Intent(ShiYouActivity.this,DengJiActivity.class);
                    intent3.putExtras(bundle3);
                    startActivity(intent3);
                }else{
                   // Intent intent3=new Intent(ShiYouActivity.this,DaYingActivity.class);
                  //  intent3.putExtras(bundle3);
                  //  startActivity(intent3);
                }

                break;
            case R.id.l4:

                ChuanSongBean bean4=new ChuanSongBean(chuanSongBean.getName(),chuanSongBean.getType(),chuanSongBean.getId(),
                        chuanSongBean.getDianhua(),chuanSongBean.getBeifangren(),chuanSongBean.getBeifangshijian(),"其它");
                Bundle bundle4 = new Bundle();
                bundle4.putParcelable("chuansong", Parcels.wrap(bean4));
                if (type==1){
                    Intent intent4=new Intent(ShiYouActivity.this,DengJiActivity.class);
                    intent4.putExtras(bundle4);
                    startActivity(intent4);
                }else{
                   // Intent intent4=new Intent(ShiYouActivity.this,DaYingActivity.class);
                   // intent4.putExtras(bundle4);
                   // startActivity(intent4);
                }

                break;

        }

    }

    private class SensorInfoReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (action.equals("guanbi2")) {
              finish();

            }
        }}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(sensorInfoReceiver);
    }
}
