package com.simcoder.novalauncherclone;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeDrawer();
    }



    List<AppObject> appList = new ArrayList<>();

    private void initializeDrawer() {
        View mBottomSheet =findViewById(R.id.bottomSheet);
        final GridView mDrawerGridView = findViewById(R.id.drawerGrid);
        BottomSheetBehavior mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
        mBottomSheetBehavior.setHideable(false);
        mBottomSheetBehavior.setPeekHeight(300);

        for (int i = 0; i < 20; i++)
            appList.add(new AppObject("", String.valueOf(i), getResources().getDrawable(R.drawable.ic_launcher_foreground)));

        mDrawerGridView.setAdapter(new AppAdapter(getApplicationContext(), appList));
    }
}
