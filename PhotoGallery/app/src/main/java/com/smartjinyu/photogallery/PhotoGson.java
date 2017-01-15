package com.smartjinyu.photogallery;

import java.util.List;

/**
 * Created by smartjinyu on 2017/1/15.
 */

public class PhotoGson {
    public photoArray photos;
    public static class photoArray{
        public List<PhotosGson> photo;
    }

}
