package po.trabalho.ordenacao.TimSort;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class Main extends Application {
    Thread thread;
    Button botao_inicio;
    Button botaoFake;
    Button botaoFake2;
    private int mergeK = 0;
    private int insertionI = 0;
    private int insertionJ = 5;
    private boolean insertion1Terminou = false;
    private boolean insertion2Terminou = false;
    private Button vetorBotoes[];
    private Button vetorBotoesOrganizados[];

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

    private Button[] posicionarVetorTimSort() {
        Button buttons[] = new Button[10];
        int espacamento = 10;
        for(int i = 0; i < 10; i++ , espacamento = espacamento + 80) {
            buttons[i] = criaCaixaVetorTimSort(espacamento);
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

    private Button criaCaixaVetorTimSort(int distancia) {
        Button button = new Button();
        button.setLayoutX(distancia);
        button.setLayoutY(280);
        button.setMinHeight(40);
        button.setMinWidth(40);
        button.setFont(new Font(14));
        button.getStyleClass().add("button-success");
        button.setVisible(false);
        return button;
    }

    private Button criarBotaoFake() {
        Button botao = criaCaixa("", 100);
        botao.setVisible(false);
        pane.getChildren().add(botao);
        return botao;
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

    private void insertionSort(int comeco, int fim, int i, Button botaoFake) {
        if(i == comeco) {
            insertionSort(comeco, fim, i + 1, botaoFake);
            return;
        } else if (i < fim){
            botaoFake.setText(vetorBotoes[i].getText());
            botaoFake.setLayoutX(vetorBotoes[i].getLayoutX());
            botaoFake.setStyle("-fx-background-color: #930000");
            botaoFake.setVisible(true);

            selecionarBotaoFake(botaoFake, () -> {
                vetorBotoes[i].setVisible(false);
                int temp = strToInt(vetorBotoes[i].getText());
                insertionSortAndar(botaoFake, i, i - 1, temp, comeco, fim);
                return;
            });
        } else {
            if (botaoFake == this.botaoFake)
                insertion1Terminou = true;
            else if (botaoFake == this.botaoFake2) {
                insertion2Terminou = true;
            }
            verificarInsertionSorts();
        }
    }

    private void insertionSortAndar(Button botaoFake, int i, int j, int temp, int comeco, int fim) {
        if (j >= comeco && strToInt(vetorBotoes[j].getText()) > temp) {
            int posicao = j;
            double x = vetorBotoes[j].getLayoutX();
            double y = vetorBotoes[j]. getLayoutY();
            remanejar(j, () -> {
                vetorBotoes[posicao + 1].setText(vetorBotoes[posicao].getText());
                vetorBotoes[posicao].setVisible(false);
                vetorBotoes[posicao].setLayoutX(x);
                vetorBotoes[posicao].setLayoutY(y);
                vetorBotoes[posicao + 1].setVisible(true);
                insertionSortAndar(botaoFake, i, j - 1, temp, comeco, fim);
            });

        } else {
            int destino = j + 1;
            int distancia = i - destino;
            inserirBotaoFake(botaoFake, distancia, () -> {
                vetorBotoes[destino].setText(temp + "");
                vetorBotoes[destino].setVisible(true);
                vetorBotoes[i].setVisible(true);
                vetorBotoes[i].setStyle("-fx-background-color: #cc922e");
                botaoFake.setVisible(false);
                insertionSort(comeco, fim, i + 1, botaoFake);
            });
        }
    }

    private void verificarInsertionSorts() {
        if (insertion1Terminou && insertion2Terminou) {
            mergeSort();
        }
    }

    private void mergeSort() {
        System.out.println("merge pode comecar");
        if(insertionI < 5 && insertionJ < 10) {
            System.out.println("teste");
            if(strToInt(vetorBotoes[insertionI].getText()) > strToInt(vetorBotoes[insertionJ].getText())) {
                vetorBotoesOrganizados[mergeK].setText(vetorBotoes[insertionJ].getText());
                mergeSortAnimacao(insertionJ, mergeK, true);
            } else {
                vetorBotoesOrganizados[mergeK].setText(vetorBotoes[insertionI].getText());
                mergeSortAnimacao(insertionI, mergeK, false);
            }
        } else if(insertionI == 5) {
            if(insertionJ < 10) {
                vetorBotoesOrganizados[mergeK].setText(vetorBotoes[insertionJ].getText());
                mergeSortAnimacao(insertionJ, mergeK, true);
            }
        } else if(insertionI<5) {
                vetorBotoesOrganizados[mergeK].setText(vetorBotoes[insertionI].getText());
                mergeSortAnimacao(insertionI, mergeK, false);
            }
    }

    private void mergeSortAnimacao(int i, int k, boolean veioDaDireita) {
        inserirMergeSort(vetorBotoes[i], vetorBotoesOrganizados[k], () -> {
            mergeK++;
            if(veioDaDireita)
                insertionJ++;
            else
                insertionI++;
            mergeSort();
        });
    }

    private void timSort() {
        insertionSort(0, 5, 0, botaoFake);
        insertionSort(5, 10, 5, botaoFake2);

    }

    private void IniciarPane() {
        botao_inicio = comecarOrdenacao();
        pane.getChildren().add(botao_inicio);
        vetorBotoes = gerarBotoes();
        vetorBotoesOrganizados = posicionarVetorTimSort();
        botaoFake = criarBotaoFake();
        botaoFake2 = criarBotaoFake();
    }

    public void remanejar(int k, Runnable depois) {
        Task<Void> task = new Task<Void>(){
            @Override
            protected Void call() {
                //permutação na tela
                for (int i = 0; i < 16; i++) { // for pra jogar os numeros pro lado
                    Platform.runLater(() -> vetorBotoes[k].setLayoutX(vetorBotoes[k].getLayoutX() + 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Platform.runLater(depois);
                return null;
            }
        };
        thread = new Thread(task);
        thread.start();
    }

    public void inserirMergeSort(Button botaoInsertion, Button botaoMerge, Runnable depois) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                for (int x = 0; x < 16; x++) {
                    Platform.runLater(() -> {
                        botaoInsertion.setLayoutY(botaoInsertion.getLayoutY() + 5);
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return null;
                    }
                }

                double xAtual = botaoInsertion.getLayoutX();
                double xDestino = botaoMerge.getLayoutX();

                if (xAtual < xDestino) {
                    int distancia = (int) (xDestino - xAtual);
                    for (int x = 0; x < distancia / 5; x++) {
                        Platform.runLater(() -> {
                            botaoInsertion.setLayoutX(botaoInsertion.getLayoutX() + 5);
                        });
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return null;
                        }
                    }

                } else if (xAtual > xDestino) {
                    int distancia = (int) (xAtual - xDestino);
                    for (int x = 0; x < distancia / 5; x++) {
                        Platform.runLater(() -> {
                            botaoInsertion.setLayoutX(botaoInsertion.getLayoutX() - 5);
                        });
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return null;
                        }
                    }
                }

                Platform.runLater(() -> {
                    botaoInsertion.setLayoutX(xDestino);
                    botaoInsertion.setLayoutY(botaoMerge.getLayoutY());
                    botaoInsertion.setVisible(false);
                    botaoMerge.setVisible(true);
                });
                Platform.runLater(depois);
                return null;
            }
        };
        thread = new Thread(task);
        thread.start();
    }

    public void selecionarBotaoFake(Button botaoFake ,Runnable depois) {
        Task<Void> task = new Task<Void>(){
            @Override
            protected Void call() {
                //permutação na tela
                for (int i = 0; i < 8; i++) { // for pra abaixar os numeros
                    Platform.runLater(() -> botaoFake.setLayoutY(botaoFake.getLayoutY() - 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Platform.runLater(depois);
                return null;
            }
        };
        thread = new Thread(task);
        thread.start();
    }

    public void inserirBotaoFake(Button botaoFake,int distancia, Runnable depois) {

        Task<Void> task = new Task<Void>(){
            @Override
            protected Void call() {
                //permutação na tela
                for (int i = 0; i < 16 * distancia; i++) { // for pra jogar os numeros pro lado
                    Platform.runLater(() -> botaoFake.setLayoutX(botaoFake.getLayoutX() - 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 8; i++) { // for pra abaixar os numeros
                    Platform.runLater(() -> botaoFake.setLayoutY(botaoFake.getLayoutY() + 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Platform.runLater(depois);
                return null;
            }
        };
        thread = new Thread(task);
        thread.start();
    }

    public void move_botoes(int k, int j, Runnable depois) {
        Task<Void> task = new Task<Void>(){
            @Override
            protected Void call() {
                //permutação na tela
                for (int i = 0; i < 5; i++) { // for pra subir o numero
                    Platform.runLater(() -> vetorBotoes[k].setLayoutY(vetorBotoes[k].getLayoutY() + 5));
                    Platform.runLater(() -> vetorBotoes[j].setLayoutY(vetorBotoes[j].getLayoutY() - 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 16; i++) { // for pra jogar os numeros pro lado
                    Platform.runLater(() -> vetorBotoes[k].setLayoutX(vetorBotoes[k].getLayoutX() + 5));
                    Platform.runLater(() -> vetorBotoes[j].setLayoutX(vetorBotoes[j].getLayoutX() - 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < 5; i++) { // for pra abaixar os numeros
                    Platform.runLater(() -> vetorBotoes[k].setLayoutY(vetorBotoes[k].getLayoutY() - 5));
                    Platform.runLater(() -> vetorBotoes[j].setLayoutY(vetorBotoes[j].getLayoutY() + 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Platform.runLater(depois);
                return null;
            }
        };
        thread = new Thread(task);
        thread.start();
    }
}
