package recyclerviewtest4;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.muzhi.camerasdk.example.R;
import com.muzhi.camerasdk.example.ResultActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import utils.ImageProcess;

public class DownLoadActivity extends Activity {
    ImageView imageContentView;
    TextView imageNameView;
    Button btnDownLoad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_activity);
        imageNameView=(TextView)findViewById(R.id.image_download_name);
        imageContentView=(ImageView)findViewById(R.id.image_download_content);
        btnDownLoad=(Button)findViewById(R.id.btn_download);
        final Image image=ImageList.getImageIntent();
        imageNameView.setText(image.getImageName());
        imageNameView.setGravity(Gravity.CENTER);
        imageContentView.setImageBitmap(image.getImageContent());
        btnDownLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveToLocal(image.getImageContent(),image.getImageName());
                    Toast.makeText(DownLoadActivity.this, "保存到本地相册成功", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }
    private void saveToLocal(Bitmap bitmap, String bitName) throws IOException {
        File file = new File("/sdcard/DCIM/Camera/" + bitName);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)) {
                out.flush();
                out.close();
                //保存图片后发送广播通知更新数据库
                // Uri uri = Uri.fromFile(file);
                // sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(file);
                intent.setData(uri);
                // MyCloudAlbumActivity.sendBroadcast(intent);
                // showToast("保存成功");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
