package com.example.demo.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
//ORM-->OBJ. MAPPED INTO A RELATION(TABLE)
@Entity
public class user {
	@Id
	int uid;
	String pwd;
	public user(int uid, String pwd) {
		super();
		this.uid = uid;
		this.pwd = pwd;
	}
	public user() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	@Override
	public String toString() {
		return "user [uid=" + uid + ", pwd=" + pwd + "]";
	}
	
	

}