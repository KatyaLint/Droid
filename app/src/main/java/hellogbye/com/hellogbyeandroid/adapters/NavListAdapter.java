/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hellogbye.com.hellogbyeandroid.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.NavItem;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Adapter for the planet data used in our drawer menu,
 */
public class NavListAdapter extends RecyclerView.Adapter<NavListAdapter.ViewHolder> {
    private ArrayList<NavItem> mDataset;
    private OnItemClickListener mListener;
    private Context mContext;
    private int mSelectedItem;


    /**
     * Interface for receiving click events from cells.
     */
    public interface OnItemClickListener {
        public void onClick(View view, int position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTextView;

        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    public NavListAdapter(ArrayList<NavItem> myDataset, OnItemClickListener listener,Context context) {
        mDataset = myDataset;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater vi = LayoutInflater.from(parent.getContext());
        View v = vi.inflate(R.layout.drawer_list_item, parent, false);
        FontTextView tv = (FontTextView) v.findViewById(R.id.text1);
   //     Typeface face=Typeface.createFromAsset(mContext.getAssets(), "fonts/dinnextltpro_bold.otf");
//        tv.setTypeface(face);
//        tv.setTextColor(mContext.getResources().getColor(R.color.white));
        return new ViewHolder(tv);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        NavItem item = mDataset.get(position);
        holder.mTextView.setText(item.getName());
        if(item.isSelected()){
            Typeface face=Typeface.createFromAsset(mContext.getAssets(), "fonts/dinnextltpro_bold.otf");
            holder.mTextView.setTypeface(face);
            holder.mTextView.setTextColor(mContext.getResources().getColor(R.color.white));
        }else{
            Typeface face=Typeface.createFromAsset(mContext.getAssets(), "fonts/dinnextltpro_regular.otf");
            holder.mTextView.setTypeface(face);
            holder.mTextView.setTextColor(mContext.getResources().getColor(R.color.hgb_nav_font_unselected));
        }
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onClick(view, position);
                mDataset.get(position).setIsSelected(true);
                mDataset.get(mSelectedItem).setIsSelected(false);
                notifyItemChanged(position);
                notifyItemChanged(mSelectedItem);
                mSelectedItem = position;

            }
        });


    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
