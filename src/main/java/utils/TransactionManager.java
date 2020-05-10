package utils;

import annotation.Autowired;
import annotation.Component;

import java.sql.SQLException;

/**
 * define a transaction manager
 */
@Component("transactionManager")
public class TransactionManager {
    @Autowired
    private ConnectionUtils connectionUtils;

    public void setConnectionUtils(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    // start transaction manager
    public void beginTransaction() throws SQLException {
        connectionUtils.getCurrentThreadConn().setAutoCommit(false);
    }


    // commit
    public void commit() throws SQLException {
        connectionUtils.getCurrentThreadConn().commit();
    }


    // rollback
    public void rollback() throws SQLException {
        connectionUtils.getCurrentThreadConn().rollback();
    }
}
