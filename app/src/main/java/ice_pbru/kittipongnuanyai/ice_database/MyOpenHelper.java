package ice_pbru.kittipongnuanyai.ice_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kittipong.n on 28/4/2559.
 */
public class MyOpenHelper extends SQLiteOpenHelper {

    public static final String database_name = "MyShop.db";
    private static final int database_version = 1;
    private static final String create_user_table = "create table userTable (" +
            "_id integer primary key, "+
            "Name text, "+
            "Surname text, "+
            "User text, "+
            "Password text, "+
            "Email text); ";



    public MyOpenHelper(Context context) {
        super(context, database_name, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_user_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}//Main Class
