package com.trivia.quiz.boundary;

import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;
import com.ibm.casemgmt.api.Case;
import com.ibm.casemgmt.api.CaseType;
import com.ibm.casemgmt.api.constants.ModificationIntent;
import com.ibm.casemgmt.api.context.CaseMgmtContext;
import com.ibm.casemgmt.api.context.P8ConnectionCache;
import com.ibm.casemgmt.api.context.SimpleP8ConnectionCache;
import com.ibm.casemgmt.api.context.SimpleVWSessionCache;
import com.ibm.casemgmt.api.objectref.ObjectStoreReference;
import com.ibm.casemgmt.api.properties.CaseMgmtProperties;

import com.trivia.question.boundary.QuestionService;
import com.trivia.question.entity.Question;
import com.trivia.quiz.entity.Quiz;
import com.trivia.user.entity.User;
import com.trivia.useranswer.boundary.UserAnswerService;
import com.trivia.useranswer.entity.UserAnswer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import com.trivia.constants.Constants;
import java.util.HashMap;
import java.util.Locale;
import javax.security.auth.Subject;

/**
 *
 * @author ahmed
 */
@Stateless
public class QuizService {
    
    @EJB
    private QuestionService questionService;
    
    @EJB
    private UserAnswerService userAnswerService;
    
    
    @Inject
    private EntityManager em;
    
    public Quiz generateQuizForUser(User user){
        Quiz quiz = new Quiz();
        
        quiz.setUserId(user); 
        List<Question> questions = questionService.findAll();
        
        Collections.shuffle(questions);
        
        List<Question> returnedList = new ArrayList<>();
        
        for(int i = 0 ; i < 5 ; i++){
            returnedList.add(questions.get(i));
        }
        
        quiz.setQuestionCollection(returnedList);
        
        persistQuiz(quiz);
        
        return quiz;
    }
    
    public void persistQuiz(Quiz quiz){
        em.persist(quiz);
    }
    
    public Quiz getQuizById(int id){
        return (Quiz) em.createNamedQuery("Quiz.findByQuizId")
                .setParameter("quizId", id)
                .getSingleResult();
    }
    
    public int calculateQuizResult(int quizId){
        
        int rightAnswersCounter=0;
        Quiz quiz = this.getQuizById(quizId);
        
        List<UserAnswer> userAnswers = userAnswerService.getUserAnswersByQuizObject(quiz);
        for(int i = 0 ; i < userAnswers.size();i++){
            if(userAnswers.get(i).getAnswerId().getRightAnswer()){
                rightAnswersCounter++;
            }
        }
        createCase(quiz , userAnswers);
        quiz.setQuizScore(rightAnswersCounter);
        return rightAnswersCounter;
    }
    
    public List<Quiz> getAllQuizezByUserId(User user){
        return em.createQuery("SELECT q FROM Quiz q WHERE q.userId = :userId")
                .setParameter("userId", user)
                .getResultList();
    }
    
    public void createCase(Quiz quiz , List<UserAnswer> answers){
        P8ConnectionCache connCache = new SimpleP8ConnectionCache();
        Connection conn = connCache.getP8Connection(Constants.URI);
        Subject subject = 
            UserContext.createSubject(conn, Constants.USERNAME, 
                                      Constants.PASSWORD, null);
        UserContext uc = UserContext.get();
        uc.pushSubject(subject);
        Locale origLocale = uc.getLocale();
        uc.setLocale(origLocale);
        CaseMgmtContext origCmctx = 
            CaseMgmtContext.set(
                new CaseMgmtContext(
                    new SimpleVWSessionCache(), connCache
                )
            );
        
        try
        {
            Domain domain = Factory.Domain.getInstance(conn, null);
            ObjectStore os = Factory.ObjectStore.fetchInstance(domain,
            "TARGET", null);  
   
            ObjectStoreReference objectStoreRef = new ObjectStoreReference(os);
            CaseType caseTypeObj = CaseType.fetchInstance(objectStoreRef,"TR_ReviewQuiz");
            Case newCase = Case.createPendingInstanceFetchDefaults(caseTypeObj);
            CaseMgmtProperties props = newCase.getProperties();
            HashMap<String,Object> vals = new HashMap();  
            

        
            vals.put("TR_Questions", (List<Question>)quiz.getQuestionCollection());        
            vals.put("TR_UserID", quiz.getUserId());
            vals.put("TR_UserName", answers.get(0).getUserId());
            vals.put("TR_QuizID", quiz.getQuizId());
            vals.put("TR_Answers", answers);
            
            
            if (props != null && vals != null) {
                for (String symbolicName : vals.keySet()) {
                if (props.supportsProperty(symbolicName)) {
                    props.putObjectValue(symbolicName, vals.get(symbolicName));
                } else {
                System.err.println("cannot find property symbolic name (" + symbolicName + ") in case ");
                }
            }
        }
        newCase.save(RefreshMode.REFRESH, null, ModificationIntent.NOT_MODIFY);      
        }
        finally
        {
            uc.popSubject();
        }
        
        
    }
}
