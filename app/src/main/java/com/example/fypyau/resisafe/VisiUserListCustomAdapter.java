package com.example.fypyau.resisafe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Nick Yau on 2/18/2017.
 */

public class VisiUserListCustomAdapter extends RecyclerView.Adapter<VisiUserListCustomAdapter.ViewHolder> {


    private Context context;
    private List<VisitorUser> user_data;

    public VisiUserListCustomAdapter(Context context, List<VisitorUser> user_data) {
        this.context = context;
        this.user_data = user_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_row_user,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(user_data.get(position).getName());
        holder.lastcheckindate.setText("Last Checked In: " + user_data.get(position).getLastcheckindate());
        holder.carplatenum.setText(user_data.get(position).getCarplatenum());
        String imagelink = user_data.get(position).getProfileimage();
        if(imagelink.isEmpty())
            holder.imageView.setImageResource(R.drawable.unknownprofile);
        else
        Glide.with(context).load(imagelink).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return user_data.size();
    }

    public class ViewHolder  extends  RecyclerView.ViewHolder{

        public TextView name;
        public TextView lastcheckindate;
        public TextView carplatenum;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            lastcheckindate = (TextView) itemView.findViewById(R.id.lastcheckindate);
            carplatenum = (TextView) itemView.findViewById(R.id.carplatenum);
            imageView = (ImageView) itemView.findViewById(R.id.userimage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VisitorUser transfer = user_data.get(getAdapterPosition());
                    Intent i = new Intent(context,VisiUserProfileActivity.class);
                    i.putExtra("userdata",transfer);
                    context.startActivity(i);
                }
            });
            Typeface custom_font = Typeface.createFromAsset(name.getContext().getAssets(),  "fonts/Bariol.ttf");
            name.setTypeface(custom_font);
            carplatenum.setTypeface(custom_font);
            lastcheckindate.setTypeface(custom_font);
        }
    }

}
