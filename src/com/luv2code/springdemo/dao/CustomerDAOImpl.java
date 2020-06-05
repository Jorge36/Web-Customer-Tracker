package com.luv2code.springdemo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Customer> getCustomers() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// create a query
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName", Customer.class);
		
		// execute a query and get result list
		List<Customer> customers = theQuery.getResultList();
		
		// return the results
		return customers;
	}

	@Override
	public void saveCustomer(Customer customer) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// save or update the customer
		currentSession.saveOrUpdate(customer);

	}

	@Override
	public Customer getCustomer(int id) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// retrieve/read the customer from database
		Customer customer = currentSession.get(Customer.class, id);
		
		// return the results
		return customer;
	}

	@Override
	public void deleteCustomer(int id) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// delete the customer from database
		// create a query
		Query<Customer> theQuery = currentSession.createQuery("delete from Customer where id=:customerId");
		
		theQuery.setParameter("customerId", id);
		
		theQuery.executeUpdate();
		
	}

	@Override
	public List<Customer> searchCustomers(String name) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// define variable
		Query<Customer> theQuery = null;
		
		if ((name != null) && (name.trim().length() > 0)) {
			
			// create a query
			theQuery = currentSession.createQuery("from Customer where lower(firstName) like :name or lower(lastName) like :name", Customer.class);
						
			// set parameter
			theQuery.setParameter("name", "%" + name.toLowerCase() + "%");
			
		} else {
			// create a query
			theQuery = currentSession.createQuery("from Customer order by lastName", Customer.class);
			
		}
			
		// execute a query and get result list
		List<Customer> customers = theQuery.getResultList();
			
		// return the results
		return customers;
		
	}

}
