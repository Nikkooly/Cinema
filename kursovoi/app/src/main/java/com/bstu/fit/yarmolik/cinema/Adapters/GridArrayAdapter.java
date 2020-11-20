package com.bstu.fit.yarmolik.cinema.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bstu.fit.yarmolik.cinema.Fragments.SelectTickets;
import com.bstu.fit.yarmolik.cinema.R;

import java.util.ArrayList;
import java.util.List;

public class GridArrayAdapter extends BaseAdapter {
    public static int counter=0;
    private ArrayList<Double> price;
    private ArrayList<Boolean> status;
    private ArrayList<Integer> places;
    private List<Integer> selectedPositions;
    private Context mContext;



    public GridArrayAdapter(Context mContext) {
        this.mContext = mContext;
        this.selectedPositions = new ArrayList<>();
    }
   /* public void clear(){
        status.clear();
        price.clear();
        id.clear();
        places.clear();
    }*/
    public void setInfo(ArrayList<Boolean> statusS, ArrayList<Integer> placesS, Integer count) {
        this.status = statusS;
        this.places=placesS;
        counter=count;
    }

    @Override
    public int getCount() {
        return places.size();
    }
    @Override
    public Object getItem(int position) {
        return places.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;
        if(convertView==null) {
            button=new Button(mContext);
            button.setLayoutParams(new GridView.LayoutParams(70,70));
            button.setPadding(4,4,4,4);
            button.setTextSize(8);
            button.setText(places.get(position).toString());

            if (status.get(position)) {
                button.setBackgroundColor(Color.LTGRAY);
                button.setEnabled(false);
            } else if (!status.get(position)){
                button.setBackgroundColor(Color.rgb(53, 172, 72));
            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   int pos = Integer.parseInt(((Button) view).getText().toString())-1;
                    int selectedIndex = selectedPositions.indexOf(pos);
                        if (!status.get(pos) && selectedIndex > -1) {
                            counter--;
                            selectedPositions.remove(selectedIndex);
                            ((Button) view).setBackgroundColor(Color.rgb(53, 172, 72));
                        } else if (!status.get(pos) && selectedIndex == -1) {
                            if(counter<=6) {
                            selectedPositions.add(pos);
                            counter++;
                            ((Button) view).setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.titleColor));
                                //selectedIndex=0;
                            }
                            else{
                        Toast.makeText(mContext, "Нельзя купить больше 7 билетов одноврменно", Toast.LENGTH_LONG).show();
                            }
                        }

                }
            });
        }
        else {
            button = (Button) convertView;
        }
        return button;
    }
    public List<Integer> getSelectedPositions() {
        return selectedPositions;
    }
}
