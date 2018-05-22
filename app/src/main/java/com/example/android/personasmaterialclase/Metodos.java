package com.example.android.personasmaterialclase;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by android on 24/10/2017.
 */

public class Metodos {
    public static int fotoAleatoria(ArrayList<Integer> fotos){
        int fotoSeleccionada;
        Random r = new Random();
        fotoSeleccionada = r.nextInt(fotos.size());
        return fotos.get(fotoSeleccionada);
    }

    public static boolean validar_aux(TextView t, String mensaje){
        if(t.getText().toString().isEmpty()){
            t.requestFocus();
            t.setError(mensaje);
            return true;
        }
        return  false;
    }

    public static boolean existencia_persona(ArrayList<Persona> personas,
                                             String cedula){
        for (int i = 0; i < personas.size(); i++) {
            if(personas.get(i).getCedula().equals(cedula)) return true;
        }
        return false;
    }

}
