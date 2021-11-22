package org.generation.blogPessoal.seguranca;

import java.util.Collection;

import org.generation.blogPessoal.model.UsuarioModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioDetailsImp implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String userName;
	private String passWord;

	public UsuarioDetailsImp(UsuarioModel user) {
		this.userName = user.getUsuarioModel();
		this.passWord = user.getSenha();

	}

	public UsuarioDetailsImp() {
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return passWord;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
