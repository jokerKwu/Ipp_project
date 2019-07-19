package www.kw.ac.ipp_project;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Calc_vertical extends AppCompatActivity {

    public static final int REQUEST_CODE_CALC=500;


    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button0;
    private Button buttonadd, buttondiv, buttonmul, buttonsub, buttonresult, buttonclear,buttonuse;

    private TextView text;
    private EditText edit;

    private int a;
    private int where = 0;


    private boolean usingCalc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_vertical);

        button1 = (Button) findViewById(R.id.btn1);
        button2 = (Button) findViewById(R.id.btn2);
        button3 = (Button) findViewById(R.id.btn3);
        button4 = (Button) findViewById(R.id.btn4);
        button5 = (Button) findViewById(R.id.btn5);
        button6 = (Button) findViewById(R.id.btn6);
        button7 = (Button) findViewById(R.id.btn7);
        button8 = (Button) findViewById(R.id.btn8);
        button9 = (Button) findViewById(R.id.btn9);
        button0 = (Button) findViewById(R.id.btn0);
        buttonadd = (Button) findViewById(R.id.btnAdd);
        buttonsub = (Button) findViewById(R.id.btnSub);
        buttonmul = (Button) findViewById(R.id.btnMul);
        buttondiv = (Button) findViewById(R.id.btnDiv);
        buttonresult = (Button) findViewById(R.id.btnRes);
        buttonclear = (Button) findViewById(R.id.btnAC);
        buttonuse=(Button)findViewById(R.id.btnUse);

        //rrvb
        text = (TextView) findViewById(R.id.edit1);

        //결과표시창
        edit = (EditText) findViewById(R.id.editRes);

        usingCalc=false;

        buttonuse.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit.getText().length()==0){
                    Toast.makeText(getApplicationContext(),"결과 값이 없습니다.",Toast.LENGTH_SHORT).show();
                }else{
                    String calcRes=edit.getText().toString();
                    Intent calcIntent=new Intent();
                    calcIntent.putExtra("calcRes",calcRes);
                    setResult(RESULT_OK,calcIntent);
                    finish();
                }
            }
        });

        OnClickListener cl = new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (v == button1) {
                    edit.setText(edit.getText().toString() + 1);
                    text.setText(text.getText().toString()+"1");
                    if(!usingCalc) usingCalc=true;
                } else if (v == button2) {
                    edit.setText(edit.getText().toString() + 2);
                    text.setText(text.getText().toString()+"2");
                    if(!usingCalc) usingCalc=true;
                } else if (v == button3) {
                    edit.setText(edit.getText().toString() + 3);
                    text.setText(text.getText().toString()+"3");
                    if(!usingCalc) usingCalc=true;
                } else if (v == button4) {
                    edit.setText(edit.getText().toString() + 4);
                    text.setText(text.getText().toString()+"4");
                    if(!usingCalc) usingCalc=true;
                } else if (v == button5) {
                    edit.setText(edit.getText().toString() + 5);
                    text.setText(text.getText().toString()+"5");
                    if(!usingCalc) usingCalc=true;
                } else if (v == button6) {
                    edit.setText(edit.getText().toString() + 6);
                    text.setText(text.getText().toString()+"6");
                    if(!usingCalc) usingCalc=true;
                } else if (v == button7) {
                    edit.setText(edit.getText().toString() + 7);
                    text.setText(text.getText().toString()+"7");
                    if(!usingCalc) usingCalc=true;
                } else if (v == button8) {
                    edit.setText(edit.getText().toString() + 8);
                    text.setText(text.getText().toString()+"8");
                    if(!usingCalc) usingCalc=true;
                } else if (v == button9) {
                    edit.setText(edit.getText().toString() + 9);
                    text.setText(text.getText().toString()+"9");
                    if(!usingCalc) usingCalc=true;
                } else if (v == button0) {
                    edit.setText(edit.getText().toString() + 0);
                    text.setText(text.getText().toString()+"0");
                    if(!usingCalc) usingCalc=true;
                } else if (v == buttonadd) {
                    // 더하기 부분
                    a = Integer.valueOf(edit.getText().toString().trim());
                    edit.setText("");
                    text.setText(text.getText().toString()+"+");
                    where = 1;
                } else if (v == buttonsub) {
                    // 빼기 부분
                    a = Integer.valueOf(edit.getText().toString().trim());
                    edit.setText("");
                    text.setText(text.getText().toString()+"-");

                    where = 2;
                } else if (v == buttonmul) {
                    // 곱하기 부분
                    a = Integer.valueOf(edit.getText().toString().trim());
                    edit.setText("");
                    text.setText(text.getText().toString()+"*");

                    where = 3;
                } else if (v == buttondiv) {
                    // 나누기 부분
                    a = Integer.valueOf(edit.getText().toString().trim());
                    edit.setText("");
                    text.setText(text.getText().toString()+"/");

                    where = 4;
                }
                //결과부분
                else if (v == buttonresult) {


                    //더하기
                    if (where == 1) {
                        a = a + Integer.valueOf(edit.getText().toString().trim());
                        edit.setText(Integer.toString(a));
                    }
                    //빼기
                    else if (where == 2) {
                        a = a - Integer.valueOf(edit.getText().toString().trim());
                        edit.setText(Integer.toString(a));
                    }
                    //곱하기
                    else if (where == 3) {
                        a = a * Integer.valueOf(edit.getText().toString().trim());
                        edit.setText(Integer.toString(a));
                    }
                    //나누기
                    else if (where == 4) {
                        a = a / Integer.valueOf(edit.getText().toString().trim());
                        edit.setText(Integer.toString(a));
                    }

                    if(usingCalc==true){
                        text.setText(text.getText().toString()+" = "+edit.getText().toString());
                        usingCalc=false;
                    }
                }

                //edittext 클리어
                else if (v == buttonclear) {
                    edit.setText("");
                    text.setText("");
                }
            }
        };

        button1.setOnClickListener(cl);
        button2.setOnClickListener(cl);
        button3.setOnClickListener(cl);
        button4.setOnClickListener(cl);
        button5.setOnClickListener(cl);
        button6.setOnClickListener(cl);
        button7.setOnClickListener(cl);
        button8.setOnClickListener(cl);
        button9.setOnClickListener(cl);
        button0.setOnClickListener(cl);
        buttonadd.setOnClickListener(cl);
        buttonsub.setOnClickListener(cl);
        buttonmul.setOnClickListener(cl);
        buttondiv.setOnClickListener(cl);
        buttonresult.setOnClickListener(cl);
        buttonclear.setOnClickListener(cl);

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        //세로 전환시 발생
        if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
            //세로 방향에서 나타나는 메시지
            Toast.makeText(this,"세로모드", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_calc_vertical);
        }
        //가로 전환시 발생
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            //가로 방향에서 나타나는 메시지
            Toast.makeText(this,"가로모드",Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_calc_horizontal);
        }
    }

}