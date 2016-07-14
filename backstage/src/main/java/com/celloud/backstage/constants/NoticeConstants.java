package com.celloud.backstage.constants;

import com.celloud.backstage.model.Notice;

public class NoticeConstants {
	public static enum MessageCategory {
		none("fa fa-comments-o"), upload("fa fa-sellsy"), data("fa fa-tasks"), task("fa fa-tasks"), app(
				"fa fa-cubes"), report("fa fa-files-o"), share("fa fa-share-square-o"), recharge("fa fa-rmb");
		private String icon;

		private MessageCategory(String icon) {
			this.icon = icon;
		}

		public String getIcon() {
			return this.icon;
		}

		public static String getIcon(String name) {
			String icon = none.getIcon();
			for (MessageCategory category : MessageCategory.values()) {
				if (category.name().equals(name)) {
					icon = category.getIcon();
					break;
				}
			}
			return icon;
		}

	}

	public static Notice createMessage(String category, String noticeTitle, String noticeContext) {
		Notice notice = new Notice(category, noticeTitle, noticeContext);
		notice.setType(TYPE_MESSAGE);
		return notice;
	}

	/**
	 * 未读
	 */
	public static final Integer UNREAD = 0;
	/**
	 * 已读
	 */
	public static final Integer READAD = 1;
	/**
	 * 公告
	 */
	public static final String TYPE_NOTICE = "notice";
	/**
	 * 消息
	 */
	public static final String TYPE_MESSAGE = "message";

}
