package com.julio_hortenciohotmail.projeto_game;

import android.content.SharedPreferences;

import com.julio_hortenciohotmail.projeto_game.AndGraph.AGGameManager;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGInputManager;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGScene;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGScreenManager;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGSprite;



 public class TelaScore extends AGScene {
    //Atributos da classe: Placar, imagem score e a variavel para armazenar o score
    AGSprite[] vrPlacar = null;
    int score;
    AGSprite voltar = null;


    //Construtor da classe
    public TelaScore(AGGameManager pManager) {
        super(pManager);
    }

    @Override
    public void init() {
        //Seta o fundo como branco
        setSceneBackgroundColor(1,1,1);



        voltar = createSprite(R.drawable.voltar,1,1);
        voltar.setScreenPercent(50,10); // 50 lagura e 15 e a altura
        voltar.vrPosition.setXY(AGScreenManager.iScreenWidth/2, AGScreenManager.iScreenHeight/6);
        voltar.fadeIn(3000);

        //6 posições para o placar
        vrPlacar = new AGSprite[6];
        //Cria os números do placar
        for(int index = 0; index < vrPlacar.length; index++){
            //cria um número
            vrPlacar[index] = createSprite(R.drawable.fonte, 4,4);
            //Seta a porcentagem
            vrPlacar[index].setScreenPercent(10,8);
            //não desenha
            vrPlacar[index].bAutoRender = false;
            //Adiciona a animação
            for(int pos = 0; pos < 10; pos++){
                vrPlacar[index].addAnimation(1, false, pos);
            }

            //Posiciona o primeiro número
            if(index == 0){
                vrPlacar[index].vrPosition.setXY(AGScreenManager.iScreenWidth / 2 - vrPlacar[0].getSpriteWidth(), AGScreenManager.iScreenHeight /2);
            }
            //Posiciona os demias de acordo com o primeiro
            else{
                vrPlacar[index].vrPosition.setXY(vrPlacar[index -1].vrPosition.fX + vrPlacar[index].getSpriteWidth()/2, AGScreenManager.iScreenHeight /2);
            }
        }
        //Método para ler o banco que é criado pelo jogo
        leituraDadosArquivo();
        //Atualiza o placar
        atualizarPlacar();
    }

    //Render para desenhar a cena
    public void render(){
        //Desenha os objetos que estão com o render automatico
        super.render();
        //desenha o placar
        desenhaPlacar();
    }
    //Método para desehar o placar na tela
    void desenhaPlacar(){
        //Loopong passando por cada número
        for(int index = 0; index < vrPlacar.length; index++){
            //desenha os números 1 por 1
            vrPlacar[index].render();
        }
    }

    //Método para ler o banco de dados criado pelo app
    public void leituraDadosArquivo(){
        //Cria o objeto que referencia o banco de dados do app
        SharedPreferences vrShared = vrGameManager.vrActivity.getSharedPreferences("BD_SuperHomem", vrGameManager.vrActivity.MODE_PRIVATE);
        //Se estiver salvo algum score
        if (vrShared.contains("score")){
            //Adiciona o score que esta salvo na variavel
           score = vrShared.getInt("score", 0);
        }
    }
    //Método para atualizar o placar: pegando cada número do score e adicionando no vetor para que possa ser desenhado posteriormente
    private void atualizarPlacar(){
        vrPlacar[5].setCurrentAnimation((int) score % 10);
        vrPlacar[4].setCurrentAnimation((int) score % 100 / 10);
        vrPlacar[3].setCurrentAnimation((int) score % 1000 / 100);
        vrPlacar[2].setCurrentAnimation((int) score % 10000 / 1000);
        vrPlacar[1].setCurrentAnimation((int) score % 100000 / 10000);
        vrPlacar[0].setCurrentAnimation((int) score / 100000);
    }

    @Override
    public void restart() {
        init();
    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {

        if (AGInputManager.vrTouchEvents.backButtonClicked()){
            vrGameManager.setCurrentScene(1);
            return;
        }
        if (AGInputManager.vrTouchEvents.screenClicked()) {
            if(voltar.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                vrGameManager.setCurrentScene(1);
                return;
            }
        }
    }
}
