package updatedproject.com.tryprojectui;

import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;

public class Placeholder extends AppCompatActivity {

    android.support.constraint.Placeholder place;
    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeholder);

        constraintLayout = findViewById(R.id.const_placeholder);
    }

    public void swapView(View view) {
        if(Build.VERSION.SDK_INT > 19)
        TransitionManager.beginDelayedTransition(constraintLayout);
    }
}
