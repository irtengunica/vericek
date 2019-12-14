package com.example.okul.vericek;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {
    final static String URL="http://turulay.com";
    TextView tvData;
    JSONObject json;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvData=(TextView) findViewById(R.id.tvData);
        new  Game().execute("text");
    }
    protected JSONObject readGameParks(){
        HttpClient client=new DefaultHttpClient();
        HttpGet get=new HttpGet(URL);
        HttpResponse response=client.execute(get);
        StatusLine status=response.getStatusLine();
        int s=status.getStatusCode();
        if(s==200)
        {
            HttpEntity e=response.getEntity();
            String data= EntityUtils.toString(e);
            JSONArray posts=new JSONArray(data);
            JSONObject last=posts.getJSONObject(0);
            return last;
        }

        return null;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
    public class Game extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            try{
            json= readGameParks();
            }catch (ClientProtocolException e){
                String data=json.getString(params[0]);
                return data;
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }catch (JSONException e){
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String data) {
            tvData.setText(data);
        }
    }
}
