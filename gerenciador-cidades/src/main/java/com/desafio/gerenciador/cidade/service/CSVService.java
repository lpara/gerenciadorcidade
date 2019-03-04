package com.desafio.gerenciador.cidade.service;

import java.util.ArrayList;
import java.util.List;

public class CSVService {

	private static final char SEPARADOR_PADRAO = ',';
    private static final char DELIMITADOR_CAMPO = '"';
    
    public static List<String> leitorLinhas(String cvsLine) {
        return leitorLinhas(cvsLine, SEPARADOR_PADRAO, DELIMITADOR_CAMPO);
    }
    
    public static List<String> leitorLinhas(String linhaCsv, char separadorCampo, char delimitadorCampoCustom) {

        List<String> result = new ArrayList<>();

        //if empty, return!
        if (linhaCsv == null || linhaCsv.isEmpty()) {
            return result;
        }

        if (delimitadorCampoCustom == ' ') {
            delimitadorCampoCustom = DELIMITADOR_CAMPO;
        }

        if (separadorCampo == ' ') {
            separadorCampo = SEPARADOR_PADRAO;
        }

        StringBuffer curVal = new StringBuffer();
        boolean entreAspas = false;
        boolean inicioConteudoCampo = false;
        boolean aspasDentroDeCampo = false;

        char[] chars = linhaCsv.toCharArray();

        for (char ch : chars) {

            if (entreAspas) {
                inicioConteudoCampo = true;
                if (ch == delimitadorCampoCustom) {
                    entreAspas = false;
                    aspasDentroDeCampo = false;
                } else {

                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!aspasDentroDeCampo) {
                            curVal.append(ch);
                            aspasDentroDeCampo = true;
                        }
                    } else {
                        curVal.append(ch);
                    }

                }
            } else {
                if (ch == delimitadorCampoCustom) {

                    entreAspas = true;

                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && delimitadorCampoCustom == '\"') {
                        curVal.append('"');
                    }

                    //double quotes in column will hit this!
                    if (inicioConteudoCampo) {
                        curVal.append('"');
                    }

                } else if (ch == separadorCampo) {

                    result.add(curVal.toString());

                    curVal = new StringBuffer();
                    inicioConteudoCampo = false;

                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }

        }

        result.add(curVal.toString());

        return result;
    }
}
