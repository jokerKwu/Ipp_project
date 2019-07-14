package www.kw.ac.ipp_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoDbHelper extends SQLiteOpenHelper {

    private static MemoDbHelper sInstance;

    public static final int DB_VERSION=1;
    public static final String DB_NAME="Memo.db";
    public static final String SQL_CREATE_ENTERS=
            String.format(
                    "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT,%s TEXT",
                    MemoContract.MemoEntry.TABLE_NAME,
                    MemoContract.MemoEntry._ID,
                    MemoContract.MemoEntry.COLUMN_NAME_TITLE,
                    MemoContract.MemoEntry.COLUMN_NAME_CONTENT
                    );
    public static final String SQL_DELETE_ENTERS=
            "DROP TABLE IF EXISTS "+MemoContract.MemoEntry.TABLE_NAME;
    public static MemoDbHelper getInstance(Context context){
        if(sInstance==null){
            sInstance=new MemoDbHelper(context);
        }return sInstance;
    }
    private MemoDbHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL(SQL_DELETE_ENTERS);
        onCreate(db);
    }
}
