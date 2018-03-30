package com.lckiss.servicecase;

/**
 * 该接口用来给服务向前台汇报工作进度
 */

public interface ProgressListener {
    public void onProgressListener(int current);

}
