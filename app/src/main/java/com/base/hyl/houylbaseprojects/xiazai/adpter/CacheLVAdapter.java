package com.base.hyl.houylbaseprojects.xiazai.adpter;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.base.common.image.GlideImgManager;
import com.base.common.log.MyLog;
import com.base.hyl.houylbaseprojects.R;
import com.base.hyl.houylbaseprojects.xiazai.fragment.LiveCacheFragment;
import com.base.hyl.houylbaseprojects.xiazai.utils.FileUtil;
import com.base.hyl.houylbaseprojects.xiazai.utils.NetUtils;
import com.base.widget.dialog.MyDialog;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.DownInfo;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.DownState;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.HttpDownManager;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpDownOnNextListener;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.utils.DbDwonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zcc on 2018/1/17.
 * 视频缓存adapter
 */

public class CacheLVAdapter extends BaseAdapter {
    public static final int TYPE_CACHE_DONE = 0;
    public static final int TYPE_CACHE_DOING = 1;//将type写入list每个item对象的type属性中
    private LiveCacheFragment mContext;
    private boolean mIsEdit = false;
    private boolean mIsAllEdit = false;
    private List<DownInfo> mList = new ArrayList<>();
    private HttpDownManager manager = null;
    private int mDelCount = 0;
    private DbDwonUtil dbUtil;
    private AnimationDrawable iv_cache_conn_frame = null;

    public CacheLVAdapter(LiveCacheFragment context, List<DownInfo> list) {
        mContext = context;
        if (list != null) {
            mList = list;
            manager = HttpDownManager.getInstance();
            dbUtil = DbDwonUtil.getInstance();
        }
    }

    /**
     * 更新
     *
     * @param list
     */
    public void update(List<DownInfo> list) {
        mList = list;
        notifyDataSetChanged();
    }

    /**
     * 获取list
     *
     * @return
     */
    public List<DownInfo> getList() {
        return mList;
    }

    /**
     * 编辑，全选
     *
     * @param isEdit
     * @param isAllEdit
     */
    public void setEdit(boolean isEdit, boolean isAllEdit) {
        mIsEdit = isEdit;
        mIsAllEdit = isAllEdit;
        notifyDataSetChanged();
    }

    /**
     * 全部暂停
     */
    public void allPause() {
        for (DownInfo info : mList) {
            info.setState(DownState.PAUSE);
        }
        manager.pauseAll();
        notifyDataSetChanged();
    }

    /**
     * 全部开始
     */
    public void allStart() {
        for (DownInfo info : mList) {
            info.setState(DownState.DOWN);
            manager.startDown(info);
        }
        notifyDataSetChanged();
    }

    /**
     * 删除
     */
    public void delete() {
        if (mDelCount != 0) {
            showDialog();
        }
    }

    /**
     * 显示删除对话框
     */
    private void showDialog() {
        final MyDialog dialog = new MyDialog(mContext.getActivity());
        dialog.showDialog(R.layout.dialog_exit);
        TextView tv_content = (TextView) dialog.findViewById(R.id.tv_content);
        tv_content.setText("是否删除");
        TextView dialog_exit_false = (TextView) dialog.findViewById(R.id.dialog_exit_false);
        dialog_exit_false.setText("否");
        TextView dialog_exit_true = (TextView) dialog.findViewById(R.id.dialog_exit_true);
        dialog_exit_true.setText("是");
        dialog_exit_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog_exit_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                List<DownInfo> resultList = new ArrayList<>();
                for (DownInfo info : mList) {
                    if (info.isDelFlag()) {
                        FileUtil.deleteFile(info.getSavePath());
                        dbUtil.deleteDowninfo(info);
                    } else {
                        resultList.add(info);
                    }
                }
                mList = resultList;
                mContext.setDelLayoutGone();
            }
        });
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        DownInfo info = getItem(position);
        DownState state = info.getState();
        if (state == DownState.FINISH) {
            return TYPE_CACHE_DONE;
        } else {
            return TYPE_CACHE_DOING;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public DownInfo getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ViewHolder holder = null;
        ViewHolder2 holder2 = null;
        if (convertView == null) {
            if (type == TYPE_CACHE_DONE) {
                holder = new ViewHolder();
                holder.real_position = position;
                convertView = View.inflate(mContext.getActivity(), R.layout.item_cache_done, null);
                holder.cb_done_edit = (CheckBox) convertView.findViewById(R.id.cb_done_edit);
                holder.iv_done_image = (ImageView) convertView.findViewById(R.id.iv_done_image);
                holder.tv_done_title = (TextView) convertView.findViewById(R.id.tv_done_title);
                holder.tv_done_content = (TextView) convertView.findViewById(R.id.tv_done_content);
                holder.tv_done_size = (TextView) convertView.findViewById(R.id.tv_done_size);
                convertView.setTag(holder);
            } else {
                holder2 = new ViewHolder2();
                holder2.real_position2 = position;
                convertView = View.inflate(mContext.getActivity(), R.layout.item_cache_doing, null);
                holder2.cb_doing_edit = (CheckBox) convertView.findViewById(R.id.cb_doing_edit);
                holder2.iv_doing_image = (ImageView) convertView.findViewById(R.id.iv_doing_image);
                holder2.tv_doing_title = (TextView) convertView.findViewById(R.id.tv_doing_title);
                holder2.tv_doing_content = (TextView) convertView.findViewById(R.id.tv_doing_content);

                holder2.iv_doing_conn = (ImageView) convertView.findViewById(R.id.iv_doing_conn);
                iv_cache_conn_frame = (AnimationDrawable) holder2.iv_doing_conn.getBackground();
                holder2.progressbar_doing = (ProgressBar) convertView.findViewById(R.id.progressbar_doing);
                holder2.tv_doing_state = (TextView) convertView.findViewById(R.id.tv_doing_state);
                holder2.iv_doing_state = (ImageView) convertView.findViewById(R.id.iv_doing_state);
                convertView.setTag(holder2);
            }
        } else {
            if (type == TYPE_CACHE_DONE) {
                holder = (ViewHolder) convertView.getTag();
            } else {
                holder2 = (ViewHolder2) convertView.getTag();
                iv_cache_conn_frame = (AnimationDrawable) holder2.iv_doing_conn.getBackground();
            }
        }
        if (holder2 != null) {
            final ViewHolder2 finalHolder2 = holder2;
            getItem(holder2.real_position2).setListener(new HttpDownOnNextListener<DownInfo>() {
                @Override
                public void onNext(DownInfo downInfo) {
                    MyLog.wtf("zcc", "下载完成");
                    finalHolder2.tv_doing_state.setText(downInfo.getSavePath());
                    dbUtil.update(downInfo);
                }

                @Override
                public void onStart() {
                    MyLog.wtf("zcc", "开始下载");
                    iv_cache_conn_frame.stop();
                    finalHolder2.iv_doing_conn.setVisibility(View.GONE);
                    finalHolder2.progressbar_doing.setVisibility(View.VISIBLE);
                }

                @Override
                public void onComplete() {
                    MyLog.wtf("zcc", "下载完成");
                    mList.remove(finalHolder2.real_position2);
                    update(mList);
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    MyLog.wtf("zcc", "失败:" + e.toString());
                    finalHolder2.iv_doing_conn.setVisibility(View.GONE);
                    finalHolder2.progressbar_doing.setVisibility(View.GONE);
                    finalHolder2.tv_doing_state.setTextColor(Color.parseColor("#f51706"));
                    finalHolder2.tv_doing_state.setText("下载失败");
                    finalHolder2.iv_doing_state.setImageResource(R.mipmap.cache_doing_refresh);
                }

                @Override
                public void onPuase() {
                    super.onPuase();
                    MyLog.wtf("zcc", "提示：暂停");
                    finalHolder2.tv_doing_state.setTextColor(Color.parseColor("#f51706"));
                    finalHolder2.tv_doing_state.setText("已暂停");
                    finalHolder2.iv_doing_state.setImageResource(R.mipmap.cache_doing_down);
                }

                @Override
                public void updateProgress(long readLength, long countLength) {
                    MyLog.wtf("zcc", "下载中——总文件大小：" + countLength + "——下载进度：" + readLength);
                    finalHolder2.iv_doing_conn.setVisibility(View.GONE);
                    finalHolder2.progressbar_doing.setVisibility(View.VISIBLE);
                    finalHolder2.tv_doing_state.setTextColor(Color.parseColor("#bab4ae"));
                    float countLenF = countLength;
                    float readLenF = readLength;
                    float countLen = countLenF / 1048576;
                    float readLen = readLenF / 1048576;
                    if (countLen < 1) {
                        countLen = countLenF / 1024;
                        readLen = readLenF / 1024;
                        finalHolder2.tv_doing_state.setText(String.format("%.2f%s%.2f%s", countLen, "K/", readLen, "KB"));
                    } else {
                        finalHolder2.tv_doing_state.setText(String.format("%.2f%s%.2f%s", countLen, "M/", readLen, "MB"));
                    }
                    finalHolder2.iv_doing_state.setImageResource(R.mipmap.cache_doing_pause);
                    finalHolder2.progressbar_doing.setMax((int) countLength);
                    finalHolder2.progressbar_doing.setProgress((int) readLength);
                }
            });
        }


        if (mIsEdit) {
            if (type == TYPE_CACHE_DONE) {
                holder.cb_done_edit.setVisibility(View.VISIBLE);
                if (mIsAllEdit) {
                    holder.cb_done_edit.setChecked(true);
                    mContext.setCBCheck(true);
                } else {
                    holder.cb_done_edit.setChecked(false);
                    mContext.setCBCheck(false);
                }
                final DownInfo item = getItem(holder.real_position);
                holder.cb_done_edit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        item.setDelFlag(isChecked);
                        if (isChecked) {
                            mDelCount++;
                        } else {
                            mDelCount--;
                        }
                        MyLog.wtf("zcc", "delcount:" + mDelCount);
                        if (mDelCount == getCount()) {
                            mContext.setCBCheck(true);
                        } else {
                            mContext.setCBCheck(false);
                        }
                    }
                });
            } else {
                holder2.cb_doing_edit.setVisibility(View.VISIBLE);
                if (mIsAllEdit) {
                    holder2.cb_doing_edit.setChecked(true);
                    mContext.setCBCheck(true);
                } else {
                    holder2.cb_doing_edit.setChecked(false);
                    mContext.setCBCheck(false);
                }
                final DownInfo item = getItem(holder2.real_position2);
                holder2.cb_doing_edit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        item.setDelFlag(isChecked);
                        if (isChecked) {
                            mDelCount++;
                        } else {
                            mDelCount--;
                        }
                        MyLog.wtf("zcc", "delcount:" + mDelCount);
                        if (mDelCount == getCount()) {
                            mContext.setCBCheck(true);
                        } else {
                            mContext.setCBCheck(false);
                        }
                    }
                });
            }
        } else {
            mDelCount = 0;
            if (type == TYPE_CACHE_DONE) {//done
                holder.cb_done_edit.setVisibility(View.GONE);
            } else {
                holder2.cb_doing_edit.setVisibility(View.GONE);
            }
        }
        if (type == TYPE_CACHE_DONE) {//done
            DownInfo item = getItem(holder.real_position);
            GlideImgManager.glideLoader(mContext.getActivity(), item.getImageUrl(), R.mipmap.iv_error, R.mipmap.iv_error, holder.iv_done_image, 1);
            holder.tv_done_title.setText(item.getTitle());
            holder.tv_done_content.setText(item.getDescription());
            float countLen = item.getCountLength();
            float countLength = countLen / 1048576;
            if (countLength < 1) {
                countLength = countLen / 1024;
                holder.tv_done_size.setText(String.format("%.2f%s", countLength, "K"));
            } else {
                holder.tv_done_size.setText(String.format("%.2f%s", countLength, "M"));
            }
        } else {
            final DownInfo item = getItem(holder2.real_position2);
            GlideImgManager.glideLoader(mContext.getActivity(), item.getImageUrl(), R.mipmap.iv_error, R.mipmap.iv_error, holder2.iv_doing_image, 1);
            holder2.tv_doing_title.setText(item.getTitle());
            holder2.tv_doing_content.setText(item.getDescription());
            holder2.progressbar_doing.setMax((int) item.getCountLength());
            holder2.progressbar_doing.setProgress((int) item.getReadLength());
            /**第一次恢复 */
            switch (item.getState()) {
                case START://起始状态
                    break;
                case PAUSE://已暂停
                    holder2.tv_doing_state.setTextColor(Color.parseColor("#f51706"));
                    holder2.tv_doing_state.setText("已暂停");
                    holder2.iv_doing_conn.setVisibility(View.GONE);
                    holder2.progressbar_doing.setVisibility(View.VISIBLE);
                    holder2.iv_doing_state.setImageResource(R.mipmap.cache_doing_down);
                    break;
                case DOWN://开始下载
                    iv_cache_conn_frame.start();
                    manager.startDown(item);
                    break;
                case STOP://下载停止
                    holder2.tv_doing_state.setText("下载停止");
                    holder2.iv_doing_conn.setVisibility(View.GONE);
                    holder2.progressbar_doing.setVisibility(View.VISIBLE);
                    holder2.tv_doing_state.setTextColor(Color.parseColor("#f51706"));
                    holder2.iv_doing_state.setImageResource(R.mipmap.cache_doing_refresh);
                    break;
                case ERROR://下载错误
                    holder2.tv_doing_state.setText("下载失败");
                    holder2.iv_doing_conn.setVisibility(View.GONE);
                    holder2.progressbar_doing.setVisibility(View.VISIBLE);
                    holder2.tv_doing_state.setTextColor(Color.parseColor("#f51706"));
                    holder2.iv_doing_state.setImageResource(R.mipmap.cache_doing_refresh);
                    break;
                case FINISH://下载完成
                    holder2.tv_doing_state.setText("下载完成");
                    break;
            }
            holder2.iv_doing_state.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DownState itemState = item.getState();
                    if (itemState == DownState.START || itemState == DownState.DOWN) {
                        manager.pause(item);
                        item.setState(DownState.PAUSE);
                    } else if (itemState == DownState.PAUSE || itemState == DownState.ERROR) {
                        if (NetUtils.getNetworkType(mContext.getActivity()) == 4) {//2g3g4g
                            boolean isOpen = mContext.showNetDialog();
                            if (isOpen) {
                                item.setState(DownState.DOWN);
                                manager.startDown(item);
                            }
                        } else {
                            item.setState(DownState.DOWN);
                            manager.startDown(item);
                        }
                    }
                }
            });
        }
        return convertView;
    }

    class ViewHolder {
        private CheckBox cb_done_edit;
        private ImageView iv_done_image;
        private TextView tv_done_title, tv_done_content, tv_done_size;
        private int real_position;
    }

    class ViewHolder2 {
        private CheckBox cb_doing_edit;
        private ImageView iv_doing_image;
        private TextView tv_doing_title, tv_doing_content;

        private TextView tv_doing_state;//下载状态
        private ImageView iv_doing_state;//下载状态
        private ImageView iv_doing_conn;//连接中
        private ProgressBar progressbar_doing;//下载进度
        private int real_position2;
    }

}
