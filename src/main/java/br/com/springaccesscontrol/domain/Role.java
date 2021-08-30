package br.com.springaccesscontrol.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Size(max = 45)
    private String name;
    
    
    public Integer getId() {
        return id;
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setId(Integer id) {
		this.id = id;
	}
     
    
}