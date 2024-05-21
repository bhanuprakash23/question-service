package com.bhanu.questionservice.controller;


import com.bhanu.questionservice.model.QuestionWrapper;
import com.bhanu.questionservice.model.Questions;
import com.bhanu.questionservice.model.Response;
import com.bhanu.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

   @Autowired
   QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Questions>> getAllQuestions()
    {

        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Questions>> getQuestionsByCategory(@PathVariable String category)
    {
            return questionService.getQuestionsByCategory(category);
    }

    @PutMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Questions questions)
    {
        return (questionService.addQuestion(questions));
    }

    @DeleteMapping("delete/{id}")
    public String deleteQuestion(@PathVariable int id)
    {
        return questionService.deleteQuestion(id);
    }

    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName, @RequestParam Integer numQuestions){
        return questionService.getQuestionsForQuiz(categoryName,numQuestions);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
        return questionService.getQuestionsFromId(questionIds);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses)
    {
        return questionService.getScore(responses);
    }
    //generate/create
    //getQuestions (questionid)
    //getScore

}
