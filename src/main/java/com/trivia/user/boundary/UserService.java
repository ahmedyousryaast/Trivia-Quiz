
package com.trivia.user.boundary;

import com.trivia.user.entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;


@Stateless
public class UserService {
    @Inject
    private EntityManager em;
    
    public User signUp(User user){
        if(this.checkAvailableUserName(user.getUserName())){
            em.persist(user);
            return user;
        }
        return user;
    }
   
    public boolean checkAvailableUserName(String userName){
        List<User> list = em.createNamedQuery("User.findByUserName").setParameter("userName", userName).getResultList();
        if(list.size()>0){
            return false;
        }
        return true;
    }
    
    public User logUserIn(User user){
        try{
            User returnedUser = em.createQuery("SELECT u FROM User u WHERE u.userName = :userName AND u.userPass = :userPass",User.class)
                .setParameter("userName", user.getUserName())
                .setParameter("userPass", user.getUserPass())
                .getSingleResult();
            return returnedUser;
        } catch(NoResultException e) {
            return null;
        }
       
    }
    
    public User getUserById(int id){
        return (User) em.createNamedQuery("User.findByUserId")
                .setParameter("userId", id)
                .getSingleResult();
    }
    
    public List<User>findAll(){
        return em.createQuery("SELECT u FROM User u WHERE u.isAdmin = :isAdmin")
                .setParameter("isAdmin", false)
                .getResultList();
    }
    
    public void promoteUser(User user){
        User u = this.getUserById(user.getUserId());
        u.setIsAdmin(true);
    }
    
    public void removeUser(User user){
        em.remove(em.contains(user) ? user : em.merge(user));
    }
    
}
