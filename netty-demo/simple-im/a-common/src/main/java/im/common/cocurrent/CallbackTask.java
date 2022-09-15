package im.common.cocurrent;

/**
 * <br/>
 * Created by ZXFeng on 2022/9/15.
 */
public interface CallbackTask<R> {

    R execute() throws Exception;

    void onBack(R r);

    void onException(Throwable t);

}
