package jp.ne.naokiur.bingomachine.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import jp.ne.naokiur.bingomachine.strage.BingoGameTable;
import jp.ne.naokiur.bingomachine.strage.DatabaseHelper;

/**
 * Created by nao-ur on 2017/05/23.
 */

public class BingoGameDao {
    private final DatabaseHelper helper;

    public BingoGameDao(Context context) {
        this.helper = new DatabaseHelper(context);

    }

    public long insert(String title, Integer maxNumber) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BingoGameTable.TITLE.name(), title);
        values.put(BingoGameTable.MAX_NUMBER.name(), maxNumber);

        long rowId = db.insert(BingoGameTable.NAME, null, values);

        db.close();

        return rowId;
    }
}
