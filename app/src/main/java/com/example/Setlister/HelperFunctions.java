package com.example.Setlister;

import android.content.Context;
import android.widget.Toast;

import com.example.Setlister.SetlistObjects.ButtonAddSet;
import com.example.Setlister.SetlistObjects.ButtonAddSongEntry;
import com.example.Setlister.SetlistObjects.Set;
import com.example.Setlister.SetlistObjects.Setlist;
import com.example.Setlister.SetlistObjects.SetlistEntity;
import com.example.Setlister.SetlistObjects.SetlistHeader;
import com.example.Setlister.SetlistObjects.SongEntry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;

public abstract class HelperFunctions {
	public static RuntimeTypeAdapterFactory CreateRuntimeTypeAdapterFactory() {
		return RuntimeTypeAdapterFactory.of(SetlistEntity.class, "type")
				.registerSubtype(Set.class, "set")
				.registerSubtype(SongEntry.class, "song")
				.registerSubtype(SetlistHeader.class, "header")
				.registerSubtype(ButtonAddSet.class, "buttonAddSet")
				.registerSubtype(ButtonAddSongEntry.class, "buttonAddSong");
	}
	
	public static Setlist LoadSetlist(String filepath) {
		// file name is title of setlist
		FileReader reader = null;
		Gson gson;
		RuntimeTypeAdapterFactory runtimeTypeAdapterFactory =
				HelperFunctions.CreateRuntimeTypeAdapterFactory();
		
		Setlist setlist = null;
		
		try {
			File file = new File(filepath);
			reader = new FileReader(file);
			gson = new GsonBuilder().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();
			Type entityType = new TypeToken<Setlist>(){}.getType();
			setlist = gson.fromJson(reader, entityType);
		} catch (JsonSyntaxException | IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return setlist;
	}
	
	public static boolean SaveSetlist(Setlist setlist, Context context) {
		String name = ((SetlistHeader) setlist.getSongs().get(0)).getTitleOrLocation();
		String date = ((SetlistHeader) setlist.getSongs().get(0)).getDate();
		String time = ((SetlistHeader) setlist.getSongs().get(0)).getTime();
		
		// sanitize the strings
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if (c == '/' || c == '\\' || c == '|' || c == ':' || c == '*' || c == '?' || c == '\"' || c == '<' || c == '>') {
				name = name.replace(c,'-');
			}
		}
		
		for (int i = 0; i < date.length(); i++) {
			char c = date.charAt(i);
			if (c == '/' || c == '\\' || c == '|' || c == ':' || c == '*' || c == '?' || c == '\"' || c == '<' || c == '>') {
				date = date.replace(c,'-');
			}
		}
		
		for (int i = 0; i < time.length(); i++) {
			char c = time.charAt(i);
			if (c == '/' || c == '\\' || c == '|' || c == ':' || c == '*' || c == '?' || c == '\"' || c == '<' || c == '>') {
				time = time.replace(c,'-');
			}
		}
		
		// file name is title of setlist
		String FILE_NAME =
				context.getFilesDir().getPath() + "/" + name + "_" + date + "_" + time + ".setlist";
		
		RuntimeTypeAdapterFactory runtimeTypeAdapterFactory =
				HelperFunctions.CreateRuntimeTypeAdapterFactory();
		FileOutputStream fileOutputStream = null;
		Gson gson;
		
		boolean result;
		
		try {
			gson = new GsonBuilder().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();
			String json = gson.toJson(setlist);
			File file = new File(FILE_NAME);
			fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(json.getBytes());
			Toast.makeText(context, "Setlist saved.", Toast.LENGTH_LONG).show();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
					result = true;
				} catch (IOException e) {
					e.printStackTrace();
					result = false;
				}
			}
		}
		return result;
	}
	
	
}
