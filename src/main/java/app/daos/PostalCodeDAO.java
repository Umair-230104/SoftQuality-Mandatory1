package app.daos;

import app.entities.PostalCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class PostalCodeDAO
{
    private final EntityManagerFactory emf;

    public PostalCodeDAO(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    public PostalCode getRandomPostalCode()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            return em.createQuery(
                    "SELECT p FROM PostalCode p ORDER BY function('random')",
                    PostalCode.class
            )
            .setMaxResults(1)
            .getSingleResult();
        }
        finally
        {
            em.close();
        }
    }
}