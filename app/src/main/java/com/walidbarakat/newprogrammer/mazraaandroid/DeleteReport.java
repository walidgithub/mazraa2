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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DeleteReport extends AppCompatActivity {
    Button btnpick, btnpick2, btnpick3, btnpick4;
    TextView txtdate, txttime, txtdate2, txttime2;
    RadioButton rball, rbmother, rbbaby;
    Spinner spnrreason;

    ListView lstview;

    SimpleDateFormat simpleDateFormat;
    String time;
    Calendar calander;

    DB_Sqlite db = new DB_Sqlite(this);

    Calendar datetime = Calendar.getInstance();

    String Choose = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_report);

        lstview = (ListView) findViewById(R.id.LstView);

        rball = (RadioButton) findViewById(R.id.RBAll);
        rbmother = (RadioButton) findViewById(R.id.RBMother);
        rbbaby = (RadioButton) findViewById(R.id.RBBaby);

        spnrreason = (Spinner) findViewById(R.id.SpnrReason);

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

        Clear();
    }

    void Clear() {
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

        db.openDatabase();
        ArrayList<String> data2 = new ArrayList<>();
        data2.clear();
        Cursor Cr = db.mDatabase.rawQuery("select distinct(deletereason) from DeleteHistory where deletereason<>''", null);
        Cr.moveToFirst();
        while (!Cr.isAfterLast()) {
            String id = Cr.getString(Cr.getColumnIndex("DeleteReason"));
            data2.add(id);
            Cr.moveToNext();
        }
        data2.add("الكل");
        ArrayAdapter NoCoreAdapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data2);
        spnrreason.setAdapter(NoCoreAdapter2);
        Cr.close();

        db.closeDatabase();


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

            try {
                db.openDatabase();

                if (spnrreason.getSelectedItem().toString().trim().equals("الكل")) {
                    if (rball.isChecked()) {
                        Cursor Cr = db.mDatabase.rawQuery("select mothernum,babynum,gender,tomother,deletereason,deletedate,username from deletehistory where deletedate between '" + DateFrom + "' and '" + DateTo + "' order by deletedate asc", null);
                        Cr.moveToFirst();
                        ArrayList<DeleteReportModel> menuItemv = new ArrayList<DeleteReportModel>();
                        while (!Cr.isAfterLast()) {
                            menuItemv.add(new DeleteReportModel(Cr.getString(Cr.getColumnIndex("MotherNum")), "  " + Cr.getString(Cr.getColumnIndex("Babynum")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("Gender")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("ToMother")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("DeleteReason")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("DeleteDate")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("Username"))));
                            Cr.moveToNext();
                        }
                        DeleteListAdapter arryadp = new DeleteListAdapter(menuItemv);
                        lstview.setAdapter(arryadp);
                        Cr.close();
                    } else if (rbmother.isChecked()) {
                        Cursor Cr = db.mDatabase.rawQuery("select mothernum,babynum,gender,tomother,deletereason,deletedate,username from deletehistory where mothernum<>'' and deletedate between '" + DateFrom + "' and '" + DateTo + "' order by deletedate asc", null);
                        Cr.moveToFirst();
                        ArrayList<DeleteReportModel> menuItemv = new ArrayList<DeleteReportModel>();
                        while (!Cr.isAfterLast()) {
                            menuItemv.add(new DeleteReportModel(Cr.getString(Cr.getColumnIndex("MotherNum")), "  " + Cr.getString(Cr.getColumnIndex("Babynum")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("Gender")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("ToMother")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("DeleteReason")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("DeleteDate")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("Username"))));
                            Cr.moveToNext();
                        }
                        DeleteListAdapter arryadp = new DeleteListAdapter(menuItemv);
                        lstview.setAdapter(arryadp);
                        Cr.close();
                    } else if (rbbaby.isChecked()) {
                        Cursor Cr = db.mDatabase.rawQuery("select mothernum,babynum,gender,tomother,deletereason,deletedate,username from deletehistory where babynum<>'' and deletedate between '" + DateFrom + "' and '" + DateTo + "' order by deletedate asc", null);
                        Cr.moveToFirst();
                        ArrayList<DeleteReportModel> menuItemv = new ArrayList<DeleteReportModel>();
                        while (!Cr.isAfterLast()) {
                            menuItemv.add(new DeleteReportModel(Cr.getString(Cr.getColumnIndex("MotherNum")), "  " + Cr.getString(Cr.getColumnIndex("Babynum")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("Gender")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("ToMother")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("DeleteReason")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("DeleteDate")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("Username"))));
                            Cr.moveToNext();
                        }
                        DeleteListAdapter arryadp = new DeleteListAdapter(menuItemv);
                        lstview.setAdapter(arryadp);
                        Cr.close();
                    }

                } else {
                    if (rball.isChecked()) {
                        Cursor Cr = db.mDatabase.rawQuery("select mothernum,babynum,gender,tomother,deletereason,deletedate,username from deletehistory where deletereason='" + spnrreason.getSelectedItem().toString().trim() + "' and deletedate between '" + DateFrom + "' and '" + DateTo + "' order by deletedate asc", null);
                        Cr.moveToFirst();
                        ArrayList<DeleteReportModel> menuItemv = new ArrayList<DeleteReportModel>();
                        while (!Cr.isAfterLast()) {
                            menuItemv.add(new DeleteReportModel(Cr.getString(Cr.getColumnIndex("MotherNum")), "  " + Cr.getString(Cr.getColumnIndex("Babynum")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("Gender")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("ToMother")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("DeleteReason")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("DeleteDate")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("Username"))));
                            Cr.moveToNext();
                        }
                        DeleteListAdapter arryadp = new DeleteListAdapter(menuItemv);
                        lstview.setAdapter(arryadp);
                        Cr.close();
                    } else if (rbmother.isChecked()) {
                        Cursor Cr = db.mDatabase.rawQuery("select mothernum,babynum,gender,tomother,deletereason,deletedate,username from deletehistory where deletereason='" + spnrreason.getSelectedItem().toString().trim() + "' and mothernum<>'' and deletedate between '" + DateFrom + "' and '" + DateTo + "' order by deletedate asc", null);
                        Cr.moveToFirst();
                        ArrayList<DeleteReportModel> menuItemv = new ArrayList<DeleteReportModel>();
                        while (!Cr.isAfterLast()) {
                            menuItemv.add(new DeleteReportModel(Cr.getString(Cr.getColumnIndex("MotherNum")), "  " + Cr.getString(Cr.getColumnIndex("Babynum")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("Gender")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("ToMother")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("DeleteReason")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("DeleteDate")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("Username"))));
                            Cr.moveToNext();
                        }
                        DeleteListAdapter arryadp = new DeleteListAdapter(menuItemv);
                        lstview.setAdapter(arryadp);
                        Cr.close();
                    } else if (rbbaby.isChecked()) {
                        Cursor Cr = db.mDatabase.rawQuery("select mothernum,babynum,gender,tomother,deletereason,deletedate,username from deletehistory where deletereason='" + spnrreason.getSelectedItem().toString().trim() + "' and babynum<>'' and deletedate between '" + DateFrom + "' and '" + DateTo + "' order by deletedate asc", null);
                        Cr.moveToFirst();
                        ArrayList<DeleteReportModel> menuItemv = new ArrayList<DeleteReportModel>();
                        while (!Cr.isAfterLast()) {
                            menuItemv.add(new DeleteReportModel(Cr.getString(Cr.getColumnIndex("MotherNum")), "  " + Cr.getString(Cr.getColumnIndex("Babynum")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("Gender")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("ToMother")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("DeleteReason")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("DeleteDate")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("Username"))));
                            Cr.moveToNext();
                        }
                        DeleteListAdapter arryadp = new DeleteListAdapter(menuItemv);
                        lstview.setAdapter(arryadp);
                        Cr.close();
                    }
                }

                db.closeDatabase();
            } catch (Exception p) {
                Log.e("", p.getMessage());
            }

        } else if (id == R.id.BtnBack) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class DeleteListAdapter extends BaseAdapter {
        ArrayList<DeleteReportModel> deletereport = new ArrayList<>();

        DeleteListAdapter(ArrayList<DeleteReportModel> deletereport) {
            this.deletereport = deletereport;
        }

        @Override
        public int getCount() {
            return deletereport.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return deletereport.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.deletereportlayout, null);

            TextView txtmothernum = (TextView) view.findViewById(R.id.TxtMotherNum);
            TextView txtbabynum = (TextView) view.findViewById(R.id.TxtBabyNum);
            TextView txtgender = (TextView) view.findViewById(R.id.TxtGender);
            TextView txttomother = (TextView) view.findViewById(R.id.TxtToMother);
            TextView txtreason = (TextView) view.findViewById(R.id.TxtReason);
            TextView txtdeletedate = (TextView) view.findViewById(R.id.TxtDeleteDate);
            TextView txtusers = (TextView) view.findViewById(R.id.TxtUsers);

            txtmothernum.setText(deletereport.get(position).MotherNum);
            txtbabynum.setText(deletereport.get(position).BabyNum);
            txtgender.setText(deletereport.get(position).Gender);
            txttomother.setText(deletereport.get(position).ToMother2);
            txtreason.setText(deletereport.get(position).Reason);
            txtdeletedate.setText(deletereport.get(position).DeleteDate);
            txtusers.setText(deletereport.get(position).Users);

            if (txtmothernum.getText().toString().trim().equals("null")) {
                txtmothernum.setVisibility(View.GONE);
            }
            if (txtbabynum.getText().toString().trim().equals("null")) {
                txtbabynum.setVisibility(View.GONE);
            }
            if (txtgender.getText().toString().trim().equals("null")) {
                txtgender.setVisibility(View.GONE);
            }
            if (txttomother.getText().toString().trim().equals("null")) {
                txttomother.setVisibility(View.GONE);
            }
            if (txtreason.getText().toString().trim().equals("null")) {
                txtreason.setVisibility(View.GONE);
                txtdeletedate.setVisibility(View.GONE);
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
