package com.single.chenhailin.mytestapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.iv_clickMe)
    AppCompatButton clickMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab.getVisibility();
    }


    @OnClick({R.id.fab,R.id.iv_clickMe})
    public void onViewClicked() {
        Intent intent =new Intent();
        intent.setClassName(MainActivity.this, com.comba.android.combacommon.MainActivity.class.getName());
        startActivity(intent);
    }
}
