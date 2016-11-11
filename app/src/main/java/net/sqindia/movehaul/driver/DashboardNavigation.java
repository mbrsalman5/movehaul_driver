package net.sqindia.movehaul.driver;


import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;
import com.sloop.fonts.FontsManager;

/**
 * Created by SQINDIA on 10/26/2016.
 */

public class DashboardNavigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    Context mContext;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    Button btn_submit, btn_book_later;
    TextView tv_name, tv_email, nav_tv_mytrips, nav_tv_profile, nav_tv_reviews, tv_tracking, nav_tv_payments, tv_Bankdetails;
    AutoCompleteTextView starting, destination;
    TextInputLayout flt_pickup, flt_droplocation;
    FloatingActionButton fab_truck;
    ImageView pickup_close, btn_menu, rightmenu;
    android.widget.LinearLayout droplv, pickuplv;
    private ViewFlipper mViewFlipper;
    Dialog dialog1;
    GPSTracker gps;
    Button btn_yes,btn_no;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        FontsManager.initFormAssets(this, "fonts/lato.ttf");
        FontsManager.changeFonts(this);

        gps = new GPSTracker(DashboardNavigation.this);

        DashboardNavigation.this.registerReceiver(this.getLocation_Receiver, new IntentFilter("appendGetLocation"));


        mContext = this;
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        tv_name = (TextView) findViewById(R.id.textView_name);
        tv_email = (TextView) findViewById(R.id.textView_email);
       // tv_myTrips = (TextView) findViewById(R.id.textView_mytrips);
        nav_tv_profile = (TextView) findViewById(R.id.textview_profile);
        nav_tv_mytrips = (TextView) findViewById(R.id.textview_mytrips);
        nav_tv_reviews = (TextView) findViewById(R.id.textview_reviews);
        nav_tv_payments = (TextView) findViewById(R.id.textview_payments);
        tv_Bankdetails = (TextView) findViewById(R.id.textView_bankdetails);
        btn_menu = (ImageView) findViewById(R.id.img_menu);
        rightmenu = (ImageView) findViewById(R.id.right_menu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        btn_submit = (Button) findViewById(R.id.button_submit);

        mViewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);

        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        int[] resources = {
                R.drawable.banner_bg,
                R.drawable.banner_bg1
        };

        mViewFlipper.setInAnimation(this, R.anim.anim1);
        mViewFlipper.setOutAnimation(this, R.anim.anim2);




        // Add all the images to the ViewFlipper
        for (int i = 0; i < resources.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageDrawable(getResources().getDrawable(resources[i]));
            mViewFlipper.addView(imageView);

        }
        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(25000);


        tv_Bankdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Bank_details.class);
                startActivity(i);
            }
        });
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        dialog1 = new Dialog(DashboardNavigation.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog1.setCancelable(false);
        dialog1.setContentView(R.layout.dialog_yes_no);
        btn_yes = (Button) dialog1.findViewById(R.id.button_yes);
        btn_no = (Button) dialog1.findViewById(R.id.button_no);

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });

        nav_tv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goProfile = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(goProfile);
                drawer.closeDrawer(Gravity.LEFT);
            }
        });
        nav_tv_mytrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goProfile = new Intent(getApplicationContext(),MyTrips.class);
                startActivity(goProfile);
                drawer.closeDrawer(Gravity.LEFT);
            }
        });
        nav_tv_payments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goPayments = new Intent(getApplicationContext(),Payment.class);
                startActivity(goPayments);
                drawer.closeDrawer(Gravity.LEFT);
            }
        });
        nav_tv_reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goReviews = new Intent(getApplicationContext(),Reviews.class);
                startActivity(goReviews);
                drawer.closeDrawer(Gravity.LEFT);
            }
        });




        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goPostings = new Intent(getApplicationContext(),JobPosting.class);
                startActivity(goPostings);
            }
        });






        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });

        rightmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContextMenu(view);


                PopupMenu popup = new PopupMenu(DashboardNavigation.this, rightmenu);

                popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());


                Menu m = popup.getMenu();
                for (int i = 0; i < m.size(); i++) {
                    MenuItem mi = m.getItem(i);

                    //for aapplying a font to subMenu ...
                    SubMenu subMenu = mi.getSubMenu();
                    if (subMenu != null && subMenu.size() > 0) {
                        for (int j = 0; j < subMenu.size(); j++) {
                            MenuItem subMenuItem = subMenu.getItem(j);
                            applyFontToMenuItem(subMenuItem);
                        }
                    }


                    //the method we have create in activity
                    applyFontToMenuItem(mi);

                }


                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.item1: {

                                return true;
                            }
                            case R.id.item2: {

                                return true;
                            }

                            default: {
                                return true;
                            }

                        }


                    }
                });

                popup.show();


            }
        });




    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);


    }


    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/lato.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }


    BroadcastReceiver getLocation_Receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {




            Bundle b = intent.getExtras();

            String asdf = b.getString("latitude");
            String ct_message = b.getString("longitude");

            String latitude = asdf;
            String longitude = ct_message;
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();



        }
    };

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        dialog1.show();
    }
}
