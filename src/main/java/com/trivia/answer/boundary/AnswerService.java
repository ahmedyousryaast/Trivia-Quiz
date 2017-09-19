package com.trivia.answer.boundary;

import com.trivia.answer.entity.Answer;
import com.trivia.question.boundary.QuestionService;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author ahmed
 */
@Stateless
public class AnswerService {
    @Inject
    private EntityManager em;
    
    @EJB
    private QuestionService questionService;
    
    
   public Answer getRightAnswerByQuestionId(int questionId){
        return (Answer) em.createQuery("SELECT a FROM Answer a WHERE a.questionId.questionId = :questionId AND a.rightAnswer = :rightAnswer")
                .setParameter("questionId", questionId)
                .setParameter("rightAnswer", true)
                .getSingleResult();
            
   }
   
   public List<Answer> getRightAnswersByQuestionsID(int [] questionIds){
       List<Answer> answersList = new ArrayList<>();
       for(int i = 0 ; i < questionIds.length;i++){
           answersList.add(this.getRightAnswerByQuestionId(questionIds[i]));
       }    
       return answersList;
   }
     
    public Answer getAnswerById(int id){
        return (Answer) em.createNamedQuery("Answer.findByAnswerId")
                .setParameter("answerId", id).getSingleResult();
    }
    
}
