/* This specific ViewHolder is used by both ManagementViewAdapter and SetlistViewAdapter. That's
why it's defined in its own file. All other ViewHolders are defined within SetlistViewAdapter
because they are only used by that class.*/
package com.example.setlisterattempt2;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


class HeaderViewHolder extends RecyclerView.ViewHolder {
	TextView headerTitle;
	TextView headerDate;
	TextView headerTime;
	
	public HeaderViewHolder(View itemView) {
		super(itemView);
		headerTitle = itemView.findViewById(R.id.setlistTitleText);
		headerDate = itemView.findViewById(R.id.setlistDateTextView);
		headerTime = itemView.findViewById(R.id.setlistTimeTextView);
	}
	
	public void SetHeaderDetails(@NonNull SetlistHeader header) {
		headerTitle.setText(header.getTitleOrLocation());
		headerDate.setText(header.getDate());
		headerTime.setText(header.getTime());
	}
}
