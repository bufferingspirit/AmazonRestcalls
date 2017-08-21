package com.example.admin.amazonrestcalls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.ItemAnimator itemAnimator;
    BookAdapter bookAdapter;
    ArrayList<Book> books = new ArrayList<>();

    BroadcastReceiver broadcastReceiver  =  new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            Book book = (Book) intent.getSerializableExtra("book");
            //Log.d("MainActivity", "onReceive: " + book.getPicture());
            books.add(book);
            bookAdapter.notifyDataSetChanged();
        }
    };;

    boolean can_load;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        can_load = false;

        recyclerView = (RecyclerView) findViewById(R.id.recView);
        layoutManager = new LinearLayoutManager(this);
        itemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);
        recyclerView.setLayoutManager(layoutManager);
        bookAdapter = new BookAdapter(books);

        recyclerView.setAdapter(bookAdapter);
        launchFetchService();

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("MY_ACTION.pictures_loaded");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    public void startSMS(View view){
        Intent intent = new Intent(this, SMSActivity.class);
        startActivity(intent);
    }


    public void launchFetchService(){
        Intent intent = new Intent(this, FetchIntentService.class);
        startService(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
