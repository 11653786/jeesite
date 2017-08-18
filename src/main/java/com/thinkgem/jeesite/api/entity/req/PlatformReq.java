package com.thinkgem.jeesite.api.entity.req;

/**
 * Created by yangtao on 2017/8/18.
 */
public class PlatformReq<T> {
    //1.0
    private String version;
    //pc,android
    private String source;
    //请求数据
    private T data;


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
