package co.edu.unicauca.bancopreguntas.domain.entities;

import java.util.List;

/**
 * Entidad que representa una pregunta en el banco de preguntas.
 */
public class Question {
    private String id;
    private String name;
    private String questionText;
    private List<String> options;
    private String correctAnswer;
    private QuestionState state;

    public Question(String id, String name, String questionText, List<String> options, String correctAnswer, QuestionState state) {
        this.id = id;
        this.name = name;
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public QuestionState getState() {
        return state;
    }

    public void setState(QuestionState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}
