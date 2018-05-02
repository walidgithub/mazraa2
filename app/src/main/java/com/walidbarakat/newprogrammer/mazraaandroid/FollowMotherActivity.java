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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FollowMotherActivity extends AppCompatActivity {
    public static int TahsynNotificationId, MedicinNotificationId;
    Button btnpick, btnpick2, btnpick3, btnpick4, btnsrch;
    TextView txtdate, txttime, txtdate2, txttime2, txttestdate, txtshowdate;
    TextView lblcount1, lblcount2, lstallitem;
    EditText txtcode, txtdays, txtnotes;
    Spinner spnrtahsyn, spnrmedicin;
    ImageView btnaddtahsyn, btnaddmedicin, btnaddall, btndelall, btndelone, btnaddone;
    CheckBox chkremember;
    ListView lstall, lstchoose;

    SimpleDateFormat simpleDateFormat;
    String time;
    Calendar calander;

    public static String SrchQ = "";

    ArrayAdapter<Object> finaladapter;
    List ls2 = new ArrayList();

    DB_Sqlite db = new DB_Sqlite(this);

    Calendar datetime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_mother);

        MobileAds.initialize(this, "ca-app-pub-3335691441959472~5805522616");

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3335691441959472/2656615097");

        btnpick = (Button) findViewById(R.id.BtnPick);
        btnpick2 = (Button) findViewById(R.id.BtnPick2);
        btnpick3 = (Button) findViewById(R.id.BtnPick3);
        btnpick4 = (Button) findViewById(R.id.BtnPick4);
        btnsrch = (Button) findViewById(R.id.BtnSrch);

        txtdate = (TextView) findViewById(R.id.TxtDate);
        txttime = (TextView) findViewById(R.id.TxtTime);
        txtdate2 = (TextView) findViewById(R.id.TxtDate2);
        txttime2 = (TextView) findViewById(R.id.TxtTime2);
        txttestdate = (TextView) findViewById(R.id.TxtTestDate);
        txtshowdate = (TextView) findViewById(R.id.Txtshowdate);

        txtcode = (EditText) findViewById(R.id.TxtCode);
        txtdays = (EditText) findViewById(R.id.TxtDays);
        txtnotes = (EditText) findViewById(R.id.TxtNotes);

        spnrtahsyn = (Spinner) findViewById(R.id.SpnrTahsyn);
        spnrmedicin = (Spinner) findViewById(R.id.SpnrMedicin);

        btnaddtahsyn = (ImageView) findViewById(R.id.AddTahsyn);
        btnaddmedicin = (ImageView) findViewById(R.id.AddMedicin);
        btnaddall = (ImageView) findViewById(R.id.AddAll);
        btndelall = (ImageView) findViewById(R.id.DelAll);
        btndelone = (ImageView) findViewById(R.id.DelOne);
        btnaddone = (ImageView) findViewById(R.id.AddOne);

        chkremember = (CheckBox) findViewById(R.id.ChkRemeber);

        lstall = (ListView) findViewById(R.id.lstAll);

        lstchoose = (ListView) findViewById(R.id.lstChoosed);
        lstchoose.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        lblcount1 = (TextView) findViewById(R.id.LblCount1);
        lblcount2 = (TextView) findViewById(R.id.LblCount2);
        lstallitem = (TextView) findViewById(R.id.LstAllItem);

        Clear();

        if (SrchQ.equals("Yes")) {
            MotherSrchResult();
        } else {
            SrchQ = "No";
        }

        SrchQ = "No";

        btnaddall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db.openDatabase();
                    ArrayList<String> data = new ArrayList<>();
                    data.clear();
                    Cursor Cr = db.mDatabase.rawQuery("select distinct(MotherNum) from allnames where MotherNum<>'' and MotherNum is not null and mothernum<>'MotherNum'", null);
                    Cr.moveToFirst();
                    while (!Cr.isAfterLast()) {
                        String id = Cr.getString(Cr.getColumnIndex("MotherNum"));
                        data.add(id);
                        Cr.moveToNext();
                    }
                    ArrayAdapter NoCoreAdapter = new ArrayAdapter(FollowMotherActivity.this, android.R.layout.simple_list_item_1, data);
                    lstchoose.setAdapter(NoCoreAdapter);
                    Cr.close();

                    //-----------------------------------------------------
                    ls2.clear();
                    for (int i = 0; i < lstchoose.getAdapter().getCount(); i++) {
                        ls2.add(lstchoose.getItemAtPosition(i));
                    }

                    finaladapter = new ArrayAdapter<>(FollowMotherActivity.this,
                            android.R.layout.simple_list_item_multiple_choice, ls2);
                    lstchoose.setAdapter(finaladapter);
                    //----------------------------------------------------

                    lblcount2.setText(String.valueOf(lstchoose.getAdapter().getCount()));
                    db.closeDatabase();


                } catch (Exception g) {
                    Log.e("", g.getMessage());
                }
            }
        });

        btndelall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> data = new ArrayList<>();
                data.clear();
                ArrayAdapter NoCoreAdapter = new ArrayAdapter(FollowMotherActivity.this, android.R.layout.simple_list_item_1, data);
                lstchoose.setAdapter(NoCoreAdapter);
                lblcount2.setText(String.valueOf(lstchoose.getAdapter().getCount()));
            }
        });

        btnaddmedicin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FollowMotherActivity.this);
                builder.setTitle("ادخل اسم العلاج");

                final EditText input = new EditText(FollowMotherActivity.this);
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
                        for (int i = 0; i < spnrmedicin.getAdapter().getCount(); i++) {
                            ls.add(spnrmedicin.getItemAtPosition(i));
                        }
                        ls.add(input.getText().toString().trim());
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FollowMotherActivity.this,
                                android.R.layout.simple_list_item_1, ls);
                        //adapter.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
                        spnrmedicin.setAdapter(adapter);

                        String valu2 = input.getText().toString().trim();
                        for (int a = 0; a < spnrmedicin.getAdapter().getCount(); a++) {
                            if (spnrmedicin.getItemAtPosition(a).equals(valu2)) {
                                spnrmedicin.setSelection(a);
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

        btnaddtahsyn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FollowMotherActivity.this);
                builder.setTitle("ادخل اسم التحصين");

                final EditText input = new EditText(FollowMotherActivity.this);
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
                        for (int i = 0; i < spnrtahsyn.getAdapter().getCount(); i++) {
                            ls.add(spnrtahsyn.getItemAtPosition(i));
                        }
                        ls.add(input.getText().toString().trim());
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FollowMotherActivity.this,
                                android.R.layout.simple_list_item_1, ls);
                        //adapter.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
                        spnrtahsyn.setAdapter(adapter);

                        String valu2 = input.getText().toString().trim();
                        for (int a = 0; a < spnrtahsyn.getAdapter().getCount(); a++) {
                            if (spnrtahsyn.getItemAtPosition(a).equals(valu2)) {
                                spnrtahsyn.setSelection(a);
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

        btnaddone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lstallitem.getText().toString().trim().equals("")) {
                    return;
                }
                List ls = new ArrayList();
                for (int i = 0; i < lstchoose.getAdapter().getCount(); i++) {
                    ls.add(lstchoose.getItemAtPosition(i));
                }

                String valu2 = lstallitem.getText().toString().trim();
                for (int a = 0; a < ls.size(); a++) {
                    if (valu2.trim().toString().equals(ls.get(a).toString().trim())) {
                        return;
                    }
                }
                ls.add(valu2);
                ArrayAdapter<Integer> adapter2 = new ArrayAdapter<Integer>(FollowMotherActivity.this,
                        android.R.layout.simple_list_item_1, ls);
                lstchoose.setAdapter(adapter2);
                //-----------------------------------------------------
                ls2.clear();
                for (int i = 0; i < lstchoose.getAdapter().getCount(); i++) {
                    ls2.add(lstchoose.getItemAtPosition(i));
                }

                finaladapter = new ArrayAdapter<>(FollowMotherActivity.this,
                        android.R.layout.simple_list_item_multiple_choice, ls2);
                lstchoose.setAdapter(finaladapter);
                //----------------------------------------------------

                lblcount2.setText(String.valueOf(lstchoose.getAdapter().getCount()));
            }
        });
        //--------------------------------------------------------------------------------------

        //--------------------------------------------------------------------------------------
        btndelone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SparseBooleanArray checkedItemPositions = lstchoose.getCheckedItemPositions();
                    int itemCount = lstchoose.getCount();

                    for (int i = itemCount - 1; i >= 0; i--) {
                        if (checkedItemPositions.get(i)) {
                            finaladapter.remove(ls2.get(i));
                        }
                    }
                    checkedItemPositions.clear();
                    finaladapter.notifyDataSetChanged();

                    lblcount2.setText(String.valueOf(lstchoose.getAdapter().getCount()));
                } catch (Exception t) {
                    Log.e("", t.getMessage());
                }

            }
        });

        lstall.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lstallitem.setText(((TextView) view).getText());
            }
        });
    }

    public void Clear() {
        try {
            lstall.setOnTouchListener(new ListView.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            // Disallow ScrollView to intercept touch events.
                            v.getParent().requestDisallowInterceptTouchEvent(true);
                            break;

                        case MotionEvent.ACTION_UP:
                            // Allow ScrollView to intercept touch events.
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }

                    // Handle ListView touch events.
                    v.onTouchEvent(event);
                    return true;
                }
            });

            lstchoose.setOnTouchListener(new ListView.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            // Disallow ScrollView to intercept touch events.
                            v.getParent().requestDisallowInterceptTouchEvent(true);
                            break;

                        case MotionEvent.ACTION_UP:
                            // Allow ScrollView to intercept touch events.
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }

                    // Handle ListView touch events.
                    v.onTouchEvent(event);
                    return true;
                }
            });

            calander = Calendar.getInstance();
            simpleDateFormat = new SimpleDateFormat("hh:mm a");

            time = simpleDateFormat.format(calander.getTime());

            txttime.setText(time);
            txttime2.setText(time);

            simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

            time = simpleDateFormat.format(calander.getTime());

            txtdate.setText(time);
            txtdate2.setText(time);

            txtcode.setEnabled(false);
            chkremember.setChecked(false);
            txtdays.setText("");
            txtnotes.setText("");


            db.openDatabase();
            int check = db.get_check_AutoFollowAutonum();
            txtcode.setText(String.valueOf(check));

            ArrayList<String> data = new ArrayList<>();
            data.clear();
            Cursor Cr = db.mDatabase.rawQuery("select distinct(Tahsyn) from allnames where Tahsyn<>'' and MotherNum<>'' and Tahsyn<>'Tahsyn'", null);
            Cr.moveToFirst();
            while (!Cr.isAfterLast()) {
                String id = Cr.getString(Cr.getColumnIndex("Tahsyn"));
                data.add(id);
                Cr.moveToNext();
            }
            data.add("لا شىء");
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
            spnrtahsyn.setAdapter(NoCoreAdapter);
            Cr.close();
            String valu1 = "لا شىء";
            for (int a = 0; a < spnrtahsyn.getAdapter().getCount(); a++) {
                if (spnrtahsyn.getItemAtPosition(a).equals(valu1)) {
                    spnrtahsyn.setSelection(a);
                    break;
                }
            }

            ArrayList<String> data2 = new ArrayList<>();
            data2.clear();
            Cr = db.mDatabase.rawQuery("select distinct(Medicin) from allnames where Medicin<>'' and MotherNum<>'' and Medicin<>'Medicin'", null);
            Cr.moveToFirst();
            while (!Cr.isAfterLast()) {
                String id = Cr.getString(Cr.getColumnIndex("Medicin"));
                data2.add(id);
                Cr.moveToNext();
            }
            data2.add("لا شىء");
            ArrayAdapter NoCoreAdapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data2);
            spnrmedicin.setAdapter(NoCoreAdapter2);
            Cr.close();
            valu1 = "لا شىء";
            for (int a = 0; a < spnrmedicin.getAdapter().getCount(); a++) {
                if (spnrmedicin.getItemAtPosition(a).equals(valu1)) {
                    spnrmedicin.setSelection(a);
                    break;
                }
            }

            ArrayList<String> data3 = new ArrayList<>();
            data3.clear();
            Cr = db.mDatabase.rawQuery("select distinct(MotherNum) from allnames where MotherNum<>'' and mothernum<>'MotherNum'", null);
            Cr.moveToFirst();
            while (!Cr.isAfterLast()) {
                String id = Cr.getString(Cr.getColumnIndex("MotherNum"));
                data3.add(id);
                Cr.moveToNext();
            }
            ArrayAdapter NoCoreAdapter3 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data3);
            lstall.setAdapter(NoCoreAdapter3);
            Cr.close();

            lblcount1.setText(String.valueOf(lstall.getAdapter().getCount()));

            ArrayList<String> data4 = new ArrayList<>();
            data4.clear();
            ArrayAdapter NoCoreAdapter4 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data4);
            lstchoose.setAdapter(NoCoreAdapter4);
            lblcount2.setText(String.valueOf(lstchoose.getAdapter().getCount()));
            db.closeDatabase();

        } catch (Exception d) {
            Log.e("", d.getMessage());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.followmenu, menu);
        return true;
    }

    public void MotherSrchResult() {

        db.openDatabase();
        Cursor Cr = db.mDatabase.rawQuery("select * from allnames where FollowAutonum='" + getIntent().getStringExtra("MotherSrchNum") + "'", null);
        try {
            Cr.moveToFirst();
            int Count = Cr.getCount();
            if (Count > 0) {
                txtcode.setText(Cr.getString(Cr.getColumnIndex("FollowAutonum")));

                String valu2 = Cr.getString(Cr.getColumnIndex("Medicin"));
                for (int a = 0; a < spnrmedicin.getAdapter().getCount(); a++) {
                    if (spnrmedicin.getItemAtPosition(a).equals(valu2)) {
                        spnrmedicin.setSelection(a);
                        break;
                    }
                }

                String valu1 = Cr.getString(Cr.getColumnIndex("Tahsyn"));
                for (int a = 0; a < spnrtahsyn.getAdapter().getCount(); a++) {
                    if (spnrtahsyn.getItemAtPosition(a).equals(valu1)) {
                        spnrtahsyn.setSelection(a);
                        break;
                    }
                }
                //--------------------------------------------------
                //DtpMedicinDate.Value = Dr("MedicinDate")
                txtshowdate.setText(String.valueOf(Cr.getString(Cr.getColumnIndex("MedicinDate"))));
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
                //DtpTahsynDate.Value = Dr("TahsynDate")
                txtshowdate.setText(String.valueOf(Cr.getString(Cr.getColumnIndex("TahsynDate"))));
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
                //--------------------------------------------------------
                txtnotes.setText(Cr.getString(Cr.getColumnIndex("NotesM")));
                Cr = db.mDatabase.rawQuery("select * from allnames where FollowAutonum='" + getIntent().getStringExtra("MotherSrchNum") + "'", null);
                try {
                    Cr.moveToFirst();
                    ArrayList<String> data = new ArrayList<>();
                    data.clear();
                    while (!Cr.isAfterLast()) {
                        String id2 = Cr.getString(Cr.getColumnIndex("MotherNum"));
                        data.add(id2);
                        Cr.moveToNext();
                    }
                    ArrayAdapter NoCoreAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, data);
                    lstchoose.setAdapter(NoCoreAdapter);
                    Cr.close();
                    //------------------------------------------------------------------
                    List ls = new ArrayList();
                    for (int i = 0; i < lstchoose.getAdapter().getCount(); i++) {
                        ls.add(lstchoose.getItemAtPosition(i));
                    }

                    ArrayAdapter<Integer> adapter2 = new ArrayAdapter<Integer>(FollowMotherActivity.this,
                            android.R.layout.simple_list_item_1, ls);
                    lstchoose.setAdapter(adapter2);
                    //-----------------------------------------------------
                    ls2.clear();
                    for (int i = 0; i < lstchoose.getAdapter().getCount(); i++) {
                        ls2.add(lstchoose.getItemAtPosition(i));
                    }

                    finaladapter = new ArrayAdapter<>(FollowMotherActivity.this,
                            android.R.layout.simple_list_item_multiple_choice, ls2);
                    lstchoose.setAdapter(finaladapter);
                    //----------------------------------------------------

                    lblcount2.setText(String.valueOf(lstchoose.getAdapter().getCount()));
                } catch (Exception g) {
                    Log.e("", g.getMessage());
                }
            }
            SrchQ = "No";
            db.closeDatabase();
        } catch (Exception h) {
            Log.e("", h.getMessage());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.BtnNew) {
            Clear();
        } else if (id == R.id.BtnSearch) {
            Clear();

            Intent intent = new Intent(FollowMotherActivity.this, MotherSrch.class);
            startActivity(intent);

        } else if (id == R.id.BtnSave) {
            if (txtcode.getText().toString().trim().equals("")) {
                return super.onOptionsItemSelected(item);
            }

            String CheckTahsyn, CheckMedicin;
            if (spnrtahsyn.getSelectedItem().toString().trim().equals("لا شىء")) {
                CheckTahsyn = "";
            } else {
                CheckTahsyn = spnrtahsyn.getSelectedItem().toString().trim();
            }

            if (spnrmedicin.getSelectedItem().toString().trim().equals("لا شىء")) {
                CheckMedicin = "";
            } else {
                CheckMedicin = spnrmedicin.getSelectedItem().toString().trim();
            }

            if (CheckTahsyn.equals("") && CheckMedicin.equals("")) {
                Toast.makeText(this, "من فضلك اكمل البيانات", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
            }

            if (!(CheckTahsyn.equals("")) && !(CheckMedicin.equals(""))) {
                Toast.makeText(this, "لا يجوز ادخال العلاجات مع التحصينات فى نفس السجل", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
            }

            if (lblcount1.getText().toString().trim().equals("0")) {
                Toast.makeText(this, "من فضلك اختار الامهات", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
            }
            try {

                //------------------------------------------------------------------------------------------------
                db.openDatabase();

                Cursor Cr = db.mDatabase.rawQuery("select * from allnames where followautonum='" + txtcode.getText().toString().trim() + "'", null);
                Cr.moveToFirst();
                int Count = Cr.getCount();
                if (Count > 0) {
                    db.DeleteFollowBaby(txtcode.getText().toString().trim());

                    for (int i = 0; i < lstchoose.getAdapter().getCount(); i++) {
                        String Tahsyn, Medicin, NotesM, Username, MotherNum, MedicinDate, TahsynDate, FollowAutonum;
                        Username = MainActivity.GeneralUser.trim();
                        Tahsyn = CheckTahsyn;
                        MotherNum = lstchoose.getItemAtPosition(i).toString().trim();
                        Medicin = CheckMedicin;
                        NotesM = txtnotes.getText().toString().trim();
                        FollowAutonum = txtcode.getText().toString().trim();

                        String string_date = txtdate.getText().toString().trim() + " " + txttime.getText().toString().trim();

                        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                        long milliseconds = 0;
                        try {
                            Date d = f.parse(string_date);
                            milliseconds = d.getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Timestamp timestamp = new Timestamp(milliseconds);
                        TahsynDate = String.valueOf(timestamp);

                        String string_date2 = txtdate2.getText().toString().trim() + " " + txttime2.getText().toString().trim();

                        SimpleDateFormat f2 = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                        long milliseconds2 = 0;
                        try {
                            Date d2 = f2.parse(string_date2);
                            milliseconds2 = d2.getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Timestamp timestamp2 = new Timestamp(milliseconds2);
                        MedicinDate = String.valueOf(timestamp2);

                        db.Insert_to_AllnamesM(Username, Tahsyn, MotherNum, Medicin, NotesM, TahsynDate, FollowAutonum, MedicinDate);

                    }
                } else {
                    if (chkremember.isChecked() == true) {
                        String oldDate = txtdate.getText().toString().trim();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar c = Calendar.getInstance();
                        try {
                            c.setTime(sdf.parse(oldDate));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(txtdays.getText().toString()) * -1);
                        String newDate = sdf.format(c.getTime());
                        txtdate.setText(newDate);

                        String oldDate2 = txtdate2.getText().toString().trim();
                        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar c2 = Calendar.getInstance();
                        try {
                            c2.setTime(sdf2.parse(oldDate2));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        c2.add(Calendar.DAY_OF_MONTH, Integer.parseInt(txtdays.getText().toString()) * -1);
                        String newDate2 = sdf2.format(c2.getTime());
                        txtdate2.setText(newDate2);

                        for (int i = 0; i < lstchoose.getAdapter().getCount(); i++) {
                            String Tahsyn, Medicin, NotesM, MotherNum, MedicinDate, TahsynDate;
                            Tahsyn = CheckTahsyn;
                            MotherNum = lstchoose.getItemAtPosition(i).toString().trim();
                            Medicin = CheckMedicin;
                            NotesM = txtnotes.getText().toString().trim();

                            String string_date = txtdate.getText().toString().trim() + " " + txttime.getText().toString().trim();

                            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                            long milliseconds = 0;
                            try {
                                Date d = f.parse(string_date);
                                milliseconds = d.getTime();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            Timestamp timestamp = new Timestamp(milliseconds);
                            TahsynDate = String.valueOf(timestamp);

                            String string_date2 = txtdate2.getText().toString().trim() + " " + txttime2.getText().toString().trim();

                            SimpleDateFormat f2 = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                            long milliseconds2 = 0;
                            try {
                                Date d2 = f2.parse(string_date2);
                                milliseconds2 = d2.getTime();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            Timestamp timestamp2 = new Timestamp(milliseconds2);
                            MedicinDate = String.valueOf(timestamp2);

                            db.Insert_to_MotherRemember(Tahsyn, MotherNum, Medicin, NotesM, TahsynDate, MedicinDate);
                        }
                        if (!(CheckMedicin.equals(""))) {
                            MedicinNotification();
                        } else if (!(CheckTahsyn.equals(""))) {
                            TahsynNotification();
                        }
                    } else if (chkremember.isChecked() == false) {
                        for (int i = 0; i < lstchoose.getAdapter().getCount(); i++) {
                            String Tahsyn, Medicin, NotesM, Username, MotherNum, MedicinDate, TahsynDate, FollowAutonum;
                            Username = MainActivity.GeneralUser.trim();
                            Tahsyn = CheckTahsyn;
                            MotherNum = lstchoose.getItemAtPosition(i).toString().trim();
                            Medicin = CheckMedicin;
                            NotesM = txtnotes.getText().toString().trim();
                            FollowAutonum = txtcode.getText().toString().trim();

                            String string_date = txtdate.getText().toString().trim() + " " + txttime.getText().toString().trim();

                            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                            long milliseconds = 0;
                            try {
                                Date d = f.parse(string_date);
                                milliseconds = d.getTime();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            Timestamp timestamp = new Timestamp(milliseconds);
                            TahsynDate = String.valueOf(timestamp);

                            String string_date2 = txtdate2.getText().toString().trim() + " " + txttime2.getText().toString().trim();

                            SimpleDateFormat f2 = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                            long milliseconds2 = 0;
                            try {
                                Date d2 = f2.parse(string_date2);
                                milliseconds2 = d2.getTime();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            Timestamp timestamp2 = new Timestamp(milliseconds2);
                            MedicinDate = String.valueOf(timestamp2);

                            db.Insert_to_AllnamesM(Username, Tahsyn, MotherNum, Medicin, NotesM, TahsynDate, FollowAutonum, MedicinDate);

                        }
                        Cr.close();
                        db.Insert_to_FollowAutonum(txtcode.getText().toString().trim());
                    }
                }
                db.closeDatabase();
                Clear();
                Toast.makeText(this, "تم حفظ بيانات التحصينات والعلاج بنجاح", Toast.LENGTH_SHORT).show();

            } catch (Exception f) {
                Log.e("", f.getMessage());
            }

        } else if (id == R.id.BtnDelete) {

            AlertDialog.Builder builder = new AlertDialog.Builder(FollowMotherActivity.this);
            builder.setTitle("هل انت متأكد من الحذف");
            builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.openDatabase();
                    Cursor Cr = db.mDatabase.rawQuery("select * from allnames where followautonum='" + txtcode.getText().toString().trim() + "'", null);
                    try {
                        Cr.moveToFirst();
                        int Count = Cr.getCount();
                        if (Count > 0) {
                            db.DeleteFollowBaby(txtcode.getText().toString().trim());

                            Toast.makeText(FollowMotherActivity.this, "تم الحذف بنجاح", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(FollowMotherActivity.this, "لا يوجد بيانات لحذفها", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception g) {
                        Log.e("", g.getMessage());
                    }
                    Cr.close();
                    Clear();
                }
            });
            builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        } else if (id == R.id.BtnBack) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //-------------------------------------------------------------------------------------------------
    public void TahsynNotification() {
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
            TahsynNotificationId = (int) (long) milliseconds2;

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliseconds);
            calendar.set(Calendar.HOUR_OF_DAY, 9);
            calendar.set(Calendar.MINUTE, 00);
            calendar.set(Calendar.SECOND, 0);

            Intent notifyIntent = new Intent(FollowMotherActivity.this, TahsynReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast
                    (FollowMotherActivity.this, TahsynNotificationId, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
            //AlarmManager.INTERVAL_DAY,
        } catch (Exception k) {
            Log.e("", k.getMessage());
        }
    }

    //-------------------------------------------------------------------------------------------------
    public void MedicinNotification() {
        //Notification------------------------------------------------------------
        try {
            String string_date = txtdate2.getText().toString().trim();

            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

            long milliseconds = 0;

            Date d = f.parse(string_date);
            milliseconds = d.getTime();
//---------------------------------------------------------------------------------------------
            String string_date2 = txtdate2.getText().toString().trim() + " " + txttime2.getText().toString().trim();
            SimpleDateFormat f2 = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");

            long milliseconds2 = 0;

            Date d2 = f2.parse(string_date2);
            milliseconds2 = d2.getTime();
            MedicinNotificationId = (int) (long) milliseconds2;

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliseconds);
            calendar.set(Calendar.HOUR_OF_DAY, 9);
            calendar.set(Calendar.MINUTE, 00);
            calendar.set(Calendar.SECOND, 0);

            Intent notifyIntent = new Intent(FollowMotherActivity.this, MedicinReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast
                    (FollowMotherActivity.this, MedicinNotificationId, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
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

    public void date(View view) {
        Choose = "1";
        updatedate();
    }

    public void time(View view) {
        Choose = "1";
        updatetime();
    }

    public void date2(View view) {
        Choose = "2";
        updatedate();
    }

    public void time2(View view) {
        Choose = "2";
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

            if (Choose.equals("1")) {
                txtdate.setText(simpleDateFormat.format(datetime.getTime()));
            } else if (Choose.equals("2")) {
                txtdate2.setText(simpleDateFormat.format(datetime.getTime()));
            }
        }
    };


    TimePickerDialog.OnTimeSetListener tme = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            datetime.set(Calendar.MINUTE, minute);

            simpleDateFormat = new SimpleDateFormat("hh:mm a");
            if (Choose.equals("1")) {
                txttime.setText(simpleDateFormat.format(datetime.getTime()));
            } else if (Choose.equals("2")) {
                txttime2.setText(simpleDateFormat.format(datetime.getTime()));
            }

        }
    };
}
