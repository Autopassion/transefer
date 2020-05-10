package test;

import factory.AnnotationBeanFactory;
import org.junit.Test;
import service.impl.TransferServiceImpl;

public class AnnotationBeanFactoryTest {
    @Test
    public void test() throws Exception {
        TransferServiceImpl transferService = (TransferServiceImpl) AnnotationBeanFactory.getBean("transferService");
        transferService.transfer("123456", "654321", 100);
    }
}
