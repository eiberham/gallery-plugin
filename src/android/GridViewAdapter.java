package com.example.gallerypictureactivity;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GridViewAdapter extends BaseAdapter{
	private Activity                activity;
    private ArrayList<String>       filepath;
    private static LayoutInflater   inflater    = null;
    Bitmap                          bmp         = null;

    public GridViewAdapter (Activity a,ArrayList<String> fpath)
    {
        activity = a;
        filepath = fpath;
        inflater = (LayoutInflater)activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    static class ViewHolder {
		  protected ImageView imagen;
	}

    public int getCount ()
    {
        return filepath.size();
    }

    public Object getItem (int position)
    {
        return position;
    }

    public long getItemId (int position)
    {
        return position;
    }

    public View getView (int position, View convertView, ViewGroup parent)
    {
    	ViewHolder viewholder;
    	
        /*if (convertView == null)
            convertView = inflater.inflate(R.layout.gridview_item, null);*/
        if (convertView == null)
            convertView = inflater.inflate(activity.getResources().getIdentifier("gridview_item", "layout", activity.getPackageName()), null);
        
        viewholder = new ViewHolder();
		viewholder.imagen = (ImageView)convertView.findViewById(activity.getResources().getIdentifier("image", "id", activity.getPackageName()));
				
        int targetWidth = 100;
        int targetHeight = 100;
        BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
        bmpOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filepath.get(position), bmpOptions);
        int currHeight = bmpOptions.outHeight;
        int currWidth = bmpOptions.outWidth;
        int sampleSize = 1;
        if (currHeight > targetHeight || currWidth > targetWidth)
        {
            if (currWidth > currHeight)
                sampleSize = Math.round((float)currHeight
                        / (float)targetHeight);
            else
                sampleSize = Math.round((float)currWidth
                        / (float)targetWidth);
        }
        bmpOptions.inSampleSize = sampleSize;
        bmpOptions.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeFile(filepath.get(position), bmpOptions);
        viewholder.imagen.setImageBitmap(bmp);
        viewholder.imagen.setScaleType(ImageView.ScaleType.FIT_XY);
        bmp = null;
       
        return convertView;
    }

}
