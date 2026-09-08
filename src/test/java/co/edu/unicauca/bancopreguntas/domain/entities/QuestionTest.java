package co.edu.unicauca.bancopreguntas.domain.entities;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    @Test
    void testQuestionCreation() {
        // Arrange
        String id = "P-100";
        String name = "Test Name";
        String text = "Test Question?";
        List<String> options = Arrays.asList("A", "B", "C", "D");
        String correctAnswer = "A";
        QuestionState state = QuestionState.BORRADOR;

        // Act
        Question question = new Question(id, name, text, options, correctAnswer, state);

        // Assert
        assertEquals(id, question.getId());
        assertEquals(name, question.getName());
        assertEquals(text, question.getQuestionText());
        assertEquals(options, question.getOptions());
        assertEquals(correctAnswer, question.getCorrectAnswer());
        assertEquals(state, question.getState());
    }

    @Test
    void testChangeState() {
        // Arrange
        Question question = new Question("P-100", "Name", "Text", null, "A", QuestionState.BORRADOR);

        // Act
        question.setState(QuestionState.PENDIENTE_REVISION);

        // Assert
        assertEquals(QuestionState.PENDIENTE_REVISION, question.getState());
    }

    @Test
    void testQuestionStateEnumValues() {
        assertEquals("Borrador", QuestionState.BORRADOR.getLabel());
        assertEquals("Pendiente de revisión", QuestionState.PENDIENTE_REVISION.getLabel());
        assertEquals("Eliminada", QuestionState.ELIMINADA.getLabel());
    }
}
