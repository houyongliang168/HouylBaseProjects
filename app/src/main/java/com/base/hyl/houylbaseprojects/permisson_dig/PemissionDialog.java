package com.base.hyl.houylbaseprojects.permisson_dig;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.View;

import com.base.hyl.houylbaseprojects.R;
import com.base.widget.fragment_dialog.TDialog;

public class PemissionDialog extends TDialog {


    public void showDialog(FragmentManager fragmentManager, final Activity context) {

        new Builder(fragmentManager)
                .setLayoutRes(R.layout.basemodule_dialog_sure_and_cancle_type_two)
                .setScreenWidthAspect(context, 0.85f)
                .setGravity(Gravity.CENTER)
                .addOnClickListener(R.id.tv_quxiao, R.id.tv_queren)
                .setDialogAnimationRes(R.style.main_menu_animstyle)
                .setOnViewClickListener(new com.base.widget.fragment_dialog.listener.OnViewClickListener() {
                    @Override
                    public void onViewClick(com.base.widget.fragment_dialog.base.BindViewHolder viewHolder, View view, TDialog tDialog) {
                        switch (view.getId()) {
                            case R.id.tv_quxiao:


                                break;
                            case R.id.tv_queren:


                                break;
                            default:
                                break;
                        }
                    }
                })
                .create()
                .show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
