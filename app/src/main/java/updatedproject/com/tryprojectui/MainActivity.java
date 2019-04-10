package updatedproject.com.tryprojectui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;

import static updatedproject.com.tryprojectui.BaseApp.CHANNEL_1;

public class MainActivity extends AppCompatActivity {

    final String TAG = "MainActivityTAG";

    private NotificationManagerCompat notificationManager;

    Button login_button;
    TextInputLayout id_lay, pass_lay;
    ProgressDialog dialog;

    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (SharedPrefManager.getInstance(this).getEnrollment() != null) {
            finish();
            startActivity(new Intent(this, Profile.class));
            return;
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);

        constraintLayout = findViewById(R.id.snack_);
        login_button = findViewById(R.id.login_button);
        id_lay = findViewById(R.id.enroll_lay);
        pass_lay = findViewById(R.id.pass_lay);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateLogging()) {
                    Login();
                }
            }/* else {
                    //startActivity(new Intent(MainActivity.this, Profile.class));
                    //Snackbar.make(constraintLayout, "You must fill all the fields", Snackbar.LENGTH_LONG).show();
                }*/

        });
    }

    public void Register(View v) {
        startActivity(new Intent(this, Registration.class));
    }

    public void Login() {
        dialog = new ProgressDialog(this);
        final String enrollment = id_lay.getEditText().getText().toString().trim();
        final String password = pass_lay.getEditText().getText().toString().trim();

        dialog.setMessage("Logging in...");
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            dialog.dismiss();
                            JSONObject json = new JSONObject(response);
                            String message = json.getString("message");
                            if (message.equals("Correct credentials")) {
                                String enrollment = json.getString("enrollment");
                                String name = json.getString("name");

                                Log.d(TAG, "enrollment = " + enrollment);
                                Log.d(TAG, "name = " + name);

                                SharedPrefManager.getInstance(MainActivity.this).userLoggin(enrollment, name);
                                startActivity(new Intent(MainActivity.this, Profile.class));
                                CustomIntent.customType(MainActivity.this, "fadein-to-fadeout");
                            } else {
                                Toast.makeText(MainActivity.this, json.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, "Database connectivity issue", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.hide();
                        Toast.makeText(MainActivity.this, "Error logging", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("enrollment", enrollment);
                params.put("password", password);

                return params;
            }
        };
        RequestHandler.getInstance(this).addTRequestQueue(stringRequest);
    }

    public boolean validateLogging() {
        boolean check = true;

        if (id_lay.getEditText().getText().toString().isEmpty() && pass_lay.getEditText().getText().toString().isEmpty()) {
            Snackbar.make(constraintLayout, "Both fields are empty", Snackbar.LENGTH_LONG).show();
            check = false;
        } else if (id_lay.getEditText().getText().toString().isEmpty()) {
            Snackbar.make(constraintLayout, "The ID field is empty", Snackbar.LENGTH_LONG).show();
            check = false;
        } else if (pass_lay.getEditText().getText().toString().isEmpty()) {
            Snackbar.make(constraintLayout, "The password is empty", Snackbar.LENGTH_LONG).show();
            check = false;
        }
        return check;
    }

    public String color_change(String str) {
        SpannableString spannableString = new SpannableString(str);
        ForegroundColorSpan foreRed = new ForegroundColorSpan(Color.RED);
        spannableString.setSpan(foreRed, 0, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString.toString();
    }

    public void Placeholer(View view) {
        startActivity(new Intent(this, Placeholder.class));
    }
}