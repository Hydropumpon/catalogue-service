package com.example.catalogue.catalogueservice.entity.jointable;

import com.example.catalogue.catalogueservice.entity.Category;
import com.example.catalogue.catalogueservice.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name="item_category")
@AllArgsConstructor
@EqualsAndHashCode
public class ItemCategory {
    @Id
    @SequenceGenerator(name = "item_category_id_gen", sequenceName = "item_category_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "item_category_id_gen")
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public ItemCategory(Item item, Category category) {
        this.category = category;
        this.item = item;
    }
}
