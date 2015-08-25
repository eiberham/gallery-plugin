/*
 * Author: Abraham.
 *
 */

module.exports = {

    /**
     * Open a native built in image gallery activity in a given folder
     *
     * @param {String} folderPath           Path to the target folder
     */
    viewGallery: function (folderPath) {
        var _folder = folderPath;
        return cordova.exec(null, null, "GalleryPlugin", "viewGallery", [_folder]);
    }
}
