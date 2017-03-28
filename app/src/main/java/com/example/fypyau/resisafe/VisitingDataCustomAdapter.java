package com.example.fypyau.resisafe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Nick Yau on 2/25/2017.
 */

public class VisitingDataCustomAdapter extends RecyclerView.Adapter<VisitingDataCustomAdapter.ViewHolder> {

    private Context context;
    private List<VisitingData> visit_data;

    public VisitingDataCustomAdapter(Context context, List<VisitingData> visit_data) {
        this.context = context;
        this.visit_data = visit_data;
    }

    @Override
    public VisitingDataCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_row_visitingdata,parent,false);

        return new VisitingDataCustomAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VisitingDataCustomAdapter.ViewHolder holder, int position) {
        holder.tvname.setText(visit_data.get(position).getVusername());
        holder.tvvisitingtype.setText(visit_data.get(position).getVisitingtype());
        holder.tvtime.setText(visit_data.get(position).getVisitingtime());
        holder.tvdate.setText(visit_data.get(position).getVisitingdate());
        holder.tvcarplatenum.setText(visit_data.get(position).getVusercarplatenum());
        if (visit_data.get(position).getVisitingtype().equals("Check In")){
            holder.tvvisitingtype.setTextColor(ContextCompat.getColor(context, R.color.checkingreen));
        } else
            holder.tvvisitingtype.setTextColor(ContextCompat.getColor(context, R.color.checkoutred));

    }

    @Override
    public int getItemCount() {
        return visit_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvname, tvcarplatenum, tvdate, tvvisitingtype, tvtime;

        public ViewHolder(View itemView){
            super(itemView);
            tvname = (TextView) itemView.findViewById(R.id.tvVisitorname);
            tvcarplatenum = (TextView) itemView.findViewById(R.id.tvCarplatenum);
            tvdate = (TextView) itemView.findViewById(R.id.tvDate);
            tvvisitingtype = (TextView) itemView.findViewById(R.id.tvVisitingtype);
            tvtime = (TextView) itemView.findViewById(R.id.tvTime);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VisitingData transfer = visit_data.get(getAdapterPosition());
                    Intent i = new Intent(context,ResiVisitingDataDetailsActivity.class);
                    i.putExtra("visitdata",transfer);
                    context.startActivity(i);
                }
            });
            Typeface custom_font = Typeface.createFromAsset(tvname.getContext().getAssets(),  "fonts/Bariol.ttf");
            tvname.setTypeface(custom_font);
            tvcarplatenum.setTypeface(custom_font);
            tvdate.setTypeface(custom_font);
            tvtime.setTypeface(custom_font);
            tvvisitingtype.setTypeface(custom_font);

        }
    }
}
