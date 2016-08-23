package com.gocgod;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public abstract class BaseActivity extends AppCompatActivity {
    protected Drawer drawer;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*buildToolbar();
        buildDrawer(savedInstanceState);*/
    }

    public void buildToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void buildDrawer(Bundle savedInstanceState)
    {
        PrimaryDrawerItem product = new PrimaryDrawerItem().withName(R.string.product)
                .withIdentifier(1)
                .withIcon(GoogleMaterial.Icon.gmd_free_breakfast);
        PrimaryDrawerItem howtobuy = new PrimaryDrawerItem().withName(R.string.howToBuy)
                .withIdentifier(2)
                .withSelectable(false)
                .withIcon(GoogleMaterial.Icon.gmd_info);
        PrimaryDrawerItem faq = new PrimaryDrawerItem().withName(R.string.faq)
                .withIdentifier(3)
                .withIcon(GoogleMaterial.Icon.gmd_help);


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
                .withAccountHeader(header)
                .addDrawerItems(
                        product,
                        howtobuy,
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
                            else if(drawerItem.getIdentifier() == 3){
                                startActivity(new Intent(getApplicationContext(), com.gocgod.Faq.class));
                                finish();
                            }
                        }
                        return false;
                    }
                })
                .build();
    }
}
