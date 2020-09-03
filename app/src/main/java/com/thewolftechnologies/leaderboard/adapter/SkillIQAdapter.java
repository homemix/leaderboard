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
import com.thewolftechnologies.leaderboard.models.SkillIQModel;
import com.thewolftechnologies.leaderboard.ui.main.SkillIQLeadersFragment;

import java.util.List;

public class SkillIQAdapter extends RecyclerView.Adapter<SkillIQAdapter.MyViewHolder> {

    private SkillIQLeadersFragment mContext;
    private List<SkillIQModel> mSkillIQModelList;

    public SkillIQAdapter(SkillIQLeadersFragment context, List<SkillIQModel> skillIQModelList) {
        mContext = context;
        mSkillIQModelList = skillIQModelList;
    }


    @NonNull
    @Override
    public SkillIQAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view_skill, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillIQAdapter.MyViewHolder holder, int position) {
        SkillIQModel skillIQModel = mSkillIQModelList.get(position);
        holder.tv_IQ_name.setText(skillIQModel.getNameIQ());
        holder.tv_IQ_skill_learning.setText(skillIQModel.getIQCountry());

        Picasso.get()
                .load(skillIQModel.getBadgeUrlIQ())
                .resize(270, 150)
                .centerCrop()
                .into(holder.img_IQ_badge);

    }

    @Override
    public int getItemCount() {
        return mSkillIQModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_IQ_name, tv_IQ_skill_learning;
        public ImageView img_IQ_badge;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_IQ_name = itemView.findViewById(R.id.tv_IQ_name);
            tv_IQ_skill_learning = itemView.findViewById(R.id.tv_IQ_skill_learning);
            img_IQ_badge = itemView.findViewById(R.id.img_IQ_badge);

        }
    }
}
