package com.example.catalogue.catalogueservice.repository;

import com.example.catalogue.catalogueservice.entity.Item;
import com.example.catalogue.catalogueservice.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Query(nativeQuery = true,
            value = "select it.* from item it " +
                    "join manufacturer m on it.manufacturer_id = m.id " +
                    "where it.price < :price and m.name = :manufacturerName")
    List<Item> getItemsByPriceLessAndManufacturer(@Param("price") BigDecimal price,
                                                  @Param("manufacturerName") String manufacture);

    @Query( nativeQuery = true,
            value = "select it.* from item it " +
                    "join item_category ic on it.id = ic.item_id " +
                    "join category c on ic.category_id = c.id " +
                    "where c.name = :categoryName")
    List<Item> findByCategoryName(@Param("categoryName") String categoryName);


    @Query(nativeQuery = true,
            value = "select it.* from item it " +
                    "join manufacturer m on it.manufacturer_id = m.id " +
                    "join item_category ic on it.id = ic.item_id " +
                    "join category c on ic.category_id = c.id " +
                    "where c.name = :categoryName and " +
                    "m.name = :manufacturerName")
    List<Item> findByCategoryNameAndManufacturerName(@Param("categoryName") String categoryName,
                                                     @Param("manufacturerName") String manufacturerName);


    @Query(nativeQuery = true,
            value = "select it.price from item it " +
                    "where it.id in :ids " +
                    "order by array_position(cast(string_to_array(rtrim(ltrim(cast((:ids) as text),'('),')'),',') as int[]), cast(it.id as int))")
    List<BigDecimal> findPricesByIdInOrdered(@Param("ids") List<Integer> ids);


    List<Item> deleteAllByManufacturer(Manufacturer manufacturer);

    List<Item> findAllByManufacturer(Manufacturer manufacturer);
}

