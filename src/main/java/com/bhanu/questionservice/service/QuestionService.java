package com.bhanu.questionservice.service;

//import com.bhanu.quizapp.model.Question;

import com.bhanu.questionservice.dao.QuestionDao;
import com.bhanu.questionservice.model.QuestionWrapper;
import com.bhanu.questionservice.model.Questions;
import com.bhanu.questionservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
     QuestionDao questiondao;

    public ResponseEntity<List<Questions>> getAllQuestions(){
        try {
            return new ResponseEntity<>(questiondao.findAll(), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }

        return  new ResponseEntity<> (new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Questions>> getQuestionsByCategory(String category) {
        try{
            return new ResponseEntity<> (questiondao.findByCategory(category), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<> (new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Questions questions) {
        questiondao.save(questions);
        return new ResponseEntity<>("success", HttpStatus.CREATED);

    }

    public String deleteQuestion(int id) {
        questiondao.deleteById(id);
        return "success";
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
        List<Integer> questions = questiondao.findRandomQuestionsByCategory(categoryName, numQuestions);
        return new ResponseEntity<>(questions,HttpStatus.OK);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Questions> questions = new ArrayList<>();

        for(Integer id: questionIds)
        {
            questions.add(questiondao.findById(id).get());
        }
        for(Questions question : questions)
        {
            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestionTitle(question.getQuestionTitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());
            wrappers.add(wrapper);
        }


        return new ResponseEntity<>(wrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        int right =0;

        for(Response response: responses)
        {
            Questions questions = questiondao.findById(response.getId()).get();
            if(response.getResponse().equals(questions.getRightAnswer()))
            {
                right++;
            }
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
