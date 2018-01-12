package com.example.danpingji.dialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import com.example.danpingji.R;


/**
 * @Function: 自定义对话框
 * @Date: 2013-10-28
 * @Time: 下午12:37:43
 * @author Tom.Cai
 */
public class TiJIaoDialog extends Dialog {


    public TiJIaoDialog(Context context) {
        super(context, R.style.dialog_style);
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.queren_ll3, null);
        //获得当前窗体
        Window window = TiJIaoDialog.this.getWindow();
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



    @Override
    public void setContentView(int layoutResID) {
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
    }

    @Override
    public void setContentView(View view) {
    }

//    /**
//     * 确定键监听器
//     * @param listener
//     */
//    public void setOnPositiveListener(View.OnClickListener listener){
//        positiveButton.setOnClickListener(listener);
//    }
//    /**
//     * 取消键监听器
//     * @param listener
//     */
//    public void setOnQuXiaoListener(View.OnClickListener listener){
//        quxiao.setOnClickListener(listener);
//    }
}
