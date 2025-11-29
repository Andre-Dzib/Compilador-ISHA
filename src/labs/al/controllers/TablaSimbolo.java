package labs.al.controllers;

import java.util.HashMap;
import java.util.Map;

public class TablaSimbolo {
    private Map<String, Integer> ids = new HashMap<>();
    private Map<String, Integer> txt = new HashMap<>();
    private Map<String, String> val = new HashMap<>();
    private int nextID = 1;
    private int nextTXT = 1;
    public TablaSimbolo(){
        this.val = new HashMap<>();
        this.txt = new HashMap<>();
        this.ids = new HashMap<>();
    }

    public boolean registrarID(String identificador){
        if (ids.containsKey(identificador)) return false;
        nextID++;
        ids.put(identificador, nextID);
        return true;
    }
    public void registrarTXT(String texto){
        nextTXT++;
        txt.put(texto, nextTXT);
    }
    public void registrarVAL(String variable, String numHEX){
        val.put(variable, numHEX);
    }
    public Map<String, Integer> getIds(){return this.ids;}
    public Map<String, Integer> getTxt(){return this.txt;}
    public Map<String, String> getVal(){return this.val;}
}
