import org.testng.annotations.Test;
import tools.ReportGenerator;

public class BaseTestFinish
{
    @Test
    public void generateReport()
    {
        ReportGenerator report = new ReportGenerator();
        report.generateReport();
    }
}
