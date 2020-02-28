package com.example.busroute;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.busroute.R;
import com.example.busroute.Result_List;

import java.util.List;


public class Result_Adapter extends RecyclerView.Adapter<Result_Adapter.ViewHolder>{
    private Result_List[] listdata;

    // RecyclerView recyclerView;
    public Result_Adapter(List<Result_List> listdata) {
        this.listdata = listdata.toArray(new Result_List[0]);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_bus, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Result_List myListData = listdata[position];
        holder.text_route.setText(listdata[position].getRoute_no());
        holder.text_bus.setText(listdata[position].getBus_no());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click on item: "+myListData.getRoute_no(),Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text_route;
        public TextView text_bus;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.text_route = (TextView) itemView.findViewById(R.id.text_route);
            this.text_bus = (TextView) itemView.findViewById(R.id.textView);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }
}