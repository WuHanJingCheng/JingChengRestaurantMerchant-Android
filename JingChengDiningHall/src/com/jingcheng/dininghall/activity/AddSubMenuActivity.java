package com.jingcheng.dininghall.activity;

import java.io.File;
import java.io.FileNotFoundException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jingcheng.dininghall.bean.Type;
import com.jingcheng.jingchengdininghall.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

public class AddSubMenuActivity extends BaseActivity implements OnClickListener {
	private TextView add_submenu_ok, add_submenu_cancel;
	private ImageView add_submenu_iv;

	private final String IMAGE_TYPE = "image/*";
//	private final int IMAGE_CODE = 1;   //IMAGE_CODE�����Զ���
	private ImageView icon, icon_down;
	private Type type = null;
	private EditText editdish_et_dishname;
	private int request = 0;
	private boolean run = true;
	private DisplayImageOptions options  = new DisplayImageOptions.Builder()
//    .showStubImage(R.drawable.img_2)  //���ü���ʱ��ͼƬ
//    .showImageOnFail(R.drawable.img_7) //���ü���ʧ�ܵ�ͼƬ
    .cacheInMemory()//���ü��ص�ͼƬ�������ڴ���
    .cacheOnDisc()//���ü��ص�ͼƬ������SD��
//    .displayer(new RoundedBitmapDisplayer(20))//���ó�Բ��ͼƬ
    .bitmapConfig(Bitmap.Config.RGB_565)  
    .build();
	private Uri result_icon = null;
	private Uri result_icon_down = null;
	private String result_subMenuName = null;
	private Intent result_Intent = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_submenu);
		setFinishOnTouchOutside(false);//  ���ô���dialog���Ƿ�finish
		add_submenu_ok = (TextView) findViewById(R.id.add_submenu_ok);
		add_submenu_cancel = (TextView) findViewById(R.id.add_submenu_cancel);
		editdish_et_dishname = (EditText) findViewById(R.id.editdish_et_dishname);
		icon = (ImageView) findViewById(R.id.icon);
		icon_down = (ImageView) findViewById(R.id.icon_down);
		add_submenu_ok.setOnClickListener(this);
		add_submenu_cancel.setOnClickListener(this);
		icon.setOnClickListener(this);
		icon_down.setOnClickListener(this);
		Intent intent = getIntent();
		type  = (Type) intent.getSerializableExtra("data");
		if(type != null){
			init();
		}
		
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
				result_subMenuName = arg0.toString();
			}
		});
	}

	private void init() {
		editdish_et_dishname.setText(type.getMenuName());
		ImageLoader.getInstance().displayImage(type.getPictureUrl(), icon, options); 
		ImageLoader.getInstance().displayImage(type.getPictureUrlSelected(), icon_down, options); 
	
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_submenu_ok:
			if(result_icon == null){
				Toast.makeText(this, "��ѡ�����ͼ��", 0).show();
			}else if(result_icon_down == null){
				Toast.makeText(this, "��ѡ�����ѡ��ͼ��", 0).show();
			}else if(result_subMenuName == null){
				Toast.makeText(this, "�������������", 0).show();
			}else{
				result_Intent = new Intent()
				.putExtra("icon", result_icon.toString())
				.putExtra("icon_down", result_icon_down.toString())
				.putExtra("subMenuName", result_subMenuName); 
				setResult(9999,result_Intent);
				finish();
			}
			break;
		case R.id.add_submenu_cancel:
			finish();
			break;
		case R.id.icon:
			run = true;
			Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
			getAlbum.setType(IMAGE_TYPE);
			startActivityForResult(getAlbum, 100);
			request = 100;
			break;
		case R.id.icon_down:
			run = true;
			Intent getAlbum1 = new Intent(Intent.ACTION_GET_CONTENT);
			getAlbum1.setType(IMAGE_TYPE);
			startActivityForResult(getAlbum1, 200);
			request = 200;
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
//		Uri uri = data.getData();
//		Bitmap bitmap = decodeUriAsBitmap(uri);
//		add_submenu_iv.setImageBitmap(bitmap);
		if(resultCode == RESULT_OK && run){
			uCrop(data.getData());
			run = false;
		}else if(request == 100 && resultCode == RESULT_OK){
			final Uri resultUri = UCrop.getOutput(data);
	        Intent intent = new Intent();
	        intent.setData(resultUri);
			Bitmap bitmap = decodeUriAsBitmap(resultUri);
			icon.setImageBitmap(bitmap);
			result_icon = resultUri;
		}else if(request == 200 && resultCode == RESULT_OK){
			final Uri resultUri = UCrop.getOutput(data);
	        Intent intent = new Intent();
	        intent.setData(resultUri);
			Bitmap bitmap = decodeUriAsBitmap(resultUri);
			icon_down.setImageBitmap(bitmap);
			result_icon_down = resultUri;
		}
	}
	private void uCrop(Uri uri){
//		Uri uri = data.getData();
	      //originUri--ԭʼͼƬ��Uri��
		File file = new File(uri.toString());
	      //mDestinationUri--Ŀ��ü���ͼƬ�����Uri
	      Uri mDestinationUri = Uri.fromFile(new File(getCacheDir(), file.getName()));
	      UCrop uCrop = UCrop.of(uri, mDestinationUri);
	      //��ʼ����
	     // 1.1. ����Ϊ��ͼƬԭʼ��߱���һ��
	      uCrop = uCrop.useSourceImageAspectRatio();
	      //1.2. ��̬������ͼƬ�Ŀ�߱ȣ���������Ϊ1:1
	      uCrop = uCrop.withAspectRatio(1,1);
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
