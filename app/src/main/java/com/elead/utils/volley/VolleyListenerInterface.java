package com.elead.utils.volley;

import com.android.volley.Response;

public class VolleyListenerInterface {
    public Response.Listener<String> mListener;
    public Response.ErrorListener mErrorListener;

    public VolleyListenerInterface(Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this.mErrorListener = errorListener;
        this.mListener = listener;
    }
}