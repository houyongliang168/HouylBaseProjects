package com.base.hyl.houylbaseprojects.textview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.base.common.base.BaseActivity;
import com.base.hyl.houylbaseprojects.R;
import com.base.hyl.houylbaseprojects.web.WebActivity;

import static com.base.hyl.houylbaseprojects.Constant.WEB_URL;

public class TextViewActivity extends BaseActivity {

    private TextView mTextView;

    private String wb_simple="<font color='#FF0000'>新年伊始，关爱活动</font>：<a href='http://www.baidu.com'>泰康自成立泰康拜博口腔以来</a>，特对泰康客户推出齿科检查关爱活动，价格聚划算，<font color='#FF0000'>2019服务客户，从齿科关爱开始</font>！";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);
        mTextView = generateFindViewById(R.id.tv_simple);
        mTextView.setText("akdfjkasdjf  \n +adfasdfas");
//        initView();
        /*设置点击事件*/
        generateFindViewById(R.id.tv_permission) .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private SpannableString dealWithText(String result) {
        if (result == null) {
            return new SpannableString("");
        }
        result = result.trim();
        if (TextUtils.isEmpty(result)) {
            return new SpannableString("");
        }
        if (result.contains("<a") && result.contains("</a>")) {
            //带<a>标签的文本
            int startTagLeftPosition = result.indexOf("<a href=\"");
            int startTagRightPosition = result.indexOf("\">");
            int endTagLeftPosition = result.indexOf("</a>");
            if (startTagLeftPosition == -1) {
                return new SpannableString(result);
            }
            if (startTagRightPosition == -1) {
                return new SpannableString(result);
            }
            if (endTagLeftPosition == -1) {
                return new SpannableString(result);
            }
            int endTagRightPosition = endTagLeftPosition + 3;

            if (endTagRightPosition >= result.length()) {
                return new SpannableString(result);
            }
            int hrefLeftPosition = startTagLeftPosition + 9;
            int hrefRightPosition = startTagRightPosition;
            if (hrefLeftPosition >= result.length()) {
                return new SpannableString(result);
            }
            if (hrefRightPosition == -1) {
                return new SpannableString(result);
            }
            //使用stringBuilder进行替换操作
            StringBuilder stringBuilder = new StringBuilder(result);
            //获取href连接内容
            String hrefContent = result.substring(hrefLeftPosition, hrefRightPosition);
            //获取替换之后的结果
            String replaceResult = stringBuilder.replace(startTagLeftPosition, endTagRightPosition + 1, hrefContent).toString();


            SpannableString spannableString = new SpannableString(replaceResult);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#3285ff"));
            //链接蓝色字体
            spannableString.setSpan(colorSpan, startTagLeftPosition, startTagLeftPosition + hrefContent.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            UnderlineSpan underlineSpan = new UnderlineSpan();
//            //链接下划线
//            spannableString.setSpan(underlineSpan, startTagLeftPosition, startTagLeftPosition + hrefContent.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            spannableString.setSpan(new MyClickableSpan(hrefContent, this), startTagLeftPosition, startTagLeftPosition + hrefContent.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            return spannableString;

        } else {
            //普通文本
            return new SpannableString(result);
        }


//        SpannableString spannableString = new SpannableString("为文字设置点击事件");


    };

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // flags
            // FROM_HTML_MODE_COMPACT：html块元素之间使用一个换行符分隔
            // FROM_HTML_MODE_LEGACY：html块元素之间使用两个换行符分隔
            mTextView.setText(Html.fromHtml(wb_simple, Html.FROM_HTML_MODE_COMPACT));
        } else {
            mTextView.setText(Html.fromHtml(wb_simple));
        }


//        NoUnderLineSpan1 noUnderLineSpan1 = new NoUnderLineSpan1();
//        if (mTextView.getText() instanceof Spannable) {
//            Spannable s = (Spannable) mTextView.getText();
//            s.setSpan(noUnderLineSpan1, 0, s.length(), Spanned.SPAN_MARK_MARK);
//        }

//        SpannableString text = new SpannableString(s1);
//        NoUnderLineSpan noUnderLineSpan = new NoUnderLineSpan("https://www.baidu.com");
//        text.setSpan(noUnderLineSpan, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        mTextView.setText(text);

        SpannableString spannableText = dealWithText(wb_simple);
        if (spannableText != null && spannableText.length() > 0) {
            mTextView.setText(spannableText);



            mTextView.setMovementMethod(LinkMovementMethod.getInstance());


        }
    }
      class NoUnderLineSpan extends URLSpan {
        private Context mContext;
        private String url;
        public NoUnderLineSpan(Context context, String src) {
            super(src);
            mContext = context;
            url = src;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
            ds.setColor(Color.parseColor("#00B2EE"));
        }

        @Override
        public void onClick(View widget) {
            Intent intent = new Intent(mContext, WebActivity.class);
            intent.putExtra(WEB_URL, url);
            mContext.startActivity(intent);
        }
    }


      class NoUnderLineSpan1 extends UnderlineSpan {
        public NoUnderLineSpan1() {
        }

        public NoUnderLineSpan1(Parcel src) {
            super(src);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }


}
