package com.walidbarakat.newprogrammer.mazraaandroid;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BabyReport extends AppCompatActivity {
    Button btnpick, btnpick2, btnpick3, btnpick4;
    TextView txtdate, txttime, txtdate2, txttime2;

    RadioButton rball, rbmale, rbfemale;

    ListView lstview;
    TextView txtcount;
    EditText txtbaby;

    List<String> al = new ArrayList<>();

    SimpleDateFormat simpleDateFormat;
    String time;
    Calendar calander;

    Calendar datetime = Calendar.getInstance();

    DB_Sqlite db = new DB_Sqlite(this);

    String Choose = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_report);

        rball = (RadioButton) findViewById(R.id.RBAll);
        rbmale = (RadioButton) findViewById(R.id.RBMale);
        rbfemale = (RadioButton) findViewById(R.id.RBFemale);

        btnpick = (Button) findViewById(R.id.BtnPick);
        btnpick2 = (Button) findViewById(R.id.BtnPick2);
        btnpick3 = (Button) findViewById(R.id.BtnPick3);
        btnpick4 = (Button) findViewById(R.id.BtnPick4);

        btnpick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Choose = "1";
                updatedate();
            }
        });

        btnpick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Choose = "1";
                updatetime();
            }
        });

        btnpick3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Choose = "2";
                updatedate();
            }
        });

        btnpick4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Choose = "2";
                updatetime();
            }
        });

        lstview = (ListView) findViewById(R.id.LstView);
        txtcount = (TextView) findViewById(R.id.TxtCount);

        txtbaby = (EditText) findViewById(R.id.TxtBabyNum);

        Clear();
    }

    public void Clear() {

        lstview.setOnTouchListener(new ListView.OnTouchListener() {
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
        txtdate = (TextView) findViewById(R.id.TxtDate);
        txttime = (TextView) findViewById(R.id.TxtTime);
        txtdate2 = (TextView) findViewById(R.id.TxtDate2);
        txttime2 = (TextView) findViewById(R.id.TxtTime2);

        calander = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm a");

        time = simpleDateFormat.format(calander.getTime());

        txttime.setText(time);
        txttime2.setText(time);

        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        time = simpleDateFormat.format(calander.getTime());

        txtdate.setText(time);
        txtdate2.setText(time);
    }

    //-------------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.srchmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.BtnSrch) {

            Timestamp DateFrom, DateTo;
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
            DateFrom = timestamp;

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
            DateTo = timestamp2;

            txtcount.setText("");
            al.clear();

            try {
                db.openDatabase();
                db.mDatabase.execSQL("delete from tmpallnames");

                if (!txtbaby.getText().toString().trim().equals("")) {
                    db.mDatabase.execSQL("insert into tmpallnames(babynum,birthdate,gender,Tomother,tahsynb,tahsyndateb,medicinb,medicindateb,notesb,username) select babynum,birthdate,gender,Tomother,tahsynb,tahsyndateb,medicinb,medicindateb,notesb,username from allnames where Babynum='" + txtbaby.getText().toString().trim() + "'");
                } else {
                    if (rball.isChecked()) {
                        db.mDatabase.execSQL("insert into tmpallnames(babynum,birthdate,gender,Tomother,tahsynb,tahsyndateb,medicinb,medicindateb,notesb,username) select babynum,birthdate,gender,Tomother,tahsynb,tahsyndateb,medicinb,medicindateb,notesb,username from allnames where babynum<>'' and birthdate between '" + DateFrom + "' and '" + DateTo + "'");
                    } else if (rbmale.isChecked()) {
                        db.mDatabase.execSQL("insert into tmpallnames(babynum,birthdate,gender,Tomother,tahsynb,tahsyndateb,medicinb,medicindateb,notesb,username) select babynum,birthdate,gender,Tomother,tahsynb,tahsyndateb,medicinb,medicindateb,notesb,username from allnames where gender='ذكر' and babynum<>'' and birthdate between '" + DateFrom + "' and '" + DateTo + "'");
                    } else if (rbfemale.isChecked()) {
                        db.mDatabase.execSQL("insert into tmpallnames(babynum,birthdate,gender,Tomother,tahsynb,tahsyndateb,medicinb,medicindateb,notesb,username) select babynum,birthdate,gender,Tomother,tahsynb,tahsyndateb,medicinb,medicindateb,notesb,username from allnames where gender='انثى' and babynum<>'' and birthdate between '" + DateFrom + "' and '" + DateTo + "'");
                    }
                }
                db.closeDatabase();
            } catch (Exception a) {
                Log.e("", a.getMessage());
            }

            try {
                db.openDatabase();
                Cursor Cr = db.mDatabase.rawQuery("select babynum,birthdate,gender,Tomother,tahsynb,tahsyndateb,medicinb,medicindateb,notesb,username from tmpallnames where Babynum<>'' order by Babynum,birthdate,tahsyndateb,medicindateb asc", null);
                Cr.moveToFirst();
                ArrayList<BabyReportModel> menuItemv = new ArrayList<BabyReportModel>();
                while (!Cr.isAfterLast()) {
                    menuItemv.add(new BabyReportModel(Cr.getString(Cr.getColumnIndex("BabyNum")), "  " + Cr.getString(Cr.getColumnIndex("BirthDate")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("Gender")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("ToMother")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("TahsynB")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("TahsynDateB")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("MedicinB")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("MedicinDateB")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("NotesB")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("Username"))));
                    al.add(Cr.getString(Cr.getColumnIndex("BabyNum")));
                    Cr.moveToNext();
                }
                BabyReport.BabyListAdaptor arryadp = new BabyReport.BabyListAdaptor(menuItemv);
                lstview.setAdapter(arryadp);
                Cr.close();

                Set<String> hs = new HashSet<>();
                hs.addAll(al);
                al.clear();
                al.addAll(hs);

                txtcount.setText(String.valueOf(al.size()));

                db.closeDatabase();
            } catch (Exception r) {
                Log.e("", r.getMessage());
            }


        } else if (id == R.id.BtnBack) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class BabyListAdaptor extends BaseAdapter {
        ArrayList<BabyReportModel> babyreport = new ArrayList<BabyReportModel>();

        BabyListAdaptor(ArrayList<BabyReportModel> babyreport) {
            this.babyreport = babyreport;
        }

        @Override
        public int getCount() {
            return babyreport.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return babyreport.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view;
            view = layoutInflater.inflate(R.layout.babylayoutreport, null);

            TextView txtbabynum = (TextView) view.findViewById(R.id.TxtBabyNum);
            TextView txtbirthdate = (TextView) view.findViewById(R.id.TxtBirthDate);
            TextView txtgender = (TextView) view.findViewById(R.id.TxtGender);
            TextView txttomother = (TextView) view.findViewById(R.id.TxtToMother);
            TextView txttahsyn = (TextView) view.findViewById(R.id.TxtTahsyn);
            TextView txttahsyndate = (TextView) view.findViewById(R.id.TxtTahsynDate);
            TextView txtmedicin = (TextView) view.findViewById(R.id.TxtMedicin);
            TextView txtmedicindate = (TextView) view.findViewById(R.id.TxtMedicinDate);
            TextView txtnotes = (TextView) view.findViewById(R.id.TxtNotes);
            TextView txtusers = (TextView) view.findViewById(R.id.TxtUsers);

            txtbabynum.setText(babyreport.get(position).BabyNum);
            txtbirthdate.setText(babyreport.get(position).BirthDate);
            txtgender.setText(babyreport.get(position).Gender);
            txttomother.setText(babyreport.get(position).Tomother);
            txttahsyn.setText(babyreport.get(position).Tahsyn);
            txttahsyndate.setText(babyreport.get(position).TahsynDate);
            txtmedicin.setText(babyreport.get(position).Medicin);
            txtmedicindate.setText(babyreport.get(position).MedicinDate);
            txtnotes.setText(babyreport.get(position).Notes);
            txtusers.setText(babyreport.get(position).Users);

            if (txtbabynum.getText().toString().trim().equals("null")) {
                txtbabynum.setVisibility(View.GONE);
            }
            if (txtbirthdate.getText().toString().trim().equals("null")) {
                txtbirthdate.setVisibility(View.GONE);
            }
            if (txtgender.getText().toString().trim().equals("null")) {
                txtgender.setVisibility(View.GONE);
            }
            if (txttomother.getText().toString().trim().equals("null")) {
                txttomother.setVisibility(View.GONE);
            }
            if (txttahsyn.getText().toString().trim().equals("null") || txttahsyn.getText().toString().trim().equals("")) {
                txttahsyn.setVisibility(View.GONE);
                txttahsyndate.setVisibility(View.GONE);
            }
            if (txtmedicin.getText().toString().trim().equals("null") || txtmedicin.getText().toString().trim().equals("")) {
                txtmedicin.setVisibility(View.GONE);
                txtmedicindate.setVisibility(View.GONE);
            }
            if (txtnotes.getText().toString().trim().equals("null")) {
                txtnotes.setVisibility(View.GONE);
            }
            if (txtusers.getText().toString().trim().equals("null")) {
                txtusers.setVisibility(View.GONE);
            }

            return view;
        }
    }

    //-------------------------------------------------------------------------------------------------
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
