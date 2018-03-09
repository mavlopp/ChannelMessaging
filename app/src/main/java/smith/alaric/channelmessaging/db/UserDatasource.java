package smith.alaric.channelmessaging.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by smithal on 07/03/2018.
 */
public class UserDatasource {
    // Database fields
    private SQLiteDatabase database;
    private FriendsDB dbHelper;
    private String[] allColumns = { FriendsDB.KEY_ID, FriendsDB.KEY_USERNAME, FriendsDB.KEY_IMAGEURL };
    public UserDatasource(Context context) {
        dbHelper = new FriendsDB(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }

    public Friend createFriend(String username, String image) {
        ContentValues values = new ContentValues();
        UUID newID = UUID.randomUUID();
        values.put(FriendsDB.KEY_ID, newID.toString());
        Log.d("UUID : ", newID.toString());
        values.put(FriendsDB.KEY_USERNAME, username);
        values.put(FriendsDB.KEY_IMAGEURL, image);
        database.insert(FriendsDB.FRIEND_TABLE_NAME, null, values);
        Cursor cursor = database.query(FriendsDB.FRIEND_TABLE_NAME,
                allColumns, FriendsDB.KEY_ID + " = \"" + newID +"\"", null,
                null, null, null);
        cursor.moveToFirst();
        Friend newFriend = cursorToFriend(cursor);
        cursor.close();
        return newFriend;
    }

    public List<Friend> getAllFriends() {
        List<Friend> lesAmis = new ArrayList<Friend>();
        Cursor cursor = database.query(FriendsDB.FRIEND_TABLE_NAME,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Friend unAmi = cursorToFriend(cursor);
            lesAmis.add(unAmi);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return lesAmis;
    }
    private Friend cursorToFriend(Cursor cursor) {
        Friend comment = new Friend();
        String result = cursor.getString(0);
        comment.setId(UUID.fromString(result));
        comment.setUsername(cursor.getString(1));
        comment.setImageUrl(cursor.getString(2));
        return comment;
    }
    public void deleteFriend(Friend unAmi) {
        UUID id = unAmi.getId();
        database.delete(FriendsDB.FRIEND_TABLE_NAME, FriendsDB.KEY_ID + " = \"" + id.toString()+"\"", null);
    }
}