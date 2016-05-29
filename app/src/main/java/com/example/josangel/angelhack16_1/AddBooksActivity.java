package com.example.josangel.angelhack16_1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.josangel.angelhack16_1.service.HttpService;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class AddBooksActivity extends AppCompatActivity {

    private Button submitButton;
    private EditText bookName;
    private EditText bookAuthor;
    private EditText bookPublisher;
    private EditText bookCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_books);

        submitButton = (Button) findViewById(R.id.addBookSubmit);
        bookName = (EditText) findViewById(R.id.add_book_name);
        bookAuthor = (EditText) findViewById(R.id.add_book_author);
        bookPublisher = (EditText) findViewById(R.id.add_book_publisher);
        bookCost = (EditText) findViewById(R.id.add_book_cost);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bookNameStr = bookName.getText().toString();
                String bookAuthroStr = bookAuthor.getText().toString();
                String bookPublisherStr = bookPublisher.getText().toString();
                String bookCostStr = bookCost.getText().toString();

                AsyncTask async = new HttpService(AddBooksActivity.this).execute("book/add", bookNameStr, bookAuthroStr,
                        bookPublisherStr, bookCostStr);

                try {
                    String returnStr = (String) async.get();

                    Toast.makeText(AddBooksActivity.this, returnStr, Toast.LENGTH_SHORT).show();

                    if(returnStr.equals("SUCCESS")){

                        finish();
                    } else {

                        // Display error saving book
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


            }
        });

    }
}
