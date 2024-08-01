package dev.app.cocospring.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GetCoinInfo {

    private String status;
    private Map<String, Object> data;

}
