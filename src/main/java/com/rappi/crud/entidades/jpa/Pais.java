package com.rappi.crud.entidades.jpa;

import com.rappi.crud.dao.PaisDAO;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "pais")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pais.findAll", query = "SELECT p FROM Pais p"),
    @NamedQuery(name = "Pais.findByCodigoIso3166", query = "SELECT p FROM Pais p WHERE p.codigoIso3166 = :codigoIso3166"),
    @NamedQuery(name = "Pais.findByNombre", query = "SELECT p FROM Pais p WHERE p.nombre = :nombre")})
public class Pais implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    // Nombre para "mostrar" en vistas publicas.
    public static final String NOMBRE_ENTIDAD = "País";
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "codigo_iso_3166")
    private String codigoIso3166;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 127)
    @Column(name = "nombre")
    private String nombre;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pais")
    private Collection<Estado> estadoCollection;

    public Pais()
    {
    }

    public Pais(String codigoIso3166)
    {
        this.codigoIso3166 = codigoIso3166;
    }

    public Pais(String codigoIso3166, String nombre)
    {
        this.codigoIso3166 = codigoIso3166;
        this.nombre = nombre;
    }
    
    /**
     * Crea un nuevo Pais a partir de un mapa de parámetros, donde las llaves son
     * los nombres de las columnas en la BD.
     * 
     * @param parametros Un conjunto de valores para cada columna.
     * @return Una instancia de Pais correspondiente.
     */
    public static Pais desdeParametros(Map<String, String[]> parametros)
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
        
        // Si no hay datos, indicar que el objeto de Datos estaría vacío usando
        // un NullPointerException.
        
        int numMinCampos = PaisDAO.ID_ES_AUTOMATICO ? 2 : 3;
        
        System.out.println("Size campos: " + campos.size());
        
        if (campos.size() < numMinCampos) 
        {
            throw new NullPointerException();
        }
        
        Pais pais = new Pais();
        
        // Obtener los valores a partir de los campos.
        String nombre = campos.get(PaisDAO.COLUMNA_NOMBRE);
        pais.setNombre(nombre);
        
        String codigoPaisString;
        
        if (campos.get(PaisDAO.COLUMNA_ID) != null)
        {
            codigoPaisString = campos.get(PaisDAO.COLUMNA_ID);
            pais.setCodigoIso3166(codigoPaisString);
        }
    
        // Retornar nueva instancia de la entidad.
        return pais;
    }
    
    @Override
    public String toString()
    {
        return String.format("%s", nombre);
    }

    public String getCodigoIso3166()
    {
        return codigoIso3166;
    }

    public void setCodigoIso3166(String codigoIso3166)
    {
        this.codigoIso3166 = codigoIso3166;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    @XmlTransient
    public Collection<Estado> getEstadoCollection()
    {
        return estadoCollection;
    }

    public void setEstadoCollection(Collection<Estado> estadoCollection)
    {
        this.estadoCollection = estadoCollection;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (codigoIso3166 != null ? codigoIso3166.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pais)) {
            return false;
        }
        Pais other = (Pais) object;
        
        boolean idsCoinciden = this.codigoIso3166.equals(other.codigoIso3166);
        
        return idsCoinciden;
    }
}
