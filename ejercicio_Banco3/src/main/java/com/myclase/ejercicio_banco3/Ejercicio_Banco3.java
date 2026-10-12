package com.myclase.ejercicio_banco3;

import controlador.ControladorBanco;
import vista.VistaBanco;

public class Ejercicio_Banco3 {

    public static void main(String[] args) {
        ControladorBanco controlador = new ControladorBanco();
        java.awt.EventQueue.invokeLater(() -> {
            new VistaBanco(controlador).setVisible(true);
        });
    }
}