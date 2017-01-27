package com.luxtech_eg.nanodegree.dakhakhny.omla.ui;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.luxtech_eg.nanodegree.dakhakhny.omla.R;
import com.luxtech_eg.nanodegree.dakhakhny.omla.model.PlacesResponse;
import com.luxtech_eg.nanodegree.dakhakhny.omla.model.Result;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ahmed on 21/01/17.
 */

public class DetailsFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleMap mMap;
    final String TAG = DetailsFragment.class.getSimpleName();
    static String ARG_BANK_URI = "bank_uri";
    private Double longitude;
    private Double latitude;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private final int LOCATION_PERMISSION_REQUEST = 200;
    private final float DEFAULT_ZOOM = 12.0f;
    MapFragment mMapFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.v(TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        mMapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, mMapFragment);
        fragmentTransaction.commit();
        mMapFragment.getMapAsync(this);


        return rootView;
    }

    @Override
    public void onStart() {
        Log.v(TAG, "onStart");
        mGoogleApiClient.connect();
        super.onStart();

    }

    @Override
    public void onStop() {
        Log.v(TAG, "onStop");
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.v(TAG, "onMapReady");
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        checkLocationAndCameraLoaded();
    }

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }


    public static DetailsFragment newInstance(String bankUri) {
        Bundle args = new Bundle();
        args.putString(ARG_BANK_URI, bankUri);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.v(TAG, "onConnected");
        if (isLocationPermissionGranted()) {
            onLocationPermissionGranted();
        } else {
            requestLocationPermission();
        }


    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v(TAG, "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.v(TAG, "onConnectionFailed");
    }

    //@RequiresPermission(anyOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    void onLocationPermissionGranted() {
        Log.v(TAG, "onLocationPermissionGranted");
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation == null) {
            return;
        }
        Log.v(TAG, "location" + mLastLocation.getLatitude() + " , " + mLastLocation.getLongitude());
        latitude = mLastLocation.getLatitude();
        longitude = mLastLocation.getLongitude();
        checkLocationAndCameraLoaded();
    }

    // function called to ensure that map and lat long are set as the map loads separately  and latlong loads separately
    //  since I cant be sure which loads first due to the possibility of race( which value arrives first)
    void checkLocationAndCameraLoaded() {
        Log.v(TAG, "is map set" + (mMap != null));
        Log.v(TAG, "is lat set" + (longitude != null));

        if (latitude != null && longitude != null && mMap != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), DEFAULT_ZOOM));
            queryBankBranches();
        }
    }

    void queryBankBranches() {
        new addMarkersTask(latitude, longitude, 50000, "bank", "cib").execute();
    }

    void addBankBranchesToMap(ArrayList<Result> results) {
        int resultsArrayListSize = results.size();
        Log.v(TAG, "results size" + resultsArrayListSize);
        if (mMap == null) {
            Log.v(TAG, "map is null");
            return;
        }

        for (int i = 0; i < resultsArrayListSize; i++) {

            Result tempResult = results.get(i);
            double lat = tempResult.getGeometry().getLocation().getLat();
            double ln = tempResult.getGeometry().getLocation().getLng();

            Log.v(TAG, "lat " + lat + ", long " + ln);
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, ln))
                    .title(tempResult.getName()));
        }
    }

    public StringBuilder sbMethod(double lat, double longitude, int radius, String type, String keyword) {

        //use your current location here
        double mLatitude = lat;
        double mLongitude = longitude;

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + mLatitude + "," + mLongitude);
        sb.append("&radius=" + radius);
        sb.append("&types=" + type);
        sb.append("&keyword=" + keyword);
        sb.append("&key=" + getActivity().getString(R.string.google_maps_key));

        // example https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=31.2156876,29.9403779&radius=50000&types=bank
        Log.d("Map", "api: " + sb.toString());

        return sb;
    }

    boolean isLocationPermissionGranted() {
        Log.v(TAG, "isLocationPermissionGranted");
        Boolean isLocationPermissionGranted;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            isLocationPermissionGranted = false;
        } else {
            isLocationPermissionGranted = true;
        }
        return isLocationPermissionGranted;
    }

    void requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST);
        }
        //else the permisisons should already be granted as they are stated in the manifest and api is less than 23 (android 6 M )
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.v(TAG, "onRequestPermissionsResult");
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "permission Granted");
                    onLocationPermissionGranted();
                } else {
                    Log.v(TAG, "permission NOT Granted");
                }

                break;
            }
        }
    }

    class addMarkersTask extends AsyncTask<Void, Void, ArrayList<Result>> {
        private final double lat;
        private final double longitude;
        private final int radius;
        private final String type;
        private final String keyword;


        addMarkersTask(double lat, double longitude, int radius, String type, String keyword) {
            this.lat = lat;
            this.longitude = longitude;
            this.radius = radius;
            this.type = type;
            this.keyword = keyword;
        }

        @Override
        protected ArrayList<Result> doInBackground(Void... params) {
            ArrayList<Result> results = new ArrayList<Result>();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(sbMethod(latitude, longitude, radius, type, keyword).toString())
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String responseBody = response.body().string().toString();
                    Log.v(TAG, " plases response body" + responseBody);

                    PlacesResponse pr = new Gson().fromJson(responseBody, PlacesResponse.class);
                    results = pr.getResults();
                } else {
                    Log.v(TAG, " onResponse is NOT Successful");
                }
            } catch (IOException e) {
                Log.v(TAG, " IOException");
                e.printStackTrace();
            } catch (Exception e) {
                Log.v(TAG, " Exception");
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(ArrayList<Result> results) {
            addBankBranchesToMap(results);
        }
    }

}
