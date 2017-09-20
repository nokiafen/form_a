package com.comba.android.combacommon.dialog;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;

import com.comba.android.combacommon.R;
import com.comba.android.combacommon.adapters.OnItemClickListener;
import com.comba.android.combacommon.adapters.RecyclerListSingleAdapter;
import com.comba.android.combacommon.net.BaseOkHttpActivity;
import com.comba.android.combacommon.toast.ToastUtil;
import com.comba.android.combacommon.utils.Toast;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.orhanobut.dialogplus.DialogPlus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import com.comba.android.combacommon.adapters.OnItemClickListener;
/**
 * Created by chenhailin on 2017/5/18.
 */

public class DialogSampleActivity extends BaseOkHttpActivity {

    private List<String> imageItems;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_https);
         imageItems = Arrays.asList(getResources().getStringArray(R.array.activity_dialog_item));
        RecyclerListSingleAdapter listAdapter = new RecyclerListSingleAdapter(imageItems, mItemClickListener);
        RecyclerView recyclerView = ButterKnife.findById(this, R.id.rv_https_activity);
        recyclerView.setAdapter(listAdapter);
    }



    private OnItemClickListener mItemClickListener=new OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            if (0 == position) {
                showMessageDialog("yourtitle","your message");
            } else if(1==position){
                showImageDialog("yourtitle", BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
            }else if(2==position){
                new WaitDialog(DialogSampleActivity.this).show();
            } else if(3==position){
                //moreAbout https://github.com/orhanobut/dialogplus
                ArrayAdapter adapter=new ArrayAdapter(DialogSampleActivity.this,R.layout.activity_list_dialog_item,R.id.tv_textview);
                adapter.add("niha");
                adapter.add("niha");
                adapter.add("niha");
                adapter.add("niha");
                DialogPlus dialog = DialogPlus.newDialog(DialogSampleActivity.this)
                        .setAdapter(adapter)
                        .setOnItemClickListener(new com.orhanobut.dialogplus.OnItemClickListener() {
                            @Override
                            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                                Toast.show(DialogSampleActivity.this,"hhahahdfa");
                            }
                        })
                        .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                        .setGravity(Gravity.TOP)
                        .create();
                dialog.show();

            }else if(4==position){
                //styleDialog more https://github.com/hss01248/DialogUtil  支持更多ios风格弹窗
                ArrayList arrayList=new ArrayList();
                arrayList.add("a");
                arrayList.add("b");
                arrayList.add("c");
                arrayList.add("d");
                arrayList.add("e");
                arrayList.add("f");
                StyledDialog.buildBottomItemDialog( arrayList, "cancel", new MyItemDialogListener() {
                    @Override
                    public void onItemClick(CharSequence charSequence, int i) {
                        Toast.show(DialogSampleActivity.this,charSequence);
                    }
                }).show();
            }else if(5==position){
                StyledDialog.buildIosAlert("title", "much more here inrouduce here ", new MyDialogListener() {
                    @Override
                    public void onFirst() {

                    }

                    @Override
                    public void onSecond() {
                        ToastUtil.showToastShort(DialogSampleActivity.this,"hellos");
                    }
                }).show();

            }
        }
    };



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
