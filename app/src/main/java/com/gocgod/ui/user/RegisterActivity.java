package com.gocgod.ui.user;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.gocgod.ApiService;
import com.gocgod.ServiceGenerator;
import com.gocgod.location.InstanceProvince;
import com.gocgod.model.LocationCity;
import com.gocgod.model.LocationProvince;
import com.gocgod.model.LocationProvinceContactResponse;
import com.gocgod.model.LocationProvinceContactSuccess;
import com.gocgod.model.ResponseSuccess;
import com.gocgod.ui.BaseActivity;
import com.gocgod.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import carbon.widget.Button;
import carbon.widget.CheckBox;
import carbon.widget.EditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.name_register)
    EditText text_name;
    @BindView(R.id.province)
    SearchableSpinner text_province;
    @BindView(R.id.city) SearchableSpinner text_city;
    @BindView(R.id.district) SearchableSpinner text_district;
    @BindView(R.id.postalcode) EditText text_postalcode;
    @BindView(R.id.birthday) EditText text_birthday;
    @BindView(R.id.phone_register)
    EditText text_phone;
    @BindView(R.id.email_register)
    EditText text_email;
    @BindView(R.id.password_register)
    EditText text_password;
    @BindView(R.id.accept_policy)
    CheckBox terms_policy;
    @BindView(R.id.submit_register)
    Button submit_register;

    private String name;
    private String province;
    private String city;
    private String district;
    private String postalcode;
    private String birthday;
    private String phone;
    private String email;
    private String password;
    private DatePickerDialog birthdaydate;
    private SimpleDateFormat dateFormatter;
//    private MaterialDialog dialogSubmit;
    SharedPreferences mSharedPreferences;

    List<LocationProvince> locationProvince = new ArrayList<LocationProvince>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        //Build Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.register);

        //Disable button until checked
        submit_register.setEnabled(false);
//        terms_policy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                submit_register.setEnabled(isChecked);
//            }
//        });

           dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        text_birthday.setInputType(InputType.TYPE_NULL);
//        findViewsById();
        setDateTimeField();

        text_province.setTitle("Pilih Provinsi");
        text_province.setPositiveButton("OK");

        if (InstanceProvince.getInstance().getOpProvince() == null) {
            getProvince();
        } else {
            generateProvince();
        }



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setDateTimeField() {
        text_birthday.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        birthdaydate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                text_birthday.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        if(view == text_birthday) {
            birthdaydate.show();
        }
    }

    public void getProvince(){
        ApiService client = ServiceGenerator.createService(ApiService.class);
        Map<String, String> data = new HashMap<>();

        Call<LocationProvinceContactResponse> call = client.getLocationProvince(data);
        call.enqueue(new Callback<LocationProvinceContactResponse>() {
            @Override
            public void onResponse(Call<LocationProvinceContactResponse> call, Response<LocationProvinceContactResponse> response) {

                LocationProvinceContactResponse result = response.body();
                InstanceProvince.getInstance().setOpProvince(result.getResponse().getProvince());
                generateProvince();

            }

            @Override
            public void onFailure(Call<LocationProvinceContactResponse> call, Throwable t) {

            }
        });
    }

    public void generateProvince(){
        final List<LocationProvince> province = InstanceProvince.getInstance().getOpProvince();
        String[] provinceName = new String[province.size()];

        for(int i = 0; i < province.size(); i++ ){
            provinceName[i] = province.get(i).getProvinceName();
        }

        ArrayAdapter<String> getAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, provinceName);
        getAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        text_province.setAdapter(getAdapter);
        getAdapter.notifyDataSetChanged();

//        Log.d("aurel", getAdapter.toString());

        text_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                getCity(province.get(position).getProvinceId().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> view){
            }
        });
    }

    public void getCity(final String province_id){
        Log.d("aurel", province_id);
    }

    public void getDistrict(final String city_id){
        ApiService client = ServiceGenerator.createService(ApiService.class);
        Call<LocationCity> call = client.getCity(city_id);
        call.enqueue(new Callback<LocationCity>() {
            @Override
            public void onResponse(Call<LocationCity> call, Response<LocationCity> response) {

            }

            @Override
            public void onFailure(Call<LocationCity> call, Throwable t) {

            }
        });


    }


}
