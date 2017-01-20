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
	
	private OkHttpClient mOkHttpClient;//okHttpClient ʵ��
	private Handler okHttpHandler;//ȫ�ִ������̺߳�M���߳�ͨ��
	
    private static final String TAG = "RequestManager";
    private static volatile RequestManager mInstance;//��������
    public static final int TYPE_GET = 0;//get����
    public static final int TYPE_POST = 1;//post�������Ϊjson
    public static final int TYPE_PUT = 2;//PUT�������Ϊjson
    public static final int TYPE_DELETE = 3;//DELETE�������Ϊjson
	/**
	 * ��·��
	 */
	public static final String JINGCHENG = "http://jingchengrestaurant.azurewebsites.net/";
	
	
	/**
	 * ��������ȡ���в˵�����<br>
	 * ����ʽ��GET<br>
	 * url: /restaurant/{restaurantId}/menu/subMenu<br>
	 * Parameters��restaurantId   ������  	 ���� 	 integer  path�����<br>
	 * 				searchName 	 ��ѯ����	�Ǳ���	String  query<br>
	 * 
	 */
	public static final String SUBMENU = "/restaurant/1/menu/subMenu";
	
	
	/**
     * ��ʼ��RequestManager<br>
     */
    public RequestManager(Context context) {
        //��ʼ��OkHttpClient
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)//���ó�ʱʱ��
                .readTimeout(10, TimeUnit.SECONDS)//���ö�ȡ��ʱʱ��
                .writeTimeout(10, TimeUnit.SECONDS)//����д�볬ʱʱ��
                .build();
        //��ʼ��Handler
        okHttpHandler = new Handler(context.getMainLooper());
    }
	
    /**
     * ��ȡ��������<br>
     * ����˫�ؼ����ʵ�ֵ���<br>
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
     *  okHttpͬ������ͳһ���<br>
     * @param actionUrl  �ӿڵ�ַ<br>
     * @param requestType ��������  0��ʾGET����1��ʾPOST����<br>
     * @param paramsMap   �������<br>
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
     * okHttp getͬ������<br>
     */
    private void requestGetBySyn(String actionUrl, HashMap<String, String> paramsMap) {
        try {
            //����һ������
            Request request = new Request.Builder().url(actionUrl).build();
            //����һ��Call
            final Call call = mOkHttpClient.newCall(request);
            //ִ������
            final Response response = call.execute();
            response.body().string();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
    
    /**
     * okHttp postͬ������<br>
     * @param actionUrl  �ӿڵ�ַ<br>
     */
    private void requestPostBySyn(String actionUrl, HashMap<String, String> paramsMap) {
        try {
            //����һ������ʵ����� RequestBody
            RequestBody body = new FormBody.Builder().build();
            
            
            //����һ������
            final Request request = new Request.Builder().url(actionUrl).post(body).build();
            //����һ��Call
            final Call call = mOkHttpClient.newCall(request);
            //ִ������
            Response response = call.execute();
            //����ִ�гɹ�
            if (response.isSuccessful()) {
                //��ȡ�������� ������String��bytes ,byteStream
                Log.e(TAG, "response ----->" + response.body().string());
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
    
    
    
    /**
     * okHttp�첽����ͳһ���<br>
     * @param actionUrl   �ӿڵ�ַ<br>
     * @param requestType ��������  0��ʾGET����1��ʾPOST����, 2��ʾput����, 3��ʾdelete����<br>
     * @param json   �������<br>
     * @param callBack ���󷵻����ݻص�<br>
     **/
    public Call requestAsyn(String actionUrl, int requestType, ReqCallBack callBack, String json) {
        Call call = null;
        switch (requestType) {
            case TYPE_GET://0 get����
                call = requestGetByAsyn(actionUrl, callBack);
                break;
            case TYPE_POST://1 post����
                call = requestPostByAsyn(actionUrl, callBack, json);
                break;
            case TYPE_PUT://2 put����
            	call = requestPutByAsyn(actionUrl, callBack, json);
            	break;
            case TYPE_DELETE://3 delete����
            	call = requestDeleteByAsyn(actionUrl, callBack, json);
            	break;
        }
        return call;
    }
    
    /**
     * okHttp delete�첽����<br>
     * @param actionUrl �ӿڵ�ַ<br>
     * @param callBack ���󷵻����ݻص�<br>
     * @param json �������<br>
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
    			failedCallBack("����ʧ��", callBack);
    			Log.e(TAG, e.toString());
    		}
    	});
    	
    	return null;
    	
    }
    /**
     * okHttp put�첽����<br>
     * @param actionUrl �ӿڵ�ַ<br>
     * @param callBack ���󷵻����ݻص�<br>
     * @param json �������<br>
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
    			failedCallBack("����ʧ��", callBack);
    			Log.e(TAG, e.toString());
    		}
    	});
    	
    	return null;
    	
    }
    
    /**
     * okHttp get�첽����<br>
     * @param actionUrl �ӿڵ�ַ<br>
     * @param paramsMap �������<br>
     * @param callBack ���󷵻����ݻص�<br>
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
                    failedCallBack("����ʧ��", callBack);
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
     * okHttp post�첽����<br>
     * @param actionUrl �ӿڵ�ַ<br>
     * @param paramsMap �������<br>
     * @param callBack ���󷵻����ݻص�<br>
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
                    failedCallBack("����ʧ��", callBack);
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
//     * ͳһΪ�������ͷ��Ϣ<br>
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
     * �ӿ�ReqCallBackʵ��<br>
     * @author Administrator
     *
     * @param <T>
     */
    public interface ReqCallBack {
        /**
         * ��Ӧ�ɹ�
         */
         void onReqSuccess(String result);

        /**
         * ��Ӧʧ��
         */
         void onReqFailed(String errorMsg);
    }
    
    /**
     * ͳһͬ�⴦��ɹ���Ϣ<br>
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
     * ͳһ����ʧ����Ϣ<br>
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
