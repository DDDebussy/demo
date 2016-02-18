package com.channelsoft.sample.util;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.channelsoft.sample.R;

import java.util.ArrayList;

/**
 * Created by 王宗贤 on 2015/12/18.
 */
public class Mpopupwindow {
    public static PopupWindow mPopupWindow;
    /**
     * 自定义popupwindow
     * @param activity 传入上下文
     * @param view 传入popupwindow中的布局文件
     * @param viewTouch 传入触发popupwindow的view
     * @param viewHint 传入背景模板，如果null则通过透明度设置popupwindow外的透明背景
     * @param percent 1:全屏；2：屏幕一半；3：屏幕三分之一；4：屏幕四分之一
     */
    public static void startPopupWindow(Activity activity,int percent,View view,View viewTouch,View viewHint){
        mPopupWindow=new PopupWindow(activity);
//        设置宽高
        WindowManager wm = (WindowManager) activity
                .getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();

        mPopupWindow.setHeight(height / percent);
        mPopupWindow.setWidth(ActionBar.LayoutParams.MATCH_PARENT);
        mPopupWindow.setContentView(view);
        mPopupWindow.setBackgroundDrawable(activity.getResources().getDrawable(R.color.white));
        mPopupWindow.setFocusable(true); // 设置PopupWindow可获得焦点
//         点击Window以外的界面返回,必须在show之前设置
        mPopupWindow.setOutsideTouchable(true);
//      设置popWindow的显示和消失动画
        mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);

//        在按键上面显示
        int[] location = new int[2];
        viewTouch.getLocationOnScreen(location);
        mPopupWindow.showAtLocation(viewTouch, Gravity.BOTTOM, 0, 0);

        if(viewHint==null) {
            //设置背景为灰色
            backgroundAlpha(0.5f, activity);
        }else{
            viewHint.setVisibility(View.VISIBLE);
        }
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener(activity,viewHint));

    }

    /*
   * 设置添加屏幕的背景透明度
   */
    public static void backgroundAlpha(float bgAlpha, Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }
    /**
     * 监听popupwindow关闭
     */
    public static class poponDismissListener implements PopupWindow.OnDismissListener {
        private Activity mActivity;
        private View view;

        public poponDismissListener(Activity mActivity, View view) {
            this.mActivity = mActivity;
            this.view = view;
        }

        @Override
        public void onDismiss() {
            if(view==null){
                backgroundAlpha(1f, mActivity);
            }else{
                view.setVisibility(View.GONE);

            }
        }
    }

}