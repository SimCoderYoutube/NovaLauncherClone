package com.simcoder.novalauncherclone;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    boolean isBottom = true;
    ViewPager mViewPager;
    int cellHeight;

    int NUMBER_OF_ROWS = 5;
    int DRAWER_PEEK_HEIGHT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        initializeHome();
        initializeDrawer();
    }

    ViewPagerAdapter mViewPagerAdapter;
    private void initializeHome() {
        ArrayList<PagerObject> pagerAppList = new ArrayList<>();

        ArrayList<AppObject> appList1 = new ArrayList<>();
        ArrayList<AppObject> appList2 = new ArrayList<>();
        ArrayList<AppObject> appList3 = new ArrayList<>();
        for (int i = 0; i< 20 ;i++)
            appList1.add(new AppObject("", "", getResources().getDrawable(R.drawable.ic_launcher_foreground)));
        for (int i = 0; i< 20 ;i++)
            appList2.add(new AppObject("", "", getResources().getDrawable(R.drawable.ic_launcher_foreground)));
        for (int i = 0; i< 20 ;i++)
            appList3.add(new AppObject("", "", getResources().getDrawable(R.drawable.ic_launcher_foreground)));

        pagerAppList.add(new PagerObject(appList1));
        pagerAppList.add(new PagerObject(appList2));
        pagerAppList.add(new PagerObject(appList3));

        cellHeight = (getDisplayContentHeight() - DRAWER_PEEK_HEIGHT) / NUMBER_OF_ROWS ;

        mViewPager = findViewById(R.id.viewPager);
        mViewPagerAdapter = new ViewPagerAdapter(this, pagerAppList, cellHeight);
        mViewPager.setAdapter(mViewPagerAdapter);
    }

    List<AppObject> installedAppList = new ArrayList<>();
    GridView mDrawerGridView;
    BottomSheetBehavior mBottomSheetBehavior;
    private void initializeDrawer() {
        View mBottomSheet =findViewById(R.id.bottomSheet);
        mDrawerGridView = findViewById(R.id.drawerGrid);
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
        mBottomSheetBehavior.setHideable(false);
        mBottomSheetBehavior.setPeekHeight(DRAWER_PEEK_HEIGHT);

        installedAppList = getInstalledAppList();

        mDrawerGridView.setAdapter(new AppAdapter(getApplicationContext(), installedAppList, cellHeight));

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(mAppDrag != null)
                    return;

                if(newState == BottomSheetBehavior.STATE_COLLAPSED && mDrawerGridView.getChildAt(0).getY() != 0)
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                if(newState == BottomSheetBehavior.STATE_DRAGGING && mDrawerGridView.getChildAt(0).getY() != 0)
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

    }



    AppObject mAppDrag = null;
    public void itemPress(AppObject app){
        if(mAppDrag != null){
            app.setPackageName(mAppDrag.getPackageName());
            app.setName(mAppDrag.getName());
            app.setImage(mAppDrag.getImage());
            mAppDrag = null;
            mViewPagerAdapter.notifyGridChanged();
            return;
        }else{
            Intent launchAppIntent = getApplicationContext().getPackageManager().getLaunchIntentForPackage(app.getPackageName());
            if (launchAppIntent != null)
                getApplicationContext().startActivity(launchAppIntent);
        }
    }

    public void itemLongPress(AppObject app){
        collapseDrawer();
        mAppDrag = app;
    }

    private void collapseDrawer() {
        mDrawerGridView.setY(0);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }







    private List<AppObject> getInstalledAppList() {
        List<AppObject> list = new ArrayList<>();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> untreatedAppList = getApplicationContext().getPackageManager().queryIntentActivities(intent, 0);

        for(ResolveInfo untreatedApp : untreatedAppList){
            String appName = untreatedApp.activityInfo.loadLabel(getPackageManager()).toString();
            String appPackageName = untreatedApp.activityInfo.packageName;
            Drawable appImage = untreatedApp.activityInfo.loadIcon(getPackageManager());

            AppObject app = new AppObject(appPackageName, appName, appImage);
            if (!list.contains(app))
                list.add(app);
        }
        return list;
    }


    private int getDisplayContentHeight() {
        final WindowManager windowManager = getWindowManager();
        final Point size = new Point();
        int screenHeight = 0, actionBarHeight = 0, statusBarHeight = 0;
        if(getActionBar()!=null){
            actionBarHeight = getActionBar().getHeight();
        }

        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(resourceId > 0){
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        int contentTop = (findViewById(android.R.id.content)).getTop();
        windowManager.getDefaultDisplay().getSize(size);
        screenHeight = size.y;
        return screenHeight - contentTop - actionBarHeight - statusBarHeight;
    }

}
