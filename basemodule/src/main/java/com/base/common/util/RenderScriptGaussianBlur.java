package com.base.common.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;


/**
 * Created by yanggang12 on 2017/6/10.
 */

public class RenderScriptGaussianBlur {

    private RenderScript renderScript;

    public RenderScriptGaussianBlur(@NonNull Context context) {
        this.renderScript = RenderScript.create(context);
    }

    public Bitmap gaussianBlur(int radius, Bitmap original) {
        Allocation input = Allocation.createFromBitmap(renderScript, original);
        Allocation output = Allocation.createTyped(renderScript, input.getType());
        ScriptIntrinsicBlur scriptIntrinsicBlur = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
            scriptIntrinsicBlur.setRadius(radius);
            scriptIntrinsicBlur.setInput(input);
            scriptIntrinsicBlur.forEach(output);
            output.copyTo(original);
            return original;
        }
      return null;
    }

}
