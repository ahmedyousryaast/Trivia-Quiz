package com.trivia.question.rest.boundary;

import com.trivia.answer.entity.Answer;
import com.trivia.question.boundary.QuestionService;
import com.trivia.question.entity.Question;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ahmed
 */
@Produces(MediaType.APPLICATION_JSON)
@Path("questions")
public class QuestionEndPoint {
    @EJB
    private QuestionService questionService;
    
    @GET
    public Response findAll(){
        return Response.ok(questionService.findAll()).build();
    }
    
    @GET
    @Path("{questionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuestion(@PathParam("questionId") int questionId){
        return Response.ok(questionService.getQuestionById(questionId))
                .build();
    }
    
    @POST
    public void addQuestion(JsonObject ob){
        questionService.addQuestionWithAnswers(ob);
    }
    
    @POST
    @Path("delete")
    public Response deleteQuestion(Question question){
        questionService.deleteQuestionById(question);
        return Response.ok().build();
    }
    
    @POST
    @Path("update")
    public void updateQuestion(Question question){
        questionService.updateQuestionData(question);
    }
}
