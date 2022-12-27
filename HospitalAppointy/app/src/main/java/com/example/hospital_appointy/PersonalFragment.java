package com.example.hospital_appointy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.sf.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PersonalFragment extends Fragment {
    static String TAG = "PersonalFragment";
    String DETAILEXTRA = "com.example.detailPersonal";
    static String ARG_ITEM = "args_item";
    List<userDoctorItem> needUserDoctorItem = new ArrayList<>();
    RecyclerView mPersonalDoctor;
    boolean flag = false;
    String userName;
    private final int IS_FINISH = 1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            List<userDoctorItem> needDoctors1 = new ArrayList<>();
            needDoctors1 = (List<userDoctorItem>)msg.obj;
            needUserDoctorItem = needDoctors1;
//            System.out.println("nes:"+needDoctors.size());
            setupAdapter();
        }
    };

    public static PersonalFragment newInstance(String username) {
        Bundle args = new Bundle();
        args.putString(ARG_ITEM,username);
        Log.i(TAG,"act:"+username);
        PersonalFragment fragment =  new PersonalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userName = getArguments().getString(ARG_ITEM);
        new Thread(){
            @Override
            public void run() {
                getneedInfo("http://192.168.31.157:1206/Server_Pro_war/userDoctorServlet");
                //获得所有用户信息

                Log.i(TAG,"读取信息完毕");
            }
        }.start();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_personal, container, false);
        mPersonalDoctor = (RecyclerView) v.findViewById(R.id.fragment_personal_recycler_view);
        mPersonalDoctor.setLayoutManager(new LinearLayoutManager(getActivity()));

        setupAdapter();
        return v;
    }
    public class PersonalHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private userDoctorItem mUserDoctorItem;
        private TextView date;
        private TextView doctorName;
        private TextView doctorDepart;

        public PersonalHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            date = (TextView) itemView.findViewById(R.id.date);
            doctorName = (TextView) itemView.findViewById(R.id.doctor_namep);
            doctorDepart = (TextView) itemView.findViewById(R.id.doctor_departmentp);
        }

        public void bindPerson(userDoctorItem userDoctorItem) {
            mUserDoctorItem = userDoctorItem;
            date.setText(""+mUserDoctorItem.getDate());
            if(!flag){
                doctorName.setText(userDoctorItem.getDoctorName());
            }else {
                doctorName.setText(userDoctorItem.getUserName());
            }
            doctorDepart.setText(userDoctorItem.getDoctorDepartment());
        }

        @Override
        public void onClick(View view) {
            //跳转到下一个页面
            User.Doctorid = mUserDoctorItem.getDoctorId();
            User.price = mUserDoctorItem.getDoctorPrice();
            Intent intent = new Intent(getActivity(),UserDoctorActivity.class);
            intent.putExtra(DETAILEXTRA,mUserDoctorItem);
            startActivity(intent);
//            mCallbacks.onDoctorSelected();
        }
    }
    private class PersonalAdapter extends RecyclerView.Adapter<PersonalHolder> {

        private List<userDoctorItem> mUserDoctorItemList = new ArrayList<>();
        public PersonalAdapter(List<userDoctorItem> items) {
            mUserDoctorItemList = items;
        }

        @NonNull
        @Override
        public PersonalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.personal_item, parent, false);
            return new PersonalHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PersonalHolder holder, int position) {
//            setupAdapter();
            userDoctorItem item = mUserDoctorItemList.get(position);
            holder.bindPerson(item);
        }

        @Override
        public int getItemCount() {
            System.out.println("元素数量："+mUserDoctorItemList.size());
            return mUserDoctorItemList.size();

        }
    }
    private void setupAdapter() {
        if (isAdded()) {
            mPersonalDoctor.setAdapter(new PersonalAdapter(needUserDoctorItem));
        }
    }
    void getneedInfo(String url){
        List<userDoctorItem> need = new ArrayList<>();
        List<userDoctorItem> need1 = new ArrayList<>();
        String allInfo = "";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() != 200) {
                Looper.prepare();
                Log.i(TAG, "服务器请求失败" + response.code());
                Toast.makeText(getActivity(), "服务器请求失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            } else {
//                Looper.prepare();
                allInfo = response.body().string();
                //Log.i(TAG,allInfo);
                JSONArray jsonArray = JSONArray.fromObject(allInfo);
                for (int i = 0; i < jsonArray.size(); i++) {
                    userDoctorItem u = new userDoctorItem(
                            jsonArray.getJSONObject(i).getString("userName"),
                            jsonArray.getJSONObject(i).getString("doctorDepartment"),
                            jsonArray.getJSONObject(i).getInt("doctorId"),
                            jsonArray.getJSONObject(i).getString("date"),
                            jsonArray.getJSONObject(i).getString("doctorName"),
                            jsonArray.getJSONObject(i).getInt("doctorPrice"),
                            jsonArray.getJSONObject(i).getString("doctorIntro"));
                    need.add(u);
                }
//                Set set = new HashSet();
                String str = "";
                for (int i = 0; i < userName.length(); i++) {
                    if (userName.charAt(i) <= '9' && userName.charAt(i) >= '0') {
                        str += userName.charAt(i);
                    } else {
                        break;
                    }
                }
                System.out.println("数字账号："+str);
                if (userName.charAt(0) <= '9' && userName.charAt(0) >= '0') {
                    flag = true;
                    for (userDoctorItem d : need) {
//                        System.out.println(str);
                        System.out.println(d.getDoctorId());
                        if (str.equals(d.getDoctorId()+"")) {
                            need1.add(d);
                        }
                    }
                } else {
                    for (userDoctorItem d : need) {
                        if (d.getUserName().equals(userName)) {
                            need1.add(d);
                        }
                    }
                }
                    System.out.println("need1:"+need1.size());
//                System.out.println("xy挂号："+need1.get(0).getDoctorName());
                    Message message = Message.obtain();
                    message.obj = need1;
                    message.what = IS_FINISH;
                    handler.sendMessage(message);
            }
            } catch(IOException e){
                e.printStackTrace();
            }
        }
}
