/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.recipes.test.logic;

import co.edu.uniandes.csw.recipes.ejb.RecipeLogic;
import co.edu.uniandes.csw.recipes.entities.RecipeEntity;
import co.edu.uniandes.csw.recipes.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.recipes.persistence.RecipePersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class RecipeLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private RecipeLogic recipeLogic;

    @Inject
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;

    private List<RecipeEntity> data = new ArrayList<RecipeEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RecipeEntity.class.getPackage())
                .addPackage(RecipeLogic.class.getPackage())
                .addPackage(RecipePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from RecipeEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {

        for (int i = 0; i < 3; i++) {
            RecipeEntity entity = factory.manufacturePojo(RecipeEntity.class);
            em.persist(entity);
            data.add(entity);

        }
    }

    @Test
    public void createRecipeTest() throws BusinessLogicException {
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        while (newEntity.getName().length() > 30 || newEntity.getDescription().length() > 150) {
            newEntity = factory.manufacturePojo(RecipeEntity.class);
        }
        recipeLogic.createRecipe(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createRecipeMismoNombreTest() throws BusinessLogicException {
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        newEntity.setName(data.get(0).getName());
        recipeLogic.createRecipe(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createRecipeNombreInvalidoTest() throws BusinessLogicException {
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        String nombre = generarString(30);
        newEntity.setName(nombre);
        recipeLogic.createRecipe(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createRecipeDescripcionInvalidaTest() throws BusinessLogicException {
        RecipeEntity newEntity = factory.manufacturePojo(RecipeEntity.class);
        String nombre = generarString(150);
        newEntity.setName(nombre);
        recipeLogic.createRecipe(newEntity);

    }

    public String generarString(Integer n) {
        String resp = "";
        for (int i = 0; i < n + 2; i++) {
            Random a = new Random();
            int letra = a.nextInt(50);
            char add = (char) letra;
            resp += letra;
        }
        return resp;
    }

}
