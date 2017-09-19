/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trivia.answer.rest.boundary;

import com.trivia.answer.boundary.AnswerService;
import com.trivia.answer.entity.Answer;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ahmed
 */
@Path("answers")
@Produces(MediaType.APPLICATION_JSON)
public class AnswerEndPoint {
    
    @EJB
    private AnswerService answerService;
    
    @POST
    public List<Answer> getRightAnswers(int [] questionIds){
        return answerService.getRightAnswersByQuestionsID(questionIds);
    }
    
}
