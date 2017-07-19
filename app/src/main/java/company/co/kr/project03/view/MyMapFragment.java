package company.co.kr.project03.view;

import android.app.Fragment;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import company.co.kr.project03.R;
import company.co.kr.project03.model.Restaurant;

/**
 * Created by Dongjin on 2017. 7. 19..
 */

public class MyMapFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "MyMapFragment";

    private GoogleMap gMap;
    private UiSettings mapUiSetting;

    private Restaurant restaurant;

    public MyMapFragment(){}

    public static MyMapFragment newInstance(@NonNull Restaurant res) {
        Bundle args = new Bundle();
        MyMapFragment fragment = new MyMapFragment();
        args.putSerializable("res", res);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            restaurant = (Restaurant)getArguments().getSerializable("res");
            Log.d(TAG, restaurant.getTitle());
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.fragment_map, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // MapFragment을 통해 레이아웃에 만든 fragment의 ID를 참조하고 구글맵을 호출한다.
        MapFragment fragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.fragment) ;
        fragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        LatLng current = new LatLng(restaurant.getLatitude(), restaurant.getLongitude());
        Log.d(TAG, restaurant.getLongitude() + ", " + restaurant.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(current);
        markerOptions.title(restaurant.getTitle());
        markerOptions.snippet(restaurant.getComment());
        markerOptions.draggable(true);
        gMap.addMarker(markerOptions);
        gMap.moveCamera(CameraUpdateFactory.newLatLng(current));
        gMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        mapUiSetting = gMap.getUiSettings();
        mapUiSetting.setZoomControlsEnabled(true);

    }


    public static String getAddress(Context mContext, double lat, double lng) {
        String nowAddress ="현재 위치를 확인 할 수 없습니다.";
        Geocoder geocoder = new Geocoder(mContext, Locale.KOREA);
        List<Address> address = null;

            if (geocoder != null) {
                //세번째 파라미터는 좌표에 대해 주소를 리턴 받는 갯수로
                //한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 최대갯수 설정
                try {
                    address = geocoder.getFromLocation(lat, lng, 1);
                } catch(IOException ie) {
                    Log.d(TAG, "주소 가져오기 실패");
                    ie.printStackTrace();
                }

                if (address != null && address.size() > 0) {
                    // 주소 받아오기
                    String currentLocationAddress = address.get(0).getAddressLine(0).toString();
                    nowAddress  = currentLocationAddress;
                }
            }
        return nowAddress;
    }
}
