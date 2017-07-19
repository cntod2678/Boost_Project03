package company.co.kr.project03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;
import company.co.kr.project03.view.MakeAttractionActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @OnClick(R.id.make_attraction_btn)
    public void makeAttractionClicked(View view){
        Intent intent = new Intent(getApplicationContext(), MakeAttractionActivity.class);
        startActivity(intent);
    }
}
