package net.twinkrun.twinkrun.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.twinkrun.twinkrun.R;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder> {

    private Context mContext;

    public HistoryRecyclerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_result_graph, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFF56B7F1);

        series.addPoint(new ValueLinePoint("Jan", 2.4f));
        series.addPoint(new ValueLinePoint("Feb", 3.4f));
        series.addPoint(new ValueLinePoint("Mar", .4f));
        series.addPoint(new ValueLinePoint("Apr", 1.2f));
        series.addPoint(new ValueLinePoint("Mai", 2.6f));
        series.addPoint(new ValueLinePoint("Jun", 1.0f));
        series.addPoint(new ValueLinePoint("Jul", 3.5f));
        series.addPoint(new ValueLinePoint("Aug", 2.4f));
        series.addPoint(new ValueLinePoint("Sep", 2.4f));
        series.addPoint(new ValueLinePoint("Oct", 3.4f));
        series.addPoint(new ValueLinePoint("Nov", .4f));
        series.addPoint(new ValueLinePoint("Dec", 1.3f));

        holder.mValueLineChart.addSeries(series);
        holder.mValueLineChart.startAnimation();
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.line_chart)
        ValueLineChart mValueLineChart;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
