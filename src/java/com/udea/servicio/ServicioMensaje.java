/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.udea.servicio;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import com.udea.modelo.Mensaje;

/**
 *
 * @author itmanager
 */
@Stateless
public class ServicioMensaje {

    @PersistenceContext (unitName="javacloudPU")
    private EntityManager em;

    public List<Mensaje> getMensajes(){
        return em.createQuery("select m from Mensaje m",Mensaje.class).getResultList();
       
    }
     
    public void crear(Mensaje mensaje){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Mensaje>> constraintViolations = 
        validator.validate(mensaje);
        if(constraintViolations.size() > 0){
            Iterator<ConstraintViolation<Mensaje>> iterator = 
            constraintViolations.iterator();
            while(iterator.hasNext()){
                ConstraintViolation<Mensaje> cv = iterator.next();
                System.err.println(
                cv.getRootBeanClass().getName()+"."+cv.getPropertyPath() 
                + " " +cv.getMessage());
            }
        }else{
            em.persist(mensaje);
            em.flush();
        }
    }

    public void persist(Object object) {
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
