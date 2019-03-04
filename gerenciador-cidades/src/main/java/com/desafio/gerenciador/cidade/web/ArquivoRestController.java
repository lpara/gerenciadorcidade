package com.desafio.gerenciador.cidade.web;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.desafio.gerenciador.cidade.dominio.Cidade;
import com.desafio.gerenciador.cidade.repository.CidadeRepository;
import com.desafio.gerenciador.cidade.service.CSVService;

@RestController
@RequestMapping("/upload")
public class ArquivoRestController {
	
	CSVService csvUtil = new CSVService();
	
	@Autowired
	CidadeRepository cidadeRepository;

	@PostMapping("/enviar") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) throws IOException {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }
        
        if(!csvUtil.validaFormatoCSV(file)) {
        	throw new IOException("Arquivo com formato inválido, suporte apenas a CSV.");
        }

        try {

            //byte[] bytes = file.getBytes();
           List<String> linhasArquivoRecebido = csvUtil.inicioLeituraArquivo(file);
           List<Cidade> cidadesImportadas = csvUtil.mapearCidadesPorLinhasCsv(linhasArquivoRecebido);
           //Corrigir problema "Transient"
           cidadeRepository.saveAll(cidadesImportadas);

            redirectAttributes.addFlashAttribute("message", 
                        "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }
}
