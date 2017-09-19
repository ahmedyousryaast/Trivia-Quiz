/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trivia.persistence.control;

import com.trivia.constants.Constants;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ahmed
 */
@Dependent
public class PersistenceControl {
    private EntityManager em;
    
    @Produces
    public EntityManager getEm() {
        return em;
    }
    
    @PersistenceContext(unitName = Constants.UNIT_NAME)
    public void setEm(EntityManager em) {
        this.em = em;
    }
    
}
