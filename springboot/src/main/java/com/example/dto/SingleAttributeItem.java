package com.example.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SingleAttributeItem {
    private String name;
    private String value;
}
