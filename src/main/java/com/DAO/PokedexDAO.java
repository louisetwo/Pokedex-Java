package com.DAO;

import com.Model.Pokemon;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class PokedexDAO<T extends EntidadeBase> extends GenericDAO {
    private static EntityManager entityManager; //hibernate

    public PokedexDAO() {
        entityManager = ConnectionFactory.getEntityManager();
    }

}
