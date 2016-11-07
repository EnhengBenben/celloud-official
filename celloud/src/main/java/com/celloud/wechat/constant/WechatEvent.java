package com.celloud.wechat.constant;

public class WechatEvent {
	/**
	 * 事件的基本参数
	 * 
	 * @author lin
	 * @date 2016年10月20日 下午1:51:36
	 */
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
		 * 消息类型，event
		 */
		public static final String MsgType = "MsgType";
		/**
		 * 事件类型,CLICK/VIEW/scancode_push
		 */
		public static final String Event = "Event";
		/**
		 * 事件KEY值，与自定义菜单接口中KEY值对应
		 */
		public static final String EventKey = "EventKey";
	}

	/**
	 * 点击事件的参数
	 * 
	 * @author lin
	 * @date 2016年10月20日 下午1:44:21
	 */
	public class click extends base {
		/**
		 * Event:click
		 */
	}

	/**
	 * 链接事件的参数
	 * 
	 * @author lin
	 * @date 2016年10月20日 下午1:51:48
	 */
	public class url extends base {
		/**
		 * Event:view <br>
		 * 指菜单ID，如果是个性化菜单，则可以通过这个字段，知道是哪个规则的菜单被点击了。
		 */
		public static final String MenuId = "MenuId";
	}

	/**
	 * 扫码事件的参数
	 * 
	 * @author lin
	 * @date 2016年10月20日 下午1:54:09
	 */
	public class scancode extends base {
		/**
		 * 扫描信息
		 */
		public static final String ScanCodeInfo = "ScanCodeInfo";
		/**
		 * 扫描类型，一般是qrcode
		 */
		public static final String ScanType = "ScanType";
		/**
		 * 扫描结果，即二维码对应的字符串信息
		 */
		public static final String ScanResult = "ScanResult";
	}
}
