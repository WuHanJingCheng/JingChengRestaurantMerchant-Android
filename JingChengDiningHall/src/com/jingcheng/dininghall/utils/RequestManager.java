package com.jingcheng.dininghall.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


public class RequestManager {
	
	private OkHttpClient mOkHttpClient;//okHttpClient 实例
	private Handler okHttpHandler;//全局处理子线程和M主线程通信
	
    private static final String TAG = "RequestManager";
    private static volatile RequestManager mInstance;//单例引用
    public static final int TYPE_GET = 0;//get请求
    public static final int TYPE_POST = 1;//post请求参数为json
    public static final int TYPE_PUT = 2;//PUT请求参数为json
    public static final int TYPE_DELETE = 3;//DELETE请求参数为json
	/**
	 * 根路径
	 */
	public static final String JINGCHENG = "http://jingchengrestaurant.azurewebsites.net/";
	
	
	/**
	 * 描述：获取所有菜单分类<br>
	 * 请求方式：GET<br>
	 * url: /restaurant/{restaurantId}/menu/subMenu<br>
	 * Parameters：restaurantId   餐厅号  	 必须 	 integer  path中添加<br>
	 * 				searchName 	 查询条件	非必须	String  query<br>
	 * 
	 */
	public static final String SUBMENU = "/restaurant/1/menu/subMenu";
	
	
	/**
     * 初始化RequestManager<br>
     */
    public RequestManager(Context context) {
        //初始化OkHttpClient
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS)//设置写入超时时间
                .build();
        //初始化Handler
        okHttpHandler = new Handler(context.getMainLooper());
    }
	
    /**
     * 获取单例引用<br>
     * 运用双重检查锁实现单例<br>
     * @return
     */
    public static RequestManager getInstance(Context context) {
        RequestManager inst = mInstance;
        if (inst == null) {
            synchronized (RequestManager.class) {
                inst = mInstance;
                if (inst == null) {
                    inst = new RequestManager(context.getApplicationContext());
                    mInstance = inst;
                }
            }
        }
        return inst;
    }
	
    
    /**
     *  okHttp同步请求统一入口<br>
     * @param actionUrl  接口地址<br>
     * @param requestType 请求类型  0表示GET请求，1表示POST请求<br>
     * @param paramsMap   请求参数<br>
     */
    public void requestSyn(String actionUrl, int requestType, HashMap<String, String> paramsMap) {
        switch (requestType) {
            case TYPE_GET:
                requestGetBySyn(actionUrl, paramsMap);
                break;
            case TYPE_POST:
                requestPostBySyn(actionUrl, paramsMap);
                break;
        }
    }
    
    /**
     * okHttp get同步请求<br>
     */
    private void requestGetBySyn(String actionUrl, HashMap<String, String> paramsMap) {
        try {
            //创建一个请求
            Request request = new Request.Builder().url(actionUrl).build();
            //创建一个Call
            final Call call = mOkHttpClient.newCall(request);
            //执行请求
            final Response response = call.execute();
            response.body().string();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
    
    /**
     * okHttp post同步请求<br>
     * @param actionUrl  接口地址<br>
     */
    private void requestPostBySyn(String actionUrl, HashMap<String, String> paramsMap) {
        try {
            //创建一个请求实体对象 RequestBody
            RequestBody body = new FormBody.Builder().build();
            
            
            //创建一个请求
            final Request request = new Request.Builder().url(actionUrl).post(body).build();
            //创建一个Call
            final Call call = mOkHttpClient.newCall(request);
            //执行请求
            Response response = call.execute();
            //请求执行成功
            if (response.isSuccessful()) {
                //获取返回数据 可以是String，bytes ,byteStream
                Log.e(TAG, "response ----->" + response.body().string());
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
    
    
    
    /**
     * okHttp异步请求统一入口<br>
     * @param actionUrl   接口地址<br>
     * @param requestType 请求类型  0表示GET请求，1表示POST请求, 2表示put请求, 3表示delete请求<br>
     * @param json   请求参数<br>
     * @param callBack 请求返回数据回调<br>
     **/
    public Call requestAsyn(String actionUrl, int requestType, ReqCallBack callBack, String json) {
        Call call = null;
        switch (requestType) {
            case TYPE_GET://0 get请求
                call = requestGetByAsyn(actionUrl, callBack);
                break;
            case TYPE_POST://1 post请求
                call = requestPostByAsyn(actionUrl, callBack, json);
                break;
            case TYPE_PUT://2 put请求
            	call = requestPutByAsyn(actionUrl, callBack, json);
            	break;
            case TYPE_DELETE://3 delete请求
            	call = requestDeleteByAsyn(actionUrl, callBack, json);
            	break;
        }
        return call;
    }
    
    /**
     * okHttp delete异步请求<br>
     * @param actionUrl 接口地址<br>
     * @param callBack 请求返回数据回调<br>
     * @param json 请求参数<br>
     * @return
     */
    private Call requestDeleteByAsyn(String actionUrl, final ReqCallBack callBack,String json){
    	RequestBody body = new FormBody.Builder().build().create(MediaType.parse("application/json;charset=UTF-8"), json);
    	final Request request = new Request.Builder().url(actionUrl).delete(body).build();
    	final Call call = mOkHttpClient.newCall(request);
    	call.enqueue(new Callback() {
    		
    		@Override
    		public void onResponse(Call call, Response response) throws IOException {
    			if (response.isSuccessful()) {
    				String string = response.body().string();
    				Log.e(TAG, "response ----->" + string);
    				successCallBack(string, callBack);
    			} else {
    				failedCallBack(String.valueOf(response.code()), callBack);
    			}
    		}
    		
    		@Override
    		public void onFailure(Call call, IOException e) {
    			failedCallBack("访问失败", callBack);
    			Log.e(TAG, e.toString());
    		}
    	});
    	
    	return null;
    	
    }
    /**
     * okHttp put异步请求<br>
     * @param actionUrl 接口地址<br>
     * @param callBack 请求返回数据回调<br>
     * @param json 请求参数<br>
     * @return
     */
    private Call requestPutByAsyn(String actionUrl, final ReqCallBack callBack,String json){
    	RequestBody body = new FormBody.Builder().build().create(MediaType.parse("application/json;charset=UTF-8"), json);
    	final Request request = new Request.Builder().url(actionUrl).put(body).build();
    	final Call call = mOkHttpClient.newCall(request);
    	call.enqueue(new Callback() {
    		
    		@Override
    		public void onResponse(Call call, Response response) throws IOException {
    			if (response.isSuccessful()) {
    				String string = response.body().string();
    				Log.e(TAG, "response ----->" + string);
    				successCallBack(string, callBack);
    			} else {
    				failedCallBack(String.valueOf(response.code()), callBack);
    			}
    		}
    		
    		@Override
    		public void onFailure(Call call, IOException e) {
    			failedCallBack("访问失败", callBack);
    			Log.e(TAG, e.toString());
    		}
    	});
    	
    	return null;
    	
    }
    
    /**
     * okHttp get异步请求<br>
     * @param actionUrl 接口地址<br>
     * @param paramsMap 请求参数<br>
     * @param callBack 请求返回数据回调<br>
     * @return
     */
    private Call requestGetByAsyn(String actionUrl, final ReqCallBack callBack) {
        StringBuilder tempParams = new StringBuilder();
        try {
            
            final Request request = new Request.Builder().url(actionUrl).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack("访问失败", callBack);
                    Log.e(TAG, e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        Log.e(TAG, "response ----->" + string);
                        successCallBack(string, callBack);
                    } else {
                        failedCallBack(String.valueOf(response.code()), callBack);
                    }
                }
            });
            return call;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }
    
    /**
     * okHttp post异步请求<br>
     * @param actionUrl 接口地址<br>
     * @param paramsMap 请求参数<br>
     * @param callBack 请求返回数据回调<br>
     * @return
     */
    private Call requestPostByAsyn(String actionUrl, final ReqCallBack callBack, String json) {
        try {
//            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, params);
            RequestBody body = new FormBody.Builder().build().create(MediaType.parse("application/json"), json);
            final Request request = new Request.Builder().url(actionUrl).post(body).build();
            Request.Builder builder = new Request.Builder();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    failedCallBack("访问失败", callBack);
                    Log.e(TAG, e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        Log.e(TAG, "response ----->" + string);
                        successCallBack(string, callBack);
                    } else {
                        failedCallBack(String.valueOf(response.code()), callBack);
                    }
                }
            });
            return call;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }
    
//    /**
//     * 统一为请求添加头信息<br>
//     * @return
//     */
//    private Request.Builder addHeaders(HashMap<String, String> paramsMap) {
//    	Request.Builder builder = new Request.Builder();
//    	for (String key : paramsMap.keySet()) {
//		   System.out.println("key= "+ key + " and value= " + paramsMap.get(key));
//		   builder.addHeader(key, paramsMap.get(key));
//		}
//        return builder;
//    }
    /**
     * 接口ReqCallBack实现<br>
     * @author Administrator
     *
     * @param <T>
     */
    public interface ReqCallBack {
        /**
         * 响应成功
         */
         void onReqSuccess(String result);

        /**
         * 响应失败
         */
         void onReqFailed(String errorMsg);
    }
    
    /**
     * 统一同意处理成功信息<br>
     * @param result<br>
     * @param callBack<br>
     */
    private void successCallBack(final String result, final ReqCallBack callBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onReqSuccess(result);
                }
            }
        });
    }
    
    /**
     * 统一处理失败信息<br>
     * @param errorMsg<br>
     * @param callBack<br>
     * @param <T><br>
     */
    private void failedCallBack(final String errorMsg, final ReqCallBack callBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onReqFailed(errorMsg);
                }
            }
        });
    }
    
}
