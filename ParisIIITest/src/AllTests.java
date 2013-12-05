import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({parisInitTests.AllParisInitTests.class, parisWorkTests.AllParisWorkTests.class,
	parisGUITests.AllParisGUITests.class,
	UnifacTest.AllUnifacTests.class, MixtureTest.AllMixtureTests.class})
public class AllTests {

}
