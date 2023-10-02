package com.example.fn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.opentelemetry.api.trace.SpanKind;

public class HelloFunction {

    @WithSpan(kind = SpanKind.SERVER)
    public String handleRequest(String input) {

        String name = (input == null || input.isEmpty()) ? "world" : input;
        System.out.println("Inside Java Hello World function");

        try {
            String response = MakeRequest();
            System.out.println(response);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "Hello, " + name + "!";

    }

    @WithSpan
    public String MakeRequest() throws Exception {
        // avoid creating several instances, should be singleon
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://bnw4q2fwudnbvrcclrzw4gbjja.apigateway.sa-saopaulo-1.oci.customer-oci.com/catfact/secondfunction")
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    // MakeRequest using HttpURLConnection
    // @WithSpan
    // public void MakeRequest() throws Exception {
    // URL url = new URL(
    // "https://bnw4q2fwudnbvrcclrzw4gbjja.apigateway.sa-saopaulo-1.oci.customer-oci.com/catfact/secondfunction");
    // HttpURLConnection con = (HttpURLConnection) url.openConnection();
    // con.setRequestMethod("GET");
    // int status = con.getResponseCode();
    // BufferedReader in = new BufferedReader(
    // new InputStreamReader(con.getInputStream()));
    // String inputLine;
    // StringBuffer content = new StringBuffer();
    // while ((inputLine = in.readLine()) != null) {
    // content.append(inputLine);
    // }
    // System.out.println(content.toString());

    // in.close();
    // con.disconnect();

    // }

}
