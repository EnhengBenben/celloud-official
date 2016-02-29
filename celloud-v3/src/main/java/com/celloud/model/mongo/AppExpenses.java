package com.celloud.model.mongo;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

/**
 * APP运行消费记录
 * 
 * @author leamo
 * @date 2016年2月29日 下午5:39:06
 */
@Entity(noClassnameStored = true, value = "Expenses")
public class AppExpenses extends Expenses {
    private static final long serialVersionUID = 1L;
    /**
     * 交易快照
     */
    @Embedded
    private AppSnapshot snapshot;

    public AppSnapshot getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(AppSnapshot snapshot) {
        this.snapshot = snapshot;
    }
}
