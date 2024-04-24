package dev.app.cocospring.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class InterestCoinDto {

    private Long id;
    private String coin_name;
    private String opening_price;
    private String closing_price;
    private String min_price;
    private String max_price;
    private String units_traded;
    private String acc_trade_value;
    private String prev_closing_price;
    private String units_traded_24H;
    private String acc_trade_value_24H;
    private String fluctate_24H;
    private String fluctate_rate_24H;
    private Boolean selected;

    @Builder
    public InterestCoinDto(Long id, String coin_name, String opening_price, String closing_price, String min_price, String max_price,
                           String units_traded, String acc_trade_value, String prev_closing_price,String units_traded_24H,
                           String acc_trade_value_24H, String fluctate_24H, String fluctate_rate_24H, Boolean selected){
        this.id = id;
        this.coin_name = coin_name;
        this.opening_price = opening_price;
        this.closing_price = closing_price;
        this.min_price = min_price;
        this.max_price = max_price;
        this.units_traded = units_traded;
        this.acc_trade_value = acc_trade_value;
        this.prev_closing_price = prev_closing_price;
        this.units_traded_24H = units_traded_24H;
        this.acc_trade_value_24H = acc_trade_value_24H;
        this.fluctate_24H = fluctate_24H;
        this.fluctate_rate_24H = fluctate_rate_24H;
        this.selected = selected;
    }

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
