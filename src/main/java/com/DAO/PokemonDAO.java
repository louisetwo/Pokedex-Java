package com.DAO;

import com.Model.Pokemon;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class PokemonDAO<T extends EntidadeBase> extends GenericDAO {
    private static EntityManager entityManager; //hibernate

    public PokemonDAO() {
        entityManager = ConnectionFactory.getEntityManager();
    }

    public List<Pokemon> listPokemonByPokedexId(Integer id) {
        entityManager = ConnectionFactory.getEntityManager();
        List lista = null;
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            // https://stackoverflow.com/questions/34725802/why-select-c-instead-of-select-in-jpql
            lista = entityManager.createQuery("SELECT p from Pokemon p where p.pokedex.id = :id").setParameter("id", id).getResultList();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }

        return lista;
    }
}
