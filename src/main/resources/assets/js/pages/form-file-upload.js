$(document).ready(function() {
    
    "use strict";
    

    // Setup html5 version
    $("#uploader").pluploadQueue({
        // General settings
        runtimes : 'html5,flash,silverlight,html4',
        url : "/viewer/user/uploadtest",
        chunk_size : '100mb',
        rename : true,
        dragdrop: false,
        multipart:true,
        multi_selection:true,
        headers: {
            paperName: '1',
            paperFile: null,
            deadline: "sddssda"  //也可以是一个数组
        },
        filters : {
            // Maximum file size
            max_file_size : '800mb',
            // Specify what files to browse for
            mime_types: [
                {title : "Image files", extensions : "jpg,png"},/*,gif*/
                {title : "Word files", extensions : "pdf,doc,docx"},/*docm,dotx,dotm,*/
                {title : "Excel files", extensions : "xls,xlsx"}/*,xlsm,xltx,xltm,xlsb,xlam*/
                /*{title : "PPT files", extensions : "PPt,pptx,pptm,ppsx,ppsm,potx,potm,ppam"},
                {title : "3D files", extensions : "3MF、AMF、AWD、AssimpJSON、Assimp、BVH、Babylon、BasisTexture、Collada、DDS、HDRCubeTexture、DRACO、EXR、FBX、GCode、GLTF、KMZ、KTX、LDraw、LWO、MD2、MMD、MTL、NRRD、OBJ、PCD、PDB、PLY、PRWM、PVR、PlayCanvas、RGBE、STL、SVG、TDS、TGA、TTF、VRML、VRM、VTK"},
                {title : "Zip files", extensions : "zip,rar,7z"}*/
            ]
        },

        // Resize images on clientside if we can
        resize: {
            //width : 200,
            //height : 200,
            quality : 90,
            crop: true // crop to exact dimensions
        },


        // Flash settings
        flash_swf_url : '/admin1/assets/plugins/plupload/js/Moxie.swf',

        // Silverlight settings
        silverlight_xap_url : '/admin1/assets/plugins/plupload/js/Moxie.xap'
    });
});