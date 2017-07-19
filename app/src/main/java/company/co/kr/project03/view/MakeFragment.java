package company.co.kr.project03.view;

/**
 * Created by Dongjin on 2017. 7. 18..
 */

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.TextInputEditText;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import company.co.kr.project03.R;
import company.co.kr.project03.model.Restaurant;

public class MakeFragment extends Fragment {

    private TextInputEditText editTitle;
    private TextInputEditText editAddress;
    private TextInputEditText editPhoneNum;
    private TextInputEditText editComment;
    private Button btnNext;

    private Restaurant restaurant;
    public MakeFragment() {}

    public static MakeFragment newInstance() {
        Bundle args = new Bundle();
        
        MakeFragment fragment = new MakeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_make, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        setEvent();

        super.onViewCreated(view, savedInstanceState);
    }

    private void initView(View view) {
        editTitle = (TextInputEditText) view.findViewById(R.id.edit_title);
        editAddress = (TextInputEditText) view.findViewById(R.id.edit_address);
        editPhoneNum = (TextInputEditText) view.findViewById(R.id.edit_phoneNum);
        editComment = (TextInputEditText) view.findViewById(R.id.edit_comment);
        btnNext = (Button) view.findViewById(R.id.btn_next);
    }

    private void setEvent() {
        editAddress.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                /*
                *   todo 사용자 위치를 받을건지 체크하고 아니라면 초기값을 넘겨주자
                *   saveState.
                * */

                return false;
            }
        });


        /*
        *  주소를 입력하면 해당 주소가 나오고,
        *  주소를 입력하지 않으면 default 인 판교역이 나온다
        * */
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restaurant = new Restaurant();
                readData(restaurant);
                showMapFragment(restaurant);
            }
        });
    }

    /*
    * Read each Data from TextInputEditText
    * */
    private void readData(Restaurant restaurant) {
        double latitude = 37.395037;
        double longitude = 127.111146;

        String title = editTitle.getText().toString();
        String address = editAddress.getText().toString();
        String phoneNum = editPhoneNum.getText().toString();
        String comment = editComment.getText().toString();

        restaurant.setTitle(title);
        restaurant.setAddress(address);
        restaurant.setPhoneNum(phoneNum);
        restaurant.setComment(comment);

        /* Get latitude & longitude from Geocoder by adrress */
        if(!address.equals("")) {
            Location findLocation = findGeoPoint(getActivity(), address);
            latitude = findLocation.getLatitude();
            longitude = findLocation.getLongitude();
        }

        restaurant.setLatitude(latitude);
        restaurant.setLongitude(longitude);
    }

    /*
    *  Show MapFragment with Restaurant data
    * */
    private void showMapFragment(Restaurant res) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_make, MyMapFragment.newInstance(res), "MAP")
                .addToBackStack("MAKE").commit();
    }

    public static Location findGeoPoint(Context mContext, String address) {
        Location location = new Location("");
        Geocoder geoCoder = new Geocoder(mContext);
        List<Address> addressList = null;

        try {
            addressList = geoCoder.getFromLocationName(address, 10);
        } catch(Exception e) {
            e.printStackTrace();
        }

        if(addressList != null) {
            for(Address ad : addressList) {
                double lat = ad.getLatitude();
                double lon = ad.getLongitude();
                location.setLatitude(lat);
                location.setLongitude(lon);
            }
        }
        return location;
    }

    //todo onBack 메소드 override

}
