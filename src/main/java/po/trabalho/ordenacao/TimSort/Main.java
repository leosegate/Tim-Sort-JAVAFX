package po.trabalho.ordenacao.TimSort;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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
    private Label[] linhasCodigoInsertion;
    private Label[] linhasCodigoMerge;
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
        button.setLayoutY(50);
        button.setMinHeight(40);
        button.setMinWidth(40);
        button.setFont(new Font(14));
        button.getStyleClass().add("button-success");
        return button;
    }

    private Button criaCaixaVetorTimSort(int distancia) {
        Button button = new Button();
        button.setLayoutX(distancia);
        button.setLayoutY(130);
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
        button.setLayoutX(1);
        button.setLayoutY(300);
        button.setText("Iniciar Tim Sort");
        button.setOnAction(e -> {
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
        linhaSelecionada(linhasCodigoInsertion[0]);
        if(i == comeco) {
            insertionSort(comeco, fim, i + 1, botaoFake);
            return;
        } else if (i < fim){
            linhaNormal(linhasCodigoInsertion[0]);
            botaoFake.setText(vetorBotoes[i].getText());
            botaoFake.setLayoutX(vetorBotoes[i].getLayoutX());
            botaoFake.setStyle("-fx-background-color: #930000");
            botaoFake.setVisible(true);

            selecionarBotaoFake(botaoFake, () -> {
                vetorBotoes[i].setVisible(false);
                int temp = strToInt(vetorBotoes[i].getText());
                linhaSelecionada(linhasCodigoInsertion[1]);
                linhaNormal(linhasCodigoInsertion[0]);
                insertionSortAndar(botaoFake, i, i - 1, temp, comeco, fim);
                return;
            });
        } else {
            if (botaoFake == this.botaoFake) {
                insertion1Terminou = true;
                insertionSort(5, 10, 5, botaoFake2);
            } else if (botaoFake == this.botaoFake2) {
                insertion2Terminou = true;
            }
            verificarInsertionSorts();
        }
    }

    private void insertionSortAndar(Button botaoFake, int i, int j, int temp, int comeco, int fim) {
        linhaNormal(linhasCodigoInsertion[1]);
        if (j >= comeco && strToInt(vetorBotoes[j].getText()) > temp) {
            linhaSelecionada(linhasCodigoInsertion[2]);
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
        //insertionSort(5, 10, 5, botaoFake2);

    }

    private void IniciarPane() {
        botao_inicio = comecarOrdenacao();
        pane.getChildren().add(botao_inicio);
        vetorBotoes = gerarBotoes();
        vetorBotoesOrganizados = posicionarVetorTimSort();
        botaoFake = criarBotaoFake();
        botaoFake2 = criarBotaoFake();
        setLinhasCodigoInsertion();
        VBox linhasInsertion = organizarLinhas(linhasCodigoInsertion);
        linhasInsertion.getStyleClass().add("box-insertionsort");
        linhasInsertion.setLayoutY(350);
        pane.getChildren().add(linhasInsertion);

        setLinhasCodigoMerge();
        VBox linhasMerge = organizarLinhas(linhasCodigoMerge);
        linhasMerge.getStyleClass().add("box-insertionsort");
        linhasMerge.setLayoutY(250);
        linhasMerge.setLayoutX(400);
        pane.getChildren().add(linhasMerge);
    }

    private Label criarLinhaCodigo(String text) {
        Label linha = new Label(text);
        linhaNormal(linha);
        return linha;
    }

    private void linhaSelecionada(Label linha) {
        linha.getStyleClass().remove("linha-normal");
        linha.getStyleClass().add("linha-selecionada");
    }

    private void linhaNormal(Label linha) {
        linha.getStyleClass().remove("linha-selecionada");
        linha.getStyleClass().add("linha-normal");
    }

    private void setLinhasCodigoInsertion() {
        linhasCodigoInsertion = new Label[10];
        linhasCodigoInsertion[0] = criarLinhaCodigo("for (int i = comeco + 1; i < fim; i++) {");
        linhasCodigoInsertion[1] = criarLinhaCodigo("    int temp = vetor[i];");
        linhasCodigoInsertion[2] = criarLinhaCodigo("    int j = i - 1;");
        linhasCodigoInsertion[3] = criarLinhaCodigo("    while (j >= comeco && vetor[j] > temp) {");
        linhasCodigoInsertion[4] = criarLinhaCodigo("        vetor[j + 1] = vetor[j];");
        linhasCodigoInsertion[5] = criarLinhaCodigo("        j--;");
        linhasCodigoInsertion[6] = criarLinhaCodigo("    }");
        linhasCodigoInsertion[7] = criarLinhaCodigo("    j++;");
        linhasCodigoInsertion[8] = criarLinhaCodigo("    j = temp");
        linhasCodigoInsertion[9] = criarLinhaCodigo("}");
    }

    private void setLinhasCodigoMerge() {
        linhasCodigoMerge = new Label[20];
        linhasCodigoMerge[0] = criarLinhaCodigo("while(i < 5 && j < 10) {");
        linhasCodigoMerge[1] = criarLinhaCodigo("    if(insertion[i] > insertion[j]) {");
        linhasCodigoMerge[2] = criarLinhaCodigo("        merge[k] = insertion[j];");
        linhasCodigoMerge[3] = criarLinhaCodigo("        j++;");
        linhasCodigoMerge[4] = criarLinhaCodigo("    } else {");
        linhasCodigoMerge[5] = criarLinhaCodigo("        merge[k] = insertion[i];");
        linhasCodigoMerge[6] = criarLinhaCodigo("        i++;");
        linhasCodigoMerge[7] = criarLinhaCodigo("    }");
        linhasCodigoMerge[8] = criarLinhaCodigo("}");
        linhasCodigoMerge[9] = criarLinhaCodigo("if(i == 5) {");
        linhasCodigoMerge[10] = criarLinhaCodigo("    while(j < 10) {");
        linhasCodigoMerge[11] = criarLinhaCodigo("        merge[k] = insertion[j];");
        linhasCodigoMerge[12] = criarLinhaCodigo("        j++;");
        linhasCodigoMerge[13] = criarLinhaCodigo("    }");
        linhasCodigoMerge[14] = criarLinhaCodigo("} else if(i < 5) {");
        linhasCodigoMerge[15] = criarLinhaCodigo("    while(i < 5) {");
        linhasCodigoMerge[16] = criarLinhaCodigo("        merge[k] = insertion[i];");
        linhasCodigoMerge[17] = criarLinhaCodigo("        i++;");
        linhasCodigoMerge[18] = criarLinhaCodigo("    }");
        linhasCodigoMerge[19] = criarLinhaCodigo("}");
    }

    private VBox organizarLinhas(Label[] linhasCodigo) {
        VBox linhas = new VBox();

        for (Label linha : linhasCodigo)
            linhas.getChildren().add(linha);
        return linhas;
    }

    public void remanejar(int k, Runnable depois) {
        Task<Void> task = new Task<Void>(){
            @Override
            protected Void call() {
                //permutação na tela
                linhaNormal(linhasCodigoInsertion[2]);
                linhaSelecionada(linhasCodigoInsertion[3]);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                linhaNormal(linhasCodigoInsertion[3]);
                linhaSelecionada(linhasCodigoInsertion[4]);
                for (int i = 0; i < 16; i++) {// for pra jogar os numeros pro lado
                    Platform.runLater(() -> vetorBotoes[k].setLayoutX(vetorBotoes[k].getLayoutX() + 5));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                linhaNormal(linhasCodigoInsertion[4]);
                linhaSelecionada(linhasCodigoInsertion[5]);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                linhaNormal(linhasCodigoInsertion[5]);
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
                linhaSelecionada(linhasCodigoInsertion[7]);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                linhaNormal(linhasCodigoInsertion[7]);
                linhaSelecionada(linhasCodigoInsertion[8]);
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
                linhaNormal(linhasCodigoInsertion[8]);
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
