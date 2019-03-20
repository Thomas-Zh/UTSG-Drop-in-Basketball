package utsg.utsg_dropin.bbtime;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {
    private String name;
    private ArrayList<String> dateList = new ArrayList<>();
    private ArrayList<String> timeList = new ArrayList<>();
    private Activity activity;

    public DataAdapter(MainActivity activity, ArrayList<String> dateList, ArrayList<String> timeList,String name) {
        this.name=name;
        this.activity = activity;
        this.dateList = dateList;
        this.timeList = timeList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView dateView,timeView;
        public MyViewHolder(View view) {
            super(view);
            if (name=="FH"){
                dateView = (TextView) view.findViewById(R.id.dateFH);
                timeView = (TextView) view.findViewById(R.id.timeFH);
            }
            else if (name=="SG"){
                dateView = (TextView) view.findViewById(R.id.dateSG);
                timeView = (TextView) view.findViewById(R.id.timeSG);
            }
            else if (name=="GR"){
                dateView = (TextView) view.findViewById(R.id.dateGR);
                timeView = (TextView) view.findViewById(R.id.timeGR);
            }
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (name=="FH"){
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ac_fh_data, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(itemView);
            return viewHolder;
        }
        else if (name=="SG"){
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ac_sg_data, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(itemView);
            return viewHolder;
        }
        else if (name=="GR"){
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ac_gr_data, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(itemView);
            return viewHolder;
        }
        else return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.dateView.setText(dateList.get(position));
            holder.timeView.setText(timeList.get(position));
    }
    @Override
    public int getItemCount() {
        return dateList.size();
    }
}
