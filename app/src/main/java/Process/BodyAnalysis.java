package Process;

import java.util.HashMap;
import java.util.Map;

import utils.Base64Util;
import utils.FileUtil;
import utils.GsonUtils;
import utils.HttpUtil;
import java.net.URLEncoder;

/**
* 人体关键点识别
*/
public class BodyAnalysis {

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
    public static String body_analysis() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/gesture";//"https://aip.baidubce.com/rest/2.0/image-classify/v1/body_analysis";
        try {
            // 本地文件路径
           // String filePath = "insult.jpg";
            byte[] imgData =datas;


            //imge=base64Util.encode(fileUtil.readFileByBytes("timg1.jpg"));


            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;
            //String accessToken =authService.getAuth("BPb8UXyNAeWWREYdgwIvfTyp", "zXPouiMTyO2CP73iI7AZzc3Og5pNfBbe");
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken =authService.getAuth();
            String result = HttpUtil.post(url, accessToken, param);
            System.out.println("handbodyanalysis:"+result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	/*
	 * public static void main(String[] args) { BodyAnalysis.body_analysis(); }
	 */
}