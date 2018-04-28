package com.igmasiri.calendario.calendario;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy", Locale.getDefault());
    private CompactCalendarView compactCalendarView;
    private boolean isExpanded = false;

    //recycler view
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    List<String> myDataset = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setea la toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //titulo arriba de la fecha en el toolbar
        setTitle("CompactCalendarViewToolbar");

        //referencia los elementos
        appBarLayout = findViewById(R.id.app_bar_layout);
        compactCalendarView = findViewById(R.id.compactcalendar_view);
        final ImageView arrow = findViewById(R.id.date_picker_arrow);
        RelativeLayout datePickerButton = findViewById(R.id.date_picker_button);

        // Force English
        compactCalendarView.setLocale(TimeZone.getDefault(), Locale.getDefault());

        //el calendario usa 3 letras para cada dia de la semana
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        //indica que se deben mostrar los dias de la semana en el headder.
        compactCalendarView.setShouldDrawDaysHeader(true);

        //set obn lcick listener para la seleccion de la fecha en el calendario
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                setSubtitle(dateFormat.format(dateClicked));
                myDataset.add("c");
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                setSubtitle(dateFormat.format(firstDayOfNewMonth));
            }
        });

        //setea un evento en la fecha seleccionada
        // Add event 1 on Sun, 07 Jun 2015 18:20:51 GMT
        try {
            Event ev1 = new Event(Color.RED, new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse("24/04/2018").getTime(), "Some extra data that I want to store.");
            compactCalendarView.addEvent(ev1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Set current date to today
        setCurrentDate(new Date());

        //sete al click listener para el boton del calendario
        datePickerButton.setOnClickListener(v -> {
            float rotation = isExpanded ? 0 : 180;
            ViewCompat.animate(arrow).rotation(rotation).start();
            isExpanded = !isExpanded;
            appBarLayout.setExpanded(isExpanded, true);
        });

        //recycler view
        myDataset.add("a");
        myDataset.add("b");
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void setCurrentDate(Date date) {
        setSubtitle(dateFormat.format(date));
        if (compactCalendarView != null) {
            compactCalendarView.setCurrentDate(date);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        TextView tvTitle = findViewById(R.id.title);
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    private void setSubtitle(String subtitle) {
        TextView datePickerTextView = findViewById(R.id.date_picker_text_view);
        if (datePickerTextView != null) {
            datePickerTextView.setText(subtitle);
        }
    }

}
