package com.example.mobile_application.trips;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_application.R;

import java.util.ArrayList;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private Activity activity;
    public List<Trip> trips = new ArrayList<>();
    private List<Trip> tripSearch;

    public TripAdapter(Activity activity, Context context, List<Trip> trips){
        this.activity = activity;
        this.context = context;
        this.trips = trips;
        this.tripSearch = trips;
    }

    @NonNull
    @Override
    public TripAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_trips, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Trip trip = trips.get(position);
        holder.tripId.setText(String.valueOf(trip.getId()));
        holder.nameTrip.setText(String.valueOf(trip.getName()));
        holder.destinationTrip.setText(String.valueOf(trip.getDestination()));
        holder.dateTrip.setText(String.valueOf(trip.getDate()));
        holder.requiredTrip.setText(String.valueOf(trip.getRequire()));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditTripActivity.class);
                intent.putExtra("trip_id", String.valueOf(trip.getId()));
                intent.putExtra("name", String.valueOf(trip.getName()));
                intent.putExtra("destination", String.valueOf(trip.getDestination()));
                intent.putExtra("date", String.valueOf(trip.getDate()));
                intent.putExtra("require", String.valueOf(trip.getRequire()));
                intent.putExtra("des", String.valueOf(trip.getDes()));
                activity.startActivityForResult(intent,1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return trips.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tripId, nameTrip, destinationTrip,dateTrip,requiredTrip,desTrip;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tripId = itemView.findViewById(R.id.tripId);
            nameTrip = itemView.findViewById(R.id.nameTrip);
            destinationTrip = itemView.findViewById(R.id.destinationTrip);
            dateTrip = itemView.findViewById(R.id.dateTrip);
            requiredTrip = itemView.findViewById(R.id.requiredTrip);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String stringSearch = charSequence.toString();

                if (stringSearch.isEmpty()) {
                    trips = tripSearch;
                } else {
                    List<Trip> list = new ArrayList<>();
                    for (Trip trip : tripSearch) {
                        if (trip.getName().toLowerCase().contains(stringSearch.toLowerCase())) {
                            list.add(trip);
                        }
                    }
                    trips = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = trips;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                trips = (List<Trip>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
