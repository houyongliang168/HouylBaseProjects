package com.base.common.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.common.log.MyLog;
import com.base.common.log.MyToast;
import com.base.hyl.houbasemodule.R;
import com.base.widget.dialog.MyDialog;


/**
 * 基类 -- 懒加载
 */

public abstract class CoreBaseFragment<P extends CoreBasePresenter> extends BaseFragment implements ICoreBaseControl.ICoreBaseView {

    protected String TAG;
    public P mPresenter;
    protected Context mContext;
    private boolean isOpen = false;
    public Activity mActivity;
    private View mView;


    //进行fragment 懒加载 优化
    private boolean mIsInited = false;//是否已经做过数据加载
    private boolean mIsPrepared = false;//判断 UI是否准备好，因为数据加载后需要更新UI
    private View view;
    private MyDialog myDialog;
    private DialogListener listener;

    public P getPresneter() {
        return mPresenter;
    }

    @Override
    public void onAttach(Context context) {

        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
        //other
        TAG = getClass().getSimpleName();
        //获取p 层 并绑定 view
        mPresenter = TUtil.getT(this, 0);
        if (this instanceof ICoreBaseControl.ICoreBaseView) {
            mPresenter.attachV(this);
        }
    }


    /*创建 view 的两种方式  传入view 和 传入 resouce 的id*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mIsPrepared = true;
        if (getLayoutView() != null) {
            mView=getLayoutView();
            return getLayoutView();
        } else {
            if (view == null) {
                view = inflater.inflate(getLayoutId(), container, false);
            } else {
                ViewGroup parent = (ViewGroup) view.getParent();
                if (parent != null) {
                    parent.removeView(view);
                }
            }
            mView=view;
            return view;
        }
    }


    /*加载数据*/

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        initUI(view,savedInstanceState);//初始化控件
        lazyLoad();//获取数据
        super.onViewCreated(view, savedInstanceState);
    }



    /*定义两种 获取布局的方法   getLayoutId  与 getLayoutView */
    public abstract int getLayoutId();

    public View getLayoutView() {
        return null;
    }



    /*跳转的方法*/
    public void startActivity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(mContext, tarActivity);
        startActivity(intent);
    }



    public void showLog(String msg) {
        MyLog.wtf("houyl","msg:"+msg);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onDestroyView() {
         /*懒加载 最后处理*/
        mIsInited = false;
        mIsPrepared = false;
        super.onDestroyView();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
         /*mvp 解除绑定*/
        if (mPresenter != null) {
            mPresenter.detachVM();
        }
    }

    /*定义懒加载的方法*/
    private void lazyLoad() {
        if (getUserVisibleHint() && !mIsInited && mIsPrepared) {
            //loadData
            loadData();
            mIsInited = true;//已经加载过数据
        }
    }
    /*设置懒加载 相关信息*/
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyLoad();
        }
    }
    /*加载数据 网络请求数据*/
    public abstract void loadData();
    /**
     * 初始化控件
     */
    public abstract void initUI(View view, @Nullable Bundle savedInstanceState);


    @Override
    public void showError(String msg) {
        MyToast.showShort(msg);
    }

    @Override
    public void showDialog() {
        showProgressDialog();
    }

    @Override
    public void dissmissDialog() {
        dismissProgressDialog();
    }
    @Override
    public void showErrorDialog(String msg) {
        if (myDialog == null) {
            myDialog = new MyDialog(getActivity());
            return;
        }
        myDialog.showDialog(R.layout.basemodule_dialog_verify);
        TextView tv_msg = (TextView) myDialog.findViewById(R.id.tv_warn_msg);
        tv_msg.setText(msg);
        myDialog.findViewById(R.id.tv_warn_true).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
    }

    @Override
    public void showDialogYesOrNo(String msg, final boolean isFinish, final DialogListener listener) {
        this.listener=listener;
        if (myDialog == null) {
            myDialog = new MyDialog(getActivity());
        }
        myDialog.showDialog(R.layout.basemodule_dialog_sure_and_cancel_type_one);
        TextView tv_msg = (TextView) myDialog.findViewById(R.id.tv_content);
        tv_msg.setText(msg);
        myDialog.findViewById(R.id.dialog_exit_true).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFinish) {
                    getActivity().finish();
                }
                if(listener!=null){
                    listener.onClickListenerYes();
                }
                myDialog.dismiss();
            }
        });
        myDialog.findViewById(R.id.dialog_exit_false).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onClickListenerNo();
                }
                myDialog.dismiss();
            }
        });
    }


}
