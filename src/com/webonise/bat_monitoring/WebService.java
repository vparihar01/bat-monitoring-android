package com.webonise.bat_monitoring;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

public class WebService {
	Context mContext;

	public WebService(Context context) {
		this.mContext = context;
	}

	public void POSTStringRequest(final String userDetails, final String signedKey, final String url, 
			final WebserviceInterface responseInterface) {
		// Tag used to cancel the request
		String tag_string_obj = "string_obj_req";

		Map<String, String> params = new HashMap<String, String>();
		params.put("signed_key", signedKey);
		params.put("user", userDetails);
		final ProgressDialog pDialog = new ProgressDialog(mContext);
		pDialog.setMessage("Loading...");
		pDialog.show();
		StringRequest jsonObjReq = new StringRequest(Method.POST, url,
				new Response.Listener<String>() {
			
			@Override
			public void onResponse(String response) {
				Log.d("onResponse", response);
				responseInterface.onResponse(response);
				pDialog.dismiss();

			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("onErrorResponse", "Error: " + error);
				responseInterface.onError(error.toString());
				pDialog.dismiss();
			}
		}) {


			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/x-www-form-urlencoded");
				headers.put("Encoding", "utf-8");
				return headers;
			}

			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();
				params.put("signed_key", signedKey);
				params.put("user", userDetails);

				return params;
			}

		};
		int socketTimeout = 30000;// 30 seconds - change to what you want
		RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		jsonObjReq.setRetryPolicy(policy);

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq,
				tag_string_obj);
	}

	public void POSTJsonRequest(final String userDetails,final String signedKey, final String url){
		// Tag used to cancel the request
		String tag_json_obj = "json_obj_req";

		final ProgressDialog pDialog = new ProgressDialog(mContext);
		pDialog.setMessage("Loading...");
		pDialog.show();		
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				url, null,
				new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				Log.d("onResponse", response.toString());
				pDialog.dismiss();
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("onErrorResponse", "Error: " + error);
				pDialog.dismiss();
			}
		}) {

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/x-www-form-urlencoded");
				headers.put("Encoding", "utf-8");
				return headers;
			}

			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();
				params.put("signed_key", signedKey);
				params.put("user", userDetails);

				return params;
			}

		};

		AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
	}
	
	
	
	public void GETStringRequest(final String url, final WebserviceInterface responseInterface, final boolean showProgressDialog) {
		// Tag used to cancel the request
		String tag_string_obj = "string_obj_req";

		final ProgressDialog pDialog = new ProgressDialog(mContext);
				
		if(showProgressDialog){
		pDialog.setMessage("Loading...");
		pDialog.show();
		}
		StringRequest stringRequest = new StringRequest(Method.GET, url,
				new Response.Listener<String>() {
			
			@Override
			public void onResponse(String response) {
				Log.d("onResponse", response);
				responseInterface.onResponse(response);
				if(showProgressDialog)
				pDialog.dismiss();

			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("onErrorResponse", "Error: " + error);
				responseInterface.onError(error.toString());
				if(showProgressDialog)
				pDialog.dismiss();
			}
		}) {


//			@Override
//			public Map<String, String> getHeaders() throws AuthFailureError {
//				HashMap<String, String> headers = new HashMap<String, String>();
//				headers.put("Content-Type", "application/x-www-form-urlencoded");
//				headers.put("Encoding", "utf-8");
//				return headers;
//			}

//			@Override
//			protected Map<String, String> getParams() {
//				Map<String, String> params = new HashMap<String, String>();
//				params.put("signed_key", signedKey);
//				params.put("user", userDetails);
//
//				return params;
//			}

		};
		int socketTimeout = 30000;// 30 seconds - change to what you want
		RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		stringRequest.setRetryPolicy(policy);

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(stringRequest,
				tag_string_obj);
	}
	
	public void registerGCMToServer(final String url, final boolean showProgressDialog) {
		// Tag used to cancel the request
		String tag_string_obj = "string_obj_req";

		final ProgressDialog pDialog = new ProgressDialog(mContext);
				
		if(showProgressDialog){
		pDialog.setMessage("Loading...");
		pDialog.show();
		}
		StringRequest stringRequest = new StringRequest(Method.GET, url,
				new Response.Listener<String>() {
			
			@Override
			public void onResponse(String response) {
				Log.d("onResponse", response);
				if(showProgressDialog)
				pDialog.dismiss();

			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("onErrorResponse", "Error: " + error);
				if(showProgressDialog)
				pDialog.dismiss();
			}
		}) {

		};
		int socketTimeout = 30000;// 30 seconds - change to what you want
		RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		stringRequest.setRetryPolicy(policy);

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(stringRequest,
				tag_string_obj);
	}

	public void GETJsonRequest(final String userDetails,final String signedKey, final String url){
		// Tag used to cancel the request
		String tag_json_obj = "json_obj_req";

		final ProgressDialog pDialog = new ProgressDialog(mContext);
		pDialog.setMessage("Loading...");
		pDialog.show();		
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
				url, null,
				new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				Log.d("onResponse", response.toString());
				pDialog.dismiss();
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("onErrorResponse", "Error: " + error);
				pDialog.dismiss();
			}
		}) {

//			@Override
//			public Map<String, String> getHeaders() throws AuthFailureError {
//				HashMap<String, String> headers = new HashMap<String, String>();
//				headers.put("Content-Type", "application/x-www-form-urlencoded");
//				headers.put("Encoding", "utf-8");
//				return headers;
//			}
//
//			@Override
//			protected Map<String, String> getParams() {
//				Map<String, String> params = new HashMap<String, String>();
//				params.put("signed_key", signedKey);
//				params.put("user", userDetails);
//
//				return params;
//			}

		};

		AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
	}
	
	

}
