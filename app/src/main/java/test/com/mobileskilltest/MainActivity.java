package test.com.mobileskilltest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import ConnectionManager.ConstantsVolley;
import Dialogs.ShowDialog;
import database.AdapterDB;
import modelo.User;


public class MainActivity extends FragmentActivity {

    private final static String TAG = "MainActivity";

    User user;


    EditText username;
    EditText password;
    Button signIn;


    AdapterDB db;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDB();

        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);

        signIn = (Button) findViewById(R.id.login_sign_in);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String loginUsername = username.getText().toString().trim();
                String loginPassword = password.getText().toString().trim();

                if (loginUsername.equals("")) {

                    Toast.makeText(getApplicationContext(), "Your username is empty", Toast.LENGTH_LONG).show();
                } else if (loginPassword.equals("")) {
                    Toast.makeText(getApplicationContext(), "Your password is empty", Toast.LENGTH_LONG).show();
                } else {
                    user = new User(loginUsername, loginPassword);
                    //new testGet().setConfig(getApplicationContext(), user.getUrlRegister(ConstantsVolley.BASE_URL_LOGIN));
                    sendGetLogin(user.getUrlRegister(ConstantsVolley.BASE_URL_LOGIN));




                }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public void sendGetLogin(String url) {

        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                //Toast.makeText(getApplicationContext(), "hello volley " + response.toString(), Toast.LENGTH_LONG).show(); //_deb;

                sendGetProducts(ConstantsVolley.BASE_URL_PRODUCTS);
                db.truncateTable(AdapterDB.DATABASE_TABLE_PUT_REQUEST);
                db.truncateTable(AdapterDB.DATABASE_TABLE_ITEMS);

                requestQueue.stop();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                new ShowDialog().setConfig(getSupportFragmentManager().beginTransaction(), "ERROR", "CONNECTION ERROR");

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

    public void sendGetProducts(String url) {

        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                //Toast.makeText(getApplicationContext(), "productos " + response.toString(), Toast.LENGTH_LONG).show(); //_deb;
                db.truncateTable(AdapterDB.DATABASE_TABLE_PRODUCTS);

                Log.d(TAG, response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray  jsonArrayResults = jsonResponse.getJSONArray("results");
                    for(int i=0;i<=jsonArrayResults.length();i++){

                        JSONObject product = jsonArrayResults.getJSONObject(i);
                        Log.d(TAG, product.getString("name"));


                        db.createProduct(product.toString());

                        Intent intent = new Intent(MainActivity.this, ListProducts.class);
                        startActivity(intent);

                    }


                }catch (Exception e){

                }

                requestQueue.stop();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "error descargando productos", Toast.LENGTH_LONG).show();

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

    private void initDB() {

        db = new AdapterDB(this);
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://test.com.mobileskilltest/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://test.com.mobileskilltest/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }




   /* public void testSQLite(){

        db.createProduct("2014-12-26T22:10:49.303Z", "https://i.ytimg.com/vi/iui9zz4W7o4/maxresdefault.jpg", "MacBook Pro 15",
        "sSL5KReTWR", "$3,200", "258", "2016-03-07T22:57:44.044Z");

        Cursor cursor = db.fetchAllProducts("1");

        String createdAt = cursor.getString(cursor.getColumnIndex(AdapterDB.KEY_CREATED_AT));
        String imageURL =cursor.getString(cursor.getColumnIndex(AdapterDB.KEY_IMAGE_URL));
        String name = cursor.getString(cursor.getColumnIndex(AdapterDB.KEY_NAME));
        String objectId =cursor.getString(cursor.getColumnIndex(AdapterDB.KEY_OBJECT_ID));
        String price = cursor.getString(cursor.getColumnIndex(AdapterDB.KEY_PRICE));
        String quantity =cursor.getString(cursor.getColumnIndex(AdapterDB.KEY_QUANTITY));
        String updatedAt =cursor.getString(cursor.getColumnIndex(AdapterDB.KEY_UPDATE_AT));

        Toast.makeText(getApplicationContext(),createdAt +" "+imageURL+" "+name+" "+objectId+" "+price+" "+quantity+" "+updatedAt,Toast.LENGTH_LONG).show();

    }*/


}
