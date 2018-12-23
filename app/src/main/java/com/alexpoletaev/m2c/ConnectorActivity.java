package com.alexpoletaev.m2c;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.alexpoletaev.m2c.model.DateRequest;
import com.alexpoletaev.m2c.model.DateTransfer;
import com.alexpoletaev.m2c.model.ErrorResponse;
import com.alexpoletaev.m2c.model.ReportResponse;
import com.alexpoletaev.m2c.service.ReportService;
import com.alexpoletaev.m2c.utils.ConnectorUtils;
import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConnectorActivity extends AppCompatActivity {
    public static final String REPORT_RESPONSE_LIST = "com.alexpoletaev.m2c.reportResponseList";
    private String siteUrl;
    private String accessToken;
    private Date fromDate;
    private Date toDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connector);

        Intent intent = getIntent();
        this.siteUrl = intent.getStringExtra(MainActivity.SITE_URL);
        //this.siteUrl = "http://10.0.2.2/"; //TODO FOR EMULATOR TESTING ONLY. REMOVE.
        this.accessToken = intent.getStringExtra(MainActivity.ACCESS_TOKEN);

        initCalendar();
    }

    public void initCalendar() {
        Calendar fromDate = Calendar.getInstance();
        fromDate.add(Calendar.YEAR, -1);
        Calendar toDate = (Calendar) fromDate.clone();
        toDate.add(Calendar.YEAR, 1);

        DateRangeCalendarView dateRangeCalendarView = findViewById(R.id.calendar);
        dateRangeCalendarView.setSelectedDateRange(fromDate, toDate);

        dateRangeCalendarView.setCalendarListener(new DateRangeCalendarView.CalendarListener() {
            @Override
            public void onFirstDateSelected(Calendar startDate) {
                // do nothing
            }

            @Override
            public void onDateRangeSelected(Calendar fromDate, Calendar toDate) {
                ConnectorActivity.this.fromDate = fromDate.getTime();
                ConnectorActivity.this.toDate = toDate.getTime();
                DateRangeCalendarView dateRangeCalendarView = findViewById(R.id.calendar);
                dateRangeCalendarView.setVisibility(View.GONE);
                findViewById(R.id.apiList).setVisibility(View.VISIBLE);
                findViewById(R.id.period).setVisibility(View.VISIBLE);
            }
        });
    }

    public void setPeriod(View view) {
        findViewById(R.id.apiList).setVisibility(View.GONE);
        findViewById(R.id.period).setVisibility(View.GONE);
        DateRangeCalendarView dateRangeCalendarView = findViewById(R.id.calendar);
        dateRangeCalendarView.resetAllSelectedViews();
        dateRangeCalendarView.setVisibility(View.VISIBLE);
    }

    public void getAllReports(View view) {
        Retrofit retrofit = ConnectorUtils.getRetrofitInstance(this.siteUrl);
        ReportService reportService = retrofit.create(ReportService.class);
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        DateTransfer dateTransfer = new DateTransfer(
            this.fromDate != null ? dateFormat.format(this.fromDate) : dateFormat.format(date),
            this.toDate != null ? dateFormat.format(this.toDate) : dateFormat.format(date),
            ConnectorUtils.DEFAULT_PERIOD
        );
        DateRequest dateRequest = new DateRequest(dateTransfer);
        Call<List<ReportResponse>> allReports = reportService.getAllReports(
            ConnectorUtils.DEFAULT_CONTENT_TYPE,
            "Bearer " + accessToken,
            ConnectorUtils.DEFAULT_STORE_CODE,
            dateRequest
        );

        allReports.enqueue(new Callback<List<ReportResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ReportResponse>> call, @NonNull Response<List<ReportResponse>> response) {
                if (response.isSuccessful()) {
                    List<ReportResponse> reportResponseList = response.body();
                    if (reportResponseList != null) {
                        Intent intent = new Intent(ConnectorActivity.this, ReportResponseActivity.class);
                        intent.putExtra(REPORT_RESPONSE_LIST, (Serializable) reportResponseList);
                        startActivity(intent);
                    }
                }else {
                    try {
                        Gson gson = new GsonBuilder().setLenient().create();
                        String error = Objects.requireNonNull(response.errorBody()).string();
                        ErrorResponse errorResponse = gson.fromJson(error, ErrorResponse.class);
                        Toast
                            .makeText(ConnectorActivity.this, errorResponse.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ReportResponse>> call, Throwable t) {
                Toast
                    .makeText(
                        ConnectorActivity.this,
                        "Something went wrong...Error message: " + t.getMessage(),
                        Toast.LENGTH_SHORT
                    )
                    .show();
            }
        });
    }
}
