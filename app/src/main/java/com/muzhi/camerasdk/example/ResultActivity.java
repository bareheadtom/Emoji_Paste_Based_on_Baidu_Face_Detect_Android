package com.muzhi.camerasdk.example;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import Process.FaceDetect;
import Process.BodyAnalysis;

import com.muzhi.camerasdk.example.adapter.ImageGridAdapter;
import com.muzhi.camerasdk.example.model.ImageInfo;
import com.muzhi.camerasdk.model.CameraSdkParameterInfo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import recyclerviewtest4.Image;
import recyclerviewtest4.ImageList;
import recyclerviewtest4.MyCloudAlbumActivity;
import utils.GsonUtils;
import utils.HttpUtil;

/**
 * @author zengxiaofeng
 */
public class ResultActivity extends Activity {

    // private EditText content;
    private GridView noScrollgridview;
    private ArrayList<ImageInfo> pic_list;
    private ImageGridAdapter mImageGridAdapter;
    private CameraSdkParameterInfo mCameraSdkParameterInfo = new CameraSdkParameterInfo();
    private Button btnEmoji;
    private Button btnMyCloudAlbum;
    ImageView imageView;

    private int imageNameNumber=0;
    private Bitmap bitmapImoji;
    private Bitmap bitmapBA;
    private Bitmap bitmapBefore;
    private Bitmap bitmapAfter;
    private Bitmap newBitmap0;
    private LayerDrawable layerDrawableFace;
    private LayerDrawable layerDrawableHand;
    private LayerDrawable layerDrawableBoth;
    private LayoutParams para;
    private String jsonResult;
    private int l, t, r, b;
    private int lFace, tFace, rFace, bFace, lHand, tHand, rHand, bHand;
    private double w, h, left, top, width, height;
    private double left1, top1, width1, height1;
    Bitmap newBitmapImoji, newBitmapBA;
    byte[] datas;
    int test = 1;
    int test1 = 1;
    int clickFace = 0;
    int clickHand = 0;
    //Bitmap bitmapShelt;

    int compressValue = 8;

    private Button btnShare;
    private Button btnHand;
    private Button btnUpload;
    Thread thread1 = new Thread();
    String emotionType;
    Thread thread2 = new Thread();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        clickFace = 0;
        clickHand = 0;
        ((TextView) findViewById(R.id.camerasdk_actionbar_title)).setText(getString(R.string.app_name));


        // content = (EditText) findViewById(R.id.content);
        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        mImageGridAdapter = new ImageGridAdapter(this, mCameraSdkParameterInfo.getMax_image());
        noScrollgridview.setAdapter(mImageGridAdapter);

        Bundle b = getIntent().getExtras();
        getBundle(b);
		/*try{
			mCameraSdkParameterInfo=(CameraSdkParameterInfo)b.getSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER);
		}
		catch(Exception e){}*/
        initEvent();

        imageView = (ImageView) findViewById(R.id.img_result);
        System.out.println("路径" + pic_list.get(0).getSource_image());

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        bitmapBefore = BitmapFactory.decodeFile(pic_list.get(0).getSource_image(), options);
        options.inSampleSize = compressValue;
        bitmapAfter = BitmapFactory.decodeFile(pic_list.get(0).getSource_image(), options);

        imageView.setImageBitmap(bitmapBefore);


        btnEmoji = (Button) findViewById(R.id.btn_emoji);
        btnEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //一键美化的代码
				/*try {
					thread1.join();
				} catch (InterruptedException e) {-
					e.printStackTrace();
				}*/


                if (test == 0) {
                    Toast.makeText(ResultActivity.this, "未检测到人脸，请重试", Toast.LENGTH_SHORT).show();
                }
                if (test == 1) {
                    pic_list.get(0);
                    System.out.println(pic_list.get(0).getSource_image());


                    int width = imageView.getDrawable().getIntrinsicWidth();
                    int height = imageView.getDrawable().getIntrinsicHeight();
                    Bitmap bitmap1 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    Bitmap bitmap2 = bitmapBefore.copy(Bitmap.Config.ARGB_8888, true);
                    Canvas canvas = new Canvas(bitmap2);
                    imageView.getDrawable().setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                    imageView.getDrawable().draw(canvas);

                    //System.out.println("btimap:"+bitmap.getWidth()+" "+bitmap.getHeight());
                    //System.out.println("newImoji size:"+newBitmapImoji.getWidth()+" "+newBitmapImoji.getHeight());

                    Drawable[] drawableArray = new Drawable[2];
                    drawableArray[0] = new BitmapDrawable(bitmap2);
                    drawableArray[1] = new BitmapDrawable(newBitmapImoji);
                    LayerDrawable layerDrawable = new LayerDrawable(drawableArray);
                    layerDrawable.setLayerInset(0, 0, 0, 0, 0);
                    layerDrawable.setLayerInset(1, lFace, tFace, rFace, bFace);
                    imageView.setImageDrawable(layerDrawable);

                }


            }
        });
        btnHand = findViewById(R.id.btn_hand);
        btnHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (test1 == 0) {
                    Toast.makeText(ResultActivity.this, "未检测到手势，请重试", Toast.LENGTH_SHORT).show();
                }
                if (test1 == 1) {
                    pic_list.get(0);
                    System.out.println(pic_list.get(0).getSource_image());
                    int width = imageView.getDrawable().getIntrinsicWidth();
                    int height = imageView.getDrawable().getIntrinsicHeight();
                    Bitmap bitmap1 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    Bitmap bitmap2 = bitmapBefore.copy(Bitmap.Config.ARGB_8888, true);
                    Canvas canvas = new Canvas(bitmap2);
                    imageView.getDrawable().setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                    imageView.getDrawable().draw(canvas);


                    //System.out.println("btimap:"+bitmap.getWidth()+" "+bitmap.getHeight());
                    //System.out.println("newImoji size:"+newBitmapImoji.getWidth()+" "+newBitmapImoji.getHeight());
                    Drawable[] drawableArray = new Drawable[2];
                    drawableArray[0] = new BitmapDrawable(bitmap2);
                    drawableArray[1] = new BitmapDrawable(newBitmapBA);


                    LayerDrawable layerDrawable = new LayerDrawable(drawableArray);
                    layerDrawable.setLayerInset(0, 0, 0, 0, 0);

                    layerDrawable.setLayerInset(1, lHand, tHand, rHand, bHand);
                    imageView.setImageDrawable(layerDrawable);
                    /*imageView.setImageBitmap(newBitmap0);*/

                }


            }
        });

        //分享功能
        btnShare = (Button) findViewById(R.id.btn_share);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //System.out.println(content.getText());
                //imageView

                // 获取ImageView中的图片
                int width = imageView.getDrawable().getIntrinsicWidth();
                int height = imageView.getDrawable().getIntrinsicHeight();
                Bitmap bitmap1 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Bitmap bitmap2 = bitmapBefore.copy(Bitmap.Config.ARGB_8888, true);
                Canvas canvas = new Canvas(bitmap2);
                imageView.getDrawable().setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                imageView.getDrawable().draw(canvas);

                //Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                //分享byte[]
                Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap2, null, null));
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);//设置分享行为
                intent.setType("image/*");//设置分享内容的类型
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent = Intent.createChooser(intent, "分享");
                startActivity(intent);

            }
        });
        btnUpload = (Button) findViewById(R.id.btn_upload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int width = imageView.getDrawable().getIntrinsicWidth();
                int height = imageView.getDrawable().getIntrinsicHeight();
                Bitmap bitmap1 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                final Bitmap bitmap2 = bitmapBefore.copy(Bitmap.Config.ARGB_8888, true);
                Canvas canvas = new Canvas(bitmap2);
                imageView.getDrawable().setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                imageView.getDrawable().draw(canvas);

                Toast.makeText(ResultActivity.this, "上传图片到云相册成功", Toast.LENGTH_SHORT).show();
                //final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                //bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                imageNameNumber++;
                Random random=new Random();
                imageNameNumber=random.nextInt(100);
                final String imageName=imageNameNumber+".png";
                Image image=new Image(imageName,bitmap2);
                ImageList.getImageList().add(image);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HttpUtil.uploadFile(bitmap2, "http://212.64.48.72/upload/1",imageName);
                            /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] imageContent = baos.toByteArray();*/

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

            }
        });

        btnMyCloudAlbum=(Button)findViewById(R.id.btn_mycloudalbum);
        btnMyCloudAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ImageList.getImageList().isEmpty()){
                    Toast.makeText(ResultActivity.this, "您还未上传图片到云相册", Toast.LENGTH_SHORT).show();
                }else {
                Intent intent = new Intent(ResultActivity.this, MyCloudAlbumActivity.class);
               // intent.putExtras(data.getExtras());
                startActivity(intent);
                }
            }
        });



        thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmapAfter.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                datas = baos.toByteArray();

                FaceDetect faceDetect = new FaceDetect();
                faceDetect.setByte(datas);
                //faceDetect.detect();
                //System.out.println("json:"+faceDetect.getResult());
                jsonResult = faceDetect.detect();
                System.out.println("json:" + jsonResult);


                try {

                    JSONObject jsonObject = new JSONObject(jsonResult);
                    String error_msg = jsonObject.getString("error_msg");
                    if (error_msg.equals("SUCCESS")) {
                        test = 1;
                        String result1 = jsonObject.getString("result");
                        System.out.println("result1=" + result1);

                        JSONObject json1 = new JSONObject(result1);
                        String face_list1 = json1.getString("face_list");
                        System.out.println("face_list1=" + face_list1);

                        JSONArray json2 = new JSONArray(face_list1);
                        int length1 = json2.length();
                        String string = null;
                        for (int n = 0; n < length1; n++) {
                            string = json2.getString(n);
                            System.out.println("string=" + string);
                        }

                        JSONObject json3 = new JSONObject(string);
					/*String face_shape = json3.getString("face_shape");
					System.out.println("face_shape="+face_shape);*/
                        String emotion = json3.getString("emotion");
                        System.out.println("emotion=" + emotion);
                        JSONObject json4 = new JSONObject(emotion);
                        emotionType = json4.getString("type");
                        System.out.println("emotionType=" + emotionType);
                        String location = json3.getString("location");
                        System.out.println("location=" + location);
                        JSONObject jsonLocation = new JSONObject(location);
                        left = Double.parseDouble(jsonLocation.getString("left"));
                        top = Double.parseDouble(jsonLocation.getString("top"));
                        width = Double.parseDouble(jsonLocation.getString("width"));
                        height = Double.parseDouble(jsonLocation.getString("height"));
                        System.out.println("ltwh:" + left + " " + top + " " + width + " " + height);
                        left = left * compressValue;
                        top = top * compressValue;
                        width = width * compressValue;
                        height = height * compressValue;


                        switch (emotionType) {
                            case "angry":
                                bitmapImoji = ((BitmapDrawable) getResources().getDrawable(R.drawable.angry)).getBitmap();
                                break;
                            case "disgust":
                                bitmapImoji = ((BitmapDrawable) getResources().getDrawable(R.drawable.disgust)).getBitmap();
                                break;
                            case "fear":
                                bitmapImoji = ((BitmapDrawable) getResources().getDrawable(R.drawable.fear)).getBitmap();
                                break;
                            case "happy":
                                bitmapImoji = ((BitmapDrawable) getResources().getDrawable(R.drawable.happy)).getBitmap();
                                break;
                            case "sad":
                                bitmapImoji = ((BitmapDrawable) getResources().getDrawable(R.drawable.sad)).getBitmap();
                                break;
                            case "surprise":
                                bitmapImoji = ((BitmapDrawable) getResources().getDrawable(R.drawable.surprise)).getBitmap();
                                break;
                            case "neutral":
                                bitmapImoji = ((BitmapDrawable) getResources().getDrawable(R.drawable.neutral)).getBitmap();
                                break;
                            case "pouty":
                                bitmapImoji = ((BitmapDrawable) getResources().getDrawable(R.drawable.pouty)).getBitmap();
                                break;
                            case "grimace":
                                bitmapImoji = ((BitmapDrawable) getResources().getDrawable(R.drawable.grimace)).getBitmap();
                                break;
                            default:
                                bitmapImoji = ((BitmapDrawable) getResources().getDrawable(R.drawable.happy)).getBitmap();
                                break;
                        }
                        //bitmapImoji=((BitmapDrawable)getResources().getDrawable(R.drawable.angry)).getBitmap();


                        newBitmapImoji = resizeBitmap(bitmapImoji, width, height);
                        //System.out.println("btimap:"+bitmap.getWidth()+" "+bitmap.getHeight());
                        //System.out.println("newImoji size:"+newBitmapImoji.getWidth()+" "+newBitmapImoji.getHeight());
                        Drawable[] drawableArray = new Drawable[2];
                        drawableArray[0] = new BitmapDrawable(bitmapBefore);
                        drawableArray[1] = new BitmapDrawable(newBitmapImoji);

                        w = bitmapBefore.getWidth();
                        h = bitmapBefore.getHeight();
                        //System.out.println("p1:w:"+bitmap.getWidth()+"h:"+bitmap.getHeight());
                        System.out.println("p1:w:" + bitmapImoji.getWidth() + "h:" + bitmapImoji.getHeight());

                        layerDrawableFace = new LayerDrawable(drawableArray);
                        layerDrawableFace.setLayerInset(0, 0, 0, 0, 0);




				/*int l=(int)(471.0/1076*400);
				int t=(int)(218.0/1492*500);
				int r=(int)(400-471.0/1076*400-315.0/1076*400);
				int b=(int)(500-218.0/1492*500-328.0/1492*500);*/
                        //System.out.println("btimap:"+bitmap.getWidth()+" "+bitmap.getHeight());
                        int w1 = bitmapBefore.getWidth();
                        int h1 = bitmapBefore.getHeight();
                        lFace = (int) (left / bitmapBefore.getWidth() * w1);
                        tFace = (int) (top / bitmapBefore.getHeight() * h1);
                        rFace = (int) (w1 - left / bitmapBefore.getWidth() * w1 - width / bitmapBefore.getWidth() * w1);
                        bFace = (int) (h1 - top / bitmapBefore.getHeight() * h1 - height / bitmapBefore.getHeight() * h1);
                        //System.out.println("last"+l+" "+t+" "+r+" "+b);
                        layerDrawableFace.setLayerInset(1, lFace, tFace, rFace, bFace);

                    } else {
                        test = 0;
                    }


					/*String angle = json3.getString("angle");
					System.out.println("angel="+angle);
					String beauty = json3.getString("beauty");
					System.out.println("beauty="+beauty);
					String age = json3.getString("age");
					System.out.println("age="+age);
					String face_probability = json3.getString("face_probability");
					System.out.println("face_probability="+face_probability);*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //hand***********************************************************
                if (test == 1) {//如果检测到人脸则遮挡住人脸再上传
                    int width = layerDrawableFace.getIntrinsicWidth();
                    int height = layerDrawableFace.getIntrinsicHeight();
                    Bitmap bitmap1 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    Bitmap bitmapShelt = bitmapBefore.copy(Bitmap.Config.ARGB_8888, true);
                    Canvas canvas = new Canvas(bitmapShelt);
                    layerDrawableFace.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                    layerDrawableFace.draw(canvas);

                    Bitmap bitmapResize = resizeBitmapReal(bitmapShelt, bitmapShelt.getWidth() / compressValue, bitmapShelt.getHeight() / compressValue);

                    System.out.println("b " + bitmapShelt.getWidth() + "" + bitmapShelt.getHeight());
                    System.out.println(" a" + bitmapResize.getWidth() + " " + bitmapResize.getHeight());
                    ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                    bitmapResize.compress(Bitmap.CompressFormat.JPEG, 100, baos1);
                    datas = baos1.toByteArray();
                } else {
                    ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                    bitmapAfter.compress(Bitmap.CompressFormat.JPEG, 100, baos1);
                    datas = baos1.toByteArray();
                }
                BodyAnalysis bodyAnalysis = new BodyAnalysis();
                bodyAnalysis.setByte(datas);

                String result = bodyAnalysis.body_analysis();
                System.out.println("jsonHand:" + result);
                JSONObject jsonObject;


                try {
                    jsonObject = new JSONObject(result);
                    int result_num = jsonObject.getInt("result_num");


                    if (result_num != 0) {
                        test1 = 1;
                        //String res =jsonObject.getString("result");   //jsonObject.getInt();     //.getString("top");
                        JSONArray jsonArray = jsonObject.getJSONArray("result");//.getJsonArray("result");//fromObject(res);//.getJSONArray();
                        /*
                         * for (int i = 0; i < jsonArray.length(); i++) {
                         * System.out.println("\t"+jsonArray.get(i)); }
                         */
                        Object o = jsonArray.get(0);
                        String s = String.valueOf(o);
                        jsonObject = new JSONObject(s);
                        System.out.println("hand" + jsonObject);
                        String classname = jsonObject.getString("classname");
                        top1 = 1.0 * jsonObject.getInt("top");
                        height1 = 1.0 * jsonObject.getInt("height");
                        width1 = 1.0 * jsonObject.getInt("width");
                        left1 = 1.0 * jsonObject.getInt("left");
                        left1 = left1 * compressValue;
                        top1 = top1 * compressValue;
                        width1 = width1 * compressValue;
                        height1 = height1 * compressValue;
                        switch (classname) {
                            case "One":
                                bitmapBA = ((BitmapDrawable) getResources().getDrawable(R.drawable.one)).getBitmap();
                                break;
                            case "Five":
                                bitmapBA = ((BitmapDrawable) getResources().getDrawable(R.drawable.five)).getBitmap();
                                break;
                            case "Fist":
                                bitmapBA = ((BitmapDrawable) getResources().getDrawable(R.drawable.fist)).getBitmap();
                                break;
                            case "Ok":
                                bitmapBA = ((BitmapDrawable) getResources().getDrawable(R.drawable.ok)).getBitmap();
                                break;
                            case "Prayer":
                                bitmapBA = ((BitmapDrawable) getResources().getDrawable(R.drawable.prayer)).getBitmap();
                                break;
                            case "Congratulation":
                                bitmapBA = ((BitmapDrawable) getResources().getDrawable(R.drawable.congratulation)).getBitmap();
                                break;
                            case "Honour":
                                bitmapBA = ((BitmapDrawable) getResources().getDrawable(R.drawable.honour)).getBitmap();
                                break;
                            case "Heart_single":
                                bitmapBA = ((BitmapDrawable) getResources().getDrawable(R.drawable.heart_single)).getBitmap();
                                break;
                            case "Thumb_up":
                                bitmapBA = ((BitmapDrawable) getResources().getDrawable(R.drawable.thumb_up)).getBitmap();
                                break;
                            case "Thumb_down":
                                bitmapBA = ((BitmapDrawable) getResources().getDrawable(R.drawable.thumb_down)).getBitmap();
                                break;
                            case "ILY":
                                bitmapBA = ((BitmapDrawable) getResources().getDrawable(R.drawable.ily)).getBitmap();
                                break;
                            case "Palm_up":
                                bitmapBA = ((BitmapDrawable) getResources().getDrawable(R.drawable.palm_up)).getBitmap();
                                break;
                            case "Heart_1":
                            case "Heart_2":
                            case "Heart_3":
                                bitmapBA = ((BitmapDrawable) getResources().getDrawable(R.drawable.heart)).getBitmap();
                                break;
                            case "Two":
                                bitmapBA = ((BitmapDrawable) getResources().getDrawable(R.drawable.two)).getBitmap();
                                break;
                            case "Three":
                                bitmapBA = ((BitmapDrawable) getResources().getDrawable(R.drawable.three)).getBitmap();
                                break;
                            case "Four":
                                bitmapBA = ((BitmapDrawable) getResources().getDrawable(R.drawable.four)).getBitmap();
                                break;
                            case "Six":
                                bitmapBA = ((BitmapDrawable) getResources().getDrawable(R.drawable.six)).getBitmap();
                                break;
                            case "Seven":
                                bitmapBA = ((BitmapDrawable) getResources().getDrawable(R.drawable.seven)).getBitmap();
                                break;
                            case "Eight":
                                bitmapBA = ((BitmapDrawable) getResources().getDrawable(R.drawable.eight)).getBitmap();
                                break;
                            case "Nine":
                                bitmapBA = ((BitmapDrawable) getResources().getDrawable(R.drawable.nine)).getBitmap();
                                break;
                            case "Rock":
                                bitmapBA = ((BitmapDrawable) getResources().getDrawable(R.drawable.rock)).getBitmap();
                                break;
                            case "Insult":
                                bitmapBA = ((BitmapDrawable) getResources().getDrawable(R.drawable.insult)).getBitmap();
                                break;
                            case "Face":
                                test1 = 0;
                                break;
                        }
                        //bitmapImoji=((BitmapDrawable)getResources().getDrawable(R.drawable.angry)).getBitmap();
                        if (bitmapBA != null) {
                            newBitmapBA = resizeBitmap(bitmapBA, width1 * 1.0, height1 * 1.0);
                            //System.out.println("btimap:"+bitmap.getWidth()+" "+bitmap.getHeight());
                            //System.out.println("newImoji size:"+newBitmapBA.getWidth()+" "+newBitmapBA.getHeight());
                            Drawable[] drawableArray = new Drawable[2];
                            drawableArray[0] = new BitmapDrawable(bitmapBefore);
                            drawableArray[1] = new BitmapDrawable(newBitmapBA);

                            w = bitmapBefore.getWidth();
                            h = bitmapBefore.getHeight();
                            //System.out.println("p1:w:"+bitmap.getWidth()+"h:"+bitmap.getHeight());
//						System.out.println("p1:w:"+bitmapImoji.getWidth()+"h:"+bitmapImoji.getHeight());

                            layerDrawableHand = new LayerDrawable(drawableArray);
                            layerDrawableHand.setLayerInset(0, 0, 0, 0, 0);




				/*int l=(int)(471.0/1076*400);
				int t=(int)(218.0/1492*500);
				int r=(int)(400-471.0/1076*400-315.0/1076*400);
				int b=(int)(500-218.0/1492*500-328.0/1492*500);*/
                            //System.out.println("btimap:"+bitmap.getWidth()+" "+bitmap.getHeight());
                            int w1 = bitmapBefore.getWidth();
                            int h1 = bitmapBefore.getHeight();
                            lHand = (int) (left1 / bitmapBefore.getWidth() * w1);
                            tHand = (int) (top1 / bitmapBefore.getHeight() * h1);
                            rHand = (int) (w1 - left1 / bitmapBefore.getWidth() * w1 - width1 / bitmapBefore.getWidth() * w1);
                            bHand = (int) (h1 - top1 / bitmapBefore.getHeight() * h1 - height1 / bitmapBefore.getHeight() * h1);
                            System.out.println("Hand" + lHand + " " + tHand + " " + rHand + " " + bHand);
                            layerDrawableHand.setLayerInset(1, lHand, tHand, rHand, bHand);
                        } else {
                            test1 = 0;
                        }

                    } else {
                        test1 = 0;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                ImageList.getImageList().clear();
                String jsonResult=HttpUtil.getJsonUrl("http://212.64.48.72/android_cloud/1");
                try {
                    if(jsonResult!=null) {
                        JSONObject jsonObject=new JSONObject(jsonResult);
                        int i = 1;
                        while (jsonObject.has(i + "")) {
                            String url = jsonObject.getString(i + "");
                            String imageName = url.substring(url.indexOf("cloud/") + "coud/".length() + 1, url.length());
                            byte[] img = HttpUtil.getPicUrl(url);
                            if (img != null) {
                                System.out.println("usl !: " + url + " imageName" + imageName);
                                Bitmap imageContent = BitmapFactory.decodeByteArray(img, 0, img.length);
                                Image image = new Image(imageName, imageContent);
                                ImageList.getImageList().add(image);
                            }
                            i++;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private Bitmap resizeBitmap(Bitmap bitmap, double newWidth, double newHeight) {
        int widthOld = bitmap.getWidth();
        int heightOld = bitmap.getHeight();
        // 计算缩放比例
        System.out.println("resizeBitmap:" + bitmap.getWidth() + " " + bitmap.getHeight());
        float scaleWidth = ((float) (newWidth + newWidth / 2)) / widthOld;
        float scaleHeight = ((float) (newHeight + newHeight / 2)) / heightOld;
        System.out.println("new:" + (float) (newWidth + newWidth / 2) + " " + (float) (newHeight + newHeight / 2));
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        System.out.println("scale:" + scaleWidth + " " + scaleHeight);
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbitmapImoji = Bitmap.createBitmap(bitmap, 0, 0, widthOld, heightOld, matrix, true);
        return newbitmapImoji;
    }

    private Bitmap resizeBitmapReal(Bitmap bitmap, double newWidth, double newHeight) {
        int widthOld = bitmap.getWidth();
        int heightOld = bitmap.getHeight();
        // 计算缩放比例
        System.out.println("resizeBitmap:" + bitmap.getWidth() + " " + bitmap.getHeight());
        float scaleWidth = ((float) (newWidth)) / widthOld;
        float scaleHeight = ((float) (newHeight)) / heightOld;
        System.out.println("new:" + (float) (newWidth + newWidth / 2) + " " + (float) (newHeight + newHeight / 2));
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        System.out.println("scale:" + scaleWidth + " " + scaleHeight);
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbitmapImoji = Bitmap.createBitmap(bitmap, 0, 0, widthOld, heightOld, matrix, true);
        return newbitmapImoji;
    }


    private void getBundle(Bundle bundle) {
        if (bundle != null) {
            pic_list = new ArrayList<ImageInfo>();

            mCameraSdkParameterInfo = (CameraSdkParameterInfo) bundle.getSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER);
            ArrayList<String> list = mCameraSdkParameterInfo.getImage_list();
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    ImageInfo img = new ImageInfo();
                    img.setSource_image(list.get(i));
                    pic_list.add(img);
                }

            }
            if (pic_list.size() < mCameraSdkParameterInfo.getMax_image()) {
                ImageInfo item = new ImageInfo();
                item.setAddButton(true);
                pic_list.add(item);
            }
            mImageGridAdapter.setList(pic_list);
        }
    }

    private void initEvent() {
        noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //ImageInfo info=(ImageInfo)ImageGridAdapter.getItem(position);
                ImageInfo info = (ImageInfo) arg0.getAdapter().getItem(position);
                if (info.isAddButton()) {

                    ArrayList<String> list = new ArrayList<String>();
                    for (ImageInfo pic : pic_list) {
                        if (!pic.isAddButton()) {
                            list.add(pic.getSource_image());
                        }
                    }
                    openCameraSDKPhotoPick(ResultActivity.this, list);
                } else {
                    openCameraSDKImagePreview(ResultActivity.this, pic_list.get(position).getSource_image(), position);
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY:
                if (data != null) {
                    getBundle(data.getExtras());
                }
                break;
            case CameraSdkParameterInfo.TAKE_PICTURE_PREVIEW:
                if (data != null) {
                    int position = data.getIntExtra("position", -1);
                    if (position >= 0) {
                        mImageGridAdapter.deleteItem(position);
                        mCameraSdkParameterInfo.getImage_list().remove(position);
                    }
                }
                break;
        }

    }


    //图片预览
    public void openCameraSDKImagePreview(Activity activity, String path, int position) {
		/*Intent intent = new Intent();
		intent.setClassName(activity.getApplication(), "com.muzhi.camerasdk.PreviewActivity");
		ArrayList<String> list=new ArrayList<String>();
		list.add(path);
		mCameraSdkParameterInfo.setImage_list(list);
		mCameraSdkParameterInfo.setPosition(position);*/

        mCameraSdkParameterInfo.setPosition(position);
        Intent intent = new Intent();
        intent.setClassName(activity.getApplication(), "com.muzhi.camerasdk.PreviewActivity");
        Bundle b = new Bundle();
        b.putSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER, mCameraSdkParameterInfo);
        intent.putExtras(b);
        startActivityForResult(intent, CameraSdkParameterInfo.TAKE_PICTURE_PREVIEW);
    }

    //本地相册选择
    public void openCameraSDKPhotoPick(Activity activity, ArrayList<String> list) {
        Intent intent = new Intent();
        intent.setClassName(activity.getApplication(), "com.muzhi.camerasdk.PhotoPickActivity");
        Bundle b = new Bundle();

        b.putSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER, mCameraSdkParameterInfo);
        intent.putExtras(b);
        startActivityForResult(intent, CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY);

    }


}
