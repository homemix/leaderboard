package com.thewolftechnologies.leaderboard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.thewolftechnologies.leaderboard.R;
import com.thewolftechnologies.leaderboard.models.LearningLeadersModel;
import com.thewolftechnologies.leaderboard.ui.main.LearningLeadersFragment;

import java.util.List;

public class LearningLeadersAdapter extends RecyclerView.Adapter<LearningLeadersAdapter.MyViewHolder> {

    private LearningLeadersFragment mContext;
    private List<LearningLeadersModel> mLearningLeadersModelList;

    public LearningLeadersAdapter(LearningLeadersFragment context, List<LearningLeadersModel> learningLeadersModelList) {
        mContext = context;
        mLearningLeadersModelList = learningLeadersModelList;
    }


    @NonNull
    @Override
    public LearningLeadersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LearningLeadersAdapter.MyViewHolder holder, int position) {
        LearningLeadersModel learningLeadersModel = mLearningLeadersModelList.get(position);

        holder.tv_name.setText(learningLeadersModel.getName());
        holder.tv_skill_learning.setText(learningLeadersModel.getHoursCountry());

        Picasso.get()
                .load(learningLeadersModel.getBadgeUrl())
                .resize(150, 150)
                .centerCrop()
                .into(holder.img_badge);


    }

    @Override
    public int getItemCount() {
        return mLearningLeadersModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_name, tv_skill_learning;
        public ImageView img_badge;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_skill_learning = itemView.findViewById(R.id.tv_skill_learning);
            img_badge = itemView.findViewById(R.id.img_badge);
        }
    }
}
