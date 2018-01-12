package com.example.danpingji.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.danpingji.R;

public class Utils {
    public static String getIp(Context context) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sp.getString("ip", Constant.ip);
    }

    public static int getPort(Context context) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sp.getInt("port", Constant.port);
    }

    public static String getAccount(Context context) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sp.getString("acc", Constant.acc);
    }

    public static String getPassword(Context context) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sp.getString("pwd", Constant.pwd);
    }

    public static boolean getIsWss(Context context) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sp.getBoolean("wss", true);
    }

    public static void setIsWss(Context context, boolean flag) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("wss", flag);
        editor.commit();
    }


    public static String getFingerInfo(byte[] fpData,Context context) {
        // 解释第1枚指纹，总长度512字节，部分数据格式：
        // 第1字节为特征标识'C'
        // 第5字节为注册结果代码，0x01-注册成功，0x02--注册失败, 0x03--未注册, 0x09--未知
        // 第6字节为指位代码
        // 第7字节为指纹质量值，0x00表示未知，1~100表示质量值
        // 第512字节 crc8值
        String fingerInfo = "";
        if (fpData != null && fpData.length == 1024 && fpData[0] == 'C') {
            fingerInfo = fingerInfo + GetFingerName(fpData[5],context);

            if (fpData[4] == 0x01) {
                fingerInfo = fingerInfo + " "+context.getString(R.string.idcard_zwzl) + String.valueOf(fpData[6]);
            } else {
                fingerInfo = fingerInfo + GetFingerStatus(fpData[4],context);
            }

            fingerInfo = fingerInfo + "  ";
            if (fpData[512] == 'C') {
                fingerInfo = fingerInfo + GetFingerName(fpData[512 + 5],context);

                if (fpData[512 + 4] == 0x01) {
                    fingerInfo = fingerInfo + " "+ context.getString(R.string.idcard_zwzl)
                            + String.valueOf(fpData[512 + 6]);
                } else {
                    fingerInfo = fingerInfo + GetFingerStatus(fpData[512 + 4],context);
                }
            }
        } else {
            fingerInfo = context.getString(R.string.idcard_wdqhbhzw);
        }

        return fingerInfo;
    }
    public static String GetFingerName(int fingerPos,Context context) {
        String fingerName = "";
        switch (fingerPos) {
            case 11:
//			右手拇指
                fingerName = context.getString(R.string.idcard_ysmz);
                break;
            case 12:
//			右手食指
                fingerName = context.getString(R.string.idcard_yssz);
                break;
            case 13:
//			右手中指
                fingerName = context.getString(R.string.idcard_yszz);
                break;
            case 14:
//			右手环指
                fingerName = context.getString(R.string.idcard_yshz);
                break;
            case 15:
//			右手小指
                fingerName = context.getString(R.string.idcard_ysxz);
                break;
            case 16:
//			左手拇指
                fingerName = context.getString(R.string.idcard_zsmz);
                break;
            case 17:
//			左手食指
                fingerName = context.getString(R.string.idcard_zssz);
                break;
            case 18:
//			左手中指
                fingerName = context.getString(R.string.idcard_zszz);
                break;
            case 19:
//			左手环指
                fingerName = context.getString(R.string.idcard_zshz);
                break;
            case 20:
//			左手小指
                fingerName = context.getString(R.string.idcard_zsxz);
                break;
            case 97:
//			右手不确定指位
                fingerName = context.getString(R.string.idcard_ysbqdzw);
                break;
            case 98:
//			左手不确定指位
                fingerName = context.getString(R.string.idcard_zsbqdzw);
                break;
            case 99:
//			其他不确定指位
                fingerName = context.getString(R.string.idcard_qtbqdzw);
                break;
            default:
//			指位未知
                fingerName = context.getString(R.string.idcard_zwwz);
                break;
        }
        return fingerName;
    }

    // 第5字节为注册结果代码，0x01-注册成功，0x02--注册失败, 0x03--未注册, 0x09--未知
    public static String GetFingerStatus(int fingerStatus,Context context) {
        String fingerStatusName = "";
        switch (fingerStatus) {
            case 0x01:
//			注册成功
                fingerStatusName = context.getString(R.string.idcard_zccg);
                break;
            case 0x02:
//			注册失败
                fingerStatusName =context.getString(R.string.idcard_zcsb);
                break;
            case 0x03:
//			未注册
                fingerStatusName = context.getString(R.string.idcard_wzc);
                break;
            case 0x09:
//			注册状态未知
                fingerStatusName = context.getString(R.string.idcard_zcztwz);
                break;
            default:
//			注册状态未知
                fingerStatusName = context.getString(R.string.idcard_zcztwz);
                break;
        }
        return fingerStatusName;
    }
}
