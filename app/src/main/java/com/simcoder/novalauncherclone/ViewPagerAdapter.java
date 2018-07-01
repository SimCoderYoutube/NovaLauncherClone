package com.simcoder.novalauncherclone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;
    ArrayList<PagerObject> pagerAppList;
    int cellHeight;
    ArrayList<AppAdapter> appAdapterList = new ArrayList<>();

    public ViewPagerAdapter(Context context, ArrayList<PagerObject> pagerAppList, int cellHeight){
        this.context = context;
        this.pagerAppList = pagerAppList;
        this.cellHeight = cellHeight;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.pager_layout, container, false);

        final GridView mGridView = layout.findViewById(R.id.grid);
        AppAdapter mGridAdapter = new AppAdapter(context, pagerAppList.get(position).getAppList(), cellHeight);
        mGridView.setAdapter(mGridAdapter);

        appAdapterList.add(mGridAdapter);

        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return pagerAppList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public void notifyGridChanged(){
        for(int i = 0; i < appAdapterList.size();i++){
            appAdapterList.get(i).notifyDataSetChanged();
        }
    }
}
