package dev.app.cocospring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "INTEREST_COIN_TABLE")
public class InterestCoinEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coin_name")
    private String coinName;

    @Column(name = "opening_price")
    private String openingPrice;

    @Column(name = "closing_price")
    private String closingPrice;

    @Column(name = "min_price")
    private String minPrice;

    @Column(name = "max_price")
    private String maxPrice;

    @Column(name = "units_traded")
    private String unitsTraded;

    @Column(name = "acc_trade_value")
    private String accTradeValue;

    @Column(name = "prev_closing_price")
    private String prevClosingPrice;

    @Column(name = "units_traded_24H")
    private String unitsTraded24H;

    @Column(name = "acc_trade_value_24H")
    private String accTradeValue24H;

    @Column(name = "fluctate_24H")
    private String fluctate24H;

    @Column(name = "fluctate_rate_24H")
    private String fluctateRate24H;

    @Column(name = "selected")
    private Boolean selected;


    public void insertCoinInfo(
            Long id, String coin_name, String opening_price, String closing_price, String min_price, String max_price,
            String units_traded, String acc_trade_value, String prev_closing_price,String units_traded_24H,
            String acc_trade_value_24H, String fluctate_24H, String fluctate_rate_24H, Boolean selected
    ){
        this.id = id;
        this.coinName = coin_name;
        this.openingPrice = opening_price;
        this.closingPrice = closing_price;
        this.minPrice = min_price;
        this.maxPrice = max_price;
        this.unitsTraded = units_traded;
        this.accTradeValue = acc_trade_value;
        this.prevClosingPrice = prev_closing_price;
        this.unitsTraded24H = units_traded_24H;
        this.accTradeValue24H = acc_trade_value_24H;
        this.fluctate24H = fluctate_24H;
        this.fluctateRate24H = fluctate_rate_24H;
        this.selected = selected;
    }


    public void changeSelected(Boolean selected){
        this.selected = selected;
    }


//    @PrimaryKey(autoGenerate = true)
//    val id : Int,
//    val coin_name : String,
//    val opening_price : String,
//    val closing_price : String,
//    val min_price : String,
//    val max_price : String,
//    val units_traded : String,
//    val acc_trade_value : String,
//    val prev_closing_price : String,
//    val units_traded_24H : String,
//    val acc_trade_value_24H : String,
//    val fluctate_24H : String,
//    val fluctate_rate_24H : String,
//    var selected : Boolean
}
