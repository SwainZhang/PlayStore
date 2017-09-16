package utils;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboDownloadListener;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.Utility;
import com.squareup.picasso.Picasso;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;

import base.MyApplication;


public class ShareHelper implements View.OnClickListener {
	private static final int maxsize = 260000;
	private String title;// 分享标题
	private String desc;// 分享内容
	private String imgUrl;
	private Bitmap thumb;// 分享图片
	private String shareUrl;// 分享链接

	//private static IWXAPI wxApi;// 微信实例
	private static WeiboAuth mWeiboAuth;// 微博Auth实例
	private static IWeiboShareAPI mWeiboShareAPI;
	private Tencent tencentApi;//QQ

	private Context context;

	private SsoHandler mSsoHandler;// 微博授权handler
	private boolean wbInstallCanceled;
	private boolean wbRegisted;

	private ShareDialog msDialog;

	/**
	 * 默认分享
	 * @param context
	 */
	public ShareHelper(Context context) {
		this(context, "【全球纺织网】面料找样采购专业APP平台", "一键拍照找样，海量面料精准匹配，80%报价供应商为十年电商平台认证商家", null, "www.qfc.cn");
	}

	public ShareHelper(Context context, String title, String desc,
					   String imgUrl, String url) {
		super();
		this.context = context;
		if(WeiboConstants.APP_KEY.equals("")){
			throw new IllegalArgumentException("The WeiboConstants is not inits");
		}
		if(WeixinConstants.AppID.equals("")){
			throw new IllegalArgumentException("The WeixinConstants is not inits");
		}
		initWxApi();
		initWbAuth();
		initWbApi();
		initQQ();
		this.title = title;
		this.desc = desc;
		this.imgUrl = imgUrl;
		setThumb(imgUrl);
		shareUrl = url;
	}

	private void initQQ() {
		tencentApi = Tencent.createInstance(QQConstants.AppID, context);
	}

	/**
	 * 分享到QQ
	 */
	private void shareToQQ() {
		final Bundle params = new Bundle();
		params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
		params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
		params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  desc);
		params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  shareUrl);
		params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgUrl);
		params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  context.getResources().getString(R.string.app_name));
		tencentApi.shareToQQ((Activity) context, params, new IUiListener() {

			@Override
			public void onCancel() {

			}

			@Override
			public void onComplete(Object data) {

			}

			@Override
			public void onError(UiError data) {
				Toast.makeText(context, data.errorMessage, Toast.LENGTH_SHORT).show();
			}

		});
	}

	/**
	 * 分享到QQZone
	 */
	private void shareToQzone() {
		final Bundle params = new Bundle();
		params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
		params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
		params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  desc);
		params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  shareUrl);
		ArrayList<String> images = new ArrayList<String>();
		if(imgUrl != null) {
			images.add(imgUrl);
		}
		params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, images);
		params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  context.getResources().getString(R.string.app_name));
		tencentApi.shareToQzone((Activity) context, params, new IUiListener() {

			@Override
			public void onCancel() {

			}

			@Override
			public void onComplete(Object data) {

			}

			@Override
			public void onError(UiError data) {
				Toast.makeText(context, data.errorMessage, Toast.LENGTH_SHORT).show();
			}

		});
	}

	/**
	 */
	private void initWbApi() {
		// 创建微博 SDK 接口实例
		/*if (null != mWeiboShareAPI)
			return;*/
		mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context,
				WeiboConstants.APP_KEY);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(id == R.drawable.share_wechat){
			/*if(imgUrl == null){
			GATracker.sendEvent(TrackerConstants.EVENT_CATEGORY_APP_SHARE, TrackerConstants.EVENT_ACTION_SHARE, TrackerConstants.EVENT_LABEL_WECHAT_SHARE, 1L);
			GATracker.sendSocial(TrackerConstants.SOCIAL_NETWORK_WECHAT, TrackerConstants.SOCIAL_ACTION_SHARE, TrackerConstants.SOCIAL_TARGE_SHARE_APP);
			}else{
				GATracker.sendEvent(TrackerConstants.EVENT_CATEGORY_PRODUCT_DETAIL_SHARE, TrackerConstants.EVENT_ACTION_SHARE, TrackerConstants.EVENT_LABEL_WECHAT_SHARE, 1L);
				GATracker.sendSocial(TrackerConstants.SOCIAL_NETWORK_WECHAT, TrackerConstants.SOCIAL_ACTION_SHARE, TrackerConstants.SOCIAL_TARGE_SHARE_APP);
			}*/
			wechatShare(0);
		}else if (id==R.drawable.share_pengyou){
			/*if(imgUrl == null){
			GATracker.sendEvent(TrackerConstants.EVENT_CATEGORY_APP_SHARE, TrackerConstants.EVENT_ACTION_SHARE, TrackerConstants.EVENT_LABEL_WEIBO_SHARE, 1L);
			GATracker.sendSocial(TrackerConstants.SOCIAL_NETWORK_WECHAT, TrackerConstants.SOCIAL_ACTION_MOMENTS_SHARE, TrackerConstants.SOCIAL_TARGE_SHARE_APP);
			}else{
				GATracker.sendEvent(TrackerConstants.EVENT_CATEGORY_PRODUCT_DETAIL_SHARE, TrackerConstants.EVENT_ACTION_SHARE, TrackerConstants.EVENT_LABEL_WEIBO_SHARE, 1L);
				GATracker.sendSocial(TrackerConstants.SOCIAL_NETWORK_WECHAT, TrackerConstants.SOCIAL_ACTION_MOMENTS_SHARE, TrackerConstants.SOCIAL_TARGE_SHARE_APP);
			}*/
			wechatShare(1);
		}else if (id == R.drawable.share_weibo){
			/*if(imgUrl == null){
			GATracker.sendEvent(TrackerConstants.EVENT_CATEGORY_APP_SHARE, TrackerConstants.EVENT_ACTION_SHARE, TrackerConstants.EVENT_LABEL_MOMENTS_SHARE, 1L);
			GATracker.sendSocial(TrackerConstants.SOCIAL_NETWORK_WEIBO, TrackerConstants.SOCIAL_ACTION_SHARE, TrackerConstants.SOCIAL_TARGE_SHARE_APP);
			}else{
				GATracker.sendEvent(TrackerConstants.EVENT_CATEGORY_PRODUCT_DETAIL_SHARE, TrackerConstants.EVENT_ACTION_SHARE, TrackerConstants.EVENT_LABEL_MOMENTS_SHARE, 1L);
				GATracker.sendSocial(TrackerConstants.SOCIAL_NETWORK_WEIBO, TrackerConstants.SOCIAL_ACTION_SHARE, TrackerConstants.SOCIAL_TARGE_SHARE_APP);
			}*/
			weiboShare();
		}else if(id == R.drawable.share_qq){
			shareToQQ();
		}else if(id == R.drawable.share_qzone){
			shareToQzone();
		}
		if(null!=msDialog&&msDialog.isShowing()) msDialog.dismiss();
	}

	/**
	 */
	private void initWbAuth() {
		/*if (null != mWeiboAuth)
			return;*/
		// 创建微博实例
		mWeiboAuth = new WeiboAuth(context, WeiboConstants.APP_KEY,
				WeiboConstants.REDIRECT_URL, WeiboConstants.SCOPE);
	}

	private void weiboShare() {
		// 获取微博客户端相关信息，如是否安装、支持 SDK 的版本
		boolean isInstalledWeibo = mWeiboShareAPI.isWeiboAppInstalled();
		// int supportApiLevel = mWeiboShareAPI.getWeiboAppSupportAPI();

		// 如果未安装微博客户端，设置下载微博对应的回调
		if (!isInstalledWeibo) {
			mWeiboShareAPI
					.registerWeiboDownloadListener(new IWeiboDownloadListener() {
						@Override
						public void onCancel() {
							Toast.makeText(context, "\t取消下载",
									Toast.LENGTH_SHORT).show();
							wbInstallCanceled = true;
						}
					});
		}
		// 注册到新浪微博
		if(!wbRegisted&&!wbInstallCanceled)wbRegisted=mWeiboShareAPI.registerApp();
		if(!isInstalledWeibo&&!wbInstallCanceled) return;
		//授权
		mAccessToken = AccessTokenKeeper.readAccessToken(context);
		if (mAccessToken != null && mAccessToken.isSessionValid()) {// 已经授权
			// context.startActivity(new Intent(context,WBShareActivity.class));
			if (mWeiboShareAPI.checkEnvironment(true)&&mWeiboShareAPI.isWeiboAppSupportAPI()) {
				int supportApiLevel = mWeiboShareAPI.getWeiboAppSupportAPI();
				sendWbShareMsg(supportApiLevel);
			}
		} else {
			// 去授权
			if (!(context instanceof Activity))
				return;
			try{
				mSsoHandler = new SsoHandler((Activity) context, mWeiboAuth);
				mSsoHandler.authorize(new AuthListener());
			}catch(Exception e){
				Toast.makeText(context,"Permission denied" ,Toast.LENGTH_SHORT).show();
			}

		}
	}

	private void sendWbShareMsg(int supportApiLevel) {
		if (supportApiLevel >= 10351 /* ApiUtils.BUILD_INT_VER_2_2 */) {
			Log.d("", "zyl weibo sendWbShareMsg supportApiLevel 10351");
			// 1. 初始化微博的分享消息
			WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
			// 2. 初始化从第三方到微博的消息请求
			SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
			weiboMessage.textObject = getTextObj();
			weiboMessage.imageObject = getImageObj();
			weiboMessage.mediaObject = getWebpageObj();
			request.transaction = String.valueOf(System.currentTimeMillis());
			request.multiMessage = weiboMessage;
			// 3. 发送请求消息到微博，唤起微博分享界面
			mWeiboShareAPI.sendRequest(request);
		} else {
			// 1. 初始化微博的分享消息
			WeiboMessage weiboMessage = new WeiboMessage();
			// 2. 初始化从第三方到微博的消息请求
			SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
			weiboMessage.mediaObject = getWebpageObj();
			weiboMessage.mediaObject = getImageObj();
			weiboMessage.mediaObject = getTextObj();
			// 用transaction唯一标识一个请求
			request.transaction = String.valueOf(System.currentTimeMillis());
			request.message = weiboMessage;
			// 3. 发送请求消息到微博，唤起微博分享界面
			mWeiboShareAPI.sendRequest(request);
		}
	}

	/**
	 * 创建文本消息对象。
	 *
	 * @return 文本消息对象。
	 */
	private TextObject getTextObj() {
		Log.d("", "zyl TextObject " + desc);
		TextObject textObject = new TextObject();
		textObject.text = desc;//
		return textObject;
	}
	/**
	 * 创建图片消息对象。
	 *
	 * @return 图片消息对象。
	 */
	private ImageObject getImageObj() {

		ImageObject imageObject = new ImageObject();
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) mImageView.getDrawable();
		imageObject.setImageObject(thumb);//
		return imageObject;
	}
	/**
	 * 创建多媒体（网页）消息对象。
	 *
	 * @return 多媒体（网页）消息对象。
	 */
	private WebpageObject getWebpageObj() {
		if(shareUrl==null) return null;//0529
		WebpageObject mediaObject = new WebpageObject();
		mediaObject.identify = Utility.generateGUID();
		mediaObject.title = title;
		mediaObject.description = desc;

		// 设置 Bitmap 类型的图片到视频对象里
		mediaObject.setThumbImage(thumb);
		mediaObject.actionUrl = shareUrl;
		mediaObject.defaultText = desc+"-";
		return mediaObject;
	}


	/**
	 *
	 */
	private void initWxApi() {
		if (null != wxApi)
			return;
		wxApi = WXAPIFactory
				.createWXAPI(MyApplication.app(), WeixinConstants.AppID, true);
		wxApi.registerApp(WeixinConstants.AppID);
	}

	public final void setThumb(String url) {
        if(url != null && !CommonUtils.isBlank(url)) {
			Picasso.with(CommonUtils.getApplicationContext()).load(url).into();
            ImageLoaderHelper.imageLoader.loadImage(url, new ImageLoadingListener() {

                @Override
                public void onLoadingStarted(String imageUri, View view) {
                }

                @Override
                public void onLoadingFailed(String imageUri, View view,
                                            FailReason failReason) {
                    thumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);

                    thumb = BitmapUtil.resizeBitmap(thumb, CommonUtils.dip2px(context, 50));

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    thumb = loadedImage;
                    thumb = BitmapUtil.resizeBitmap(thumb, CommonUtils.dip2px(context, 50));
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
        } else {
            thumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
            thumb = BitmapUtil.resizeBitmap(thumb, CommonUtils.dip2px(context, 50));
        }
	}

	/**
	 * 微博认证授权回调类。 1. SSO 授权时，需要在  中调用
	 * {@link SsoHandler#authorizeCallBack} 后， 该回调才会被执行。 2. 非 SSO
	 * 授权时，当授权结束后，该回调就会被执行。 当授权成功后，请保存该 access_token、expires_in、uid 等信息到
	 * SharedPreferences 中。
	 */
	class AuthListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {
			// 从 Bundle 中解析 Token
			Log.e("", "zyl values"+values);
			mAccessToken = Oauth2AccessToken.parseAccessToken(values);
			if (mAccessToken.isSessionValid()) {
				// 显示 Token
				// updateTokenView(false);//taotao 不用了

				// 保存 Token 到 SharedPreferences
				AccessTokenKeeper.writeAccessToken(context, mAccessToken);
				Toast.makeText(context, "授权成功", Toast.LENGTH_SHORT).show();
				weiboShare();//0529
			} else {
				// 以下几种情况，您会收到 Code：
				// 1. 当您未在平台上注册的应用程序的包名与签名时；
				// 2. 当您注册的应用程序包名与签名不正确时；
				// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
				String code = values.getString("code");
				String message = "授权失败";
				if (!TextUtils.isEmpty(code)) {
					message = message + "\nObtained the code: " + code;
				}
				Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public void onCancel() {
			Toast.makeText(context, "取消授权", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(context, "Auth exception : " + e.getMessage(),
					Toast.LENGTH_LONG).show();
		}
	}

	private Oauth2AccessToken mAccessToken;

	/**
	 * 微信分享 （这里仅提供一个分享网页的示例，其它请参看官网示例代码）
	 *
	 * @param flag
	 *            (0:分享到微信好友，1：分享到微信朋友圈)
	 */
	private void wechatShare(int flag) {
		if(!wxApi.isWXAppInstalled()){
			DialogLoaderHelper.showToast(context, "对不起,您没有安装微信app");
		}

		WXWebpageObject webpage = new WXWebpageObject();
		if(null==shareUrl||shareUrl.isEmpty()) shareUrl = "http://qfc.cn";
		webpage.webpageUrl = shareUrl;
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = title;
		msg.description = desc;
		// 这里替换一张自己工程里的图片资源
		// Bitmap thumb =
		msg.setThumbImage(thumb);

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = String.valueOf(System.currentTimeMillis());
		req.message = msg;
		req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession
				: SendMessageToWX.Req.WXSceneTimeline;
		wxApi.sendReq(req);
	}

	public Dialog showShareDialog() {
		msDialog = new ShareDialog(context);
		msDialog.setItemListener(this);
		msDialog.show();
		return msDialog;
	}

	/**
	 * 在调用shareHelper的activity中重载onActivityREsult方法并调用此方法
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	public void callback(int requestCode, int resultCode, Intent data ) {
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	public static class WeiboConstants{
		public static String APP_KEY = "";
		public static String REDIRECT_URL = "";
		public static String SCOPE = "";
	}

	public static class WeixinConstants{
		public static String AppID = "";
	}

	public static class QQConstants{
		public static String AppID = "";
	}


}
