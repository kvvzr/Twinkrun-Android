package net.twinkrun.twinkrun.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.twinkrun.twinkrun.R;
import net.twinkrun.twinkrun.entity.Result;
import net.twinkrun.twinkrun.entity.Role;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ResultGraphView extends LinearLayout {

    @Bind(R.id.date_text_view)
    TextView mDateTextView;

    @Bind(R.id.score_text_view)
    TextView mScoreTextView;

    @Bind(R.id.line_chart)
    ValueLineChart mValueLineChart;

    public ResultGraphView(Context context) {
        super(context);
        init();
    }

    public ResultGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_result_graph, this);
        ButterKnife.bind(this, view);
    }

    public void setResult(Result result) {
        mDateTextView.setText(result.getDate().toString());
        String scoreText = String.valueOf(result.getScore()) + getResources().getString(R.string.score_unit);
        mScoreTextView.setText(scoreText);

        ValueLineSeries series = new ValueLineSeries();
        series.setColor(ContextCompat.getColor(getContext(), R.color.twinkrunClearWhite));

        for (Map.Entry<Role, List<Integer>> entry : result.getTransition().entrySet()) {
            for (int point : entry.getValue()) {
                series.addPoint(new ValueLinePoint(point));
            }
        }

        mValueLineChart.addSeries(series);
    }

    public void startAnimation() {
        mValueLineChart.startAnimation();
    }
}
