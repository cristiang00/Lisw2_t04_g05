package co.edu.unicauca.bancopreguntas.presentation.controllers;

import co.edu.unicauca.bancopreguntas.domain.entities.Question;
import co.edu.unicauca.bancopreguntas.domain.entities.QuestionState;
import co.edu.unicauca.bancopreguntas.domain.services.QuestionService;
import co.edu.unicauca.bancopreguntas.presentation.model.QuestionObservable;

import java.util.List;

/**
 * Controlador en el patrón MVC.
 * Coordina las interacciones entre la vista, el servicio de dominio y el modelo observable.
 */
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionObservable questionObservable;

    public QuestionController(QuestionService questionService, QuestionObservable questionObservable) {
        this.questionService = questionService;
        this.questionObservable = questionObservable;
    }

    /**
     * Obtiene la lista de todas las preguntas.
     * @return Lista de preguntas.
     */
    public List<Question> loadQuestions() {
        return questionService.getAllQuestions();
    }

    /**
     * Obtiene una pregunta específica por su ID.
     * @param id Identificador de la pregunta.
     * @return La pregunta encontrada o null.
     */
    public Question getQuestion(String id) {
        return questionService.getQuestionById(id);
    }

    /**
     * Cambia el estado de una pregunta y notifica a los observadores.
     * @param id Identificador de la pregunta.
     * @param newState Nuevo estado a establecer.
     */
    public void changeQuestionState(String id, QuestionState newState) {
        questionService.changeState(id, newState);
        // Notificar a las vistas que hubo un cambio en los datos
        questionObservable.notifyObservers();
    }
}
