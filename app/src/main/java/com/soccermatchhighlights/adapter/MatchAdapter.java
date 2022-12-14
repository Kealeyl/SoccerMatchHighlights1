package com.soccermatchhighlights.adapter;

import static com.soccermatchhighlights.Constants.ENTITY_SOCCER_MATCH;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.soccermatchhighlights.R;
import com.soccermatchhighlights.Match;
import com.soccermatchhighlights.MatchActions;
import com.soccermatchhighlights.Time;

import java.util.ArrayList;
import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchAdapterViewHolder> {

    private List<Match> matchList = new ArrayList<>();
    private Context context;
    private ClickEvent clickEventListener;

    public void setClickEventListener(ClickEvent clickEventListener) {
        this.clickEventListener = clickEventListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSoccerMatchListAndNotifyDataSetChanged(List<Match> matchList) {
        this.matchList = matchList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MatchAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View root = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_match, parent, false);
        return new MatchAdapterViewHolder(root);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull MatchAdapterViewHolder holder, int position) {
        Match match = matchList.get(position);
        holder.tvTitle.setText(match.getTitle());
        holder.tvCompetition.setText(match.getCompetition().getName());
        holder.tvData.setText(Time.utcToLocalTime(match.getDate()));
        Glide.with(holder.itemView)
                .load(match.getThumbnail())
                .into(holder.imgThumbnail);
        holder.layout.setOnClickListener(view -> {
            Intent intent = new Intent(context, MatchActions.class);
            intent.putExtra(ENTITY_SOCCER_MATCH, match);
            context.startActivity(intent);
        });

        if (match.isFavourite()) {
            holder.imgFavourite.setImageResource(R.drawable.ic_star);
        } else {
            holder.imgFavourite.setImageResource(R.drawable.ic_unstar);
        }

        holder.imgFavourite.setOnClickListener(view -> {
            if (clickEventListener != null) {
                clickEventListener.onClick(position, match);
            }
        });
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public static class MatchAdapterViewHolder extends RecyclerView.ViewHolder {
        protected ConstraintLayout layout;
        protected TextView tvTitle, tvCompetition, tvData;
        protected ImageView imgThumbnail, imgFavourite;

        public MatchAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvCompetition = itemView.findViewById(R.id.tv_competition);
            tvData = itemView.findViewById(R.id.tv_data);
            imgThumbnail = itemView.findViewById(R.id.img_thumbnail);
            imgFavourite = itemView.findViewById(R.id.img_favourite);
        }
    }

    public interface ClickEvent {
        void onClick(int position, Match match);
    }
}
