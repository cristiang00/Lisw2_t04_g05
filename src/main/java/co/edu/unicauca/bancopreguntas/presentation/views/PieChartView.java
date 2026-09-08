package co.edu.unicauca.bancopreguntas.presentation.views;

import co.edu.unicauca.bancopreguntas.domain.entities.QuestionState;
import co.edu.unicauca.bancopreguntas.presentation.model.QuestionObservable;
import co.edu.unicauca.bancopreguntas.transversal.observer.IObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.Map;

/**
 * Vista que dibuja un gráfico de pastel con la distribución de los estados.
 * Implementa IObserver para actualizarse cuando el modelo cambia.
 */
public class PieChartView extends JPanel implements IObserver {

    private final QuestionObservable model;
    private Map<QuestionState, Long> stats;

    public PieChartView(QuestionObservable model) {
        this.model = model;
        this.model.addObserver(this); // Registrarse como observador
        setBorder(BorderFactory.createTitledBorder("Vista Gráfica (Distribución)"));
        setPreferredSize(new Dimension(300, 300));
        
        // Cargar datos iniciales
        update();
    }

    @Override
    public void update() {
        this.stats = model.getStatistics();
        repaint(); // Forzar el redibujado del panel
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (stats == null) return;
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        long total = 0;
        for (Long count : stats.values()) {
            total += count;
        }

        if (total == 0) {
            g2d.drawString("No hay datos para mostrar", 50, getHeight() / 2);
            return;
        }

        int width = getWidth();
        int height = getHeight();
        int padding = 40;
        int size = Math.min(width, height) - padding * 2;
        int x = (width - size) / 2;
        int y = (height - size) / 2 - 20; // Subir un poco para dejar espacio a la leyenda

        Color[] colors = {
            new Color(100, 149, 237), // Borrador - Azul
            new Color(255, 165, 0),   // Pendiente - Naranja
            new Color(220, 20, 60)    // Eliminada - Rojo
        };

        double startAngle = 0.0;
        int colorIndex = 0;
        
        // Dibujar el pastel
        for (QuestionState state : QuestionState.values()) {
            long count = stats.get(state);
            double angle = (count / (double) total) * 360.0;
            
            Arc2D.Double arc = new Arc2D.Double(x, y, size, size, startAngle, angle, Arc2D.PIE);
            g2d.setColor(colors[colorIndex]);
            g2d.fill(arc);
            
            startAngle += angle;
            colorIndex++;
        }
        
        // Dibujar leyenda
        int legendY = y + size + 20;
        colorIndex = 0;
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        for (QuestionState state : QuestionState.values()) {
            long count = stats.get(state);
            double percent = (count / (double) total) * 100.0;
            String text = String.format("%s: %.1f%%", state.getLabel(), percent);
            
            g2d.setColor(colors[colorIndex]);
            g2d.fillRect(10, legendY, 15, 15);
            g2d.setColor(Color.BLACK);
            g2d.drawString(text, 30, legendY + 12);
            
            legendY += 20;
            colorIndex++;
        }
    }
}
