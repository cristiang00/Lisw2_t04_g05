package co.edu.unicauca.bancopreguntas.transversal.observer;

/**
 * Interfaz para el sujeto observable en el patrón Observer.
 */
public interface IObservable {
    /**
     * Agrega un observador a la lista.
     * @param observer El observador a agregar.
     */
    void addObserver(IObserver observer);

    /**
     * Elimina un observador de la lista.
     * @param observer El observador a eliminar.
     */
    void removeObserver(IObserver observer);

    /**
     * Notifica a todos los observadores registrados que ha ocurrido un cambio.
     */
    void notifyObservers();
}
