package ru.good_trends.resultant_test;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Evgeniy on 08.02.2018.
 */

public class CurrencyArrayAdapter extends ArrayAdapter<СurrencyClass> {
    private Context appContext = null;
    private ArrayList<СurrencyClass> items = null;
    final String LOG_TAG = "myLogs";


    public CurrencyArrayAdapter(Context context, int textViewResourceId, ArrayList<СurrencyClass> items){
        super(context,textViewResourceId,items);
        this.appContext = context;
        this.items=items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.listview_currenty_adapter, null);
        }

        СurrencyClass o = items.get(position);
        if (o != null) {
            TextView tv_name = (TextView) v.findViewById(R.id.textViewName);
            TextView tv_volume = (TextView) v.findViewById(R.id.textViewVolume);
            TextView tv_amount = (TextView) v.findViewById(R.id.textViewAmount);

            if (tv_name != null) {
                tv_name.setText(o.GetName());
            }
            if(tv_volume != null){
                tv_volume.setText(o.GetVolume().toString());
            }
            if(tv_amount != null){
                tv_amount.setText(o.GetAmount().toString());
            }

        }
        return v;
    }
}
