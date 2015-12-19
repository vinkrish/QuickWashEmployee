package in.vinkrish.quickwashemployee;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import in.vinkrish.quickwashemployee.adapter.Employee;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {
    EditText userName, password;
    Button login;
    private CoordinatorLayout coordinatorLayout;
    private static final String BASE_URL = "http://vinkrish.info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        init();
    }

    private void initView() {
        userName = (EditText) findViewById(R.id.usrName_et);
        password = (EditText) findViewById(R.id.password_et);
        login = (Button) findViewById(R.id.login_btn);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
    }

    private void init() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOrder();
            }
        });

    }

    private void checkOrder() {
        if (isOnline()) {
            new LoginAsyncTak().execute();
        } else {
            showSnackBar("Please check internet and try again!");
        }
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cm.getActiveNetworkInfo();
        if (nf != null && nf.isConnectedOrConnecting()) return true;
        else return false;
    }

    class LoginAsyncTak extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog = new ProgressDialog(LoginActivity.this);
        boolean success = false;
        Employee employee;

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Authenticating User...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            employee = new Employee();
            employee.setUsername(userName.getText().toString());
            employee.setPassword(password.getText().toString());
        }

        @Override
        protected Void doInBackground(Void... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiEndPointInterface apiService = retrofit.create(ApiEndPointInterface.class);

            try {
                LoginResponse loginResponse = apiService.checkUser(employee).execute().body();
                success = loginResponse.getStatus().equals("success");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            pDialog.dismiss();
            password.setText("");
            if (success) {
                Intent intent = new Intent(LoginActivity.this, in.vinkrish.quickwashemployee.PendingOrdersActivity.class);
                startActivity(intent);
            } else {
                showSnackBar("login failed, please try with valid credentials!");
            }
        }

    }

    private void showSnackBar(String msg) {
        Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG)
                .show();
    }

}
