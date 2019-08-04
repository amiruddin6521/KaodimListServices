package com.example.kaodimlistservices.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kaodimlistservices.remote.Section;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shuhart.stickyheader.StickyAdapter;
import java.util.ArrayList;
import com.example.kaodimlistservices.R;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class SectionAdapter extends StickyAdapter<RecyclerView.ViewHolder, RecyclerView.ViewHolder> {
    private ArrayList<Section> sectionArrayList;
    private static final int LAYOUT_HEADER= 0;
    private static final int LAYOUT_CHILD= 1;
    private Context context;

    public SectionAdapter(Context context, ArrayList<Section> sectionArrayList){

        this.context = context;
        this.sectionArrayList = sectionArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == LAYOUT_HEADER ) {
            return new HeaderViewholder(inflater.inflate(R.layout.header_layout, parent, false));
        }else {
            return new ItemViewHolder(inflater.inflate(R.layout.item_layout, parent, false));
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (sectionArrayList.get(position).isHeader()) {
            ((HeaderViewholder) holder).textViewH.setText(sectionArrayList.get(position).getName());
        } else {
            ((ItemViewHolder) holder).textViewI.setText(sectionArrayList.get(position).getName());

            if(sectionArrayList.get(position).getImageThumbUrl() == null) {
                Picasso.Builder builder = new Picasso.Builder(context);
                builder.downloader(new OkHttp3Downloader(context));
                builder.build().load("null")
                        .placeholder((R.drawable.placeholder))
                        .error(R.drawable.error)
                        .into(((ItemViewHolder) holder).roundedImageViewI);
            } else {
                Picasso.Builder builder = new Picasso.Builder(context);
                builder.downloader(new OkHttp3Downloader(context));
                builder.build().load(sectionArrayList.get(position).getImageThumbUrl().getLg())
                        .placeholder((R.drawable.placeholder))
                        .error(R.drawable.error)
                        .into(((ItemViewHolder) holder).roundedImageViewI);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(sectionArrayList.get(position).isHeader()) {
            return LAYOUT_HEADER;
        }else
            return LAYOUT_CHILD;
    }

    @Override
    public int getItemCount() {
        return sectionArrayList.size();
    }

    @Override
    public int getHeaderPositionForItem(int itemPosition) {
        //Log.d("seeee",""+itemPosition+"......"+sectionArrayList.get(itemPosition).sectionPosition());
        return sectionArrayList.get(itemPosition).sectionPosition();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int headerPosition) {
        ((HeaderViewholder) holder).textViewH.setText( sectionArrayList.get(headerPosition).getName());
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return createViewHolder(parent, LAYOUT_HEADER);
    }

    public static class HeaderViewholder extends RecyclerView.ViewHolder {
        TextView textViewH;

        HeaderViewholder(View itemView) {
            super(itemView);
            textViewH = itemView.findViewById(R.id.servHeader);
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textViewI;
        RoundedImageView roundedImageViewI;

        ItemViewHolder(View itemView) {
            super(itemView);
            textViewI = itemView.findViewById(R.id.servName);
            roundedImageViewI = itemView.findViewById(R.id.servImg);
        }
    }
}
