package po.trabalho.ordenacao.TimSort;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.util.Objects;

public class Main extends Application {
    //AnchorPane pane;
    Button botao_inicio;
    private Button vetorBotoes[];

    public static void main(String[] args) {
        launch(args);
    }

    private static AnchorPane pane = new AnchorPane();

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pesquisa e Ordenacao");
        IniciarPane();
        Scene scene = new Scene(pane, 1000, 600);
        try {
            String css = this.getClass().getResource("/styles/style.css").toExternalForm();
            scene.getStylesheets().add(css);
        }catch (Exception e){
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.show();
    }

    private int[] gerarNumeros() {
        int[] inteiro = new int[10];
        for(int i = 0; i < 10; i++)
            inteiro[i] = (int)(Math.random() * 101); // 0 a 100
        return inteiro;
    }

    private Button[] gerarBotoes() {
        Button buttons[] = new Button[10];
        int[] sequencia = gerarNumeros();
        int espacamento = 10;
        for(int i = 0; i < 10; i++ , espacamento = espacamento + 80) {
            buttons[i] = criaCaixa("" + sequencia[i], espacamento);
            pane.getChildren().add(buttons[i]);
        }
        return buttons;
    }

    private Button criaCaixa(String numero, int distancia) {
        Button button = new Button(numero);
        button.setLayoutX(distancia);
        button.setLayoutY(200);
        button.setMinHeight(40);
        button.setMinWidth(40);
        button.setFont(new Font(14));
        button.getStyleClass().add("button-success");
        return button;
    }

    private Button comecarOrdenacao() {
        Button button = new Button();
        button.setLayoutX(10);
        button.setLayoutY(100);
        button.setText("Inicia...");
        button.setOnAction(e -> {
            //move_botoes();
            timSort();
        });
        return button;
    }

    public static int strToInt(String str) {
        int i = 0;
        int num = 0;

        while (i < str.length()) {
            num *= 10;
            num += str.charAt(i++) - '0';
        }

        return num;
    }

    private void insertionSort(int comeco, int fim, String cor) {
        for(int i = comeco; i < fim; i++)
            vetorBotoes[i].getStyleClass().add(cor);

        for(int i = comeco; i < fim; i++) {
            int temp = strToInt(vetorBotoes[i].getText());
            vetorBotoes[i].getStyleClass().add("buttom-selected");
            if(i != 0) {
                int j = i - 1;
                while(0 <= j && strToInt(vetorBotoes[j].getText()) > temp) {
                    vetorBotoes[j + 1].setText(vetorBotoes[j].getText() + "");
                    j--;
                }
                vetorBotoes[j + 1].setText(temp + "");
            }
            vetorBotoes[i].getStyleClass().add("buttom-cor");
        }
    }

    private void mergeSort() {

    }

    private void timSort() {
        insertionSort(0, 5, ".button-insertion1");
        insertionSort(6, 10, ".button-insertion2");


    }

    private void IniciarPane() {
        botao_inicio = comecarOrdenacao();
        pane.getChildren().add(botao_inicio);
        vetorBotoes = gerarBotoes();
    }

    public void move_botoes()
    {
        Task<Void> task = new Task<Void>(){
            @Override
            protected Void call() {
                //permutação na tela
                for (int i = 0; i < 5; i++) { // for pra subir o numero
                    Platform.runLater(() -> vetorBotoes[0].setLayoutY(vetorBotoes[0].getLayoutY() + 5));
                    Platform.runLater(() -> vetorBotoes[1].setLayoutY(vetorBotoes[1].getLayoutY() - 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 16; i++) { // for pra jogar os numeros pro lado
                    Platform.runLater(() -> vetorBotoes[0].setLayoutX(vetorBotoes[0].getLayoutX() + 5));
                    Platform.runLater(() -> vetorBotoes[1].setLayoutX(vetorBotoes[1].getLayoutX() - 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 5; i++) { // for pra abaixar os numeros
                    Platform.runLater(() -> vetorBotoes[0].setLayoutY(vetorBotoes[0].getLayoutY() - 5));
                    Platform.runLater(() -> vetorBotoes[1].setLayoutY(vetorBotoes[1].getLayoutY() + 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //permutação na memória
                Button aux = vetorBotoes[0];
                vetorBotoes[0] = vetorBotoes[1];
                vetorBotoes[1] = aux;
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }
}
