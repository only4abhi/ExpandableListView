package com.example.abhishek.expandablelistview;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedHashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpandableLVFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpandableLVFragment extends Fragment {

    private LinkedHashMap<String,Brandinfo> brandlist = new LinkedHashMap<String, Brandinfo>();
    private DataAdapter listadapter;
    private ArrayList<Brandinfo> deptlist = new ArrayList<Brandinfo>();
    private ExpandableListView explistview;
    ProgressDialog mProgressDialog;

    //Tag values to read from json
    public String cat_ID = "cat_id";
    public String cat_NM = "cat_name";
    public String subcat_ID = "subcat_id";
    public String subcat_NM = "subcat_name";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ExpandableLVFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExpandableLVFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpandableLVFragment newInstance(String param1, String param2) {
        ExpandableLVFragment fragment = new ExpandableLVFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_expandable_lv, container, false);

        // LoadData();

        explistview=(ExpandableListView)view.findViewById(R.id.explistview);
        listadapter=new DataAdapter(ExpandableLVFragment.this.getActivity(),deptlist);
        explistview.setAdapter(listadapter);
        explistview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Brandinfo headerinfo = deptlist.get(groupPosition);
                Toast.makeText(getContext(),"Header is : "+ headerinfo.getName(),Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        explistview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Brandinfo headerinfo = deptlist.get(groupPosition);
                //Childinfo
                Carinfo detailinfo = headerinfo.getList().get(childPosition);
                //Display It
                Toast.makeText(getContext(),"Clicked On : "+ headerinfo.getName()+"/"+detailinfo.getName(),Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute("http://69.89.31.191/~webtech6/android_api/explstview.php");
            }
        });

        return view;
    }


    //Add Dynamic Image
    class ReadJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        /*
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(getActivity());
            // Set progressdialog title
            mProgressDialog.setTitle("Load data...");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }
        */


        @Override
        protected void onPostExecute(String content) {

            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray =  jsonObject.getJSONArray("explst_dtls");

                for(int i =0;i<jsonArray.length(); i++){
                    //getting json object from current index
                    JSONObject obj = jsonArray.getJSONObject(i);
                    addProduct(obj.getString(cat_NM),obj.getString(subcat_NM));
                    // addProduct("Audi",obj.getString(subcat_NM));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }


            // Close the progressdialog
        }

    }

    private static String readURL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);
            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();
            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }


////////////////////////////////////////////////////////////////////////////////////////



    //Method Expand All

    private void expand_all(){
        int count = listadapter.getGroupCount();
        for(int i=0;i<count;i++){
            explistview.expandGroup(i);
        }
    }

    //Method Collapse All

    private void collapse_all(){
        int count = listadapter.getGroupCount();
        for(int i=0;i<count;i++){
            explistview.collapseGroup(i);
        }
    }

/*
    private void LoadData(){

        addProduct("Audi","Q7");
        addProduct("Audi","Q6");
        addProduct("Audi","Q5");
        addProduct("Audi","Q4");
        addProduct("Audi","Q3");

        addProduct("BMW","B7");
        addProduct("BMW","B6");
        addProduct("BMW","B5");
        addProduct("BMW","B4");
        addProduct("BMW","B3");

        addProduct("Bentley","BT7");
        addProduct("Bentley","BT6");
        addProduct("Bentley","BT5");
        addProduct("Bentley","BT4");
        addProduct("Bentley","BT3");

    }
*/
    //Add product to List Method

    private int addProduct(String Brand,String Car){

        int groupPosition;
        ArrayList<Brandinfo> arrayList;
        Brandinfo headerinfo = brandlist.get(Brand);
        if(headerinfo==null){
            headerinfo = new Brandinfo();
            headerinfo.setName(Brand);
            brandlist.put(Brand,headerinfo);
            deptlist.add(headerinfo);
        }

        ArrayList<Carinfo> carlist = headerinfo.getList();
        int listsize = carlist.size();
        listsize++;

        Carinfo detailinfo = new Carinfo();
        detailinfo.setName(Car);
        detailinfo.setSequence(String.valueOf(listsize));
        carlist.add(detailinfo);
        headerinfo.setList(carlist);

        //groupPostion

        groupPosition = deptlist.indexOf(headerinfo);

        return groupPosition;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Expandable List View");
    }

}
