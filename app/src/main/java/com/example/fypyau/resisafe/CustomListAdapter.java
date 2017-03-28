package com.example.fypyau.resisafe;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by Nick Yau on 2/9/2017.
 */

public class CustomListAdapter extends BaseAdapter implements Filterable{
    private Activity activity;
    private LayoutInflater inflater;
    private List<Event> eventItems;
    private List<Event> OeventItems;


    public CustomListAdapter(Activity activity, List<Event> eventItems) {
        this.activity = activity;
        this.eventItems = eventItems;
        this.OeventItems = eventItems;
    }


    public int getCount() {
        return eventItems.size();
    }


    public Object getItem(int location) {
        return eventItems.get(location);
    }


    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.event_list_row, null);

        TextView eventname = (TextView) convertView.findViewById(R.id.title);
        TextView eventlocation = (TextView) convertView.findViewById(R.id.rating);
        TextView eventdescription = (TextView) convertView.findViewById(R.id.genre);
        TextView eventdate = (TextView) convertView.findViewById(R.id.releaseYear);
        ImageView arrowimage = (ImageView) convertView.findViewById(R.id.arrowimage);
        RelativeLayout layout = (RelativeLayout) convertView.findViewById(R.id.eventlist);


        Typeface custom_font = Typeface.createFromAsset(eventname.getContext().getAssets(),  "fonts/Bariol.ttf");
        eventname.setTypeface(custom_font);
        eventlocation.setTypeface(custom_font);
        eventdescription.setTypeface(custom_font);


        Event m = eventItems.get(position);

        if (m.getEventLocation().equals("Swimming Pool")){
            layout.setBackgroundResource(R.drawable.eventbackgroundpool);
        } else if (m.getEventLocation().equals("Gym")){
            layout.setBackgroundResource(R.drawable.eventbackgroundgym);
        } else if (m.getEventLocation().equals("Badminton Court")){
            layout.setBackgroundResource(R.drawable.eventbackgroundbadcourt);
        } else if (m.getEventLocation().equals("Public Park")){
            layout.setBackgroundResource(R.drawable.eventbackgroundpark);
        }
        else
            layout.setBackgroundResource(R.drawable.eventbackgroundhouse);

        eventname.setText(m.getEventName());

        eventlocation.setText(String.valueOf(m.getEventLocation()));

        eventdescription.setText(m.getEventDescription());

        eventdate.setText(m.getEventstartdate() + ", " + m.getEventstarttime());

        arrowimage.setImageResource(R.drawable.ic_arrow);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                eventItems = (ArrayList<Event>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<Event> FilteredArrList = new ArrayList<Event>();

                if (OeventItems == null) {
                    OeventItems = new ArrayList<Event>(eventItems); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = OeventItems.size();
                    results.values = OeventItems;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < OeventItems.size(); i++) {
                        String data = OeventItems.get(i).getEventName();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new Event(OeventItems.get(i).getEventName(),OeventItems.get(i).getEventDate(),OeventItems.get(i).getEventLocation(),
                                    OeventItems.get(i).getEventDescription(),OeventItems.get(i).getEventID(),OeventItems.get(i).getEventstartdate(),OeventItems.get(i).getEventenddate(),OeventItems.get(i).getEventstarttime(),OeventItems.get(i).getEventendtime(), OeventItems.get(i).getRuserID()));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }
}


