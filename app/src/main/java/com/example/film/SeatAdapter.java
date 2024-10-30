package com.example.film;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.film.utils.Const;

import java.util.ArrayList;
import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatAdapterVH> {

    private Context context;
    private SeatClickListener listener;
    private List<SeatSelection> list = new ArrayList<>();

    public SeatAdapter(Context context, SeatClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void renewItems(List<SeatSelection> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        int i = 0;
        for (SeatSelection seatSelection : list) {
            if (seatSelection.isSelected()) {
                i++;
            }
        }
        return i;
    }

    public List<Integer> getSelectedSeats() {
        List<Integer> selectedSeats = new ArrayList<>();
        for (SeatSelection seatSelection : list) {
            if (seatSelection.isSelected()) {
                selectedSeats.add(seatSelection.getSeat());
            }
        }
        return selectedSeats;
    }

    @NonNull
    @Override
    public SeatAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seat, null);
        return new SeatAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatAdapterVH holder, int position) {
        int pos = holder.getAdapterPosition();
        SeatSelection selection = list.get(pos);

        if (selection.isBooked()) {
            holder.radioButton.setChecked(false);
            holder.radioButton.setEnabled(false);
        } else {
            holder.radioButton.setChecked(true);
            holder.radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.radioButton.setChecked(true);
                    selection.setSelected(!selection.isSelected());
                    if (selection.isSelected()) {
                        ColorStateList colorStateList = new ColorStateList(
                                new int[][]
                                        {
                                                new int[]{-android.R.attr.state_enabled}, // Disabled
                                                new int[]{android.R.attr.state_enabled}   // Enabled
                                        },
                                new int[]
                                        {
                                                Color.RED, // disabled
                                                Color.RED   // enabled
                                        }
                        );

                        holder.radioButton.setButtonTintList(colorStateList); // set the color tint list
                    } else {
                        ColorStateList colorStateList = new ColorStateList(
                                new int[][]
                                        {
                                                new int[]{-android.R.attr.state_enabled}, // Disabled
                                                new int[]{android.R.attr.state_enabled}   // Enabled
                                        },
                                new int[]
                                        {
                                                Color.BLACK, // disabled
                                                Color.BLACK   // enabled
                                        }
                        );

                        holder.radioButton.setButtonTintList(colorStateList); // set the color tint list
                    }
                    listener.onSeatClick();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class SeatAdapterVH extends RecyclerView.ViewHolder {

        RadioButton radioButton;
        View itemView;

        public SeatAdapterVH(View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.radioButton);
            this.itemView = itemView;
        }
    }

}