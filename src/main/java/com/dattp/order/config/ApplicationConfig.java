package com.dattp.order.config;

public class ApplicationConfig {
    // entity state
    public static final int DEFAULT_STATE = -1;// chua co trang thai
    public static final int NOT_FOUND_STATE = 0;// khong tim thay
    public static final int OK_STATE = 1;//thanh cong
    public static final int WATING_STATE = 2;//dang cho xu ly
    public static final int CANCEL_STATE = 3;//bi huy
}