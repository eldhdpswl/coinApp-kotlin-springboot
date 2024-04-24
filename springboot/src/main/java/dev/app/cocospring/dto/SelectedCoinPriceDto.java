package dev.app.cocospring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dev.app.cocospring.JsonComponent.JsonDateSerializerAndDeserializer;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class SelectedCoinPriceDto {

    private Long id;
    private String coinName;
    private String transaction_date;
    private String type;
    private String units_traded;
    private String price;
    private String total;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = JsonDateSerializerAndDeserializer.Serializer.class)
    @JsonDeserialize(using = JsonDateSerializerAndDeserializer.Deserializer.class)
    private Date timeStamp;


//    @JsonSerialize(using = JsonDateSerializerAndDeserializer.Serializer.class)
//    @JsonDeserialize(using = JsonDateSerializerAndDeserializer.Deserializer.class)
//    private String timeStamp;

    @Builder
    public SelectedCoinPriceDto(Long id, String coinName, String transaction_date, String type,
                                String units_traded, String price, String total, Date timeStamp){
        this.id = id;
        this.coinName = coinName;
        this.transaction_date = transaction_date;
        this.type = type;
        this.units_traded = units_traded;
        this.price = price;
        this.total = total;
        this.timeStamp = timeStamp;
    }

}
