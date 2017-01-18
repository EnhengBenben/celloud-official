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
		home, url;
	}

	/**
	 * 密码找回
	 * 
	 * @author lin
	 * @date 2016年6月16日 下午6:03:41
	 */
	public static enum PWD_FIND {
		home, url;
	}

	public static enum CONFIRM_OLD_EMAIL {
		home, url;
	}

	public static enum CONFIRM_NEW_EMAIL {
		home, url;
	}

	public static enum RECHARGE {
		home, date, adCharge, cashBalance;
	}

	public static enum PROJECT_SHARE {
		home,
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
		home, userName, projectName, data, app,
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
		home, userName, dataName;
	}

	public static enum EXCEPTION {
		home, context;
	}

	public static enum FEADBACK {
		home,
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
		userName, email,
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
		home,
        /**
         * 用户名
         */
        username;
    }

}
