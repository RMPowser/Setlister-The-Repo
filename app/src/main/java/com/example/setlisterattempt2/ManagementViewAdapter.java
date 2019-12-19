package com.example.setlisterattempt2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ManagementViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	public static final String SETLIST_KEY = "setlist";
	
	private ArrayList<SetlistHeader> headerList;
	private final Context context;
	
	public ManagementViewAdapter(Context context, ArrayList<SetlistHeader> headerList) {
		this.context = context;
		this.headerList = headerList;
	}
	
	public ArrayList<SetlistHeader> getHeaderList() {
		return headerList;
	}
	
	public void setHeaderList(ArrayList<SetlistHeader> headerList) {
		this.headerList = headerList;
	}
	
	public void Refresh() {
		notifyDataSetChanged();
	}
	
	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		
		View view;
		view = LayoutInflater.from(context).inflate(R.layout.layout_setlist_header, parent, false);
		return new HeaderViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		final int index = position;
		
		((HeaderViewHolder) holder).SetHeaderDetails(headerList.get(index));
		((HeaderViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String fileName = headerList.get(index).getSetlistFileName();
				String filePath = context.getFilesDir().getPath() + "/" + fileName;
				Intent intent = new Intent(context, SetlistViewActivity.class);
				Setlist setlist = SetlistViewActivity.LoadSetlist(filePath);
				intent.putExtra(SETLIST_KEY, setlist);
				// the above function works because the Setlist class and all children of the SetlistEntity
				// class were made parcelable
				context.startActivity(intent);
			}
		});
		((HeaderViewHolder) holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				String filePath = headerList.get(index).getSetlistFileName();
				((ManagementViewActivity) context).OpenDeleteFileDialog(filePath);
				return true;
			}
		});
	}
	
	@Override
	public int getItemCount() {
		return headerList.size();
	}
}
