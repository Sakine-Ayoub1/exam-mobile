package com.example.sakine_ayoub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity  implements OnMapReadyCallback {

    private EditText editTextIP;
    private GoogleMap googleMap;
    private MapView mapView;

    private TextView city;
    private TextView region;
    private TextView country;

    private String API_URL = "https://ipinfo.io/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextIP = findViewById(R.id.searchIp);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        city = findViewById(R.id.city);
        region = findViewById(R.id.region);
        country = findViewById(R.id.country);
    }

    public void searchIP(View view){
        String ipAddress = editTextIP.getText().toString().trim();
        if (!ipAddress.isEmpty()) {
            String url = API_URL + ipAddress + "/geo";
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String loc = response.getString("loc");
                                String[] latLng = loc.split(",");
                                double latitude = Double.parseDouble(latLng[0]);
                                double longitude = Double.parseDouble(latLng[1]);
                                city.setText("City: "+ response.getString("city"));
                                region.setText("Region: "+ response.getString("region"));
                                country.setText("Country: "+ response.getString("country"));

                                LatLng location = new LatLng(latitude, longitude);
                                googleMap.clear();
                                googleMap.addMarker(new MarkerOptions().position(location));
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Gérer les erreurs ici
                        }
                    });

            requestQueue.add(jsonObjectRequest);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}