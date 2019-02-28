package com.example.arafat.genderapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    //constants:
    private static final String TAG = "MainActivity";
    private static final String BASE_URL = "https://gender-api.com/get?name=";
    private static final String API_KEY = "&key=PveLbldDrevgVmSwQT";
    //https://gender-api.com/get?name=elizabeth&key=PveLbldDrevgVmSwQT

    //member variables:
    private EditText mName;
    private TextView mInfo;
    private Button mSearch;
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mName = findViewById(R.id.editText);
        mInfo = findViewById(R.id.textView);
        mSearch = findViewById(R.id.button);
        mImage = findViewById(R.id.male_or_female2);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: called");
        letsDoSomeNetworking();
    }

    private void letsDoSomeNetworking() {

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = mName.getText().toString();
                if (name.equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter your name.", Toast.LENGTH_SHORT).show();

                } else {
                    String url = BASE_URL + name + API_KEY;
                    Log.d(TAG, "onClick: " + url);
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.get(url, new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            Log.d(TAG, "onSuccess: called");
                            Log.d(TAG, "onSuccess: " + response.toString());

                            String info = null;
                            try {
                                info = response.getString("gender");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (info.equals("male")) {
                                mInfo.setText(name + ", You are a boy! Am I right?");
                                mImage.setImageResource(R.drawable.male3);
                            } else {
                                mInfo.setText(name + ", You are a girl! Am I right?");
                                mImage.setImageResource(R.drawable.female2);
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Log.d(TAG, "onFailure: called");
                            Log.d(TAG, "onFailure: " + throwable.toString());
                            Log.d(TAG, "onFailure: " + statusCode);
                        }
                    });
                }
            }
        });

    }
}
