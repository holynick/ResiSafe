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
 * Created by Nick Yau on 3/10/2017.
 */

public class VisiQRcodeListCustomAdapter extends RecyclerView.Adapter<VisiQRcodeListCustomAdapter.ViewHolder> {

    private Context context;
    private List<Qrcode> qrcode_data;

    public VisiQRcodeListCustomAdapter(Context context, List<Qrcode> qrcode_data) {
        this.context = context;
        this.qrcode_data = qrcode_data;
    }

    @Override
    public VisiQRcodeListCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_qrcode,parent,false);

        return new VisiQRcodeListCustomAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VisiQRcodeListCustomAdapter.ViewHolder holder, int position) {
        holder.tvEventname.setText(qrcode_data.get(position).getEventname());
        holder.tvEventtime.setText("Time: " + qrcode_data.get(position).getEventstartdate());
        holder.tvEventdate.setText("Date: " + qrcode_data.get(position).getEventstarttime());
        Qrcode m = qrcode_data.get(position);
        if (m.getEventlocation().equals("Swimming Pool")){
            holder.imageView.setImageResource(R.drawable.eventbackgroundpool);
        } else if (m.getEventlocation().equals("Gym")){
            holder.imageView.setImageResource(R.drawable.eventbackgroundgym);
        } else if (m.getEventlocation().equals("Badminton Court")){
            holder.imageView.setImageResource(R.drawable.eventbackgroundbadcourt);
        } else if (m.getEventlocation().equals("Public Park")){
            holder.imageView.setImageResource(R.drawable.eventbackgroundpark);
        }
        else
            holder.imageView.setImageResource(R.drawable.eventbackgroundhouse);

    }

    @Override
    public int getItemCount() {
        return qrcode_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvEventname, tvEventdate, tvEventtime;
        ImageView imageView;

        public ViewHolder(View itemView){
            super(itemView);
            tvEventname = (TextView) itemView.findViewById(R.id.tvEventname);
            tvEventdate = (TextView) itemView.findViewById(R.id.tvEventdate);
            tvEventtime = (TextView) itemView.findViewById(R.id.tvEventtime);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Qrcode transfer = qrcode_data.get(getAdapterPosition());
                    Intent i = new Intent(context,VisiQRcodeDetailsActivity.class);
                    i.putExtra("qrcodedata",transfer);
                    context.startActivity(i);
                }
            });
            Typeface custom_font = Typeface.createFromAsset(tvEventname.getContext().getAssets(),  "fonts/Bariol.ttf");


        }
    }
}


