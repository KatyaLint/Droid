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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
        void onClick(View view, int position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public FontTextView mTextView;
        public ImageView mSelectedImage;
        public ImageView nav_item_view_image;

        public ViewHolder(View v) {
            super(v);
            mTextView = (FontTextView)v.findViewById(R.id.nav_item_text_main);
            mSelectedImage = (ImageView)v.findViewById(R.id.nav_item_selected_image);
            nav_item_view_image = (ImageView)v.findViewById(R.id.nav_item_view_image);
        }
    }

    public NavListAdapter(ArrayList<NavItem> myDataset, OnItemClickListener listener,Context context) {
        mDataset = myDataset;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drawer_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        NavItem item = mDataset.get(position);
        holder.mTextView.setText(item.getName());
        if(item.isSelected()){

            holder.mTextView.setTextColor(mContext.getResources().getColor(R.color.COLOR_003D4C));
            holder.mSelectedImage.setVisibility(View.VISIBLE);
            holder.nav_item_view_image.setImageResource(item.getIconEnable());
        }else{

            holder.mTextView.setTextColor(mContext.getResources().getColor(R.color.COLOR_7FA5B4));
            holder.mSelectedImage.setVisibility(View.INVISIBLE);
            holder.nav_item_view_image.setImageResource(item.getIconDisable());
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