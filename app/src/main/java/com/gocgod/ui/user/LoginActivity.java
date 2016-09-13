package com.gocgod.ui.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.gocgod.ApiService;
import com.gocgod.ServiceGenerator;
import com.gocgod.model.ResponseSuccess;
import com.gocgod.ui.Global;
import com.gocgod.R;
import com.gocgod.ui.MainActivity;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.securepreferences.SecurePreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import carbon.widget.Button;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.email_login)
    EditText emailLogin;
    @BindView(R.id.password_login)
    EditText passwordLogin;
    @BindView(R.id.forgot_password_dialog)
    Button forgotPassword;
    @BindView(R.id.loadingLayout)
    carbon.widget.LinearLayout loadingLayout;
    @BindView(R.id.loading)
    carbon.widget.ProgressBar loading;

//    private MaterialDialog forgotDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Global.setupUI(findViewById(R.id.login_layout), LoginActivity.this, null);

        loadingLayout.setVisibility(View.GONE);

        SpannableString content = new SpannableString(getString(R.string.forgot_password));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        forgotPassword.setText(content);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        this.finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }
//
//    @OnClick(R.id.submit_login)
//    public void authUser() {
//        final String device_token = Auth.getInstance().getDeviceToken();
//        final String email = userHp.getText().toString();
//        String user_password = userPassword.getText().toString();
//
//        if (user_hp.matches("") || user_hp.length() < 5 || user_hp.length() > 15) {
//            Global.showShortToast(this, R.string.invalid_user_hp);
//        }else if (user_password.matches("")) {
//            Global.showShortToast(this, R.string.invalid_user_password);
//        }else {
//            Global.showIndeterminateProgressDialog(this, false, R.string.authenticating, R.string.please_wait);
//
//            //Make API Call
//            ApiService client = ServiceGenerator.createService(ApiService.class);
//
//            Login task = new Login(user_hp, user_password, device_token, Global.LOCALE);
//            Call<BasicResponse> call = client.login(task);
//            call.enqueue(new Callback<BasicResponse>() {
//                @Override
//                public void onResponse(Response<BasicResponse> response, Retrofit retrofit) {
//                    Global.dialogProgress.dismiss();
//
//                    if (response.isSuccess()) { //Response 200 or 300
//                        BasicResponse login = response.body();
//                        String type;
//                        String title;
//                        String message;
//                        String code;
//
//                        if (login.getSuccess() != null) {
//                            code = login.getSuccess().getCode();
//
//                            if (code.matches("OK-AUTH-200")){
//                                //Retrieve Remote Data
//                                String remote_token = login.getSuccess().getToken();
//                                String remote_user_name = login.getSuccess().getData().getName();
//                                String remote_user_email = login.getSuccess().getData().getEmail();
//                                int remote_user_category = login.getSuccess().getData().getCategory();
//
//
//                                //Store Data to User PReferences for Persistent Data
//                                mSharedPreferences = getSharedPreferences(Global.PREFS, MODE_PRIVATE);
//                                SharedPreferences.Editor editor = mSharedPreferences.edit();
//                                editor.putString(Global.AUTH_TOKEN, remote_token);
//                                editor.putString(Global.AUTH_NAME, remote_user_name);
//                                editor.putString(Global.AUTH_EMAIL, remote_user_email);
//                                editor.putInt(Global.AUTH_CATEGORY, remote_user_category);
//                                editor.putString(Global.AUTH_PHONE, user_hp);
//                                editor.apply();
//
//                                //Store to temp variable to easy access
//                                Auth me = Auth.getInstance();
//                                me.setAuthCheck(true);
//                                me.setToken(remote_token);
//                                me.setHp(user_hp);
//                                me.setName(remote_user_name);
//                                me.setEmail(remote_user_email);
//                                me.setCategory(remote_user_category);
//
//                                MainActivity.is_logged_in = true;
//                                Global.showLongToast(LoginActivity.this, "Welcome " + remote_user_name);
//                                finish();
//                            }
//                        } else {
//                            type = login.getError().getType();
//                            code = login.getError().getCode();
//                            message = login.getError().getMessage().getWarning();
//                            title = getString(R.string.login_failed);
//
//                            if (type.matches("validation_fail")){
//                                message = null;
//                                List<String> error_hp = login.getError().getMessage().getHp();
//                                List<String> error_password = login.getError().getMessage().getPassword();
//                                if (!error_hp.isEmpty()){
//                                    for (String s : error_hp)
//                                    {
//                                        message += s + "\n";
//                                    }
//                                }else if (!error_password.isEmpty()){
//                                    for (String s : error_hp)
//                                    {
//                                        message += s + "\n";
//                                    }
//                                }else{
//                                    message = getString(R.string.unknown_error);
//                                }
//                            }
//
//                            if (message != null) {
//                                Global.simpleAlert(LoginActivity.this, title, message, null);
//                            }else {
//                                Global.simpleAlert(LoginActivity.this, getString(R.string.failed), getString(R.string.unable_retrieve_data), null);
//                            }
//                        }
//                    } else {
//                        Snackbar.make(findViewById(R.id.login_layout), R.string.unable_retrieve_data, Snackbar.LENGTH_LONG).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Throwable t) {
//                    Log.d("Fail Log: ", t.getLocalizedMessage());
//                    Global.dialogProgress.dismiss();
//                    Snackbar.make(findViewById(R.id.login_layout), R.string.connection_error, Snackbar.LENGTH_LONG).show();
//                }
//            });
//        }
//    }

//    @OnClick(R.id.forgot_password_dialog)
//    public void forgotPassword() {
//        forgotDialog = new MaterialDialog.Builder(this)
//                .title(R.string.forgot_password)
//                .content(R.string.reminder_will_send_to_email)
//                .inputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
//                .positiveText(R.string.ok)
//                .alwaysCallInputCallback() // this forces the callback to be invoked with every input change
//                .input(R.string.email, 0, false, new MaterialDialog.InputCallback() {
//                    @Override
//                    public void onInput(MaterialDialog dialog, CharSequence input) {
//                        if (Global.isValidEmail(input.toString().trim())) {
//                            dialog.setContent(R.string.reminder_will_send_to_email);
//                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
//                        } else {
//                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
//                        }
//                    }
//                })
//                .onPositive(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        //Hide dialog show progress
//                        forgotDialog.dismiss();
//                        Global.showIndeterminateProgressDialog(LoginActivity.this, true, R.string.process, R.string.please_wait);
//
//                        //Sending Mail
//                        ApiService client = ServiceGenerator.createService(ApiService.class);
//                        String email = forgotDialog.getInputEditText().getText().toString();
//
//                        Remind task = new Remind(email, Global.LOCALE);
//                        Call<BasicResponse> call = client.remind(task);
//                        call.enqueue(new Callback<BasicResponse>() {
//                            @Override
//                            public void onResponse(Response<BasicResponse> response, Retrofit retrofit) {
//                                //hide progress
//                                Global.dialogProgress.dismiss();
//
//                                if (response.isSuccess()) {
//                                    BasicResponse transaction = response.body();
//                                    String type;
//                                    String title;
//                                    String message;
//
//                                    if (transaction.getSuccess() != null) {
//                                        type = transaction.getSuccess().getType();
//                                        message = transaction.getSuccess().getMessage();
//                                        title = getString(R.string.success);
//                                    } else {
//                                        type = transaction.getError().getType();
//                                        message = transaction.getError().getMessage().getWarning();
//                                        title = getString(R.string.failed);
//
//                                        if (type.matches("validation_fail")){
//                                            message = null;
//                                            for (String s : transaction.getError().getMessage().getEmail())
//                                            {
//                                                message += s + "\n";
//                                            }
//                                        }
//                                    }
//
//                                    if (message != null) {
//                                        Global.simpleAlert(LoginActivity.this, title, message, null);
//                                    }else {
//                                        Global.simpleAlert(LoginActivity.this, getString(R.string.failed), getString(R.string.unable_retrieve_data), null);
//                                    }
//                                } else {
//                                    Snackbar.make(findViewById(R.id.login_layout), R.string.unable_retrieve_data, Snackbar.LENGTH_LONG).show();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Throwable t) {
//                                Global.dialogProgress.dismiss();
//                                Log.d("Fail Log: ", t.getLocalizedMessage());
//                                Snackbar.make(findViewById(R.id.login_layout), R.string.connection_error, Snackbar.LENGTH_LONG).show();
//                            }
//                        });
//                    }
//                }).build();
//
//        forgotDialog.show();
//    }
//
    @OnClick(R.id.btnLogin)
    public void login()
    {
        loadingLayout.setVisibility(View.VISIBLE);

        String email =  emailLogin.getText().toString();
        final String password = passwordLogin.getText().toString();

        if(email.equals("") || password.equals(""))
        {
            String message = "Email dan password harus diisi";
            Global.showSnackBar(findViewById(R.id.login_layout), message, Color.WHITE);
        }
        else {

            ApiService client = ServiceGenerator.createService(ApiService.class);

            Call<ResponseSuccess> call = client.postLogin(email, password);
            call.enqueue(new Callback<ResponseSuccess>() {
                @Override
                public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                    ResponseSuccess result = response.body();

                    String loginStatus = result.getSuccess().getType();

                    if (loginStatus.equalsIgnoreCase("OK-LOGIN")) {
                        loading.setVisibility(View.GONE);

                        SharedPreferences userData = new SecurePreferences(LoginActivity.this);
                        SharedPreferences.Editor editor = userData.edit();
                        editor.putString("id", result.getSuccess().getData().getLogin().getId().toString());
                        editor.putString("name", result.getSuccess().getData().getLogin().getName().toString());
                        editor.putString("address", result.getSuccess().getData().getLogin().getAddress().toString());
                        editor.putInt("province_id", result.getSuccess().getData().getLogin().getProvinceId());
                        editor.putInt("city_id", result.getSuccess().getData().getLogin().getCityId());
                        editor.putInt("district_id", result.getSuccess().getData().getLogin().getDistrictId());
                        editor.putString("zipcode", result.getSuccess().getData().getLogin().getZipcode().toString());
                        editor.putString("date_of_birth", result.getSuccess().getData().getLogin().getDateOfBirth().toString());
                        editor.putString("email", result.getSuccess().getData().getLogin().getEmail().toString());
                        editor.putString("phone", result.getSuccess().getData().getLogin().getPhone().toString());
                        editor.putInt("status_user", result.getSuccess().getData().getLogin().getStatusUser());
                        editor.putInt("balance", result.getSuccess().getData().getLogin().getBalance());
                        editor.putString("bank_account", result.getSuccess().getData().getLogin().getBankAccount().toString());
                        editor.putInt("bank_id", result.getSuccess().getData().getLogin().getBankId());
                        editor.putString("api_token", result.getSuccess().getData().getLogin().getApiToken());
                        editor.putBoolean("is_login", true);
                        editor.commit();

                        finish();

                        /*SharedPreferences sharedPref = new SecurePreferences(MainActivity.this);
                        Boolean login = sharedPref.getBoolean("is_login", false);*/
                    } else {
                        loadingLayout.setVisibility(View.GONE);

                        String message = result.getSuccess().getMessage();
                        Global.showSnackBar(findViewById(R.id.login_layout), message, Color.WHITE);
                    }
                }

                @Override
                public void onFailure(Call<ResponseSuccess> call, Throwable t) {

                }
            });
        }
    }

    @OnClick(R.id.register_button)
    public void openRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
