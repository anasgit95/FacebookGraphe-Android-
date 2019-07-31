package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.facebook.GraphRequest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class SOC_DB  extends AsyncTask<String, Void, String> {
    Context context;
    GraphRequest.GraphJSONObjectCallback    graphJSONObjectCallback;
    SOC_DB (Context context){
        this.context =  context;
    }

    public SOC_DB(GraphRequest.GraphJSONObjectCallback graphJSONObjectCallback) {
        this.graphJSONObjectCallback = graphJSONObjectCallback ;
    }


    @Override
    protected String doInBackground(String... params) {
        String path = "http://192.168.100.45/login.php";
      String email =params[0];
      String nom = params[1];
      String prenom =params[2];
        try {
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream OS = httpURLConnection.getOutputStream() ;
            BufferedWriter bufferedWriter =  new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
            String data = URLEncoder.encode("email","UTF-8") +"="+ URLEncoder.encode(email,"UTF-8")+"&"+
                    URLEncoder.encode("nom", "UTF-8") +"="+ URLEncoder.encode(nom, "UTF-8") +"&"+URLEncoder.encode("prenom", "UTF-8") +"="+ URLEncoder.encode(prenom, "UTF-8")
                    ;
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            InputStream IS = httpURLConnection.getInputStream() ;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS,"iso-8859-1"));
            String result="";
            String line="";



            while ((line=bufferedReader.readLine()) !=null ) {
                result+=line ;
            }
              bufferedReader.close();
            IS.close();
            httpURLConnection.disconnect();
            return result ;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


}
