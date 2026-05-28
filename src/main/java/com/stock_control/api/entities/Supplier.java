package com.stock_control.api.entities;

import ch.qos.logback.core.joran.conditional.IfAction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_suppliers")
@SQLDelete(sql = "UPDATE tb_suppliers SET active = false WHERE cnpj = ?")
@SQLRestriction("active = TRUE")
public class Supplier implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "company_name", nullable = false, length = 100)
    private String companyName;

    @Column(nullable = false, length = 14, unique = true)
    private String cnpj;

    @Column(name = "contact_email", nullable = false, length = 100)
    private String contactEmail;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean active = true;

    public Supplier() {
    }

    public Supplier(UUID id, String companyName, String cnpj, String contactEmail, boolean active) {
        this.id = id;
        this.companyName = companyName;
        this.cnpj = cnpj;
        this.contactEmail = contactEmail;
        this.active = active;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return Objects.equals(id, supplier.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
