package cn.wolfcode.wms.exception;

//自定义的安全检查登录
public class SecurityException extends RuntimeException{

    public SecurityException(){
        super();
    }
    public SecurityException(String message){
        super(message);
    }
    public SecurityException(Throwable cause){
        super(cause);
    }
}
