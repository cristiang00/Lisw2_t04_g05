package co.edu.unicauca.bancopreguntas;

import co.edu.unicauca.bancopreguntas.dataaccess.QuestionImplRepository;
import co.edu.unicauca.bancopreguntas.domain.repositories.IQuestionRepository;
import co.edu.unicauca.bancopreguntas.domain.services.QuestionService;
import co.edu.unicauca.bancopreguntas.presentation.controllers.QuestionController;
import co.edu.unicauca.bancopreguntas.presentation.model.QuestionObservable;
import co.edu.unicauca.bancopreguntas.presentation.views.MainView;
import co.edu.unicauca.bancopreguntas.presentation.views.PieChartView;
import co.edu.unicauca.bancopreguntas.presentation.views.StatisticsView;

import javax.swing.*;

/**
 * Clase principal que inicializa la aplicación.
 * Ensambla las capas, configura el patrón MVC y el patrón Observer.
 */
public class Main {
    public static void main(String[] args) {
        // Ejecutar en el Event Dispatch Thread de Swing
        SwingUtilities.invokeLater(() -> {
            try {
                // Configurar el look and feel del sistema
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 1. Capa de Acceso a Datos
            IQuestionRepository repository = new QuestionImplRepository();

            // 2. Capa de Dominio
            QuestionService questionService = new QuestionService(repository);

            // 3. Capa de Presentación - Modelo Observable
            QuestionObservable questionObservable = new QuestionObservable(questionService);

            // 4. Capa de Presentación - Controlador
            QuestionController controller = new QuestionController(questionService, questionObservable);

            // 5. Capa de Presentación - Vistas
            MainView mainView = new MainView(controller);
            StatisticsView statisticsView = new StatisticsView(questionObservable);
            PieChartView pieChartView = new PieChartView(questionObservable);

            // 6. Configurar la ventana principal para mostrar todas las vistas
            // MainView ya tiene un BorderLayout, así que solo agregamos al lado EAST
            
            // Reorganizamos la ventana principal para alojar los observadores a los lados
            javax.swing.JPanel observersPanel = new javax.swing.JPanel();
            observersPanel.setLayout(new java.awt.GridLayout(2, 1, 10, 10));
            observersPanel.add(statisticsView);
            observersPanel.add(pieChartView);
            
            mainView.add(observersPanel, java.awt.BorderLayout.EAST);
            
            // Ajustar tamaño para que quepan todos los componentes
            mainView.pack();
            mainView.setLocationRelativeTo(null); // Centrar en pantalla
            mainView.setVisible(true);
        });
    }
}
