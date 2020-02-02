package model.entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Vendedor implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String email;

	private String nome;

	private Date nascimento;

	private Double salario;

	private Departamento departamento;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
    public static Vendedor getInstanceFromResultSet(ResultSet rs, Departamento dep)
            throws SQLException {
        Vendedor vendedor = new Vendedor();
        vendedor.setId(rs.getInt("id"));
        vendedor.setNome(rs.getString("nome"));
        vendedor.setEmail(rs.getString("email"));
        vendedor.setNascimento(rs.getDate("nascimento"));
        vendedor.setSalario(rs.getDouble("salario"));
        vendedor.setDepartamento(dep);

        return vendedor;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vendedor other = (Vendedor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vendedor [id=" + id + ", email=" + email + ", nome=" + nome + ", nascimento=" + nascimento
				+ ", salario=" + salario + ", departamento=" + departamento + "]";
	}
}
