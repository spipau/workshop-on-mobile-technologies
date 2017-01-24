package sse.in.weather;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private sse.in.weather.MainActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.setClickListener(this);
    }

    @SuppressWarnings("unused")
    public void onSearchClicked(View view) {

        String query = binding.mainSearchEdittext.getText().toString();

        binding.mainProgress.setVisibility(View.VISIBLE);

        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + query + "&units=metric&cnt=7&lang=en&appid=e4f540652a8824cbd8bb4dbf6bfe6131";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        //Log.d(TAG, "Response: " + response.toString());

                        String cityHeadline = String.format(getString(R.string.main_city_headline), binding.mainSearchEdittext.getText());
                        binding.mainCityHeadline.setText(cityHeadline);
                        binding.mainCityHeadline.setVisibility(View.VISIBLE);

                        try {
                            binding.mainDescriptionTextview.setText(response.getJSONArray("weather").getJSONObject(0).getString("description"));
                            binding.mainLayoutDescription.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            binding.mainLayoutDescription.setVisibility(View.GONE);
                            Log.e(TAG, e.getMessage());
                        }

                        try {
                            binding.mainTemperatureTextview.setText(String.valueOf(response.getJSONObject("main").getDouble("temp")) + "°");
                            binding.mainLayoutTemerature.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            binding.mainLayoutTemerature.setVisibility(View.GONE);
                            Log.e(TAG, e.getMessage());
                        }

                        try {
                            binding.mainHumidityTextview.setText(String.valueOf(response.getJSONObject("main").getDouble("humidity")) + "%");
                            binding.mainLayoutHumidity.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            binding.mainLayoutHumidity.setVisibility(View.GONE);
                            Log.e(TAG, e.getMessage());
                        }

                        try {
                            binding.mainWindSpeedTextview.setText(String.valueOf(response.getJSONObject("wind").getDouble("speed") + " KM/h"));
                            binding.mainLayoutWindSpeed.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            binding.mainLayoutWindSpeed.setVisibility(View.GONE);
                            Log.e(TAG, e.getMessage());
                        }

                        try {
                            final String lat = String.valueOf(response.getJSONObject("coord").getDouble("lat"));
                            final String lon = String.valueOf(response.getJSONObject("coord").getDouble("lon"));

                            binding.mainMapButton.setVisibility(View.VISIBLE);

                            binding.mainMapButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String uri = "geo:" + lat + "," + lon;
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                    startActivity(intent);
                                }
                            });

                        } catch (JSONException e) {
                            binding.mainMapButton.setVisibility(View.GONE);
                            Log.e(TAG, e.getMessage());
                        }

                        binding.mainProgress.setVisibility(View.GONE);
                        binding.mainShortSearchHelpText.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        binding.mainProgress.setVisibility(View.GONE);
                        binding.mainShortSearchHelpText.setVisibility(View.VISIBLE);

                        binding.mainCityHeadline.setVisibility(View.GONE);
                        binding.mainLayoutDescription.setVisibility(View.GONE);
                        binding.mainLayoutTemerature.setVisibility(View.GONE);
                        binding.mainLayoutHumidity.setVisibility(View.GONE);
                        binding.mainLayoutWindSpeed.setVisibility(View.GONE);
                        binding.mainMapButton.setVisibility(View.GONE);

                        Log.e(TAG, "Error!!! " + error.getMessage());

                        Toast.makeText(getApplicationContext(), R.string.main_error, Toast.LENGTH_LONG).show();
                    }
                });

        // Access the RequestQueue through the singleton class.
        VolleySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }


}
