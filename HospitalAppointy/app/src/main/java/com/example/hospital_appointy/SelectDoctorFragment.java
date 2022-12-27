package com.example.hospital_appointy;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SelectDoctorFragment extends Fragment {
    static String TAG = "SelectDoctorFragment";
    static String ARG_ITEM = "args_item";
    String NAME_EXTRA = "com.example.name";
    String DETAILEXTRA = "com.example.detailDoctor";
    //Callbacks mCallbacks;
//    Adapter mAdapter;
    List<doctorItem> mDoctorItemList = new ArrayList<>();
    private RecyclerView mSelectDoctor;
    Button personal;
    String Username;
    String Department;
    private final int IS_FINISH = 1;
    List<doctorItem> needDoctors = new ArrayList<>();
    //    Handler handler = new Handler();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<doctorItem> needDoctors1 = new ArrayList<>();
            needDoctors1 = (List<doctorItem>)msg.obj;
            needDoctors = needDoctors1;
            System.out.println("nes:"+needDoctors.size());
            setupAdapter();
        }
    };



    public static SelectDoctorFragment newInstance(String udStr) {
        Bundle args = new Bundle();
        args.putString(ARG_ITEM,udStr);
        Log.i(TAG,"act:"+udStr);
        SelectDoctorFragment fragment =  new SelectDoctorFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JSONObject jsonObject = JSONObject.fromObject(getArguments().getString(ARG_ITEM));
        Username = jsonObject.getString("username");
        Department = jsonObject.getString("department");
        new Thread(){
            @Override
            public void run() {
                getneedInfo("http://192.168.31.157:1206/Server_Pro_war/DoctorServlet");
                //获得所有用户信息

                Log.i(TAG,"读取信息完毕");
            }
        }.start();
        System.out.println("科室："+Department);
        Log.i(TAG,"进入下个界面："+getArguments().getString(ARG_ITEM));
//        setupAdapter();
        //获取服务器数据
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_select_doctor, container, false);
        mSelectDoctor = (RecyclerView) v.findViewById(R.id.fragment_selectdoctor_recycler_view);
        mSelectDoctor.setLayoutManager(new LinearLayoutManager(getActivity()));
        personal = (Button) v.findViewById(R.id.personal2);
        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击个人中心跳转
                Intent intent = new Intent(getActivity(), PersonalActivity.class);
                intent.putExtra(NAME_EXTRA, Username);
                startActivity(intent);
            }
        });
        setupAdapter();
        return v;
    }

    public class SelectDoctorHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private doctorItem mDoctorItem;
        private TextView price;
        private TextView doctorName;
        private TextView doctorDepart;

        public SelectDoctorHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            price = (TextView) itemView.findViewById(R.id.price);
            doctorName = (TextView) itemView.findViewById(R.id.doctor_name);
            doctorDepart = (TextView) itemView.findViewById(R.id.doctor_department);
        }

        public void bindDoctor(doctorItem doctorItem) {
            mDoctorItem = doctorItem;
            price.setText(""+mDoctorItem.getDoctorPrice());
            doctorName.setText(mDoctorItem.getDoctorName());
            doctorDepart.setText(mDoctorItem.getDoctorDepartment());
        }

        @Override
        public void onClick(View view) {
            //跳转到下一个页面
            User.Doctorid = mDoctorItem.getDoctorId();
            User.price = mDoctorItem.getDoctorPrice();
            Intent intent = new Intent(getActivity(),DoctorDetailActivity.class);
            intent.putExtra(DETAILEXTRA,mDoctorItem);
            startActivity(intent);
        }
    }
    private class SelectDoctorAdapter extends RecyclerView.Adapter<SelectDoctorHolder> {

        private List<doctorItem> DoctorItemList = new ArrayList<>();
        public SelectDoctorAdapter(List<doctorItem> items) {
            DoctorItemList = items;
        }

        @NonNull
        @Override
        public SelectDoctorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.doctor_item, parent, false);
            return new SelectDoctorHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SelectDoctorHolder holder, int position) {
//            setupAdapter();
            doctorItem item = needDoctors.get(position);
            holder.bindDoctor(item);
        }

        @Override
        public int getItemCount() {
            System.out.println("元素数量："+DoctorItemList.size());
            return needDoctors.size();

        }
    }

    private void setupAdapter() {
        if (isAdded()) {
            mSelectDoctor.setAdapter(new SelectDoctorAdapter(needDoctors));
        }
    }
    //从网络上获得消息
    void getneedInfo(String url){
//        int count = 0;

        List<doctorItem> needDoctors1 = new ArrayList<>();
        String allInfo = "";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if(response.code() != 200){
                Looper.prepare();
                Log.i(TAG,"服务器请求失败"+response.code());
                Toast.makeText(getActivity(), "服务器请求失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }else {
//                Looper.prepare();
                allInfo = response.body().string();
                //Log.i(TAG,allInfo);
                JSONArray jsonArray = JSONArray.fromObject(allInfo);
                for (int i = 0; i < jsonArray.size(); i++) {
                    doctorItem u = new doctorItem(
                            jsonArray.getJSONObject(i).getString("doctorDepartment"),
                            jsonArray.getJSONObject(i).getInt("doctorId"),
                            jsonArray.getJSONObject(i).getString("date"),
                            jsonArray.getJSONObject(i).getString("doctorName"),
                            jsonArray.getJSONObject(i).getInt("doctorPrice"),
                            jsonArray.getJSONObject(i).getString("doctorIntro"));
                    mDoctorItemList.add(u);
                }
                Set set = new HashSet();
                for (doctorItem d:mDoctorItemList){
                    if(d.getDoctorDepartment().equals(Department)&&set.add(d.getDoctorId())){
                        needDoctors1.add(d);
                    }
                }
                Message message = Message.obtain();
                message.obj = needDoctors1;
                message.what = IS_FINISH;
                handler.sendMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Looper.loop();
    }
}
