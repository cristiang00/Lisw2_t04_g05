package co.edu.unicauca.bancopreguntas.presentation.views;

import co.edu.unicauca.bancopreguntas.domain.entities.QuestionState;
import co.edu.unicauca.bancopreguntas.presentation.model.QuestionObservable;
import co.edu.unicauca.bancopreguntas.transversal.observer.IObserver;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Vista que muestra las estadísticas de las preguntas por estado.
 * Implementa IObserver para actualizarse cuando el modelo cambia.
 */
public class StatisticsView extends JPanel implements IObserver {

    private final QuestionObservable model;
    private final JTextArea textArea;

    public StatisticsView(QuestionObservable model) {
        this.model = model;
        this.model.addObserver(this); // Registrarse como observador
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Vista de Estadísticas"));

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setMargin(new Insets(10, 10, 10, 10));

        add(new JScrollPane(textArea), BorderLayout.CENTER);
        
        // Cargar datos iniciales
        update();
    }

    @Override
    public void update() {
        Map<QuestionState, Long> stats = model.getStatistics();
        StringBuilder sb = new StringBuilder();
        sb.append("Preguntas por estado\n");
        sb.append("----------------------\n");
        
        for (QuestionState state : QuestionState.values()) {
            sb.append(state.getLabel()).append(": ").append(stats.get(state)).append("\n");
        }
        
        textArea.setText(sb.toString());
    }
}
