package Adapters;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ConnectionManager.BitmapLruCache;
import ConnectionManager.ConstantsVolley;
import ConnectionManager.CustomVolleyRequestQueue;
import Dialogs.ShowDialog;
import Dialogs.ShowDialogAddItem;
import database.AdapterDB;
import modelo.Body;
import modelo.Item;
import modelo.ObjectRequest;
import modelo.Productos;
import test.com.mobileskilltest.ListProducts;
import test.com.mobileskilltest.R;

/**
 * Created by Anderdavid on 17/08/2016.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ProductViewHolder>{

    List<Productos> productos;
    Context context;
    ListProducts listProducts;
    public String newKey="";
    public String newValue="";

    AdapterDB db;



    private  final String TAG ="RVAdapter";

    public RVAdapter(List<Productos> productos,Context context,ListProducts listProducts){
        this.productos = productos;
        this.context =context;
        this.listProducts =listProducts;
    }


    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView txvName;
        TextView txvPrice;
        TextView txvQuantity;
        ImageButton add;

        Context ctx;

        public void setContext(Context ctx) {
            this.ctx =ctx;
        }

        NetworkImageView personPhoto;

        ProductViewHolder(View itemView) {
            super(itemView);

            cardView= (CardView)itemView.findViewById(R.id.cv);
            txvName = (TextView)itemView.findViewById(R.id.name);
            txvPrice = (TextView)itemView.findViewById(R.id.price);
            txvQuantity =(TextView)itemView.findViewById(R.id.quantity);
            add =(ImageButton)itemView.findViewById(R.id.add);

            personPhoto = (NetworkImageView)itemView.findViewById(R.id.image_producto);

            //personPhoto = (ImageView)itemView.findViewById(R.id.image_producto);
        }
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card, viewGroup, false);
        ProductViewHolder pvh = new ProductViewHolder(v);
        pvh.setContext(context);

        initDB();

        return pvh;
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder productViewHolder, final int position) {
        productViewHolder.txvName.setText(productos.get(position).getName());
        productViewHolder.txvQuantity.setText(productos.get(position).getQuantity());
        productViewHolder.txvPrice.setText(productos.get(position).getPrice());



        ImageLoader mImageLoader = CustomVolleyRequestQueue.getInstance(context).getImageLoader();
        mImageLoader.get(productos.get(position).getPhotoId(), ImageLoader.getImageListener(productViewHolder.personPhoto,
                        android.R.color.darker_gray, android.R.drawable.ic_dialog_alert));
        productViewHolder.personPhoto.setImageUrl(productos.get(position).getPhotoId(), mImageLoader);

        productViewHolder.add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Toast.makeText(context, "id " + position, Toast.LENGTH_LONG).show();


                AlertDialog.Builder builder = new AlertDialog.Builder(listProducts);

                LayoutInflater inflater = listProducts.getLayoutInflater();
                View vi = inflater.inflate(R.layout.add_item, null);
                builder.setView(vi);

                final EditText etKey = (EditText) vi.findViewById(R.id.item_key);
                final EditText etValue = (EditText) vi.findViewById(R.id.item_value);


                builder.setTitle("You want to add a new item?");


                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if (!etKey.getText().equals("") && !etValue.getText().equals("")) {

                            String objectId = productos.get(position).getObjectId();
                            Log.d(TAG, "objectId " + objectId);

                            addItem(etKey.getText().toString(), etValue.getText().toString(), objectId);
                        }

                    }
                })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


            }


        });
    }

    private void initDB() {

        db = new AdapterDB(context);
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void addItem(String key, String value,String objectId) {

        Item item = new Item(key, value);
        String sItem = item.getItems("");

        JSONObject jsonObjectItem = null;
        JSONObject rawPayLoad = null;

        String path = "/1/classes/products/"+objectId;

        try {
            jsonObjectItem = new JSONObject(sItem);
            Log.d(TAG, jsonObjectItem.toString());
            JSONObject objectRequest = new JSONObject();

            objectRequest.put("method", "PUT");
            objectRequest.put("path", path);
            objectRequest.put("body", jsonObjectItem);
            Log.d(TAG, objectRequest.toString());

            db.createItem(objectRequest.toString());

            Cursor cursor = db.fetchAllItems();
            cursor.moveToFirst();
            JSONArray request = new JSONArray();

            do {
                String mItemAux = cursor.getString(cursor.getColumnIndex(AdapterDB.KEY_ITEM));
                Log.d(TAG,"mItemAux "+mItemAux);
                JSONObject objectRequestAux = new JSONObject(mItemAux);

                request.put(objectRequestAux);
                Log.d(TAG, "request "+request.toString());
             }while(cursor.moveToNext());



            rawPayLoad = new JSONObject();
            rawPayLoad.put("requests", request);
            Log.d(TAG, rawPayLoad.toString());

            db.truncateTable(AdapterDB.DATABASE_TABLE_PUT_REQUEST);
            db.createRequest(rawPayLoad.toString());

        } catch (JSONException e) {
            e.printStackTrace();


        }
        //sendGetPutItems(ConstantsVolley.BATCH_URL, rawPayLoad.toString());

    }



    public void sendGetPutItems(String url,String rawPayLoad) {

        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url,rawPayLoad, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d(TAG,response.toString());

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

    public void senGetBitmap(String url, final ImageView imageView){


        final RequestQueue requestQueue = Volley.newRequestQueue(context);


        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                //Toast.makeText(context, "imagen descargada", Toast.LENGTH_LONG).show();

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.compress(Bitmap.CompressFormat.PNG, 30, out);
                Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
                imageView.setImageBitmap(decoded);
                Log.d(TAG,"imagen descargada");

            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.ARGB_8888, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                //Procesar el error de la manera oportuna
                Log.d(TAG, error.toString());
            }
        }
        );

        requestQueue.add(imageRequest);
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
