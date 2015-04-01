package parisGUITests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ParisInitGUITest.class, ParisTabFolderGUITest.class, ParisMenuGUITest.class,
	ParisReferenceGUITest.class, ParisGeneralGUITest.class})
public class AllParisGUITests {

}
