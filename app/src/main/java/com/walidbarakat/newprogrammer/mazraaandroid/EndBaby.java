package com.walidbarakat.newprogrammer.mazraaandroid;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by PC on 11/04/2018.
 */

public class EndBaby extends AppCompatActivity {
    Spinner spnrendreason;
    ListView lstview;
    ImageView addreason;
    CheckBox chkendoption;
    Button btnpick7, btnpick8;
    TextView txtdate4, txttime4;
    List ls2 = new ArrayList();

    SimpleDateFormat simpleDateFormat;
    String time;
    Calendar calander;

    Calendar datetime = Calendar.getInstance();

    DB_Sqlite db = new DB_Sqlite(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endbaby_layout);

        lstview = (ListView) findViewById(R.id.LstView);
        spnrendreason = (Spinner) findViewById(R.id.SpnrEndReason);
        addreason = (ImageView) findViewById(R.id.AddReason);
        chkendoption = (CheckBox) findViewById(R.id.ChkEnd);

        btnpick7 = (Button) findViewById(R.id.BtnPick7);
        btnpick8 = (Button) findViewById(R.id.BtnPick8);

        txtdate4 = (TextView) findViewById(R.id.TxtDate4);
        txttime4 = (TextView) findViewById(R.id.TxtTime4);

        calander = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm a");

        time = simpleDateFormat.format(calander.getTime());

        txttime4.setText(time);

        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        time = simpleDateFormat.format(calander.getTime());

        txtdate4.setText(time);

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
        ArrayAdapter NoCoreAdapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data2);
        spnrendreason.setAdapter(NoCoreAdapter2);
        Cr.close();
        db.closeDatabase();

        addreason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EndBaby.this);
                    builder.setTitle("ادخل سبب العزل");

                    final EditText input = new EditText(EndBaby.this);
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
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(EndBaby.this,
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
                } catch (Exception t) {
                    Log.e("", t.getMessage());
                }

            }
        });

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        Calendar cal = Calendar.getInstance();

        Timestamp DateFrom;
        String string_date = dateFormat.format(cal.getTime());

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

        db.openDatabase();
        Cr = db.mDatabase.rawQuery("select Babynum,Birthdate,gender,MotherNum,enddate,autonum from allnames where birthdate<>'' and babynum<>'' and enddate <= '" + DateFrom + "'", null);
        Cr.moveToFirst();
        try {
            ArrayList<EndBabyModel> menuItemv = new ArrayList<EndBabyModel>();
            while (!Cr.isAfterLast()) {
                menuItemv.add(new EndBabyModel(Cr.getString(Cr.getColumnIndex("BabyNum")), Cr.getString(Cr.getColumnIndex("BirthDate")), Cr.getString(Cr.getColumnIndex("Gender")), Cr.getString(Cr.getColumnIndex("MotherNum")), Cr.getString(Cr.getColumnIndex("EndDate")), Cr.getString(Cr.getColumnIndex("Autonum"))));
                Cr.moveToNext();
            }
            EndAdaptor arryadp = new EndAdaptor(menuItemv);
            lstview.setAdapter(arryadp);
        } catch (Exception r) {
            Log.e("", r.getMessage());
        }
        Cr.close();
        db.closeDatabase();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.endmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Btndelete) {
            for (int a = 0; a < ls2.size(); a++) {
                if (MainActivity.DBType.equals("Remote")) {
     /*
                        String Query;
                        String Babynum, ToMother, Gender, DeleteReason, Deletedate, Username;
                        Username = "'" + MainActivity.GeneralUser.toString().trim() + "',";
                        DeleteReason = "'" + spnrendreason.getSelectedItem().toString().trim() + "',";
                        Query = "select * from allnames where autonum='" + txtcode.getText().toString().trim() + "'";
                        st = d.conn.createStatement();
                        rs = st.executeQuery(Query);
                        if (rs.next()) {
                            Babynum = "'" + rs.getString("babynum").toString().trim() + "',";
                            ToMother = "'" + rs.getString("tomother").toString().trim() + "',";
                            Gender = "'" + rs.getString("gender").toString().trim() + "',";
                        } else {
                            return super.onOptionsItemSelected(item);
                        }
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
                        Deletedate = "'" + timestamp + "'";

                        Query = "insert into DeleteHistory(Username, DeleteReason, Babynum, ToMother, Gender, Deletedate) values (" + Username + DeleteReason + Babynum + ToMother + Gender + Deletedate + ")";
                        st = d.conn.createStatement();
                        st.executeUpdate(Query);

                        Query = "delete from Allnames where babynum='" + txtbaby.getText().toString().trim() + "' and mothernum is null";
                        st = d.conn.createStatement();
                        st.executeUpdate(Query);
*/
                } else if (MainActivity.DBType.equals("Local")) {
                    try {
                        db.openDatabase();
                        String Babynum, ToMother, Gender, DeleteReason, Deletedate, Username;
                        Username = MainActivity.GeneralUser.trim();
                        DeleteReason = spnrendreason.getSelectedItem().toString().trim();
                        Cursor Cr = db.mDatabase.rawQuery("select * from allnames where Autonum='" + ls2.get(a).toString().trim() + "'", null);
                        Cr.moveToFirst();
                        int count = Cr.getCount();
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

                        ArrayList<String> data2 = new ArrayList<>();
                        data2.clear();
                        Cr = db.mDatabase.rawQuery("select distinct(deletereason) from DeleteHistory where deletereason<>''", null);
                        Cr.moveToFirst();
                        while (!Cr.isAfterLast()) {
                            String id2 = Cr.getString(Cr.getColumnIndex("DeleteReason"));
                            data2.add(id2);
                            Cr.moveToNext();
                        }
                        ArrayAdapter NoCoreAdapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data2);
                        spnrendreason.setAdapter(NoCoreAdapter2);
                        Cr.close();

                        db.closeDatabase();
//--------------------------------------------------------------------------------------------------------
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                        Calendar cal = Calendar.getInstance();

                        Timestamp DateFrom;
                        String string_date2 = dateFormat.format(cal.getTime());

                        SimpleDateFormat f2 = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                        long milliseconds2 = 0;
                        try {
                            Date d2 = f2.parse(string_date2);
                            milliseconds2 = d2.getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Timestamp timestamp2 = new Timestamp(milliseconds2);
                        DateFrom = timestamp2;

                        db.openDatabase();
                        Cr = db.mDatabase.rawQuery("select Babynum,Birthdate,gender,MotherNum,enddate,autonum from allnames where birthdate<>'' and babynum<>'' and enddate <= '" + DateFrom + "'", null);
                        Cr.moveToFirst();
                        try {
                            ArrayList<EndBabyModel> menuItemv = new ArrayList<EndBabyModel>();
                            while (!Cr.isAfterLast()) {
                                menuItemv.add(new EndBabyModel(Cr.getString(Cr.getColumnIndex("BabyNum")), Cr.getString(Cr.getColumnIndex("BirthDate")), Cr.getString(Cr.getColumnIndex("Gender")), Cr.getString(Cr.getColumnIndex("MotherNum")), Cr.getString(Cr.getColumnIndex("EndDate")), Cr.getString(Cr.getColumnIndex("Autonum"))));
                                Cr.moveToNext();
                            }
                            EndAdaptor arryadp = new EndAdaptor(menuItemv);
                            lstview.setAdapter(arryadp);
                        } catch (Exception r) {
                            Log.e("", r.getMessage());
                        }
                        Cr.close();
                        db.closeDatabase();
                    } catch (Exception j) {
                        Log.e("", j.getMessage());
                    }
                }

                Toast.makeText(EndBaby.this, "تم عزل المواليد بنجاح", Toast.LENGTH_SHORT).show();

            }


        }
        return super.onOptionsItemSelected(item);
    }

    //------------------------------------------------------------------
    private class EndAdaptor extends BaseAdapter {
        ArrayList<EndBabyModel> endbaby = new ArrayList<EndBabyModel>();

        EndAdaptor(ArrayList<EndBabyModel> endbaby) {
            this.endbaby = endbaby;
        }

        @Override
        public int getViewTypeCount() {
            return getCount();
        }

        @Override
        public int getItemViewType(int position) {

            return position;
        }

        @Override
        public int getCount() {
            return endbaby.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return endbaby.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = getLayoutInflater();
            final View view = layoutInflater.inflate(R.layout.endbabyshow, null);

            TextView txtnum = (TextView) view.findViewById(R.id.TxtNum);
            TextView txtbirthdate = (TextView) view.findViewById(R.id.TxtBirthDate);
            TextView txtgender = (TextView) view.findViewById(R.id.TxtGender);
            TextView txttomother = (TextView) view.findViewById(R.id.TxtMother);
            TextView txtenddate = (TextView) view.findViewById(R.id.TxtEndDate);
            final TextView txtautonum = (TextView) view.findViewById(R.id.TxtAutonum);
            final CheckBox checkBox = (CheckBox) view.findViewById(R.id.ChkEnd);

            txtnum.setText(endbaby.get(position).BabyNum);
            txtbirthdate.setText(endbaby.get(position).BirthDate);
            txtgender.setText(endbaby.get(position).Gender);
            txttomother.setText(endbaby.get(position).ToMother);
            txtenddate.setText(endbaby.get(position).EndDate);
            txtautonum.setText(endbaby.get(position).Autonum);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String valu2 = txtautonum.getText().toString().trim();
                    if (checkBox.isChecked()) {
                        for (int a = 0; a < ls2.size(); a++) {
                            if (valu2.trim().toString().equals(ls2.get(a).toString().trim())) {
                                return;
                            }
                        }
                        ls2.add(valu2);
                    } else {
                        ls2.remove(valu2);
                    }
                }
            });

            return view;
        }
    }

    String Choose = "";

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

            if (Choose.equals("4")) {
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
            if (Choose.equals("4")) {
                txttime4.setText(simpleDateFormat.format(datetime.getTime()));
            }

        }
    };
}
