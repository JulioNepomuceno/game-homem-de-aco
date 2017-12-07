package com.julio_hortenciohotmail.projeto_game;

import com.julio_hortenciohotmail.projeto_game.AndGraph.AGGameManager;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGInputManager;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGScene;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGScreenManager;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGSprite;

/**
 * Created by julio on 23/11/17.
 */

public class CenaSobre extends AGScene
{
    AGSprite imagemFundo =null;
    AGSprite imagemCredito = null;
    AGSprite imagemLogo = null;
    AGSprite imagemFacto = null;
    AGSprite imagemVoltar = null;
    public CenaSobre(AGGameManager gerente){
        super(gerente);
    }
    @Override
    public void init() {
        this.imagemFundo = this.createSprite(R.drawable.fundo, 1, 1);
        imagemFundo.setScreenPercent(100,100);//PERCENTUAL QUE A IMAGEM VAI OCUPAR NA TELA.
        imagemFundo.vrPosition.setXY(AGScreenManager.iScreenWidth/2, AGScreenManager.iScreenHeight/2);//PERCENTUAL QUE A IMAGEM VAI OCUPAR NA TELA.
        //imagemFundo.fadeIn(3000);//ESTADO TRANSPARENTE PARA VISIVEL
        this.setSceneBackgroundColor(1,0,0);

        imagemCredito = createSprite(R.drawable.credito,1,1);
        imagemCredito.setScreenPercent(80,40);
        imagemCredito.vrPosition.setXY(AGScreenManager.iScreenWidth/2 , AGScreenManager.iScreenHeight/2);
        imagemCredito.fadeIn(3000);

        imagemVoltar = createSprite(R.drawable.voltar,1,1);
        imagemVoltar.setScreenPercent(40,10);
        imagemVoltar.vrPosition.setXY(AGScreenManager.iScreenWidth/2 , AGScreenManager.iScreenHeight/6);
        imagemVoltar.fadeIn(3000);

        imagemLogo = createSprite(R.drawable.logojh,1,1);
        imagemLogo.setScreenPercent(50,25);
        imagemLogo.vrPosition.setXY(AGScreenManager.iScreenWidth/2 , AGScreenManager.iScreenHeight/1.2f);
        imagemLogo.fadeIn(3000);

        imagemFacto = createSprite(R.drawable.catolica,1,1);
        imagemFacto.setScreenPercent(30,10);
        imagemFacto.vrPosition.setXY(AGScreenManager.iScreenWidth - imagemFacto.getSpriteWidth()/1.8f ,  imagemFacto.getSpriteHeight() /1.8f);
        imagemFacto.fadeIn(3000);
    }

    @Override
    public void restart() {

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
    }
}
