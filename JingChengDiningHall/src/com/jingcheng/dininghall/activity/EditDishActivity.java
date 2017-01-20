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
	private final int IMAGE_CODE = 0;   //IMAGE_CODE�����Զ���
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
		setFinishOnTouchOutside(false);//  ���ô���dialog���Ƿ�finish
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
	 * ��������ʼ������
	 */
	private void init() {
		editdish_et_dishname.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				//s:�仯ǰ�������ַ��� start:�ַ���ʼ��λ�ã� count:�仯ǰ�����ֽ�����after:�仯����ֽ���
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				 //s:�仯��������ַ�
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
				//s:�仯ǰ�������ַ��� start:�ַ���ʼ��λ�ã� count:�仯ǰ�����ֽ�����after:�仯����ֽ���
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				 //s:�仯��������ַ�
				result_dishPrice = arg0.toString();
			}
		});
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.editdish_ok:
			if(result_dishImg == null){
				Toast.makeText(this, "��ѡ���ƷͼƬ", 0).show();
			}else if(result_dishName == null){
				Toast.makeText(this, "���������", 0).show();
			}else if(result_dishPrice == null){
				Toast.makeText(this, "�������Ʒ�۸�", 0).show();
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
	    }else if (resultCode == RESULT_OK) {        //�˴��� RESULT_OK ��ϵͳ�Զ����һ������
//	        img_data = data;
			Uri uri = data.getData();
	      //originUri--ԭʼͼƬ��Uri��
	      //mDestinationUri--Ŀ��ü���ͼƬ�����Uri
	      Uri mDestinationUri = Uri.fromFile(new File(getCacheDir(), new File(uri.toString()).getName()));
	      UCrop uCrop = UCrop.of(uri, mDestinationUri);
	      //��ʼ����
	     // 1.1. ����Ϊ��ͼƬԭʼ��߱���һ��
	      uCrop = uCrop.useSourceImageAspectRatio();
	      //1.2. ��̬������ͼƬ�Ŀ�߱ȣ���������Ϊ1:1
	      uCrop = uCrop.withAspectRatio(4, 3);
	      //2.���òü�����ͼƬ�ĸ�ʽ��
	      UCrop.Options options = new UCrop.Options();
//	      options.setCompressionFormat(Bitmap.CompressFormat.WEBP);
//	      options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
	      options.setCompressionFormat(Bitmap.CompressFormat.PNG);
	      //4.���òü�ͼƬ�����Ʋ������أ�
	      options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.NONE, UCropActivity.ALL);
	      //5.���ý�������ü�ͼƬ�����ߴ磺
//	      options.setMaxBitmapSize(100);
	    //����������ű���
	      options.setMaxScaleMultiplier(5);
	      //����ͼƬ���л�����ʱ�Ķ���
	      options.setImageToCropBoundsAnimDuration(666);
	      //���òü������Ƿ�Ϊ��Բ
	      options.setCircleDimmedLayer(false);
	      //�����Ƿ�չʾ���βü���
	      options.setShowCropFrame(false);
	    //�Ƿ����صײ�������Ĭ����ʾ
	      options.setHideBottomControls(true);
	      //���òü�������ߵĿ��
//	      options.setCropGridStrokeWidth(20);
	      //���òü�������ߵ���ɫ
//	      options.setCropGridColor(Color.GREEN);
	      //�������ߵ�����
//	      options.setCropGridColumnCount(2);
	      //���ú��ߵ�����
//	      options.setCropGridRowCount(1);
	      //��������
	      uCrop.withOptions(options);
	      //�������øղ�����Ĳ�������api�Զ���һЩ����
	      //��������
	      uCrop.start(this);
	        return;
	    }
//		img_data = data;
//		Uri uri = data.getData();
//		Bitmap bitmap = decodeUriAsBitmap(uri);
//		editdish_image.setImageBitmap(bitmap);
	}
	
	/**
     * @param uri��ͼƬ�ı���url��ַ
     * @return Bitmap��
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
