package com.alexpoletaev.m2c;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alexpoletaev.m2c.model.ReportResponse;
import com.alexpoletaev.m2c.model.ReportResponseEntity;

import java.util.List;

public class ReportResponseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_response);
        Intent intent = getIntent();
        List<ReportResponse> reportResponseList = (List<ReportResponse>) intent.getSerializableExtra(ConnectorActivity.REPORT_RESPONSE_LIST);

        LinearLayout siteListLayout = findViewById(R.id.reportResponse);
        reportResponseList.forEach(responseRow -> {
            LinearLayout periodGroup = new LinearLayout(ReportResponseActivity.this);

            ListView listView = new ListView(ReportResponseActivity.this);
            List<ReportResponseEntity> entityList = responseRow.getItems();

            ArrayAdapter<ReportResponseEntity> adapter = new ArrayAdapter<ReportResponseEntity>(
                    ReportResponseActivity.this,
                    android.R.layout.simple_list_item_2,
                    android.R.id.text1, entityList
            ) {
                @NonNull
                @Override
                public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = view.findViewById(android.R.id.text1);
                    TextView text2 = view.findViewById(android.R.id.text2);

                    text1.setText(entityList.get(position).getPeriod());
                    text2.setText(getString(
                        R.string.report_response_row,
                        entityList.get(position).getValue()
                    ));
                    return view;
                }
            };

            listView.setAdapter(adapter);
            ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.listview_header_report_response, listView, false);
            TextView headerTextView = headerView.findViewById(R.id.reportResponseHeader);
            headerTextView.setText(responseRow.getLabel());
            listView.addHeaderView(headerView);

            periodGroup.addView(listView);

            siteListLayout.addView(periodGroup);
        });
        siteListLayout.setVisibility(View.VISIBLE);
    }
}
