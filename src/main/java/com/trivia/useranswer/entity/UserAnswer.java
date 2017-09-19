package com.trivia.useranswer.entity;

import com.trivia.quiz.entity.*;
import com.trivia.answer.entity.Answer;
import com.trivia.question.entity.Question;
import com.trivia.user.entity.User;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ahmed
 */
@Entity
@Table(name = "triv_user_answers")
@NamedQueries({
    @NamedQuery(name = "UserAnswer.findAll", query = "SELECT u FROM UserAnswer u")
    , @NamedQuery(name = "UserAnswer.findByUserAnswerId", query = "SELECT u FROM UserAnswer u WHERE u.userAnswerId = :userAnswerId")})
public class UserAnswer implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_answer_id")
    private Integer userAnswerId;
    
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false , fetch = FetchType.LAZY)
    private User userId;
    
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    @ManyToOne(optional = false)
    private Question questionId;
    
    @JoinColumn(name = "quiz_id", referencedColumnName = "quiz_id")
    @ManyToOne(optional = false , cascade = CascadeType.ALL)
    private Quiz quizId;
    
    @JoinColumn(name = "answer_id", referencedColumnName = "answer_id")
    @ManyToOne(optional = false)
    private Answer answerId;
    

    public UserAnswer() {
    }

    public UserAnswer(Integer userAnswerId) {
        this.userAnswerId = userAnswerId;
    }

    public Integer getUserAnswerId() {
        return userAnswerId;
    }

    public void setUserAnswerId(Integer userAnswerId) {
        this.userAnswerId = userAnswerId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Question getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Question questionId) {
        this.questionId = questionId;
    }

    public Quiz getQuizId() {
        return quizId;
    }

    public void setQuizId(Quiz quizId) {
        this.quizId = quizId;
    }

    public Answer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Answer answerId) {
        this.answerId = answerId;
    }

//    @XmlTransient
//    public Collection<Quiz> getQuizCollection() {
//        return quizCollection;
//    }
//
//    public void setQuizCollection(Collection<Quiz> quizCollection) {
//        this.quizCollection = quizCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userAnswerId != null ? userAnswerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserAnswer)) {
            return false;
        }
        UserAnswer other = (UserAnswer) object;
        if ((this.userAnswerId == null && other.userAnswerId != null) || (this.userAnswerId != null && !this.userAnswerId.equals(other.userAnswerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.trivia.quiz.entity.UserAnswer[ userAnswerId=" + userAnswerId + " ]";
    }

    
}
