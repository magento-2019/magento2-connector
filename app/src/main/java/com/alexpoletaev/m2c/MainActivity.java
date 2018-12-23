package com.alexpoletaev.m2c;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String SITE_URL = "com.alexpoletaev.m2c.siteUrl";
    public static final String ACCESS_TOKEN = "com.alexpoletaev.m2c.accessToken";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout siteListLayout = findViewById(R.id.siteList);

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        sharedPreferences.getAll().forEach((url, token) -> {
            TextView dynamicTextView = new TextView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0,
                    1f
            );
            dynamicTextView.setLayoutParams(layoutParams);
            dynamicTextView.setId(View.generateViewId());
            dynamicTextView.setText(url);
            dynamicTextView.setGravity(Gravity.CENTER);
            dynamicTextView.setTextColor(getColor(R.color.colorPrimary));
            dynamicTextView.setPadding(15,15,15,15);
            dynamicTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            dynamicTextView.setBackgroundResource(R.drawable.textview_border_bottom);
            dynamicTextView.setSingleLine(false);

            dynamicTextView.setOnClickListener(v -> {
                Intent intent = new Intent(this, ConnectorActivity.class);
                intent.putExtra(SITE_URL, url);
                intent.putExtra(ACCESS_TOKEN, token.toString());
                startActivity(intent);
            });
            dynamicTextView.setOnLongClickListener(v -> {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setMessage("Are you sure you want to delete this record?");
                alert.setPositiveButton("YES", (dialog, which) -> {
                    sharedPreferences.edit().remove(url).apply();
                    dialog.dismiss();
                    finish();
                    startActivity(getIntent());
                });
                alert.setNegativeButton("NO", (dialog, which) -> dialog.dismiss());
                alert.show();

                return true;
            });
            siteListLayout.addView(dynamicTextView);
        });
    }

    public void submit(View view) {
        EditText siteUrlView = findViewById(R.id.siteUrl);
        EditText accessTokenView = findViewById(R.id.accessToken);
        String siteUrl = siteUrlView.getText().toString();
        String accessToken = accessTokenView.getText().toString();
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(siteUrl, accessToken).apply();

        findViewById(R.id.addSiteForm).setVisibility(View.GONE);

        finish();
        startActivity(getIntent());
    }

    public void triggerSiteAddingForm(View view) {
        View form = findViewById(R.id.addSiteForm);
        LinearLayout siteListLayout = findViewById(R.id.siteList);
        form.setVisibility(form.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        siteListLayout.setVisibility(siteListLayout.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        Button button = findViewById(R.id.triggerSiteAddingForm);
        button.setText(button.getText().equals(getString(R.string.add_site)) ? R.string.close : R.string.add_site);
    }
}
