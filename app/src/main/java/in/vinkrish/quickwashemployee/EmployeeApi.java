package in.vinkrish.quickwashemployee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Vinay on 07-11-2017.
 */

public interface EmployeeApi {

    @POST("/quck_wash/emp.php")
    Call<List<Order>> getOrder(@Body Employee emp);

    @POST("/quick_wash/select_pending.php")
    Call<List<Order>> getNewOrder();

    @POST("/quick_wash/login.php")
    Call<LoginResponse> checkUser(@Body Employee emp);

}
