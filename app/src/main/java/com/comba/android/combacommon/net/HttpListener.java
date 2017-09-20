package com.comba.android.combacommon.net;

import com.yanzhenjie.nohttp.rest.Response;

/**
 * <p>接受回调结果.</p>
 *
 * @author Yan Zhenjie.
 */
public interface HttpListener<T> {

    void onSucceed(int what, Response<T> response);

    void onFailed(int what, Response<T> response);

}
