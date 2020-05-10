package test;

import dao.impl.AccountDaoImpl;
import org.junit.Test;
import pojo.Account;
import utils.ConnectionUtils;

public class AccountDaoTest {
    @Test
    public void test() throws Exception {
        AccountDaoImpl accountDao = new AccountDaoImpl();
        ConnectionUtils connectionUtils = new ConnectionUtils();
        accountDao.setConnectionUtils(connectionUtils);
        Account account = accountDao.queryAccountByCardNo("123456");
        System.out.println(account);
    }
}
