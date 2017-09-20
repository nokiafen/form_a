package com.comba.android.combacommon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.comba.android.combacommon.adapters.OnItemClickListener;
import com.comba.android.combacommon.adapters.RecyclerListSingleAdapter;
import com.comba.android.combacommon.contacts.ContactRecordListActivity;
import com.comba.android.combacommon.dialog.DialogSampleActivity;
import com.comba.android.combacommon.hardware.CpuUtils;
import com.comba.android.combacommon.net.BaseOkHttpActivity;
import com.comba.android.combacommon.net.HttpsActivity;
import com.comba.android.combacommon.net.httpAbout.ButterKnifeAnnnotionSampleActivity;
import com.comba.android.combacommon.pay.UPPayActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends BaseOkHttpActivity implements View.OnClickListener {
    private static  final  int UPPAYSTARTCODE=0x11;
    private List<String> imageItems;


//    private List<String> imageItems;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_https);
        imageItems = Arrays.asList(getResources().getStringArray(R.array.activity_demo_item));
        RecyclerListSingleAdapter listAdapter = new RecyclerListSingleAdapter(imageItems, mItemClickListener);
        RecyclerView recyclerView = ButterKnife.findById(this, R.id.rv_https_activity);
        recyclerView.setAdapter(listAdapter);
    }

    private OnItemClickListener mItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            if (0 == position) {
                Intent intent = new Intent();
                intent.setClassName(MainActivity.this, HttpsActivity.class.getName());
                MainActivity.this.startActivity(intent);
            } else if (1 == position) {
                Intent intentI = new Intent();
                intentI.setClassName(MainActivity.this, DialogSampleActivity.class.getName());
                MainActivity.this.startActivity(intentI);
            } else if (2 == position) {
                Intent intentI = new Intent();
                intentI.setClassName(MainActivity.this, ButterKnifeAnnnotionSampleActivity.class.getName());
                MainActivity.this.startActivity(intentI);
            } else if (3 == position) {
                Intent intentI = new Intent();
                intentI.setClassName(MainActivity.this, ContactRecordListActivity.class.getName());
                MainActivity.this.startActivity(intentI);
            }else if(4==position){
                int appCpuUsedPercent = CpuUtils.getAppCpuUsedPercent(getPackageName());
            }else if(5==position){
                startActivityForResult(new Intent(MainActivity.this, UPPayActivity.class),UPPAYSTARTCODE);
            }
        }

    };


    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==UPPAYSTARTCODE){
            if(resultCode==UPPayActivity.RESUTL_CODEPAY_SUCCESS){
                Toast.makeText(this,"it is a good deal",Toast.LENGTH_LONG).show();
            }else if(resultCode==UPPayActivity.RESUTL_CODEPAY_FAIL){
                Toast.makeText(this,"it is a fail deal",Toast.LENGTH_SHORT).show();
            }else if(resultCode==UPPayActivity.RESUTL_CODEPAY_CANCLE){
                Toast.makeText(this,"it is a cancle deal",Toast.LENGTH_SHORT).show();
            }
        }
    }
}


