package Process;

/*package com.baidu.ai.aip;

import com.baidu.ai.aip.utils.HttpUtil;
import com.baidu.ai.aip.utils.GsonUtils;*/

import java.util.HashMap;
import java.util.Map;

import utils.Base64Util;
import utils.FileUtil;
import utils.GsonUtils;
import utils.HttpUtil;
//import com.baidu.aip.face.AipFace;

/**
* 人脸检测与属性分析
*/
public class FaceDetect {

    /**
    * 重要提示代码中所需工具类
    * FileUtil,Base64Util,HttpUtil,GsonUtils请从
    * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
    * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
    * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
    * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
    * 下载
    */
    static byte[] datas;
	private static AuthService authService=new AuthService();
	public void setByte(byte[] datas){
	    this.datas=datas;
    }
    public byte[] getDatas(){
	    return datas;
    }
    public static String detect() {
        // 请求url
    	
    	FileUtil fileUtil=new FileUtil();
    	Base64Util base64Util=new Base64Util();
    	String imge=null;

    		//imge=base64Util.encode(fileUtil.readFileByBytes("timg1.jpg"));
            imge=base64Util.encode(datas);



    	
    
    	
    	
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/detect";
        try {
            Map<String, Object> map = new HashMap<>();
           // map.put("image", "027d8308a2ec665acb1bdf63e513bcb9");
             map.put("image", imge);
            map.put("face_field", "emotion");
            map.put("image_type", "BASE64");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = authService.getAuth("BPb8UXyNAeWWREYdgwIvfTyp", "zXPouiMTyO2CP73iI7AZzc3Og5pNfBbe");
            System.out.println("accessToken"+accessToken);

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println("\n返回结果为"+result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }




        return null;





    }
    //map.put("",base64Util.encode(fileUtil.readFileByBytes("timg1.jpg")))
    //(String)HttpUtil.post("http:",authService.getAuth("",""),"",GsonUtils.toJson(map))

   /* public static void main(String[] args) {
        FaceDetect.detect();
    }*/
}