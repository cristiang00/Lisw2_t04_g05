package co.edu.unicauca.bancopreguntas.domain.services;

import co.edu.unicauca.bancopreguntas.dataaccess.QuestionImplRepository;
import co.edu.unicauca.bancopreguntas.domain.entities.Question;
import co.edu.unicauca.bancopreguntas.domain.entities.QuestionState;
import co.edu.unicauca.bancopreguntas.domain.repositories.IQuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class QuestionServiceTest {

    private IQuestionRepository repository;
    private QuestionService service;

    @BeforeEach
    void setUp() {
        repository = new QuestionImplRepository();
        service = new QuestionService(repository);
    }

    // ==================== Tests de consulta ====================

    @Test
    @DisplayName("Obtener todas las preguntas retorna 5 preguntas precargadas")
    void testGetAllQuestions() {
        List<Question> questions = service.getAllQuestions();
        assertNotNull(questions);
        assertEquals(5, questions.size(), "Debería haber 5 preguntas iniciales");
    }

    @Test
    @DisplayName("La lista de preguntas no está vacía")
    void testGetAllQuestionsNotEmpty() {
        List<Question> questions = service.getAllQuestions();
        assertFalse(questions.isEmpty(), "La lista de preguntas no debería estar vacía");
    }

    @Test
    @DisplayName("Buscar pregunta por ID existente retorna la pregunta correcta")
    void testGetQuestionById() {
        Question q = service.getQuestionById("P-001");
        assertNotNull(q);
        assertEquals("Pregunta sobre DDD", q.getName());
    }

    @Test
    @DisplayName("Buscar pregunta P-002 retorna 'Patrones de diseño Creacionales'")
    void testGetQuestionByIdP002() {
        Question q = service.getQuestionById("P-002");
        assertNotNull(q);
        assertEquals("Patrones de diseño Creacionales", q.getName());
        assertEquals(QuestionState.PENDIENTE_REVISION, q.getState());
    }

    @Test
    @DisplayName("Buscar pregunta por ID inexistente retorna null")
    void testGetQuestionByIdNotFound() {
        Question q = service.getQuestionById("P-999");
        assertNull(q);
    }

    @Test
    @DisplayName("Buscar pregunta con ID vacío retorna null")
    void testGetQuestionByIdEmpty() {
        Question q = service.getQuestionById("");
        assertNull(q);
    }

    // ==================== Tests de cambio de estado ====================

    @Test
    @DisplayName("Cambiar estado de BORRADOR a PENDIENTE_REVISION persiste correctamente")
    void testChangeStateBorradorToPendiente() {
        assertEquals(QuestionState.BORRADOR, service.getQuestionById("P-001").getState());
        service.changeState("P-001", QuestionState.PENDIENTE_REVISION);
        assertEquals(QuestionState.PENDIENTE_REVISION, service.getQuestionById("P-001").getState());
    }

    @Test
    @DisplayName("Cambiar estado de BORRADOR a ELIMINADA persiste correctamente")
    void testChangeStateBorradorToEliminada() {
        assertEquals(QuestionState.BORRADOR, service.getQuestionById("P-003").getState());
        service.changeState("P-003", QuestionState.ELIMINADA);
        assertEquals(QuestionState.ELIMINADA, service.getQuestionById("P-003").getState());
    }

    @Test
    @DisplayName("Cambiar estado con nuevo estado null no modifica la pregunta")
    void testChangeStateWithNullState() {
        QuestionState originalState = service.getQuestionById("P-001").getState();
        service.changeState("P-001", null);
        assertEquals(originalState, service.getQuestionById("P-001").getState());
    }

    @Test
    @DisplayName("Cambiar estado múltiples veces consecutivas funciona correctamente")
    void testChangeStateMultipleTimes() {
        service.changeState("P-001", QuestionState.PENDIENTE_REVISION);
        assertEquals(QuestionState.PENDIENTE_REVISION, service.getQuestionById("P-001").getState());

        service.changeState("P-001", QuestionState.ELIMINADA);
        assertEquals(QuestionState.ELIMINADA, service.getQuestionById("P-001").getState());

        service.changeState("P-001", QuestionState.BORRADOR);
        assertEquals(QuestionState.BORRADOR, service.getQuestionById("P-001").getState());
    }

    // ==================== Tests de estadísticas ====================

    @Test
    @DisplayName("Estadísticas iniciales: 2 Borrador, 2 Pendiente, 1 Eliminada")
    void testGetStatisticsInitial() {
        Map<QuestionState, Long> stats = service.getStatistics();
        assertEquals(2L, stats.get(QuestionState.BORRADOR));
        assertEquals(2L, stats.get(QuestionState.PENDIENTE_REVISION));
        assertEquals(1L, stats.get(QuestionState.ELIMINADA));
    }

    @Test
    @DisplayName("Estadísticas se actualizan tras cambiar un estado")
    void testGetStatisticsAfterStateChange() {
        service.changeState("P-001", QuestionState.ELIMINADA);
        Map<QuestionState, Long> stats = service.getStatistics();
        assertEquals(1L, stats.get(QuestionState.BORRADOR));
        assertEquals(2L, stats.get(QuestionState.PENDIENTE_REVISION));
        assertEquals(2L, stats.get(QuestionState.ELIMINADA));
    }
}
