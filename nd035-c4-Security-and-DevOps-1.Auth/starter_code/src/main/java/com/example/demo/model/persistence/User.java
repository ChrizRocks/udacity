package com.example.demo.model.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	private long id;
	
	@Column(nullable = false, unique = true)
	@JsonProperty
	private String username;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(nullable = false)
	private String password;

	private byte[] salt = createSalt();

	public String getPassword(){
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
		//this.password = set_SecurePassword(password,salt);
	}

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
	@JsonIgnore
    private Cart cart;
	
	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	private static byte[] createSalt(){
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return salt;
	}

//	private String set_SecurePassword(String passwordToHash, byte[] salt){
//		String generatedPassword = null;
//		try {
//			MessageDigest md = MessageDigest.getInstance("SHA-256");
//			md.update(salt);
//			byte[] bytes = md.digest(passwordToHash.getBytes());
//			StringBuilder sb = new StringBuilder();
//			for (int i = 0; i< bytes.length;i++){
//				sb.append(Integer.toString( (bytes[i] & 0xff ) + 0x100,16 ).substring(1));
//			}
//			generatedPassword = sb.toString();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		return generatedPassword;
//	}
	
}
