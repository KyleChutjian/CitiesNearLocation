package com.example.citiesnearlocation;

import android.content.Context;
import android.text.Layout;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<String> cityArrayList;

    Adapter(Context context, ArrayList<String> cities){
        layoutInflater = LayoutInflater.from(context);
        this.cityArrayList = cities;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        String city = cityArrayList.get(i);
        holder.cityName.setText(city);

    }

    @Override
    public int getItemCount() {
        return cityArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView cityName;

        public ViewHolder(View cardView) {
            super(cardView);
            cityName = cardView.findViewById(R.id.cardText);


        }
    }
}
