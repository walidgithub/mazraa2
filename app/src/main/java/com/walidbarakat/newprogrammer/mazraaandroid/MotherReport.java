package com.walidbarakat.newprogrammer.mazraaandroid;

import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MotherReport extends AppCompatActivity {
    ListView lstview;
    TextView lstitem, txtcount;
    EditText txtmother;
    ProgressBar progressBar;

    int ListCount;

    List<String> al = new ArrayList<>();

    ArrayList<MotherReportModel> menuItemv = new ArrayList<MotherReportModel>();

    DB_Sqlite db = new DB_Sqlite(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mother_report);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        progressBar.setVisibility(View.GONE);

        lstview = (ListView) findViewById(R.id.LstView);
        txtcount = (TextView) findViewById(R.id.TxtCount);

        lstitem = (TextView) findViewById(R.id.LstItem);

        txtmother = (EditText) findViewById(R.id.TxtMotherNum);

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
            txtcount.setText("");

            List<String> data = new ArrayList<>();
            data.clear();
            db.openDatabase();
            Cursor Cr = db.mDatabase.rawQuery("select * from allnames where mothernum<>'' and mothernum<>'MotherNum'", null);
            Cr.moveToFirst();
            while (!Cr.isAfterLast()) {
                String id2 = String.valueOf(Cr.getString(Cr.getColumnIndex("MotherNum")));
                data.add(id2);
                Cr.moveToNext();
            }
            Cr.close();
            ListCount = data.size();
            db.closeDatabase();

            new MyTask().execute();


        } else if (id == R.id.BtnBack) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyTask extends AsyncTask<String, String, String> {
        MotherListAdaptor NoCoreAdapter = new MotherListAdaptor(menuItemv);

        int count;

        @Override
        protected void onPreExecute() {

            progressBar.setMax(ListCount);
            progressBar.setProgress(0);
            progressBar.setVisibility(View.VISIBLE);
            count = 0;

            db.openDatabase();
            db.mDatabase.execSQL("delete from tmpallnames");

            if (!txtmother.getText().toString().trim().equals("")) {
                db.mDatabase.execSQL("insert into tmpallnames(mothernum,dady,mpartition,createdate,babynum,birthdate,gender,tahsyn,tahsyndate,medicin,medicindate,notesm,username) select mothernum,dady,mpartition,createdate,babynum,birthdate,gender,tahsyn,tahsyndate,medicin,medicindate,notesm,username from allnames where mothernum='" + txtmother.getText().toString().trim() + "'");
            } else {
                db.mDatabase.execSQL("insert into tmpallnames(mothernum,dady,mpartition,createdate,babynum,birthdate,gender,tahsyn,tahsyndate,medicin,medicindate,notesm,username) select mothernum,dady,mpartition,createdate,babynum,birthdate,gender,tahsyn,tahsyndate,medicin,medicindate,notesm,username from allnames where mothernum<>'' and MotherNum<>'MotherNum'");
            }
            db.closeDatabase();

            NoCoreAdapter = (MotherListAdaptor) lstview.getAdapter();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            db.openDatabase();
            menuItemv.clear();
            Cursor Cr = db.mDatabase.rawQuery("select mothernum,dady,mpartition,createdate,babynum,birthdate,gender,tahsyn,tahsyndate,medicin,medicindate,notesm,username from tmpallnames where mothernum<>'' order by mothernum,birthdate,tahsyndate,medicindate asc", null);
            Cr.moveToFirst();
            while (!Cr.isAfterLast()) {
                menuItemv.add(new MotherReportModel(Cr.getString(Cr.getColumnIndex("MotherNum")), "  " + Cr.getString(Cr.getColumnIndex("Dady")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("MPartition")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("CreateDate")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("BabyNum")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("BirthDate")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("Gender")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("Tahsyn")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("TahsynDate")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("Medicin")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("MedicinDate")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("NotesM")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("Username"))));
                al.add(Cr.getString(Cr.getColumnIndex("MotherNum")));
                publishProgress(Cr.getString(Cr.getColumnIndex("MotherNum")));
                Cr.moveToNext();
                /*
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                 */

            }
            Cr.close();

            return "Ok";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            MotherListAdaptor NoCoreAdapter = new MotherListAdaptor(menuItemv);
            lstview.setAdapter(NoCoreAdapter);

            Set<String> hs = new HashSet<>();
            hs.addAll(al);
            al.clear();
            al.addAll(hs);

            txtcount.setText(String.valueOf(al.size()));
            count++;
            progressBar.setProgress(count);
        }

        @Override
        protected void onPostExecute(String result) {
            db.closeDatabase();

            progressBar.setVisibility(View.GONE);
        }
    }

    //--------------------------------------------------------------------------------------------------
    private class MotherListAdaptor extends BaseAdapter {
        ArrayList<MotherReportModel> motherreport = new ArrayList<MotherReportModel>();

        MotherListAdaptor(ArrayList<MotherReportModel> motherreport) {
            this.motherreport = motherreport;
        }

        @Override
        public int getCount() {
            return motherreport.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return motherreport.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.motherlayoutreport, null);

            TextView txtmothernum = (TextView) view.findViewById(R.id.TxtMotherNum);
            TextView txtdady = (TextView) view.findViewById(R.id.TxtDady);
            TextView txtpartition = (TextView) view.findViewById(R.id.TxtPartition);
            TextView txtcreatedate = (TextView) view.findViewById(R.id.TxtCreateDate);
            TextView txtbabynum = (TextView) view.findViewById(R.id.TxtBabyNum);
            TextView txtbirthdate = (TextView) view.findViewById(R.id.TxtBirthDate);
            TextView txtgender = (TextView) view.findViewById(R.id.TxtGender);
            TextView txttahsyn = (TextView) view.findViewById(R.id.TxtTahsyn);
            TextView txttahsyndate = (TextView) view.findViewById(R.id.TxtTahsynDate);
            TextView txtmedicin = (TextView) view.findViewById(R.id.TxtMedicin);
            TextView txtmedicindate = (TextView) view.findViewById(R.id.TxtMedicinDate);
            TextView txtnotes = (TextView) view.findViewById(R.id.TxtNotes);
            TextView txtusers = (TextView) view.findViewById(R.id.TxtUsers);


            txtmothernum.setText(motherreport.get(position).MotherNum);
            txtdady.setText(motherreport.get(position).Dady);
            txtpartition.setText(motherreport.get(position).Partition);
            txtcreatedate.setText(motherreport.get(position).CreateDate);
            txtbabynum.setText(motherreport.get(position).BabyNum);
            txtbirthdate.setText(motherreport.get(position).BirthDate);
            txtgender.setText(motherreport.get(position).Gender);
            txttahsyn.setText(motherreport.get(position).Tahsyn);
            txttahsyndate.setText(motherreport.get(position).TahsynDate);
            txtmedicin.setText(motherreport.get(position).Medicin);
            txtmedicindate.setText(motherreport.get(position).MedicinDate);
            txtnotes.setText(motherreport.get(position).Notes);
            txtusers.setText(motherreport.get(position).Users);

            if (txtmothernum.getText().toString().trim().equals("null")) {
                txtmothernum.setVisibility(View.GONE);
            }
            if (txtdady.getText().toString().trim().equals("null")) {
                txtdady.setVisibility(View.GONE);
            }
            if (txtpartition.getText().toString().trim().equals("null")) {
                txtpartition.setVisibility(View.GONE);
            }
            if (txtcreatedate.getText().toString().trim().equals("null")) {
                txtcreatedate.setVisibility(View.GONE);
            }
            if (txtbabynum.getText().toString().trim().equals("null")) {
                txtbabynum.setVisibility(View.GONE);
            }
            if (txtbirthdate.getText().toString().trim().equals("null")) {
                txtbirthdate.setVisibility(View.GONE);
            }
            if (txtgender.getText().toString().trim().equals("null")) {
                txtgender.setVisibility(View.GONE);
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

}