package com.example.harry_000.icshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

    public class BrandStoresActivity extends AppCompatActivity {
        private List<Store> Stores = new ArrayList<Store>();
        private MyDatabase db;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_brand_stores);

            Intent i= getIntent();
            String id = i.getStringExtra("brandID");
            String strBrand = i.getStringExtra("brandName");

            //Toast.makeText(getApplicationContext(), id,
            //    Toast.LENGTH_LONG).show();


            db = new MyDatabase(this);
            Stores = db.getStoresByBrand(id); // you would not typically call this on the main thread

            StoresListAdapter Adapter = new StoresListAdapter(this, Stores);

            ListView listView = (ListView) findViewById(android.R.id.list);
            listView.setAdapter(Adapter);

            TextView Brand = (TextView) this.findViewById(R.id.headertext);
            Brand.setText("Stores for:" + strBrand);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    // selected item
                    Store clickedObject = (Store) parent.getAdapter().getItem(position);

                    String strId = String.valueOf(clickedObject.getID());
                    // Launching new Activity on selecting single List Item
                    Intent i = new Intent(getApplicationContext(), StoreDetailActivity.class);
                    // sending data to new activity
                    i.putExtra("storeID", strId);
                    startActivity(i);

                }
            });


        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            db.close();
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_brand_stores, menu);
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
}
