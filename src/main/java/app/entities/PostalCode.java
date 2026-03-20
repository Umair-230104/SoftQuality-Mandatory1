package app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "postal_code")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostalCode
{
    @Id
    @Column(name = "cpostalcode", length = 4, nullable = false)
    private String postalCode;

    @Column(name = "ctownname", length = 100)
    private String townName;
}