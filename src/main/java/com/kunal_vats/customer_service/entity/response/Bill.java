package com.kunal_vats.customer_service.entity.response;

import com.kunal_vats.customer_service.enums.BillStatus;
import lombok.Data;

@Data
public class Bill {

    Long billId;
    Long customerId;
    Long amount;
    BillStatus status;

}