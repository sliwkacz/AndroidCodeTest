package com.rockspin.androiddevtest.mian;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rockspin.androiddevtest.R;
import com.rockspin.androiddevtest.api.model.CosmonautActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CosmonautActivityAdapter
        extends RecyclerView.Adapter<CosmonautActivityAdapter.ViewHolder> {

    private final LayoutInflater mInflater;
    private List<CosmonautActivity> mCosmonautActivityList;

    public CosmonautActivityAdapter(Context context) {
        this(context, new ArrayList<>());
    }

    public CosmonautActivityAdapter(Context context, List<CosmonautActivity> ideaList) {
        mInflater = LayoutInflater.from(context);
        mCosmonautActivityList = ideaList;
    }

    public void setCosmonautActivityList(List<CosmonautActivity> cosmonautActivityList) {
        mCosmonautActivityList = cosmonautActivityList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View rootView = mInflater.inflate(R.layout.list_item_ev, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CosmonautActivity cosmonautActivity = mCosmonautActivityList.get(position);
        holder.dateTv.setText(formatDate(cosmonautActivity.getDate()));
        holder.tvName.setText(cosmonautActivity.getPurpose());
    }

    private String formatDate(String dateString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = inputFormat.parse(dateString);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public int getItemCount() {
        return mCosmonautActivityList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_date)
        TextView dateTv;

        @BindView(R.id.tv_patent_name)
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
