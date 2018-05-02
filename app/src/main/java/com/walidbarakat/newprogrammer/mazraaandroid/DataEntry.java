package com.walidbarakat.newprogrammer.mazraaandroid;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;


public class DataEntry extends AppCompatActivity {
    public static int NotificationId;
    EditText txtmother, txtmale, txtbaby, txtcode, txtpartition;
    Spinner spnrmotherfollw, spnrgender, spnrdays, spnrendreason;
    Button btnpick, btnpick2, btnpick3, btnpick4, btnpick5, btnpick6, btnpick7, btnpick8;
    TextView txtdate, txttime, txtdate2, txttime2, txtdate3, txttime3, txtdate4, txttime4, txtshowdate, txtlastBirthDate;
    TextView lblmother, lblbabynum;
    RadioButton rbmother, rbbaby;
    CheckBox chkendoption;
    ImageView addreason;

    SimpleDateFormat simpleDateFormat;
    String time;
    Calendar calander;

    Calendar datetime = Calendar.getInstance();

    DB_Sqlite db = new DB_Sqlite(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);

        MobileAds.initialize(this, "ca-app-pub-3335691441959472~5805522616");

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3335691441959472/2656615097");


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        txtmother = (EditText) findViewById(R.id.TxtMother);
        txtbaby = (EditText) findViewById(R.id.TxtBaby);
        txtmale = (EditText) findViewById(R.id.TxtDady);
        txtcode = (EditText) findViewById(R.id.TxtCode);
        txtpartition = (EditText) findViewById(R.id.TxtPartition);

        spnrmotherfollw = (Spinner) findViewById(R.id.SpnrMotherFollow);
        spnrgender = (Spinner) findViewById(R.id.SpnrGender);
        spnrdays = (Spinner) findViewById(R.id.SpnrDays);
        spnrendreason = (Spinner) findViewById(R.id.SpnrEndReason);

        btnpick = (Button) findViewById(R.id.BtnPick);
        btnpick2 = (Button) findViewById(R.id.BtnPick2);
        btnpick3 = (Button) findViewById(R.id.BtnPick3);
        btnpick4 = (Button) findViewById(R.id.BtnPick4);
        btnpick5 = (Button) findViewById(R.id.BtnPick5);
        btnpick6 = (Button) findViewById(R.id.BtnPick6);
        btnpick7 = (Button) findViewById(R.id.BtnPick7);
        btnpick8 = (Button) findViewById(R.id.BtnPick8);

        txtdate = (TextView) findViewById(R.id.TxtDate);
        txttime = (TextView) findViewById(R.id.TxtTime);
        txtdate2 = (TextView) findViewById(R.id.TxtDate2);
        txttime2 = (TextView) findViewById(R.id.TxtTime2);
        txtdate3 = (TextView) findViewById(R.id.TxtDate3);
        txttime3 = (TextView) findViewById(R.id.TxtTime3);
        txtdate4 = (TextView) findViewById(R.id.TxtDate4);
        txttime4 = (TextView) findViewById(R.id.TxtTime4);
        txtshowdate = (TextView) findViewById(R.id.Txtshowdate);
        txtlastBirthDate = (TextView) findViewById(R.id.TxtlastBirthDate);
        lblmother = (TextView) findViewById(R.id.LblMother);
        lblbabynum = (TextView) findViewById(R.id.LblBabyNum);

        rbmother = (RadioButton) findViewById(R.id.RBMother);
        rbbaby = (RadioButton) findViewById(R.id.RBBaby);

        addreason = (ImageView) findViewById(R.id.AddReason);

        chkendoption = (CheckBox) findViewById(R.id.ChkEndOption);


        rbbaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clear();
            }
        });

        rbmother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clear();
            }
        });

        addreason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DataEntry.this);

                builder.setTitle("ادخل سبب العزل");

                final EditText input = new EditText(DataEntry.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        InputMethodManager inputMgr = (InputMethodManager) v.getContext().
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                    }
                });

                builder.setPositiveButton("اضافه", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List ls = new ArrayList();
                        for (int i = 0; i < spnrendreason.getAdapter().getCount(); i++) {
                            ls.add(spnrendreason.getItemAtPosition(i));
                        }
                        ls.add(input.getText().toString().trim());
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DataEntry.this,
                                android.R.layout.simple_list_item_1, ls);
                        //adapter.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
                        spnrendreason.setAdapter(adapter);

                        String valu2 = input.getText().toString().trim();
                        for (int a = 0; a < spnrendreason.getAdapter().getCount(); a++) {
                            if (spnrendreason.getItemAtPosition(a).equals(valu2)) {
                                spnrendreason.setSelection(a);
                                break;
                            }
                        }
                    }
                });
                builder.setNegativeButton("اغلاق", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });

        Clear();
    }


    public void Clear() {
        txtcode.setEnabled(false);
        chkendoption.setSelected(false);
        txtmother.setText("");
        txtpartition.setText("");
        txtmale.setText("");
        lblbabynum.setText("");
        lblmother.setText("");

        txtbaby.setText("");

        calander = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm a");

        time = simpleDateFormat.format(calander.getTime());

        txttime.setText(time);
        txttime2.setText(time);
        txttime3.setText(time);
        txttime4.setText(time);

        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        time = simpleDateFormat.format(calander.getTime());

        txtdate.setText(time);
        txtdate2.setText(time);
        txtdate3.setText(time);
        txtdate4.setText(time);

        List ls = new ArrayList();
        ls.clear();
        for (int x = 1; x < 100; x++) {
            ls.add(x);
        }
        ArrayAdapter arradb = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ls);
        spnrdays.setAdapter(arradb);


        try {
            int check = db.get_check_AutoAllnames();
            txtcode.setText(String.valueOf(check));

            db.openDatabase();
            Cursor Cr = db.mDatabase.rawQuery("select * from lastmotherdata where tablename='allnames'", null);
            Cr.moveToFirst();
            int count = Cr.getCount();
            if (count > 0) {
                txtmale.setText(Cr.getString(0));
                txtpartition.setText(Cr.getString(1));
            } else {
                txtmale.setText("");
                txtpartition.setText("");
            }
            Cr.close();

            ArrayList<String> data = new ArrayList<>();
            data.clear();
            Cr = db.mDatabase.rawQuery("select distinct(mothernum) from allnames where mothernum<>'' and mothernum is not null and mothernum<>'MotherNum'", null);
            Cr.moveToFirst();
            while (!Cr.isAfterLast()) {
                String id = Cr.getString(Cr.getColumnIndex("MotherNum"));
                data.add(id);
                Cr.moveToNext();
            }
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
            spnrmotherfollw.setAdapter(NoCoreAdapter);
            Cr.close();

            ArrayList<String> data2 = new ArrayList<>();
            data2.clear();
            Cr = db.mDatabase.rawQuery("select distinct(deletereason) from DeleteHistory where deletereason<>''", null);
            Cr.moveToFirst();
            while (!Cr.isAfterLast()) {
                String id = Cr.getString(Cr.getColumnIndex("DeleteReason"));
                data2.add(id);
                Cr.moveToNext();
            }
            ArrayAdapter NoCoreAdapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data2);
            spnrendreason.setAdapter(NoCoreAdapter2);
            Cr.close();

            db.closeDatabase();
        } catch (Exception k) {
            Log.e("", k.getMessage());
        }

        txtmother.requestFocus();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dataentrymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.BtnNew) {
            Clear();
        } else if (id == R.id.BtnSearch) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            if (rbmother.isChecked()) {
                builder.setTitle("اكتب رقم الام");
            } else if (rbbaby.isChecked()) {
                builder.setTitle("اكتب رقم المولود");
            }

// Set up the input
            final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            if (rbmother.isChecked()) {
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else if (rbbaby.isChecked()) {
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            }
            builder.setView(input);

            input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    InputMethodManager inputMgr = (InputMethodManager) v.getContext().
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                }
            });


// Set up the buttons
            builder.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (rbmother.isChecked()) {

                        try {
                            db.openDatabase();
                            Cursor Cr = db.mDatabase.rawQuery("select * from allnames where mothernum='" + input.getText().toString().trim() + "' and mothernum<>''", null);
                            Cr.moveToFirst();
                            int count = Cr.getCount();
                            if (count > 0) {

                                txtmale.setText(Cr.getString(Cr.getColumnIndex("Dady")));
                                txtpartition.setText(Cr.getString(Cr.getColumnIndex("MPartition")));
                                txtcode.setText(Cr.getString(Cr.getColumnIndex("Autonum")));
                                txtmother.setText(Cr.getString(Cr.getColumnIndex("MotherNum")));
                                lblmother.setText(Cr.getString(Cr.getColumnIndex("MotherNum")));

                                txtshowdate.setText(String.valueOf(Cr.getString(Cr.getColumnIndex("CreateDate"))));
                                String mysourcestring = txtshowdate.getText().toString();
                                String year = mysourcestring.substring(0, 4);
                                String month = mysourcestring.substring(5, 7);
                                String day = mysourcestring.substring(8, 10);
                                txtdate3.setText(day + "/" + month + "/" + year);

                                String hour = mysourcestring.substring(11, 13);
                                String minute = mysourcestring.substring(14, 16);
                                if (Integer.parseInt(hour) > 12) {
                                    int total = Integer.parseInt(hour) - 12;
                                    if (String.valueOf(total).length() == 1) {
                                        txttime3.setText("0" + total + ":" + minute + " " + "PM");
                                    } else {
                                        txttime3.setText(total + ":" + minute + " " + "PM");
                                    }
                                } else {
                                    txttime3.setText(hour + ":" + minute + " " + "AM");
                                }

                            } else {
                                Toast.makeText(DataEntry.this, "لا يوجد بيانات ام بهذا الرقم", Toast.LENGTH_SHORT).show();
                                txtcode.setText("");
                                txtmother.setText("");
                                lblmother.setText("");
                                txtmale.setText("");
                                txtpartition.setText("");

                                calander = Calendar.getInstance();
                                simpleDateFormat = new SimpleDateFormat("hh:mm a");
                                time = simpleDateFormat.format(calander.getTime());
                                txttime3.setText(time);
                                simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                time = simpleDateFormat.format(calander.getTime());
                                txtdate3.setText(time);
                                Clear();
                            }
                            Cr.close();
                            db.closeDatabase();
                        } catch (Exception l) {
                            Log.e("", l.getMessage());
                        }


                    } else if (rbbaby.isChecked()) {

                        db.openDatabase();
                        Cursor Cr = db.mDatabase.rawQuery("select * from allnames where babynum='" + input.getText().toString().trim() + "' and babynum<>'' and tomother<>''", null);
                        Cr.moveToFirst();
                        int count = Cr.getCount();
                        if (count > 0) {
                            txtcode.setText(Cr.getString(Cr.getColumnIndex("Autonum")));
                            txtbaby.setText(Cr.getString(Cr.getColumnIndex("BabyNum")));
                            lblbabynum.setText(Cr.getString(Cr.getColumnIndex("BabyNum")));
                            String valu2 = Cr.getString(Cr.getColumnIndex("Gender"));
                            for (int a = 0; a < spnrgender.getAdapter().getCount(); a++) {
                                if (spnrgender.getItemAtPosition(a).equals(valu2)) {
                                    spnrgender.setSelection(a);
                                    break;
                                }
                            }

                            String valu1 = Cr.getString(Cr.getColumnIndex("Tomother"));
                            for (int a = 0; a < spnrmotherfollw.getAdapter().getCount(); a++) {
                                if (spnrmotherfollw.getItemAtPosition(a).equals(valu1)) {
                                    spnrmotherfollw.setSelection(a);
                                    break;
                                }
                            }

                            //--------------------------------------------------
                            txtshowdate.setText(String.valueOf(Cr.getString(Cr.getColumnIndex("BirthDate"))));
                            String mysourcestring = txtshowdate.getText().toString();
                            String year = mysourcestring.substring(0, 4);
                            String month = mysourcestring.substring(5, 7);
                            String day = mysourcestring.substring(8, 10);
                            txtdate2.setText(day + "/" + month + "/" + year);

                            String hour = mysourcestring.substring(11, 13);
                            String minute = mysourcestring.substring(14, 16);
                            if (Integer.parseInt(hour) > 12) {
                                int total = Integer.parseInt(hour) - 12;
                                if (String.valueOf(total).length() == 1) {
                                    txttime2.setText("0" + total + ":" + minute + " " + "PM");
                                } else {
                                    txttime2.setText(total + ":" + minute + " " + "PM");
                                }
                            } else {
                                txttime2.setText(hour + ":" + minute + " " + "AM");
                            }
                            //--------------------------------------------------
                            txtlastBirthDate.setText(txtdate2.getText().toString().trim() + " " + txttime2.getText().toString().trim());
                            //--------------------------------------------------
                            txtshowdate.setText(String.valueOf(Cr.getString(Cr.getColumnIndex("EndDate"))));
                            mysourcestring = txtshowdate.getText().toString();
                            year = mysourcestring.substring(0, 4);
                            month = mysourcestring.substring(5, 7);
                            day = mysourcestring.substring(8, 10);
                            txtdate.setText(day + "/" + month + "/" + year);

                            hour = mysourcestring.substring(11, 13);
                            minute = mysourcestring.substring(14, 16);
                            if (Integer.parseInt(hour) > 12) {
                                int total = Integer.parseInt(hour) - 12;
                                if (String.valueOf(total).length() == 1) {
                                    txttime.setText("0" + total + ":" + minute + " " + "PM");
                                } else {
                                    txttime.setText(total + ":" + minute + " " + "PM");
                                }
                            } else {
                                txttime.setText(hour + ":" + minute + " " + "AM");
                            }

                        } else {
                            Toast.makeText(DataEntry.this, "لا يوجد بيانات مولود بهذا الرقم", Toast.LENGTH_SHORT).show();
                            txtcode.setText("");
                            txtbaby.setText("");
                            lblbabynum.setText("");
                            spnrmotherfollw.setPrompt("");
                            spnrgender.setPrompt("");

                            calander = Calendar.getInstance();
                            simpleDateFormat = new SimpleDateFormat("hh:mm a");
                            time = simpleDateFormat.format(calander.getTime());
                            txttime.setText(time);
                            txttime2.setText(time);
                            simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            time = simpleDateFormat.format(calander.getTime());
                            txtdate.setText(time);
                            txtdate2.setText(time);
                            Clear();
                        }
                        Cr.close();
                        db.closeDatabase();
                    }

                }
            });
            builder.setNegativeButton("اغلاق", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
            //-----------------------------------------------------------------------------
        } else if (id == R.id.BtnSave) {
            if (rbmother.isChecked()) {
                if (txtcode.getText().toString().trim().equals("") || (txtmother.getText().toString().trim().equals(""))) {
                    Toast.makeText(this, "من فضلك ادخل بيانات الام", Toast.LENGTH_SHORT).show();
                    return super.onOptionsItemSelected(item);
                }

                try {
                    db.openDatabase();
                    //db.DeleteAllnames("1");
                    Cursor Cr = db.mDatabase.rawQuery("select * from allnames where autonum='" + txtcode.getText().toString().trim() + "'", null);
                    Cr.moveToFirst();
                    int count = Cr.getCount();
                    if (count > 0) {
                        lblmother.setText(Cr.getString(Cr.getColumnIndex("MotherNum")));
                        if (lblmother.getText().toString().trim().equals(txtmother.getText().toString().trim())) {
                            String Malenum, Partition, Autonum, TableName;
                            Autonum = txtcode.getText().toString().trim();
                            Malenum = txtmale.getText().toString().trim();
                            Partition = txtpartition.getText().toString().trim();
                            TableName = "allnames";

                            Boolean result = db.updateallname(Autonum, Partition, Malenum);
                            if (result == true) {

                            } else {

                            }

                            result = db.updatelastmotherdata(TableName, Partition, Malenum);
                            if (result == true) {
                                Toast.makeText(this, "تم تعديل بيانات الام بنجاح", Toast.LENGTH_SHORT).show();
                                Clear();
                            } else {
                                Toast.makeText(this, "لم يتم تعديل بيانات الام بنجاح", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Cr = db.mDatabase.rawQuery("select * from allnames where mothernum='" + txtmother.getText().toString().trim() + "'", null);
                            Cr.moveToFirst();
                            count = Cr.getCount();
                            if (count > 0) {
                                Toast.makeText(this, "لا يجوز تكرار رقم الام", Toast.LENGTH_SHORT).show();
                                return super.onOptionsItemSelected(item);
                            } else {
                                String Autonum, Mothernum, Malenum, Partition, TableName;
                                Mothernum = txtmother.getText().toString().trim();
                                Malenum = txtmale.getText().toString().trim();
                                Partition = txtpartition.getText().toString().trim();
                                Autonum = txtcode.getText().toString().trim();
                                TableName = "allnames";

                                Boolean result = db.updateallnamemother(Autonum, Mothernum, Partition, Malenum);
                                if (result == true) {

                                } else {

                                }

                                result = db.updatelastmotherdata(TableName, Partition, Malenum);
                                if (result == true) {
                                    Toast.makeText(this, "تم تعديل بيانات الام بنجاح", Toast.LENGTH_SHORT).show();
                                    Clear();
                                } else {
                                    Toast.makeText(this, "لم يتم تعديل بيانات الام بنجاح", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }


                    } else {
                        Cr = db.mDatabase.rawQuery("select * from allnames where mothernum='" + txtmother.getText().toString().trim() + "'", null);
                        Cr.moveToFirst();
                        count = Cr.getCount();
                        if (count > 0) {
                            Toast.makeText(DataEntry.this, "لا يجوز تكرار رقم الام", Toast.LENGTH_SHORT).show();
                            return super.onOptionsItemSelected(item);
                        } else {
                            String Autonum, Mothernum, Malenum, Username, CreateDate, Partition, Tablename;
                            Username = MainActivity.GeneralUser.trim();
                            Autonum = txtcode.getText().toString().trim();
                            Mothernum = txtmother.getText().toString().trim();
                            Partition = txtpartition.getText().toString().trim();
                            Tablename = "allnames";
                            String string_date = txtdate3.getText().toString().trim() + " " + txttime3.getText().toString().trim();

                            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                            long milliseconds = 0;
                            try {
                                Date d = f.parse(string_date);
                                milliseconds = d.getTime();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            Timestamp timestamp = new Timestamp(milliseconds);
                            CreateDate = String.valueOf(timestamp);
                            Malenum = txtmale.getText().toString().trim();
                            db.Insert_to_Mother(CreateDate, Username, Autonum, Mothernum, Partition, Malenum);

                            db.Deletelastmotherdata(Tablename);

                            db.Insert_to_lastmotherdata(Tablename, Partition, Malenum);

                            db.Insert_to_autoname(Autonum);

                            Toast.makeText(DataEntry.this, "تم حفظ بيانات الام بنجاح", Toast.LENGTH_SHORT).show();
                            Clear();
                        }
                    }
                    Cr.close();
                    db.closeDatabase();
                } catch (Exception f) {
                    Log.e("", f.getMessage());
                }

//--------------------------------------------------------------------------------------------------
// --------------------------------------------------------------------------------------------------
            } else if (rbbaby.isChecked()) {
                try {
                    if (spnrgender.getSelectedItem().toString().equals("انثى")) {
                        String character = "+";
                        String text = txtbaby.getText().toString();
                        if (text.contains(character) == true) {

                        } else {
                            txtbaby.setText(txtbaby.getText().toString().trim() + "+");
                        }
                    } else if (spnrgender.getSelectedItem().toString().equals("ذكر")) {
                        String character = "+";
                        String text = txtbaby.getText().toString();
                        if (text.contains(character) == true) {
                            Toast.makeText(DataEntry.this, "المولود نوعه ذكر لا يجوز اضافة علامة +", Toast.LENGTH_SHORT).show();
                            return super.onOptionsItemSelected(item);
                        }
                    }

                    if (chkendoption.isChecked()) {
                        String oldDate = txtdate2.getText().toString().trim();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar c = Calendar.getInstance();
                        try {
                            c.setTime(sdf.parse(oldDate));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(spnrdays.getSelectedItem().toString()));
                        String newDate = sdf.format(c.getTime());
                        txtdate.setText(newDate);
                    } else {
                        if (spnrgender.getSelectedItem().toString().equals("ذكر")) {
                            String oldDate = txtdate2.getText().toString().trim();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            Calendar c = Calendar.getInstance();
                            try {
                                c.setTime(sdf.parse(oldDate));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            c.add(Calendar.DAY_OF_MONTH, 60);
                            String newDate = sdf.format(c.getTime());
                            txtdate.setText(newDate);
                        } else if (spnrgender.getSelectedItem().toString().equals("انثى")) {
                            String oldDate = txtdate2.getText().toString().trim();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            Calendar c = Calendar.getInstance();
                            try {
                                c.setTime(sdf.parse(oldDate));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            c.add(Calendar.DAY_OF_MONTH, 80);
                            String newDate = sdf.format(c.getTime());
                            txtdate.setText(newDate);
                        }
                    }
                    if (spnrgender.getSelectedItem().toString().equals("") || txtcode.getText().toString().trim().equals("") || txtbaby.getText().toString().trim().equals("") || spnrdays.getSelectedItem().toString().trim().equals("") || spnrmotherfollw.getSelectedItem().toString().trim().equals("")) {
                        Toast.makeText(DataEntry.this, "من فضلك ادخل بيانات المولود", Toast.LENGTH_SHORT).show();
                        return super.onOptionsItemSelected(item);
                    }


                    db.openDatabase();
                    Cursor Cr = db.mDatabase.rawQuery("select * from allnames where autonum='" + txtcode.getText().toString().trim() + "'", null);
                    Cr.moveToFirst();
                    int count = Cr.getCount();
                    if (count > 0) {
                        lblbabynum.setText(Cr.getString(Cr.getColumnIndex("BabyNum")));
                        if (lblbabynum.getText().toString().trim().equals(txtbaby.getText().toString().trim())) {
                            String ToMother, Gender, Birthdate, BirthDate2, EndDate, Autonum, Autonum2;
                            //--------------------------------------------------------
                            Autonum = txtcode.getText().toString().trim();
                            String string_date = txtdate2.getText().toString().trim() + " " + txttime2.getText().toString().trim();

                            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                            long milliseconds = 0;
                            try {
                                Date d = f.parse(string_date);
                                milliseconds = d.getTime();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            Timestamp timestamp = new Timestamp(milliseconds);
                            Birthdate = String.valueOf(timestamp);
//--------------------------------------------------------
                            String string_date2 = txtdate.getText().toString().trim() + " " + txttime.getText().toString().trim();

                            SimpleDateFormat f2 = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                            long milliseconds2 = 0;
                            int RequestCode = 0;
                            try {
                                Date d2 = f2.parse(string_date2);
                                milliseconds2 = d2.getTime();
                                RequestCode = (int) (long) milliseconds2;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            Timestamp timestamp2 = new Timestamp(milliseconds2);
                            EndDate = String.valueOf(timestamp2);

                            Autonum2 = null;

                            BirthDate2 = txtlastBirthDate.getText().toString().trim();
//--------------------------------------------------------
                            ToMother = spnrmotherfollw.getSelectedItem().toString().trim();
                            Gender = spnrgender.getSelectedItem().toString().trim();

                            db.updateallnameBaby(ToMother, Gender, Birthdate, EndDate, Autonum);

                            db.updateallnameBaby2(ToMother, Gender, Birthdate, BirthDate2, Autonum2);

                            Toast.makeText(DataEntry.this, "تم تعديل بيانات المولود بنجاح", Toast.LENGTH_SHORT).show();

                            EndNotification();

                            Intent alertintent = new Intent(getApplicationContext(), MyReceiver.class);
                            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                            alarmManager.cancel(PendingIntent.getBroadcast(getApplicationContext(), RequestCode, alertintent, PendingIntent.FLAG_UPDATE_CURRENT));

                            Clear();
                        } else {
                            Cr = db.mDatabase.rawQuery("select * from allnames where babynum='" + txtbaby.getText().toString().trim() + "' and mothernum is null", null);
                            Cr.moveToFirst();
                            count = Cr.getCount();
                            if (count > 0) {
                                Toast.makeText(DataEntry.this, "لا يجوز تكرار رقم المولود", Toast.LENGTH_SHORT).show();
                                return super.onOptionsItemSelected(item);
                            } else {
                                String Babynum, ToMother, Gender, Birthdate, BirthDate2, EndDate, Autonum, Autonum2;
                                String string_date = txtdate2.getText().toString().trim() + " " + txttime2.getText().toString().trim();

                                SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                                long milliseconds = 0;
                                try {
                                    Date d = f.parse(string_date);
                                    milliseconds = d.getTime();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                Timestamp timestamp = new Timestamp(milliseconds);
                                Birthdate = String.valueOf(timestamp);

                                String string_date2 = txtdate.getText().toString().trim() + " " + txttime.getText().toString().trim();

                                SimpleDateFormat f2 = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                                long milliseconds2 = 0;

                                int RequestCode = 0;

                                try {
                                    Date d2 = f2.parse(string_date2);
                                    milliseconds2 = d2.getTime();
                                    RequestCode = (int) (long) milliseconds2;
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                Timestamp timestamp2 = new Timestamp(milliseconds2);
                                EndDate = String.valueOf(timestamp2);

                                BirthDate2 = txtlastBirthDate.getText().toString().trim();

                                Babynum = txtbaby.getText().toString().trim();

                                Autonum = txtcode.getText().toString().trim();
                                Autonum2 = null;
                                ToMother = spnrmotherfollw.getSelectedItem().toString().trim();
                                Gender = spnrgender.getSelectedItem().toString().trim();

                                db.updateallnameBaby3(Babynum, ToMother, Gender, Birthdate, EndDate, Autonum);

                                db.updateallnameBaby4(Babynum, ToMother, Gender, Birthdate, BirthDate2, Autonum2);

                                Toast.makeText(DataEntry.this, "تم تعديل بيانات المولود بنجاح", Toast.LENGTH_SHORT).show();

                                EndNotification();

                                Intent alertintent = new Intent(getApplicationContext(), MyReceiver.class);
                                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                                alarmManager.cancel(PendingIntent.getBroadcast(getApplicationContext(), RequestCode, alertintent, PendingIntent.FLAG_UPDATE_CURRENT));

                                Clear();
                            }
                            Cr.close();
                        }

                    } else {
                        Cr = db.mDatabase.rawQuery("select * from allnames where babynum='" + txtbaby.getText().toString().trim() + "' and mothernum is null", null);
                        Cr.moveToFirst();
                        count = Cr.getCount();
                        if (count > 0) {
                            Toast.makeText(DataEntry.this, "لا يجوز تكرار رقم المولود", Toast.LENGTH_SHORT).show();
                            return super.onOptionsItemSelected(item);
                        } else {
                            String Autonum, Babynum, ToMother, Gender, Birthdate, EndDate, Username;
                            Username = MainActivity.GeneralUser.trim();
                            Autonum = txtcode.getText().toString().trim();
                            Babynum = txtbaby.getText().toString().trim();
                            ToMother = spnrmotherfollw.getSelectedItem().toString().trim();
                            Gender = spnrgender.getSelectedItem().toString().trim();

                            String string_date = txtdate2.getText().toString().trim() + " " + txttime2.getText().toString().trim();

                            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                            long milliseconds = 0;
                            try {
                                Date d = f.parse(string_date);
                                milliseconds = d.getTime();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            Timestamp timestamp = new Timestamp(milliseconds);
                            Birthdate = String.valueOf(timestamp);

                            String string_date2 = txtdate.getText().toString().trim() + " " + txttime.getText().toString().trim();

                            SimpleDateFormat f2 = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                            long milliseconds2 = 0;
                            try {
                                Date d2 = f2.parse(string_date2);
                                milliseconds2 = d2.getTime();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            Timestamp timestamp2 = new Timestamp(milliseconds2);
                            EndDate = String.valueOf(timestamp2);

                            db.Insert_to_Baby(Username, Autonum, Babynum, ToMother, Gender, EndDate, Birthdate);

                            db.Insert_to_Baby2(Username, ToMother, Gender, Babynum, Birthdate);

                            db.Insert_to_AutoAllnames(Autonum);

                            EndNotification();
                            //-----------------------------------------------------------------------------------

                            Toast.makeText(DataEntry.this, "تم اضافة بيانات المولود بنجاح", Toast.LENGTH_SHORT).show();
                            Clear();
                        }
                    }

                    db.closeDatabase();
                } catch (Exception h) {
                    Log.e("", h.getMessage());
                }
            }
        } else if (id == R.id.BtnDelete) {
            if (spnrendreason.getSelectedItem().toString().trim().equals("") || txtcode.getText().toString().trim().equals("")) {
                Toast.makeText(DataEntry.this, "من فضلك قم بالبحث اولا وادخل سبب العزل", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
            }


            db.openDatabase();
            Cursor Cr = db.mDatabase.rawQuery("select * from allnames where autonum='" + txtcode.getText().toString().trim() + "'", null);
            Cr.moveToFirst();
            int count = Cr.getCount();
            if (count > 0) {
                if (txtmother.toString().trim().equals("") & txtbaby.getText().toString().trim().equals("")) {
                    Toast.makeText(DataEntry.this, "لا يوجد بيانات ليتم العزل", Toast.LENGTH_SHORT).show();
                    return super.onOptionsItemSelected(item);
                }
            } else {
                Toast.makeText(DataEntry.this, "لا يوجد بيانات ليتم العزل", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
            }
            Cr.close();
            db.closeDatabase();


            if (rbmother.isChecked() == true) {

                try {
                    db.openDatabase();
                    String Mothernum, Deletedate, DeleteReason, Username;
                    Username = MainActivity.GeneralUser.trim();
                    DeleteReason = spnrendreason.getSelectedItem().toString().trim();
                    Cr = db.mDatabase.rawQuery("select * from allnames where autonum='" + txtcode.getText().toString().trim() + "' and mothernum<>''", null);
                    Cr.moveToFirst();
                    count = Cr.getCount();
                    if (count > 0) {
                        Mothernum = Cr.getString(Cr.getColumnIndex("MotherNum"));
                    } else {
                        return super.onOptionsItemSelected(item);
                    }
                    Cr.close();
                    String string_date = txtdate4.getText().toString().trim() + " " + txttime4.getText().toString().trim();

                    SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                    long milliseconds = 0;
                    try {
                        Date d = f.parse(string_date);
                        milliseconds = d.getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Timestamp timestamp = new Timestamp(milliseconds);
                    Deletedate = String.valueOf(timestamp);

                    db.Insert_to_DeleteHistory(Username, DeleteReason, Mothernum, Deletedate);

                    db.DeleteAllnamesM(txtmother.getText().toString().trim());

                    db.closeDatabase();
                } catch (Exception g) {
                    Log.e("", g.getMessage());
                }

                Toast.makeText(DataEntry.this, "تم عزل الام بنجاح", Toast.LENGTH_SHORT).show();

                Clear();

            } else if (rbbaby.isChecked() == true) {

                try {
                    db.openDatabase();
                    String Babynum, ToMother, Gender, DeleteReason, Deletedate, Username;
                    Username = MainActivity.GeneralUser.trim();
                    DeleteReason = spnrendreason.getSelectedItem().toString().trim();
                    Cr = db.mDatabase.rawQuery("select * from allnames where autonum='" + txtcode.getText().toString().trim() + "'", null);
                    Cr.moveToFirst();
                    count = Cr.getCount();
                    if (count > 0) {
                        Babynum = Cr.getString(Cr.getColumnIndex("BabyNum"));
                        ToMother = Cr.getString(Cr.getColumnIndex("Tomother"));

                        Gender = Cr.getString(Cr.getColumnIndex("Gender"));

                    } else {
                        return super.onOptionsItemSelected(item);
                    }
                    Cr.close();

                    String string_date = txtdate4.getText().toString().trim() + " " + txttime4.getText().toString().trim();

                    SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                    long milliseconds = 0;
                    try {
                        Date d = f.parse(string_date);
                        milliseconds = d.getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Timestamp timestamp = new Timestamp(milliseconds);
                    Deletedate = String.valueOf(timestamp);

                    db.Insert_to_DeleteHistoryB(Username, DeleteReason, Babynum, ToMother, Gender, Deletedate);

                    db.DeleteAllnamesB(Babynum);

                    db.closeDatabase();
                } catch (Exception j) {
                    Log.e("", j.getMessage());
                }


                Toast.makeText(DataEntry.this, "تم عزل المولود بنجاح", Toast.LENGTH_SHORT).show();

                Clear();
            }

        } else if (id == R.id.BtnBack) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void EndNotification() {
        //Notification------------------------------------------------------------
        try {
            String string_date = txtdate.getText().toString().trim();

            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

            long milliseconds = 0;

            Date d = f.parse(string_date);
            milliseconds = d.getTime();
//---------------------------------------------------------------------------------------------
            String string_date2 = txtdate.getText().toString().trim() + " " + txttime.getText().toString().trim();
            SimpleDateFormat f2 = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");

            long milliseconds2 = 0;

            Date d2 = f2.parse(string_date2);
            milliseconds2 = d2.getTime();
            NotificationId = (int) (long) milliseconds2;

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliseconds);
            calendar.set(Calendar.HOUR_OF_DAY, 22);
            calendar.set(Calendar.MINUTE, 20);
            calendar.set(Calendar.SECOND, 0);

            Intent notifyIntent = new Intent(DataEntry.this, MyReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast
                    (DataEntry.this, NotificationId, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
            //AlarmManager.INTERVAL_DAY,
        } catch (Exception k) {
            Log.e("", k.getMessage());
        }
    }

    //-------------------------------------------------------------------------------------------------
    String Choose = "";

    public void date2(View view) {
        Choose = "2";
        updatedate();
    }

    public void time2(View view) {
        Choose = "2";
        updatetime();
    }

    public void date3(View view) {
        Choose = "3";
        updatedate();
    }

    public void time3(View view) {
        Choose = "3";
        updatetime();
    }

    public void date4(View view) {
        Choose = "4";
        updatedate();
    }

    public void time4(View view) {
        Choose = "4";
        updatetime();
    }

    public void updatedate() {
        new DatePickerDialog(this, dte, datetime.get(Calendar.YEAR), datetime.get(Calendar.MONTH), datetime.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void updatetime() {
        new TimePickerDialog(this, tme, datetime.get(Calendar.HOUR_OF_DAY), datetime.get(Calendar.MINUTE), false).show();
    }

    DatePickerDialog.OnDateSetListener dte = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            datetime.set(Calendar.YEAR, year);
            datetime.set(Calendar.MONTH, month);
            datetime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

            if (Choose.equals("2")) {
                txtdate2.setText(simpleDateFormat.format(datetime.getTime()));
            } else if (Choose.equals("3")) {
                txtdate3.setText(simpleDateFormat.format(datetime.getTime()));
            } else if (Choose.equals("4")) {
                txtdate4.setText(simpleDateFormat.format(datetime.getTime()));
            }
        }
    };


    TimePickerDialog.OnTimeSetListener tme = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            datetime.set(Calendar.MINUTE, minute);

            simpleDateFormat = new SimpleDateFormat("hh:mm a");
            if (Choose.equals("2")) {
                txttime2.setText(simpleDateFormat.format(datetime.getTime()));
            } else if (Choose.equals("3")) {
                txttime3.setText(simpleDateFormat.format(datetime.getTime()));
            } else if (Choose.equals("4")) {
                txttime4.setText(simpleDateFormat.format(datetime.getTime()));
            }

        }
    };
}
