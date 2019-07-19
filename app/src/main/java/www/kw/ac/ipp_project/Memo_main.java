 package www.kw.ac.ipp_project;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

 public class Memo_main extends AppCompatActivity {

    public static final int REQUEST_CODE_INSERT = 1000;
    private MemoAdapter mAdapter;
    private static List<String> list;


    Toolbar toolbar;
    EditText memo_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar) ;
        setSupportActionBar(toolbar);
        getSupportActionBar().show();

        ListView listView = findViewById(R.id.memo_list);

        list=new ArrayList<String>();

        Cursor cursor=getMemoCursor();
        mAdapter=new MemoAdapter(this,cursor);
        listView.setAdapter(mAdapter);


        //클릭하면 해당 메모 작성 자바 파일로 이동 하는 이벤트 리스너
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id){
                Intent intent=new Intent(Memo_main.this, Memo_write.class);
                Cursor cursor=(Cursor)mAdapter.getItem(position);
                String title=cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_TITLE));
                String content=cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_CONTENT));

                intent.putExtra("id",id);
                intent.putExtra("title",title);
                intent.putExtra("content",content);
                startActivityForResult(intent,REQUEST_CODE_INSERT);
            }
        });


        //클릭을 길게하면 메모를 삭제하는 이벤트 리스너 추가
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final long deleteId=id;

                Toast.makeText(getApplicationContext(),Integer.toString(list.size()),Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder=new AlertDialog.Builder(Memo_main.this);
                builder.setTitle("memo delete");
                builder.setMessage("메모를 삭제하시겠습니까?");
                builder.setPositiveButton("삭제(delete)", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db=MemoDbHelper.getInstance(Memo_main.this).getWritableDatabase();
                        int deletedCount = db.delete(MemoContract.MemoEntry.TABLE_NAME,MemoContract.MemoEntry._ID+"="+deleteId,null);
                        if(deletedCount==0){
                            Toast.makeText(Memo_main.this,"삭제 에러",Toast.LENGTH_SHORT).show();
                        }else{
                            mAdapter.swapCursor(getMemoCursor());
                            Toast.makeText(Memo_main.this,"삭제 성공",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("취소",null);
                builder.show();
                return true;
            }
        });
    }

    private Cursor getMemoCursor(){
        MemoDbHelper dbHelper=MemoDbHelper.getInstance(this);
        return dbHelper.getReadableDatabase().query(MemoContract.MemoEntry.TABLE_NAME,null,null,null,null,null,null);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==REQUEST_CODE_INSERT&&resultCode==RESULT_OK){
            mAdapter.swapCursor(getMemoCursor());
        }
    }


    private static class MemoAdapter extends CursorAdapter {
        public MemoAdapter(Context context, Cursor c){
            super(context,c);
        }
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent){
            return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2,parent,false);
        }
        @Override
        public void bindView(View view,Context context,Cursor cursor){
            TextView titleText=view.findViewById(android.R.id.text1);
            titleText.setText(cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_TITLE)));
            TextView dateText=view.findViewById(android.R.id.text2);
            dateText.setText("update date : "+cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_DATE)));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu) ;
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuBack :
                startActivity(new Intent(Memo_main.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"뒤로가기",Toast.LENGTH_SHORT).show();
                finish();
                return true ;
            case R.id.menuWrite :
                startActivity(new Intent(Memo_main.this, Memo_write.class));
                Toast.makeText(getApplicationContext(),"작성하기",Toast.LENGTH_SHORT).show();
                finish();
                return true ;
            case R.id.menuDraw :
                startActivity(new Intent(Memo_main.this, MemoPaint.class));
                Toast.makeText(getApplicationContext(),"그리기",Toast.LENGTH_SHORT).show();
                finish();
                return true ;
            default :
                return super.onOptionsItemSelected(item) ;
        }
    }
}

