package com.comba.android.combacommon.net.httpAbout;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.comba.android.combacommon.R;
import com.comba.android.combacommon.R2;
import com.comba.android.combacommon.net.BaseOkHttpActivity;
import com.comba.android.combacommon.toast.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenhailin on 2017/5/18.
 */

public class ButterKnifeAnnnotionSampleActivity extends BaseOkHttpActivity {

    /**
     * Library 注解相关中必须用R2 ()全部！！！！！！！！！！！！！！！！！
     */
    @BindView(R2.id.iv_des)
    ImageView ivDes;
    @BindView(R2.id.tv_des)
    TextView tvDes;
    @BindView(R2.id.iv_card)
    CardView ivCard;


    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_butterknife_sample);
        ButterKnife.bind(this);

    }




    @OnClick({R2.id.iv_des, R2.id.tv_des, R2.id.iv_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R2.id.iv_des:
                break;
            case R2.id.tv_des:
                break;
            case R2.id.iv_card:
                ToastUtil.showToastShort(this,"i am a card yet?");
                break;
        }
    }


    /**
     * 更多见下图
     */
//    @Bind
//    TextView mTextView//最常用的注解，用来绑定View，避免findViewById，也可以用在ViewHolder里，必须是public
//
//    @Bind({ R.id.first_name, R.id.middle_name, R.id.last_name })
//    List<EditText> nameViews//绑定多个view，只能用List不能用ArrayList
//
//    @OnClick(R.id.submit)
//    public void submit(View view) {...}//绑定点击事件，支持多个id绑定同一个方法
//
//    @OnItemSelected(R.id.list_view)
//    void onItemSelected(int position) {...}//selected事件
//
//    @OnItemClick(R.id.example_list)
//    void onItemClick(int position) {...}//itemClick事件
//
//    @OnFocusChange(R.id.example)
//    void onFocusChanged(boolean focused){...}//焦点改变监听
//
//    @OnItemLongClick(R.id.example_list)
//    boolean onItemLongClick(int position){...}//长按监听
//
//    @OnPageChange(R.id.example_pager)
//    void onPageSelected(int position){...}//Viewpager切换监听
//
//    @OnTextChanged(R.id.example)
//    void onTextChanged(CharSequence text)//内容改变监听
//
//    @BindInt//用来绑定Integer类型的resource ID
//    @BindString//用来绑定string.xml里的字符串
//    @BindDrawable//用来绑定图片
//    @BindColor//用来绑定颜色
//    @BindDimen//用来绑定dimens

//    @BindArray(R2.array.activity_https_item) String[] arrays;


}
