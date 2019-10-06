package com.fed.androidschool_program_course;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
    private final List<String> items;

    public SpinnerAdapter(List<String> mLectors) {
        this.items = mLectors;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public String getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if(convertView == null){
            convertView = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_expandable_list_item_1,
                    viewGroup,false);
            SpinnerAdapter.ViewHolder viewHolder = new SpinnerAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        SpinnerAdapter.ViewHolder holder = (SpinnerAdapter.ViewHolder)convertView.getTag();
        holder.mTextView.setText(getItem(i));
        return convertView;
    }

    private class ViewHolder{
        private TextView mTextView;
        private ViewHolder(View view){

            mTextView = view.findViewById(android.R.id.text1);
        }
    }
}
