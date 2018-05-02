package com.walidbarakat.newprogrammer.mazraaandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by PC on 11/04/2018.
 */

public class TahsynAdapter extends ArrayAdapter<MedicinAndTahsynModel> {
private int layoutResource;

public TahsynAdapter(Context context, int layoutResource, List<MedicinAndTahsynModel> medicinAndTahsynModelList) {
        super(context, layoutResource, medicinAndTahsynModelList);
        this.layoutResource = layoutResource;
        }


@Override
public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        view = layoutInflater.inflate(layoutResource, null);
        }

        MedicinAndTahsynModel medicinAndTahsynModel = getItem(position);

        if (medicinAndTahsynModel != null) {
        TextView txtnum = (TextView) view.findViewById(R.id.TxtNum);
        TextView txtmedicin = (TextView) view.findViewById(R.id.TxtTahsyn);
        TextView txtmedicindate = (TextView) view.findViewById(R.id.TxtTDate);
        TextView txtnotes = (TextView) view.findViewById(R.id.TxtNotes);

        if (txtnum != null) {
        txtnum.setText(medicinAndTahsynModel.Num);
        }
        if (txtmedicin != null) {
        txtmedicin.setText(medicinAndTahsynModel.Detail);
        }
        if (txtmedicindate != null) {
        txtmedicindate.setText(medicinAndTahsynModel.DetailDate);
        }
        if (txtnotes != null) {
        txtnotes.setText(medicinAndTahsynModel.DetailNotes);
        }
        }

        return view;
        }
}
