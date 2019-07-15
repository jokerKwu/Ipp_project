package www.kw.ac.ipp_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button calcActivityBtn,memoActivityBtn,paintActivityBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button calcActivityBtn=(Button)findViewById(R.id.calcActivityBtn);
        Button memoActivityBtn=(Button)findViewById(R.id.memoActivityBtn);
        Button paintActivityBtn=(Button)findViewById(R.id.paintActivityBtn);

        calcActivityBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),Calc_vertical.class);
                startActivity(intent);
            }
        });

        memoActivityBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Memo_main.class);
                startActivity(intent);
            }
        });

        paintActivityBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MemoPaint.class);
                startActivity(intent);
            }
        });

    }


}
