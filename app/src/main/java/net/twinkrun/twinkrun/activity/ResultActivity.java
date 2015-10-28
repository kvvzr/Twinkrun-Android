package net.twinkrun.twinkrun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import net.twinkrun.twinkrun.R;
import net.twinkrun.twinkrun.adapter.RankingAdapter;
import net.twinkrun.twinkrun.entity.Player;
import net.twinkrun.twinkrun.entity.Result;
import net.twinkrun.twinkrun.entity.Role;
import net.twinkrun.twinkrun.view.ResultGraphView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ResultActivity extends AppCompatActivity {

    @Bind(R.id.result_list_view)
    ListView mResultListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setTitle(getResources().getString(R.string.result_title));

        ButterKnife.bind(this);

        Result result = new Result(1000, new HashMap<Role, List<Integer>>(), new ArrayList<Player>());
        ResultGraphView header = new ResultGraphView(this);
        header.setResult(result);
        header.startAnimation();
        mResultListView.addHeaderView(header);
        mResultListView.setAdapter(new RankingAdapter(this, result.getRanking()));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
