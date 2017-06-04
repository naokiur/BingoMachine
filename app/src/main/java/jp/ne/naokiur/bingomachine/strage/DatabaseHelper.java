package jp.ne.naokiur.bingomachine.strage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nao-ur on 2017/05/22.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "bingo_roll.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BingoProcessTable.generateCreateTable());
        db.execSQL(BingoGameTable.generateCreateTable());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
