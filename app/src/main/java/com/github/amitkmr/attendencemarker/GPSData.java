package com.github.amitkmr.attendencemarker;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Created by amit on 12/4/16.
 */
public class GPSData extends DialogFragment implements LocationListener{

    public interface onSyncCoordinate {
        public void syncCoordinate(String s1, String s2);
    }

    onSyncCoordinate someEventListener;




    protected LocationManager locationManager;
    protected LocationListener locationListener;
    TextView txtLat;
    String lat;
    String provider;
//    protected String latitude,longitude;
    protected boolean gps_enabled,network_enabled;
    View rootView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            someEventListener = (onSyncCoordinate) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }

    final String LOG_TAG = "myLogs";
    String latitude, longitude;
    private ProgressBar spinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_gps_data, container, false);

        txtLat = (TextView)rootView.findViewById(R.id.gps_coordinates);
        spinner = (ProgressBar)rootView.findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        Button button = (Button) rootView.findViewById(R.id.gps_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                someEventListener.syncCoordinate(latitude, longitude);
                dismiss();
            }
        });

        return rootView;
    }
    @Override
    public void onLocationChanged(Location location) {
        spinner.setVisibility(View.GONE);
        txtLat = (TextView) rootView.findViewById(R.id.gps_coordinates);
        txtLat.setText("Latitude:" + location.getLatitude() + "\nLongitude:" + location.getLongitude());
        latitude  = location.getLatitude()+"";
        longitude = location.getLongitude()+"";


    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }
}
