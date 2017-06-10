package jp.ne.naokiur.bingomachine.service;

import android.content.Context;

import jp.ne.naokiur.bingomachine.service.dao.BingoGameDao;

/**
 * Created by nao-ur on 2017/06/04.
 */

public class BingoGame {
    private String title;
    private Integer maxNumber;
    private long gameId;
    private BingoGameDao bingoGameDao;

    public BingoGame(String title, Integer maxNumber, Context context) {
        this.title = title;
        this.maxNumber = maxNumber;
        this.bingoGameDao = new BingoGameDao(context);
    }

    public String getTitle() {
        return title;
    }

    public Integer getMaxNumber() {
        return maxNumber;
    }

    public long getGameId() {
        return gameId;
    }

    public void register() {

        this.gameId = bingoGameDao.insert(title, maxNumber);
    }
}
