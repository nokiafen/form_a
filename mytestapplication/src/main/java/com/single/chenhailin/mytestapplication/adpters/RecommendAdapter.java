package com.single.chenhailin.mytestapplication.adpters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenhailin on 2017/5/18.
 */

public class RecommendAdapter extends PagerAdapter {

        private Context context;
        private List<View> views;

        public RecommendAdapter(ArrayList<View> views, Context context){
            this.context = context;
            this.views = views;
        }
        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            ((ViewPager)container).removeView(views.get(position));

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager)container).addView(views.get(position));
            return views.get(position);
            // return super.instantiateItem(container, position);
        }

}
