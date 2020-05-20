package com.muzhi.camerasdk.example;



import com.bumptech.glide.Glide;
import com.donkingliang.banner.CustomBanner;
import com.muzhi.camerasdk.model.CameraSdkParameterInfo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity1 extends Activity {

   // final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private CustomBanner<String> mBanner;

    private RadioGroup mChoiceMode, mShowCamera,mCrop,mFilter;
    private EditText mRequestNum;
    private CameraSdkParameterInfo mCameraSdkParameterInfo=new CameraSdkParameterInfo();
	
	@RequiresApi(api = Build.VERSION_CODES.M)
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main1);

        // 轮播图
        mBanner = (CustomBanner) findViewById(R.id.banner);

        ArrayList<String> images = new ArrayList<>();
        images.add("https://tva1.sinaimg.cn/large/006tNbRwgy1g9xvo8629zj30ka0kaqf9.jpg");
        images.add("https://tva1.sinaimg.cn/large/006tNbRwgy1g9xvo8hw9mj30k80k849w.jpg");
       /* images.add("https://tva1.sinaimg.cn/large/006tNbRwgy1g9xvwtz5xbj30hj0armxm.jpg");*/
        images.add("https://tva1.sinaimg.cn/large/006tNbRwgy1g9xwc7ld29j30sg0lc11q.jpg");
        setBean(images);



		//动态申请权限
        insertDummyContactWrapper1();
        insertDummyContactWrapper2();
        insertDummyContactWrapper3();
		
		((TextView)findViewById(R.id.camerasdk_actionbar_title)).setText(getString(R.string.app_name));
		
       /* mChoiceMode = (RadioGroup) findViewById(R.id.choice_mode);
        mShowCamera = (RadioGroup) findViewById(R.id.show_camera);
        mRequestNum = (EditText) findViewById(R.id.request_num);
        mCrop = (RadioGroup) findViewById(R.id.rg_crop);
        mFilter = (RadioGroup) findViewById(R.id.rg_filter);*/
        
//        mChoiceMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
//                if(checkedId == R.id.multi){
//                    mRequestNum.setEnabled(true);
//                }else{
//                    mRequestNum.setEnabled(false);
//                    mRequestNum.setText("");
//                }
//            }
//        });

        findViewById(R.id.btn_camera).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mCameraSdkParameterInfo.setSingle_mode(true);

                //boolean camera_flag=mShowCamera.getCheckedRadioButtonId() == R.id.show;
                mCameraSdkParameterInfo.setShow_camera(false);


               /* if(!TextUtils.isEmpty(mRequestNum.getText())){
                  // int maxNum = Integer.valueOf(mRequestNum.getText().toString());

                }*/
                mCameraSdkParameterInfo.setMax_image(1);

                //boolean crop_flag=mCrop.getCheckedRadioButtonId()==R.id.crop_yes;
                mCameraSdkParameterInfo.setCroper_image(false);

                if(false){
                    //暂时只支持单张即Single_mode模式必须为true
                    if(!mCameraSdkParameterInfo.isSingle_mode()){
                        Toast.makeText(MainActivity1.this, "选择模式必须为单选", Toast.LENGTH_LONG).show();;
                        return;
                    }
                }

                //boolean filter_flag=mFilter.getCheckedRadioButtonId()==R.id.filter_yes;
                mCameraSdkParameterInfo.setFilter_image(true);

                /*if(filter_flag){
                	//暂时只支持单张即Single_mode模式必须为true
                	if(!mCameraSdkParameterInfo.isSingle_mode()){
                		Toast.makeText(MainActivity.this, "选择模式必须为单选", Toast.LENGTH_LONG).show();;
                		return;
                	}
                }*/

                Intent intent = new Intent();
                intent.setClassName(getApplication(), "com.muzhi.camerasdk.PhotoPickActivity");
                //intent.setClassName(getApplication(), "com.muzhi.camerasdk.CameraActivity");

                Bundle b=new Bundle();
                b.putSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER, mCameraSdkParameterInfo);
                intent.putExtras(b);

                intent.putExtra("camera","yes");

                startActivityForResult(intent, CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY);

            }
        });

        findViewById(R.id.btn_pick).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				
				//boolean mode_flag=mChoiceMode.getCheckedRadioButtonId() == R.id.single;
				mCameraSdkParameterInfo.setSingle_mode(true);
				
				//boolean camera_flag=mShowCamera.getCheckedRadioButtonId() == R.id.show;
				mCameraSdkParameterInfo.setShow_camera(false);
               
                
               /* if(!TextUtils.isEmpty(mRequestNum.getText())){
                  // int maxNum = Integer.valueOf(mRequestNum.getText().toString());

                }*/
                mCameraSdkParameterInfo.setMax_image(1);
                
                //boolean crop_flag=mCrop.getCheckedRadioButtonId()==R.id.crop_yes;
                mCameraSdkParameterInfo.setCroper_image(false);
                
                if(false){
                	//暂时只支持单张即Single_mode模式必须为true
                	if(!mCameraSdkParameterInfo.isSingle_mode()){
                		Toast.makeText(MainActivity1.this, "选择模式必须为单选", Toast.LENGTH_LONG).show();;
                		return;
                	}
                }
                
                //boolean filter_flag=mFilter.getCheckedRadioButtonId()==R.id.filter_yes;
                mCameraSdkParameterInfo.setFilter_image(true);
                
                /*if(filter_flag){
                	//暂时只支持单张即Single_mode模式必须为true
                	if(!mCameraSdkParameterInfo.isSingle_mode()){
                		Toast.makeText(MainActivity.this, "选择模式必须为单选", Toast.LENGTH_LONG).show();;
                		return;
                	}
                }*/
                
                Intent intent = new Intent();
                intent.setClassName(getApplication(), "com.muzhi.camerasdk.PhotoPickActivity");
                //intent.setClassName(getApplication(), "com.muzhi.camerasdk.CameraActivity");
                intent.putExtra("camera","no");

                Bundle b=new Bundle();
                b.putSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER, mCameraSdkParameterInfo);
                intent.putExtras(b);
                startActivityForResult(intent, CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY);
                
			}
		});

	}

    //设置普通指示器
    private void setBean(final ArrayList<String> beans) {
        mBanner.setPages(new CustomBanner.ViewCreator<String>() {
            @Override
            public View createView(Context context, int position) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }

            @Override
            public void updateUI(Context context, View view, int position, String entity) {
                Glide.with(context).load(entity).into((ImageView) view);
            }
        }, beans)
                //设置自动翻页
                .startTurning(5000);
    }



    public void openFilterActivity(){
		
	}
	
	//跳转到返回结果
	public void openResultActivity(){
		
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY) {
			if(data!=null){
				Intent intent = new Intent(MainActivity1.this, ResultActivity.class);
				intent.putExtras(data.getExtras());
				startActivity(intent);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void insertDummyContactWrapper1() {
        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }

        //insertDummyContact();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void insertDummyContactWrapper2() {
        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.CAMERA);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.CAMERA},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }

        //insertDummyContact();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void insertDummyContactWrapper3() {
        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }

        //insertDummyContact();
    }

}
