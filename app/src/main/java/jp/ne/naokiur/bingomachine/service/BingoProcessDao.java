package jp.ne.naokiur.bingomachine.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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

    public void insert(Integer value) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BingoProcessTable.VALUE.name(), value);

        db.insert(BingoProcessTable.NAME, null, values);

        db.close();
    }
}
