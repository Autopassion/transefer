package dao.impl;

import annotation.Autowired;
import annotation.Repository;
import dao.AccountDao;
import pojo.Account;
import utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository(value = "accountDao")
public class AccountDaoImpl implements AccountDao {

    @Autowired
    private static ConnectionUtils connectionUtils;

    public void setConnectionUtils(ConnectionUtils connectionUtils){
        this.connectionUtils = connectionUtils;
    }


    @Override
    public Account queryAccountByCardNo(String cardNo) throws Exception {
        Connection connection = connectionUtils.getCurrentThreadConn();
        String sql = "select * from account where cardNo = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,cardNo);
        ResultSet resultSet = preparedStatement.executeQuery();
        Account account = new Account();
        while (resultSet.next()) {
            account.setCardNo(resultSet.getString("cardNo"));
            account.setName(resultSet.getString("name"));
            account.setMoney(resultSet.getInt("money"));
        }
        resultSet.close();
        preparedStatement.close();
        return account;
    }

    @Override
    public int updateAccountByCardNo(Account account) throws Exception {
        Connection connection = connectionUtils.getCurrentThreadConn();
        String sql = "update account set money = ? where cardNo = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,account.getMoney());
        preparedStatement.setString(2,account.getCardNo());
        int i = preparedStatement.executeUpdate();
        preparedStatement.close();
        return i;
    }
}
