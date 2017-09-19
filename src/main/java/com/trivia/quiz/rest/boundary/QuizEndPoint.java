package com.trivia.quiz.rest.boundary;

import com.trivia.quiz.boundary.QuizService;
import com.trivia.quiz.entity.Quiz;
import com.trivia.user.entity.User;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ahmed
 */
@Path("quiz")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QuizEndPoint {
    @EJB
    private QuizService quizService;
    
    @POST
    public Quiz generateQuiz(User user){
        Quiz quiz = quizService.generateQuizForUser(user);
        return quiz;
    }
    
    @POST
    @Path("submit")
    public int getQuizResult(int quizId){
        return quizService.calculateQuizResult(quizId);
    }
    
    @POST
    @Path("history")
    public List<Quiz> quizHistoryforUser(User user){       
        return quizService.getAllQuizezByUserId(user);
    }

}
