package com.example.film;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.film.database.ItemClickListener;
import com.example.film.database.Movie;
import com.example.film.utils.Const;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeAdapterVH> {

    private Context context;
    private ItemTimeClickListener listener;
    private List<TimeSelection> list = new ArrayList<>();

    public TimeAdapter(Context context, ItemTimeClickListener listener) {
        list.addAll(Const.availableTime);
        this.context = context;
        this.listener = listener;
    }

    public void renewItems(List<TimeSelection> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TimeAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time, null);
        return new TimeAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeAdapterVH holder, int position) {
        int pos = holder.getAdapterPosition();
        TimeSelection selection = list.get(pos);
//        Log.e("convert", "Bitmap: " + convert(bitmap));

        holder.time.setText(selection.getTime());
        if (selection.isSelected()) {
            holder.cardView.setCardBackgroundColor(context.getColor(R.color.red));
        } else {
            holder.cardView.setCardBackgroundColor(context.getColor(R.color.grey));
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected(false);
                list.get(pos).setSelected(true);
                notifyDataSetChanged();
                listener.onTimeCLick(selection.getTime());
            }
        });
    }

    @Override
    public int getItemCount() {
        return Const.availableTime.size();
    }

    public void clearSelected(boolean notify) {
        for (TimeSelection timeSelection : list) {
            timeSelection.setSelected(false);
        }
        if (notify){
            notifyDataSetChanged();
        }
    }

    public static class TimeAdapterVH extends RecyclerView.ViewHolder {

        View itemView;
        CardView cardView;
        TextView time;

        public TimeAdapterVH(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card);
            time = itemView.findViewById(R.id.time);
            this.itemView = itemView;
        }
    }

}