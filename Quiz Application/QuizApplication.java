import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Question {
    private String questionText;
    private String[] options;
    private int correctOptionIndex;

    public Question(String questionText, String[] options, int correctOptionIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestionText() {
        return this.questionText;
    }

    public String[] getOptions() {
        return this.options;
    }

    public int getCorrectOptionIndex() {
        return this.correctOptionIndex;
    }
}

class Quiz {
    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;
    private Timer timer;

    public Quiz(List<Question> questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0;
        this.score = 0;
    }

    public void startQuiz() {
        if (this.currentQuestionIndex < this.questions.size()) {
            Question currentQuestion = this.questions.get(this.currentQuestionIndex);
            this.displayQuestion(currentQuestion);
            this.startTimer(currentQuestion);
        } else {
            this.endQuiz();
        }
    }

    private void displayQuestion(Question question) {
        System.out.println("Question: " + question.getQuestionText());
        String[] options = question.getOptions();
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }

    private void startTimer(Question question) {
        this.timer = new Timer();
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Time's up!");
                Quiz.this.checkAnswer(-1); // -1 indicates no answer submitted
            }
        }, 30000); // 30 seconds timer for each question
    }

    public void checkAnswer(int selectedOption) {
        this.timer.cancel();
        Question currentQuestion = this.questions.get(this.currentQuestionIndex);
        int correctOption = currentQuestion.getCorrectOptionIndex();

        if (selectedOption == correctOption) {
            this.score++;
            System.out.println("Correct!");
        } else if (selectedOption == -1) {
            System.out.println("Time's up! Correct answer: Option " + (correctOption + 1));
        } else {
            System.out.println("Incorrect. Correct answer: Option " + (correctOption + 1));
        }

        this.currentQuestionIndex++;
        this.startQuiz();
    }

    private void endQuiz() {
        System.out.println("Quiz finished!");
        System.out.println("Your Score: " + this.score + "/" + this.questions.size());
    }
}

public class QuizApplication {
    public static void main(String[] args) {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What is the capital of South Korea?", new String[]{"Seol", "Berlin", "Madrid", "Rome"}, 0));
        questions.add(new Question("Which planet is known as the Red Planet?", new String[]{"Mars", "Venus", "Earth", "Jupiter"}, 0));
        questions.add(new Question("What is 2 + 2?", new String[]{"3", "4", "5", "6"}, 1));

        Quiz quiz = new Quiz(questions);
        quiz.startQuiz();
        Scanner scanner = new Scanner(System.in);

            System.out.print("Enter your answer (1/2/3/4 or 0 to quit): ");
            int choice = scanner.nextInt();

            if (choice == 0) {
                quiz = null;
            } else if (choice >= 1 && choice <= 4) {
                quiz.checkAnswer(choice - 1);
            } else {
                System.out.println("Invalid input. Please enter a valid option.");
            }
        }
    }
}
