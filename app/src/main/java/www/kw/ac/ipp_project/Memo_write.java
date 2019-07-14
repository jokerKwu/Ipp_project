package www.kw.ac.ipp_project;

import android.icu.text.UnicodeSetSpanner;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Memo_write extends AppCompatActivity {
    private EditText mTitleEditText;
    private EditText mContentEditText;
    private long mMemold=-1;
    Toolbar toolbar;
    public static final int REQUEST_CODE_INSERT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_write);

        mTitleEditText=findViewById(R.id.title_edit);
        mContentEditText=findViewById(R.id.content_edit);
        toolbar = (Toolbar) findViewById(R.id.toolbar) ;
        setSupportActionBar(toolbar);
        getSupportActionBar().show();

        Intent intent=getIntent();
        if(intent!=null){
            mMemold=intent.getLongExtra("id",-1);
            String title=intent.getStringExtra("title");
            String content=intent.getStringExtra("content");

            mTitleEditText.setText(title);
            mContentEditText.setText(content);
        }
    }
    public boolean memoSave(){
        String title=mTitleEditText.getText().toString();
        String contents=mContentEditText.getText().toString();
        ContentValues contentValues=new ContentValues();
        contentValues.put(MemoContract.MemoEntry.COLUMN_NAME_TITLE,title);
        contentValues.put(MemoContract.MemoEntry.COLUMN_NAME_CONTENT,contents);

        SQLiteDatabase db=MemoDbHelper.getInstance(this).getWritableDatabase();
        getSupportActionBar().hide();
        if(mMemold==-1){
            long newRowID=db.insert(MemoContract.MemoEntry.TABLE_NAME,null,contentValues);
            if(newRowID==-1){
                Toast.makeText(this,"저장 오류",Toast.LENGTH_SHORT).show();
                return false;
            }else if(title.length()!=0||contents.length()!=0){
                Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                return true;
            }
        }else{
            int count=db.update(MemoContract.MemoEntry.TABLE_NAME,contentValues,MemoContract.MemoEntry._ID+" = "+mMemold,null);

            if(count==0){
                Toast.makeText(this,"수정 에러",Toast.LENGTH_SHORT).show();
                return false;
            }else{
                Toast.makeText(this, "수정 완료", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed(){
        memoSave();
        super.onBackPressed();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu) ;
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemBack :
                startActivityForResult(new Intent(Memo_write.this, Memo_main.class), REQUEST_CODE_INSERT);
                Toast.makeText(getApplicationContext(),"뒤로가기",Toast.LENGTH_SHORT).show();
                return true ;
            case R.id.itemPaint :
                startActivityForResult(new Intent(Memo_write.this, MemoPaint.class), REQUEST_CODE_INSERT);
                Toast.makeText(getApplicationContext(),"그림판",Toast.LENGTH_SHORT).show();
                return true ;
            case R.id.itemCalc :
                startActivityForResult(new Intent(Memo_write.this, Calc_vertical.class), REQUEST_CODE_INSERT);
                Toast.makeText(getApplicationContext(),"계산기",Toast.LENGTH_SHORT).show();
                return true ;
            case R.id.itemAdd:
                if(memoSave()==true)
                    startActivityForResult(new Intent(Memo_write.this, Memo_main.class), REQUEST_CODE_INSERT);
                return true;
            case R.id.itemDeleted:
                return true;
            default :
                return super.onOptionsItemSelected(item) ;
        }
    }
}

