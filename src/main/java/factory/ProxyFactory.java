package factory;

import annotation.Autowired;
import annotation.Component;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import utils.TransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component(value = "proxyFactory")
public class ProxyFactory {
    @Autowired
    private TransactionManager transactionManager;

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    //jdk proxy
    public Object getJdkProxy(Object obj) {
        return  Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object result = null;
                        try{
                            transactionManager.beginTransaction();
                            result = method.invoke(obj,args);
                            transactionManager.commit();
                        }catch (Exception e) {
                            transactionManager.rollback();
                            throw e;
                        }
                        return result;
                    }
                });
    }

    //cglib proxy
    public Object getCglibProxy(Object obj) {
        return  Enhancer.create(obj.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Object result = null;
                try{
                    transactionManager.beginTransaction();
                    result = method.invoke(obj,objects);
                    transactionManager.commit();
                }catch (Exception e) {
                    transactionManager.rollback();
                    throw e;
                }
                return result;
            }
        });
    }



}
