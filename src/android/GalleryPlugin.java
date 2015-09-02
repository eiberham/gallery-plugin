package com.example.gallerypictureactivity;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.apache.cordova.PluginResult;

import android.app.Activity;
import android.content.Intent;

public class GalleryPlugin extends CordovaPlugin{
	
	public CallbackContext  					callbackContext;
	public String								folderPath;
	private static final String VIEW_GALLERY = 	"viewGallery";
	
	/**
	 * Constructor.
	 */
	public GalleryPlugin() {

	}
	
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		this.callbackContext = callbackContext;
		this.folderPath = args.getString(0);
		
		if(action.equals(VIEW_GALLERY)){
			Intent intent = new Intent(this.cordova.getActivity(), GalleryPictureActivity.class);
			intent.putExtra("folderPath", this.folderPath);
			
			if(this.cordova != null)
	    		this.cordova.startActivityForResult((CordovaPlugin) this, intent, 1);
			
			PluginResult r = new PluginResult(PluginResult.Status.OK);
	        r.setKeepCallback(true);
	        callbackContext.sendPluginResult(r);
	        
	    	return true;
		}
		return false;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		resultCode = Activity.RESULT_OK;
	}
	
}
