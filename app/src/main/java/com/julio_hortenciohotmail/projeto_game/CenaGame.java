package com.julio_hortenciohotmail.projeto_game;

import android.content.SharedPreferences;

import com.julio_hortenciohotmail.projeto_game.AndGraph.AGGameManager;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGInputManager;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGScene;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGScreenManager;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGSoundManager;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGSprite;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGTimer;
import com.julio_hortenciohotmail.projeto_game.AndGraph.AGVector2D;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by julio on 24/11/17.
 */

public class CenaGame extends AGScene {
    private ArrayList<AGSprite> arrayKriptonita = null;
    private ArrayList<AGSprite> arrayTiros = null;
    private ArrayList<AGSprite> arrayExplosoes = null;
    private AGSprite superHomem = null;
    private AGSprite superExplosao = null;
    private AGSprite[] imagemFundo = null;

    private int somExplosao = 0;
    private int somExplosaoPrincipal = 0;
    private Random sorteiaPosicao = null;

    private AGSprite[] placar = null;

    private AGTimer tempoKriptonita = null;

    private int numeroColisoes = 0;
    int iAux;
    int som = 0;

    public CenaGame(AGGameManager gerente) {
        super(gerente);
    }


    @Override
    public void init() {

        this.setSceneBackgroundColor(0, 0, 0);
        this.arrayKriptonita = new ArrayList<>();
        this.arrayTiros = new ArrayList<>();
        this.arrayExplosoes = new ArrayList<>();
        this.superExplosao = null;

        this.sorteiaPosicao = new Random(AGScreenManager.iScreenWidth);

        tempoKriptonita = new AGTimer(3000);

        numeroColisoes = 0;


        this.imagemFundo = new AGSprite[2];
        imagemFundo[0] = this.createSprite(R.drawable.stars, 1, 1);
        imagemFundo[0].setScreenPercent(110, 110);
        imagemFundo[0].vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 2);


        imagemFundo[1] = this.createSprite(R.drawable.stars, 1, 1);
        imagemFundo[1].setScreenPercent(110, 110);
        imagemFundo[1].vrPosition.setXY(AGScreenManager.iScreenWidth / 2, imagemFundo[0].getSpriteHeight() + imagemFundo[0].vrPosition.getY());


        //super homem
        superHomem = this.createSprite(R.drawable.superhomem, 1, 1);
        superHomem.setScreenPercent(20, 25);
        superHomem.iMirror = AGSprite.VERTICAL_HORIZONTAL;
        superHomem.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, (superHomem.getSpriteHeight() / 2));
        superHomem.bAutoRender = false;


        //carrega imagen na memoria
        createSprite(R.drawable.explosion, 9, 9).bVisible = false;
        createSprite(R.drawable.bala, 1, 1).bVisible = false;
        createSprite(R.drawable.kriptonita, 1, 1).bVisible = false;

        //instancia o placar
        placar = new AGSprite[3];
        for (int i = 2; i >= 0; i--) {
            placar[i] = createSprite(R.drawable.fonte, 4, 4);
            placar[i].setScreenPercent(10, 10);
            placar[i].bAutoRender = false;


            for (int quadro = 0; quadro < 10; quadro++) {
                placar[i].addAnimation(1, true, quadro);
            }


        }


        placar[0].vrPosition.setXY(AGScreenManager.iScreenWidth - placar[0].getSpriteWidth() / 3,
                AGScreenManager.iScreenHeight - placar[0].getSpriteHeight() / 2.5f);//primeira posicao do placar
        placar[1].vrPosition.setXY(placar[0].vrPosition.getX() - placar[1].getSpriteWidth() / 2.5f,
                AGScreenManager.iScreenHeight - placar[0].getSpriteHeight() / 2.5f);//segunda posicao do placar
        placar[2].vrPosition.setXY(placar[1].vrPosition.getX() - placar[1].getSpriteWidth() / 2.5f,
                AGScreenManager.iScreenHeight - placar[0].getSpriteHeight() / 2.5f);//segunda posicao do placar

        placar[0].setCurrentAnimation(0);
        placar[1].setCurrentAnimation(0);
        placar[2].setCurrentAnimation(0);


        if (ConfiguracaoMusica.som) {
            ConfiguracaoMusica.tocarmusica(new Random().nextInt(3));
        }
        this.somExplosao = AGSoundManager.vrSoundEffects.loadSoundEffect("morreu.mp4");
        this.somExplosaoPrincipal = AGSoundManager.vrSoundEffects.loadSoundEffect("gaayy.mp4");

        //Cria objeto responsavel pela gravacao dos dados em arquivo
        SharedPreferences vrShared = vrGameManager.vrActivity.getSharedPreferences("BD_SuperHomem", vrGameManager.vrActivity.MODE_PRIVATE);
        //Se estiver um score salvo
        if (vrShared.contains("score")) {
            //pego o score e salva em uma variavel auxiliar
            iAux = vrShared.getInt("score", 0);

        }
        som = AGSoundManager.vrSoundEffects.loadSoundEffect("miau.mp4");

    }

    public void render() {
        super.render();
        placar[0].render();
        placar[1].render();
        placar[2].render();
        superHomem.render();
    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }


    @Override
    public void loop() {

        atualizaFundo();
        //movimento da super homem
        //if(superHomem.vrPosition.getX() + (superHomem.getSpriteWidth()/2) > 0 ){


        if (superHomem.vrPosition.getX() < (AGScreenManager.iScreenWidth - superHomem.getSpriteWidth() / 2) && AGInputManager.vrAccelerometer.getAccelX() > 2) {
            superHomem.vrPosition.setX(superHomem.vrPosition.getX() + 10);

        } else if (superHomem.vrPosition.getX() > superHomem.getSpriteWidth() / 2 && AGInputManager.vrAccelerometer.getAccelX() < -2) {
            superHomem.vrPosition.setX(superHomem.vrPosition.getX() - 10);


        }


        if (superExplosao != null && superExplosao.getCurrentAnimation().isAnimationEnded()) {
            if (ConfiguracaoMusica.som) {
                ConfiguracaoMusica.tocarmusica(new Random().nextInt(3));
            }
//            if(numeroColisoes > Config.recordeBanco){
//                Config.recordeBanco = numeroColisoes;
////                MainActivity.salvarRecorde(numeroColisoes);
//            }
            vrGameManager.setCurrentScene(1);
            return;
        }


        atualizaTiro();
        atualizaKriptonita();
        dificuldade();
        colide();
        atualizaPlacar();
        atualizaExplosao();
        colidesuperHomem();

        if (AGInputManager.vrTouchEvents.backButtonClicked()) {

            if (ConfiguracaoMusica.som) {
                ConfiguracaoMusica.tocarmusica(new Random().nextInt(3));
            }
            salvaScore();

            vrGameManager.setCurrentScene(1);
            return;
        }

        if (AGInputManager.vrTouchEvents.screenClicked() && superHomem.bVisible) {
            AGSoundManager.vrSoundEffects.play(som);

            criaTiro();
        }


    }

    //--------------------tiros-------------------------------
    public void criaTiro() {


        for (AGSprite reciclado : arrayTiros) {

            if (reciclado.bRecycled) {
                reciclado.vrPosition.setXY(superHomem.vrPosition.getX(), superHomem.getSpriteHeight());
                reciclado.bRecycled = false;
                reciclado.bVisible = true;
                return;
            }
        }

        AGSprite tiro = null;
        tiro = createSprite(R.drawable.bala, 2, 4);
        tiro.setScreenPercent(8, 3);
        tiro.vrPosition.setXY(superHomem.vrPosition.getX(), superHomem.getSpriteHeight());

        arrayTiros.add(tiro);
    }

    public void atualizaTiro() {

        for (AGSprite tiro : arrayTiros) {
            tiro.vrPosition.setY(tiro.vrPosition.getY() + 20);


            //quando o tiro sumir, ele será reciclado
            if (tiro.vrPosition.getY() > AGScreenManager.iScreenHeight + tiro.getSpriteHeight()) {
                tiro.bRecycled = true;
                tiro.bVisible = false;
            }
        }
    }

    public void atualizaFundo() {

        this.imagemFundo[0].vrPosition.setY(imagemFundo[0].vrPosition.getY() - 25);
        this.imagemFundo[1].vrPosition.setY(imagemFundo[1].vrPosition.getY() - 25);


        if (imagemFundo[0].vrPosition.getY() + (imagemFundo[0].getSpriteHeight() / 2) < 0) {
            this.imagemFundo[0].vrPosition.setY((imagemFundo[0].getSpriteHeight() / 2) + AGScreenManager.iScreenHeight);
        }
        if (imagemFundo[1].vrPosition.getY() + (imagemFundo[1].getSpriteHeight() / 2) < 0) {
            this.imagemFundo[1].vrPosition.setY((imagemFundo[1].getSpriteHeight() / 2) + AGScreenManager.iScreenHeight);
        }
    }


    //-----------nasves inimigas--------------------------------
    public void criaKriptonita() {


        for (AGSprite reciclado : arrayKriptonita) {

            if (reciclado.bRecycled) {
                reciclado.vrPosition.setXY((reciclado.getSpriteWidth() / 2) + sorteiaPosicao.nextInt((int) (AGScreenManager.iScreenWidth - reciclado.getSpriteWidth())), AGScreenManager.iScreenHeight + reciclado.getSpriteHeight());
                reciclado.bRecycled = false;
                reciclado.bVisible = true;

                return;
            }
        }

        AGSprite nave = null;
        nave = createSprite(R.drawable.kriptonita, 2, 4);
        nave.setScreenPercent(10, 10);
        nave.vrPosition.setXY((nave.getSpriteWidth() / 2) + sorteiaPosicao.nextInt((int) (AGScreenManager.iScreenWidth - nave.getSpriteWidth())), AGScreenManager.iScreenHeight + nave.getSpriteHeight());


        arrayKriptonita.add(nave);


    }

    public void atualizaKriptonita() {

        for (AGSprite nave : arrayKriptonita) {
            nave.vrPosition.setY(nave.vrPosition.getY() - 13);

            //quando a nave sumir, ele será reciclada
            if (nave.vrPosition.getY() < -(nave.getSpriteHeight() / 2)) {
                nave.bRecycled = true;
                nave.bVisible = false;


            }
        }
    }

    public void colidesuperHomem() {
        for (AGSprite nave : arrayKriptonita) {

            if (!nave.bRecycled && superHomem.collide(nave) && superHomem.bVisible) {
                if (ConfiguracaoMusica.som) {
                    AGSoundManager.vrSoundEffects.play(somExplosaoPrincipal);
                }
                criaSuperExplosao();

            }

        }
    }

    //------------- colisão e esplosão-----------------------------
    public void colide() {

        for (AGSprite nave : arrayKriptonita) {

            if (!nave.bRecycled) {
                for (AGSprite tiro : arrayTiros) {

                    if (!tiro.bRecycled && nave.collide(tiro.vrPosition)) {
                        nave.bRecycled = true;
                        nave.bVisible = false;

                        tiro.bRecycled = true;
                        tiro.bVisible = false;
                        criaExplosao(nave.vrPosition);
                        if (ConfiguracaoMusica.som) {
                            AGSoundManager.vrSoundEffects.play(somExplosao);
                        }

                        numeroColisoes++;

                    }
                }

            }


        }
    }

    public void criaExplosao(AGVector2D posicao) {


        for (AGSprite reciclado : arrayExplosoes) {

            if (reciclado.bRecycled) {
                reciclado.vrPosition.setXY(posicao.getX(), posicao.getY());
                reciclado.bRecycled = false;
                reciclado.bVisible = true;
                reciclado.getCurrentAnimation().restart();
                return;
            }
        }
        AGSprite exposao = null;
        exposao = createSprite(R.drawable.explosion, 9, 9);
        exposao.addAnimation(100, false, 0, 72);
        exposao.setScreenPercent(20, 10);
        exposao.vrPosition.setXY(posicao.getX(), posicao.getY());
        arrayExplosoes.add(exposao);


    }

    public void atualizaExplosao() {

        for (AGSprite explosao : arrayExplosoes) {

            //quando a nave sumir, ele será reciclada
            if (explosao.getCurrentAnimation().isAnimationEnded()) {
                explosao.bRecycled = true;
                explosao.bVisible = false;
            }
        }
    }

    //--------------- placar --------------------
    public void atualizaPlacar() {

        if (numeroColisoes < 100) {
            placar[1].setCurrentAnimation(numeroColisoes / 10);
            placar[0].setCurrentAnimation(numeroColisoes % 10);

        } else {


            placar[2].setCurrentAnimation(Integer.parseInt(String.valueOf(numeroColisoes).substring(0, 1)));
            placar[1].setCurrentAnimation(Integer.parseInt(String.valueOf(numeroColisoes).substring(1, 2)));
            placar[0].setCurrentAnimation(Integer.parseInt(String.valueOf(numeroColisoes).substring(2, 3)));

        }

    }

    public void criaSuperExplosao() {
        superExplosao = createSprite(R.drawable.explosion, 9, 9);
        superExplosao.addAnimation(100, false, 0, 80);
        superExplosao.setScreenPercent(20, 10);
        superExplosao.vrPosition.setXY(superHomem.vrPosition.getX(), superHomem.vrPosition.getY());

        superHomem.bVisible = false;
        salvaScore();

    }


    //---------------- dificuldade-----------------------------
    public void dificuldade() {
        tempoKriptonita.update();
        if (tempoKriptonita.isTimeEnded()) {
            criaKriptonita();

            if (numeroColisoes <= 15) {
                tempoKriptonita.restart(3000);//3000


            } else if (numeroColisoes <= 25) {
                tempoKriptonita.restart(2500);//2500


            } else if (numeroColisoes <= 35) {
                tempoKriptonita.restart(2000);//2000

            } else if (numeroColisoes <= 45) {
                tempoKriptonita.restart(1500);//1500

            } else if (numeroColisoes <= 55) {
                tempoKriptonita.restart(900);//1000

            } else if (numeroColisoes <= 70) {
                tempoKriptonita.restart(500);//500

            } else {
                tempoKriptonita.restart();


            }

        }
    }

    //Método para salvar os pontos feitos pelo usuário
    private void salvaScore() {
        try {
            //se os pontos que estão salvos for maior que o placar
            if (iAux > numeroColisoes) {
                //placar recebe esses pontos
                numeroColisoes = iAux;
            }
            //Cria objeto responsavel pela gravaÃ§Ã£o dos dados em arquivo
            SharedPreferences vrShared = vrGameManager.vrActivity.getSharedPreferences("BD_SuperHomem", vrGameManager.vrActivity.MODE_PRIVATE);
            //Cria o editor para escrita de dados
            SharedPreferences.Editor vrEditor = vrShared.edit();
            //Escreve os dados no Shared e confirma
            vrEditor.putInt("score", numeroColisoes);
            vrEditor.commit();
        } catch (Exception e) {

        }
    }

}
