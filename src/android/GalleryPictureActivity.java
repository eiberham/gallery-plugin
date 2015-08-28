package com.example.gallerypictureactivity;

import java.io.File;
import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class GalleryPictureActivity extends Activity {
	private ArrayList<String>   filepath;
    GridView                	grid;
    GridViewAdapter         	adapter;
    File                    	file;
    public static Bitmap    	bmp = null;
    ImageView               	imageview;
    public int					posicion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_gallery_picture);
		setContentView(getResources().getIdentifier("activity_gallery_picture", "layout", getPackageName()));
		
		Bundle extras = getIntent().getExtras();
        String path = extras.getString("folderPath");
        
		// Check for SD Card
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED))
        {
            Toast.makeText(this, "Error! No SDCARD Found!",
                    Toast.LENGTH_LONG).show();
        }
        else
        {
            // Locate the image folder in your SD Card
            file = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/patrimoniales/industrial/pics/75-175333-1");
            /*file = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + path);*/
        }
        if (file.isDirectory())
        {
            filepath = new ArrayList<String>();
            
            for(File img : file.listFiles()){
            	filepath.add(img.getAbsolutePath());
            }
            
        }
        //grid = (GridView)findViewById(R.id.gridview);
        grid = (GridView)findViewById(getResources().getIdentifier("gridview", "id", getPackageName()));
        adapter = new GridViewAdapter(this, filepath);
        grid.setAdapter(adapter);
        
        //imageview = (ImageView)findViewById(R.id.imageView1);
        imageview = (ImageView)findViewById(getResources().getIdentifier("imageView1", "id", getPackageName()));
        imageview.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(GalleryPictureActivity.this)
			    .setTitle("Eliminar foto")
			    .setMessage("¿Desea eliminar esta foto?")
			    .setIcon(getResources().getIdentifier("ic_trash", "drawable", getPackageName()))
			    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // continue with delete
			        	File file = new File(filepath.get(posicion));
			        	if(file.delete()){
			        		adapter.notifyDataSetChanged();
			        		filepath.remove(posicion);
			        		if(posicion + 1 > filepath.size()){
			        			bmp = fetchConfiguredImage(filepath.get(0));
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
				return false;
			}
		});

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
