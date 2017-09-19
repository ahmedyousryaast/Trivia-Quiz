package com.trivia.useranswer.boundary;

import com.trivia.answer.boundary.AnswerService;
import com.trivia.answer.entity.Answer;
import com.trivia.question.boundary.QuestionService;
import com.trivia.question.entity.Question;
import com.trivia.quiz.boundary.QuizService;
import com.trivia.quiz.entity.Quiz;
import com.trivia.user.boundary.UserService;
import com.trivia.user.entity.User;
import com.trivia.useranswer.entity.UserAnswer;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.persistence.EntityManager;

/**
 *
 * @author ahmed
 */
@Stateless
public class UserAnswerService {
    @Inject
    private EntityManager em;
    
    @EJB
    private AnswerService answerService;
    
    @EJB
    private QuizService quizService;
    
    @EJB
    private UserService userService;
    
    @EJB
    private QuestionService questionService;
    
    
    public void addUserAnswer(JsonObject ob){
        int questionId = ob.getInt("questionId");
        int answerId = Integer.parseInt(ob.getString("answerId"));
        int quizId = ob.getInt("quizId");
        int userId = ob.getInt("userId");
        User user = userService.getUserById(userId);
        Quiz quiz = quizService.getQuizById(quizId);
        Answer answer = answerService.getAnswerById(answerId);
        Question question = questionService.getQuestionById(questionId);
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setAnswerId(answer);
        userAnswer.setQuestionId(question);
        userAnswer.setQuizId(quiz);
        userAnswer.setUserId(user);
        
        em.persist(userAnswer);
    }
    
    public List<UserAnswer> getUserAnswersByQuizObject(Quiz quiz){
        List<UserAnswer> list =  em.createQuery("SELECT u FROM UserAnswer u WHERE u.quizId = :quizId")
                .setParameter("quizId", quiz)
                .getResultList();
        return list;
    }
}
