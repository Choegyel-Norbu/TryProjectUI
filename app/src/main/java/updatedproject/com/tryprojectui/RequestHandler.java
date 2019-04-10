package updatedproject.com.tryprojectui;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestHandler  {
    private static RequestHandler mInstance;
    private RequestQueue requestQueue;
    private Context mcontext;

    public RequestHandler(Context context) {
        mcontext= context;
        requestQueue = getRequestQueue();
    }

    public static synchronized RequestHandler getInstance(Context context){
        if(mInstance == null) {
            mInstance = new RequestHandler(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if(mInstance == null) {
            requestQueue = Volley.newRequestQueue(mcontext.getApplicationContext());
        }
        return requestQueue;
    }
    public <T> void addTRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
