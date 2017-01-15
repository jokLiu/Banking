package bank.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
   CustomerInfoTest.class,
   CustomerTableTest.class,
   TransactionTest.class,
   DatabaseTest.class,
   LogInInfoTest.class,
   DatabaseRaceTest.class
  
})
public class TestSuite {

}
