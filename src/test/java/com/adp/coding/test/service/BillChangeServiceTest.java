package com.adp.coding.test.service;

import com.adp.coding.test.exception.ChangeNotSufficientException;
import com.adp.coding.test.model.ChangeType;
import com.adp.coding.test.model.CoinChangeData;
import com.adp.coding.test.model.CoinNumProperties;
import com.adp.coding.test.model.request.ChangeBillRequest;
import com.adp.coding.test.model.response.CoinChangeResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@RunWith(MockitoJUnitRunner.class)
public class BillChangeServiceTest {

    @InjectMocks
    private BillChangeService billChangeServiceMock;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInit() {
        CoinNumProperties coinNumProperties = new CoinNumProperties();
        coinNumProperties.setFive(10);
        coinNumProperties.setOne(10);
        coinNumProperties.setTen(10);
        coinNumProperties.setTwentyFive(10);
        ReflectionTestUtils.setField(billChangeServiceMock,"coinNumProperties" , coinNumProperties);

      //  Map<ChangeType, CoinChangeData> billChangeData = new ConcurrentSkipListMap(Collections.reverseOrder());
    //    ReflectionTestUtils.setField(billChangeServiceMock,"billChangeData" , coinNumProperties);
        billChangeServiceMock.init();
        Map<ChangeType, CoinChangeData> billChangeData = (Map<ChangeType, CoinChangeData>)ReflectionTestUtils.getField(billChangeServiceMock,"billChangeData");
        assertEquals(4, billChangeData.size());
    }

    @Test
    public void testChangeBill_asc() {

        CoinNumProperties coinNumProperties = new CoinNumProperties();
        coinNumProperties.setFive(100);
        coinNumProperties.setOne(100);
        coinNumProperties.setTen(100);
        coinNumProperties.setTwentyFive(100);
        ReflectionTestUtils.setField(billChangeServiceMock,"coinNumProperties" , coinNumProperties);
        billChangeServiceMock.init();
        Map<ChangeType, CoinChangeData> billChangeData = (Map<ChangeType, CoinChangeData>)ReflectionTestUtils.getField(billChangeServiceMock,"billChangeData");
        assertEquals(4, billChangeData.size());
        ChangeBillRequest changeBillRequest = new ChangeBillRequest();
        changeBillRequest.setBill(10);
        CoinChangeResponse coinChangeResponse = billChangeServiceMock.changeBill(changeBillRequest);
        assertEquals(0,coinChangeResponse.getTwentyFiveCents());
        assertEquals(40,coinChangeResponse.getTenCents());
        assertEquals(100,coinChangeResponse.getOneCent());
        assertEquals(100,coinChangeResponse.getFiveCents());
    }

    @Test
    public void testChangeBill_desc() {

        CoinNumProperties coinNumProperties = new CoinNumProperties();
        coinNumProperties.setFive(100);
        coinNumProperties.setOne(100);
        coinNumProperties.setTen(100);
        coinNumProperties.setTwentyFive(10);
        ReflectionTestUtils.setField(billChangeServiceMock,"coinNumProperties" , coinNumProperties);
        ReflectionTestUtils.setField(billChangeServiceMock,"order" , "desc");
        billChangeServiceMock.init();
        Map<ChangeType, CoinChangeData> billChangeData = (Map<ChangeType, CoinChangeData>)ReflectionTestUtils.getField(billChangeServiceMock,"billChangeData");
        assertEquals(4, billChangeData.size());
        ChangeBillRequest changeBillRequest = new ChangeBillRequest();
        changeBillRequest.setBill(10);
        CoinChangeResponse coinChangeResponse = billChangeServiceMock.changeBill(changeBillRequest);
        assertEquals(10,coinChangeResponse.getTwentyFiveCents());
        assertEquals(75,coinChangeResponse.getTenCents());
        assertEquals(0,coinChangeResponse.getOneCent());
        assertEquals(0,coinChangeResponse.getFiveCents());
    }


    @Test
    public void testChangeBill_throwsException() {

        CoinNumProperties coinNumProperties = new CoinNumProperties();
        coinNumProperties.setFive(10);
        coinNumProperties.setOne(10);
        coinNumProperties.setTen(10);
        coinNumProperties.setTwentyFive(10);
        ReflectionTestUtils.setField(billChangeServiceMock,"coinNumProperties" , coinNumProperties);
        billChangeServiceMock.init();
        Map<ChangeType, CoinChangeData> billChangeData = (Map<ChangeType, CoinChangeData>)ReflectionTestUtils.getField(billChangeServiceMock,"billChangeData");
        assertEquals(4, billChangeData.size());
        ChangeBillRequest changeBillRequest = new ChangeBillRequest();
        changeBillRequest.setBill(10);
        try {
            billChangeServiceMock.changeBill(changeBillRequest);
        } catch(ChangeNotSufficientException e) {
            assertNotNull(e);
        }
    }

}
