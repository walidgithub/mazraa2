package com.walidbarakat.newprogrammer.mazraaandroid;

import android.content.Intent;
import android.database.Cursor;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    TextView txtuser, txtpass;

    long time;

    public static String DBType;
    DB_Sqlite db = new DB_Sqlite(this);

    public static String GeneralUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtuser = (TextView) findViewById(R.id.TxtUser);
        txtpass = (TextView) findViewById(R.id.TxtPass);
        txtpass.setTransformationMethod(new UserActivity.AsteriskPasswordTransformationMethod());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        txtuser.requestFocus();
    }

    //---------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Users) {
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            startActivity(intent);
        } else if (id == R.id.BtnBack) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void Enter(View view) throws SQLException {

/*
   txtuser.setText("admin");
        txtpass.setText("admin");
 */


        String Usernam = txtuser.getText().toString();
        String Password = txtpass.getText().toString();

        if (Usernam.trim().equals("") || Password.trim().equals("")) {
            Toast.makeText(this, "الرجاء ادخال البيانات", Toast.LENGTH_SHORT).show();
            return;
        } else {

                try {
                    db.openDatabase();
                    Cursor Cr = db.mDatabase.rawQuery("select * from Users Where Username='" + Usernam + "'", null);
                    Cr.moveToFirst();
                    int count = Cr.getCount();
                    if (count > 0) {
                        Cr = db.mDatabase.rawQuery("select * from Users Where pass='" + Password + "'", null);
                        Cr.moveToFirst();
                        count = Cr.getCount();
                        if (count > 0) {
                            GeneralUser = Cr.getString(1);
                            Intent intent = new Intent(MainActivity.this, EnterActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "كلمة المرور خطأ", Toast.LENGTH_SHORT).show();
                            txtpass.setText("");
                            txtpass.requestFocus();
                            return;
                        }
                    } else {
                        Toast.makeText(this, "لا يوجد مستخدم بهذا الاسم", Toast.LENGTH_SHORT).show();
                        txtuser.setText("");
                        txtpass.setText("");
                        txtuser.requestFocus();
                        return;
                    }
                    Cr.close();
                    db.closeDatabase();
                } catch (Exception l) {
                    Log.e("", l.getMessage());
                }


        }


    }


    public void Exit(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        if (time + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "اضغط مره اخرى للخروج من البرنامج", Toast.LENGTH_SHORT).show();
        }
        time = System.currentTimeMillis();
    }
}
