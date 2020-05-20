package recyclerviewtest4;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;

import com.muzhi.camerasdk.example.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import site.SiteActivity;
import utils.HttpUtil;

public class MyCloudAlbumActivity extends Activity {
    private List<Image> imageList=ImageList.getImageList();
    Button btnSite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_cloud_album_activity);
        btnSite=(Button)findViewById(R.id.btn_site);
        btnSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyCloudAlbumActivity.this, SiteActivity.class);
                startActivity(intent);
            }
        });
        System.out.println("list:"+imageList.get(0).getImageName());




        //initFruits();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerview1);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager
                (2, StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager1=new LinearLayoutManager(this);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        ImageAdapter1 adapter1=new ImageAdapter1(imageList);
        recyclerView.setAdapter(adapter1);


        //sendBroadcast(new Intent());
    }



}