package com.example.CommonTest.utils;


import android.util.Log;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.util.Auth;
import org.json.JSONObject;


public class uploadPic {
    static String domin="http://q94nddme1.bkt.clouddn.com/";

    public static String uploadPic(String data,String key)
    {
        //指定zone的具体区域
        //FixedZone.zone0   华东机房
        //FixedZone.zone1   华北机房
        //FixedZone.zone2   华南机房
        //FixedZone.zoneNa0 北美机房


    /*
    Configuration config = new Configuration.Builder()
            .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
            .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
            .connectTimeout(10)           // 链接超时。默认10秒
            .useHttps(true)               // 是否使用https 默认是false
            .responseTimeout(60)          // 服务器响应超时。默认60秒
            .recorder(recorder)           // recorder分片上传时，已上传片记录器。默认null
            .recorder(recorder, keyGen)   // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
            .zone(FixedZone.zone0)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
    */


        Configuration config = new Configuration.Builder()
                .useHttps(true)               // 是否使用https上传域名
                .zone(FixedZone.zone0)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();

        UploadManager uploadManager = new UploadManager(config); // UploadManager对象只需要创建一次重复使用


        /**
         * 生成token
         * create()方法的两个参数分别是 AK SK
         * uoloadToken()方法的参数是 要上传到的空间(bucket)
         */

//        AK:I2HcqrxZkEc1PLdL7xLMw_v3cpoaRryKk3VoNX7H
//        SK:A-Hi9TEfWuFswR4ksjME4F-JMsRc9JxRePr_vjIj
//        存储空间：wzp109761images
        String token = Auth.create("I2HcqrxZkEc1PLdL7xLMw_v3cpoaRryKk3VoNX7H", "A-Hi9TEfWuFswR4ksjME4F-JMsRc9JxRePr_vjIj").uploadToken("wzp109761images");

        /**
         * 调用put方法上传
         * 第一个参数 data：可以是字符串，是要上传图片的路径
         *                可以是File对象，是要上传的文件
         *                可以是byte[]数组，要是上传的数据
         * 第二个参数 key：字符串，是图片在服务器上的名称，要具有唯一性，可以用UUID
         * 第三个参数 token：根据开发者的 AK和SK 生成的token，这个token 应该在后端提供一个接口，然后android代码中发一个get请求获得这个tocken，但这里为了演示，直接写在本地了.
         * 第四个参数：UpCompletionHandler的实例，有个回调方法
         * 第五个参数：可先参数
         */
        uploadManager.put
                (
                        data, key, token,
                        new UpCompletionHandler()
                        {
                            /**
                             * 回调方法
                             * @param key 开发者设置的 key 也就是文件名
                             * @param info 日志，包含上传的ip等
                             * @param res 包括一个hash值和key
                             */
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject res)
                            {
                                if(info.isOK())
                                {
                                    Log.i("上传结果：", "Upload Success");
                                    System.out.println("url:");
                                }
                                else
                                {
                                    Log.i("上传结果：", "Upload Fail");
                                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                                }
                                Log.i("key：", key + "\ninfo：" + info + "\nres：" + res);
                            }
                        },
                        null
                );
        return domin+key+".jpg";
    }

}
