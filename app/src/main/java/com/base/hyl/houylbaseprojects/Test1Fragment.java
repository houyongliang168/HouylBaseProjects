package com.base.hyl.houylbaseprojects;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.common.log.MyLog;
import com.base.hyl.houylbaseprojects.aidl.LiberaryAIDL;
import com.base.hyl.houylbaseprojects.aidl.LibraryService;
import com.base.hyl.houylbaseprojects.aidl.NotifyCallBack;
import com.orhanobut.logger.Logger;

import java.util.Random;

import static android.content.Context.BIND_AUTO_CREATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Test1Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Test1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Test1Fragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String TAG = Test1Fragment.class.getSimpleName();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    boolean mBound = false; //一开始，并没有和Service绑定.这个参数是用来显示绑定状态
    private OnFragmentInteractionListener mListener;
    private TextView tv_show;

    public Test1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Test1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Test1Fragment newInstance(String param1, String param2) {
        Test1Fragment fragment = new Test1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        MyLog.wtf(TAG, "onCreate");
        Logger.e(TAG + "onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MyLog.wtf(TAG, "onCreateView");
        Logger.e(TAG + "onCreateView");
        return inflater.inflate(R.layout.fragment_test1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.tv_add).setOnClickListener(this);
        view.findViewById(R.id.tv_remove).setOnClickListener(this);
        view.findViewById(R.id.tv_bind).setOnClickListener(this);
        view.findViewById(R.id.tv_unbind).setOnClickListener(this);
        tv_show = view.findViewById(R.id.tv_show);


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        MyLog.wtf(TAG, "onResume");
        Logger.e(TAG + "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        MyLog.wtf(TAG, "onPause");
        Logger.e(TAG + "onPause");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        MyLog.wtf(TAG, "onDetach");
        Logger.e(TAG + "onDetach");
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add:
                addCustomer();
                break;
            case R.id.tv_remove:
                leaveCustomer();
                break;
            case R.id.tv_bind:
                bindServer();
                break;
            case R.id.tv_unbind:
                unbindServer();
                break;
            default:
                break;

        }
    }

    private LiberaryAIDL myBinder;
    //匿名内部类：服务连接对象
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        //和服务绑定成功后，服务会回调该方法
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = LiberaryAIDL.Stub.asInterface(service);
            mBound = true; //true说明是绑定状态
            try {
                //我们这个demo里面 只注册了一个回调 实际上可以注册很多个回调 因为service里面 我们存的是list callback
                myBinder.registerCallBack(mNotifyCallBack);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        //当服务异常终止时会调用。注意，解除绑定服务时不会调用
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false; //服务异常终止时，状态为未绑定
            try {
                myBinder.unregisterCallBack(mNotifyCallBack);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            myBinder = null;
        }
    };

    private NotifyCallBack mNotifyCallBack = new NotifyCallBack.Stub() {

        @Override
        public void notifyMainUiThread(String name, boolean joinOrLeave) throws RemoteException {
            String toastStr = "";
            if (joinOrLeave) {
                toastStr = name + "进入了餐厅";
            } else {
                toastStr = name + "离开了餐厅";
            }
            tv_show.setText(toastStr);
        }
    };

    private void startServer() {
        Intent startIntent = new Intent(getActivity(), LibraryService.class);
        getActivity().startService(startIntent);
    }

    private void stopServer() {
        Intent startIntent = new Intent(getActivity(), LibraryService.class);
        getActivity().stopService(startIntent);
    }

    private void bindServer() {
        Intent startIntent = new Intent(getActivity(), LibraryService.class);
        getActivity().bindService(startIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private void unbindServer() {
        if(mBound){
            getActivity().unbindService(mServiceConnection);
            mBound=false;
        }
        
      
    }

    private void leaveCustomer() {
        try {
            myBinder.registerCallBack(mNotifyCallBack);
            myBinder.leave();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    private void addCustomer() {
        try {
            myBinder.join(new Binder(), getRandomString(6));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
