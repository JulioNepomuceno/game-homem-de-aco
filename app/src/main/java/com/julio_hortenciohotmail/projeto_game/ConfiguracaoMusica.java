package com.julio_hortenciohotmail.projeto_game;

import com.julio_hortenciohotmail.projeto_game.AndGraph.AGSoundManager;

/**
 * Created by julio on 23/11/17.
 */

public abstract class ConfiguracaoMusica {

    public static boolean som = true;
    public static int recordeBanco = 0;

    public static void tocarmusica (int musica){
        AGSoundManager.vrMusic.stop();
        switch (musica){
            case 0:
                AGSoundManager.vrMusic.loadMusic("trilha1.mp4", true);
                AGSoundManager.vrMusic.play();
                return;
            case 1:
                AGSoundManager.vrMusic.loadMusic("trilha2.mp4", true);
                AGSoundManager.vrMusic.play();
                return;
            case 2:
                AGSoundManager.vrMusic.loadMusic("trilha3.mp4", true);
                AGSoundManager.vrMusic.play();
                return;

        }
    }

}
