package com.endava.grocy;

import com.endava.grocy.client.EntityClient;
import com.endava.grocy.client.StockClient;
import com.endava.grocy.data.DataProvider;
import com.endava.grocy.fixture.GrocyFixture;

public class TestBaseClass {

    protected EntityClient entityClient = new EntityClient();
    protected StockClient stockClient = new StockClient();
    protected DataProvider dataProvider = new DataProvider();
    protected GrocyFixture grocyFixture = new GrocyFixture();
}
