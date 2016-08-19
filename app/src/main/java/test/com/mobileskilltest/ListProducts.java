package test.com.mobileskilltest;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapters.RVAdapter;
import ConnectionManager.ConstantsVolley;
import database.AdapterDB;
import modelo.Productos;

/**
 * Created by Anderdavid on 16/08/2016.
 */
public class ListProducts extends AppCompatActivity{


    private final String TAG ="ListProducts";


    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    AdapterDB db;
    Cursor cursor;
    private List<Productos> products;

    Toolbar toolbar;
    RecyclerView listProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_productos);

        Log.d(TAG, "hello world lista");

        listProducts = (RecyclerView)findViewById(R.id.lista_productos);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new AdapterDB(this);
        initDB();


        listProducts.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        listProducts.setLayoutManager(mLayoutManager);

        products = new ArrayList<>();
        reload();


        RVAdapter adapter = new RVAdapter(products,getApplicationContext(),ListProducts.this);
        listProducts.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_refresh) {

            refresh();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*public void testSQLite(){


        Cursor cursor = db.fetchAllProducts();
        cursor.moveToFirst();

         while(cursor.moveToNext()) {
             String producto = cursor.getString(cursor.getColumnIndex(AdapterDB.KEY_PRODUCTO));
             Toast.makeText(getApplicationContext(), producto, Toast.LENGTH_LONG).show();
         }

    }*/

    private void initDB() {

        db = new AdapterDB(this);
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void refresh(){

        addItems();
        sendGetProducts(ConstantsVolley.BASE_URL_PRODUCTS);


    }

    public void addItems() {

        Cursor cursor = db.fetchAllRequest();
        if (cursor.moveToFirst()) {

            do {
                String request = cursor.getString(cursor.getColumnIndex(AdapterDB.KEY_REQUEST));
               // Toast.makeText(getApplicationContext(), request, Toast.LENGTH_LONG).show();
                Log.d(TAG, request);
                sendGetPutItems(ConstantsVolley.BATCH_URL,request);
            }while(cursor.moveToNext());
        }
    }

    public void sendGetPutItems(String url,String rawPayLoad) {

        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url,rawPayLoad, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d(TAG,response.toString());
                db.truncateTable(AdapterDB.DATABASE_TABLE_PUT_REQUEST);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG,error.toString());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("X-Parse-REST-API-Key", "bsiWfUOJ7vhiongFPQM9PTmeqCUoKJZM8HezfZGx");
                params.put("X-Parse-Application-Id", "UioTROrtK4RxrhKB7n2k2hOXEfTyScQNto9I0zTV");
                params.put("Content-Type","application/json");

                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);


    }

    public void sendGetProducts(String url) {

        db.truncateTable(AdapterDB.DATABASE_TABLE_PRODUCTS);

        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                //Toast.makeText(getApplicationContext(), "productos " + response.toString(), Toast.LENGTH_LONG).show(); //_deb;


                Log.d(TAG, response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArrayResults = jsonResponse.getJSONArray("results");
                    for(int i=0;i<=jsonArrayResults.length();i++){

                        JSONObject product = jsonArrayResults.getJSONObject(i);
                        Log.d(TAG, product.getString("name"));


                        db.createProduct(product.toString());

                        Intent intent =new Intent(ListProducts.this,ListProducts.class);
                        startActivity(intent);
                        finish();



                    }


                }catch (Exception e){

                }

                requestQueue.stop();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(getApplicationContext(), "error descargando productos", Toast.LENGTH_LONG).show();

                // new ShowDialog().setConfig( getSupportFragmentManager().beginTransaction(),"ERROR","CONNECTION ERROR");

                requestQueue.stop();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("X-Parse-REST-API-Key", "bsiWfUOJ7vhiongFPQM9PTmeqCUoKJZM8HezfZGx");
                params.put("X-Parse-Application-Id", "UioTROrtK4RxrhKB7n2k2hOXEfTyScQNto9I0zTV");

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    public void reload(){



        cursor = db.fetchAllProducts();
        cursor.moveToFirst();

        do{
            String producto = cursor.getString(cursor.getColumnIndex(AdapterDB.KEY_PRODUCTO));
            Log.d(TAG,producto);
             try {
                 JSONObject jSONProducto = new JSONObject(producto);
                 products.add(new Productos(
                         jSONProducto.getString(Productos.NAME),
                         jSONProducto.getString(Productos.QUANTITY),
                         jSONProducto.getString(Productos.PRICE),
                         jSONProducto.getString(Productos.IMAGE_URL),
                         jSONProducto.getString(Productos.OBJECT_ID)

                 ));
             }catch (Exception e){

             }
        }while (cursor.moveToNext());


    }




}
