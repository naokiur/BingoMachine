package jp.ne.naokiur.bingomachine.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import jp.ne.naokiur.bingomachine.strage.BingoProcessTable;
import jp.ne.naokiur.bingomachine.strage.DatabaseHelper;

/**
 * Created by nao-ur on 2017/05/23.
 */

public class BingoProcessDao {
    private final DatabaseHelper helper;

    public BingoProcessDao(Context context) {
        this.helper = new DatabaseHelper(context);

    }

    public void insert(long gameId, Integer value) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BingoProcessTable.GAME_ID.name(), gameId);
        values.put(BingoProcessTable.VALUE.name(), value);

        db.insert(BingoProcessTable.NAME, null, values);

        db.close();
    }

    public List<Integer> selectByGameId(long gameId) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "
                        + BingoProcessTable.NAME
                + " WHERE "
                    + BingoProcessTable.GAME_ID.name() + " = " + gameId + ";", null);

        List<Integer> resultList = new ArrayList<>();

        while (c.moveToNext()) {
            resultList.add(c.getInt(2));
        }

        return resultList;
    }

    public void deleteAll() {
        SQLiteDatabase db = helper.getWritableDatabase();

        db.delete(BingoProcessTable.NAME, null, null);
    }
}
