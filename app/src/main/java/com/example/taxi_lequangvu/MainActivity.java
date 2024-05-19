package com.example.taxi_lequangvu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText searchInput;
    private ListView listView;
    ArrayList<HoaDonTaxi> list = new ArrayList<>();
    private FloatingActionButton buttonAdd;
    private TaxiAdapter adapter;
    private TaxiDatabase db;
    private HoaDonTaxi selectedHoaDonTaxi;
    private int selectedId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new TaxiDatabase(this, "SqliteDB_05052003", null, 1);
        searchInput = findViewById(R.id.search_input);
        listView = findViewById(R.id.list_view);
        buttonAdd = findViewById(R.id.button_add);

        list = db.getAllRecords();
//        getApplicationContext().deleteDatabase("SqliteDB_05052003");
        if(list.size() == 0){
            db.addRecord(new HoaDonTaxi("1D-283.34",12,8800,5));
            db.addRecord(new HoaDonTaxi("4D-283.34",11.5,8800,5));
            db.addRecord(new HoaDonTaxi("6D-283.34",16,8800,5));
            db.addRecord(new HoaDonTaxi("2D-283.34",5,10000,5));
            db.addRecord(new HoaDonTaxi("724-17",13,8800,5));
            db.addRecord(new HoaDonTaxi("0D-283.34",8,8800,5));
        }


        list = db.getAllRecords();
        adapter = new TaxiAdapter(list,this);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedId = position;
                selectedHoaDonTaxi = list.get(position);
                return false;
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MutateActivity.class);
                startActivityForResult(intent, 150);
            }
        });

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.list_view_options, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.option_edit){
            Intent intent = new Intent(MainActivity.this, MutateActivity.class);
            Bundle b = new Bundle();
            b.putString("plateNumber", selectedHoaDonTaxi.getPlateNumber());
            b.putDouble("distance", selectedHoaDonTaxi.getDistance());
            b.putInt("price", selectedHoaDonTaxi.getPrice());
            b.putInt("discount", selectedHoaDonTaxi.getDiscountPercent());
            intent.putExtras(b);
            startActivityForResult(intent, 100);
        }

        if(id == R.id.option_delete){
            showDeleteConfirmationDialog();
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.sort_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.sort_by_plate_number){
            adapter.sortByPlateNumber();
            Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
        }

        if(id == R.id.sort_by_total_price){
            adapter.sortByTotalPrice();
            Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
        }
        adapter.notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Bạn có chắc chắn muốn xóa không? " + selectedHoaDonTaxi.getId());
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                db.deleteRecord(selectedId);
                list.remove(selectedId);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle b = data.getExtras();
        String plateNumber = b.getString("plateNumber");
        Double distance = b.getDouble("distance");
        int price = b.getInt("price");
        int discount = b.getInt("discount");
        HoaDonTaxi h = new HoaDonTaxi(plateNumber, distance, price, discount);

        if(requestCode == 150 && resultCode == 200){
            db.addRecord(h);
            list.add(h);
            adapter.notifyDataSetChanged();
        }else if(requestCode == 100 && resultCode == 200){
            db.updateRecord(h, selectedId);
            list.set(selectedId, h);
            adapter.notifyDataSetChanged();
        }
    }


}