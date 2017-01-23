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
//	private final int IMAGE_CODE = 1;   //IMAGE_CODE任意自定义
	private ImageView icon, icon_down;
	private Type type = null;
	private EditText editdish_et_dishname;
	private int request = 0;
	private boolean run = true;
	private DisplayImageOptions options  = new DisplayImageOptions.Builder()
//    .showStubImage(R.drawable.img_2)  //设置加载时的图片
//    .showImageOnFail(R.drawable.img_7) //设置加载失败的图片
    .cacheInMemory()//设置加载的图片缓存在内存中
    .cacheOnDisc()//设置加载的图片缓存在SD卡
//    .displayer(new RoundedBitmapDisplayer(20))//设置成圆角图片
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
		setFinishOnTouchOutside(false);//  设置触碰dialog外是否finish
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
				//s:变化前的所有字符； start:字符开始的位置； count:变化前的总字节数；after:变化后的字节数
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				 //s:变化后的所有字符
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
				Toast.makeText(this, "请选择分类图标", 0).show();
			}else if(result_icon_down == null){
				Toast.makeText(this, "请选择分类选择图标", 0).show();
			}else if(result_subMenuName == null){
				Toast.makeText(this, "请输入分类名称", 0).show();
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
	      //originUri--原始图片的Uri；
		File file = new File(uri.toString());
	      //mDestinationUri--目标裁剪的图片保存的Uri
	      Uri mDestinationUri = Uri.fromFile(new File(getCacheDir(), file.getName()));
	      UCrop uCrop = UCrop.of(uri, mDestinationUri);
	      //开始设置
	     // 1.1. 设置为何图片原始宽高比列一样
	      uCrop = uCrop.useSourceImageAspectRatio();
	      //1.2. 动态的设置图片的宽高比，这里设置为1:1
	      uCrop = uCrop.withAspectRatio(1,1);
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
