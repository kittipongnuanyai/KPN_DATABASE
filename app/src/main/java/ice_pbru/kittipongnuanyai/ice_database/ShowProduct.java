package ice_pbru.kittipongnuanyai.ice_database;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class ShowProduct extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        listView  = (ListView) findViewById(R.id.listView);

        SynJSON synJSON = new SynJSON();
        synJSON.execute();



    }//Main method


    public class SynJSON extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... voids) {
            try {
                String strURL = "http://ice.pbru.ac.th/ice56/kittipong/php_get_foodtable.php";
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(strURL).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                Log.d("Kittipong -->", "doIn -->" + e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                Log.d("Kittipong -->", "Response-->" + s);
                JSONArray jsonArray = new JSONArray(s);

                String[] iconStrings = new String[jsonArray.length()];
                String[] tittleStrings = new String[jsonArray.length()];
                String[] priceStrings = new String[jsonArray.length()];


                for (int i = 0;i<jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    iconStrings[i] = jsonObject.getString("foodPic");
                    tittleStrings[i] = jsonObject.getString("foodName");
                    priceStrings[i] = jsonObject.getString("foodPrice");

                    Log.d("Kittipong -->", "foodPic -->" + iconStrings[i].toString());
                    Log.d("Kittipong -->", "foodName -->" + tittleStrings[i].toString());
                    Log.d("Kittipong -->", "foodPrice -->" + priceStrings[i].toString());
                }

                ProductAdapter productAdapter = new ProductAdapter(ShowProduct.this, iconStrings, tittleStrings, priceStrings);
                listView.setAdapter(productAdapter);




            } catch (Exception e) {
                Log.d("Kittipong --> ", "onPost --> " + e.toString());
            }

        }
    }


}//Maino class
