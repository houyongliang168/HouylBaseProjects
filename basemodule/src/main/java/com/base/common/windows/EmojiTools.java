package com.base.common.windows;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;
import com.base.common.log.MyToast;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 41113 on 2018/10/26.
 * 过滤表情字符
 */

public class EmojiTools {


    private static InputFilter mInputFilter = new InputFilter() {
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                MyToast.showShort("不支持输入表情");
                return "";
            }
            return null;
        }
    };

    /**
     * 设置editText过滤器
     *
     * @param editText
     */
    public static void setEditTextEmojiFilter(EditText editText) {
        InputFilter[] oldFilters = editText.getFilters();
        int oldFiltersLength = oldFilters.length;
        InputFilter[] newFilters = new InputFilter[oldFiltersLength + 1];
        if (oldFiltersLength > 0) {
            System.arraycopy(oldFilters, 0, newFilters, 0, oldFiltersLength);
        }
        //添加新的过滤规则
        newFilters[oldFiltersLength] = new InputFilter() {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    MyToast.showShort("不支持输入表情");
                    return "";
                }
                return source;
            }
        };
        editText.setFilters(newFilters);
    }

}
