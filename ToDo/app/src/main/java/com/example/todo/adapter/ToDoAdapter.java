package com.example.todo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.todo.R;
import com.example.todo.model.ToDo;


import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.CustomViewHolder> {

    private List<ToDo> dataList;
    private Context context;

    public ToDoAdapter(Context context, List<ToDo> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtTitle;
        TextView txtCompletion;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.tvTitle);
            txtCompletion = mView.findViewById(R.id.tvCompletion);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.txtTitle.setText(dataList.get(position).getTitle());
        holder.txtCompletion.setText(dataList.get(position).getCompletion());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
