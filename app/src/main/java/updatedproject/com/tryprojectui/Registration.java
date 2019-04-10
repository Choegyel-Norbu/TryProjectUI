package updatedproject.com.tryprojectui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    TextInputLayout name, enroll, email, password;
    Button reg_btn;
    TextView ok, back_signIn;
    ProgressDialog dialog;

    Dialog dialogAck;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Initialization();
        dialog = new ProgressDialog(this);
        relativeLayout = findViewById(R.id.constraintLayout);
        dialogAck = new Dialog(this);

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    public void Initialization() {

        name = findViewById(R.id.name_lay);
        enroll = findViewById(R.id.enroll_lay);
        email = findViewById(R.id.email_lay);
        password = findViewById(R.id.password_lay);
        reg_btn = findViewById(R.id.register);
    }

    public void registerUser() {
        if (entryValidation()) {
            dialog.setMessage("Registering...");
            dialog.show();
            StringRequest StrReq = new StringRequest(Request.Method.POST,
                    Constants.URL_REGISTER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);
                                String message = obj.getString("message");
                                if (message.equals("Registration successfull")) {
                                    Acknowledgement();
                                } else {
                                    Snackbar.make(relativeLayout, obj.getString("message"), Snackbar.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.hide();
                            Toast.makeText(Registration.this, "There was some error!", Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", name.getEditText().getText().toString().trim());
                    params.put("enrollment", enroll.getEditText().getText().toString().trim());
                    params.put("email", email.getEditText().getText().toString().trim());
                    params.put("password", password.getEditText().getText().toString().trim());
                    return params;
                }
            };

            RequestHandler.getInstance(this).addTRequestQueue(StrReq);
        }
    }

    public boolean entryValidation() {
        boolean validate = true;
        if (name.getEditText().getText().toString().isEmpty()) {
            name.setErrorEnabled(true);
            name.setError("Name field cannot be empty");
            validate = false;
        } else {
            name.setErrorEnabled(false);
        }

        if (enroll.getEditText().getText().toString().isEmpty()) {
            enroll.setErrorEnabled(true);
            enroll.setError("Error! The ID field cannot be empty");
            validate = false;
        } else {
            enroll.setErrorEnabled(false);
            if (!ReGex(enroll.getEditText().getText().toString().trim())) {
                enroll.setErrorEnabled(true);
                enroll.setError("Invalid enrollment number");
                validate = false;
            } else {
                enroll.setErrorEnabled(false);
            }
        }

        if (email.getEditText().getText().toString().isEmpty()) {
            email.setErrorEnabled(true);
            email.setError("Error! The Email field cannot be empty");
            validate = false;
        } else {
            email.setErrorEnabled(false);
        }

        if (password.getEditText().getText().toString().trim().isEmpty()) {
            password.setErrorEnabled(true);
            password.setError("Error! The Password field cannot be empty");
        } else if (password.getEditText().getText().toString().trim().length() > 8) {
            password.setError("Password too long");
            validate = false;
        } else {
            password.setErrorEnabled(false);
        }
        return validate;
    }

    public void Acknowledgement() {
        dialogAck = new Dialog(this);
        dialogAck.setContentView(R.layout.ack_layout);
        dialogAck.show();
        ok = dialogAck.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAck.dismiss();

            }
        });
        SignInOption();
    }

    public void SignInOption() {

//        Do you want to SignIn

        back_signIn = findViewById(R.id.want_to_signIn);

        String option = back_signIn.getText().toString().trim();
        SpannableString sp = new SpannableString(option);

        ForegroundColorSpan blue = new ForegroundColorSpan(Color.GREEN);
        StyleSpan bold = new StyleSpan(Typeface.BOLD);
        UnderlineSpan underline = new UnderlineSpan();
        sp.setSpan(bold, 15, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(blue, 15, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(underline, 15, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        back_signIn.setVisibility(View.VISIBLE);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Toast.makeText(Registration.this, "SignIn clicked", Toast.LENGTH_SHORT).show();
            }
        };
        sp.setSpan(clickableSpan, 15, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        back_signIn.setText(sp);
    }

    /*
        checks for whether the enrollment is in a correct format
     */
    public boolean ReGex(String enrollment) {

        String ReGex = "071[4-8]\\d{4}";
        Pattern patter = Pattern.compile(ReGex);
        Matcher matcher = patter.matcher(enrollment);

        while (matcher.find()) {
            if (matcher.group().length() != 0) {
                return true;
            }
        }
        return false;
    }

    public void backButton(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void click(View view) {
        Acknowledgement();
    }
}
