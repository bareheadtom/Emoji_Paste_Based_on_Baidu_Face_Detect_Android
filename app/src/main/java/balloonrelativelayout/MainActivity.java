package balloonrelativelayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

//import android.util.EventLogTags;
import android.util.Log;


import com.muzhi.camerasdk.example.MainActivity1;
import com.muzhi.camerasdk.example.R;
import com.muzhi.camerasdk.example.ResultActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.*;


public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private BalloonRelativeLayout mBalloonRelativeLayout;
    private VideoView mVideoView;

    private int TIME = 100;//这里默认每隔100毫秒添加一个气泡
    Handler mHandler = new Handler();
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                mHandler.postDelayed(this, TIME);
                mBalloonRelativeLayout.addBalloon();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mVideoView = (VideoView) findViewById(R.id.videoView);
        mBalloonRelativeLayout = (BalloonRelativeLayout) findViewById(R.id.balloonRelativeLayout);
        initVideoView();
    }
*/

    private void initVideoView() {
        //设置屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mqr));
        //设置相关的监听
        mVideoView.setOnPreparedListener(this);
        mVideoView.setOnCompletionListener(this);
    }

    //播放准备
    @Override
    public void onPrepared(MediaPlayer mp) {
        //开始播放
        mVideoView.start();
        mHandler.postDelayed(runnable, TIME);
    }

    //播放结束
    @Override
    public void onCompletion(MediaPlayer mp) {
        //开始播放
        mVideoView.start();
    }

    private EditText user;
    private EditText password;
    private Button login;
    private Button visitor_login;
    private TextView register;
    private SharedPreferences pref;
    private CheckBox rembemberPass;
    public static final String TAG = "LoginActivity";
    private static final String URLLOGIN = "xxx/login/json/data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mVideoView = (VideoView) findViewById(R.id.videoView);
        mBalloonRelativeLayout = (BalloonRelativeLayout) findViewById(R.id.balloonRelativeLayout);
        initVideoView();



        pref = PreferenceManager.getDefaultSharedPreferences(this);

        //绑定控件
        init();

        //记住密码
        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember) {
            String user1 = pref.getString("user", "");
            String password1 = pref.getString("password", "");
            user.setText(user1);
            password.setText(password1);
            rembemberPass.setChecked(true);
        }

        visitor_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity1.class);

                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override

            //登录按键的响应
            public void onClick(View v) {
             /*   new Thread() {
                    public void run() {*/
                        String[] data = null;
                        String inputUser= user.getText().toString();
                        String inputPassword = password.getText().toString();

                        if (TextUtils.isEmpty(inputUser)) {
                            Toast.makeText(MainActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(inputPassword)) {
                            Toast.makeText(MainActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                        } else {
                            String str;
                            //username="1";//测试用户名
                            //password="1";//密码
                            String path="http://212.64.48.72/login/"+inputUser+"/"+inputPassword;

                            try {
                                URL url = new URL(path);
                                //URL url = new URL(URLEncoder.encode(path));

                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                //conn.setDoOutput(true);
                                conn.setDoInput(true);
                                conn.setRequestMethod("GET");
                                conn.connect();
                                InputStream is = conn.getInputStream();
                                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                                while ((str = br.readLine()) != null) {
                                    str = new String(str.getBytes(), "UTF-8");
                                    System.out.println(str);
                                    //Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
                                    Log.d("MyTest", str);
                                    //Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
                                    if(str.equals("login_success")){
                                        Intent intent = new Intent();
                                        intent.setClass(MainActivity.this, MainActivity1.class);//从MainActivity页面跳转至Activity1页面
                                        startActivity(intent);
                                        MainActivity.this.finish();}
                                    else
                                    {
                                        Looper.prepare();
                                        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }
                                       /* startActivity(new Intent(MainActivity.this, SuccessActivity.class));
                                        MainActivity.this.finish();*/
                                }
//          user_not_exists 用户不存在
//          login_success  登录成功
//          wrong_password 密码错误
                                is.close();
                                conn.disconnect();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
             /*       };
                }.start();
*/








            }
        });




        visitor_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MainActivity1.class);//从MainActivity页面跳转至Activity1页面
                startActivity(intent);
                MainActivity.this.finish();
            }
        });




    }
    /**
     * 初始化
     */
    private void init(){
        login = findViewById(R.id.login);
        visitor_login=findViewById(R.id.visitor_login);
        register = findViewById(R.id.register);
        user = findViewById(R.id.user);
        password = findViewById(R.id.password);
        rembemberPass = findViewById(R.id.remember);
    }
}
