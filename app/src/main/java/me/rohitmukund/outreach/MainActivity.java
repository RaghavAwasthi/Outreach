package me.rohitmukund.outreach;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> repositories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, getRepositories());
        listView.setAdapter(arrayAdapter);
arrayAdapter.notifyDataSetChanged();

    }

    public ArrayList<String> getRepositories() {
        repositories = new ArrayList<String>();

        OkHttpClient client = new OkHttpClient();
        final String url = "https://api.github.com/orgs/JBossOutreach/repos";
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    String myResponse = response.body().string();

                    try {
                        JSONArray arr = new JSONArray(myResponse);
                        for(int i = 0; i <arr.length(); i++) {
                            JSONObject part = arr.getJSONObject(i);
                            String repoName = part.getString("name");
                            repositories.add(repoName);
                            }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
        return repositories;
    }
}