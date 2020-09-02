package com.thewolftechnologies.leaderboard.ui.main;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.thewolftechnologies.leaderboard.R;
import com.thewolftechnologies.leaderboard.adapter.LearningLeadersAdapter;
import com.thewolftechnologies.leaderboard.models.LearningLeadersModel;
import com.thewolftechnologies.leaderboard.utills.Common;
import com.thewolftechnologies.leaderboard.utills.Network;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LearningLeadersFragment extends Fragment {

    private RecyclerView mRecyclerViewLearningLeaders;
    private LinearLayoutManager mLinearLayoutManager;
    private List<LearningLeadersModel> mLearningLeadersModelList;
    LearningLeadersAdapter mLearningLeadersAdapter;

    Network mNetwork = new Network();
    Common mCommon = new Common();
    String url = mCommon.learningLeadersUrl();

    public LearningLeadersFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_learning_leaders, container, false);

        mRecyclerViewLearningLeaders = view.findViewById(R.id.rv_learning_leaders);
        mLearningLeadersModelList = new ArrayList<>();
        mLearningLeadersAdapter = new LearningLeadersAdapter(this, mLearningLeadersModelList);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLinearLayoutManager.getOrientation();

        mRecyclerViewLearningLeaders.setHasFixedSize(true);
        mRecyclerViewLearningLeaders.setLayoutManager(mLinearLayoutManager);

        mRecyclerViewLearningLeaders.setAdapter(mLearningLeadersAdapter);
        if (mNetwork.isConnected(getContext())) {
            getLearningLeaders();
        } else {
            Toast.makeText(getContext(), "NO Network Connection", Toast.LENGTH_LONG).show();
        }

        return view;
    }


    private void getLearningLeaders() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        JsonArrayRequest jsonObjRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    if (response.length() > 0) {
                        mLearningLeadersModelList.clear();
                        for (int leadersRecords = 0; leadersRecords < response.length(); leadersRecords++) {

                            LearningLeadersModel learningLeadersModel = new LearningLeadersModel();
                            JSONObject jsonObjectLeaders = response.getJSONObject(leadersRecords);

                            String learningHours = jsonObjectLeaders.getString("hours");
                            String country = jsonObjectLeaders.getString("country");
                            String learningHoursCountry = learningHours+" learning hours, "+country;

                            learningLeadersModel.setName(jsonObjectLeaders.getString("name"));
                            learningLeadersModel.setHoursCountry(learningHoursCountry);
                            learningLeadersModel.setBadgeUrl(jsonObjectLeaders.getString("badgeUrl"));

                            mLearningLeadersModelList.add(learningLeadersModel);

                            mLearningLeadersAdapter.notifyDataSetChanged();

                            progressDialog.dismiss();

                        }
                    } else {
                        Toast.makeText(getContext(), "No Data To Display ", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }

                } catch (Exception e) {
                    Toast.makeText(getContext(), "Error In Getting Data ", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error In Getting Data ", Toast.LENGTH_LONG).show();
                VolleyLog.d("volley", "Error: " + error.getMessage());
                error.printStackTrace();
                progressDialog.dismiss();

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }


        };

        Volley.newRequestQueue(getContext()).add(jsonObjRequest);

    }
}