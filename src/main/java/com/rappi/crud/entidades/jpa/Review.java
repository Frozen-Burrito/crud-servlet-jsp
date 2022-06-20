package com.rappi.crud.entidades.jpa;

import com.rappi.crud.dao.ReviewDAO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "review")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Review.findAll", query = "SELECT r FROM Review r"),
    @NamedQuery(name = "Review.findById", query = "SELECT r FROM Review r WHERE r.id = :id"),
    @NamedQuery(name = "Review.findByPuntaje", query = "SELECT r FROM Review r WHERE r.puntaje = :puntaje"),
    @NamedQuery(name = "Review.findByContenido", query = "SELECT r FROM Review r WHERE r.contenido = :contenido"),
    @NamedQuery(name = "Review.findByFecha", query = "SELECT r FROM Review r WHERE r.fecha = :fecha")})
public class Review implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    // Nombre para "mostrar" en vistas publicas.
    public static final String NOMBRE_ENTIDAD = "Reseña";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "puntaje")
    private short puntaje;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "contenido")
    private String contenido;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    
    @JoinColumn(name = "id_autor", referencedColumnName = "nombre_usuario")
    @ManyToOne(optional = false)
    private Usuario autor;
    
    @JoinColumn(name = "id_restaurante", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Restaurante restaurante;
    

    public Review()
    {
    }

    public Review(Integer id)
    {
        this.id = id;
    }

    public Review(Integer id, short puntaje, String contenido, Date fecha)
    {
        this.id = id;
        this.puntaje = puntaje;
        this.contenido = contenido;
        this.fecha = fecha;
    }
    
       /**
     * Crea una nueva Reseña a partir de un mapa de parámetros, donde las llaves son
     * los nombres de las columnas en la BD.
     * 
     * @param parametros Un conjunto de valores para cada columna.
     * @return Una instancia de Reseña correspondiente.
     */
    public static Review desdeParametros(Map<String, String[]> parametros)
    {
        Map<String, String> campos = new HashMap<>(); 
        
        // Obtener solo el primer valor para cada atributo (transformar un String[]
        // en solo un String.
        parametros.entrySet().forEach(entrada -> {
            System.out.println(entrada.getKey() + ": " + entrada.getValue()[0]);
            campos.put(
                    entrada.getKey(), 
                    entrada.getValue() != null ? entrada.getValue()[0] : null
            );
        });
        
        // Crear nueva instancia de Usuario.
        final Review review = new Review();
        
        // Obtener los valores a partir de los campos.
        String idReviewStr = campos.get(ReviewDAO.COLUMNA_ID);
        
        if (idReviewStr != null)
        {
            review.setId(Integer.parseInt(idReviewStr));
        }
        
        String puntajeStr = campos.get(ReviewDAO.COLUMNA_PUNTAJE);
        
        if (puntajeStr != null)
        {
            review.setPuntaje(Short.parseShort(puntajeStr));
        }
        
        String contenido = campos.get(ReviewDAO.COLUMNA_CONTENIDO);
        review.setContenido(contenido);
        
        // Las fechas necesitan cambios, porque el HTML las produce con una "T" 
        // entre la fecha y hora, además de que no incluyen segundos.
        String strFecha = campos.get(ReviewDAO.COLUMNA_FECHA);
        
        if (strFecha != null)
        {
            review.setFecha(Timestamp.valueOf(strFecha.replace("T", " ").concat(":00")));
        }
        
        String idAutorStr = campos.get(ReviewDAO.COLUMNA_ID_AUTOR);
        review.setAutor(new Usuario(idAutorStr));
        
        String idRestauranteStr = campos.get(ReviewDAO.COLUMNA_ID_RESTAURANTE);
        
        if (idRestauranteStr != null)
        {
            short idRestaurante = Short.parseShort(idRestauranteStr);
            review.setRestaurante(new Restaurante(idRestaurante));
        }
    
        // Retornar nueva instancia de la entidad.
        return review;
    }
    
    @Override
    public String toString()
    {
        return "Review{" + "id=" + id + ", puntaje=" + puntaje + ", contenido=" + contenido + ", fecha=" + fecha + ", nombreUsuarioAutor=" + autor.getNombreUsuario() + ", idRestaurante=" + restaurante.getId() + " }";
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public short getPuntaje()
    {
        return puntaje;
    }

    public void setPuntaje(short puntaje)
    {
        this.puntaje = puntaje;
    }

    public String getContenido()
    {
        return contenido;
    }

    public void setContenido(String contenido)
    {
        this.contenido = contenido;
    }

    public Date getFecha()
    {
        return fecha;
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    public Usuario getAutor()
    {
        return autor;
    }

    public void setAutor(Usuario autor)
    {
        this.autor = autor;
    }

    public Restaurante getRestaurante()
    {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante)
    {
        this.restaurante = restaurante;
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
        if (!(object instanceof Review)) 
        {
            return false;
        }
        
        Review other = (Review) object;
         
        boolean idEsNulo = this.id == null && other.id == null;
        boolean idsCoinciden = this.id != null && this.id.equals(other.id);
        
        return (idEsNulo || idsCoinciden);        
    }
}
