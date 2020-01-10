package com.example.Setlister.ViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Setlister.HelperFunctions;
import com.example.Setlister.Activities.ManagementViewActivity;
import com.example.Setlister.R;
import com.example.Setlister.SetlistObjects.Setlist;
import com.example.Setlister.SetlistObjects.SetlistHeader;
import com.example.Setlister.Activities.SetlistViewActivity;

import java.util.ArrayList;

public class ManagementViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	public static final String SETLIST_KEY = "setlist";
	
	private ArrayList<SetlistHeader> headerList;
	private final Context context;
	
	public ManagementViewAdapter(Context context, ArrayList<SetlistHeader> headerList) {
		this.context = context;
		setHeaderList(headerList);
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
		return new ViewHolders.HeaderViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		final int index = position;
		
		((ViewHolders.HeaderViewHolder) holder).SetHeaderDetails(headerList.get(index));
		((ViewHolders.HeaderViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String fileName = headerList.get(index).getSetlistFileName();
				String filePath = context.getFilesDir().getPath() + "/" + fileName;
				Intent intent = new Intent(context, SetlistViewActivity.class);
				Setlist setlist = HelperFunctions.LoadSetlist(filePath);
				intent.putExtra(SETLIST_KEY, setlist);
				// the above function works because the Setlist class and all children of the SetlistEntity
				// class were made parcelable
				context.startActivity(intent);
			}
		});
		((ViewHolders.HeaderViewHolder) holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				String fileName = headerList.get(index).getSetlistFileName();
				((ManagementViewActivity) context).OpenDeleteFileDialog(fileName);
				return true;
			}
		});
	}
	
	@Override
	public long getItemId(int position) {
		if (position < headerList.size()){
			return headerList.get(position).getIdentifier();
		}
		return RecyclerView.NO_ID; //return -1 by default
	}
	
	@Override
	public int getItemCount() {
		return getHeaderList().size();
	}
}
