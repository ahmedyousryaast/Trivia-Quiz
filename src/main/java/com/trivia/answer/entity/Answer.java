package com.trivia.answer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trivia.question.entity.Question;
import com.trivia.useranswer.entity.UserAnswer;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "triv_answers")
@NamedQueries({
    @NamedQuery(name = "Answer.findAll", query = "SELECT a FROM Answer a")
    , @NamedQuery(name = "Answer.findByAnswerId", query = "SELECT a FROM Answer a WHERE a.answerId = :answerId")
    , @NamedQuery(name = "Answer.findByQuestionId", query = "SELECT a FROM Answer a WHERE a.questionId.questionId = :questionId")
    , @NamedQuery(name = "Answer.findByAnswerStr", query = "SELECT a FROM Answer a WHERE a.answerStr = :answerStr")
    , @NamedQuery(name = "Answer.findByRightAnswer", query = "SELECT a FROM Answer a WHERE a.rightAnswer = :rightAnswer")})
public class Answer implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "answerId")
    private Collection<UserAnswer> userAnswerCollection;



    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "answer_id")
    private Integer answerId;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "answer_str")
    private String answerStr;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "right_answer")
    @JsonIgnore
    private boolean rightAnswer;
    
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    @ManyToOne(optional = false , cascade = CascadeType.ALL)
    private Question questionId;

    public Answer() {
    }

    public Answer(Integer answerId) {
        this.answerId = answerId;
    }

    public Answer(Integer answerId, String answerStr, boolean rightAnswer) {
        this.answerId = answerId;
        this.answerStr = answerStr;
        this.rightAnswer = rightAnswer;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public String getAnswerStr() {
        return answerStr;
    }

    public void setAnswerStr(String answerStr) {
        this.answerStr = answerStr;
    }

    public boolean getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(boolean rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
    
    @JsonIgnore 
    public Question getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Question questionId) {
        this.questionId = questionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (answerId != null ? answerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Answer)) {
            return false;
        }
        Answer other = (Answer) object;
        if ((this.answerId == null && other.answerId != null) || (this.answerId != null && !this.answerId.equals(other.answerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.trivia.answer.entity.Answer[ answerId=" + answerId + " ]";
    }

    @XmlTransient
    public Collection<UserAnswer> getUserAnswerCollection() {
        return userAnswerCollection;
    }

    public void setUserAnswerCollection(Collection<UserAnswer> userAnswerCollection) {
        this.userAnswerCollection = userAnswerCollection;
    }


}
