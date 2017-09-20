package com.single.chenhailin.mytestapplication;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.comba.android.combacommon.adapters.RecyclerListSingleAdapter;
import com.comba.android.combacommon.view.ListRecyclerView;
import com.single.chenhailin.mytestapplication.adpters.RecommendAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by chenhailin on 2017/5/18.
 */

public class ViewsTestActivity extends AppCompatActivity {
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.show_gallery)
    LinearLayout showGallery;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    private RecommendAdapter mRecommendAdapter;

    @BindView(R.id.rv_https_activity)
    ListRecyclerView  listRecycleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrollerlayout);
        ButterKnife.bind(this);
        initViewPager();
    }

    private void initViewPager() {
        pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                PointF downP = new PointF();
                PointF curP = new PointF();
                int act = event.getAction();
                if (act == MotionEvent.ACTION_DOWN
                        || act == MotionEvent.ACTION_MOVE
                        || act == MotionEvent.ACTION_UP) {
                    ((ViewGroup) v).requestDisallowInterceptTouchEvent(true);
                    if (downP.x == curP.x && downP.y == curP.y) {
                        return false;
                    }
                }
                return false;
            }
        });
//        pager.setAdapter(mRecommendAdapter);
        LayoutInflater mLayoutInflater=LayoutInflater.from(this);
        View view1=mLayoutInflater.inflate(R.layout.guide_one, null);
        View view2=mLayoutInflater.inflate(R.layout.guide_two, null);
        View view3=mLayoutInflater.inflate(R.layout.guide_three, null);

        final ArrayList<View> views =new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        mRecommendAdapter = new RecommendAdapter(views,this);
        pager.setAdapter(mRecommendAdapter);



        /******************************************************/
        List<String> imageItems = Arrays.asList(getResources().getStringArray(com.comba.android.combacommon.R.array.activity_https_item));
        RecyclerListSingleAdapter listAdapter = new RecyclerListSingleAdapter(imageItems, null);
        listRecycleView.setAdapter(listAdapter);
    }

    @OnClick({R.id.pager, R.id.show_gallery, R.id.toolbar, R.id.collapsing_toolbar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pager:
                break;
            case R.id.show_gallery:
                break;
            case R.id.toolbar:
                break;
            case R.id.collapsing_toolbar:
                break;
        }
    }
}
