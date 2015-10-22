package net.twinkrun.twinkrun.model;

public interface IGame {

    void onStartCountDown();
    void onUpdateCountDown(int count);
    void onStartGame();
    void onUpdateScore(int score);
    void onFlash(int color);
    void onUpdateRole(int color, int score);
    void onStopGame();
    void onEndGame();
}
