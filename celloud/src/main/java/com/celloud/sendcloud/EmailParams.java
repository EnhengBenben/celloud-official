package com.celloud.sendcloud;

/**
 * SendCloud邮件模板参数
 * 
 * @author lin
 * @date 2016年6月21日 下午5:41:06
 */
public class EmailParams {

	/**
	 * 用户注册
	 * 
	 * @author lin
	 * @date 2016年6月16日 下午6:03:32
	 */
	public static enum USER_REGISTER {
		url;
	}

	/**
	 * 密码找回
	 * 
	 * @author lin
	 * @date 2016年6月16日 下午6:03:41
	 */
	public static enum PWD_FIND {
		url;
	}

	public static enum CONFIRM_OLD_EMAIL {
		url;
	}

	public static enum CONFIRM_NEW_EMAIL {
		url;
	}

	public static enum PROJECT_SHARE {
		/**
		 * 被共享人
		 */
		userName,
		/**
		 * 共享人
		 */
		shareUserName,
		/**
		 * 项目报告名称
		 */
		dataName,
		/**
		 * 项目报告编号
		 */
		dataKey;
	}

	public static enum RUN_OVER {
		userName, projectName, app,
		/**
		 * 开始时间
		 */
		start,
		/**
		 * 结束时间
		 */
		end;
	}

	public static enum UPLOAD_OVER {
		userName, dataName;
	}

	public static enum EXCEPTION {
		context;
	}

	public static enum FEADBACK {
		/**
		 * 标题
		 */
		title,
		/**
		 * 反馈日期
		 */
		start,
		/**
		 * 用户名
		 */
		userName,
		/**
		 * 正文
		 */
		context,
		/**
		 * 版权日期
		 */
		end;
	}

    /**
     * 用户申请发票
     */
    public static enum INVOICE {
        /**
         * 用户名
         */
        username;
    }

}
