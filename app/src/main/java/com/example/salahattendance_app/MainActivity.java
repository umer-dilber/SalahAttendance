package com.example.salahattendance_app;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText date;
    int mYear, mMonth, mDay;
    CheckBox Fajar, Zuhr, Asar, Maghrib, Esha;
    Button btn;
    DatabaseHelper db = new DatabaseHelper(this);
    boolean flag;
    ContentValues rowData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        date = findViewById(R.id.Date);
        String dateStr = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        date.setText(dateStr);
        Fajar = findViewById(R.id.checkFajar);
        Zuhr = findViewById(R.id.checkZuhr);
        Asar = findViewById(R.id.checkAsar);
        Maghrib = findViewById(R.id.checkMaghrib);
        Esha = findViewById(R.id.checkEsha);
        btn = findViewById(R.id.btnSubmit);
        date.setOnClickListener(this);
        checkAgainstDate();
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view == date) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                    (view1, year, month, day) -> {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, day);
                        if (selectedDate.after(c)) {
                            Toast.makeText(MainActivity.this, "Selected date can't be greater than today's date.", Toast.LENGTH_SHORT).show();
                        } else {
                            String Locale = "";
                            date.setText(String.format(Locale,"%02d-%02d-%04d", day, month + 1, year));
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if (view == btn){
            MyData data = new MyData((date.getText()).toString(), getValue(Fajar.isChecked()), getValue(Zuhr.isChecked()), getValue(Asar.isChecked()), getValue(Maghrib.isChecked()), getValue(Esha.isChecked()));
            flag = db.insertData(data);
            if (!flag){
                checkAgainstDate();
                Toast.makeText(getApplicationContext(), "Data has been already added for this day!", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getApplicationContext(), "Attendance submitted!", Toast.LENGTH_SHORT).show();
        }
    }
    public void checkAgainstDate(){
        rowData = db.getEntriesWithDate((date.getText()).toString());
        if (rowData.size() > 0) {
            int faj = rowData.getAsInteger("Fajar");
            Check(Fajar, faj);
            int zh = rowData.getAsInteger("Zuhr");
            Check(Zuhr, zh);
            int as = rowData.getAsInteger("Asar");
            Check(Asar, as);
            int mag = rowData.getAsInteger("Maghrib");
            Check(Maghrib, mag);
            int es = rowData.getAsInteger("Esha");
            Check(Esha, es);
        }
    }
    public void Check(CheckBox prayer, int val){
        prayer.setChecked(val == 1);
    }
    public int getValue(Boolean prayer){
        if (prayer)
            return 1;
        else
            return 0;
    }
    
    
}