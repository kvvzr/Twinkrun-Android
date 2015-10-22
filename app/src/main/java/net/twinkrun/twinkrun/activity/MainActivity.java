package net.twinkrun.twinkrun.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import net.twinkrun.twinkrun.R;
import net.twinkrun.twinkrun.adapter.HistoryRecyclerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.history_recycler_view)
    RecyclerView mHistoryRecyclerView;

    @OnClick(R.id.play_button)
    public void onClickPlayButton() {
        startActivity(new Intent(this, PlayerSelectActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            // TODO: BLEに対応してなかった場合の表示
            finish();
        }

        mHistoryRecyclerView.hasFixedSize();
        mHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mHistoryRecyclerView.setAdapter(new HistoryRecyclerAdapter(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
