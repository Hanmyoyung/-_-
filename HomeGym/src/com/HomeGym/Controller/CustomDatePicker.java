package com.HomeGym.Controller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

public class CustomDatePicker  extends DatePicker{
	public CustomDatePicker(Context context) {

        super(context);

        init();

   }

  

   public CustomDatePicker(Context context, AttributeSet attrs) {

        super(context, attrs);

        init();

   }

  

   public CustomDatePicker(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

        init();

   }




   private void init() {

       

        // 현재 폰트 색을 제너럴세팅의 색으로 변경

        LinearLayout linearLayout = (LinearLayout)getChildAt(0);

        if (linearLayout != null) {

             linearLayout = (LinearLayout) linearLayout.getChildAt(0);

             if (linearLayout != null) {

                  for (int i = 0; i < linearLayout.getChildCount(); i++) {

                       NumberPicker numberPicker = (NumberPicker)linearLayout.getChildAt(i);

                       if (numberPicker != null) {

                            EditText editText = (EditText)numberPicker.getChildAt(1);

                            //editText.setTextColor(GeneralSetting.getMainFontColor());

                       }    

                  }

             }

        }

   }

  

   @Override

   public boolean onInterceptTouchEvent(MotionEvent ev)

   {

       if (ev.getActionMasked() == MotionEvent.ACTION_DOWN)

       {

           ViewParent p = getParent();

           if (p != null)

               p.requestDisallowInterceptTouchEvent(true);

       }




       return false;

   }
    

}
