package labs.al.controllers;

import java.util.*;

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

    public void registrarID(String identificador){
        if (!ids.containsKey(identificador)) ids.put(identificador, nextID++);
    }
    public void registrarTXT(String texto){
        if (!txt.containsKey(texto)) txt.put(texto, nextTXT++);
    }
    public void registrarVAL(String variable, String numHEX){
        if (!val.containsKey(variable)) val.put(variable, numHEX);
    }
    public Map<String, Integer> getIds(){return ordenarPorValor(this.ids);}
    public Map<String, Integer> getTxt(){return ordenarPorValor(this.txt);}
    public Map<String, String> getVal(){return this.val;}
    private Map<String, Integer> ordenarPorValor(Map<String, Integer> original) {
        return original.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue()) // Orden ascendente por valor
                .collect(
                        LinkedHashMap::new,
                        (m, e) -> m.put(e.getKey(), e.getValue()),
                        LinkedHashMap::putAll
                );
    }
    public List<String> lineal(){
        List<String> lista = new ArrayList<>();
        String separador = "==================================================";
        lista.add(separador);
        lista.add("                      ID'S                  ");
        lista.add(separador);
        for (Map.Entry<String, Integer> code: this.getIds().entrySet()){
            lista.add(code.getKey() + ",  ID" + code.getValue());
        }
        lista.add(separador);
        lista.add("                      TXT'S                  ");
        lista.add(separador);
        for (Map.Entry<String, Integer> code: this.getTxt().entrySet()){
            lista.add(code.getKey() + ",  TXT" + code.getValue());
        }
        lista.add(separador);
        lista.add("                      VAL'S                  ");
        lista.add(separador);
        for (Map.Entry<String, String> code: this.getVal().entrySet()){
            lista.add(code.getKey() + ",  " + code.getValue());
        }
        return lista;
    }
}
