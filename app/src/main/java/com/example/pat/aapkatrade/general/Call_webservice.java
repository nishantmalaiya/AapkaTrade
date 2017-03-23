package com.example.pat.aapkatrade.general;

import android.content.Context;
import android.util.Log;

import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.HashMap;

/**
 * Created by PPC21 on 24-Jan-17.
 */

public class Call_webservice {

    static JsonObject jsonObject = new JsonObject();
    public static TaskCompleteReminder taskCompleteReminder = null;


    public static void getcountrystatedata(Context c, String webservicetype, String webservice_url, HashMap<String, String> body_parameter, HashMap<String, String> headers) {
        final ProgressBarHandler progressBarHandler = new ProgressBarHandler(c);
        if (webservicetype.equals("country"))

        {
            progressBarHandler.show();


            HashMap<String, String> webservice_body_parameter = body_parameter;
            String authorization = webservice_body_parameter.get("authorization");
            String get_webservice_body_parameter = webservice_body_parameter.get("type");
            String get_webservice_body_parameter_authorization = webservice_body_parameter.get("authorization");


            Log.e("body_parameter null", "body_parameter null");

            HashMap<String, String> webservice_header_type = headers;
            webservice_header_type.get("authorization");


            Ion.with(c)
                    .load(webservice_url)
                    .setHeader("authorization", authorization)
                    .setBodyParameter("authorization", get_webservice_body_parameter_authorization)
                    .setBodyParameter("type", get_webservice_body_parameter)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            progressBarHandler.hide();

                            taskCompleteReminder.Taskcomplete(result);

                            //taskCompleteReminder.Taskcomplete("complete");
//
                        }

                    });


        } else if (webservicetype.equals("state")) {
            progressBarHandler.show();


            HashMap<String, String> webservice_body_parameter = body_parameter;
            String authorization = webservice_body_parameter.get("authorization");
            if (authorization.equals(null)) {
                Log.e("authorization null", "authorization null");
            } else {

                String get_webservice_body_parameter = webservice_body_parameter.get("type");
                String get_webservice_body_parameter_authorization = webservice_body_parameter.get("authorization");
                String country_id = webservice_body_parameter.get("id");

                if (get_webservice_body_parameter.equals(null)) {
                    Log.e("body_parameter null", "body_parameter null");

                } else {


                    HashMap<String, String> webservice_header_type = headers;
                    webservice_header_type.get("authorization");


                    Ion.with(c)
                            .load(webservice_url)
                            .setHeader("authorization", authorization)
                            .setBodyParameter("authorization", get_webservice_body_parameter_authorization)
                            .setBodyParameter("type", webservicetype)
                            .setBodyParameter("id", country_id)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if(result!=null){
                                        Log.e("jsonobject_state1", result.toString());
                                        taskCompleteReminder.Taskcomplete(result);
                                    }else {

                                        Log.e("jsonobject_state1_error", e.toString());


                                    }


                                    progressBarHandler.hide();


                                    //taskCompleteReminder.Taskcomplete("complete");
//
                                }

                            });
                }
            }

        } else if (webservicetype.equals("city")) {
            progressBarHandler.show();


            HashMap<String, String> webservice_body_parameter = body_parameter;
            String authorization = webservice_body_parameter.get("authorization");
            if (authorization.equals(null)) {
                Log.e("authorization null", "authorization null");
            } else {

                String get_webservice_body_parameter = webservice_body_parameter.get("type");
                String get_webservice_body_parameter_authorization = webservice_body_parameter.get("authorization");
                String state_id = webservice_body_parameter.get("id");

                if (get_webservice_body_parameter.equals(null)) {
                    Log.e("body_parameter null", "body_parameter null");

                } else {


                    HashMap<String, String> webservice_header_type = headers;
                    webservice_header_type.get("authorization");


                    Ion.with(c)
                            .load(webservice_url)
                            .setHeader("authorization", authorization)
                            .setBodyParameter("authorization", get_webservice_body_parameter_authorization)
                            .setBodyParameter("type", webservicetype)
                            .setBodyParameter("id", state_id)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    progressBarHandler.hide();

                                    taskCompleteReminder.Taskcomplete(result);
                                    Log.e("jsonobject_city", jsonObject.toString());
                                    //taskCompleteReminder.Taskcomplete("complete");
//
                                }

                            });
                }
            }

        } else if (webservicetype.equals("category")) {

            HashMap<String, String> webservice_body_parameter = body_parameter;
            String authorization = webservice_body_parameter.get("authorization");
            if (authorization.equals(null)) {
                Log.e("authorization null", "authorization null");
            } else {

                String get_webservice_body_parameter = webservice_body_parameter.get("type");
                String get_webservice_body_parameter_authorization = webservice_body_parameter.get("authorization");


                if (get_webservice_body_parameter.equals(null)) {
                    Log.e("body_parameter null", "body_parameter null");

                } else {


                    HashMap<String, String> webservice_header_type = headers;
                    webservice_header_type.get("authorization");


                    Ion.with(c)
                            .load(webservice_url)
                            .setHeader("authorization", authorization)
                            .setBodyParameter("authorization", authorization)
                            .setBodyParameter("type", get_webservice_body_parameter)

                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (result != null)

                                    {



                                        taskCompleteReminder.Taskcomplete(result);
                                        Log.e("result_json_category", result.toString());
                                        //taskCompleteReminder.Taskcomplete("complete");
                                    } else {
                                        Log.e("result_error_category", e.toString());
                                    }
//
                                }

                            });
                }
            }

        } else if (webservicetype.equals("subcategory")) {
            progressBarHandler.show();
            HashMap<String, String> webservice_body_parameter = body_parameter;
            String authorization = webservice_body_parameter.get("authorization");
            if (authorization.equals(null)) {
                Log.e("authorization null", "authorization null");
            } else {

                String get_webservice_body_parameter = webservice_body_parameter.get("type");
                String get_webservice_body_parameter_authorization = webservice_body_parameter.get("authorization");
                String categoryid = webservice_body_parameter.get("id");


                if (get_webservice_body_parameter.equals(null)) {
                    Log.e("body_parameter null", "body_parameter null");

                } else {


                    HashMap<String, String> webservice_header_type = headers;
                    webservice_header_type.get("authorization");


                    Ion.with(c)
                            .load(webservice_url)
                            .setHeader("authorization", authorization)
                            .setBodyParameter("authorization", authorization)
                            .setBodyParameter("type", get_webservice_body_parameter)
                            .setBodyParameter("id", categoryid)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    progressBarHandler.hide();


                                    if (result != null)

                                    {


                                        taskCompleteReminder.Taskcomplete(result);
                                        Log.e("result_json", result.toString());
                                        //taskCompleteReminder.Taskcomplete("complete");
                                    } else {
                                        Log.e("result_error", e.toString());
                                    }
//
                                }

                            });
                }
            }

        }


    }


    public static void call_login_webservice(Context context, String url, String webservicetype, HashMap<String, String> body_parameter, HashMap<String, String> headers) {
        final ProgressBarHandler progressBarHandler = new ProgressBarHandler(context);

        progressBarHandler.show();
        HashMap<String, String> webservice_body_parameter = body_parameter;
        String authorization = webservice_body_parameter.get("authorization");
        if (authorization.equals(null)) {
            Log.e("authorization null", "authorization null");
        } else {

            String get_webservice_body_parameter = webservice_body_parameter.get("type");
            String get_webservice_body_parameter_authorization = webservice_body_parameter.get("authorization");
            String get_webservice_body_parameter_emailid = webservice_body_parameter.get("email");
            String get_webservice_body_parameter_password = webservice_body_parameter.get("password");


            if (get_webservice_body_parameter.equals(null)) {
                Log.e("body_parameter null", "body_parameter null");

            } else {


                HashMap<String, String> webservice_header_type = headers;
                webservice_header_type.get("authorization");


                Ion.with(context)
                        .load(url)
                        .setHeader("authorization", authorization)
                        .setBodyParameter("authorization", get_webservice_body_parameter_authorization)
                        .setBodyParameter("type", webservicetype)
                        .setBodyParameter("email", get_webservice_body_parameter_emailid)
                        .setBodyParameter("password", get_webservice_body_parameter_password)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {

                                progressBarHandler.hide();

                                taskCompleteReminder.Taskcomplete(result);
                                Log.e("jsonobject", jsonObject.toString());
                                //taskCompleteReminder.Taskcomplete("complete");
//
                            }

                        });
            }
        }


    }

    public static void resend_otp(Context context, String url, String webservicetype, HashMap<String, String> body_parameter, HashMap<String, String> headers) {


        HashMap<String, String> webservice_body_parameter = body_parameter;
        String authorization = webservice_body_parameter.get("authorization");
        if (authorization.equals(null)) {
            Log.e("authorization null", "authorization null");
        } else {

            String get_webservice_body_parameter_client_id = webservice_body_parameter.get("client_id");
            String get_webservice_body_parameter_authorization = webservice_body_parameter.get("authorization");


            if (get_webservice_body_parameter_client_id.equals(null)) {
                Log.e("body_parameter null", "body_parameter null");

            } else {


                HashMap<String, String> webservice_header_type = headers;
                webservice_header_type.get("authorization");


                Ion.with(context)
                        .load(url)
                        .setHeader("authorization", authorization)
                        .setBodyParameter("authorization", get_webservice_body_parameter_authorization)
                        //.setBodyParameter("type", webservicetype)
                        .setBodyParameter("client_id", get_webservice_body_parameter_client_id)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {

                                taskCompleteReminder.Taskcomplete(result);
                                Log.e("jsonobject", jsonObject.toString());
                                //taskCompleteReminder.Taskcomplete("complete");
//
                            }

                        });
            }
        }


    }


    public static void verify_otp(Context context, String url, String webservicetype, HashMap<String, String> body_parameter, HashMap<String, String> headers) {


        HashMap<String, String> webservice_body_parameter = body_parameter;
        String authorization = webservice_body_parameter.get("authorization");
        if (authorization.equals(null)) {
            Log.e("authorization null", "authorization null");
        } else {

            String get_webservice_body_parameter_client_id = webservice_body_parameter.get("client_id");
            String get_webservice_body_parameter_authorization = webservice_body_parameter.get("authorization");
            String get_webservice_body_parameter_otp = webservice_body_parameter.get("otp");


            if (get_webservice_body_parameter_client_id.equals(null)) {
                Log.e("body_parameter null", "body_parameter null");

            } else {


                HashMap<String, String> webservice_header_type = headers;
                webservice_header_type.get("authorization");


                Ion.with(context)
                        .load(url)
                        .setHeader("authorization", authorization)
                        .setBodyParameter("authorization", get_webservice_body_parameter_authorization)
                        .setBodyParameter("otp", get_webservice_body_parameter_otp)
                        .setBodyParameter("client_id", get_webservice_body_parameter_client_id)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {

                                taskCompleteReminder.Taskcomplete(result);
                                Log.e("jsonobject", jsonObject.toString());
                                //taskCompleteReminder.Taskcomplete("complete");
//
                            }

                        });
            }
        }


    }


    public static void forgot_password(Context context, String url, String webservicetype, HashMap<String, String> body_parameter, HashMap<String, String> headers) {


        HashMap<String, String> webservice_body_parameter = body_parameter;
        String authorization = webservice_body_parameter.get("authorization");
        if (authorization.equals(null)) {
            Log.e("authorization null", "authorization null");
        } else {

            String get_webservice_body_parameter_client_id = webservice_body_parameter.get("client_id");
            String get_webservice_body_parameter_authorization = webservice_body_parameter.get("authorization");
            String get_webservice_body_parameter_type = webservice_body_parameter.get("type");
            String get_webservice_body_parameter_email = webservice_body_parameter.get("email");
            String get_webservice_body_parameter_mobile = webservice_body_parameter.get("mobile");







            if (get_webservice_body_parameter_client_id.equals(null)) {
                Log.e("body_parameter null", "body_parameter null");

            } else {


                HashMap<String, String> webservice_header_type = headers;
                webservice_header_type.get("authorization");


                Ion.with(context)
                        .load(url)
                        .setHeader("authorization", authorization)
                        .setBodyParameter("authorization", get_webservice_body_parameter_authorization)
                        .setBodyParameter("type", get_webservice_body_parameter_type)
                        .setBodyParameter("email", get_webservice_body_parameter_email)
                        .setBodyParameter("mobile", get_webservice_body_parameter_mobile)

                        .setBodyParameter("client_id", get_webservice_body_parameter_client_id)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {

                                taskCompleteReminder.Taskcomplete(result);
                                Log.e("jsonobject", jsonObject.toString());
                                //taskCompleteReminder.Taskcomplete("complete");
//
                            }

                        });
            }
        }


    }


    public static void suggest_search(Context context, String url, String webservicetype, HashMap<String, String> body_parameter, HashMap<String, String> headers) {


        HashMap<String, String> webservice_body_parameter = body_parameter;
        String authorization = webservice_body_parameter.get("authorization");
        if (authorization.equals(null)) {
            Log.e("authorization null", "authorization null");
        } else {


            String get_webservice_body_parameter_authorization = webservice_body_parameter.get("authorization");
            String get_webservice_body_parameter_location = webservice_body_parameter.get("location");


            HashMap<String, String> webservice_header_type = headers;
            webservice_header_type.get("authorization");


            Ion.with(context)
                    .load(url)
                    .setHeader("authorization", authorization)
                    .setBodyParameter("location", get_webservice_body_parameter_location.trim())
                    .setBodyParameter("authorization", get_webservice_body_parameter_authorization)

                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {

                            taskCompleteReminder.Taskcomplete(result);
                            Log.e("jsonobject", jsonObject.toString());
                            //taskCompleteReminder.Taskcomplete("complete");
//
                        }

                    });

        }


    }

    public static void location_webservice(Context c, String webservice_url) {

        Ion.with(c)
                .load(webservice_url)


                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        taskCompleteReminder.Taskcomplete(result);
                        Log.e("jsonobject_location", jsonObject.toString());
                        //taskCompleteReminder.Taskcomplete("complete");
//
                    }

                });




    }




    }


