package com.example.josangel.angelhack16_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultListActivity extends AppCompatActivity {

    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);

        String url = "http://192.168.1.94:8080/api/book/find";

        // get a JSON from server
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        List<Map<String, String>> stringList = new ArrayList<>();
                        Map<String, String> map1 = new HashMap<>();
                        map1.put("bookName", "bookName");
                        map1.put("author", "author");
                        map1.put("userName", "userName");
                        stringList.add(map1);

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
