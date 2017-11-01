package top.fighter_lee.testapp.info;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author fighter_lee
 * @date 2017/11/1
 */
public interface NetApi {

    @GET("check_and_get_url.php?type=android&appid=test")
    Observable<CaiInfo> getCaiInfo();

}
