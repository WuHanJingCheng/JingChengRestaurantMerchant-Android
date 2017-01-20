package com.jingcheng.dininghall.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jingcheng.dininghall.utils.BlobHelp;
import com.jingcheng.jingchengdininghall.R;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

public class EditDishActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = "EditDishActivity";
	private final String IMAGE_TYPE = "image/*";
	private final int IMAGE_CODE = 0;   //IMAGE_CODE任意自定义
	private TextView editdish_ok;
	private TextView editdish_cancel;
	private String title;
	private TextView editdish_title;
	private ImageView editdish_image;
	private Intent img_data;
	private Uri result_dishImg = null;
	private String result_dishName = null;
	private String result_dishPrice = null;
	
	public static final String storageConnectionString =
	        "DefaultEndpointsProtocol=https;"
	        + "AccountName=jingchengrestaurant;"
	        + "AccountKey=vgPX6U7Z3+r4X3CRqoX/bfwxaqAZwDccu1EzLKyAHfvtheHwypGy9o7ulhCLE/9+CNJqTPq8r4uGQ29/TWnN/w==";
	private File file;
	private EditText editdish_et_dishname;
	private EditText editdish_et_price;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editdish);
		setFinishOnTouchOutside(false);//  设置触碰dialog外是否finish
		editdish_ok = (TextView) findViewById(R.id.editdish_ok);
		editdish_cancel = (TextView) findViewById(R.id.editdish_cancel);
		editdish_title = (TextView) findViewById(R.id.editdish_title);
		editdish_image = (ImageView) findViewById(R.id.editdish_image);
		editdish_et_dishname = (EditText) findViewById(R.id.editdish_et_dishname);
		editdish_et_price = (EditText) findViewById(R.id.editdish_et_price);
		
		editdish_ok.setOnClickListener(this);
		editdish_cancel.setOnClickListener(this);
		editdish_image.setOnClickListener(this);
		init();
	}
	/**
	 * 
	 * 描述：初始化标题
	 */
	private void init() {
		editdish_et_dishname.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				//s:变化前的所有字符； start:字符开始的位置； count:变化前的总字节数；after:变化后的字节数
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				 //s:变化后的所有字符
				result_dishName = arg0.toString();
			}
		});
		
			editdish_et_price.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				//s:变化前的所有字符； start:字符开始的位置； count:变化前的总字节数；after:变化后的字节数
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				 //s:变化后的所有字符
				result_dishPrice = arg0.toString();
			}
		});
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.editdish_ok:
			if(result_dishImg == null){
				Toast.makeText(this, "请选择菜品图片", 0).show();
			}else if(result_dishName == null){
				Toast.makeText(this, "请输入菜名", 0).show();
			}else if(result_dishPrice == null){
				Toast.makeText(this, "请输入菜品价格", 0).show();
			}else{
				img_data = new Intent()
				.putExtra("dishName", result_dishName)
				.putExtra("dishPrice", result_dishPrice)
				.putExtra("dishImg", result_dishImg.toString());
				setResult(8888, img_data);
				finish();
			}
			break;
		case R.id.editdish_cancel:
			finish();
			break;
		case R.id.editdish_image:
			/*
			 * private Uri result_dishImg = null;
	private String result_dishName = null;
	private String result_dishPrice = null;
			 * 
			 */
				
				Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
				getAlbum.setType(IMAGE_TYPE);
				startActivityForResult(getAlbum, IMAGE_CODE);
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
	        final Uri resultUri = UCrop.getOutput(data);
	        Intent intent = new Intent();
	        intent.setData(resultUri);
//	        img_data = intent;
	        result_dishImg = resultUri;
			Bitmap bitmap = decodeUriAsBitmap(resultUri);
			editdish_image.setImageBitmap(bitmap);
	    } else if (resultCode == UCrop.RESULT_ERROR) {
	        final Throwable cropError = UCrop.getError(data);
	    }else if (resultCode == RESULT_OK) {        //此处的 RESULT_OK 是系统自定义得一个常量
//	        img_data = data;
			Uri uri = data.getData();
	      //originUri--原始图片的Uri；
	      //mDestinationUri--目标裁剪的图片保存的Uri
	      Uri mDestinationUri = Uri.fromFile(new File(getCacheDir(), new File(uri.toString()).getName()));
	      UCrop uCrop = UCrop.of(uri, mDestinationUri);
	      //开始设置
	     // 1.1. 设置为何图片原始宽高比列一样
	      uCrop = uCrop.useSourceImageAspectRatio();
	      //1.2. 动态的设置图片的宽高比，这里设置为1:1
	      uCrop = uCrop.withAspectRatio(4, 3);
	      //2.设置裁剪出来图片的格式：
	      UCrop.Options options = new UCrop.Options();
//	      options.setCompressionFormat(Bitmap.CompressFormat.WEBP);
//	      options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
	      options.setCompressionFormat(Bitmap.CompressFormat.PNG);
	      //4.设置裁剪图片的手势操作开关：
	      options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.NONE, UCropActivity.ALL);
	      //5.设置将被载入裁剪图片的最大尺寸：
//	      options.setMaxBitmapSize(100);
	    //设置最大缩放比例
	      options.setMaxScaleMultiplier(5);
	      //设置图片在切换比例时的动画
	      options.setImageToCropBoundsAnimDuration(666);
	      //设置裁剪窗口是否为椭圆
	      options.setCircleDimmedLayer(false);
	      //设置是否展示矩形裁剪框
	      options.setShowCropFrame(false);
	    //是否隐藏底部容器，默认显示
	      options.setHideBottomControls(true);
	      //设置裁剪框横竖线的宽度
//	      options.setCropGridStrokeWidth(20);
	      //设置裁剪框横竖线的颜色
//	      options.setCropGridColor(Color.GREEN);
	      //设置竖线的数量
//	      options.setCropGridColumnCount(2);
	      //设置横线的数量
//	      options.setCropGridRowCount(1);
	      //结束设置
	      uCrop.withOptions(options);
	      //在这里用刚才上面的参数设置api自定义一些属性
	      //结束设置
	      uCrop.start(this);
	        return;
	    }
//		img_data = data;
//		Uri uri = data.getData();
//		Bitmap bitmap = decodeUriAsBitmap(uri);
//		editdish_image.setImageBitmap(bitmap);
	}
	
	/**
     * @param uri：图片的本地url地址
     * @return Bitmap；
     */
    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
	
	
}
