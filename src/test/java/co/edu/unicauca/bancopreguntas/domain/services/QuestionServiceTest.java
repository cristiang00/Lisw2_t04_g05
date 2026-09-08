package co.edu.unicauca.bancopreguntas.domain.services;

import co.edu.unicauca.bancopreguntas.dataaccess.QuestionImplRepository;
import co.edu.unicauca.bancopreguntas.domain.entities.Question;
import co.edu.unicauca.bancopreguntas.domain.entities.QuestionState;
import co.edu.unicauca.bancopreguntas.domain.repositories.IQuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class QuestionServiceTest {

    private IQuestionRepository repository;
    private QuestionService service;

    @BeforeEach
    void setUp() {
        // Usamos la implementación real en memoria que ya tiene datos pre-cargados
        repository = new QuestionImplRepository();
        service = new QuestionService(repository);
    }

    @Test
    void testGetAllQuestions() {
        List<Question> questions = service.getAllQuestions();
        assertNotNull(questions);
        assertEquals(5, questions.size(), "Debería haber 5 preguntas iniciales");
    }

    @Test
    void testGetQuestionById() {
        Question q = service.getQuestionById("P-001");
        assertNotNull(q);
        assertEquals("Pregunta sobre DDD", q.getName());
    }

    @Test
    void testGetQuestionByIdNotFound() {
        Question q = service.getQuestionById("P-999");
        assertNull(q);
    }

    @Test
    void testChangeState() {
        // Arrange
        Question q = service.getQuestionById("P-001"); // Inicialmente es BORRADOR
        assertEquals(QuestionState.BORRADOR, q.getState());

        // Act
        service.changeState("P-001", QuestionState.PENDIENTE_REVISION);

        // Assert
        Question updated = service.getQuestionById("P-001");
        assertEquals(QuestionState.PENDIENTE_REVISION, updated.getState());
    }

    @Test
    void testGetStatistics() {
        // Inicialmente (según QuestionImplRepository): 2 BORRADOR, 2 PENDIENTE, 1 ELIMINADA
        Map<QuestionState, Long> stats = service.getStatistics();
        
        assertEquals(2L, stats.get(QuestionState.BORRADOR));
        assertEquals(2L, stats.get(QuestionState.PENDIENTE_REVISION));
        assertEquals(1L, stats.get(QuestionState.ELIMINADA));
        
        // Cambiar estado y verificar estadísticas
        service.changeState("P-001", QuestionState.ELIMINADA);
        
        stats = service.getStatistics();
        assertEquals(1L, stats.get(QuestionState.BORRADOR));
        assertEquals(2L, stats.get(QuestionState.PENDIENTE_REVISION));
        assertEquals(2L, stats.get(QuestionState.ELIMINADA));
    }
}
