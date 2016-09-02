package com.gocgod.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gocgod.ui.optional.AgentLocationActivity;
import com.gocgod.ui.optional.FaqActivity;
import com.gocgod.ui.optional.HowToBuyActivity;
import com.gocgod.R;
import com.gocgod.ui.user.LoginActivity;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public abstract class BaseActivity extends AppCompatActivity {
    protected Drawer drawer = null;
    protected Toolbar toolbar;
    protected ActionBar actionBar;

    Integer mCartCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buildToolbar(String title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if(title != null)
            actionBar.setTitle(title);
    }


    public void buildDrawer(Bundle savedInstanceState, Toolbar toolbar)
    {
        PrimaryDrawerItem product = new PrimaryDrawerItem().withName(R.string.product)
                .withIdentifier(1)
                .withIcon(GoogleMaterial.Icon.gmd_free_breakfast);
        PrimaryDrawerItem howtobuy = new PrimaryDrawerItem().withName(R.string.howToBuy)
                .withIdentifier(2)
                .withIcon(GoogleMaterial.Icon.gmd_info);
        PrimaryDrawerItem faq = new PrimaryDrawerItem().withName(R.string.faq)
                .withIdentifier(3)
                .withIcon(GoogleMaterial.Icon.gmd_help);
        PrimaryDrawerItem location = new PrimaryDrawerItem().withName(R.string.location)
                .withIdentifier(4)
                .withIcon(GoogleMaterial.Icon.gmd_location_city);
        PrimaryDrawerItem login = new PrimaryDrawerItem().withName(R.string.login_user)
                .withIdentifier(20)
                .withTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .withIcon(GoogleMaterial.Icon.gmd_lock);

        //menu header
        AccountHeader header = new AccountHeaderBuilder()
                .withActivity(this)
                .withSavedInstance(savedInstanceState)
                .withHeaderBackground(R.color.colorPrimary)
                .withCompactStyle(true)
                .addProfiles(
                        new ProfileDrawerItem().withIcon(R.drawable.logo_1).withName("Smiley")
                                .withEmail("smiley@smiley.com")
                )

                .withSelectionListEnabledForSingleProfile(false)
                .build();

        //create the drawer and remember the `Drawer` result object
        drawer = new DrawerBuilder()
                .withSelectedItem(-1)
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(header)
                .addDrawerItems(
                        product,
                        howtobuy,
                        location,
                        faq
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        if(drawerItem != null)
                        {
                            if(drawerItem.getIdentifier() == 1){
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                            else if(drawerItem.getIdentifier() == 2){
                                startActivity(new Intent(getApplicationContext(), HowToBuyActivity.class));
                                finish();
                            }
                            else if(drawerItem.getIdentifier() == 3){
                                startActivity(new Intent(getApplicationContext(), FaqActivity.class));
                                finish();
                            }
                            else if(drawerItem.getIdentifier() == 4){
                                startActivity(new Intent(getApplicationContext(), AgentLocationActivity.class));
                                finish();
                            }
                            else if(drawerItem.getIdentifier() == 20){
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            }
                        }
                        return false;
                    }
                })
                .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
                    @Override
                    public boolean onNavigationClickListener(View clickedView) {
                        //this method is only called if the Arrow icon is shown. The hamburger is automatically managed by the MaterialDrawer
                        //if the back arrow is shown. close the activity
                        finish();
                        //return true if we have consumed the event
                        return true;
                    }
                })
                .addStickyDrawerItems(login)
                .build();
    }
}
