package com.trivia.question.boundary;


import com.trivia.answer.boundary.AnswerService;
import com.trivia.answer.entity.Answer;
import com.trivia.question.entity.Question;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.persistence.EntityManager;

/**
 *
 * @author ahmed
 */
@Stateless
public class QuestionService {
    @Inject
    private EntityManager em;
    
    @EJB
    private AnswerService answerService;
    
    public List<Question> findAll() {
        return em.createNamedQuery("Question.findAll").getResultList();
    }
    
    
    public Question getQuestionById(int id){
        Question question = (Question) em.createNamedQuery("Question.findByQuestionId")
                .setParameter("questionId",id).getSingleResult();
        return question;
    }
    
    public void addQuestion(Question question){
        em.persist(question);
    }
    
    public void addQuestionWithAnswers(JsonObject ob){
        Question question = this.parseQuestionObject(ob);
        this.addQuestion(question);
        List<Answer> list = this.parseAnswersObject(ob, question);             
        question.setAnswerCollection(this.parseAnswersObject(ob, question));
    }
    
    public Question parseQuestionObject(JsonObject ob){
        String questionStr = ob.getString("questionStr");
        Question question = new Question();
        question.setQuestionStr(questionStr);
        return question;
    }
    
    public List<Answer> parseAnswersObject(JsonObject ob , Question questionId){
        JsonArray answersArray = ob.getJsonArray("answerCollection");
        List<Answer> answerList = new ArrayList<>();
        for (int i = 0; i < answersArray.size(); i++) {
            JsonObject answerObject = answersArray.getJsonObject(i);
            String answerStr = answerObject.getString("answerStr");
            boolean rightAnswer = answerObject.getBoolean("rightAnswer");
            Answer answer = new Answer();
            answer.setQuestionId(questionId);
            answer.setAnswerStr(answerStr);
            answer.setRightAnswer(rightAnswer);
            answerList.add(answer);
        }
        return answerList;
    }
    
    public void deleteQuestionById(Question question){
        em.remove(em.contains(question) ? question : em.merge(question));
    }
    
    public void updateQuestionData(Question question){
        Question q = this.getQuestionById(question.getQuestionId());
        ArrayList<Answer> oldAnswers = new ArrayList<>();
        ArrayList<Answer> newAnswers = new ArrayList<>();
        for(Answer answer : question.getAnswerCollection()){
            oldAnswers.add(answerService.getAnswerById(answer.getAnswerId()));
            newAnswers.add(answer);
        }
        for(int i = 0 ; i < oldAnswers.size();i++){
               oldAnswers.get(i).setAnswerStr(newAnswers.get(i).getAnswerStr());
        }
        
        q.setQuestionStr(question.getQuestionStr());
    }
    
}
