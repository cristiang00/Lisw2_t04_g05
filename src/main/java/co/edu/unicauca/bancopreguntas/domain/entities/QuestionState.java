package co.edu.unicauca.bancopreguntas.domain.entities;

/**
 * Enum que representa los posibles estados de una pregunta.
 */
public enum QuestionState {
    BORRADOR("Borrador"),
    PENDIENTE_REVISION("Pendiente de revisión"),
    ELIMINADA("Eliminada");

    private final String label;

    QuestionState(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
