package com.czareg.battlefield.feature.command.order;

import com.czareg.battlefield.feature.command.entity.Command;

public abstract class Order {

    public abstract Command execute(OrderContext context);
}
