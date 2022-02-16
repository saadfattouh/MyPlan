package com.example.myplan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.myplan.api.Urls;
import com.example.myplan.utils.Validation;

import org.json.JSONException;
import org.json.JSONObject;


public class Register extends AppCompatActivity {

    EditText mNameET;
    EditText mPassET;
    EditText mEmailET;
    EditText mPhone;

    Button mRegisterBtn;
    Button mToLoginBtn;


    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bindViews();

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        mToLoginBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, Login.class));
            finish();
        });

        mRegisterBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, ChooseSubject.class));
//            if(Validation.validateInput(this, mNameET, mPassET, mEmailET, mPhone))
//                register();
        });




    }

    private void bindViews() {
        mNameET = findViewById(R.id.name);
        mPassET = findViewById(R.id.password);
        mEmailET = findViewById(R.id.email);
        mPhone = findViewById(R.id.phone);

        mRegisterBtn = findViewById(R.id.btnRegister);
        mToLoginBtn = findViewById(R.id.btnLinkToLoginScreen);

    }


    private void register() {
        pDialog.setMessage("Processing Please wait...");
        pDialog.show();

        //first getting the values
        final String pass = mPassET.getText().toString();
        final String name = mNameET.getText().toString();
        final String email = mEmailET.getText().toString();


        String url = Urls.BASE_URL + Urls.REGISTER;

        AndroidNetworking.post(url)
                .addBodyParameter("name", name)
                .addBodyParameter("email", email)
                .addBodyParameter("password", pass)
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

//                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//
//                                //getting the user from the response
//                                JSONObject userJson = obj.getJSONObject("data");
//                                User user;
//                                SharedPrefManager.getInstance(getApplicationContext()).setUserType(Constants.USER);
//                                user = new User(
//                                        Integer.parseInt(userJson.getString("id")),
//                                        userJson.getString("name"),
//                                        "+966 "+userJson.getString("email")
//                                );
//
//                                //storing the user in shared preferences
//                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
//                                goToUserMainActivity();
//                                finish();
//
//                                mRegisterBtn.setEnabled(true);
//                            } else if(obj.getInt("status") == -1){
//                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//                                mRegisterBtn.setEnabled(true);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        pDialog.dismiss();
                        mRegisterBtn.setEnabled(true);
                        Toast.makeText(Register.this, anError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}