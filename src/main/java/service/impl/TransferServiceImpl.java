package service.impl;

import annotation.Autowired;
import annotation.Service;
import annotation.Transactional;
import dao.AccountDao;
import pojo.Account;
import service.TransferService;

@Transactional
@Service(value = "transferService")
public class TransferServiceImpl implements TransferService {
    @Autowired
    private AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao){
        this.accountDao = accountDao;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    @Override
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {
        Account from = accountDao.queryAccountByCardNo(fromCardNo);
        Account to = accountDao.queryAccountByCardNo(toCardNo);
        from.setMoney(from.getMoney() - money);
        to.setMoney(to.getMoney() + money);
        accountDao.updateAccountByCardNo(from);
        accountDao.updateAccountByCardNo(to);
    }
}
