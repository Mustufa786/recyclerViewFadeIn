package com.codixlab.fadeanimationrecyclerview.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codixlab.fadeanimationrecyclerview.R;
import com.codixlab.fadeanimationrecyclerview.model.People;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.constraint.Constraints.TAG;

public class FadeAnimationAdapter extends RecyclerView.Adapter<FadeAnimationAdapter.ViewHolder> {

    Context context;
    List<People> items;
    private int lastPosition = -1;
    private boolean on_attach = true;
    long DURATION_IN_FADE_ID = 400;
    OnItemClickListener clickListener;

   public interface OnItemClickListener {
        void OnItemClick(View view, People people, int position);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {

        this.clickListener = clickListener;
    }

    public FadeAnimationAdapter(Context context, List<People> items) {

        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_list, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {

        holder.name.setText(items.get(i).name);
        Picasso.get().load(items.get(i).image).into(holder.imageView);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickListener.OnItemClick(holder.itemView, items.get(i), i);
            }
        });
        setAnimation(holder.itemView, i);

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                Log.d(TAG, "onScrollStateChanged: Called " + newState);
                on_attach = false;
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        super.onAttachedToRecyclerView(recyclerView);
    }

    private void setAnimation(View itemView, int i) {
        if (!on_attach) {
            i = -1;
        }
        boolean isNotFirstItem = i == -1;
        i++;
        itemView.setAlpha(0.f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animator = ObjectAnimator.ofFloat(itemView, "alpha", 0.f, 0.5f, 1.0f);
        ObjectAnimator.ofFloat(itemView, "alpha", 0.f).start();
        animator.setStartDelay(isNotFirstItem ? DURATION_IN_FADE_ID / 2 : (i * DURATION_IN_FADE_ID / 3));
        animator.setDuration(500);
        animatorSet.play(animator);
        animator.start();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imageView;
        TextView name;
        View parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            parentLayout = itemView.findViewById(R.id.parentLayout);
        }
    }
}
