package com.gocgod.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gocgod.ApiService;
import com.gocgod.ServiceGenerator;
import com.gocgod.model.ResponseSuccess;
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
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.securepreferences.SecurePreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseActivity extends AppCompatActivity {
    private AccountHeader header;

    protected Drawer sideBar = null;
    protected Toolbar toolbar;
    protected ActionBar actionBar;
    protected SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = new SecurePreferences(getApplicationContext());
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
        Boolean is_login = sharedPreferences.getBoolean("is_login", false);

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
        PrimaryDrawerItem logout = new PrimaryDrawerItem().withName(R.string.logout_user)
                .withIdentifier(21)
                .withTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .withIcon(GoogleMaterial.Icon.gmd_exit_to_app);

        //menu header
        if(is_login)
        {
            header = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withSavedInstance(savedInstanceState)
                    .withHeaderBackground(R.color.colorPrimary)
                    .withCompactStyle(true)
                    .addProfiles(
                            new ProfileDrawerItem().withIcon(R.drawable.logo_1)
                                    .withName(sharedPreferences.getString("name", "Customer"))
                                    .withEmail(sharedPreferences.getString("email", null)),
                            new ProfileSettingDrawerItem()
                                    .withName(getResources().getString(R.string.profile))
                                    .withIcon(GoogleMaterial.Icon.gmd_account_circle)
                                    .withIdentifier(31),
                            new ProfileSettingDrawerItem()
                                    .withName(getResources().getString(R.string.change_password))
                                    .withIcon(GoogleMaterial.Icon.gmd_vpn_key)
                                    .withIdentifier(32)
                    )
                    .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                        @Override
                        public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                            Intent intent = null;

                            if (profile.getIdentifier() == 31) {
                                //intent = new Intent(MainActivity.this, ProfileActivity.class);
                            } else if(profile.getIdentifier() == 32){
                                
                            }

                            if(intent != null)
                                startActivity(intent);

                            return false;
                        }
                    })
                    .withSelectionListEnabledForSingleProfile(false)
                    .build();
        }
        else
        {
            header = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withSavedInstance(savedInstanceState)
                    .withHeaderBackground(R.color.colorPrimary)
                    .withCompactStyle(true)
                    .addProfiles(
                            new ProfileDrawerItem().withIcon(R.drawable.logo_1)
                    )
                    .withSelectionListEnabledForSingleProfile(false)
                    .build();
        }

        //create the drawer and remember the `Drawer` result object
        DrawerBuilder drawerBuilder = new DrawerBuilder()
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
                            else if(drawerItem.getIdentifier() == 21){
                                logout();
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
                });

        if(is_login)
        {
            drawerBuilder.addStickyDrawerItems(logout);
        }
        else
        {
            drawerBuilder.addStickyDrawerItems(login);
        }

        sideBar = drawerBuilder.build();
    }

    public void logout()
    {
        final SecurePreferences sharedPreferences = new SecurePreferences(BaseActivity.this);
        String apiKey = sharedPreferences.getString("api_token", "");

        //bikin logout ke server
        ApiService client = ServiceGenerator.createService(ApiService.class);

        //Ambil data produk
        Call<ResponseSuccess> call = client.logout(apiKey);
        call.enqueue(new Callback<ResponseSuccess>() {
            @Override
            public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                ResponseSuccess result = response.body();

                String logoutStatus = result.getSuccess().getType();

                if(logoutStatus.equalsIgnoreCase("OK-LOG OUT"))
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();

                    buildDrawer(null, toolbar);
                }
                else if(logoutStatus.equalsIgnoreCase("ERROR-LOG OUT API TOKEN"))
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();

                    buildDrawer(null, toolbar);

                    Toast.makeText(getApplicationContext(), result.getSuccess().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSuccess> call, Throwable t) {

            }
        });
    }
}
