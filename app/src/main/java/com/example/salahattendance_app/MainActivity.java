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
    TextView txt;
    int mYear, mMonth, mDay;
    CheckBox Fajar, Zuhr, Asar, Maghrib, Esha;
    Button btn;
    DatabaseHelper db = new DatabaseHelper(this);

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
        txt = findViewById(R.id.textview);
        date.setOnClickListener(this);

        ContentValues rowData = db.getEntriesWithDate((date.getText()).toString());
        if (rowData.size() > 0) {
            // display the data on the TextView
            Set<Map.Entry<String, Object>> entrySet = rowData.valueSet();
            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry<String, Object> entry : entrySet) {
                stringBuilder.append(entry.getKey());
                stringBuilder.append(": ");
                stringBuilder.append(entry.getValue().toString());
                stringBuilder.append("\n");
                Log.d(TAG, entry.getKey() + ": " + entry.getValue().toString());
            }
            txt.setText(stringBuilder.toString());
            btn.setVisibility(View.INVISIBLE);
        }
        else {
            btn.setOnClickListener(this);
        }


    }

    @Override
    public void onClick(View view) {

        if (view == date) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view1, year, monthOfYear, dayOfMonth) -> date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year), mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if (view == btn){
            MyData data = new MyData((date.getText()).toString(), getValue(Fajar.isChecked()), getValue(Zuhr.isChecked()), getValue(Asar.isChecked()), getValue(Maghrib.isChecked()), getValue(Esha.isChecked()));
            db.insertData(data);
            Toast.makeText(getApplicationContext(), "Data Added", Toast.LENGTH_SHORT).show();
        }
    }
    public int getValue(Boolean prayer){
        if (prayer)
            return 1;
        else
            return 0;
    }
    public boolean getStatus(int prayer){
        if (prayer == 1)
            return true;
        else
            return false;
    }
}