package updatedproject.com.tryprojectui;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import static java.sql.Types.INTEGER;

public class SQLiteClass extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Book_day_count.db";
    public static final String DATABAES_TABLE_NAME = "Title_table";
    public static final int VERSION = 1;

    public SQLiteClass(@Nullable Context context){
            super(context, DATABASE_NAME, null, VERSION);
            SQLiteDatabase db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String q = "CREATE TABLE `Title_table` (`ID` INTEGER PRIMARY KEY AUTOINCREMENT,`Title` TEXT,`Date` TEXT,`day_count` INTEGER);";
        db.execSQL(q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DATABAES_TABLE_NAME);
        onCreate(db);
    }
}
