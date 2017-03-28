package com.example.fypyau.resisafe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nick Yau on 2/28/2017.
 */

public class UserVisitingListCustomAdapter extends RecyclerView.Adapter<UserVisitingListCustomAdapter.ViewHolder>  {

    private Context context;
    private List<VisitingData> visit_data;

    public UserVisitingListCustomAdapter(Context context, List<VisitingData> visit_data) {
        this.context = context;
        this.visit_data = visit_data;
    }

    @Override
    public UserVisitingListCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_uservisiting_row,parent,false);

        return new UserVisitingListCustomAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserVisitingListCustomAdapter.ViewHolder holder, int position) {
        holder.visitingtype.setText(visit_data.get(position).getVisitingtype());
        holder.dateandtime.setText(visit_data.get(position).getVisitingdate() + " ," + visit_data.get(position).getVisitingtime());
        if (visit_data.get(position).getVisitingtype().equals("Check in")){
            holder.visitingtype.setTextColor(ContextCompat.getColor(context, R.color.checkingreen));
        } else
            holder.visitingtype.setTextColor(ContextCompat.getColor(context, R.color.checkoutred));
    }

    @Override
    public int getItemCount() {
        return visit_data.size();
    }

    public class ViewHolder  extends  RecyclerView.ViewHolder{

        public TextView visitingtype;
        public TextView dateandtime;

        public ViewHolder(View itemView) {
            super(itemView);
            visitingtype = (TextView) itemView.findViewById(R.id.tvVisitingtype);
            dateandtime = (TextView) itemView.findViewById(R.id.tvDateandTime);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VisitingData transfer = visit_data.get(getAdapterPosition());
                    Intent i = new Intent(context,ResiVisitingDataDetailsActivity.class);
                    i.putExtra("visitdata",transfer);
                    context.startActivity(i);
                }
            });


        }
    }
}
