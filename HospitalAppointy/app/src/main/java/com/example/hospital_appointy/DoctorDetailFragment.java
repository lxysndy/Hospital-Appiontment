package com.example.hospital_appointy;

import static android.app.Activity.RESULT_OK;

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

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.sf.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DoctorDetailFragment extends Fragment {
    static String ARG_ITEM = "args_item";
    static String JUDGE = "judge";
    static String PRICE = "doctor_price";
    static String TAG = "DoctorDetailFragment";
    List<doctorItem> mDoctorItemList = new ArrayList<>();
    doctorItem mDoctorItem;
    userDoctorItem mUserDoctorItem;
    RecyclerView mDetailInfo;
    String Introduction="";
    TextView Intro;
    String targetDate;
    private final int IS_FINISH = 1;
    List<String> dates = new ArrayList<>();
    List<userDoctorItem> booked = new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<String> ndate = new ArrayList<>();
            ndate = (List<String>)msg.obj;
            dates = ndate;
            System.out.println("nes:"+dates.size());
            setupAdapter();
        }
    };
    public static DoctorDetailFragment newInstance1(doctorItem doctorItem) {
        Bundle args = new Bundle();
        args.clear();
        args.putSerializable(ARG_ITEM,doctorItem);
        args.putString(JUDGE,"doctorItem");
        //Log.i(TAG,"act:"+doctorItem);
        DoctorDetailFragment fragment =  new DoctorDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public static DoctorDetailFragment newInstance2(userDoctorItem userDoctorItem) {
        Bundle args = new Bundle();
        args.clear();
        args.putSerializable(ARG_ITEM,userDoctorItem);
        //Log.i(TAG,"act:"+doctorItem);
        args.putString(JUDGE,"userDoctorItem");
        DoctorDetailFragment fragment =  new DoctorDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int doctorid;
        String username;
        if(getArguments().getString(JUDGE).equals("doctorItem")){
            mDoctorItem = (doctorItem) getArguments().getSerializable(ARG_ITEM);
            doctorid = mDoctorItem.getDoctorId();
            Introduction = mDoctorItem.getDoctorName()+"，"+mDoctorItem.getDoctorIntro();
            System.out.println("医生名字："+mDoctorItem.getDoctorName());
//            username = mDoctorItem.getDoctorName();
        }else{
            mUserDoctorItem = (userDoctorItem) getArguments().getSerializable(ARG_ITEM);
            doctorid = mUserDoctorItem.getDoctorId();
            Introduction = mUserDoctorItem.getDoctorName()+"，"+mUserDoctorItem.getDoctorIntro();
            System.out.println(Introduction);
            System.out.println("医生名字："+mUserDoctorItem.getDoctorName());
//            username = mUserDoctorItem.getDoctorName();
        }
        new Thread(){
            @Override
            public void run() {
                getneedInfo("http://192.168.31.157:1206/Server_Pro_war/DoctorServlet",doctorid);
                //获得所有用户信息

                Log.i(TAG,"读取信息完毕");
            }
        }.start();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_doctor_detail, container, false);

        mDetailInfo = (RecyclerView) v.findViewById(R.id.fragment_detail_recycler_view);
        mDetailInfo.setLayoutManager(new LinearLayoutManager(getActivity()));
        Intro = (TextView)v.findViewById(R.id.words_text);
        Intro.setText(Introduction);

        setupAdapter();
        return v;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 2:
                if (resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("return");
                    System.out.println("返回消息"+returnedData);
                    if(returnedData.equals("cg")){
                        //插入消息到数据库
                        doctorItem target = null;
                        for (doctorItem d:mDoctorItemList){
                            if(d.getDoctorId()==User.Doctorid&&d.getDate().equals(targetDate)){
                                target = d;
                                break;
                            }
                        }
                        doctorItem d = target;
                        new Thread(() -> {
                            String Finalurl="http://192.168.31.157:1206/Server_Pro_war/PersonalServlet";
                            insertUserDoctor(Finalurl,d);
                        }).start();
                    }else{
                        Toast.makeText(getActivity(),"支付失败！",Toast.LENGTH_SHORT);
                    }
                }
                break;
            default:
        }
    }
    public void insertUserDoctor(String url,doctorItem doctorItem){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("userName",User.Username)
                .add("doctorDepartment",doctorItem.getDoctorDepartment())
                .add("doctorId",""+doctorItem.getDoctorId())
                .add("date",doctorItem.getDate())
                .add("doctorName",doctorItem.getDoctorName())
                .add("doctorPrice",""+doctorItem.getDoctorPrice())
                .add("doctorIntro",doctorItem.getDoctorIntro())
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("POST",body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if(response.code()!=200){
                Looper.prepare();
                Log.i(TAG,"请求失败"+response.code());
                Toast.makeText(getActivity(), "登录请求失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public class DetailHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mDate;
        private Button book;
//        private TextView Intro;

        public DetailHolder(@NonNull View itemView) {
            super(itemView);
            mDate = itemView.findViewById(R.id.appointy_date);
            book = itemView.findViewById(R.id.book);
//            Intro = itemView.findViewById(R.id.words_text);
            book.setOnClickListener(this);
        }

        public void bindDate(String date) {
            mDate.setText(date);
//            Intro.setText(mDoctorItem.getDoctorIntro());
        }

        @Override
        public void onClick(View view) {
            //预约
            boolean cf = false;
            for (userDoctorItem u:booked){
                if(u.getDoctorId()==User.Doctorid&&u.getDate().equals(mDate.getText())){
                    cf = true;
                    break;
                }
            }
            if(cf){
                System.out.println("不能预约");
                Toast.makeText(getActivity(), "你已预约该时间！", Toast.LENGTH_SHORT).show();
            }else {
                targetDate = (String) mDate.getText();
                Intent intent = new Intent(getActivity(),MainActivity.class);
                intent.putExtra(PRICE,User.price);
                startActivityForResult(intent,2);
            }
//            System.out.println(booked.size());
        }
    }
    private class DetailAdapter extends RecyclerView.Adapter<DetailHolder> {

        private List<String> Dates = new ArrayList<>();

        public DetailAdapter(List<String> items) {
            Dates = items;
        }

        @NonNull
        @Override
        public DetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.date_item, parent, false);
            return new DetailHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DetailHolder holder, int position) {
            String date = Dates.get(position);
            holder.bindDate(date);
        }

        @Override
        public int getItemCount() {
            return Dates.size();
        }
    }
    private void setupAdapter() {
        if (isAdded()) {
            mDetailInfo.setAdapter(new DetailAdapter(dates));
        }
    }
    void getneedInfo(String url,int doctorId){
//        int count = 0;
        String userUrl = "http://192.168.31.157:1206/Server_Pro_war/userDoctorServlet";
        List<String> dd = new ArrayList<>();
        List<userDoctorItem> userDoctorItemList = new ArrayList<>();
        String allInfo = "";
        String allInfo1 = "";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Request request2 = new Request.Builder()
                .url(userUrl)
                .get()
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            Response response2 = okHttpClient.newCall(request2).execute();
            if(response.code() != 200||response2.code()!=200){
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
//                Set set = new HashSet();
                for (doctorItem d:mDoctorItemList){
                    if(d.getDoctorId() == doctorId){
                        dd.add(d.getDate());
                        System.out.println("日期："+d.getDate());
                    }
                }
                allInfo1 = response2.body().string();
                //Log.i(TAG,allInfo);
                JSONArray jsonArray1 = JSONArray.fromObject(allInfo1);
                for (int i = 0; i < jsonArray1.size(); i++) {
                    userDoctorItem u = new userDoctorItem(
                            jsonArray1.getJSONObject(i).getString("userName"),
                            jsonArray1.getJSONObject(i).getString("doctorDepartment"),
                            jsonArray1.getJSONObject(i).getInt("doctorId"),
                            jsonArray1.getJSONObject(i).getString("date"),
                            jsonArray1.getJSONObject(i).getString("doctorName"),
                            jsonArray1.getJSONObject(i).getInt("doctorPrice"),
                            jsonArray1.getJSONObject(i).getString("doctorIntro"));
                    userDoctorItemList.add(u);
                }
//                Set set = new HashSet();
                for (userDoctorItem u:userDoctorItemList){
                    if(u.getUserName().equals(User.Username)){
//                        System.out.println(u.getDoctorName());
                        booked.add(u);
                    }
                }
                Message message = Message.obtain();
                message.obj = dd;
                message.what = IS_FINISH;
                handler.sendMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Looper.loop();
    }
}
