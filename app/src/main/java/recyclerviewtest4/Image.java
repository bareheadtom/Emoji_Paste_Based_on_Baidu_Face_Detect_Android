package recyclerviewtest4;

import android.graphics.Bitmap;

/**
 * Created by 86130 on 2019/7/9.
 */

public class Image {
    private String imageName;
    private Bitmap imageContent;
    public Image(String imageName, Bitmap imageContent){
        this.imageName=imageName;
        this.imageContent=imageContent;
    }
    public String getImageName(){
        return imageName;
    }
    public Bitmap getImageContent(){
        return imageContent;
    }
}