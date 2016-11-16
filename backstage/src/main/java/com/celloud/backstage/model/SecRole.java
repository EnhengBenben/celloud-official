package com.celloud.backstage.model;

import java.util.Date;

public class SecRole extends Tree{
	private Integer id;

    // 角色代码(用于判断)
    private String code;

    // 角色名称(用于显示)
    private String name;

    // 角色描述
    private String description;

    // 是否启用
    private Integer disabled;

    // 创建时间
    private Date createDate;

	private Integer parentId;

	private String mutex;

	private String attract;

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
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getMutex() {
		return mutex;
	}

	public void setMutex(String mutex) {
		this.mutex = mutex;
	}

	public String getAttract() {
		return attract;
	}

	public void setAttract(String attract) {
		this.attract = attract;
	}

}