package com.walidbarakat.newprogrammer.mazraaandroid;

import android.support.v4.app.Fragment;
import android.database.Cursor;
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

/**
 * Created by PC on 08/04/2018.
 */

public class MotherMedicin extends Fragment {
    DB_Sqlite db;
    ListView lstview;

    public MotherMedicin() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mothermedicin_layout, container, false);

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
// where Medicin<>'' and medicindate <= '" + DateFrom + "'

        db.openDatabase();
        Cursor Cr = db.mDatabase.rawQuery("select mothernum,Medicin,medicindate,notes from motheralarm where Medicin<>'' and medicindate <= '" + DateFrom + "'", null);
        Cr.moveToFirst();
        try {
            List<MedicinAndTahsynModel> medicinAndTahsynModelList = new ArrayList<>();
            while (!Cr.isAfterLast()) {
                MedicinAndTahsynModel medicinAndTahsynModel = new MedicinAndTahsynModel(Cr.getString(Cr.getColumnIndex("MotherNum")), "  " + Cr.getString(Cr.getColumnIndex("Medicin")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("MedicinDate")) + "  ", "  " + Cr.getString(Cr.getColumnIndex("Notes")));
                medicinAndTahsynModelList.add(medicinAndTahsynModel);
                Cr.moveToNext();
            }
            MedicinAdapter medicinadapter = new MedicinAdapter(getContext(), R.layout.mothermedicinshow, medicinAndTahsynModelList);
            lstview.setAdapter(medicinadapter);
            Cr.close();
            db.closeDatabase();
        } catch (Exception r) {
            Log.e("", r.getMessage());
        }

        return view;
    }
}
