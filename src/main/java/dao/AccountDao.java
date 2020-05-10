package dao;

import pojo.Account;

public interface AccountDao {
    Account queryAccountByCardNo(String cardNo) throws Exception;

    int updateAccountByCardNo(Account account) throws Exception;
}
