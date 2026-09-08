package co.edu.unicauca.bancopreguntas.transversal.observer;

/**
 * Interfaz para los observadores en el patrón Observer.
 * Cualquier vista que necesite ser notificada de cambios debe implementar esta interfaz.
 */
public interface IObserver {
    /**
     * Método invocado por el sujeto observable cuando cambia su estado.
     */
    void update();
}
