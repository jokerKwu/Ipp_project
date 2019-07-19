package www.kw.ac.ipp_project;

import android.app.Activity;
import android.icu.text.SimpleDateFormat;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;

public class Memo_write extends AppCompatActivity {
    String TAG="info";
    private EditText mTitleEditText;
    private EditText mContentEditText;
    private long mMemold=-1;
    Toolbar toolbar;
    public static final int REQUEST_CODE_INSERT = 1000;
    public static final int REQUEST_CODE_CALC=500;
    int backint=0;
    private Thread thread; //

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


        //메인......
/*
        thread =new Thread(){
            @Override
            public void run(){
                while(true){
                    try{
                        sleep(10000);
                    }catch(InterruptedException e){
                        thread.interrupt();
                    }
                    handler.sendEmptyMessage(0);
                }
            }
        };
        thread.start();
*/
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intent){
        if(requestCode==REQUEST_CODE_CALC){
            if(resultCode==RESULT_OK){
                String resultCalcValue=intent.getStringExtra("calcRes");
                mContentEditText.append(resultCalcValue);
            }
        }
    }

    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            if(msg.what==0){
                autoMemoSave();
            }
        }
    };

    public void autoMemoSave(){
        Toast.makeText(getApplicationContext(),"자동저장 테스트",Toast.LENGTH_SHORT).show();
    }


    /***
     * 액티비티가 죽게되거나 finish() 가 호출될때 이곳을 수행한다.
     */
    @Override
    protected void onDestroy() {
            super.onDestroy();
      // thread.interrupt(); // 스레드를 죽여버리는 구문이다. 이 구문을 사용하려면 스레드의 try catch문에서 this.interrupt를 시켜줘야한다.
       Log.i(TAG,"onDestory");

    }

    public boolean memoSave(){
        String title=mTitleEditText.getText().toString();
        String contents=mContentEditText.getText().toString();

        long now=System.currentTimeMillis();
        Date date=new Date(now);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String getTime=sdf.format(date);

        ContentValues contentValues=new ContentValues();
        contentValues.put(MemoContract.MemoEntry.COLUMN_NAME_TITLE,title);
        contentValues.put(MemoContract.MemoEntry.COLUMN_NAME_CONTENT,contents);
        contentValues.put(MemoContract.MemoEntry.COLUMN_NAME_DATE,getTime);

        SQLiteDatabase db=MemoDbHelper.getInstance(this).getWritableDatabase();
        getSupportActionBar().hide();
        if(mMemold==-1){
            long newRowID=db.insert(MemoContract.MemoEntry.TABLE_NAME,null,contentValues);
            if(newRowID==-1){
                Toast.makeText(this,"저장 오류",Toast.LENGTH_SHORT).show();
                return false;
            }else if(title.length()!=0||contents.length()!=0){
                //Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(Memo_write.this, Memo_main.class) );
        finish();
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
                memoSave();
                startActivity(new Intent(Memo_write.this, Memo_main.class));
                finish();
                return true ;
            case R.id.itemSave:
                memoSave();
                startActivity(new Intent(Memo_write.this, Memo_main.class) );
                finish();
                return true;
            case R.id.itemPaint :
                startActivity(new Intent(Memo_write.this, MemoPaint.class) );
                Toast.makeText(getApplicationContext(),"그림판",Toast.LENGTH_SHORT).show();
                finish();
                return true ;
            case R.id.itemCalc :
                startActivityForResult(new Intent(Memo_write.this, Calc_vertical.class),REQUEST_CODE_CALC);
                Toast.makeText(getApplicationContext(),"계산기",Toast.LENGTH_SHORT).show();

                return true ;
            case R.id.itemAdd:
                if(memoSave()==true)
                    startActivity(new Intent(Memo_write.this, Memo_main.class));
                finish();
                return true;
            case R.id.itemDeleted:
                return true;
            default :
                return super.onOptionsItemSelected(item) ;
        }
    }
}
