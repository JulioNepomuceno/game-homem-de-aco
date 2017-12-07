package com.julio_hortenciohotmail.projeto_game;

import com.julio_hortenciohotmail.projeto_game.AndGraph.AGGameManager;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGInputManager;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGScene;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGScreenManager;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGSoundManager;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGSprite;

import java.util.Random;

/**
 * Created by julio on 23/11/17.
 */

public class CenaConfiguracao  extends AGScene
{
    AGSprite imagemfundo = null;
    AGSprite imagemVoltar = null;


    private AGSprite[] recorde = null;
    AGSprite som = null;
    public CenaConfiguracao(AGGameManager gerente){
        super(gerente);
    }

    @Override
    public void init() {
        this.imagemfundo = this.createSprite(R.drawable.fundo, 1, 1);
        imagemfundo.setScreenPercent(100,100);//PERCENTUAL QUE A IMAGEM VAI OCUPAR NA TELA.
        imagemfundo.vrPosition.setXY(AGScreenManager.iScreenWidth/2, AGScreenManager.iScreenHeight/2);//PERCENTUAL QUE A IMAGEM VAI OCUPAR NA TELA.
        //imagemFundo.fadeIn(3000);//ESTADO TRANSPARENTE PARA VISIVEL

        imagemVoltar = createSprite(R.drawable.voltar,1,1);
        imagemVoltar.setScreenPercent(40,10);
        imagemVoltar.vrPosition.setXY(AGScreenManager.iScreenWidth/2 , AGScreenManager.iScreenHeight/6);
        imagemVoltar.fadeIn(3000);

        if(ConfiguracaoMusica.som){
            this.som = this.createSprite(R.drawable.off,1,1);
        }
        else{
            this.som = this.createSprite(R.drawable.som,1,1);
        }

        this.som.setScreenPercent(30,15);
        this.som.vrPosition.setXY(AGScreenManager.iScreenWidth/2, AGScreenManager.iScreenHeight/2);
        this.som.fadeIn(3000);


    }

    @Override
    public void restart() {

    }
    public void render() {
        super.render();

    }
    @Override
    public void stop() {

    }

    @Override
    public void loop() {

        if(AGInputManager.vrTouchEvents.backButtonClicked())
        {
            vrGameManager.setCurrentScene(1);
            return;
        }
        if(imagemVoltar.collide(AGInputManager.vrTouchEvents.getLastPosition())){
            vrGameManager.setCurrentScene(1);
            return;
        }

        if(AGInputManager.vrTouchEvents.screenClicked()){

            if(this.som.collide(AGInputManager.vrTouchEvents.getLastPosition())){
                //imagem off
                if(ConfiguracaoMusica.som){
                    AGSoundManager.vrMusic.stop();

                    ConfiguracaoMusica.som = false;
                }
                //imagem musica
                else {

                    ConfiguracaoMusica.som = true;
                    ConfiguracaoMusica.tocarmusica(new Random().nextInt(3));
                }

                vrGameManager.setCurrentScene(1);
            }

        }

    }

}
