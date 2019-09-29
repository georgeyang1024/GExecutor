package cn.georgeyang.bean;

public class OutBean<D,M> {
    public D data;
    public M map;

    public OutBean() {

    }
    public OutBean(D data, M map) {
        this.data = data;
        this.map = map;
    }
}
