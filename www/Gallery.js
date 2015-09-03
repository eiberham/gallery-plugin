/*
 * Author: Abraham Cedeno
 * Date: 01/09/2015
 *
 */
var exec = require('cordova/exec');

var GalleryPlugin =
{
    /**
     * Open a native built in image gallery activity in a given folder
     *
     * @param {String} folderPath           Path to the target folder ignoring the root path storage/emulated/0
     * sample path : /folder/subfolder/subfolder/
     */
    viewGallery : function( folderPath )
    {
        var _folder = folderPath;
        return exec(null, null, "GalleryPlugin", "viewGallery", [_folder]);
    }
}

module.exports = GalleryPlugin;
