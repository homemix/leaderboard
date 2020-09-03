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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.thewolftechnologies.leaderboard.R;
import com.thewolftechnologies.leaderboard.adapter.SkillIQAdapter;
import com.thewolftechnologies.leaderboard.models.SkillIQModel;
import com.thewolftechnologies.leaderboard.utills.Common;
import com.thewolftechnologies.leaderboard.utills.Network;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SkillIQLeadersFragment extends Fragment {

    private RecyclerView mRecyclerViewSkillIQ;
    private LinearLayoutManager mLinearLayoutManager;
    private List<SkillIQModel> mSkillIQModelList;
    SkillIQAdapter mSkillIQAdapter;

    Network mNetwork = new Network();
    Common mCommon = new Common();
    String url =mCommon.skillIQUrl();

    public SkillIQLeadersFragment() {
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
        View view = inflater.inflate(R.layout.fragment_skill_i_q_leaders, container, false);

        mRecyclerViewSkillIQ = view.findViewById(R.id.rv_skill_IQ);
        mSkillIQModelList = new ArrayList<>();
        mSkillIQAdapter = new SkillIQAdapter(this, mSkillIQModelList);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLinearLayoutManager.getOrientation();

        mRecyclerViewSkillIQ.setHasFixedSize(true);
        mRecyclerViewSkillIQ.setLayoutManager(mLinearLayoutManager);

        mRecyclerViewSkillIQ.setAdapter(mSkillIQAdapter);

        if (mNetwork.isConnected(getContext())) {
            getSkillIQ();
        } else {
            Toast.makeText(getContext(), "NO Network Connection", Toast.LENGTH_LONG).show();
        }


        return view;
    }



    private void getSkillIQ() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        JsonArrayRequest jsonObjRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    if (response.length() > 0) {
                        mSkillIQModelList.clear();
                        for (int skillIQRecords = 0; skillIQRecords < response.length(); skillIQRecords++) {

                            SkillIQModel skillIQModel = new SkillIQModel();
                            JSONObject jsonObjectSkillIQ = response.getJSONObject(skillIQRecords);

                            String skillIQ = jsonObjectSkillIQ.getString("score");
                            String country = jsonObjectSkillIQ.getString("country");
                            String learningHoursCountry = skillIQ+" skill IQ Score , "+country;

                            skillIQModel.setNameIQ(jsonObjectSkillIQ.getString("name"));
                            skillIQModel.setIQCountry(learningHoursCountry);
                            skillIQModel.setBadgeUrlIQ(jsonObjectSkillIQ.getString("badgeUrl"));


                            mSkillIQModelList.add(skillIQModel);

                            mSkillIQAdapter.notifyDataSetChanged();

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