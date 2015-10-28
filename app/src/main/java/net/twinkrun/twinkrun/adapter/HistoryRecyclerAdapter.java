package net.twinkrun.twinkrun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.twinkrun.twinkrun.R;
import net.twinkrun.twinkrun.entity.Player;
import net.twinkrun.twinkrun.entity.Result;
import net.twinkrun.twinkrun.entity.Role;
import net.twinkrun.twinkrun.view.ResultGraphView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder> {

    private Context mContext;

    public HistoryRecyclerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_history, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mResultGraphView.setResult(getItem(position));
        holder.mResultGraphView.startAnimation();
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public Result getItem(int position) {
        return new Result(1000, new HashMap<Role, List<Integer>>(), new ArrayList<Player>());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.result_graph_view)
        ResultGraphView mResultGraphView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
