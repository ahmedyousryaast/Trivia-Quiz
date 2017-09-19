package com.trivia.quiz.entity;

import com.trivia.question.entity.Question;
import com.trivia.user.entity.User;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ahmed
 */
@Entity
@Table(name = "triv_quiz")
@NamedQueries({
    @NamedQuery(name = "Quiz.findAll", query = "SELECT q FROM Quiz q")
    , @NamedQuery(name = "Quiz.findByQuizId", query = "SELECT q FROM Quiz q WHERE q.quizId = :quizId")
    , @NamedQuery(name = "Quiz.findByQuizScore", query = "SELECT q FROM Quiz q WHERE q.quizScore = :quizScore")})
public class Quiz implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "quiz_id")
    private Integer quizId;
    
    
    @Column(name = "quiz_score")
    private Integer quizScore;
    
    @JoinTable(name = "quiz_questions", joinColumns = {
        @JoinColumn(name = "quiz_id", referencedColumnName = "quiz_id")}, inverseJoinColumns = {
        @JoinColumn(name = "question_id", referencedColumnName = "question_id")})
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Question> questionCollection;
    
//    //this variable is useless and to be removed but later
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quizId")
//    private Collection<UserAnswer> userAnswerCollection;
    
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User userId;
    
//    @JoinColumn(name = "user_answer_id", referencedColumnName = "user_answer_id")
//    @ManyToOne
//    private UserAnswer userAnswerId;

    public Quiz() {
    }

    public Quiz(Integer quizId) {
        this.quizId = quizId;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public Integer getQuizScore() {
        return quizScore;
    }

    public void setQuizScore(Integer quizScore) {
        this.quizScore = quizScore;
    }

    
    public Collection<Question> getQuestionCollection() {
        return questionCollection;
    }

    public void setQuestionCollection(Collection<Question> questionCollection) {
        this.questionCollection = questionCollection;
    }

    
//    public Collection<UserAnswer> getUserAnswerCollection() {
//        return userAnswerCollection;
//    }
//
//    public void setUserAnswerCollection(Collection<UserAnswer> userAnswerCollection) {
//        this.userAnswerCollection = userAnswerCollection;
//    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

//    public UserAnswer getUserAnswerId() {
//        return userAnswerId;
//    }
//
//    public void setUserAnswerId(UserAnswer userAnswerId) {
//        this.userAnswerId = userAnswerId;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (quizId != null ? quizId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Quiz)) {
            return false;
        }
        Quiz other = (Quiz) object;
        if ((this.quizId == null && other.quizId != null) || (this.quizId != null && !this.quizId.equals(other.quizId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.trivia.quiz.entity.Quiz[ quizId=" + quizId + " ]";
    }
    
}
