package com.rappi.crud.entidades.jpa;

import com.rappi.crud.dao.MunicipioDAO;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "municipio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Municipio.findAll", query = "SELECT m FROM Municipio m"),
    @NamedQuery(name = "Municipio.findById", query = "SELECT m FROM Municipio m WHERE m.id = :id"),
    @NamedQuery(name = "Municipio.findByNombre", query = "SELECT m FROM Municipio m WHERE m.nombre = :nombre")})
public class Municipio implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    // Nombre para "mostrar" en vistas publicas.
    public static final String NOMBRE_ENTIDAD = "Muncipio";
    
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
    
    @JoinColumn(name = "id_estado", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Estado estado;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "municipio")
    private Collection<Colonia> colonias;

    public Municipio()
    {
    }

    public Municipio(Short id)
    {
        this.id = id;
    }

    public Municipio(Short id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
    }
    
        /**
     * Crea un nuevo Municipio a partir de un mapa de parámetros, donde las llaves son
     * los nombres de las columnas en la BD.
     * 
     * @param parametros Un conjunto de valores para cada columna.
     * @return Una instancia de Municipio correspondiente.
     */
    public static Municipio desdeParametros(Map<String, String[]> parametros)
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
        
        // Crear nueva instancia de Municipio.
        final Municipio municipio = new Municipio();
        
        if (campos.get(MunicipioDAO.COLUMNA_ID) != null)
        {
            short id = Short.parseShort(campos.get(MunicipioDAO.COLUMNA_ID));
            municipio.setId(id);
        }
        
        // Obtener los valores a partir de los campos.
        String nombre = campos.get(MunicipioDAO.COLUMNA_NOMBRE);
        municipio.setNombre(nombre);
        
        String idEstadoStr = campos.get(MunicipioDAO.COLUMNA_ID_ESTADO);
        
        if (idEstadoStr != null)
        {
            short idEstado = Short.parseShort(idEstadoStr);
            municipio.setEstado(new Estado(idEstado));
        }
    
        // Retornar la instancia de la entidad.
        return municipio;
    }
    
    @Override
    public String toString()
    {
        return String.format("%s, %s", nombre, estado);
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

    public Estado getEstado()
    {
        return estado;
    }

    public void setEstado(Estado estado)
    {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<Colonia> getColonias()
    {
        return colonias;
    }

    public void setColonias(Collection<Colonia> colonias)
    {
        this.colonias = colonias;
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
        if (!(object instanceof Municipio)) {
            return false;
        }
        Municipio other = (Municipio) object;

        boolean idEsNulo = this.id == null && other.id != null;
        boolean idsNoCoinciden = this.id != null && !this.id.equals(other.id);
        
        if (idEsNulo || idsNoCoinciden) 
        {
            return false;
        }
        
        return (idEsNulo || idsNoCoinciden);
    }
}
