package company.co.kr.project03.view;

/**
 * Created by Dongjin on 2017. 7. 18..
 */

import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import company.co.kr.project03.R;

public class MakeAttractionActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private final String TAG = "MakeAttractionActivity";

    @BindView(R.id.toolbar) Toolbar toolbar;

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_attraction);

        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        initFragment();
        setToolbar();
    }

    private void initFragment() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_make, MakeFragment.newInstance(), "MAKE").commit();
    }

    private void setToolbar() {
        toolbar.setTitle(R.string.MakeTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "Connection Failed");
    }
}
