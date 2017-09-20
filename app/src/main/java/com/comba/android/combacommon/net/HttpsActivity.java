/*
 * Copyright 2015 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.comba.android.combacommon.net;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.comba.android.combacommon.R;
import com.comba.android.combacommon.adapters.OnItemClickListener;
import com.comba.android.combacommon.adapters.RecyclerListSingleAdapter;
import com.comba.android.combacommon.net.httpAbout.SSLContextUtil;
import com.comba.android.combacommon.utils.Constants;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLContext;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;

/**
 */
public class HttpsActivity extends BaseOkHttpActivity implements HttpListener<String> {

    private  Object cancelSign=new Object();

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_https);

        List<String> imageItems = Arrays.asList(getResources().getStringArray(R.array.activity_https_item));
        RecyclerListSingleAdapter listAdapter = new RecyclerListSingleAdapter(imageItems, mItemClickListener);
        RecyclerView recyclerView = ButterKnife.findById(this, R.id.rv_https_activity);
        recyclerView.setAdapter(listAdapter);


        HttpRequestUtil.getPhotoRequest(false,false,RequestMethod.GET,this,this,"",1,false,false);

    }

//    private OnItemClickListener mItemClickListener = new OnItemClickListener() {
//        @Override
//        public void onItemClick(View v, int position) {
//            if (0 == position) {
//                httpsVerify();
//            } else {
//                httpsNoVerify();
//            }
//        }

        private  OnItemClickListener mItemClickListener=new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (0 == position) {
                    httpsVerify();
                } else if(1==position){
                    httpsNoVerify();
                }else if(2==position){
                    //请求图片
                    request(3, NoHttp.createImageRequest(Constants.URL_NOHTTP_IMAGE,RequestMethod.GET), new HttpListener<Bitmap>() {
                        @Override
                        public void onSucceed(int what, Response<Bitmap> response) {
                            int responseCode = response.getHeaders().getResponseCode();// 服务器响应码
                            if (responseCode == 200) {// 如果确定你们的服务器是get或者post，上面的不用判断
                                showImageDialog(null, response.get());
                            }
                        }

                        @Override
                        public void onFailed(int what, Response<Bitmap> response) {
                                showMessageDialog(R.string.request_failed, response.getException().getMessage());
                        }
                    }, false, true);
                } else if(3==position){ //上传图片
                    File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"aco.mp4");
                    if(!file.exists()){
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    executeUpload(file);
                }else if(4==position){  //网络请求缓存策略
                    requestImage();
                }
            }
        };


        /**
         * Https请求，带证书。
         */
        private void httpsVerify() {
            Request<String> httpsRequest = NoHttp.createStringRequest("https://www.baidu.com/", RequestMethod.GET);
            SSLContext sslContext = SSLContextUtil.getSSLContext();

            // 主要是需要一个SocketFactory对象，这个对象是java通用的，具体用法还请Google、Baidu。
            if (sslContext != null)
                httpsRequest.setSSLSocketFactory(sslContext.getSocketFactory());
            request(0, httpsRequest, this, false, true);
        }

        /**
         * Https请求，不带证书。
         */
        private void httpsNoVerify() {
            Request<String> httpsRequest = NoHttp.createStringRequest("http://www.qq.com/", RequestMethod.GET);
            SSLContext sslContext = SSLContextUtil.getDefaultSLLContext();
            if (sslContext != null)
                httpsRequest.setSSLSocketFactory(sslContext.getSocketFactory());
            httpsRequest.setHostnameVerifier(SSLContextUtil.HOSTNAME_VERIFIER);
            request(0, httpsRequest, this, false, true);
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            showMessageDialog(R.string.request_succeed, response.get());
        }

        @Override
        public void onFailed(int what, Response<String> response) {
            showMessageDialog(R.string.request_failed, response.getException().getMessage());
        }

        //上传图片*文件
    private void executeUpload(File file) {
        Request<String> request = NoHttp.createStringRequest(Constants.URL_NOHTTP_UPLOAD, RequestMethod.POST);

        // 添加普通参数。
        request.add("user", "yolanda");

        // 上传文件需要实现NoHttp的Binary接口，NoHttp默认实现了FileBinary、InputStreamBinary、ByteArrayBitnary、BitmapBinary。
        FileBinary fileBinary0 = new FileBinary(file);
        /**
         * 监听上传过程，如果不需要监听就不用设置。
         * 第一个参数：what，what和handler的what一样，会在回调被调用的回调你开发者，作用是一个Listener可以监听多个文件的上传状态。
         * 第二个参数： 监听器。
         */
        fileBinary0.setUploadListener(0, null);

        request.add("userHead", fileBinary0);// 添加1个文件

        request(0, request, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                showMessageDialog(R.string.request_succeed, response.get());
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                showMessageDialog(R.string.request_failed, response.getException().getMessage());
            }
        }, false, true);

    }

    /**
     * 网络请求缓存策略-----------start
     */
    private void requestImage() {
        Request<Bitmap> request = NoHttp.createImageRequest(Constants.URL_NOHTTP_IMAGE);
        request.setCacheKey("CacheKeyRequestNetworkFailedReadCacheImage");//
        // 这里的key是缓存数据的主键，默认是url，使用的时候要保证全局唯一，否则会被其他相同url数据覆盖。
        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        //设置为REQUEST_NETWORK_FAILED_READ_CACHE表示请求服务器失败，就返回上次的缓存，如果缓存为空才会请求失败。
        request.setCancelSign(cancelSign);
        request(0, request, imageHttpListener, false, true);
    }


    private HttpListener<Bitmap> imageHttpListener = new HttpListener<Bitmap>() {
        @Override
        public void onSucceed(int what, Response<Bitmap> response) {
            String string = response.isFromCache() ? getString(R.string.request_from_cache) : getString(R.string
                    .request_from_network);
            showImageDialog(string, response.get());
        }

        @Override
        public void onFailed(int what, Response<Bitmap> response) {
            showMessageDialog(R.string.request_failed, response.getException().getMessage());
        }
    };

    /**
     网络请求缓存策略-----------end
     */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelBySign(cancelSign);//取消打了该标记的请求
    }
}

