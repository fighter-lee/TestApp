package top.fighter_lee.testapp.info;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * @author fighter_lee
 * @date 2017/11/1
 */
public interface NetApi {

    @GET("check_and_get_url.php?type=android&appid=test")
    Observable<CaiInfo> getCaiInfo();

    /**
     * 查询支持彩票类别
     *
     * @param extra
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<ShowapiRresBody> getTicketList(@Url String url,@Field("extra") String extra);

    @FormUrlEncoded
    @POST
    Observable<ResponseBody> getTicketList1(@Url String url,@Field("extra") String extra);

    @FormUrlEncoded
    @POST
    Observable<MuntiOpen> getMutiPeriodCheck(@Url String url,@Field("code") String code,
                                                                      @Field("endTime") String endTime,
                                                                      @Field("count") String count);
}
