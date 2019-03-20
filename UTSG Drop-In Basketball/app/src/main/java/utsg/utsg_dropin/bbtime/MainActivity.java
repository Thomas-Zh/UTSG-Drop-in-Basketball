package utsg.utsg_dropin.bbtime;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private String urlFH="https://recreation.utoronto.ca/Program/GetProgramDetails?courseId=cc534634-c7ed-4345-9049-14101b24abd2&semesterId=84928334-c2f3-4d8c-94ac-418e3add697a";
    private String urlSG="https://recreation.utoronto.ca/Program/GetProgramDetails?courseId=633772a6-3507-4344-9953-2337b796ded4&semesterId=84928334-c2f3-4d8c-94ac-418e3add697a";
    private String urlGR="https://recreation.utoronto.ca/Program/GetProgramDetails?courseId=56e18068-7b50-45df-8854-1921ad1bee80&semesterId=84928334-c2f3-4d8c-94ac-418e3add697a";
    private ArrayList<String> dateListFH = new ArrayList<>();
    private ArrayList<String> timeListFH = new ArrayList<>();
    private ArrayList<String> dateListSG = new ArrayList<>();
    private ArrayList<String> timeListSG = new ArrayList<>();
    private ArrayList<String> dateListGR = new ArrayList<>();
    private ArrayList<String> timeListGR = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager.setOffscreenPageLimit(3);
        TabAdapter adapter = new TabAdapter(this, getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "AC Field House");
        adapter.addFragment(new Tab2Fragment(), "AC Sports Gym");
        adapter.addFragment(new Tab3Fragment(), "Goldring Center");

        viewPager.setAdapter(adapter);
        new BBtime().execute();

        tabLayout.setupWithViewPager(viewPager);
    }
    private class BBtime extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading..."+"\n"+ "Copyright 2007, University of Toronto , all rights reserved.");
            progressDialog.show();
        }
        @Override
        protected Void doInBackground(Void... params){
            try{
                Document documentFH = (Document) Jsoup.connect(urlFH).get();
                Elements elementsFH = documentFH.select(".program-schedule-card-header");
                int elementSizeFH = elementsFH.size();
                if (elementSizeFH ==0) {
                    dateListFH.add("No Events");
                    timeListFH.add(":(");
                }
                for(int i=0;i<elementSizeFH;i++) {
                    Elements elementsDate = documentFH.select(".program-schedule-card-header").select(".pull-left").eq(i);
                    String date = elementsDate.text();
                    dateListFH.add(date);
                }
                for(int i=0; i<2*elementSizeFH;i=i+2){
                    Elements elementsTime = documentFH.select(".program-schedule-card-header").select("small").eq(i);
                    String rawTime=elementsTime.text();
                    Pattern pattern = Pattern.compile("(\\d{1,2}:\\d{2} [AP]M to \\d{1,2}:\\d{2} [AP]M)");
                    Matcher matcher = pattern.matcher(rawTime);
                    if (matcher.find()){
                        String time = matcher.group(0);
                        timeListFH.add(time);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                Document documentSG = (Document) Jsoup.connect(urlSG).get();
                Elements elementsSG = documentSG.select(".program-schedule-card-header");
                int elementSizeSG = elementsSG.size();
                if (elementSizeSG ==0){
                    dateListSG.add("No Events");
                    timeListSG.add(":(");
                }
                 for(int i=0;i<elementSizeSG;i++) {
                    Elements elementsDate = documentSG.select(".program-schedule-card-header").select(".pull-left").eq(i);
                    String date = elementsDate.text();
                    dateListSG.add(date);
                }
                for(int i=0; i<2*elementSizeSG;i=i+2) {
                    Elements elementsTime = documentSG.select(".program-schedule-card-header").select("small").eq(i);
                    String rawTime = elementsTime.text();
                    Pattern pattern = Pattern.compile("(\\d{1,2}:\\d{2} [AP]M to \\d{1,2}:\\d{2} [AP]M)");
                    Matcher matcher = pattern.matcher(rawTime);
                    if (matcher.find()) {
                        String time = matcher.group(0);
                        timeListSG.add(time);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                Document documentGR = (Document) Jsoup.connect(urlGR).get();
                Elements elementsGR = documentGR.select(".program-schedule-card-header");
                int elementSizeGR = elementsGR.size();
                if (elementSizeGR ==0){
                    dateListGR.add("No Events");
                    timeListGR.add(":(");
                }
                for(int i=0;i<elementSizeGR;i++) {
                    Elements elementsDate = documentGR.select(".program-schedule-card-header").select(".pull-left").eq(i);
                    String date = elementsDate.text();
                    dateListGR.add(date);
                }
                for(int i=0; i<2*elementSizeGR;i=i+2){
                    Elements elementsTime = documentGR.select(".program-schedule-card-header").select("small").eq(i);
                    String rawTime=elementsTime.text();
                    Pattern pattern = Pattern.compile("(\\d{1,2}:\\d{2} [AP]M to \\d{1,2}:\\d{2} [AP]M)");
                    Matcher matcher = pattern.matcher(rawTime);
                    if (matcher.find()){
                        String time = matcher.group(0);
                        timeListGR.add(time);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void param){
            RecyclerView recyclerViewFH = (RecyclerView)findViewById(R.id.RecyclerFH);
            RecyclerView recyclerViewSG = (RecyclerView)findViewById(R.id.RecyclerSG);
            RecyclerView recyclerViewGR = (RecyclerView)findViewById(R.id.RecyclerGR);
            recyclerViewFH.setLayoutManager (new LinearLayoutManager(getApplicationContext()));
            recyclerViewSG.setLayoutManager (new LinearLayoutManager(getApplicationContext()));
            recyclerViewGR.setLayoutManager (new LinearLayoutManager(getApplicationContext()));
            DataAdapter dataAdapterFH = new DataAdapter(MainActivity.this,dateListFH, timeListFH, "FH");
            DataAdapter dataAdapterSG = new DataAdapter(MainActivity.this,dateListSG, timeListSG, "SG");
            DataAdapter dataAdapterGR = new DataAdapter(MainActivity.this,dateListGR, timeListGR, "GR");
            recyclerViewFH.setAdapter(dataAdapterFH);
            recyclerViewSG.setAdapter(dataAdapterSG);
            recyclerViewGR.setAdapter(dataAdapterGR);
            progressDialog.dismiss();
        }
    }
}