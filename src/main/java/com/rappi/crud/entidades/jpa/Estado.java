package com.rappi.crud.entidades.jpa;

import com.rappi.crud.dao.EstadoDAO;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "estado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estado.findAll", query = "SELECT e FROM Estado e"),
    @NamedQuery(name = "Estado.findById", query = "SELECT e FROM Estado e WHERE e.id = :id"),
    @NamedQuery(name = "Estado.findByNombre", query = "SELECT e FROM Estado e WHERE e.nombre = :nombre")})
public class Estado implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    // Nombre para "mostrar" en vistas publicas.
    public static final String NOMBRE_ENTIDAD = "Estado";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Short id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nombre")
    private String nombre;
    
    @JoinColumn(name = "codigo_pais", referencedColumnName = "codigo_iso_3166")
    @ManyToOne(optional = false)
    private Pais pais;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estado")
    private Collection<Municipio> muncipios;

    public Estado()
    {
    }

    public Estado(Short id)
    {
        this.id = id;
    }

    public Estado(Short id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
    }
    
    /**
     * Crea un nuevo Pais a partir de un mapa de parámetros, donde las llaves son
     * los nombres de las columnas en la BD.
     * 
     * @param parametros Un conjunto de valores para cada columna.
     * @return Una instancia de Pais correspondiente.
     */
    public static Estado desdeParametros(Map<String, String[]> parametros)
    {
        Map<String, String> campos = new HashMap<>(); 
        
        // Obtener solo el primer valor para cada atributo (transformar un String[]
        // en solo un String.
        parametros.entrySet().forEach(entrada -> {
            campos.put(
                    entrada.getKey(), 
                    entrada.getValue() != null ? entrada.getValue()[0] : null
            );
        });
        
        short id = -1;
        
        if (campos.get(EstadoDAO.COLUMNA_ID) != null)
        {
            id = Short.parseShort(campos.get(EstadoDAO.COLUMNA_ID));
        }
        
        // Obtener los valores a partir de los campos.
        String nombre = campos.get(EstadoDAO.COLUMNA_NOMBRE);
        
        Estado estado = new Estado(id, nombre);
        
        String codigoPais = campos.get(EstadoDAO.COLUMNA_CODIGO_PAIS);
        estado.setPais(new Pais(codigoPais));
    
        // Retornar nueva instancia de la entidad.
        return estado;
    }
    
    @Override
    public String toString()
    {
        return String.format("%s, %s", nombre, pais);
    }

    public Short getId()
    {
        return id;
    }

    public void setId(Short id)
    {
        this.id = id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public Pais getPais()
    {
        return pais;
    }

    public void setPais(Pais pais)
    {
        this.pais = pais;
    }

    @XmlTransient
    public Collection<Municipio> getMuncipios()
    {
        return muncipios;
    }

    public void setMuncipios(Collection<Municipio> muncipios)
    {
        this.muncipios = muncipios;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estado)) {
            return false;
        }
        Estado other = (Estado) object;

        boolean idEsNulo = this.id == null && other.id != null;
        boolean idsNoCoinciden = this.id != null && !this.id.equals(other.id);
        
        if (idEsNulo || idsNoCoinciden) 
        {
            return false;
        }
        
        return (idEsNulo || idsNoCoinciden);
    }
}
