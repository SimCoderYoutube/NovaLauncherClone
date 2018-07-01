package com.simcoder.novalauncherclone;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class AppAdapter extends BaseAdapter {
    Context context;
    List<AppObject> appList;
    int cellHeight;

    public AppAdapter(Context context, List<AppObject> appList, int cellHeight){
        this.context = context;
        this.appList = appList;
        this.cellHeight = cellHeight;
    }
    @Override
    public int getCount() {
        return appList.size();
    }

    @Override
    public Object getItem(int position) {
        return appList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View v;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_app, parent, false);
        }else{
            v = convertView;
        }

        LinearLayout mLayout = v.findViewById(R.id.layout);
        ImageView mImage = v.findViewById(R.id.image);
        TextView mLabel = v.findViewById(R.id.label);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, cellHeight);
        mLayout.setLayoutParams(lp);

        mImage.setImageDrawable(appList.get(position).getImage());
        mLabel.setText(appList.get(position).getName());

        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).itemPress(appList.get(position));
            }
        });
        mLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((MainActivity) context).itemLongPress(appList.get(position));
                return false;
            }
        });
        return v;
    }
}
