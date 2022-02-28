package com.example.myplan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.myplan.api.Urls;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    private Button mLoginBtn;
    private Button mToRegisterBtn;
    private EditText mEmailET;
    private EditText mPassET;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //bind to views
        mEmailET = findViewById(R.id.email);
        mPassET =  findViewById(R.id.password);
        mLoginBtn =  findViewById(R.id.btnLogin);
        mToRegisterBtn =  findViewById(R.id.btnLinkToRegisterScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Login button Click Event
        mLoginBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {


//                mLoginBtn.setEnabled(false);

                //validation
                String email = mEmailET.getText().toString().trim();
                String password = mPassET.getText().toString().trim();
                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    if(!email.contains(getResources().getString(R.string.student_email_suffex))){
                        Toast.makeText(Login.this, getResources().getString(R.string.please_provide_a_valid_student_email), Toast.LENGTH_SHORT).show();
                        mLoginBtn.setEnabled(true);
                    }else {
                        //this is a student with valid student email
                        startActivity(new Intent(Login.this, MainActivity.class));
//                        login(email, password);
                    }

                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.email_and_password_required), Toast.LENGTH_LONG)
                            .show();
                }
            }
        });




        // Link to Register Screen
        mToRegisterBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        Register.class);
                startActivity(i);
                finish();
            }
        });

    }


    private void login(String userName, String password) {
        pDialog.setMessage("Processing Please wait...");
        pDialog.show();

        String url = Urls.BASE_URL + Urls.LOGIN;

        AndroidNetworking.post(url)
                .addBodyParameter("email", userName)
                .addBodyParameter("password", password)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        pDialog.dismiss();

                        try {
                            //converting response to json object
                            JSONObject obj = response;
                            //if no error in response
                            if (obj.getInt("status") == 1) {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        pDialog.dismiss();
                        mLoginBtn.setEnabled(true);
                        Toast.makeText(Login.this, anError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}