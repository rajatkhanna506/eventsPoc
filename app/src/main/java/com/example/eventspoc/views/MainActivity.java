package com.example.eventspoc.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.eventspoc.Adapter.EventsAdapter;
import com.example.eventspoc.R;
import com.example.eventspoc.models.Embedded;
import com.example.eventspoc.models.Event;
import com.example.eventspoc.models.GetAllEvents;
import com.example.eventspoc.retrofit.ApiClass;
import com.example.eventspoc.retrofit.ApiInterface;
import com.example.eventspoc.utility.util;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ApiInterface apiInterface = null;
    ArrayList<Event> eventsList;
    EventsAdapter adapter;
    ListView lvEventslist;
    ProgressDialog progressDialog;
    TextView tvRadius;
    TextView tvStartDate;
    TextView tvEndDate;
    TextView tvFilterData;
    Boolean radiusFlag = false;
    Boolean dateFlag = false;
    AlertDialog.Builder alertDialogBuilder;
    String radius;
    String unit;
    int mYear, mMonth, mDay;
    String start;
    String end;
    String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvEventslist = findViewById(R.id.lvEvents);
        tvStartDate = findViewById(R.id.tvStartDate);
        tvEndDate = findViewById(R.id.tvEndDate);
        tvRadius = findViewById(R.id.tvRadius);
        tvStartDate.setOnClickListener(this);
        tvEndDate.setOnClickListener(this);
        tvFilterData = findViewById(R.id.tvFilterData);
        tvRadius.setOnClickListener(this);
        tvFilterData.setOnClickListener(this);
        eventsList = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        final Calendar myCalendar = Calendar.getInstance();

        getEventList();


    }

    public void getEventList() {

        cityName = "chicago";
        apiInterface = ApiClass.getClient(getApplicationContext()).create(ApiInterface.class);
        Call<GetAllEvents> call = apiInterface.getEvents(util.api_key, radius, unit, start, end,cityName);
        call.enqueue(new Callback<GetAllEvents>() {
            @Override
            public void onResponse(Call<GetAllEvents> call, Response<GetAllEvents> response) {

                if (response.isSuccessful()) {

                    Log.d("ResponseCode", "ResponseCode" + response.code());


                    GetAllEvents getAllEvents = response.body();
                    Embedded embedded = getAllEvents.getEmbedded();
                    List<Event> events = embedded.getEvents();
                    eventsList.addAll(events);
                    Log.d("TAG", "size" + eventsList.size());
                    adapter = new EventsAdapter(MainActivity.this, eventsList);
                    lvEventslist.setAdapter(adapter);
                    start = null;
                    end = null;
                    radius = null;
                    Log.d("TAG", "topic list size" + getAllEvents);


                }
            }

            @Override
            public void onFailure(Call<GetAllEvents> call, Throwable t) {

            }


        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvRadius:
//                if (radiusFlag) {
//                    tvRadius.setBackgroundColor(Color.TRANSPARENT);
//                    radiusFlag = false;
//                    radius = null;
//                } else {
//                    radiusFlag = true;
//                    tvRadius.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_input, null);
                alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setView(viewInflated);
                final EditText userInput = (EditText) viewInflated.findViewById(R.id.etRadius);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        radius = userInput.getText().toString();
                                        Log.d("TAG", "data" + userInput.getText().toString());
                                        unit = "km";


                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

//                }


                break;
            case R.id.tvStartDate:
                openStartCalendar(v);
                break;
            case R.id.tvEndDate:
                openEndCalendar(v);
                break;
            case R.id.tvFilterData:
                if (eventsList.size() > 0) {
                    eventsList.clear();
                }
                getEventList();
        }
    }

    public String openStartCalendar(View v) {
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {


                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        start = sdf.format(calendar.getTime());
                        Log.d("TAG", "date" + start);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();

        return start;
    }

    public String openEndCalendar(View v) {
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {


                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        end = sdf.format(calendar.getTime());
                        Log.d("TAG", "endDate" + end);


                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();

        return end;
    }
}

