package ru.good_trends.resultant_test;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";
    private Timer mTimer;
    private MyTimerTask mMyTimerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (mTimer != null) {
            mTimer.cancel();
        }

        mTimer = new Timer();
        mMyTimerTask = new MyTimerTask();
        mTimer.schedule(mMyTimerTask, 1000, 15000); // задержка 1000ms, повтор 15000ms
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            new ParseTask().execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
            // получаем данные с внешнего ресурса
            try {
                URL url = new URL("http://phisix-api3.appspot.com/stocks.json");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultJson;
        }

        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
            // выводим целиком полученную json-строку
            Log.d(LOG_TAG, strJson);

            JSONObject dataJsonObj = null;

            try {
                dataJsonObj = new JSONObject(strJson);
                JSONArray currency = dataJsonObj.getJSONArray("stock");

                ArrayList<СurrencyClass> newList = new ArrayList<СurrencyClass>();

                // перебираем и выводим данные
                for (int i = 0; i < currency.length(); i++) {
                    JSONObject с_currency = currency.getJSONObject(i);

                    String name = с_currency.getString("name");
                    Integer volume = с_currency.getInt("volume");

                    JSONObject price = с_currency.getJSONObject("price");
                    Double amount = price.getDouble("amount");

                    Log.d(LOG_TAG, "name: " + name + ", volume: " + volume + ", amount: " + amount);

                            СurrencyClass record = new СurrencyClass();
                            record.SetName(name);
                            record.SetVolume(volume);
                            record.SetAmount(amount);
                            newList.add(record);
                }

                ListView CurrencyListView = (ListView)  findViewById(R.id.listview_currency);
                CurrencyArrayAdapter simpleAdapter = new CurrencyArrayAdapter(getApplicationContext(), R.layout.listview_currenty_adapter, newList);
                CurrencyListView.setAdapter(simpleAdapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new ParseTask().execute();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }


}
