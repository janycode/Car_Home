package com.autohome.common.baidu;

import com.baidu.aip.ocr.AipOcr;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * @program: OpenMain
 * @description: 基于百度AI识别封装的操作  识别
 * @author: Jerry(姜源)
 * @create: 2020-07-29 17:08
 */
@Slf4j
public class OcrUtil {
    /**
     * 百度智能云：登陆后 --> 控制台 --> 产品服务 --> 【文字识别】 --> 创建应用 --> 自动生成API KEY
     * AppID / API Key / Secret Key
     * Java SDK 内容审核接口说明：https://cloud.baidu.com/doc/OCR/s/nk3h7yc12
     */
    public static final String APP_ID = "xxx";
    public static final String API_KEY = "yyy";
    public static final String SECRET_KEY = "zzz";

    private static AipOcr ocr;

    static {
        ocr = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
    }

    /**
     * 身份证识别
     *
     * @param data
     * @param flag true 正面，false 反面
     * @return
     */
    public static String idcard(byte[] data, boolean flag) {
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "true");
        options.put("detect_risk", "true");
        JSONObject res;
        // front：身份证含照片的一面；back：身份证带国徽的一面
        if (flag) {
            res = ocr.idcard(data, "front", options);
            if (res.getString("image_status").equals("normal")) {
                return res.getJSONObject("words_result").getJSONObject("公民身份号码").getString("words");
            } else {
                log.error("身份证识别识别：" + res.getString("image_status"));
            }
        } else {
            res = ocr.idcard(data, "back", options);
            log.info(res.toString());
            return "无法识别身份证号，这是国徽面";
        }
        return null;
    }

    /**
     * 营业执照识别
     *
     * @param data
     * @return
     */
    public static String busine(byte[] data) {
        JSONObject res = ocr.businessLicense(data, new HashMap<>());
        return res.getJSONObject("words_result").getJSONObject("证件编号").getString("words");
    }

    // test
    public static void main(String[] args) throws IOException {
        String imgName = "E:\\图片\\test\\sfz-B.jpg";
        FileInputStream fis = new FileInputStream(new File(imgName));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] arr = new byte[1024];
        int len;
        while ((len = fis.read(arr)) != -1) {
            baos.write(arr, 0, len);
        }
        System.out.println(imgName + " ---> " + idcard(baos.toByteArray(), false));
    }

}
