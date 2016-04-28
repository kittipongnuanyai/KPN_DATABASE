package ice_pbru.kittipongnuanyai.ice_database;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    //Explicit
    public EditText usernameEditText, passwordEditText;
    public String usernameString, passwordString;
    private MySQLite mySQLite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     //bind widget
        bindwidget();

    //Request SQLite
        mySQLite = new MySQLite(this);

    //Synchronize mySQL to SQLite
        synAndDelete();





    } //Main Method

    private void synAndDelete() {
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabase.delete(MySQLite.user_table, null, null);

        MySynJSON mySynJSON = new MySynJSON();
        mySynJSON.execute();

    }

    public void clickRegis(View view) {
        startActivity(new Intent(MainActivity.this, SignUp.class));

    }

    public void testMyAlert(View view) {
        MyAlert myAlert = new MyAlert();
        myAlert.myDialog(this,"Alert","ทดสอบ");

    }

    public void clickSignIn(View view) {
        usernameString = usernameEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        if (chaekspace()) {
            //have space
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this,",มีช่องว่าง","กรุณาตรวจสอบช่องว่าง");
        } else {
            //no space
            checkUser();
        }



    }

    private void checkUser() {
        try {
            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name, MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE WHERE User = " + "'" + usernameString + "'", null);
            cursor.moveToFirst();
            String[] resultStrings = new String[cursor.getColumnCount()];
            for (int i = 0;i<cursor.getColumnCount();i++) {
                resultStrings[i] = cursor.getString(i);
            }
            cursor.close();

            //check password
            if (passwordString.equals(resultStrings[4])) {
                Toast.makeText(this, "ยินดีต้อนรับ" + resultStrings[1], Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, ShowProduct.class);
                intent.putExtra("Result", resultStrings);
                startActivity(intent);
                finish();
            } else {
                MyAlert myAlert = new MyAlert();
                myAlert.myDialog(this,"Password error", "Please check you password again");
            }

        } catch (Exception e) {

            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this,"User error", "ไม่มีชื่อผู้ใช้งานนี้ในระบบฐานข้อมูล");


        }


    }//Check user

    private boolean chaekspace() {
        return usernameString.equals("")||passwordString.equals("");

    }


    private void bindwidget() {

        usernameEditText = (EditText) findViewById(R.id.editText6);
        passwordEditText = (EditText) findViewById(R.id.editText7);


    }//bind widget

    public class MySynJSON extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                String strURL = "http://ice.pbru.ac.th/ICE56/kittipong/php_get_user.php";
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(strURL).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                Log.d("Kittipong", "doInBack --> " + e.toString());
                return null;
            }
          } //do in background

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Kittipong","strJSON --> " + s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i<jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String strName = jsonObject.getString(MySQLite.colum_Name);
                    String strSurname = jsonObject.getString(MySQLite.colum_Surname);
                    String strUser = jsonObject.getString(MySQLite.colum_User);
                    String strPassword = jsonObject.getString(MySQLite.colum_Password);
                    String strEmail = jsonObject.getString(MySQLite.colum_Email);
                    mySQLite.addNewUser(strName, strSurname, strUser, strPassword, strEmail);
                }
                Toast.makeText(MainActivity.this, "Synchronize mySQL to SQLite Finish", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Log.d("Kittipong", "onPost --> " + e.toString());
            }

        }//onPostExecute
    }






}//Main Class
