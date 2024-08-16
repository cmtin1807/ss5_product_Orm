package com.example.productorm.service;

import com.example.productorm.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;


@Service
public class HibernateProductService implements IProductService {
    private static SessionFactory sessionFactory;
    private static EntityManager entityManager;

    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.conf.xml").buildSessionFactory();
            entityManager = sessionFactory.createEntityManager();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Product> findAll() {
        String findAllQuery = "SELECT p FROM Product p";
        TypedQuery<Product> query = entityManager.createQuery(findAllQuery, Product.class);
        return query.getResultList();
    }

    @Override
    public Product findById(int id) {
        String queryStr = "SELECT p FROM Product AS p WHERE p.id = :id";
        TypedQuery<Product> query = entityManager.createQuery(queryStr, Product.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public void save(Product product) {
        Transaction transaction = null;
        Product productSaved = null;
        if (product.getId() == 0) {
            productSaved = new Product();
        }
        else {
            productSaved = findById(product.getId());
        }
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            productSaved.setDescription(product.getDescription());
            productSaved.setPrice(product.getPrice());
            productSaved.setName(product.getName());
            productSaved.setCompany(product.getCompany());
            session.saveOrUpdate(productSaved);
            transaction.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void delete(int id) {
        Product product = findById(id);
        if (product != null) {
            Transaction transaction = null;
            try(Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                session.delete(product);
                transaction.commit();
            }
            catch (Exception e) {
                e.printStackTrace();
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }

    }
}
