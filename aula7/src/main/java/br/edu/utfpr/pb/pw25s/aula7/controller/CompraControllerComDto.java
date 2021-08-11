package br.edu.utfpr.pb.pw25s.aula7.controller;

import br.edu.utfpr.pb.pw25s.aula7.dto.CompraDto;
import br.edu.utfpr.pb.pw25s.aula7.model.Compra;
import br.edu.utfpr.pb.pw25s.aula7.model.CompraProduto;
import br.edu.utfpr.pb.pw25s.aula7.model.CompraProdutoPK;
import br.edu.utfpr.pb.pw25s.aula7.service.CompraService;
import br.edu.utfpr.pb.pw25s.aula7.service.FornecedorService;
import br.edu.utfpr.pb.pw25s.aula7.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("compra-dto")
public class CompraControllerComDto {

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
        return "compra/form-dto";
    }

    private void carregarCombos(Model model) {
        model.addAttribute("fornecedores", fornecedorService.findAll());
        model.addAttribute("produtos", produtoService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid CompraDto compraDto,
                                  BindingResult result,
                                  Model model) {
        try {
            if (result.hasErrors()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            compraService.save(compraDtoToCompra(compraDto));
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
        return "compra/form-dto";
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

    public Compra compraDtoToCompra(CompraDto compraDto) {
        Compra compra = new Compra();

        List<CompraProduto> compraProdutos = new ArrayList<>();
        compraDto.getCompraProdutos().forEach( cpDto -> {
            CompraProdutoPK pk = new CompraProdutoPK();
            pk.setCompra(compra);
            pk.setProduto(produtoService.findOne(cpDto.getProdutoId()));

            CompraProduto cp = new CompraProduto();
            cp.setId(pk);
            cp.setQuantidade(cpDto.getQuantidade());
            cp.setValor(cpDto.getValor());
            compraProdutos.add(cp);
        });
        compra.setFornecedor(fornecedorService.findOne(compraDto.getFornecedorId()));
        compra.setData(LocalDate.now());
        compra.setObservacoes(compraDto.getObservacoes());
        compra.setNotaFiscal(compraDto.getNotaFiscal());
        compra.setCompraProdutos(compraProdutos);

        return compra;
    }
}
