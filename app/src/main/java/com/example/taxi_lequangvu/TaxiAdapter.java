package com.example.taxi_lequangvu;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TaxiAdapter extends BaseAdapter implements Filterable {
    private ArrayList<HoaDonTaxi> list;
    private Activity context;
    private LayoutInflater inflater;
    private ArrayList<HoaDonTaxi> databackup;

    public TaxiAdapter(ArrayList<HoaDonTaxi> list, Activity context) {
        this.list = list;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View v = view;
        String packageName = context.getPackageName();
        if(v == null){
            v = inflater.inflate(R.layout.hoadontaxi_item,null);
        }

        TextView plateNumberTextView = v.findViewById(R.id.plate_number_text);
        TextView distanceTextView = v.findViewById(R.id.distance_text);
        TextView totalPriceTextView = v.findViewById(R.id.total_price_text);
        plateNumberTextView.setText(list.get(position).getPlateNumber());
        distanceTextView.setText("Quãng đường: " + list.get(position).getDistance() + " km");
        totalPriceTextView.setText(list.get(position).getTotalPrice()+"");

        return v;
    }

    @Override
    public Filter getFilter() {
        Filter f = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults fr = new FilterResults();
                if(databackup == null){
                    databackup = new ArrayList<>(list);
                }

                if(constraint == null || constraint.length() == 0){
                    fr.count = databackup.size();
                    fr.values = databackup;
                }else{
                    ArrayList<HoaDonTaxi> newdata = new ArrayList<>();
                    for(HoaDonTaxi h: databackup){
                        if(h.getPlateNumber().toLowerCase().contains(constraint.toString().toLowerCase())){
                            newdata.add(h);

                        }
                    }
                    fr.count = newdata.size();
                    fr.values = newdata;
                }
                return fr;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = new ArrayList<HoaDonTaxi>();
                ArrayList<HoaDonTaxi> tmp = (ArrayList<HoaDonTaxi>) results.values;
                for(HoaDonTaxi h: tmp){
                    list.add(h);
                }
                notifyDataSetChanged();
            }
        };
        return f;
    }

    public ArrayList<HoaDonTaxi> sortByTotalPrice(){
        Collections.sort(list, new Comparator<HoaDonTaxi>() {
            @Override
            public int compare(HoaDonTaxi h1, HoaDonTaxi h2) {
                return Double.compare(h1.getTotalPrice(), h2.getTotalPrice());
            }
        });
        return list;
    }
    public ArrayList<HoaDonTaxi> sortByPlateNumber(){
        Collections.sort(list, new Comparator<HoaDonTaxi>() {
            @Override
            public int compare(HoaDonTaxi h1, HoaDonTaxi h2) {
                return h1.getPlateNumber().compareTo(h2.getPlateNumber());
            }
        });
        return list;
    }

}
