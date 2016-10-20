package com.celloud.wechat.constant;

/**
 * 微信消息参数
 * 
 * @author lin
 * @date 2016年10月20日 下午2:37:23
 */
public class WechatMessage {

	class base {
		/**
		 * 开发者微信号
		 */
		public static final String ToUserName = "ToUserName";
		/**
		 * 发送方帐号（一个OpenID）
		 */
		public static final String FromUserName = "FromUserName";
		/**
		 * 消息创建时间 （整型）
		 */
		public static final String CreateTime = "CreateTime";
		/**
		 * text/image
		 */
		public static final String MsgType = "MsgType";
		/**
		 * 消息id，64位整型
		 */
		public static final String MsgId = "MsgId";
	}

	public class text extends base {
		/**
		 * 文本消息内容
		 */
		public static final String Content = "Content";
	}

	public class pic extends base {
		/**
		 * 图片链接（由系统生成）
		 */
		public static final String PicUrl = "PicUrl";
		/**
		 * 图片消息媒体id，可以调用多媒体文件下载接口拉取数据
		 */
		public static final String MediaId = "MediaId";
	}
}
