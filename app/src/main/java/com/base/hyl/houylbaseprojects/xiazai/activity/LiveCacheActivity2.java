package com.base.hyl.houylbaseprojects.xiazai.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.base.common.base.BaseActivity;
import com.base.hyl.houylbaseprojects.R;
import com.base.hyl.houylbaseprojects.download.DownloadService;
import com.base.hyl.houylbaseprojects.download.config.Config;
import com.base.hyl.houylbaseprojects.xiazai.adpter.CustomDetailsFragmentAdapter;
import com.base.hyl.houylbaseprojects.xiazai.db.DownInfo;
import com.base.hyl.houylbaseprojects.xiazai.fragment.LiveCacheFragment2;
import com.base.hyl.houylbaseprojects.xiazai.fragment.LiveCachedFragment;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zcc on 2018/1/12.
 * 视频缓存
 */
public class LiveCacheActivity2 extends BaseActivity {
    public static String s1="http://1253804083.vod2.myqcloud.com/37128721vodgzp1253804083/f8a8ed4e4564972819035913346/PxMzcEM2PRwA.mp4";
    public static String s2="http://1253804083.vod2.myqcloud.com/37128721vodgzp1253804083/03872b144564972818871192085/ejPKKY6oimMA.mp4";
    public static String s3="http://1253804083.vod2.myqcloud.com/37128721vodgzp1253804083/be2db07d4564972818934883834/5Z3tOaKEDDwA.mp4";
    public static String s4="http://1253804083.vod2.myqcloud.com/37128721vodgzp1253804083/6a226e194564972818933574861/amE9YaW7lHQA.mp4";
    public static String s5="http:/http://1253804083.vod2.myqcloud.com/37128721vodgzp1253804083/bbc87eb64564972818934777024/oNOvUB4Hnn0A.mp4";
    public static String s6="http://1253804083.vod2.myqcloud.com/37128721vodgzp1253804083/a5abf4ba7447398155257982519/1mdDHkrofSIA.mp4";
    public static String s7="http://1253804083.vod2.myqcloud.com/37128721vodgzp1253804083/9c8caf984564972818771725259/IJt9hJUolzYA.mp4";
    public static String s0="http://1253804083.vod2.myqcloud.com/37128721vodgzp1253804083/b82ef5514564972818710149655/B0asil8VLfgA.mp4";
    public static List<String> listString=new ArrayList<>();



    private static final String INTENT_DATA = "downInfo";
    private View iv_cache_back;
    private TextView tv_cache_edit;
    private SlidingTabLayout tab_cache;
    private ViewPager pager_cache;
    private List<String> listTitles = new ArrayList<>();
    private boolean mIsEdit = false;
    private static int fragmentPosition = 0;
    private DownInfo mDownInfo = null;
    private List<Fragment> listFragment=new ArrayList<>();
    private TextView tv_add;
    private CustomDetailsFragmentAdapter adapter;
    private LiveCachedFragment instanceCached;
    private LiveCacheFragment2 instanceCashe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_activity_cache);
        try {
            //custom download database.
            //      DBController dbController = DBController.getInstance(getApplicationContext());

            Config config = new Config();
            //set database path.
            //    config.setDatabaseName("/sdcard/a/d.db");
            //      config.setDownloadDBController(dbController);

            //set download quantity at the same time.
            config.setDownloadThread(3);

            //set each download info thread number
            config.setEachDownloadThread(2);

            // set connect timeout,unit millisecond
            config.setConnectTimeout(10000);

            // set read data timeout,unit millisecond
            config.setReadTimeout(10000);
            DownloadService.getDownloadManager(this.getApplicationContext(), config);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initView();
        initData();

    }

    private void initData() {
        //添加数据源头
        listString.add(s0);
        listString.add(s1);
        listString.add(s2);
        listString.add(s3);
        listString.add(s4);
        listString.add(s5);
        listString.add(s6);
        listString.add(s7);

        instanceCached = LiveCachedFragment.getInstance(0 + "", mIsEdit);
        instanceCashe = LiveCacheFragment2.getInstance(1 + "", mIsEdit);

        listFragment.add(instanceCached);
        listFragment.add(instanceCashe);
        listTitles.add("已缓存");
        listTitles.add("缓存中");
        adapter = new CustomDetailsFragmentAdapter(getSupportFragmentManager(), listFragment, listTitles);
        pager_cache.setAdapter(adapter);
        pager_cache.setOffscreenPageLimit(2);
        tab_cache.setViewPager(pager_cache);
        pager_cache.setCurrentItem(0);
        /*监听 tab 选择*/
        tab_cache.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {    }

            @Override
            public void onTabReselect(int position) { }
        });

    }


    /**
     *  获取 下载的Bean
     * @return
     */
    private int count=0;
    public DownInfo getRandomBean(){
//        Random random = new Random();
//        int num = random.nextInt(7);
        int num =count;
        String sUrl="";
        if(num <=listString.size()&&num>=0){
            sUrl=listString.get(num);
        }

        DownInfo downInfo = new DownInfo(sUrl);
        downInfo.setBanner(false);
        downInfo.setImageUrl("");
        downInfo.setLiveId("测试"+(int)(Math.random()*1000000));
        downInfo.setTitle("测试"+num);
        count++;
        return downInfo;
    }

    private void initView() {
        iv_cache_back = findViewById(R.id.iv_cache_back);
        iv_cache_back.setOnClickListener(mOnClickListener);
        tv_cache_edit = (TextView) findViewById(R.id.tv_cache_edit);
        tv_cache_edit.setOnClickListener(mOnClickListener);
        tab_cache = (SlidingTabLayout) findViewById(R.id.tab_cache);
        pager_cache = (ViewPager) findViewById(R.id.pager_cache);
        tv_add = findViewById(R.id.tv_add);
        tv_add.setClickable(true);
        tv_add.setOnClickListener(mOnClickListener);

    }


    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == iv_cache_back) {
                if (mIsEdit) {
                    setEditFalse();
                } else {
                    finish();
                }
            } else if (v == tv_cache_edit) {
                String cacheEditStr = tv_cache_edit.getText().toString();
                if (cacheEditStr.equals("编辑")) {
                    mIsEdit = true;
                    tv_cache_edit.setText("完成");
                } else {
                    mIsEdit = false;
                    tv_cache_edit.setText("编辑");
                }
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                if(instanceCached!=null){
                    instanceCached.  setEditStatus(mIsEdit);
                }
                if(instanceCashe!=null){
                    instanceCashe.setEditStatus(mIsEdit);
                }

            }else if(v ==tv_add){//添加数据
                if(instanceCashe!=null){

                    instanceCashe.addData(  getRandomBean());
                }


            }
        }
    };

    @Override
    public void onBackPressed() {
        if (mIsEdit) {
            setEditFalse();
        } else {
            finish();
        }
    }

    /**
     * 设置编辑状态
     */
    public void setEditFalse() {
        mIsEdit = false;
        tv_cache_edit.setText("编辑");
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }


}
