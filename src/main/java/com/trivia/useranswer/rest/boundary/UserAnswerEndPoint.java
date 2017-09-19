/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trivia.useranswer.rest.boundary;
import com.trivia.quiz.boundary.QuizService;
import com.trivia.quiz.entity.Quiz;
import com.trivia.useranswer.boundary.UserAnswerService;
import com.trivia.useranswer.entity.UserAnswer;
import java.util.List;
import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ahmed
 */
@Path("userAnswer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserAnswerEndPoint {
    @EJB
    private UserAnswerService userAnswerService;
    
    @EJB
    private QuizService quizService;
    
    @POST
    public void addUserAnswer(JsonObject ob){
        userAnswerService.addUserAnswer(ob);
    }
    
    @POST
    @Path("quiz")
    public List<UserAnswer> getUserAnswersByQuizId(int quizId){
        Quiz quiz = quizService.getQuizById(quizId);
        return userAnswerService.getUserAnswersByQuizObject(quiz);
    }
    

}
