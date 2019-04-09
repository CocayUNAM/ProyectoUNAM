package com.cocay.sicecd.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.cocay.sicecd.dao.CustomerDao;
import com.cocay.sicecd.model.Customer;

 
@Repository
public class CustomerDaoImpl extends JdbcDaoSupport implements CustomerDao {
 
  @Autowired
  DataSource dataSource;
 
  @PostConstruct
  private void initialize() {
    setDataSource(dataSource);
  }
 
  @Override
  public void insert(List<? extends Customer> Customers) {
    String sql = "INSERT INTO customer " + "(id, first_name, last_name) VALUES (?, ?, ?)";
    getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        Customer customer = Customers.get(i);
        ps.setLong(1, customer.getId());
        ps.setString(2, customer.getFirstName());
        ps.setString(3, customer.getLastName());
      }
 
      public int getBatchSize() {
        return Customers.size();
      }
    });
 
  }
 
  @Override
  public List<Customer> loadAllCustomers() {
    String sql = "SELECT * FROM customer";
    List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
 
    List<Customer> result = new ArrayList<Customer>();
    for (Map<String, Object> row : rows) {
      Customer customer = new Customer();
      customer.setId((Long) row.get("id"));
      customer.setFirstName((String) row.get("first_name"));
      customer.setLastName((String) row.get("last_name"));
      result.add(customer);
    }
 
    return result;
  }
}
