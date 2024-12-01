package com.qikserve.supermarket.enuns;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public enum TypePromotion {

    FLAT_PERCENT(1L),

    QTY_BASED_PRICE_OVERRIDE(2L),

    BUY_X_GET_Y_FREE(3L);

    private final Long type;

}
