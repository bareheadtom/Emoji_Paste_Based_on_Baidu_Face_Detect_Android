package recyclerviewtest4;

/**
 * Created by 86130 on 2019/7/9.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.muzhi.camerasdk.example.R;
import com.muzhi.camerasdk.example.ResultActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import utils.ImageProcess;

/**
 * Created by 86130 on 2019/7/9.
 */

public class ImageAdapter1 extends RecyclerView.Adapter<ImageAdapter1.ViewHolder1>{
    private List<Image> mImageList1;
    Context pContext;

    @Override
    public ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        if(pContext==null){
            pContext=parent.getContext();
        }
        View view= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.image_item1,parent,false);
        final ViewHolder1 holder1=new ViewHolder1(view);
        holder1.fruitView1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position=holder1.getAdapterPosition();
                Image image =mImageList1.get(position);
                Intent intent = new Intent(pContext, DownLoadActivity.class);
                ImageList.setImageIntent(image);
               pContext.startActivity(intent);
            }
        });
        return holder1;
    }

    @Override
    public void onBindViewHolder(ViewHolder1 holder, int position) {
        Image image=mImageList1.get(position);
        holder.fruitImage1.setImageBitmap(image.getImageContent());
        holder.fruitName1.setText(image.getImageName());
        holder.fruitName1.setGravity(Gravity.CENTER);
    }

    @Override
    public int getItemCount() {
        return mImageList1.size();
    }
    public ImageAdapter1(List<Image> fruit1List){
        mImageList1=fruit1List;
    }
    static class ViewHolder1 extends RecyclerView.ViewHolder{
        View fruitView1;
        ImageView fruitImage1;
        TextView fruitName1;
        public ViewHolder1(View view){
            super(view);
            fruitView1=view;
            fruitImage1=(ImageView)view.findViewById(R.id.image_content);
            fruitName1=(TextView)view.findViewById(R.id.image_name);
        }
    }

}
