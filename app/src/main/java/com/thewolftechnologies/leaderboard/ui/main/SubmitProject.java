package com.thewolftechnologies.leaderboard.ui.main;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.thewolftechnologies.leaderboard.R;
import com.thewolftechnologies.leaderboard.utills.Common;

import java.util.HashMap;
import java.util.Map;

public class SubmitProject extends AppCompatActivity {
    private EditText et_first_name, et_last_name, et_email, et_github_link;
    private Button btn_submit_project;

    Common mCommon = new Common();
    String submitUrl = mCommon.projectSubmitUrl();


    private String emailValue;
    private String firstNameValue;
    private String lastNameValue;
    private String linkValue;

    public final static String emailAddress = "entry.1824927963";
    public final static String firstName = "entry.1877115667";
    public final static String lastName = "entry.2006916086";
    public final static String linkToProject = "entry.284483984";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        et_first_name = findViewById(R.id.et_first_name);
        et_last_name = findViewById(R.id.et_last_name);
        et_email = findViewById(R.id.et_email);
        et_github_link = findViewById(R.id.et_github_link);
        btn_submit_project = findViewById(R.id.btn_submit_project);

        btn_submit_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(SubmitProject.this);
                builder.setMessage("Are you sure ?")
                        //.setTitle("Delete Entry")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                emailValue = et_email.getText().toString();
                                firstNameValue = et_first_name.getText().toString();
                                lastNameValue = et_last_name.getText().toString();
                                linkValue = et_github_link.getText().toString();

                                if (emailValue.matches("") || firstNameValue.matches("") || lastNameValue.matches("") || linkValue.matches("")) {
                                    Toast.makeText(SubmitProject.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                   postSubmitProject();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = builder.create();

                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(SubmitProject.this.getColor(R.color.colorOrange));
            }
        });
    }

    private void postSubmitProject() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, submitUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        progressDialog.dismiss();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        progressDialog.dismiss();
                        Log.d("Error.Response", "" + error);

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(emailAddress, et_email.getText().toString());
                params.put(firstName, et_first_name.getText().toString());
                params.put(lastName, et_last_name.getText().toString());
                params.put(linkToProject, et_github_link.getText().toString());
                return params;
            }


        };
        Volley.newRequestQueue(SubmitProject.this).add(postRequest);

    }
}