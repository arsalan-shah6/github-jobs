package com.app.gethubjobs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
     EditText jobSearch,jobLocation;
     TextView jobId,jobType,jobURL,jobCreated,companyUrl,companyLocation,jobTitle,jobDescription,SearchBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        jobSearch=findViewById( R.id.search );
        jobLocation=findViewById( R.id.near );
        jobId=findViewById( R.id.id );
        jobType=findViewById( R.id.type );
        jobURL=findViewById( R.id.url );
        jobCreated=findViewById( R.id.created );
        companyUrl=findViewById( R.id.company_url );
        companyLocation=findViewById( R.id.location );
        jobTitle=findViewById( R.id.title );
        jobDescription=findViewById( R.id.description );
        SearchBtn=findViewById( R.id.searchBtn );

        SearchBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText( MainActivity.this, "Tapped", Toast.LENGTH_SHORT ).show();
                String search=jobSearch.getText().toString().trim();
                String location=jobLocation.getText().toString().trim();
                ApiCall(search,location);

            }
        } );



    }

    private void ApiCall(String search, String location) {
              String Url="https://jobs.github.com/positions.json?description="+search+"&location="+location;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request= new StringRequest( Request.Method.GET, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array=new JSONArray(response);
                            for (int i=0; i<array.length();i++){
                                JSONObject jsonObject=array.getJSONObject(i );


                                jobType.setText(jsonObject.getString( "type" ));
                                companyUrl.setText(jsonObject.getString( "company" ));
                                jobId.setText(jsonObject.getString( "id" ));
                                jobURL.setText(jsonObject.getString( "url" ));
                                jobCreated.setText(jsonObject.getString( "created_at" ));
                                companyLocation.setText(jsonObject.getString( "location" ));
                                jobTitle.setText(jsonObject.getString( "title" ));
                                jobDescription.setText(jsonObject.getString( "description" ));



                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText( MainActivity.this, ""+error, Toast.LENGTH_SHORT ).show();

            }
        } );
        queue.add( request );
    }

}