package ps20250nguyenngocthuyduong.utils;

import java.net.URL;

public class ImageUtil {
    // Base path for images
    public static final String IMAGES_BASE_PATH = "/ps20250nguyenngocthuyduong/resources/images/";
    
    public static final String BASE_DIR = System.getProperty("user.dir");
    public static final String IMAGE_PATH = BASE_DIR + "/resources/images/";
    
    
    /**
     * Get the resource URL for the specified image file.
     *
     * @param imageName The name of the image file. The parameter should be the path relative to the base image directory defined in {@link #IMAGES_BASE_PATH}.
     *                  For example, if the base path is "/ps20250nguyenngocthuyduong/resources/images/" and you want to retrieve an image located at
     *                  "/ps20250nguyenngocthuyduong/resources/images/icons/example.png", you should pass "icons/example.png" as the parameter.
     * @return The URL of the image resource, or null if the resource could not be found.
     */
    public static URL getResource(String url) {
        String path = IMAGES_BASE_PATH + url;
        return ImageUtil.class.getResource(path);
    }
    
    public static String getDisplayResource(String url) {
        return IMAGE_PATH + url;
    }
}
