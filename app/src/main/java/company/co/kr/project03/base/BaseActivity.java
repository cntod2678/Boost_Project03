package company.co.kr.project03.base;

/**
 * Created by Dongjin on 2017. 7. 19..
 */

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import company.co.kr.project03.OnButtonClickListener;
import company.co.kr.project03.R;

public class BaseActivity extends AppCompatActivity implements OnButtonClickListener {

    public static final int BUTTON_BACK = 0x100;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void setContentView(int resourceId) {
        super.setContentView(resourceId);
        bindViews();
    }

    protected void bindViews() {
        ButterKnife.bind(this);
        setUpToolbar();
    }

    protected void setUpToolbar() {
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void onClick(int id){
        switch(id) {
            case BUTTON_BACK :
                break;
        }
    }
}
