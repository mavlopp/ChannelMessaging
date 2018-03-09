package smith.alaric.channelmessaging.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.util.UUID;

/**
 * Created by smithal on 07/03/2018.
 */
public class FriendsDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "MyDB.db";
    public static final String FRIEND_TABLE_NAME = "Friend";
    public static final String KEY_ID = "id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_IMAGEURL = "imageUrl";
    private static final String FRIEND_TABLE_CREATE = "CREATE TABLE " + FRIEND_TABLE_NAME + " (" + KEY_ID + " TEXT, " + KEY_USERNAME + " TEXT, " +
            KEY_IMAGEURL + " TEXT);";
    public FriendsDB(Context context) {
        super(context, Environment.getExternalStorageDirectory()+"/"+DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FRIEND_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FRIEND_TABLE_NAME);
        onCreate(db);
    }
}
