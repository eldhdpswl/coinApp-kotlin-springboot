package dev.app.cocospring.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dev.app.cocospring.JsonComponent.JsonDateSerializerAndDeserializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SELECTED_COIN_PRICE_TABLE")
public class SelectedCoinPriceEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coinName")
    private String coinName;

    @Column(name = "transaction_date")
    private String transactionDate;

    @Column(name = "type")
    private String type;

    @Column(name = "units_traded")
    private String unitsTraded;

    @Column(name = "price")
    private String price;

    @Column(name = "total")
    private String total;

    @Column(name = "timeStamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = JsonDateSerializerAndDeserializer.Serializer.class)
    @JsonDeserialize(using = JsonDateSerializerAndDeserializer.Deserializer.class)
    private Date timeStamp;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")

    public void selectedCoinPrice(Long id, String coinName, String transaction_date, String type,
                                  String units_traded, String price, String total, Date timeStamp){
        this.id = id;
        this.coinName = coinName;
        this.transactionDate = transaction_date;
        this.type = type;
        this.unitsTraded = units_traded;
        this.price = price;
        this.total = total;
        this.timeStamp = timeStamp;
    }


}
