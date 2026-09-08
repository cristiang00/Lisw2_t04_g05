package co.edu.unicauca.bancopreguntas.presentation.model;

import co.edu.unicauca.bancopreguntas.domain.entities.QuestionState;
import co.edu.unicauca.bancopreguntas.domain.services.QuestionService;
import co.edu.unicauca.bancopreguntas.transversal.observer.IObservable;
import co.edu.unicauca.bancopreguntas.transversal.observer.IObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Modelo del MVC que actúa como Sujeto Observable.
 * Mantiene el estado que será observado por las vistas.
 */
public class QuestionObservable implements IObservable {

    private final List<IObserver> observers;
    private final QuestionService questionService;

    public QuestionObservable(QuestionService questionService) {
        this.observers = new ArrayList<>();
        this.questionService = questionService;
    }

    @Override
    public void addObserver(IObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update();
        }
    }

    /**
     * Obtiene las estadísticas actuales usando el servicio.
     * @return Mapa con la cantidad de preguntas por estado.
     */
    public Map<QuestionState, Long> getStatistics() {
        return questionService.getStatistics();
    }
}
