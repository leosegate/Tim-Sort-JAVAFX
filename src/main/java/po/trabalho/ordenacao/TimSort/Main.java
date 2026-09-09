package po.trabalho.ordenacao.TimSort;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class Main extends Application {
    //AnchorPane pane;
    Button botao_inicio;
    private Button vet[];

    public static void main(String[] args) {
        launch(args);
    }

    private final AnchorPane pane = new AnchorPane();

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pesquisa e Ordenacao");
        IniciarPane();
        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private int[] gerarNumeros() {
        int[] inteiro = new int[10];
        for(int i = 0; i < 10; i++)
            inteiro[i] = (int)(Math.random() * 101); // 0 a 100
        return inteiro;
    }

    private Button[] teste() {

        Button buttons[] = new Button[10];
        int[] sequencia = gerarNumeros();
        int espacamento = 100;
        for(int i = 0; i < 10; i++ , espacamento = espacamento + 80) {
            buttons[i] = criaCaixa("" + sequencia[i], espacamento);
            pane.getChildren().add(buttons[i]);
        }

        /*
        vet = new Button[2]; -> criou um vetor de botao;
        vet[0] = criaCaixa("10", 100);
        pane.getChildren().add(vet[0]);
        vet[1] = criaCaixa("20", 180);
        pane.getChildren().add(vet[1]);
        */
        return buttons;
    }

    private Button criaCaixa(String numero, int distancia) {
        Button button = new Button(numero);
        button.setLayoutX(distancia);
        button.setLayoutY(200);
        button.setMinHeight(40);
        button.setMinWidth(40);
        button.setFont(new Font(14));
        return button;
    }

    private Button comecarOrdenacao() {
        Button button = new Button();
        button.setLayoutX(10);
        button.setLayoutY(100);
        button.setText("Inicia...");
        button.setOnAction(e -> {
            move_botoes();
        });
        return button;
    }

    private void IniciarPane() {
        botao_inicio = comecarOrdenacao();
        pane.getChildren().add(botao_inicio);

        vet = teste();

        /*
        vet = new Button[2];
        vet[0] = criaCaixa("10", 100);
        pane.getChildren().add(vet[0]);
        vet[1] = criaCaixa("20", 180);
        pane.getChildren().add(vet[1]); */
    }

    public void move_botoes()
    {
        Task<Void> task = new Task<Void>(){
            @Override
            protected Void call() {
                //permutação na tela
                for (int i = 0; i < 10; i++) {
                    Platform.runLater(() -> vet[0].setLayoutY(vet[0].getLayoutY() + 5));
                    Platform.runLater(() -> vet[1].setLayoutY(vet[1].getLayoutY() - 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 16; i++) {
                    Platform.runLater(() -> vet[0].setLayoutX(vet[0].getLayoutX() + 5));
                    Platform.runLater(() -> vet[1].setLayoutX(vet[1].getLayoutX() - 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 10; i++) {
                    Platform.runLater(() -> vet[0].setLayoutY(vet[0].getLayoutY() - 5));
                    Platform.runLater(() -> vet[1].setLayoutY(vet[1].getLayoutY() + 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //permutação na memória
                Button aux = vet[0];
                vet[0] = vet[1];
                vet[1] = aux;
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }
}
