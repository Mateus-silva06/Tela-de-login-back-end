package br.com.projetofinal.Projetofinal.DAO;

import org.springframework.data.repository.CrudRepository;

import br.com.projetofinal.Projetofinal.model.Usuario;

public interface IUsuario extends CrudRepository<Usuario, String> {

}
