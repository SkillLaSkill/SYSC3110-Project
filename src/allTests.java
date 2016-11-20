import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({NodeTest.class, GraphTest.class, PacketTest.class, SimulationTest.class})
public class allTests {}
