package com.trivia.question.entity;

import com.trivia.answer.entity.Answer;
import com.trivia.quiz.entity.Quiz;
import com.trivia.useranswer.entity.UserAnswer;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ahmed
 */
@Entity
@Table(name = "triv_questions")
@NamedQueries({
    @NamedQuery(name = "Question.findAll", query = "SELECT q FROM Question q")
    , @NamedQuery(name = "Question.findByQuestionId", query = "SELECT q FROM Question q WHERE q.questionId = :questionId")
    , @NamedQuery(name = "Question.findByQuestionStr", query = "SELECT q FROM Question q WHERE q.questionStr = :questionStr")})
public class Question implements Serializable {
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionId")
    private Collection<UserAnswer> userAnswerCollection;

    @ManyToMany(mappedBy = "questionCollection" ,fetch = FetchType.EAGER)
    private Collection<Quiz> quizCollection;
    
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionId")
//    private Collection<UserAnswer> userAnswerCollection;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "questionId",fetch = FetchType.EAGER)
    private Collection<Answer> answerCollection;

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "question_id")
    private Integer questionId;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "question_str")
    private String questionStr;

    public Question() {
    }

    public Question(Integer questionId) {
        this.questionId = questionId;
    }

    public Question(Integer questionId, String questionStr) {
        this.questionId = questionId;
        this.questionStr = questionStr;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getQuestionStr() {
        return questionStr;
    }

    public void setQuestionStr(String questionStr) {
        this.questionStr = questionStr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionId != null ? questionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.questionId == null && other.questionId != null) || (this.questionId != null && !this.questionId.equals(other.questionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.trivia.question.entity.Question[ questionId=" + questionId + " ]";
    }

    public Collection<Answer> getAnswerCollection() {
        return answerCollection;
    }

    public void setAnswerCollection(Collection<Answer> answerCollection) {
        this.answerCollection = answerCollection;
    }

    @XmlTransient
    public Collection<Quiz> getQuizCollection() {
        return quizCollection;
    }

    public void setQuizCollection(Collection<Quiz> quizCollection) {
        this.quizCollection = quizCollection;
    }

//    @XmlTransient
//    public Collection<UserAnswer> getUserAnswerCollection() {
//        return userAnswerCollection;
//    }
//
//    public void setUserAnswerCollection(Collection<UserAnswer> userAnswerCollection) {
//        this.userAnswerCollection = userAnswerCollection;
//    }

    @XmlTransient
    public Collection<UserAnswer> getUserAnswerCollection() {
        return userAnswerCollection;
    }

    public void setUserAnswerCollection(Collection<UserAnswer> userAnswerCollection) {
        this.userAnswerCollection = userAnswerCollection;
    }


}
