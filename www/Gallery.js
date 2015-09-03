/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
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
