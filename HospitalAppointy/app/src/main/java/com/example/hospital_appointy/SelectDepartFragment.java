package com.example.hospital_appointy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectDepartFragment extends Fragment {
    final static String TAG = "SelectDepartFragment";
    static String ARG_ITEM = "args_item";
    String NAME_EXTRA = "com.example.name";
    final static String[] epartments = new String[]{"胸外科","神经内科", "呼吸内科","血液科", "妇产科", "儿科", "耳鼻喉科", "口腔科", "眼科"};
    //private Callbacks mCallbacks;
    String DEPARTEXTRA = "com.example.hospital_appointy.Depart";
    Button personal;
    private RecyclerView mSelectDepart;
    String departname;
    String username;
    List<String> departments = new ArrayList<>();

    public static SelectDepartFragment newInstance(String username) {
        Bundle args = new Bundle();
        args.putString(ARG_ITEM, username);
        SelectDepartFragment fragment = new SelectDepartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, getArguments().getString(ARG_ITEM));
        username = getArguments().getString(ARG_ITEM);
        for (String d : epartments) {
            departments.add(d);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_select_depart, container, false);

        mSelectDepart = (RecyclerView) v.findViewById(R.id.fragment_selectdepart_recycler_view);
        mSelectDepart.setLayoutManager(new LinearLayoutManager(getActivity()));
        personal = (Button) v.findViewById(R.id.personal);
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:设置按钮响应
                Intent intent = new Intent(getActivity(), PersonalActivity.class);
                intent.putExtra(NAME_EXTRA, username);
                startActivity(intent);
            }
        });

        setupAdapter();
        return v;
    }


    public class SelectDepartHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mDepartment;

        public SelectDepartHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mDepartment = (TextView) itemView.findViewById(R.id.item_text);
        }

        public void bindDepart(String department) {
            mDepartment.setText(department);
        }

        @Override
        public void onClick(View view) {
            //TODO:点击科室跳转到医生介绍
            departname = (String) mDepartment.getText();
            //mCallbacks.onDepartSelected(departname,username);
            //进入选医生的界面
            Log.i(TAG, "选择科室");
            JSONObject ud = new JSONObject();
            try {
                ud.put("username", username);
                ud.put("department", departname);
                String udStr = ud.toString();
                Intent intent = new Intent(getActivity(), SelectDoctorActivity.class);
                intent.putExtra(DEPARTEXTRA, udStr);
                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


        private class SelectDepartAdapter extends RecyclerView.Adapter<SelectDepartHolder> {

            private List<String> mDepartmentList = new ArrayList<>();

            public SelectDepartAdapter(List<String> items) {
                mDepartmentList = items;
            }

            @NonNull
            @Override
            public SelectDepartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View view = inflater.inflate(R.layout.department_item, parent, false);
                return new SelectDepartHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull SelectDepartHolder holder, int position) {
                String department = mDepartmentList.get(position);
                holder.bindDepart(department);
            }

            @Override
            public int getItemCount() {
                return mDepartmentList.size();
            }
        }

        private void setupAdapter() {
            if (isAdded()) {
                mSelectDepart.setAdapter(new SelectDepartAdapter(departments));
            }
        }
    }

