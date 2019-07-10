package www.kw.ac.ipp_project;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Calc_vertical extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_vertical);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
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
