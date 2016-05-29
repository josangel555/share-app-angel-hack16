package com.example.josangel.angelhack16_1;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultListActivity extends AppCompatActivity {

    private TextView tvResult;
    private static final String TAG = "com.example.josangel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);

        String url = "http://192.168.1.94:8080/api/book/find";

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ResultListActivity.this, AddBooksActivity.class);
                startActivity(intent);
            }
        });

        // get a JSON from server
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        List<Map<String, String>> stringList = new ArrayList<>();

                        try {

                            JSONArray bookArray = response.getJSONArray("Books");

                            for (int i=0; i<bookArray.length(); i++){
                                JSONObject bookObj = bookArray.getJSONObject(i);

                                Map<String, String> map = new HashMap<>();
                                map.put("bookName", bookObj.getString("bookName"));
                                map.put("author", bookObj.getString("author"));
                                map.put("userName", bookObj.getString("userName"));
                                stringList.add(map);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ListView listView = (ListView) findViewById(R.id.listView);
                        ListAdapter adapter =  new SimpleAdapter(ResultListActivity.this,
                              stringList, R.layout.book_list, new String[]{"bookName", "author", "userName"},
                                new int[]{R.id.bookName, R.id.author, R.id.userName});

                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(ResultListActivity.this, "You clicked :: " + id, Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        // Access the RequestQueue through your singleton class.
        BookApplication.getInstance().getRequestQueue().add(jsObjRequest);

        // Show a fab for user to add books

        // populate a list of books in the area

        // Add option to filter the list


    }
}
