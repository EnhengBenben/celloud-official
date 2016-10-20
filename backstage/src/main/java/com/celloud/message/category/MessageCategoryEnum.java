package com.celloud.message.category;
/** 
 * @author MQ: 
 * @date 2016年7月5日 下午2:01:35 
 * @description 
 */
public enum MessageCategoryEnum {
    SHARED(1, "被分享报告"), REPORT(2, "报告生成"), BALANCE(3, "账户余额变更"), SYSTEM(4, "系统公告");
    
    private Integer id;
    private String name;
    
    private MessageCategoryEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
