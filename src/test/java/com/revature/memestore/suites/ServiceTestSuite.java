package com.revature.memestore.suites;

import com.revature.memestore.services.InventoryServiceTest;
import com.revature.memestore.services.InvoiceServiceTest;
import com.revature.memestore.services.UserServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserServiceTest.class,
        InvoiceServiceTest.class,
        InventoryServiceTest.class
})
public class ServiceTestSuite {
}
