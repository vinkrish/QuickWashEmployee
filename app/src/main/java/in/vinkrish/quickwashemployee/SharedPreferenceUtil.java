package in.vinkrish.quickwashemployee;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Vinay on 08-11-2017.
 */

public class SharedPreferenceUtil {

    public static boolean isLogged(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("credentials", Context.MODE_PRIVATE);
        return sharedPref.getBoolean("is_logged", false);
    }

    public static void updateLogged(Context context, boolean isPrompted) {
        SharedPreferences sharedPref = context.getSharedPreferences("credentials", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("is_logged", isPrompted);
        editor.apply();
    }

}
