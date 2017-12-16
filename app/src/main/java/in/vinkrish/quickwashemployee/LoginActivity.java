package in.vinkrish.quickwashemployee;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText userName, password;
    Button login;
    ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;
    private static final String BASE_URL = "http://vingel.in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        init();
    }

    private void initView() {
        userName = findViewById(R.id.usrName_et);
        password = findViewById(R.id.password_et);
        login = findViewById(R.id.login_btn);
        progressBar = findViewById(R.id.progress);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);
    }

    private void init() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtil.isNetworkAvailable(getApplicationContext())) {
                    login();
                } else {
                    showSnackBar("Please check internet and try again!");
                }
            }
        });
    }

    private void login() {
        progressBar.setVisibility(View.VISIBLE);
        EmployeeApi authApi = ApiClient.getClient().create(EmployeeApi.class);

        Employee employee = new Employee();
        employee.setUsername(userName.getText().toString());
        employee.setPassword(password.getText().toString());

        Call<LoginResponse> queue = authApi.checkUser(employee);
        queue.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("success")) {
                        SharedPreferenceUtil.updateLogged(LoginActivity.this, true);
                        Intent intent = new Intent(LoginActivity.this, PendingOrdersActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        showSnackBar("login failed, please try with valid credentials!");
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void showSnackBar(String msg) {
        Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_LONG).show();
    }

}
