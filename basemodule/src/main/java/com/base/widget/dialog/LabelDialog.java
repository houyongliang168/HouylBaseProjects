package com.base.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.base.widget.dialog.bean.BaseLableBean;
import com.base.hyl.houbasemodule.R;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyang249 on 2018/7/4.
 */

public class LabelDialog extends Dialog {


    private GridView mGv;

    private List<BaseLableBean> mList = new ArrayList();


    private StyleAdapter mAdapter;

    private int resid;
    private Context mContext;
    private boolean isCanCancle;
    private boolean isListView;
    private ListView mLv;
    private LinearLayout ll_button;
    private ClickListener listener;
    private String title;
    private RelativeLayout rl_root;
    private boolean isOkGetData=false;
    public LabelDialog(Context context, int resid, List<BaseLableBean> list, String title, boolean isCanCancle, boolean isListView) {
        super(context, R.style.BottomDialogStyle);
        initAttributes();
        this.mContext=context;
        this.resid=resid;
        this.isCanCancle=isCanCancle;
        this.isListView=isListView;
        this.title=title;
      for(BaseLableBean bean:list){
          try {
              mList.add((BaseLableBean) bean.clone());
          } catch (CloneNotSupportedException e) {
              e.printStackTrace();
          }
      }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(resid);
        initView();
        initData();
    }

    private void initView() {
        rl_root=(RelativeLayout) findViewById(R.id.rl_root);
        mGv = (GridView) findViewById(R.id.gv_dialog);
        mLv= (ListView) findViewById(R.id.lv_dialog);
        ll_button= (LinearLayout) findViewById(R.id.ll_button);
        this.setCancelable(isCanCancle);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
               /* if(!isOkGetData) {
                   *//* for (BaseLableBean baseLableBean : mList) {
                        baseLableBean.setCheck(false);
                    }
                    initData();
                    resetData();*//*
                   mAdapter.clearData();
                }*/
            /*if(mAdapter!=null){
                mAdapter.clearData();
                for(BaseLableBean baseLableBean:mList){
                    baseLableBean.setCheck(false);
                }
                initData();
            }*/
            }
        });
    }
public void setDialogParams(int height,int width){
    ViewGroup.LayoutParams params=rl_root.getLayoutParams();
    params.width=width;
    params.height=height;
    rl_root.setLayoutParams(params);
}
    private void initData() {
        // 填充数据集合
        mAdapter = new StyleAdapter(mContext, mList);
        if(isListView){
            mLv.setVisibility(View.VISIBLE);
            mGv.setVisibility(View.GONE);
            mLv.setAdapter(mAdapter);
            ll_button.setVisibility(View.GONE);
        }else{
            mGv.setVisibility(View.VISIBLE);
            mLv.setVisibility(View.GONE);
            mGv.setAdapter(mAdapter);
        }
        TextView textView= (TextView) findViewById(R.id.tv_title);
        if(!TextUtils.isEmpty(title)){
            textView.setText(title);
        }else{
            textView.setVisibility(View.GONE);
        }
    }

    private void initAttributes(){
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        window.setAttributes(attributes);
    }
    public List getSelectData(){
        isOkGetData=true;
        if(mAdapter==null)
            return new ArrayList();
        return mAdapter.mList;
    }
    public void resetData(){
        if(mAdapter!=null) {
            for(BaseLableBean baseLableBean:mList){
                baseLableBean.setCheck(false);
            }
            initData();
        }
}
    public void setClickListener(ClickListener listener){
        this.listener=listener;

    }
    public class StyleAdapter extends BaseAdapter {

        private Context mContext;

        private List<BaseLableBean> mList;

        private List<BaseLableBean> mSelectData=new ArrayList<>();
        private boolean isFirstPosition=false;
        public StyleAdapter(Context context, List  list) {
            mContext = context;
            mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public BaseLableBean getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        public List getSelectData(){
            return this.mList;
        }
        public void clearData(){
            this.mList.clear();
            isFirstPosition=false;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(!isListView) {
                final ViewHolderForGridView holder;
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_dialog_lable, parent, false);
                    holder = new ViewHolderForGridView();
                    holder.tvName = (CheckBox) convertView.findViewById(R.id.tv_label_name);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolderForGridView) convertView.getTag();
                }
                if(position==0&&isFirstPosition) {
                    return convertView;
                }
                isFirstPosition = true;

                final BaseLableBean item = getItem(position);
                if(item.isCheck()){
                    holder.tvName.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                  //  mList.add(item);
                  //  item.setCheck(true);
                    holder.tvName.setChecked(true);
                }else{
                    holder.tvName.setTextColor(mContext.getResources().getColor(R.color.color666666));
                 //   item.setCheck(false);
                  //  mList.remove(item);
                    holder.tvName.setChecked(false);
                }
                holder.tvName.setText(item.getLableName());
                holder.tvName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            holder.tvName.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                            item.setCheck(true);
                         //   mList.add(item);
                        } else {
                            holder.tvName.setTextColor(mContext.getResources().getColor(R.color.color666666));
                            item.setCheck(false);
                        //    mList.remove(item);
                        }
                    }
                });
            }else{
                final ViewHolderForListView holder;
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_dialog_list, parent, false);
                    holder = new ViewHolderForListView();
                    holder.tvType = (TextView) convertView.findViewById(R.id.tv_label_name);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolderForListView) convertView.getTag();
                }
                final BaseLableBean item = getItem(position);
                if(item.isCheck()){
                    holder.tvType.setTextColor(mContext.getResources().getColor(R.color.colorff5722));
                }else{
                    holder.tvType.setTextColor(mContext.getResources().getColor(R.color.color999999));
                }
                holder.tvType.setText(item.getLableName());
                holder.tvType.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       /* if(item.isCheck()){
                            item.setCheck(false);
                        }else{
                            item.setCheck(true);
                        }*/
                        item.setCheck(true);
                        listener.onClick(item);
                    }
                });
            }
            return convertView;
        }

         class ViewHolderForGridView {
            CheckBox tvName;
        }
        class ViewHolderForListView{
            TextView tvType;
        }

    }
    public interface ClickListener {
        void onClick(BaseLableBean item);
    }

}