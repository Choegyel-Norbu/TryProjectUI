package updatedproject.com.tryprojectui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProfileRecyclerAdapter extends RecyclerView.Adapter<ProfileRecyclerAdapter.ViewHolder>{

    final String TAG = "profileTAG";

    Context context;
    ArrayList<RecyclerSetter> list;

    public ProfileRecyclerAdapter(Context context, ArrayList<RecyclerSetter> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.author.setText(list.get(position).getAuthor());
        holder.date.setText(list.get(position).getDate());
        holder.edit.setImageResource(R.drawable.ic_edit);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(ArrayList<RecyclerSetter> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, author, date;
        ImageView edit;
        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            date = itemView.findViewById(R.id.date);
            edit = itemView.findViewById(R.id.edit);
        }
    }
}
