package com.adp.coding.test.service;


import com.adp.coding.test.exception.ChangeNotSufficientException;
import com.adp.coding.test.model.request.ChangeBillRequest;
import com.adp.coding.test.model.ChangeType;
import com.adp.coding.test.model.CoinChangeData;
import com.adp.coding.test.model.CoinNumProperties;
import com.adp.coding.test.model.response.CoinChangeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

@Service
@Slf4j
public class BillChangeService {

    @Value("${coin.sort.order:asc}")
    private String order;


    Map<ChangeType, CoinChangeData> billChangeData = null;
    private CoinNumProperties coinNumProperties;

    public BillChangeService(CoinNumProperties coinNumProperties){
        this.coinNumProperties = coinNumProperties;
    }

    @PostConstruct
    public void init() {
        if("desc".equalsIgnoreCase(order))
          //  billChangeData = new TreeMap(Collections.reverseOrder());
            billChangeData = new ConcurrentSkipListMap(Collections.reverseOrder());
        else
            billChangeData = new ConcurrentSkipListMap();// new TreeMap();

        Arrays.stream(ChangeType.values())
                .forEach(changeType -> {
                            switch (changeType) {
                                case ONE:
                                    billChangeData.put(changeType, CoinChangeData.builder()
                                            .availableChange(coinNumProperties.getOne())
                                            .total(changeType.getCoin() * coinNumProperties.getOne()).build());
                                    break;
                                case FIVE:
                                    billChangeData.put(changeType, CoinChangeData.builder()
                                            .availableChange(coinNumProperties.getFive())
                                            .total(changeType.getCoin() * coinNumProperties.getFive()).build());
                                    break;
                                case TEN:
                                    billChangeData.put(changeType, CoinChangeData.builder()
                                            .availableChange(coinNumProperties.getTen())
                                            .total(changeType.getCoin() * coinNumProperties.getTen()).build());
                                    break;
                                case TWENTY_FIVE:
                                    billChangeData.put(changeType, CoinChangeData.builder()
                                            .availableChange(coinNumProperties.getTwentyFive())
                                            .total(changeType.getCoin() * coinNumProperties.getTwentyFive()).build());
                                    break;
                            }
                        }
                );
    }

    private double calculateTotalValueOfChange(){

        Double totalValue = Double.valueOf(0.0);
        totalValue = billChangeData.values().stream().map(e-> e.getTotal()).reduce(0.0,Double::sum);
        return totalValue;
    }
    
    public CoinChangeResponse changeBill(ChangeBillRequest changeBillRequest){
        log.info("{}",changeBillRequest);
        Double bill = Double.valueOf(changeBillRequest.getBill().doubleValue());

        if(bill > calculateTotalValueOfChange()) {
            log.error("Bill changed was not done.");
            throw new ChangeNotSufficientException(bill);
        }

        CoinChangeResponse coinChangeResponse = new CoinChangeResponse();;

        for(ChangeType key: billChangeData.keySet())  {
            log.debug("key:{}", key);
            Double noUsedChangeBill = 0.0;
            CoinChangeData coinChangeData = billChangeData.get(key);
            Double availableValue = coinChangeData.getTotal();
            if(availableValue == 0)
                continue;

            Double noRequiredChangedBill = Math.floor(bill/key.getCoin());
            log.debug("changedBill:{}", noRequiredChangedBill);
            if(noRequiredChangedBill > 0 && coinChangeData.getAvailableChange() > 0) {
                double valueToApply = 0;
                if(noRequiredChangedBill <= coinChangeData.getAvailableChange()) {
                    valueToApply = noRequiredChangedBill * key.getCoin();
                } else {
                    valueToApply =  coinChangeData.getTotal();

                }
                noUsedChangeBill = valueToApply/key.getCoin();
                bill = bill - valueToApply;
                coinChangeData.setTotal(availableValue - valueToApply);
                coinChangeData.setAvailableChange(coinChangeData.getAvailableChange() - noUsedChangeBill.intValue());
                billChangeData.put (key,coinChangeData);
                setCoinChangeResponse(key, noUsedChangeBill.intValue(), coinChangeResponse);
            }
            if(bill == 0)
                break;
        };
        return coinChangeResponse;
    }

    private void setCoinChangeResponse(ChangeType changeType , int noUsedChangeBill ,CoinChangeResponse coinChangeResponse){
        switch (changeType) {
            case ONE:
                coinChangeResponse.setOneCent(noUsedChangeBill);
                break;
            case FIVE:
                coinChangeResponse.setFiveCents(noUsedChangeBill);
                break;
            case TEN:
                coinChangeResponse.setTenCents(noUsedChangeBill);
                break;
            case TWENTY_FIVE:
                coinChangeResponse.setTwentyFiveCents(noUsedChangeBill);
                break;
        }
    }
}
