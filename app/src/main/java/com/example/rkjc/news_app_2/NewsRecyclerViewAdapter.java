package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class NewsRecyclerViewAdapter  extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {
    Context mContext;
    ArrayList<NewsItem> newsItems;


    public NewsRecyclerViewAdapter(Context context,ArrayList<NewsItem> newsItem){
        this.mContext=context;
        this.newsItems=newsItem;
    }

    public interface ItemClickListener {
        void onItemClick(Cursor cursor, int clickedItemIndex);
    }

    @Override
    public NewsRecyclerViewAdapter.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.news_item, parent, shouldAttachToParentImmediately);
        NewsViewHolder viewHolder = new NewsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsRecyclerViewAdapter.NewsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView date;
        TextView description;

        public NewsViewHolder (View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView)  itemView.findViewById(R.id.description);
            date = (TextView) itemView.findViewById(R.id.date);

            itemView.setOnClickListener(this);
        }

        void bind(final int listIndex) {
            title.setText(newsItems.get(listIndex).getTitle());
            date.setText(newsItems.get(listIndex).getDate());
            description.setText(newsItems.get(listIndex).getDescription());

        }

        public void onClick(View view) {
            String url1=newsItems.get(getAdapterPosition()).getUrl();
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url1));
            mContext.startActivity(intent);
        }
    }


}
