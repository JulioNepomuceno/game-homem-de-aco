package com.julio_hortenciohotmail.projeto_game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.julio_hortenciohotmail.projeto_game.AndGraph.AGActivityGame;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGGameManager;

public class MainActivity extends AGActivityGame {
    private static MainActivity contexto = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vrManager = new AGGameManager(this, true);
        contexto = this;

        //INSTANCIA DE UMA CENA GRAFICA
        CenaAbertura vrCenaAbertura = new CenaAbertura(vrManager);

        CenaMenu vrCenaMenu = new CenaMenu(vrManager);
        CenaConfiguracao configuracao = new CenaConfiguracao(vrManager);
        CenaSobre sobre = new CenaSobre(vrManager);
        CenaGame cenaGame = new CenaGame(vrManager);
        TelaScore score = new TelaScore(vrManager);

        vrManager.addScene(vrCenaAbertura);//0
        vrManager.addScene(vrCenaMenu);//1
        vrManager.addScene(cenaGame);//2
        vrManager.addScene(configuracao);//3
        vrManager.addScene(sobre);//4
        vrManager.addScene(score);//5
        }
}
