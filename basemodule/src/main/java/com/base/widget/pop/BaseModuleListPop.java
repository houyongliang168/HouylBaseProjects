package com.base.widget.pop;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.base.common.windows.DensityUtil;
import com.base.common.windows.ScreenUtils;
import com.base.hyl.houbasemodule.R;


/**
 * Created by zcc on 2017/8/1.
 * 列表pop
 */

public class BaseModuleListPop {
    private String[] mData=new String[]{};
    private PopupWindow mPopupwindow;
    private Activity mActivity;
    private ListView lv_pop;
    private String mSelectedItem;

    /**
     * 点击listview每一个item时
     */
    private ClickItemListener mListener;

    /**
     * 点击listview每一个item时
     */
    public interface ClickItemListener {
        public void onClick(int position);
    }

    public BaseModuleListPop(Activity activity) {
        mActivity = activity;
    }

    public void show(View view, String[] list, String selectedItem, ClickItemListener listener) {
        mData = list;
        mSelectedItem = selectedItem;
        mListener = listener;
        showPop(view);
    }

    private void showPop(View view) {

        View root = View.inflate(mActivity, R.layout.view_list_pop, null);

        lv_pop = (ListView) root.findViewById(R.id.lv_pop);

        final popupAdapter adapter = new popupAdapter();
        lv_pop.setAdapter(adapter);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        int rh = DensityUtil.dip2px(mActivity, 39) * mData.length;
        int sh = ScreenUtils.getScreenHeight(mActivity) - location[1] - view.getHeight();
        if (rh > sh) {
            height = sh;
        }
        mPopupwindow = new PopupWindow(root, ViewGroup.LayoutParams.MATCH_PARENT, height);

        mPopupwindow.setFocusable(true);
        mPopupwindow.setOutsideTouchable(true);
        mPopupwindow.setAnimationStyle(R.style.main_menu_animstyle);

        mPopupwindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        ScreenUtils.backgroundAlpha(mActivity, 0.5f);

        root.setFocusable(true);
        root.setFocusableInTouchMode(true);

        root.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mPopupwindow.dismiss();
                    return true;
                }
                return false;
            }
        });

        lv_pop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (null != mListener) {
                    mListener.onClick(i);
                }
                mPopupwindow.dismiss();
            }
        });

        mPopupwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ScreenUtils.backgroundAlpha(mActivity, 1f);
            }
        });
    }

    private class popupAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.length;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;
            if (convertView != null && convertView instanceof LinearLayout) {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            } else {
                view = View.inflate(mActivity, R.layout.item_live_pop, null);
                holder = new ViewHolder();

                holder.tv_item = (TextView) view.findViewById(R.id.tv_item_live);

                view.setTag(holder);

            }

            String itemStr = getItem(position);
            if (itemStr.equals(mSelectedItem)) {
                holder.tv_item.setTextColor(Color.parseColor("#ff5722"));
//                holder.tv_item.setSelected(true);
            } else {
                holder.tv_item.setTextColor(Color.parseColor("#333333"));
//                holder.tv_item.setSelected(false);
            }
            holder.tv_item.setText(itemStr);

            return view;
        }

        @Override
        public String getItem(int position) {
            return mData[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            TextView tv_item;
        }
    }

}
