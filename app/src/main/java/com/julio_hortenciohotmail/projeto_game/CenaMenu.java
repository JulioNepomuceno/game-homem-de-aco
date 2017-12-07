package com.julio_hortenciohotmail.projeto_game;


import com.julio_hortenciohotmail.projeto_game.AndGraph.AGGameManager;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGInputManager;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGScene;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGScreenManager;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGSoundManager;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGSprite;

public class CenaMenu extends AGScene {
    AGSprite imagemFundo = null;
    AGSprite vrIniciar = null;
    AGSprite vrOpcoes = null;
    AGSprite vrSobre = null;
    AGSprite vrSair = null;
    AGSprite vrScore = null;
    int estado = 0;
    int som=0;
    int som1 = 0;
    int som2 = 0;
    int som3 = 0;

    public CenaMenu(AGGameManager gerente) {
        super(gerente);
    }

    @Override
    public void init()
    {
        this.imagemFundo = this.createSprite(R.drawable.superhomemvoando, 1, 1);
        imagemFundo.setScreenPercent(100,100);//PERCENTUAL QUE A IMAGEM VAI OCUPAR NA TELA.
        imagemFundo.vrPosition.setXY(AGScreenManager.iScreenWidth/2, AGScreenManager.iScreenHeight/2);//PERCENTUAL QUE A IMAGEM VAI OCUPAR NA TELA.


        this.setSceneBackgroundColor(1,0,0);

        vrIniciar = this.createSprite(R.drawable.jogar, 1,1);
        vrIniciar.setScreenPercent(50,10);
        vrIniciar.vrPosition.setXY(AGScreenManager.iScreenWidth/2, AGScreenManager.iScreenHeight/1.4f);
        vrIniciar.fadeIn(3000);

        vrOpcoes = this.createSprite(R.drawable.opcao, 1,1);
        vrOpcoes.setScreenPercent(50,10);
        vrOpcoes.vrPosition.setXY(AGScreenManager.iScreenWidth/2, AGScreenManager.iScreenHeight/1.7f);
        vrOpcoes.fadeIn(3000);


        vrScore = createSprite(R.drawable.score,1,1);
        vrScore.setScreenPercent(50, 10);
        vrScore.vrPosition.setXY(AGScreenManager.iScreenWidth/2, AGScreenManager.iScreenHeight/2.2f);
        vrScore.fadeIn(3000);

        vrSobre = this.createSprite(R.drawable.sobre,1,1);
        vrSobre.setScreenPercent(50,10);
        vrSobre.vrPosition.setXY(AGScreenManager.iScreenWidth/2, AGScreenManager.iScreenHeight/3.0f);
        vrSobre.fadeIn(3000);

        vrSair = this.createSprite(R.drawable.sair, 1,1);
        vrSair.setScreenPercent(50,10);
        vrSair.vrPosition.setXY(AGScreenManager.iScreenWidth/2, AGScreenManager.iScreenHeight/5);
        vrSair.fadeIn(3000);

        som = AGSoundManager.vrSoundEffects.loadSoundEffect("coragem.mp4");
        som1 = AGSoundManager.vrSoundEffects.loadSoundEffect("pesadao.mp4");
        som2 = AGSoundManager.vrSoundEffects.loadSoundEffect("aiai.mp4");
        som3 = AGSoundManager.vrSoundEffects.loadSoundEffect("show.mp4");

    }

    @Override
    public void restart()
    {

    }

    @Override
    public void stop()
    {

    }

    @Override
    public void loop()
    {
        if(AGInputManager.vrTouchEvents.backButtonClicked())
        {
           vrGameManager.setCurrentScene(0);
            return;
        }
       if(AGInputManager.vrTouchEvents.screenClicked()){

           if(vrIniciar.collide(AGInputManager.vrTouchEvents.getLastPosition())){
               AGSoundManager.vrSoundEffects.play(som);

               vrGameManager.setCurrentScene(2);
               return;
           }

           if(vrSair.collide(AGInputManager.vrTouchEvents.getLastPosition())){
               System.exit(0);
               return;
           }
           if(vrSobre.collide(AGInputManager.vrTouchEvents.getLastPosition())){
               AGSoundManager.vrSoundEffects.play(som1);

               vrGameManager.setCurrentScene(4);
               return;
           }
           if(vrOpcoes.collide(AGInputManager.vrTouchEvents.getLastPosition())){
               AGSoundManager.vrSoundEffects.play(som2);

               vrGameManager.setCurrentScene(3);
               return;
           }
           if(vrScore.collide(AGInputManager.vrTouchEvents.getLastPosition())){
               AGSoundManager.vrSoundEffects.play(som3);

               vrGameManager.setCurrentScene(5);
               return;
           }
       }

    }
}