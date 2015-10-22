package net.twinkrun.twinkrun.model;

import android.bluetooth.le.ScanResult;
import android.os.Handler;
import android.util.Pair;

import net.twinkrun.twinkrun.entity.Player;
import net.twinkrun.twinkrun.entity.Role;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private IGame mIGame;
    private Handler mGameTimer;
    private Handler mFlashTimer;
    private Handler mUpdateRoleTimer;
    private Handler mUpdateScoreTimer;
    private Handler mCountDownTimer;
    private Player mPlayer;
    private List<Player> mOthers;
    private List<Pair<Role, List<Integer>>> mTransition;
    private List<Integer> mCurrentTransition;

    private boolean mPlaying = false;
    private int mScore;
    private int mAddScore;
    private int mFlashCount;
    private int mCountDown;

    public Game(IGame game, Player player, List<Player> others) {
        mIGame = game;
        mPlayer = player;
        mOthers = others;
    }

    public void togglePlayState() {
        mPlaying = !mPlaying;

        if (mPlaying) {
            mIGame.onStartCountDown();
            start();
        } else {
            stop();
            mIGame.onStopGame();
        }
    }

    public void addScanResult(ScanResult result) {

    }

    private void start() {
        stop();
        countDown();
    }

    public void stop() {
        mTransition = new ArrayList<>();
        mCurrentTransition = new ArrayList<>();

        mScore = 1000;
        mAddScore = 0;
        mFlashCount = 0;
        mCountDown = 5;

        if (mGameTimer != null) {
            mGameTimer.removeCallbacksAndMessages(null);
        }

        if (mFlashTimer != null) {
            mFlashTimer.removeCallbacksAndMessages(null);
        }

        if (mUpdateRoleTimer != null) {
            mUpdateRoleTimer.removeCallbacksAndMessages(null);
        }

        if (mUpdateScoreTimer != null) {
            mUpdateScoreTimer.removeCallbacksAndMessages(null);
        }

        if (mCountDownTimer != null) {
            mCountDownTimer.removeCallbacksAndMessages(null);
        }
    }

    private void countDown() {
        mCountDownTimer = new Handler();

        new Runnable() {
            @Override
            public void run() {
                mIGame.onUpdateCountDown(mCountDown);
                if (mCountDown == 0) {
                    startGame();
                } else {
                    mCountDownTimer.postDelayed(this, 1000);
                }
                mCountDown--;
            }
        }.run();
    }

    private void startGame() {
        mIGame.onStartGame();

        mUpdateScoreTimer = new Handler();
        mUpdateScoreTimer.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateScore();
                mUpdateScoreTimer.postDelayed(this, 200);
            }
        }, 200);

        mUpdateRoleTimer = new Handler();
        new Runnable() {
            @Override
            public void run() {
                updateRole();
                mUpdateRoleTimer.postDelayed(this, 3000);
            }
        }.run();

        mGameTimer = new Handler();
        mGameTimer.postDelayed(new Runnable() {
            @Override
            public void run() {
                endGame();
            }
        }, 30000);
    }

    private void updateScore() {
        mCurrentTransition.add(mScore);

        mScore += mAddScore;
        mAddScore = 0;

        for (Player p : mOthers) {
            p.setCountedScore(false);
        }

        mIGame.onUpdateScore(mScore);
    }

    private void updateRole() {
        mIGame.onUpdateRole(0, mScore);

        mTransition.add(new Pair<Role, List<Integer>>(null, mCurrentTransition));
        mCurrentTransition.clear();

        mFlashCount = 0;
        flash();
    }

    private void flash() {
        if (mFlashCount < 4 /* flashCount */) {
            mIGame.onFlash(0);
            mFlashTimer = new Handler();
            mFlashTimer.postDelayed(new Runnable() {
                @Override
                public void run() {
                    flash();
                }
            }, 250 /* flashTime / flashCount */);
        }
        mFlashCount++;
    }

    private void endGame() {
        mIGame.onEndGame();
        stop();
    }
}
