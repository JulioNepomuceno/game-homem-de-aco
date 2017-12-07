package com.julio_hortenciohotmail.projeto_game;

import com.julio_hortenciohotmail.projeto_game.AndGraph.AGGameManager;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGInputManager;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGScene;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGScreenManager;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGSprite;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGTimer;

import java.util.Random;

/**
 * Created by julio on 22/11/17.
 */

public class CenaAbertura extends AGScene {

    AGSprite imagemFundo = null;
    AGTimer intevalotempo =null;
    AGSprite imagem =null;

    public CenaAbertura(AGGameManager gerente) {
        super(gerente);
    }

    @Override
    public void init() {

        this.imagemFundo = this.createSprite(R.drawable.fundoprincipal, 1, 1);
        imagemFundo.setScreenPercent(100,100);//PERCENTUAL QUE A IMAGEM VAI OCUPAR NA TELA.
        imagemFundo.vrPosition.setXY(AGScreenManager.iScreenWidth/2, AGScreenManager.iScreenHeight/2);//PERCENTUAL QUE A IMAGEM VAI OCUPAR NA TELA.
        //imagemFundo.fadeIn(3000);//ESTADO TRANSPARENTE PARA VISIVEL

        setSceneBackgroundColor(1,1,1);

        intevalotempo = new AGTimer(5000);
        imagem = this.createSprite(R.drawable.escudo,1,1);
        imagem.setScreenPercent(80,40);
        imagem.vrPosition.setXY(AGScreenManager.iScreenWidth/2, AGScreenManager.iScreenHeight/2);
        imagem.fadeIn(3000);

        ConfiguracaoMusica.tocarmusica(new Random().nextInt(3));


    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {

        if (imagem.fadeEnded()){
            imagem.fadeOut(1500);
            return;
        }

        intevalotempo.update();
        if (intevalotempo.isTimeEnded()){
            vrGameManager.setCurrentScene(1);

            return;
        }
        if (AGInputManager.vrTouchEvents.screenClicked()){
            vrGameManager.setCurrentScene(1);
            return;
        }
    }
}
