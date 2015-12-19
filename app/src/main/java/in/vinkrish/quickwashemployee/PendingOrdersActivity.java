package in.vinkrish.quickwashemployee;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.List;

import in.vinkrish.quickwashemployee.adapter.MyRecyclerAdapter;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class PendingOrdersActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Order> orderList;
    private CoordinatorLayout coordinatorLayout;
    private static final String BASE_URL = "http://localhost";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_orders);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


       /* Order o = new Order();
        o.setName("Vinay");
        orderList = new ArrayList<>();
        orderList.add(o);
        adapter = new MyRecyclerAdapter(this, orderList);
        mRecyclerView.setAdapter(adapter);*/

        checkOrder();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_orders, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            checkOrder();
        } else if (id == R.id.action_logout) {
            System.exit(0);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

    }

    private void checkOrder() {
        if (isOnline()) {
            new OrderAsyncTak().execute();
        } else {
            showSnackBar("Please check internet and try again!");
        }
    }

    class OrderAsyncTak extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog = new ProgressDialog(PendingOrdersActivity.this);
        boolean success = false;

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Fetching Orders...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiEndPointInterface apiService = retrofit.create(ApiEndPointInterface.class);

            try {
                orderList = apiService.getNewOrder().execute().body();
                success = true;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            pDialog.dismiss();
            if (success) {
                MyRecyclerAdapter adapter = new MyRecyclerAdapter(PendingOrdersActivity.this, orderList);
                mRecyclerView.setAdapter(adapter);
            } else {
                showSnackBar("Pending Orders are not retrieved, please try again!");
            }
        }

    }

    private void showSnackBar(String msg) {
        Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG)
                .show();
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cm.getActiveNetworkInfo();
        if (nf != null && nf.isConnectedOrConnecting()) return true;
        else return false;
    }

}
