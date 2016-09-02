package com.gocgod.ui.user;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.gocgod.ui.BaseActivity;
import com.gocgod.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import carbon.widget.Button;
import carbon.widget.CheckBox;
import carbon.widget.EditText;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.name_register)
    EditText text_name;
    @BindView(R.id.province) EditText text_province;
    @BindView(R.id.city) EditText text_city;
    @BindView(R.id.district) EditText text_district;
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

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
//
//    private void findViewsById() {
//        text_birthday.setInputType(InputType.TYPE_NULL);
//    }

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


}
