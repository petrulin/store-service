
package com.otus.storeservice.rabbitmq.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class DishDTO {
    private Long menuId;
    private Long count;

}
