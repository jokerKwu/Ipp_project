package www.kw.ac.ipp_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoDbHelper extends SQLiteOpenHelper {

    private static MemoDbHelper sInstance;

    public static final int DB_VERSION=1;
    public static final String DB_NAME="Memo9.db";
    public static final String SQL_CREATE_ENTERS=
            String.format(
                    "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT,%s TEXT,%s TEXT)",
                    MemoContract.MemoEntry.TABLE_NAME,
                    MemoContract.MemoEntry._ID,
                    MemoContract.MemoEntry.COLUMN_NAME_TITLE,
                    MemoContract.MemoEntry.COLUMN_NAME_CONTENT,
                    MemoContract.MemoEntry.COLUMN_NAME_DATE
                    );
    public static final String SQL_DELETE_ENTERS=
            "DROP TABLE IF EXISTS "+MemoContract.MemoEntry.TABLE_NAME;

    //싱글톤 생성
    public static MemoDbHelper getInstance(Context context){
        if(sInstance==null){
            sInstance=new MemoDbHelper(context);
        }return sInstance;
    }
    private MemoDbHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    //테이블 생성
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTERS);
    }

    //테이블 삭제 후 다시 생성
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL(SQL_DELETE_ENTERS);
        onCreate(db);
    }
}
