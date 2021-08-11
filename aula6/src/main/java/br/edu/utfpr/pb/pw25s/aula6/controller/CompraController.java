package br.edu.utfpr.pb.pw25s.aula6.controller;

import br.edu.utfpr.pb.pw25s.aula6.model.Compra;
import br.edu.utfpr.pb.pw25s.aula6.model.CompraProduto;
import br.edu.utfpr.pb.pw25s.aula6.model.Usuario;
import br.edu.utfpr.pb.pw25s.aula6.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("compra")
public class CompraController {

    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private FornecedorService fornecedorService;
    @Autowired
    private CompraService compraService;


    @GetMapping
    public String list(Model model) {
        model.addAttribute("compras", compraService.findAll());
        return "compra/list";
    }

    @GetMapping(value = {"new", "novo", "form"})
    public String form(Model model) {
        model.addAttribute("compra", new Compra());
        carregarCombos(model);
        return "compra/form";
    }

    private void carregarCombos(Model model) {
        model.addAttribute("fornecedores", fornecedorService.findAll());
        model.addAttribute("produtos", produtoService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid Compra compra,
                                  BindingResult result,
                                  Model model) {
        try {
            if (result.hasErrors()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            compra.setData(LocalDate.now());
            compra.setFornecedor(fornecedorService.findOne(compra.getFornecedor().getId()));
            compra.getCompraProdutos().forEach(cp -> cp.getId().setCompra(compra));

            compraService.save(compra);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("{id}")
    public String form(@PathVariable Long id, Model model) {
        model.addAttribute("compra", compraService.findOne(id));
        carregarCombos(model);
        return "compra/form";
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            compraService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
