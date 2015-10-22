package net.twinkrun.twinkrun.model;

import android.graphics.Color;

public interface IGame {

    void onStartCountDown();
    void onUpdateCountDown(int count);
    void onStartGame();
    void onUpdateScore(int score);
    void onFlash(Color color);
    void onUpdateRole(Color color, int score);
    void onStopGame();
    void onEndGame();
}
