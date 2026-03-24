package br.com.projetofinal.Projetofinal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import br.com.projetofinal.Projetofinal.DAO.IUsuario;
import br.com.projetofinal.Projetofinal.model.Usuario;

@RestController
@CrossOrigin("*")
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private IUsuario dao;
	
	@GetMapping
	public List<Usuario> listaUsuarios() {
		return (List<Usuario>) dao.findAll();
	}
	
	@PostMapping
	public ResponseEntity<?> criarUsuario(@RequestBody Usuario usuario){
		if(dao.existsById(usuario.getCpf())) {
			return ResponseEntity.status(409).body("usuario ja existente");
		}
		
		Iterable<Usuario> lista = dao.findAll();
	    for (Usuario u : lista) {
	        if (u.getEmail().equalsIgnoreCase(usuario.getEmail())) {
	            return ResponseEntity.status(409).body("Email já cadastrado");
	        }
	    }
	    
	    Usuario usuarioNovo = dao.save(usuario);
	    return ResponseEntity.ok(usuarioNovo);
	}
	
	@PutMapping
	public Usuario editarUsuario (@RequestBody Usuario usuario) {
		Usuario usuarioNovo = dao.save(usuario);
		return usuarioNovo;

	}
	
	@DeleteMapping("/{cpf}")
    public String deletarUsuario(@PathVariable String cpf) {
        if (dao.existsById(cpf)) {
            dao.deleteById(cpf);
            return "Usuario com CPF " + cpf + " deletado";
        } else {
            return "Usuario nao encontrado.";
        }
	}
	
	
}