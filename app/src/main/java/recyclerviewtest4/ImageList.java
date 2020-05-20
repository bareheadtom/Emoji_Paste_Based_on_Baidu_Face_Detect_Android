package recyclerviewtest4;

import java.util.ArrayList;
import java.util.List;

public class ImageList {
    static List<Image> imageList=new ArrayList<>();
    static Image imageIntent;
    public static void setImageIntent(Image image){
        imageIntent=image;
    }
    public static Image getImageIntent(){
        return imageIntent;
    }
    public static List<Image> getImageList(){
        return imageList;
    }
}
