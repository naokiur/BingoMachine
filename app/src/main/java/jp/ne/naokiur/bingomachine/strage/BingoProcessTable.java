package jp.ne.naokiur.bingomachine.strage;

import android.provider.BaseColumns;

/**
 * Created by nao-ur on 2017/05/22.
 */

public enum BingoProcessTable implements BaseColumns {
    GAME_ID(1),
    VALUE(2);

    public static final String NAME = "BINGO_PROCESS";
    private Integer columnNo;

    BingoProcessTable(Integer columnNo) {
        this.columnNo = columnNo;
    }

    public static String generateCreateTable() {
        return "CREATE TABLE "
                + NAME + " ("
                + _ID + " INTEGER PRIMARY KEY,"
                + GAME_ID + " INTEGER,"
                + VALUE + " INTEGER" +
                ")";
    }

    public Integer getColumnNo() {
        return columnNo;
    }
}
