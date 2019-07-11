package www.kw.ac.ipp_project;


import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class Memo_main extends AppCompatActivity implements View.OnClickListener{
    Button bt1,bt2,bt3;
    FragmentManager fm;
    FragmentTransaction tran;
    Memo_list memo_list;
    Memo_search memo_search;
    Memo_write memo_write;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar) ;
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt3 = (Button) findViewById(R.id.bt3);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        memo_list = new Memo_list(); //프래그먼트 객채셍성
        memo_search=new Memo_search();
        memo_write=new Memo_write();

        setFrag(0);

    }

    @Override
    public void onBackPressed(){
        Toast.makeText(getApplicationContext(),"뒤로가기 테스트",Toast.LENGTH_SHORT).show();
        FragmentManager fm=getSupportFragmentManager();
        fm.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.bt1:
                setFrag(0);
                break;
            case R.id.bt2:
                setFrag(1);
                break;
            case R.id.bt3:
                setFrag(2);
                break;
        }
    }
    public void setFrag(int n){    //프래그먼트를 교체하는 작업을 하는 메소드를 만들었습니다
        fm=getSupportFragmentManager();
        tran = fm.beginTransaction();

        switch (n){
            case 0:
                getSupportActionBar().hide();
                tran.replace(R.id.main_frame, memo_list);//replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.addToBackStack(null);
                tran.commit();
                break;
            case 1:
                getSupportActionBar().show();
                tran.replace(R.id.main_frame, memo_write);//replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.addToBackStack(null);
                tran.commit();
                break;
            case 2:
                getSupportActionBar().hide();
                tran.replace(R.id.main_frame, memo_search);//replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.addToBackStack(null);
                tran.commit();
                break;
        }
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
                Toast.makeText(getApplicationContext(),"뒤로가기",Toast.LENGTH_SHORT).show();
                return true ;
            case R.id.itemPaint :
                Toast.makeText(getApplicationContext(),"그림판",Toast.LENGTH_SHORT).show();
                return true ;
            case R.id.itemCalc :
                Toast.makeText(getApplicationContext(),"계산기",Toast.LENGTH_SHORT).show();
                return true ;
            default :
                return super.onOptionsItemSelected(item) ;
        }
    }
}

