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
 * Created by Nick Yau on 3/5/2017.
 */

public class ResiUserListCustomAdapter extends RecyclerView.Adapter<ResiUserListCustomAdapter.ViewHolder> {


private Context context;
private List<ResidentUser> user_data;

public ResiUserListCustomAdapter(Context context, List<ResidentUser> user_data) {
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
        holder.lastcheckindate.setText("Last Created Event: " + user_data.get(position).getLasteventdate());
        holder.carplatenum.setText("");
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
                ResidentUser transfer = user_data.get(getAdapterPosition());
                Intent i = new Intent(context,ResiUserProfileActivity.class);
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
