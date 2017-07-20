package company.co.kr.project03.view;

import android.app.Fragment;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import company.co.kr.project03.R;
import company.co.kr.project03.model.Restaurant;
import io.realm.Realm;

/**
 * Created by Dongjin on 2017. 7. 19..
 */

public class MyMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private static final String TAG = "MyMapFragment";

    private GoogleMap gMap;
    private UiSettings mapUiSetting;
    private TextView textAdress;
    private Button btnSave;

    private Restaurant restaurant = new Restaurant();

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
            Log.d(TAG, restaurant.getLatitude() + ", " + restaurant.getLongitude());
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.fragment_map, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapFragment fragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.fragment) ;
        fragment.getMapAsync(this);

        onSaveClick(view);
    }

    private void initView(View view) {
        textAdress = (TextView) view.findViewById(R.id.text_address);
        btnSave = (Button) view.findViewById(R.id.btn_save);
    }

    private void setTextAdress() {
        String address = getAddress(getActivity(), restaurant.getLatitude(), restaurant.getLongitude());
        restaurant.setAddress(address);
        textAdress.setText(address);
        textAdress.setSelected(true);
    }

    private void onSaveClick(View view) {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Realm.init(getActivity());
                final Realm realm = Realm.getDefaultInstance();

                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm bgRealm) {
                        Restaurant res = bgRealm.copyToRealm(restaurant);
                        res = realm.where(Restaurant.class).equalTo("title", restaurant.getTitle()).findFirst();
                        res.setTitle(" ");
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        // Transaction was a success.
                        Toast.makeText(getActivity(), "성공적으로 등록", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        // Transaction failed and was automatically canceled.
                    }
                });

                realm.beginTransaction();
                realm.commitTransaction();
                realm.close();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        LatLng latLng = new LatLng(restaurant.getLatitude(), restaurant.getLongitude());
        Log.d(TAG, restaurant.getLatitude() + ", " + restaurant.getLongitude());

        setTextAdress();
        gMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(restaurant.getTitle())
                .snippet(restaurant.getComment())
                .draggable(true));

        gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        gMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        gMap.setOnMarkerDragListener(this);

        mapUiSetting = gMap.getUiSettings();
        mapUiSetting.setZoomControlsEnabled(true);
    }

    public String getAddress(Context mContext, double lat, double lng) {
        String nowAddress ="default";
        Geocoder geocoder = new Geocoder(mContext);
        List <Address> address = null;

        try {
            // 한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 최대갯수 설정
            address = geocoder.getFromLocation(lat, lng, 10);
        } catch (IOException e) {
             e.printStackTrace();
        }

        if (address != null) {
            nowAddress = address.get(0).getAddressLine(0).toString();
        }
        Log.d(TAG, "주소 출력 " + nowAddress);
        return nowAddress;
    }

    /*
    *   Marker drag events
    * */
    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        LatLng newLatLng = marker.getPosition();
        restaurant.setLatitude(newLatLng.latitude);
        restaurant.setLongitude(newLatLng.longitude);
        setTextAdress();
        gMap.moveCamera(CameraUpdateFactory.newLatLng(newLatLng));
    }

}
