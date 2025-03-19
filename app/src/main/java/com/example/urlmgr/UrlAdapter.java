package com.example.urlmgr;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UrlAdapter extends RecyclerView.Adapter<UrlAdapter.UrlViewHolder> {

    private List<UrlItem> urls;
    private Context context;
    private OnDeleteClickListener onDeleteClickListener;

    public UrlAdapter(Context context, List<UrlItem> urls, OnDeleteClickListener onDeleteClickListener) {
        this.context = context;
        this.urls = urls;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public UrlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.url_item, parent, false);
        return new UrlViewHolder(view, onDeleteClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UrlViewHolder holder, int position) {
        UrlItem urlItem = urls.get(position);
        holder.bind(urlItem);
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public static class UrlViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewUrlName;
        private TextView textViewLongUrl;
        private TextView textViewShortUrl;
        private TextView textViewLocation;
        private Button buttonDelete;

        public UrlViewHolder(@NonNull View itemView, OnDeleteClickListener onDeleteClickListener) {
            super(itemView);
            textViewUrlName = itemView.findViewById(R.id.text_view_url_name);
            textViewLongUrl = itemView.findViewById(R.id.text_view_long_url);
            textViewShortUrl = itemView.findViewById(R.id.text_view_short_url);
            textViewLocation = itemView.findViewById(R.id.text_view_location);
            buttonDelete = itemView.findViewById(R.id.button_delete);

            buttonDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onDeleteClickListener.onDeleteClick(position);
                }
            });
        }

        public void bind(UrlItem urlItem) {
            textViewUrlName.setText(urlItem.getName());
            textViewLongUrl.setText(urlItem.getLongUrl());
            textViewShortUrl.setText(urlItem.getShortUrl());
            textViewLocation.setText(urlItem.getLocation());

            textViewShortUrl.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlItem.getShortUrl()));
                itemView.getContext().startActivity(browserIntent);
            });
        }
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }
}
