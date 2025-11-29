package labs.as.controllers;

import java.util.List;

public record Gramatica() {
    static final String SENT = "<SENT>";
    static final String SENTS = "<SENTS>";
    static final String PROGRAMA = "PROGRAMA";
    static final String PROG = "PROG";
    static final String FINPROG = "FINPROG";
    static final String ID = "[id]";
    static final String EXPR = "<EXPR>";
    static final String SI = "SI";
    static final String COMPARA = "<COMPARA>";
    static final String ENTONCES = "ENTONCES";
    static final String SINO = "SINO";
    static final String FINSI = "FINSI";
    static final String MIENTRAS = "MIENTRAS";
    static final String HACER = "HACER";
    static final String FINM = "FINM";
    static final String IMPRIME = "IMPRIME";
    static final String TXT = "[txt]";
    static final String LEE = "LEE";
    static final String TERM = "<TERM>";
    static final String FAC = "<FAC>";
    static final String VAL = "[val]";
    static final String OP_REL = "[op_rel]";
    static final String OP_AR = "[op_ar]";

    static Regla reglaProg = new Regla(PROG, new String[]{PROGRAMA, ID, SENTS, FINPROG});         // <PROG> → PROGRAMA [id] <SENTS> FINPROG
    static Regla[] reglasFac = {
   //         new Regla(TERM, new String[]{TERM, "*", FAC}),
 //           new Regla(TERM, new String[]{TERM, "/", FAC}),
            new Regla(EXPR, new String[]{FAC, OP_AR, FAC}),
            new Regla(EXPR, new String[]{FAC}, ((previo, siguiente) -> ! ( siguiente.contentEquals(OP_AR) || siguiente.contentEquals(OP_REL)) ))
    };

    static Regla[] reglasFinsi = {
            new Regla(SENT, new String[]{SI, COMPARA, ENTONCES, SENTS, SINO, SENTS, FINSI}), // <SENT> → SI <COMPARA> ENTONCES <SENTS> SINO <SENTS> FINSI
            new Regla(SENT, new String[]{SI, COMPARA, ENTONCES, SENTS, FINSI}),               // <SENT> → SI <COMPARA> ENTONCES <SENTS> FINSI
    };

    static Regla reglaMientras = new Regla(SENT, new String[]{MIENTRAS, COMPARA, HACER, SENTS, FINM});           // <SENT> → MIENTRAS <COMPARA> HACER <SENTS> FINM

    static Regla reglaSents = new Regla(SENTS, new String[]{SENT, SENTS});              // <SENTS> → <SENT> <SENTS>

    static Regla[] reglasExpr = {
            new Regla(SENT, new String[]{ID, "=", EXPR}),             // <SENT> → [id] = <EXPR>
            new Regla(COMPARA, new String[]{ID, OP_REL, EXPR}),
            new Regla(SENT, new String[]{IMPRIME, EXPR})                                 // <SENT> → IMPRIME <EXPR>
    };

    static Regla[] reglasTerm = {
//            new Regla(EXPR, new String[]{EXPR, "+", TERM}),                           // <EXPR> → <EXPR>+<TERM>
//            new Regla(EXPR, new String[]{EXPR, "-", TERM}),                           // <EXPR> → <EXPR>-<TERM>
//            new Regla(EXPR, new String[]{TERM}),                                                // <EXPR> → <TERM>
    };

    static Regla[] reglasID = {
            new Regla(SENT, new String[]{LEE, ID}),                                          // <SENT> → LEE [id]
            new Regla(FAC, new String[]{ID}, ((prev, sig) -> sig.contentEquals(OP_AR) || prev.contentEquals(OP_AR) || prev.contentEquals(OP_REL) || prev.contentEquals(IMPRIME) || prev.contentEquals("=")))                                             // <FAC> → [id]
    };


    static Regla reglaTXT = new Regla(SENT, new String[]{IMPRIME, TXT});                                      // <SENT> → IMPRIME [txt]

    static Regla reglaParentesis = new Regla(FAC, new String[]{"(", EXPR, ")"});                                         // <FAC> → (<EXPR>)

    static Regla reglaSent = new Regla(SENTS, new String[]{SENT}, ((previo, siguiente) -> siguiente.contentEquals(FINPROG) || siguiente.contentEquals(FINSI) || siguiente.contentEquals(FINM)));                      // <SENTS> → <SENT>

    static Regla reglaVal = new Regla(FAC, new String[]{VAL});                                            // <FAC> → [val]
}
