package com.desafio.gerenciador.cidade.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.multipart.MultipartFile;

import com.desafio.gerenciador.cidade.dominio.Cidade;
import com.desafio.gerenciador.cidade.dominio.Estado;

public class CSVService {

	private static final char SEPARADOR_PADRAO = ',';
    private static final char DELIMITADOR_CAMPO = '"';
    private static final String CSV_PATTERN = "([\\@\\.\\w\\s\\-]*[^\\s]+(\\.(csv))$)";
    
    public boolean validaFormatoCSV(MultipartFile file) {
    	Pattern pattern = Pattern.compile(CSV_PATTERN);
        Matcher matcher = pattern.matcher(file.getOriginalFilename());
        return matcher.matches();
    }
    
    public List<String> inicioLeituraArquivo(MultipartFile file) throws IOException{
    	String linha;
    	List<String> listaLinhasArquivo = new ArrayList<>();
        InputStream is = file.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        while((linha = br.readLine()) != null && !linha.isEmpty()) {
        	listaLinhasArquivo.add(linha);
        }
        
        return listaLinhasArquivo;
    }
    
    public List<Cidade> mapearCidadesPorLinhasCsv(List<String> linhasArquivoCsv){    	
    	List<Cidade> result = new ArrayList<>();
    	List<String> listaCamposCidade = new ArrayList<>();
    	//Removendo a linha com os títulos das colunas
    	linhasArquivoCsv.remove(0);
    	for(String linha : linhasArquivoCsv) {
    		listaCamposCidade = new ArrayList<>();
    		listaCamposCidade = leitorLinhas(linha);
    		Cidade cidadeLinha = new Cidade();
    		cidadeLinha.setIdIbge(Long.valueOf(listaCamposCidade.get(0)));
    		Estado estadoCidadeLinha = new Estado();
    		estadoCidadeLinha.setCodigoUF(listaCamposCidade.get(1));
    		cidadeLinha.setUf(estadoCidadeLinha);
    		cidadeLinha.setNome(listaCamposCidade.get(2));
    		cidadeLinha.setCapital(listaCamposCidade.get(3).isEmpty() ? false : true);
    		cidadeLinha.setLongitude(listaCamposCidade.get(4));
    		cidadeLinha.setLatitude(listaCamposCidade.get(5));
    		cidadeLinha.setNomeAscii(listaCamposCidade.get(6));
    		cidadeLinha.setNomeAlternativo(listaCamposCidade.get(7));
    		cidadeLinha.setMicroregiao(listaCamposCidade.get(8));
    		cidadeLinha.setMacroregiao(listaCamposCidade.get(9));
    		result.add(cidadeLinha);
    	}
    	
    	return result;
    }
    
    public static List<String> leitorLinhas(String cvsLine) {
        return leitorLinhas(cvsLine, SEPARADOR_PADRAO, DELIMITADOR_CAMPO);
    }
    
    public static List<String> leitorLinhas(String linhaCsv, char separadorCampo, char delimitadorCampoCustom) {

        List<String> result = new ArrayList<>();

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

                    if (chars[0] != '"' && delimitadorCampoCustom == '\"') {
                        curVal.append('"');
                    }

                    if (inicioConteudoCampo) {
                        curVal.append('"');
                    }

                } else if (ch == separadorCampo) {

                    result.add(curVal.toString());

                    curVal = new StringBuffer();
                    inicioConteudoCampo = false;

                } else if (ch == '\r') {
                    continue;
                } else if (ch == '\n') {
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
