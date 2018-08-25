/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import modelo.Carta;
import modelo.Color;
import modelo.DoblePar;
import modelo.Figura;
import modelo.Mazo;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author leo
 */
public class PruebaFiguras {

    public PruebaFiguras() {
    }

    @Test
    public void test() {
        Carta[] cartas = new Carta[5];
        Mazo mazo = new Mazo(true);//Crea un mazo sin barajar.

        //Cargo color.
        for (int i = 0; i < cartas.length; i++) {
            cartas[i] = mazo.getCarta(i);
        }
        Figura f = new Color();
        //Prueba color
        assertEquals(true, f.esFigura(cartas));
        //Pruebo el valor del color.
        //la carta alta es un 6(ordinal 4 del enum * 1000)
        assertEquals(f.getValor(), 4000);
        //Pruebo valor de un color mas alto.
        for (int i = 0; i < cartas.length; i++) {
            cartas[i] = mazo.getCarta(i+15);//Cargo cartas con color al 8 trebol
        }
        assertEquals(true, f.esFigura(cartas));
        assertEquals(f.getValor(), 16000);
        //Prueba color en falso.
        cartas[0] = mazo.getCarta(0);//2 de pique
        cartas[1] = mazo.getCarta(1);//3 de pique
        cartas[2] = mazo.getCarta(2);//4 de pique
        cartas[3] = mazo.getCarta(16);//5 de terbol
        cartas[4] = mazo.getCarta(18);//7 de trebol
        assertEquals(false, f.esFigura(cartas));
        //Prueba color en falso.
        //Cargo doble par
        cartas[0] = mazo.getCarta(0);//2 de pique
        cartas[1] = mazo.getCarta(1);//3 de pique
        cartas[2] = mazo.getCarta(13);//2 de trebol
        cartas[3] = mazo.getCarta(14);//3 de terbol
        cartas[4] = mazo.getCarta(15);//4 de trebol
        assertEquals(false, f.esFigura(cartas));

        cartas[4] = mazo.getCarta(29);

        //Prueba doble par.
        f = new DoblePar();
        assertEquals(true, f.esFigura(cartas));

        //Pruebo el valor del par.
        // valor del par de 2=1*40*2 + valor del par de 3= 2 * 40 * 2 = 240
        assertEquals(f.getValor(), 240);

    }
}
