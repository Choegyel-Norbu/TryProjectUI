package updatedproject.com.tryprojectui;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SharedPrefManager {

    final String TAG = "sharedPrefTAG";

    private static final String STD_DETAIL_SHARED = "user_info";
    private static String ENROLL;
    private static String NAME;

    private static final String DATE_COUNT = "date_shared";

    private static SharedPrefManager mInstance;
    private Context mcontext;

    public SharedPrefManager(Context context) {
        mcontext = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLoggin(String enrollment, String name) {
        SharedPreferences sharedPref = mcontext.getSharedPreferences(STD_DETAIL_SHARED, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ENROLL, enrollment);
        editor.putString(NAME, name);
        editor.apply();
        return true;
    }

    public boolean userLoggedIn() {
        SharedPreferences sharedPref = mcontext.getSharedPreferences(STD_DETAIL_SHARED, Context.MODE_PRIVATE);
        if (sharedPref.getString(ENROLL, null) != null) {
            return true;
        }
        return false;
    }

    public void logOut() {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(STD_DETAIL_SHARED, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public String getName() {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(STD_DETAIL_SHARED, Context.MODE_PRIVATE);
        return sharedPreferences.getString(NAME, null);
    }

    public String getEnrollment() {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(STD_DETAIL_SHARED, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ENROLL, null);
    }
}
