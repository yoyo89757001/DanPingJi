package com.example.danpingji.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.danpingji.R;
import com.example.danpingji.beans.JiuDianBean;


/**
 * @Function: 自定义对话框
 * @Date: 2013-10-28
 * @Time: 下午12:37:43
 * @author Tom.Cai
 */
public class XiuGaiJiuDianDialog extends Dialog {
   // private TextView title2;
    private Button l1,l2;
    private EditText jiudianname,idid;
    public XiuGaiJiuDianDialog(Context context) {
        super(context, R.style.dialog_style2);
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.xiugaidialog3, null);

        jiudianname= (EditText) mView.findViewById(R.id.xiangce);
        idid= (EditText)mView.findViewById(R.id.idid);
       // title2= (TextView) mView.findViewById(R.id.title2);
        l1= (Button)mView. findViewById(R.id.queren);
        l2= (Button) mView.findViewById(R.id.quxiao);
        //获得当前窗体
        Window window = XiuGaiJiuDianDialog.this.getWindow();
        //重新设置
        WindowManager.LayoutParams lp =getWindow().getAttributes();
        window .setGravity(Gravity.CENTER | Gravity.TOP);
        // lp.x = 100; // 新位置X坐标
        lp.y = 100; // 新位置Y坐标
        lp.width = 300; // 宽度
        lp.height = 300; // 高度
        //   lp.alpha = 0.7f; // 透明度
        // dialog.onWindowAttributesChanged(lp);
        //(当Window的Attributes改变时系统会调用此函数)
        window .setAttributes(lp);
        super.setContentView(mView);
    }

    public void setContents(String ss, String s3){
       if (ss!=null)
           idid.setText(ss);
        if (s3!=null)
            jiudianname.setText(s3);
    }

    public JiuDianBean getJiuDianBean(){
        JiuDianBean dianBean=new JiuDianBean();
        dianBean.setId(idid.getText().toString().trim());
        dianBean.setName(jiudianname.getText().toString().trim());
        return dianBean;

    }



    @Override
    public void setContentView(int layoutResID) {
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
    }

    @Override
    public void setContentView(View view) {
    }

    /**
     * 确定键监听器
     * @param listener
     */
    public void setOnQueRenListener(View.OnClickListener listener){
        l1.setOnClickListener(listener);
    }
    /**
     * 取消键监听器
     * @param listener
     */
    public void setQuXiaoListener(View.OnClickListener listener){
        l2.setOnClickListener(listener);
    }


}
