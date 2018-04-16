package com.example.rh.newsapp.network.api;


import com.example.rh.newsapp.model.JokeBean;
import com.example.rh.newsapp.model.PhotoArticleBean;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * @author RH
 * @date 2018/3/1
 */
public interface NeiHanService {
    /**内涵段子API*/

    /**
     * 根URL
     */
    String BASEURL = "http://is.snssdk.com/neihan/stream/mix/v1/";

    /**
     * 请求头为手机
     */
    String USER_AGENT_MOBILE = "Mozilla/5.0 (Linux; Android 5.1.1; Nexus 6 Build/LYZ28E) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Mobile Safari/537.36";

    /**
     * 请求头为PC
     */
    String USER_AGENT_PC = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";


    /**url：http://iu.snssdk.com/neihan/stream/mix/v1/
     拼接参数：
     webp：固定值 1
     essence：固定值 1
     content_type：从获取 content_type 中获取得到的 list_id 字段值。目前推荐的是-101，视频的是-104，段友秀的是-301，图片的是-103，段子的是-102
     message_cursor：固定值-1
     am_longitude：经度。可为空
     am_latitude：纬度。可为空
     am_city：城市名，例如：北京市。可为空
     am_loc_time：当前时间 Unix 时间戳，毫秒为单位
     count：返回数量
     min_time：上次更新时间的 Unix 时间戳，秒为单位
     screen_width：屏幕宽度，px为单位
     double_col_mode：固定值0
     iid：???，一个长度为10的纯数字字符串，用于标识用户唯一性
     device_id：设备 id，一个长度为11的纯数字字符串
     ac：网络环境，可取值 wifi
     channel：下载渠道，可360、tencent等
     aid：固定值7
     app_name：固定值joke_essay
     version_code：版本号去除小数点，例如612
     version_name：版本号，例如6.1.2
     device_platform：设备平台，android 或 ios
     ssmix：固定值 a
     device_type：设备型号，例如 hongmi
     device_brand：设备品牌，例如 xiaomi
     os_api：操作系统版本，例如20
     os_version：操作系统版本号，例如7.1.0
     uuid：用户 id，一个长度为15的纯数字字符串
     openudid：一个长度为16的数字和小写字母混合字符串
     manifest_version_code：版本号去除小数点，例如612
     resolution：屏幕宽高，例如 1920*1080
     dpi：手机 dpi
     update_version_code：版本号去除小数点后乘10，例如6120*/


    /**
     * 图片接口1
     */
    @Headers({"User-Agent:" + USER_AGENT_MOBILE, "Cache-Control: public, max-age=3600"})
    @GET(BASEURL + "?mpic=1" +
            "&webp=1" +
            "&essence=1" +
            "&content_type=-103" +
            "&message_cursor=-1" +
            "&longitude=116.4121485" +
            "&latitude=39.9365054" +
            "&am_longitude=116.41828" +
            "&am_latitude=39.937848" +
            "&am_city=%E5%8C%97%E4%BA%AC%E5%B8%82" +
            "&am_loc_time=1483686438786" +
            "&count=30" +
            "&min_time=1483929653" +
            "&screen_width=1080" +
            "&iid=7164180604" +
            "&device_id=34822199408" +
            "&ac=wifi" +
            "&channel=baidu" +
            "&aid=7" +
            "&app_name=joke_essay" +
            "&version_code=590" +
            "&version_name=5.9.0" +
            "&device_platform=android" +
            "&ssmix=a" +
            "&device_type=Nexus%2B5" +
            "&device_brand=google" +
            "&os_api=25" +
            "&os_version=7.1" +
            "&uuid=359250050588035" +
            "&openudid=12645e537a2f0f25" +
            "&manifest_version_code=590" +
            "&resolution=1080*1776" +
            "&dpi=480" +
            "&update_version_code=5903")
    //Call<PhotoArticleBean> getPhoto1();
    Observable<PhotoArticleBean> getPhoto1();

    /**
     * 图片接口2
     * 视频无法播放
     */
    @Headers({"User-Agent:" + USER_AGENT_MOBILE, "Cache-Control: public, max-age=3600"})
    @GET(BASEURL)
    Observable<PhotoArticleBean> getPhoto(@Query("mpic") int mpic,
                                          @Query("webp") int webp,
                                          @Query("essence") int essence,
                                          @Query("content_type") String content_type,
                                          @Query("message_cursor") String message_cursor,
                                          @Query("longitude") String longitude,
                                          @Query("latitude") String latitude,
                                          @Query("am_longitude") String am_longitude,
                                          @Query("am_latitude") String am_latitude,
                                          @Query("am_city") String am_city,
                                          @Query("am_loc_time") String am_loc_time,
                                          @Query("count") int count,
                                          @Query("min_time") String min_time,
                                          @Query("screen_width") int screen_width,
                                          //@Query("do00le_col_mode") int do00le_col_mode,
                                          @Query("iid") String iid,
                                          @Query("device_id") String device_id,
                                          @Query("ac") String ac,
                                          @Query("channel") String channel,
                                          @Query("aid") int aid,
                                          @Query("app_name") String app_name,
                                          @Query("version_code") String version_code,
                                          @Query("version_name") String version_name,
                                          @Query("device_platform") String device_platform,
                                          @Query("ssmix") String ssmix,
                                          @Query("device_type") String device_type,
                                          @Query("device_brand") String device_brand,
                                          @Query("os_api") int os_api,
                                          @Query("os_version") String os_version,
                                          @Query("uuid") String uuid,
                                          @Query("openudid") String openudid,
                                          @Query("manifest_version_code") String manifest_version_code,
                                          @Query("resolution") String resolution,
                                          @Query("dpi") int dpi,
                                          @Query("update_version_code") String update_version_code);


    /**
     * 段子
     */
    @Headers({"User-Agent:" + USER_AGENT_PC, "Cache-Control: public, max-age=3600"})
    @GET("?mpic=1&webp=1&essence=1" +
            "&content_type=-102" +
            "&message_cursor=-1" +
            "&am_longitude=110" +
            "&am_latitude=120" +
            "&am_city=%E5%8C%97%E4%BA%AC%E5%B8%82" +
            "&am_loc_time=1489226058493" +
            "&count=30&min_time=1489205901" +
            "&screen_width=1450" +
            "&do00le_col_mode=0" +
            "&iid=3216590132" +
            "&device_id=32613520945" +
            "&ac=wifi" +
            "&channel=360" +
            "&aid=7" +
            "&app_name=joke_essay" +
            "&version_code=612" +
            "&version_name=6.1.2" +
            "&device_platform=android" +
            "&ssmix=a" +
            "&device_type=sansung" +
            "&device_brand=xiaomi" +
            "&os_api=28" +
            "&os_version=6.10.1" +
            "&uuid=326135942187625" +
            "&openudid=3dg6s95rhg2a3dg5" +
            "&manifest_version_code=612" +
            "&resolution=1450*2800" +
            "&dpi=620" +
            "&update_version_code=6120")
    Observable<JokeBean> getJoke();
}
