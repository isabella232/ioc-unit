package com.oneandone.iocunit.jta.narayana.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author aschoerk
 */
@Entity

@Table(name = "test_entity_1")
public class TestEntity1  {

    static Logger logger = LoggerFactory.getLogger(TestEntity1.class);

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "string_attribute")
    private String stringAttribute;

    @Column(name = "int_attribute")
    private int intAttribute;

    public TestEntity1() {

    }

    public Long getId() {
        return id;
    }

    public void setId(long idP) {
        this.id = idP;
    }

    public String getStringAttribute() {
        return stringAttribute;
    }

    public void setStringAttribute(String stringAttributeP) {
        this.stringAttribute = stringAttributeP;
    }

    public int getIntAttribute() {
        return intAttribute;
    }

    public void setIntAttribute(int intAttributeP) {
        this.intAttribute = intAttributeP;
    }

}
