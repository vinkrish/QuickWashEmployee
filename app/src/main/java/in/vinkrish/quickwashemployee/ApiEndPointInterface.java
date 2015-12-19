package in.vinkrish.quickwashemployee;

import java.util.List;

import in.vinkrish.quickwashemployee.adapter.Employee;
import retrofit.Call;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by vinkrish on 17/12/15.
 */
public interface ApiEndPointInterface {

    @POST("/quck_wash/emp.php")
    void getOrder (@Body Employee emp, Callback<List<Order>> callBack);

    @POST("/quick_wash/select_pending.php")
    Call<List<Order>> getNewOrder ();

    @POST("/quick_wash/login.php")
    Call<LoginResponse> checkUser (@Body Employee emp);

}
