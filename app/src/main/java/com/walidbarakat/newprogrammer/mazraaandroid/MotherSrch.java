package com.walidbarakat.newprogrammer.mazraaandroid;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MotherSrch extends AppCompatActivity {

    ListView lstview;
    Button btnpick, btnpick2, btnpick3, btnpick4;
    TextView txtdate, txttime, txtdate2, txttime2, txttestdate, lstitem;

    SimpleDateFormat simpleDateFormat;
    String time;
    Calendar calander;

    Calendar datetime = Calendar.getInstance();
    DB_Sqlite db = new DB_Sqlite(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mother_srch);

        lstview = (ListView) findViewById(R.id.LstView);
        lstitem = (TextView) findViewById(R.id.LstItem);

        btnpick = (Button) findViewById(R.id.BtnPick);
        btnpick2 = (Button) findViewById(R.id.BtnPick2);
        btnpick3 = (Button) findViewById(R.id.BtnPick3);
        btnpick4 = (Button) findViewById(R.id.BtnPick4);

        txtdate = (TextView) findViewById(R.id.TxtDate);
        txttime = (TextView) findViewById(R.id.TxtTime);
        txtdate2 = (TextView) findViewById(R.id.TxtDate2);
        txttime2 = (TextView) findViewById(R.id.TxtTime2);
        txttestdate = (TextView) findViewById(R.id.TxtTestDate);

        Clear();


        lstview.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    TextView txtcode = (TextView) view.findViewById(R.id.TxtCode);
                    lstitem.setText(txtcode.getText().toString());
                    Intent Motherintent = new Intent(MotherSrch.this, FollowMotherActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("MotherSrchNum", lstitem.getText().toString());
                    Motherintent.putExtras(bundle);
                    FollowMotherActivity.SrchQ = "Yes";
                    startActivity(Motherintent);
                    finish();
                } catch (Exception h) {
                    Log.e("", h.getMessage());
                }
            }
        });

    }

    public void Clear() {
        lstitem.setText("");

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


            db.openDatabase();
            Cursor Cr = db.mDatabase.rawQuery("select Distinct(FollowAutonum),tahsyn,tahsyndate,medicin,medicindate,notesm from allnames where tahsyndate between '" + DateFrom + "' and '" + DateTo + "' or medicindate between '" + DateFrom + "' and '" + DateTo + "' order by FollowAutonum asc", null);
            Cr.moveToFirst();
            try {
                ArrayList<BabySrchModel> menuItemv = new ArrayList<BabySrchModel>();
                while (!Cr.isAfterLast()) {
                    menuItemv.add(new BabySrchModel(Cr.getString(Cr.getColumnIndex("FollowAutonum")), "  " + Cr.getString(Cr.getColumnIndex("Tahsyn")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("TahsynDate")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("Medicin")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("MedicinDate")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("NotesM"))));
                    Cr.moveToNext();
                }
                ListAdaptor arryadp = new ListAdaptor(menuItemv);
                lstview.setAdapter(arryadp);
            } catch (Exception r) {
                Log.e("", r.getMessage());
            }
            Cr.close();
            db.closeDatabase();


        } else if (id == R.id.BtnBack) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //-------------------------------------------------------------------------------------------------
    private class ListAdaptor extends BaseAdapter {
        ArrayList<BabySrchModel> Mothersrch = new ArrayList<BabySrchModel>();

        ListAdaptor(ArrayList<BabySrchModel> Mothersrch) {
            this.Mothersrch = Mothersrch;
        }

        @Override
        public int getCount() {
            return Mothersrch.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return Mothersrch.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.babysrchlayout, null);

            TextView txtcode = (TextView) view.findViewById(R.id.TxtCode);
            TextView txttahsyn = (TextView) view.findViewById(R.id.TxtTahsyn);
            TextView txttahsyndate = (TextView) view.findViewById(R.id.TxtTahsynDate);
            TextView txtmedicin = (TextView) view.findViewById(R.id.TxtMedicin);
            TextView txtmedicindate = (TextView) view.findViewById(R.id.TxtMedicinDate);
            TextView txtnotes = (TextView) view.findViewById(R.id.TxtNotes);

            txtcode.setText(Mothersrch.get(position).Code);
            txttahsyn.setText(Mothersrch.get(position).Tahsyn);
            txttahsyndate.setText(Mothersrch.get(position).TahsynDate);
            txtmedicin.setText(Mothersrch.get(position).Medicin);
            txtmedicindate.setText(Mothersrch.get(position).MedicinDate);
            txtnotes.setText(Mothersrch.get(position).Notes);

            return view;
        }
    }

    //--------------------------------------------------------------------------------------------------
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
