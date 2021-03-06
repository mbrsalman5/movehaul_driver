package com.movhaul.driver;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import com.rey.material.widget.LinearLayout;
import com.rey.material.widget.ListView;
import com.sloop.fonts.FontsManager;

import java.util.ArrayList;

/**
 * Created by sqindia on 01-11-2016.
 * customer review
 * it is in demo mode
 */

public class Reviews extends Activity {
    ListView lv_payment_list;
    LinearLayout btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_review);
        FontsManager.initFormAssets(this, "fonts/lato.ttf");       //initialization
        FontsManager.changeFonts(this);
        lv_payment_list = (ListView) findViewById(R.id.listview_payment);
        btn_back = (LinearLayout) findViewById(R.id.layout_back);
        final ArrayList<String> payment_arlist = new ArrayList<>();
       // ht_arlist = new ArrayList<>();
        CustomerReviewsAdapter adapter = new CustomerReviewsAdapter(Reviews.this, payment_arlist);
        lv_payment_list.setAdapter(adapter);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent i = new Intent(Reviews.this,DashboardNavigation.class);
                startActivity(i);*/
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
       // super.onBackPressed();
       /* Intent i = new Intent(Reviews.this,DashboardNavigation.class);
        startActivity(i);*/
        finish();
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
