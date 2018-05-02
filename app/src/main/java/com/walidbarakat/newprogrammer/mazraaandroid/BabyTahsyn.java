package com.walidbarakat.newprogrammer.mazraaandroid;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BabyTahsyn extends Fragment {

    DB_Sqlite db;
    ListView lstview;

    public BabyTahsyn() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tahsyn_baby, container, false);

        db = new DB_Sqlite(getContext());

        lstview = (ListView) view.findViewById(R.id.LstView);
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
        Cursor Cr = db.mDatabase.rawQuery("select Babynum,Tahsyn,Tahsyndate,notes from Babyalarm where Tahsyn<>'' and Tahsyndate <= '" + DateFrom + "'", null);
        Cr.moveToFirst();
        try {
            List<MedicinAndTahsynModel> medicinAndTahsynModelList = new ArrayList<>();
            while (!Cr.isAfterLast()) {
                MedicinAndTahsynModel medicinAndTahsynModel = new MedicinAndTahsynModel(Cr.getString(Cr.getColumnIndex("BabyNum")), "  " + Cr.getString(Cr.getColumnIndex("Tahsyn")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("TahsynDate")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("Notes")));
                medicinAndTahsynModelList.add(medicinAndTahsynModel);
                Cr.moveToNext();
            }
            TahsynAdapter tahsynadapter = new TahsynAdapter(getContext(), R.layout.mothertahsynshow, medicinAndTahsynModelList);
            lstview.setAdapter(tahsynadapter);
            Cr.close();
            db.closeDatabase();
        } catch (Exception r) {
            Log.e("", r.getMessage());
        }

        return view;
    }
}
