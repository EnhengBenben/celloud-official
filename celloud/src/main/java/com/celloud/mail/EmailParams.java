package com.celloud.mail;

public class EmailParams {

	public static final String URL = "%url%";
	public static final String EMAIL_TO = "%userName%";
	public static final String SHARE_FROM = "%shareUserName%";
	public static final String DATE_NAME = "%dataName%";
	public static final String DATE_KEY = "%dataKey%";
	public static final String PROJECT_NAME = "%projectName%";
	public static final String APP = "%app%";
	public static final String START = "%start%";
	public static final String END = "%end%";

	/**
	 * 用户注册
	 * 
	 * @author lin
	 * @date 2016年6月16日 下午6:03:32
	 */
	public static enum USER_REGISTER {
		URL(EmailParams.URL);
		public String param;

		private USER_REGISTER(String param) {
			this.param = param;
		}

		public String getParam() {
			return this.param;
		}
	}

	/**
	 * 密码找回
	 * 
	 * @author lin
	 * @date 2016年6月16日 下午6:03:41
	 */
	public static enum PWD_FIND {
		URL(EmailParams.URL);
		public String param;

		private PWD_FIND(String param) {
			this.param = param;
		}

		public String getParam() {
			return this.param;
		}
	}

	public static enum CONFIRM_OLD_EMAIL {
		URL(EmailParams.URL);
		public String param;

		private CONFIRM_OLD_EMAIL(String param) {
			this.param = param;
		}

		public String getParam() {
			return this.param;
		}
	}

	public static enum CONFIRM_NEW_EMAIL {
		URL(EmailParams.URL);
		public String param;

		private CONFIRM_NEW_EMAIL(String param) {
			this.param = param;
		}

		public String getParam() {
			return this.param;
		}
	}


	public static enum PROJECT_SHARE {
		EMAIL_TO(EmailParams.EMAIL_TO), SHARE_FROM(EmailParams.SHARE_FROM), DATE_NAME(EmailParams.DATE_NAME), DATE_KEY(
				EmailParams.DATE_KEY);
		public String param;

		private PROJECT_SHARE(String param) {
			this.param = param;
		}

		public String getParam() {
			return this.param;
		}
	}

	public static enum RUN_OVER {
		EMAIL_TO(EmailParams.EMAIL_TO), PROJECT_NAME(EmailParams.PROJECT_NAME), APP(EmailParams.APP), START(
				EmailParams.START), END(EmailParams.END);
		public String param;

		private RUN_OVER(String param) {
			this.param = param;
		}

		public String getParam() {
			return this.param;
		}
	}

	public static enum UPLOAD_OVER {
		EMAIL_TO(EmailParams.EMAIL_TO), DATE_NAME(EmailParams.DATE_NAME);
		public String param;

		private UPLOAD_OVER(String param) {
			this.param = param;
		}

		public String getParam() {
			return this.param;
		}
	}

}
