package www.kw.ac.ipp_project;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MemoPaint extends AppCompatActivity {
    //그리기 뷰 전역 변수.
    private Memo_drawLine drawLine = null;
    public static float mStrokeWidth = 1;
    public static int mStrokeColor = Color.BLACK;
    public static int mCurrentColor = Color.WHITE;

    Toolbar paint_toolbar;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        setContentView(R.layout.activity_memo_paint);
        paint_toolbar=(Toolbar)findViewById(R.id.paint_toolbar);
        setSupportActionBar(paint_toolbar);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        //hasFocus : 앱이 화면에 보여졌을때 true로 설정되어 호출됨.
        //만약 그리기 뷰 전역변수에 값이 없을경우 전역변수를 초기화 시킴.
        if(hasFocus && drawLine == null)
        {
            //그리기 뷰가 보여질(나타날) 레이아웃 찾기..
            LinearLayout llCanvas = (LinearLayout)findViewById(R.id.llCanvas);
            if(llCanvas != null) //그리기 뷰가 보여질 레이아웃이 있으면...
            {
                //그리기 뷰 레이아웃의 넓이와 높이를 찾아서 Rect 변수 생성.
                Rect rect = new Rect(0, 0,
                        llCanvas.getMeasuredWidth(), llCanvas.getMeasuredHeight());

                //그리기 뷰 초기화..
                drawLine = new Memo_drawLine(this, rect);

                //그리기 뷰를 그리기 뷰 레이아웃에 넣기 -- 이렇게 하면 그리기 뷰가 화면에 보여지게 됨.
                llCanvas.addView(drawLine);
            }


            resetCurrentMode(0);
        }

        super.onWindowFocusChanged(hasFocus);
    }


    private int[] btns = {R.id.btnRED, R.id.btnBLUE, R.id.btnGREEN, R.id.btnBLACK,R.id.btnYELLOW,R.id.btnPURPLE,R.id.btnGRAY,
    R.id.btnPINK,R.id.btnORANGE};

    private int[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.BLACK,Color.rgb(255,255,0),
    Color.rgb(128,0,128),Color.rgb(128,128,128),Color.rgb(255,192,203),
            Color.rgb(255,165,0)};


    private void resetCurrentMode(int curMode)
    {
        for(int i=0;i<btns.length;i++)
        {

            ImageButton btn = (ImageButton)findViewById(btns[i]);
            if(btn != null)
            {
                btn.setBackgroundColor(i==curMode?0xff555555:0xffffffff);
            }
        }
        if(drawLine != null) {
            drawLine.setLineColor(colors[curMode]);
            mCurrentColor=curMode;
        }
    }

    public void btnClick(View view)
    {
        if(view == null) return;

        for(int i=0;i<btns.length;i++)
        {
            if(btns[i] == view.getId())
            {
                resetCurrentMode(i);
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //선굵기,색깔,바탕색 변경 메뉴 추가
        SubMenu subMenu = menu.addSubMenu(0, 0, 0, "선굵기");
        subMenu.add(1, 1, 0, "얇은 선");
        subMenu.add(1, 2, 1, "중간 선");
        subMenu.add(1, 3, 2, "굵은 선");
        subMenu.setGroupCheckable(1, true, true);
        getMenuInflater().inflate(R.menu.paint_menu, menu);
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                mStrokeWidth = 1;
                drawLine.setPenWidth(colors[mCurrentColor],1);
                return true;
            case 2:
                mStrokeWidth = 3;
                drawLine.setPenWidth(colors[mCurrentColor],3);
                return true;
            case 3:
                mStrokeWidth = 5;
                drawLine.setPenWidth(colors[mCurrentColor],5);
                return true;
            case R.id.item_back_yellow:
                drawLine.setBackgroundColor(1);
                return true;
            case R.id.item_back_white:
                drawLine.setBackgroundColor(2);
                return true;
            case R.id.item_back_green:
                drawLine.setBackgroundColor(3);
                return true;
            case R.id.item_clear:
                drawLine.setClear();
                return true;
            case R.id.itemBack:
                startActivityForResult(new Intent(MemoPaint.this, Memo_main.class), 1000);
                return true;
            case R.id.itemSave:


                Toast.makeText(getApplicationContext(),"저장 완료",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.group2:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
