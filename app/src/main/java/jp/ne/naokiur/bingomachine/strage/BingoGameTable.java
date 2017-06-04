package jp.ne.naokiur.bingomachine.strage;

import android.provider.BaseColumns;

/**
 * Created by nao-ur on 2017/05/22.
 */

public enum BingoGameTable implements BaseColumns {
    TITLE,
    MAX_NUMBER;

    public static final String NAME = "BINGO_GAME";

    public static String generateCreateTable() {
        return "CREATE TABLE "
                + NAME + " ("
                + _ID + " INTEGER PRIMARY KEY,"
                + TITLE + " TEXT,"
                + MAX_NUMBER + " INTEGER"
                + ")";
    }
}
