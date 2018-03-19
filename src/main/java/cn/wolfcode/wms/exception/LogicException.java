package cn.wolfcode.wms.exception;

//自定义的安全检查登录
public class LogicException extends RuntimeException{

    public LogicException(){
        super();
    }
    public LogicException(String message){
        super(message);
    }
    public LogicException(Throwable cause){
        super(cause);
    }
}
