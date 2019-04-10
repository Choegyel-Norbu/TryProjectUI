package updatedproject.com.tryprojectui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.TooltipCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;
import maes.tech.intentanim.CustomIntent;
import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;

public class Profile extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {

    final String TAG = "profileTAG";

    Toolbar toolbar;
    DrawerLayout Drawer;
    NavigationView navigationView;

    Dialog dialog;
    TextView close;

    RelativeLayout relativeLayout_bookDialog;

    Button Notification;
    public NotificationManagerCompat notificationManagerCompat;

    EditText book_title, book_author, book_id, date;
    ProgressDialog progressDialog;
    TextView header_email;

    FloatingActionButton floatingActionButton;

    ArrayList<RecyclerSetter> list = new ArrayList<RecyclerSetter>();
    ProfileRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.Collapse);
        collapsingToolbarLayout.setTitle(SharedPrefManager.getInstance(this).getName());
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
        collapsingToolbarLayout.setCollapsedTitleGravity(10);
        header_email = findViewById(R.id.header_email);

        //Navigation Drawer
        Drawer = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, Drawer, toolbar,
                R.string.open_drawer, R.string.close_drawer);
        Drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.navigation);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.home:
                        View view = getLayoutInflater().inflate(R.layout.custom_toast, null);
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.setView(view);
                        toast.show();
                        break;
                    case R.id.vle:
                        Toast.makeText(Profile.this, "VlE", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.message:
                        Toast.makeText(Profile.this, "Message", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.phone:
                        Toast.makeText(Profile.this, "Phone", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.facebook:
                        Toast.makeText(Profile.this, "Facebook", Toast.LENGTH_SHORT).show();
                        break;
                }
                Drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        notificationManagerCompat = NotificationManagerCompat.from(this);
        Notification = findViewById(R.id.notification);

        //floating Action
        dialog = new Dialog(this);
        floatingActionButton = findViewById(R.id.floatingAction);

        floatingActionButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TooltipCompat.setTooltipText(floatingActionButton, "Take book");
                return true;
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.book_register);
                dialog.show();
                dialog_book_register();
            }
        });
        floatingActionButton.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                TooltipCompat.setTooltipText(floatingActionButton, "Take book");
                return true;
            }
        });

        //content and recycler View
        content();

        progressDialog = new ProgressDialog(this);
    }

    //Overflow menu

    @Override

    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                final EditText search = findViewById(R.id.search_tab);
                search.setVisibility(View.VISIBLE);
                final TextView go = findViewById(R.id.go);

                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        go.setVisibility(View.VISIBLE);
                        textChange(search);
                        Log.d(TAG, "textChange method is called");
                    }
                });
                go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        search.setVisibility(View.INVISIBLE);
                        go.setVisibility(View.INVISIBLE);
                    }
                });
                break;
            case R.id.update:
                Toast.makeText(this, "Records", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                SharedPrefManager.getInstance(this).logOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
        return true;
    }

    public void textChange(EditText search) {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    public void filter(String text) {
        ArrayList<RecyclerSetter> filteredList = new ArrayList<>();
        for(RecyclerSetter item : list) {
            if(item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }

    public void dialog_book_register() {

        close = dialog.findViewById(R.id.close);
        book_title = dialog.findViewById(R.id.book_title);
        book_author = dialog.findViewById(R.id.book_author);
        book_id = dialog.findViewById(R.id.book_id);

        relativeLayout_bookDialog = dialog.findViewById(R.id.book_dialog);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH);
        int d = calendar.get(Calendar.DAY_OF_MONTH);

        String currentDate = ""+y+"-"+m+"-"+d+"";
        date.setText(currentDate);
    }

    public void book_submit(View view) {

        String title, author, id;
        title = book_title.getText().toString();
        author =  book_author.getText().toString();
        id = book_id.getText().toString();
        boolean check = true;
        if (title.isEmpty()) {
            check = false;
        }
        if (author.isEmpty()) {
            check = false;
        }
        if (id.isEmpty()) {
            check = false;
        }
        if (check == false)
            Snackbar.make(relativeLayout_bookDialog, "All fields are mandatory!", Snackbar.LENGTH_LONG).show();
        else {
            Book_login(id, new SimpleDateFormat("yyyy-MM-dd").format(new Date()), title, author,
                    SharedPrefManager.getInstance(this).getEnrollment());
        }
    }


    public void Book_login(String bid, String Date, String book_title, String book_author, String enrollment) {
        final String id = bid;
        final String date = Date;
        final String title = book_title;
        final String author = book_author;
        final String enrollment_no = enrollment;

        progressDialog.setMessage("Registering book...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_MAINSCRIPT_BOOK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String bookCount = jsonObject.getString("confirmation");
                            if(bookCount.equals("books more than 10")){
                                Snackbar.make(relativeLayout_bookDialog, "You already have 10 books registered!", Snackbar.LENGTH_LONG).show();
                            }else{
                                SQLiteClass sqlite = new SQLiteClass(Profile.this);
                                Toast.makeText(Profile.this, jsonObject.getString("confirmation"),
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(Profile.this, "Registration error!", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("bid", id);
                params.put("date", date);
                params.put("title", title);
                params.put("author", author);
                params.put("enrollment", enrollment_no);
                return params;
            }
        };
        RequestHandler.getInstance(this).addTRequestQueue(stringRequest);
    }

    public void content() {

        RecyclerView recyclerView = findViewById(R.id.Recycler_view);
        ArrayList<RecyclerSetter> AfterList = new ArrayList<>();
        AfterList = list;

        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String d = df.format(date);

        list.add(new RecyclerSetter("The magazine" ,SharedPrefManager.getInstance(this).getName(), d));
        list.add(new RecyclerSetter("The Tibetan book of living and dying" ,SharedPrefManager.getInstance(this).getName(), d));
        list.add(new RecyclerSetter("Student Hand Book" ,SharedPrefManager.getInstance(this).getName(), d));
        list.add(new RecyclerSetter("The cooking" ,SharedPrefManager.getInstance(this).getName(), d));
        list.add(new RecyclerSetter("One" ,SharedPrefManager.getInstance(this).getName(), d));
        list.add(new RecyclerSetter("Two" ,SharedPrefManager.getInstance(this).getName(), d));
        list.add(new RecyclerSetter("Three" ,SharedPrefManager.getInstance(this).getName(), d));
        list.add(new RecyclerSetter("Four" ,SharedPrefManager.getInstance(this).getName(), d));

        adapter = new ProfileRecyclerAdapter(this, AfterList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void finish() {
        super.finish();
        CustomIntent.customType(this, "fadein-to-fadeout");
    }

    public void edit(Context mctx){
        startActivity(new Intent(mctx, Editor.class));
    }

    public void BookDaysCount(String title) {
        Map<String, Integer> daysCount = new HashMap<>();

        daysCount.put(title, 1);

        Set<String> keys = daysCount.keySet();
        for(String key : keys) {

        }
    }
}
