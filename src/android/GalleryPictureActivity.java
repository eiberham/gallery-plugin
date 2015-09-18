package com.example.gallerypictureactivity;

import java.io.File;
import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

public class GalleryPictureActivity extends Activity {
	private ArrayList<String>   filepath = new ArrayList<String>();
    GridView                	grid;
    GridViewAdapter         	adapter;
    File                    	file;
    public static Bitmap    	bmp = null;
    ImageView               	imageview;
    ImageButton					imagebutton;
    public int					posicion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getResources().getIdentifier("activity_gallery_picture", "layout", getPackageName()));
		
		Bundle extras = getIntent().getExtras();
        String path = extras.getString("folderPath");
        
        if(! path.isEmpty()){
        	file = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/patrimoniales/industrial/pics/" + path);
        	if(file.exists()){
        		if (file.isDirectory())
                {   
                    for(File img : file.listFiles()){
                    	filepath.add(img.getAbsolutePath());
                    }
                    
                }
                
                grid = (GridView)findViewById(getResources().getIdentifier("gridview", "id", getPackageName()));
                adapter = new GridViewAdapter(this, filepath);
                grid.setAdapter(adapter);
                
                grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick (AdapterView<?> parent, View view,
                            int position, long id)
                    {
                    	posicion = position;
                    	bmp = fetchConfiguredImage(filepath.get(position));
                        imageview.setImageBitmap(bmp);
                        if(imageview.getScaleType() != ImageView.ScaleType.FIT_XY)
            				imageview.setScaleType(ImageView.ScaleType.FIT_XY);
                        bmp = null;
                       
                    }
                });
        	}
        }
        
        
        imageview = (ImageView)findViewById(getResources().getIdentifier("imageView1", "id", getPackageName()));
        
        if(filepath.size() > 0){
        	bmp = fetchConfiguredImage(filepath.get(0));
            imageview.setImageBitmap(bmp);
            if(imageview.getScaleType() != ImageView.ScaleType.FIT_XY) imageview.setScaleType(ImageView.ScaleType.FIT_XY);
            bmp = null;
        }
        
        imagebutton = (ImageButton)findViewById(getResources().getIdentifier("imageButton1", "id", getPackageName()));
        imagebutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(filepath.size() > 0){
					new AlertDialog.Builder(GalleryPictureActivity.this)
				    .setTitle("Eliminar fotografía")
				    .setMessage("¿Desea eliminar esta fotografía?")
				    .setIcon(getResources().getIdentifier("ic_trash", "drawable", getPackageName()))
				    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				        	File file = new File(filepath.get(posicion));
				        	if(file.delete()){
				        		adapter.notifyDataSetChanged();			        		
				        		filepath.remove(posicion);
				        		if(posicion + 1 > filepath.size()){  
				        			if(filepath.size() < 1){
				        				Drawable resource = getResources().getDrawable(getResources().getIdentifier("img_ui_logo", "drawable", getPackageName()));
				        				imageview.setImageDrawable(resource);
				        				if(imageview.getScaleType() != ImageView.ScaleType.CENTER_INSIDE)
					        				imageview.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				        				return;
				        			}
				        			bmp = fetchConfiguredImage(filepath.get(0));
				        			posicion = 0;
				        			imageview.setImageBitmap(bmp);
				        			if(imageview.getScaleType() != ImageView.ScaleType.FIT_XY)
				        				imageview.setScaleType(ImageView.ScaleType.FIT_XY);
				        		}else{
				        			bmp = fetchConfiguredImage(filepath.get(posicion));
				        			imageview.setImageBitmap(bmp);
				        			if(imageview.getScaleType() != ImageView.ScaleType.FIT_XY)
				        				imageview.setScaleType(ImageView.ScaleType.FIT_XY);
				        		}
				        	}
				        }
				     })
				    .setNegativeButton("No", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // do nothing
				        }
				     }).show();
				}
			}
		});

	}
	
	Bitmap fetchConfiguredImage(String filePath){
		int targetWidth = 700; int targetHeight = 500;
        BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
        bmpOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, bmpOptions);
        int currHeight = bmpOptions.outHeight; int currWidth = bmpOptions.outWidth;
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
        return BitmapFactory.decodeFile(filePath, 
        		bmpOptions);
	}
}
