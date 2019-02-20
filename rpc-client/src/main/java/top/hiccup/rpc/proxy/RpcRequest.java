package top.hiccup.rpc.proxy;

import java.io.Serializable;

/**
 * RPC请求对象
 *
 * @author wenhy
 * @date 2019/2/20
 */
public class RpcRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 请求的服务类
     */
    private String className;
    /**
     * 请求的方法
     */
    private String methodName;
    /**
     * 请求参数
     */
    private Object[] parameters;

    public RpcRequest(String className, String methodName, Object[] parameters) {
        this.className = className;
        this.methodName = methodName;
        this.parameters = parameters;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
