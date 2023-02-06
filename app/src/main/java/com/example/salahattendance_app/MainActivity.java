package com.example.salahattendance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
        Fajar = findViewById(R.id.checkFajar);
        Zuhr = findViewById(R.id.checkZuhr);
        Asar = findViewById(R.id.checkAsar);
        Maghrib = findViewById(R.id.checkMaghrib);
        Esha = findViewById(R.id.checkEsha);
        btn = findViewById(R.id.btnSubmit);
        txt = findViewById(R.id.textView);
        date.setOnClickListener(this);
        btn.setOnClickListener(this);

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

        }
    }
    public int getValue(Boolean prayer){
        if (prayer)
            return 1;
        else
            return 0;
    }
}